package data;

import Item.City;
import Item.OrderListItem;
import main.Main;
import util.JDBCutil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Optable {
    private static Connection conn;
    private static Statement stmt;
    public static String registerUser(String acc,String pas,String nam,String ide,String te){
        try {
            conn=JDBCutil.getConnection();
            stmt = conn.createStatement();
            String sql="insert into user values(default,'"+nam+"','"+acc+"','"+pas+"','"+ide+"','"+te+"');";
            stmt.execute(sql);
            JDBCutil.free(null,stmt,conn);
            return "s";
        } catch (SQLException e) {
            JDBCutil.free(null,stmt,conn);
            return  e.getMessage();
        }
    }

    public static String reFlight(OrderListItem item){
        if(item.getNum()!=null) {
            try {
                conn = JDBCutil.getConnection();
                stmt = conn.createStatement();
                String sql = "delete from forderlist where oid =" + item.getNum() + ";";
                stmt.execute(sql);
                String sql1= null;
                if(item.getKind().equals("1"))
                    sql1 = "update ft set ftcur=" + item.getFtcur() + " where id=" + item.getFtid();
                else sql1="update ft set fjcur=" + item.getFtcur() + " where id=" + item.getFtid();
                stmt.execute(sql1);
                JDBCutil.free(null, stmt, conn);
                item.setNum(null);
                return "s";
            } catch (SQLException e) {
                JDBCutil.free(null, stmt, conn);
                return e.getMessage();
            }
        }else return "f";
    }

    public static int orderFlight(OrderListItem item,String user,int kind){
        ResultSet set=null;
        String num=null;
        try {
            conn=JDBCutil.getConnection();
            stmt = conn.createStatement();
            String sql=null;
            if(user.equals(Main.user.getUname()))
                sql="insert into forderlist values(default,'"+item.getKey()+"','"+item.getFtid()+"','"+Main.user.getUnum()+"',"+kind+",0);";
            else{
                String s="select unum from user where uname='"+user+"';";
                set=stmt.executeQuery(s);
                if(set.next()) {
                    sql="insert into forderlist values(default,'"+item.getKey()+"','"+item.getFtid()+"','"+set.getString("unum")+"',"+kind+",0);";
                }
            }
            String sql1="select oid from forderlist where fid="+item.getKey()+" and uid="+Main.user.getUnum()+" and ftid="+item.getFtid()+";";
            stmt.execute(sql);
            set=stmt.executeQuery(sql1);
            while(set.next()){
                num=set.getString("oid");
            }
            if(user.equals(Main.user.getUname()))
                item.setNum(num);
            String sql2=null;
            if(kind==0)
                sql2 = "update ft set fjcur=" + item.getFjcur() + " where id=" + item.getFtid() + ";";
            else sql2 = "update ft set ftcur=" + item.getFtcur() + " where id=" + item.getFtid() + ";";
            stmt.execute(sql2);
            JDBCutil.free(set,stmt,conn);

            return 1;
        } catch (SQLException e) {
            JDBCutil.free(set,stmt,conn);
            e.printStackTrace();
            return -1;
        }
    }

    public static void addFlight(String fname,String fscity,String fecity,String ftma,String ftmo,String fjma,String fjmo){
        try {
            conn = JDBCutil.getConnection();
            stmt = conn.createStatement();
            String sql = "insert into fflight values (default,'"+fname+"','"+LocalData.getCityId(fscity)
            +"','"+LocalData.getCityId(fecity)+"','"+ftma+"','"+fjma+"','18:00');";
            stmt.execute(sql);
            String s="select * from fflight where fname='"+fname+"';";
            int id=0;
            ResultSet set = stmt.executeQuery(s);
            while (set.next())
                id=set.getInt("fid");
            for(int i=1;i<=7;i++){
                int today=19+i;
                String sql1="insert into ft values (default,"+id+",'2018-06-"+today+"',"+
                        ftma+","+fjma+","+ftmo+","+fjmo+");";
                stmt.execute(sql1);
            }
            JDBCutil.free(null, stmt, conn);
        } catch (SQLException e) {
            JDBCutil.free(null, stmt, conn);
        }
    }

    public static void addCity(String city,String air){
        try {
            conn = JDBCutil.getConnection();
            stmt = conn.createStatement();
            String sql="insert into city values ("+getCityNum()+",'"+city+"','"+air+"');";
            stmt.execute(sql);
            LocalData.cities.add(new City(getCityNum(), city));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static int getCityNum(){
        ResultSet set=null;
        int i=0;
        try {
            conn = JDBCutil.getConnection();
            stmt = conn.createStatement();
            String sql = "select * from city";
            set=stmt.executeQuery(sql);
            while (set.next())
                i++;
        } catch (SQLException e) {
        }
        i++;
        return i;
    }

    public static void setPrint(String oid){
        try {
            conn = JDBCutil.getConnection();
            stmt = conn.createStatement();
            String s="update forderlist set isprint=1 where oid="+oid+";";
            stmt.execute(s);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
