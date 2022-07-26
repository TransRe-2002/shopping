import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ExpressDao extends DBDao
{
    private static int inputEno()
    {
        int Eno = 0;
        try
        {
            Eno = getIntNumber();
            var pS = getConnection().prepareStatement("SELECT COUNT(*) FROM Customer WHERE Cuno = ? LIMIT 1");
            pS.setInt(1,Eno);
            var result = pS.executeQuery();
            while (result.next())
            {
                System.out.println("快递编号不存在，请重新输入！");
                Eno = getIntNumber();
                pS.setInt(1,Eno);
                result = pS.executeQuery();
            }
        }
        catch (SQLException e)
        {
            System.err.println("数据库连接有误！");
            e.printStackTrace();
        }
        return Eno;
    }

    public static boolean searchExpress()
    {
        System.out.println("输入要查询的快递编号：");
        int Eno = inputEno();
        PreparedStatement pS = null;
        Express express = null;
        try
        {
            express = new Express(Eno);
        }
        catch (SQLException e)
        {
            System.err.println("数据库连接有误！");
            e.printStackTrace();
        }
        if (express != null)
        {
            System.out.println(express);
            return true;
        }
        else
        {
            System.out.println("快递编号不正确，没有该快递！");
            return false;
        }
    }

    public static boolean deleteExpress()
    {
        System.out.println("输入要删除的快递的编号：");
        int Eno = inputEno();
        try
        {
            PreparedStatement pS = getConnection().prepareStatement("DELETE FROM Express WHERE Eno = ?");
            pS.setInt(1,Eno);
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

    public static boolean changeExpress()
    {
        System.out.println("输入要修改信息的快递的编号：");
        int Eno = inputEno();
        while (true)
        {
             System.out.println
                     (
                             "选择修改的项目：\n" +
                                     "1:快递编号：\n" +
                                     "2:订单编号：\n" +
                                     "3:客户编号：\n" +
                                     "4:快递员编号：\n" +
                                     "5:地址编号：\n" +
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
                                             "1:快递编号：\n" +
                                             "2:订单编号：\n" +
                                             "3:客户编号：\n" +
                                             "4:快递员编号：\n" +
                                             "5:地址编号：\n" +
                                             "6:取消修改"
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
                        System.out.println("输入新的快递编号：");
                        int newEno = getIntNumber();
                        pS = getConnection().prepareStatement("UPDATE Express SET Eno = ? WHERE Eno = ?");
                        pS.setInt(1,newEno);
                        pS.setInt(2,Eno);
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
                        System.out.println("输入新的交易编号：");
                        int newTrno = getIntNumber();
                        pS = getConnection().prepareStatement("UPDATE Express SET Trno = ? WHERE Eno = ?");
                        pS.setInt(1,newTrno);
                        pS.setInt(2,Eno);
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
                        System.out.println("输入新的客户编号：");
                        int newCuno = getIntNumber();
                        pS = getConnection().prepareStatement("UPDATE Express SET Cuno = ? WHERE Eno = ?");
                        pS.setInt(1,newCuno);
                        pS.setInt(2,Eno);
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
                    case 4:
                        System.out.println("输入新的快递员编号：");
                        int newCrno = getIntNumber();
                        pS = getConnection().prepareStatement("UPDATE Express SET Crno = ?,ECno = (SELECT ECno FROM Courier WHERE Courier.Crno = ?) WHERE Cuno = ?");
                        pS.setInt(1,newCrno);
                        pS.setInt(2,newCrno);
                        pS.setInt(2,Eno);
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
                    case 5:
                        System.out.println("输入新的发货地址：");
                        String newAddress = reader.readLine();
                        pS = getConnection().prepareStatement("UPDATE Express SET Address = ? WHERE Eno = ?");
                        pS.setString(1,newAddress);
                        pS.setInt(2,Eno);
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
    public static boolean insertExpress()
    {
        System.out.println("依次输入快递编号，订单编号，客户编号，快递员编号和地址（空格分割）：");
        PreparedStatement pS = null;
        String value = null;
        String[] row = null;
        try
        {
            while (true)
            {
                value = reader.readLine();
                row = value.split(" ");
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
        if (row != null && row.length == 5)
        {
            try
            {
                pS = getConnection().prepareStatement("INSERT INTO Express VALUES(?,?,?,?,(SELECT ECno FROM Courier WHERE Courier.Crno = ?),?)");
                pS.setInt(1,Integer.parseInt(row[0]));
                pS.setInt(2,Integer.parseInt(row[1]));
                pS.setInt(3,Integer.parseInt(row[2]));
                pS.setInt(4,Integer.parseInt(row[3]));
                pS.setInt(5,Integer.parseInt(row[3]));
                pS.setString(6,row[4]);
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
            return true;
        }
        else
        {
            System.out.println("出现错误，即将退出该选项！");
            return false;
        }
    }

    public static boolean printExpress()
    {
        try
        {
            PreparedStatement pS = getConnection().prepareStatement("SELECT * FROM Express");
            ResultSet rS = pS.executeQuery();
            ResultSetMetaData rSMD = rS.getMetaData();
            System.out.println
                    (
                    rSMD.getColumnName(1) + "\t\t" +
                    rSMD.getColumnName(2) + "\t\t" +
                    rSMD.getColumnName(3) + "\t\t" +
                    rSMD.getColumnName(4) + "\t\t" +
                    rSMD.getColumnName(5) + "\t\t" +
                    rSMD.getColumnName(6)
                    );
            while (rS.next())
            {
                System.out.println
                        (
                                rS.getInt(1) + "\t\t" +
                                rS.getInt(2) + "\t\t" +
                                rS.getInt(3) + "\t\t" +
                                rS.getInt(4) + "\t\t" +
                                rS.getInt(5) + "\t\t" +
                                String.format("%-10s",rS.getString(6) + "...")
                        );
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
