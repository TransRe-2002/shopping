import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class CommodityDao extends DBDao
{
    private static int inputCono()
    {
        int Cono = 0;
        try
        {
            Cono = getIntNumber();
            var pS = getConnection().prepareStatement("SELECT COUNT(*) FROM Commodity WHERE Cono = ? LIMIT 1");
            pS.setInt(1, Cono);
            var result = pS.executeQuery();
            while (result.next() && result.getInt(1) != 1)
            {
                System.out.println("商品编号不存在，请重新输入！");
                Cono = getIntNumber();
                pS.setInt(1,Cono);
                result = pS.executeQuery();
            }
        }
        catch (SQLException e)
        {
            System.err.println("数据库连接有误！");
            e.printStackTrace();
        }
        return Cono;
    }
    public static boolean searchCommodity()
    {
        System.out.println("输入要查询的商品的编号：");
        int Cono = inputCono();
        Commodity comm = null;
        try
        {
            comm = new Commodity(Cono);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
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
    public static boolean deleteCommodity()
    {
        System.out.println("输入要删除的商品的编号：");
        int Cono = inputCono();
        try
        {
            PreparedStatement pS = getConnection().prepareStatement("DELETE FROM Commodity WHERE Cono = ?");
            pS.setInt(1,Cono);
            int b = pS.executeUpdate();
            if (b > 0)
            {
                System.out.println("删除成功！");
            }
            else
            {
                System.out.println("删除失败！");
                return false;
            }
        }
        catch (SQLException e)
        {
            System.err.println("数据库连接失败！");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static boolean changeComodity()
    {
        System.out.println("输入要修改信息的商品的编号：");
        int Cono = inputCono();
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
                                    "6:取消修改"
                    );
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
                switch (choose) {
                    case 1:
                        System.out.println("输入新的商品编号：");
                        int newCono = getIntNumber();
                        pS = getConnection().prepareStatement("UPDATE Commodity SET Cono = ? WHERE Cono = ?");
                        pS.setInt(1, newCono);
                        pS.setInt(2, Cono);
                        result = pS.executeUpdate();
                        if (result > 0) {
                            System.out.println("修改成功！");
                        } else {
                            System.out.println("修改失败！没有这个商品编号！");
                        }
                        break;
                    case 2:
                        System.out.println("输入新的商品名称：");
                        String newConame = null;
                        try {
                            newConame = reader.readLine();
                        } catch (IOException e) {
                            System.err.println("输入有误！");
                            e.printStackTrace();
                        }
                        pS = getConnection().prepareStatement("UPDATE Commodity SET Coname = ? WHERE Cono = ?");
                        pS.setString(1, newConame);
                        pS.setInt(2, Cono);
                        result = pS.executeUpdate();
                        if (result > 0) {
                            System.out.println("修改成功！");
                        } else {
                            System.out.println("修改失败！没有这个商品编号！");
                        }
                        break;
                    case 3:
                        System.out.println("输入新的商品价格：");
                        int newPrice = getIntNumber();
                        pS = getConnection().prepareStatement("UPDATE Commodity SET Price = ? WHERE Cono = ?");
                        pS.setInt(1, newPrice);
                        pS.setInt(2, Cono);
                        result = pS.executeUpdate();
                        if (result > 0) {
                            System.out.println("修改成功！");
                        } else {
                            System.out.println("修改失败！没有这个商品编号！");
                        }
                        break;
                    case 4:
                        System.out.println("输入新的商品种类：");
                        String newCategory = null;
                        try {
                            newCategory = reader.readLine();
                        } catch (IOException e) {
                            System.err.println("输入有误！");
                            e.printStackTrace();
                        }
                        pS = getConnection().prepareStatement("UPDATE Commodity SET Category = ? WHERE Cono = ?");
                        pS.setString(1, newCategory);
                        pS.setInt(2, Cono);
                        result = pS.executeUpdate();
                        if (result > 0) {
                            System.out.println("修改成功！");
                            return true;
                        } else {
                            System.out.println("修改失败！没有这个商品编号！");
                            return false;
                        }
                    case 5:
                        pS = getConnection().prepareStatement("SELECT * FROM Brand");
                        ResultSet resultSet = pS.executeQuery();
                        System.out.println("请通过以下编号和品牌名确定品牌：");
                        System.out.println("编号\t品牌名称\t");
                        while (resultSet.next()) {
                            System.out.println(resultSet.getInt("Bno") + ":\t" + resultSet.getString("Bname"));
                        }
                        System.out.println("请输入编号：");
                        int newBno = 0;
                        while (true) {
                            newBno = getIntNumber();
                            pS = getConnection().prepareStatement("SELECT count(*) FROM Brand WHERE Bno = ?");
                            pS.setInt(1, newBno);
                            result = pS.executeUpdate();
                            if (result >= 1) {
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
                System.out.println("继续更改？（y/n)");
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
            catch (SQLException e)
            {
                System.err.println("数据库有误！");
                e.printStackTrace();
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
        if (row != null)
        {
            try
            {
                pS = getConnection().prepareStatement("INSERT INTO Commodity VALUES(?,?,?,?,?)");
                pS.setInt(1, Integer.parseInt(row[0]));
                pS.setString(2, row[1]);
                pS.setInt(3, Integer.parseInt(row[2]));
                pS.setString(4, row[3]);
                pS.setInt(5, Integer.parseInt(row[4]));
                boolean b = pS.execute();
                if (!b)
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
            return true;
        }
        else
        {
            System.out.println("出现错误，即将退出该选项！");
            return false;
        }
    }
    public static boolean printCommodity()
    {
        try
        {
            PreparedStatement pS = getConnection().prepareStatement("SELECT * FROM Commodity");
            ResultSet rS = pS.executeQuery();
            ResultSetMetaData rSMD = rS.getMetaData();
            System.out.println(rSMD.getColumnName(1) + "\t\t" + rSMD.getColumnName(2) + "\t\t" + rSMD.getColumnName(3) + "\t\t" +
                    rSMD.getColumnName(4) + "\t\t" + rSMD.getColumnName(5));
            while (rS.next())
            {
                System.out.println
                        (
                                rS.getInt(1) + "\t\t" +
                                        String.format("%-10s",rS.getString(2)) + "...\t" +
                        rS.getInt(3) + "\t\t" + rS.getString(4) + "\t\t" + rS.getInt(5));
            }
        }
        catch (SQLException e)
        {
            System.err.println("数据库连接失败");
            e.printStackTrace();
        }
        return true;
    }
}