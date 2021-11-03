import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Sales
{
    public static void main(String[] args) throws SQLException {
        Connection conn = DriverManager.getConnection("","dbi","");
    }
}