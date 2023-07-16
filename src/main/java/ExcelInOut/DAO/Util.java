package ExcelInOut.DAO;

import org.apache.poi.poifs.filesystem.FileMagic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author hss
 * @date 2023/7/14 23:09
 */
public class Util {
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection(){
        Connection c=null;
        try {
            c= DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/user?characterEncoding=utf8","root","a12345");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public static boolean isExcelFile(){
        return false;
    }
}
