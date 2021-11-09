import java.sql.*;

public class Sales
{
    public static void main(String[] args) throws SQLException {
        String pid = "p01";
        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:10","sam","password");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select o.aid, SUM(o.qty * o.dollars) as sales from dbi.orders as o where o.pid = '"+pid+"' group by o.aid");

        while (rs.next()) {
            String aid = rs.getString("aid");
            String sales = rs.getString("sales");
            System.out.println(aid +": "+sales);
        }
    }
}