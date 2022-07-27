import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class CustomerDao extends DBDao
{
    private static int inputCuno()
    {
        int Cuno = 0;
        try
        {
            Cuno = getIntNumber();
            var pS = getConnection().prepareStatement("SELECT COUNT(*) FROM Customer WHERE Cuno = ? LIMIT 1");
            pS.setInt(1, Cuno);
            var result = pS.executeQuery();
            while (result.next() && result.getInt(1) != 1)
            {
                System.out.println("客户编号不存在，请重新输入！");
                Cuno = getIntNumber();
                pS.setInt(1,Cuno);
                result = pS.executeQuery();
            }
        }
        catch (SQLException e)
        {
            System.err.println("数据库连接有误！");
            e.printStackTrace();
        }
        return Cuno;
    }

    public static boolean searchCustomer()
    {
        System.out.println("输入要查询的客户的编号：");
        int Cuno = inputCuno();
        Customer cust = null;
        try
        {
            cust = new Customer(Cuno);
        }
        catch (SQLException e)
        {
            System.err.println("数据库连接有误！");
            e.printStackTrace();
        }
        if (cust != null)
        {
            System.out.println(cust);
            return true;
        }
        else
        {
            System.out.println("客户编号不正确，没有该客户！");
            return false;
        }
    }

    public static boolean deleteCustomer()
    {
        System.out.println("输入要删除的客户的编号：");
        int Cuno = inputCuno();
        try
        {
            PreparedStatement pS = getConnection().prepareStatement("DELETE FROM Customer WHERE Cuno = ?");
            pS.setInt(1,Cuno);
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

    public static boolean changeCustomer()
    {
        System.out.println("输入要修改信息的客户的编号：");
        int Cuno = inputCuno();
        while (true) {
            System.out.println
                    (
                            "选择修改的项目：\n" +
                                    "1:客户编号：\n" +
                                    "2:客户名称：\n" +
                                    "3:客户电话：\n" +
                                    "4:取消修改"
                    );
            int choose = 0;
            while (true)
            {
                choose = getIntNumber();
                if (choose >= 1 && choose <= 3) break;
                else if (choose == 4) return false;
                else
                {
                    System.out.println("请输入1--4内的有效数字！");
                    System.out.println
                            (
                                    "选择修改的项目\n" +
                                            "1:客户编号\n" +
                                            "2:客户名称\n" +
                                            "3:客户电话\n" +
                                            "4:取消修改"
                            );
                }
            }
            try
            {
                PreparedStatement pS = null;
                int result;
                switch (choose)
                {
                    case 1:
                        System.out.println("输入新的客户编号：");
                        int newCuno = getIntNumber();
                        pS = getConnection().prepareStatement("UPDATE Customer SET Cuno = ? WHERE Cuno = ?");
                        pS.setInt(1,newCuno);
                        pS.setInt(2,Cuno);
                        result = pS.executeUpdate();
                        if (result > 0)
                        {
                            System.out.println("修改成功！");
                        }
                        else
                        {
                            System.out.println("修改失败！没有这个商品编号！");
                        }
                        break;
                    case 2:
                        System.out.println("输入新的客户名称：");
                        String newCuname = null;
                        newCuname = reader.readLine();
                        pS = getConnection().prepareStatement("UPDATE Customer SET Cuname = ? WHERE Cuno = ?");
                        pS.setString(1,newCuname);
                        pS.setInt(2,Cuno);
                        result = pS.executeUpdate();
                        if (result > 0)
                        {
                            System.out.println("修改成功！");
                        }
                        else
                        {
                            System.out.println("修改失败！没有这个客户编号！");
                        }
                        break;
                    case 3:
                        System.out.println("输入新的客户电话：");
                        String newTel = reader.readLine();
                        pS = getConnection().prepareStatement("UPDATE Customer SET Tel = ? WHERE Cuno = ?");
                        pS.setString(1,newTel);
                        pS.setInt(2,Cuno);
                        result = pS.executeUpdate();
                        if (result > 0)
                        {
                            System.out.println("修改成功！");
                        }
                        else
                        {
                            System.out.println("修改失败！没有这个客户编号！");
                        }
                        break;
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
            catch (IOException e)
            {
                System.err.println("数据输入有误！");
                e.printStackTrace();
            }
            catch (SQLException e)
            {
                System.err.println("数据库有误！");
                e.printStackTrace();
            }
        }
    }
    public static boolean insertCustomer()
    {
        System.out.println("依次输入用户编号，姓名，电话号码（空格分割）：");
        PreparedStatement pS = null;
        String value = null;
        String[] row = null;
        try
        {
            while (true)
            {
                value = reader.readLine();
                row = value.split(" ");
                if (row.length != 3)
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
                pS = getConnection().prepareStatement("INSERT INTO Customer VALUES(?,?,?)");
                pS.setInt(1,Integer.parseInt(row[0]));
                pS.setString(2,row[1]);
                pS.setString(3,row[2]);
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

    public static boolean printCustomer()
    {
        try
        {
            PreparedStatement pS = getConnection().prepareStatement("SELECT * FROM Customer");
            ResultSet rS = pS.executeQuery();
            ResultSetMetaData rSMD = rS.getMetaData();
            System.out.println(rSMD.getColumnName(1) + "\t\t" + rSMD.getColumnName(2) + "\t\t" + rSMD.getColumnName(3));
            while (rS.next())
            {
                System.out.println(rS.getInt(1) + "\t\t" + String.format("%-10s",rS.getString(2)) + "\t\t" + rS.getInt(3));
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