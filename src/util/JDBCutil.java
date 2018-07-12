package util;
import java.sql.*;

public class JDBCutil {
    private static final String url="jdbc:mysql://localhost:3306/FIMS";
    private static final String user=****;
    private static final String password=*****;

    static{
        try{
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public static void free(ResultSet rs,Statement stmt,Connection conn){

        try {
            if(rs!=null)
                rs.close();
            if(stmt!=null)
                stmt.close();
            if(conn!=null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            rs=null;
            stmt=null;
            conn=null;
        }
    }
}
