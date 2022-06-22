import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Dao extends DBDao
{
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int getIntNumber()
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
    public static boolean searchCommodity()
    {
        System.out.println("输入要查询的商品的编号：");
        int Cono = getIntNumber();
        PreparedStatement pS = null;
        Commodity comm = null;
        try
        {
            pS = getConnection().prepareStatement("SELECT count() FROM Commodity WHERE Cono = ? LIMIT 1");
            pS.setInt(1,Cono);
            ResultSet result = pS.executeQuery();
            if (!result.next())
            {
                System.out.println("商品编号不正确，没有该商品！");
                return false;
            }
            comm = new Commodity(Cono);
        }
        catch (SQLException e)
        {
            System.err.println("数据库连接有误！");
            e.printStackTrace();
        }
        if (comm != null)
        {
            System.out.println(comm);
            return true;
        }
        else
        {
            System.out.println("商品编号不正确，没有该商品！");
            return false;
        }
    }
    public static boolean deleteComodity()
    {
        System.out.println("输入要删除的商品的编号：");
        int Cono = getIntNumber();
        try
        {
            PreparedStatement pS = getConnection().prepareStatement("DELETE FROM Commodity WHERE Cono = ?");
            pS.setInt(1,Cono);
            int b = pS.executeUpdate();
            if (b > 0)
            {
                System.out.println("删除成功！");
                return true;
            }
            else
            {
                System.out.println("删除失败！商品号不存在！");
                return false;
            }
        }
        catch (SQLException e)
        {
            System.err.println("数据库连接失败！");
            e.printStackTrace();
            return false;
        }
    }
    public static boolean changeComodity()
    {
        System.out.println("输入要修改信息的商品的编号：");
        int Cono = getIntNumber();
        while (true)
        {
            System.out.println
                    (
                            "选择修改的项目：\n" +
                                    "1:商品编号：\n" +
                                    "2:商品名称：\n" +
                                    "3:商品价格：\n" +
                                    "4:商品类别：\n" +
                                    "5:品牌名称：\n" +
                                    "6:取消修改");
            int choose = 0;
            while (true)
            {

                choose = getIntNumber();
                if (choose >= 1 && choose <= 5) break;
                else if (choose == 6) return false;
                else
                {
                    System.out.println("请输入1--6内的有效数字！");
                    System.out.println
                            (
                                    "选择修改的项目：\n" +
                                    "1:商品编号：\n" +
                                    "2:商品名称：\n" +
                                    "3:商品价格：\n" +
                                    "4:商品类别：\n" +
                                    "5:品牌名称：\n" +
                                    "6:取消修改");
                    }
                }
            try
            {
                PreparedStatement pS = null;
                int result;
                switch (choose)
                {
                    case 1:
                        System.out.println("输入新的商品编号：");
                        int newCono = getIntNumber();
                        pS = getConnection().prepareStatement("UPDATE Commodity SET Cono = ? WHERE Cono = ?");
                        pS.setInt(1, newCono);
                        pS.setInt(2, Cono);
                        result = pS.executeUpdate();
                        if (result > 0)
                        {
                            System.out.println("修改成功！");
                            return true;
                        }
                        else
                        {
                            System.out.println("修改失败！没有这个商品编号！");
                            return false;
                        }
                    case 2:
                        System.out.println("输入新的商品名称：");
                        String newConame = null;
                        try
                        {
                            newConame = reader.readLine();
                        }
                        catch (IOException e)
                        {
                            System.err.println("输入有误！");
                            e.printStackTrace();
                        }
                        pS = getConnection().prepareStatement("UPDATE Commodity SET Coname = ? WHERE Cono = ?");
                        pS.setString(1, newConame);
                        pS.setInt(2, Cono);
                        result = pS.executeUpdate();
                        if (result > 0)
                        {
                            System.out.println("修改成功！");
                            return true;
                        }
                        else
                        {
                            System.out.println("修改失败！没有这个商品编号！");
                            return false;
                        }
                    case 3:
                        System.out.println("输入新的商品价格：");
                        int newPrice = getIntNumber();
                        pS = getConnection().prepareStatement("UPDATE Commodity SET Price = ? WHERE Cono = ?");
                        pS.setInt(1, newPrice);
                        pS.setInt(2, Cono);
                        result = pS.executeUpdate();
                        if (result > 0)
                        {
                            System.out.println("修改成功！");
                            return true;
                        }
                        else
                        {
                            System.out.println("修改失败！没有这个商品编号！");
                            return false;
                        }
                    case 4:
                        System.out.println("输入新的商品种类：");
                        String newCategory = null;
                        try
                        {
                            newCategory = reader.readLine();
                        }
                        catch (IOException e)
                        {
                            System.err.println("输入有误！");
                            e.printStackTrace();
                        }
                        pS = getConnection().prepareStatement("UPDATE Commodity SET Category = ? WHERE Cono = ?");
                        pS.setString(1, newCategory);
                        pS.setInt(2, Cono);
                        result = pS.executeUpdate();
                        if (result > 0)
                        {
                            System.out.println("修改成功！");
                            return true;
                        }
                        else
                        {
                            System.out.println("修改失败！没有这个商品编号！");
                            return false;
                        }
                    case 5:
                        pS = getConnection().prepareStatement("SELECT * FROM Brand");
                        ResultSet resultSet = pS.executeQuery();
                        System.out.println("请通过以下编号和品牌名确定品牌：");
                        System.out.println("编号\t品牌名称\t");
                        while (resultSet.next())
                        {
                            System.out.println(resultSet.getInt("Bno") + ":\t" + resultSet.getString("Bname"));
                        }
                        System.out.println("请输入编号：");
                        int newBno = 0;
                        while (true)
                        {
                            while (true)
                            {
                                try
                                {
                                    newBno = Integer.parseInt(reader.readLine());
                                    break;
                                }
                                catch (IOException e)
                                {
                                    System.err.println("编号输入有误！");
                                }
                                catch (NumberFormatException e)
                                {
                                    System.err.println("输入数字有误请重试！");
                                }
                            }
                            pS = getConnection().prepareStatement("SELECT count(*) FROM Brand WHERE Bno = ?");
                            pS.setInt(1, newBno);
                            result = pS.executeUpdate();
                            if (result >= 1)
                            {
                                pS = getConnection().prepareStatement("UPDATE Commodity SET Bno = ? WHERE Cono = ?");
                                pS.setInt(1, newBno);
                                pS.setInt(2, Cono);
                                pS.execute();
                                break;
                            }
                            else
                            {
                                System.out.println("修改失败！没有这个品牌编号！请重试：");
                            }
                        }
                }
            }
            catch (SQLException e)
            {
                System.err.println("数据库有误！");
                e.printStackTrace();
            }
            System.out.println("继续更改？（y/n)");
            try
            {
                char c;
                while (true)
                {
                    c = reader.readLine().toLowerCase().charAt(0);
                    if (c == 'y')
                        break;
                    else if (c == 'n')
                        return true;
                    else
                        System.out.println("输入错误请重试！");
                }
            }
            catch (IOException e)
            {
                System.err.println("输入有误");
                e.printStackTrace();
            }
        }
    }

    public static boolean insertComodity()
    {
        System.out.println("请依次输入商品号，商品名，价格，种类，以及品牌号（空格分割）：");
        PreparedStatement pS = null;
        String vales = null;
        String[] row = null;
        try
        {
            while (true)
            {
                vales = reader.readLine();
                row = vales.split(" ");
                if (row.length != 5)
                    System.out.println("格式错误！重新输入：");
                else
                    break;
            }
        }
        catch (IOException e)
        {
            System.err.println("输入异常！");
            e.printStackTrace();
        }
        try
        {
            pS = getConnection().prepareStatement("INSERT INTO Commodity VALUES(?,?,?,?,?)");
            pS.setInt(1,Integer.parseInt(row[0]));
            pS.setString(2,row[1]);
            pS.setString(3,row[2]);
            pS.setString(4,row[3]);
            pS.setInt(5,Integer.parseInt(row[5]));
            boolean b = pS.execute();
            if (b)
            {
                System.out.println("插入成功！");
            }
            else
            {
                System.out.println("插入失败！");
            }
            return b;
        }
        catch (SQLException e)
        {
            System.err.println("数据库连接失败！");
            e.printStackTrace();
        }
        catch (NullPointerException e)
        {
            System.err.println("致命错误，即将退出该选项！");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static boolean printComodity()
    {
        try
        {
            PreparedStatement pS = getConnection().prepareStatement("SELECT * FROM Commodity");

        }
        catch ()
        return true;
    }
}
