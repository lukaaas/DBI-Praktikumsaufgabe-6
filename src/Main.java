import java.sql.*;

//10.0.2.15
public class Main
{
    public static void main(String[] args)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.178.47:3306/DBI", "dbi","root");

            System.out.println("Connect");
        }catch (Exception e)
        {
            System.out.println(e);
        }

    }
}

