import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DBDao
{
    private static String dbClassName = "org.mariadb.jdbc.Driver";
    private static String URL =  "jdbc:mariadb://localhost:3306/Shopping";
    private static String name = "yuanx";
    private static String password = "2718281828";
    private static Connection conn = null;
    static
    {
        try
        {
            Class.forName(dbClassName);
            conn = DriverManager.getConnection(URL,name,password);
        }
        catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
    public static Connection getConnection()
    {
        return conn;
    }

    public static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static int getIntNumber()
    {
        int IntNumber;
        while (true)
        {
            try {
                IntNumber = Integer.parseInt(reader.readLine());
                break;
            }
            catch (IOException e)
            {
                System.err.println("输入有误！");
                e.printStackTrace();
            }
            catch (NumberFormatException e)
            {
                System.err.println("输入数字有误请重试！");
            }
        }
        return IntNumber;
    }
}
