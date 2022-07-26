import java.sql.SQLException;

public class Main
{
    public static void main(String[] args) {
        try
        {
            var pS = DBDao.getConnection().prepareStatement("SELECT COUNT(*) FROM Commodity WHERE Cono = ?");
            pS.setInt(1,1);
            var result = pS.executeQuery();
            System.out.println(result.next());
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}