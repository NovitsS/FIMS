package data;

import Item.City;
import Item.Flight;
import Item.OrderListItem;
import Item.UserItem;
import javafx.collections.ObservableList;
import main.Main;
import ui.OrderController;
import util.JDBCutil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServerData {
    private static Connection conn;
    private static Statement stmt;
    private static ResultSet set;

    public static String getUser(String name,String iden){
        try {
            conn=JDBCutil.getConnection();
            stmt=conn.createStatement();
            String sql="select * from user where uname='"+name+"';";
            set = stmt.executeQuery(sql);
            int i=0;
            if(set.next()&&iden.equals(set.getString("uiden"))){
                return name;
            }else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void getUsers(List users){
        try {
            conn=JDBCutil.getConnection();
            stmt=conn.createStatement();
            String sql="select * from user ;";
            set = stmt.executeQuery(sql);
            while (set.next()){
                UserItem item=new UserItem();
                item.setUiden(set.getString("uiden"));
                item.setUname(set.getString("uname"));
                item.setUnum(set.getString("unum"));
                item.setUtel(set.getString("utel"));
                users.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ;
    }

    public static String getUserPassword(String account) throws SQLException {
        conn=JDBCutil.getConnection();
        stmt = conn.createStatement();
        String sql = "select * from user where uaccount ='" + account + "';";
        set = stmt.executeQuery(sql);
        if (set.next()) {
            Main.user=new UserItem();
            Main.user.setUnum(set.getString("unum"));
            Main.user.setUname(set.getString("uname"));
            Main.user.setUiden(set.getString("uiden"));
            Main.user.setUtel(set.getString("utel"));
            LocalData.userItems.add(set.getString("uname"));
            return set.getString("upassword");
        }
        return null;
    }

    public static void getSecondItem(ObservableList<OrderListItem> items,String uid){
        String sql="select isprint,oid,forderlist.fid,ftime,kind from forderlist,ft where uid="+uid+" and ftid=ft.id;";
        try {
            conn=JDBCutil.getConnection();
            stmt=conn.createStatement();
            set=stmt.executeQuery(sql);
            while(set.next()){
                String fid=set.getString("forderlist.fid");
                String date=set.getString("ftime");
                for(int i=0;i<LocalData.list.size();i++){
                    if(LocalData.list.get(i).getKey().equals(fid)&&LocalData.list.get(i).getTime().equals(date)) {
                        LocalData.list.get(i).setKind(set.getString("kind"));
                        LocalData.list.get(i).setIsprint(set.getInt("isprint"));
                        items.add(LocalData.list.get(i));
                        LocalData.list.get(i).setNum(set.getString("oid"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void getFlights(ObservableList<Flight> flights) {
        flights.clear();
        String sql="select fname,ftime,ftcur,fjcur,ftmax,fjmax from fflight,ft where fflight.fid=ft.fid;";
        try {
            conn=JDBCutil.getConnection();
            stmt=conn.createStatement();
            set = stmt.executeQuery(sql);
            while(set.next()){
                int max=set.getInt("ftmax")+set.getInt("fjmax");
                int cur=set.getInt("ftcur")+set.getInt("fjcur");
                Flight flight=new Flight(set.getString("fname"),set.getString("ftime"),max+"",cur+"");
                flights.add(flight);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getOrderListItem(ObservableList<OrderListItem> observableList, String start, String end, String date){
        observableList.clear();
        try {
            String sql2="select ft.id,c1.cname,c2.cname,ftime,fflight.fid,c1.cair,c2.cair,ftcur,fjcur,ftmon,fjmon,fname,fhour from city c1,city c2,fflight,ft " +
                    "where c1.cid=fscity and c2.cid=fecity and fflight.fid=ft.fid;";
            if(start==null&&end==null) {
                conn=JDBCutil.getConnection();
                stmt=conn.createStatement();
                set = stmt.executeQuery(sql2);
                while (set.next()){
                    OrderListItem item = new OrderListItem(set.getString("fname"), set.getString("c1.cname"), set.getString("c2.cname"), set.getString("ftime"),
                            set.getString("c1.cair"), set.getString("c2.cair"));
                    item.setKey(set.getString("fflight.fid"));
                    item.setHour(set.getString("fhour"));
                    item.setFjcur(set.getString("fjcur"));
                    item.setFjmon(set.getString("fjmon"));
                    item.setFtcur(set.getString("ftcur"));
                    item.setFtmon(set.getString("ftmon"));
                    item.setFtid(set.getString("ft.id"));
                    observableList.add(item);
                }
            }
            else {
                if (date == null)
                    for(int i=0;i<LocalData.list.size();i++)
                    {
                        if(LocalData.list.get(i).getStart().equals(start)&&LocalData.list.get(i).getEnd().equals(end))
                            observableList.add(LocalData.list.get(i));
                    }else {
                    for(int i=0;i<LocalData.list.size();i++)
                    {
                        if(LocalData.list.get(i).getStart().equals(start)&&LocalData.list.get(i).getEnd().equals(end)
                                &&LocalData.list.get(i).getTime().equals(date))
                            observableList.add(LocalData.list.get(i));
                    }
                }

            }

            JDBCutil.free(set,stmt,conn);
        } catch (SQLException e) {
            e.printStackTrace();
            JDBCutil.free(set,stmt,conn);
        }
    }

    public static void getCityItem(ObservableList<String> startItem,ObservableList<String> endItem){
        try {
            conn = JDBCutil.getConnection();
            stmt = conn.createStatement();
            String sql="select * from city;";
            set = stmt.executeQuery(sql);
            while (set.next()){
                startItem.add(set.getString("cname"));
                endItem.add(set.getString("cname"));
                City  c= new City(set.getInt("cid"),set.getString("cname"));
                LocalData.cities.add(c);
            }
            JDBCutil.free(set,stmt,conn);
        } catch (SQLException e) {
            e.printStackTrace();
            JDBCutil.free(set,stmt,conn);
        }

    }
}
