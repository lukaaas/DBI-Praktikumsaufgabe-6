import java.io.*;
import java.sql.*;

public class CustomerOverview
{
    protected static BufferedReader stdin =
            new BufferedReader(new InputStreamReader(System.in));

    // helper method for reading input
    protected static String getInput(String prompt)
    {
        try
        {
            System.out.println(prompt);
            return stdin.readLine();
        }
        catch (IOException e)
        {
            System.err.println(e.toString());
            return null;
        }
    }  // end getInput

    // straightforward helper method for connecting to a database
    // (please see the JDBC DataSource interface for a more preferred,
    // however slightly more complex and abstract way for getting
    // a JDBC Connection object!)
    protected static Connection getConnection(
            String dbUrl, String userId, String userPwd)
            throws SQLException
    {
        return DriverManager.getConnection(dbUrl, userId, userPwd);
    }  // end getConnection

    // main program
    public static void main(String[] args) throws SQLException
    {
        final String dbUrl = "jdbc:postgresql:postgres";	// URL local postgresql

        if (args.length!=2)	// userid and password provided?
        {
            System.err.println(
                    "\nusage: java CustomerOverview <userid> <passwd>\n");
            System.exit(1);
        }
        else
        {
            Connection         conn = null;
            PreparedStatement  stmt = null;
            ResultSet          rs   = null;

            try
            {
                conn = getConnection(dbUrl, args[0], args[1]);
                conn.setAutoCommit(false);
                System.out.println("\nConnected to sample database!\n");

                stmt = conn.prepareStatement(
                        "select agents.aid, agents.aname, sum(dollars) " +
                                "from dbi.orders, dbi.agents " +
                                "where cid=? and orders.aid=agents.aid " +
                                "group by agents.aid, agents.aname" );

                String customerId = getInput("Please enter customer id: ");

                while ((customerId!=null) && (customerId.length()!=0))
                {
                    stmt.setString(1,customerId);
                    rs = stmt.executeQuery();

                    System.out.println();
                    System.out.println("AID|        ANAME|    DOLLARS");
                    System.out.println("---|-------------|-----------");

                    while (rs.next())
                    {
                        System.out.println(rs.getString(1) + "\t\t" +
                                rs.getString(2) + "\t\t" + rs.getDouble(3));
                    }
                    rs.close();
                    conn.commit();
                    stmt.clearParameters();
                    customerId = getInput("\nPlease enter customer id: ");
                }

                stmt.close();
                conn.close();
                System.out.println("\nDisconnected!\n");

            }
            catch (SQLException e)
            {
                System.err.println(e.toString());
                System.exit(1);
            }
            finally // close used resources
            {
                if (rs!=null)   rs.close();
                if (stmt!=null) stmt.close();
                if (conn!=null) conn.close();
            }
        }
    }  // end main
}  // end class CustomerOverview