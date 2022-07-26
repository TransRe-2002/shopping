import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class TradeDao extends DBDao
{
    private static int inputTrno()
    {
        int Trno = 0;
        try
        {
            Trno = getIntNumber();
            var pS = getConnection().prepareStatement("SELECT COUNT(*) FROM Trade WHERE Trno = ?");
            pS.setInt(1,Trno);
            var result = pS.executeUpdate();
            while (result < 1)
            {
                System.out.println("订单编号不存在，请重新输入！");
                Trno = getIntNumber();
                pS.setInt(1,Trno);
                result = pS.executeUpdate();
            }
        }
        catch (SQLException e)
        {
            System.err.println("数据库连接有误！");
            e.printStackTrace();
        }
        return Trno;
    }

    public static boolean searchTrade()
    {
        System.out.println("输入要查询的客户的编号：");
        int Trno = inputTrno();
        PreparedStatement pS = null;
        Trade trade = null;
        try
        {
            trade = new Trade(Trno);
        }
        catch (SQLException e)
        {
            System.err.println("数据库连接有误！");
            e.printStackTrace();
        }
        if (trade != null)
        {
            System.out.println(trade);
            return true;
        }
        else
        {
            System.out.println("客户编号不正确，没有该客户！");
            return false;
        }
    }

    public static boolean deleteTrade()
    {
        System.out.println("输入要删除的订单的编号：");
        int Trno = inputTrno();
        try
        {
            PreparedStatement pS = getConnection().prepareStatement("DELETE FROM Trade WHERE Trno = ?");
            pS.setInt(1,Trno);
            int b = pS.executeUpdate();
            if (b > 0)
            {
                System.out.println("删除成功！");
                return true;
            }
            else
            {
                System.out.println("删除失败！订单号不存在！");
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

    public static boolean changeTrade()
    {
        System.out.println("输入要修改信息的订单的编号：");
        int Trno = inputTrno();
        while (true)
        {
            System.out.println
                    (
                            "选择修改的项目：\n" +
                                    "1:订单编号\n" +
                                    "2:商品编号\n" +
                                    "3:客户编号\n" +
                                    "4:数量\n" +
                                    "5:订单日期\n" +
                                    "6:总价\n" +
                                    "7:取消"
                    );
            int choose = 0;
            while (true)
            {
                choose = getIntNumber();
                if (choose >= 1 && choose <= 6) break;
                else if (choose == 7) return false;
                else
                {
                    System.out.println("请输入1--7内的有效数字！");
                    System.out.println
                            (
                                    "选择修改的项目\n" +
                                            "1:订单编号\n" +
                                            "2:商品编号\n" +
                                            "3:客户编号\n" +
                                            "4:数量\n" +
                                            "5:订单日期\n" +
                                            "6:总价\n" +
                                            "7:取消修改\n"
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
                        System.out.println("输入新的订单编号：");
                        int newTrno = getIntNumber();
                        pS = getConnection().prepareStatement("UPDATE Trade SET Trno = ? WHERE Trno = ?");
                        pS.setInt(1,newTrno);
                        pS.setInt(2,Trno);
                        result = pS.executeUpdate();
                        if (result > 0)
                        {
                            System.out.println("修改成功！");
                        }
                        else
                        {
                            System.out.println("修改失败！没有这个订单编号！");
                        }
                        break;
                    case 2:
                        System.out.println("输入新的商品编号：");
                        int newCono = getIntNumber();
                        pS = getConnection().prepareStatement("UPDATE Trade SET Cono = ? WHERE Trno = ?");
                        pS.setInt(1, newCono);
                        pS.setInt(2, Trno);
                        result = pS.executeUpdate();
                        if (result > 0) {
                            System.out.println("修改成功！");
                        } else {
                            System.out.println("修改失败！没有这个商品编号！");
                        }
                        break;
                    case 3:
                        System.out.println("输入新的客户编号：");
                        int newCuno = getIntNumber();
                        pS = getConnection().prepareStatement("UPDATE Trade SET Cuno = ? WHERE Trno = ?");
                        pS.setInt(1,newCuno);
                        pS.setInt(2,Trno);
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
                        System.out.println("输入新的数量：");
                        int newAmount = getIntNumber();
                        pS = getConnection().prepareStatement("UPDATE Trade SET Amount = ? WHERE Trno = ?");
                        pS.setInt(1,newAmount);
                        pS.setInt(2,Trno);
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
                        System.out.println("输入新的订单日期（年、月、日顺序，使用空格分隔）：");
                        String newdate = reader.readLine();
                        String[] datenum = newdate.split(" ",3);
                        LocalDate newOrderDate = LocalDate.of(Integer.parseInt(datenum[0]),Integer.parseInt(datenum[1]),Integer.parseInt(datenum[2]));
                        pS = getConnection().prepareStatement("UPDATE Trade SET OrderDate = ? WHERE Trno = ?");
                        pS.setDate(1, Date.valueOf(newOrderDate));
                        pS.setInt(2, Trno);
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
                    case 6:
                        System.out.println("输入新的价格：");
                        int newPrice = getIntNumber();
                        pS = getConnection().prepareStatement("UPDATE Trade SET TotalPrice = ? WHERE Trno = ?");
                        pS.setInt(1,newPrice);
                        pS.setInt(2,Trno);
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

    public static boolean insertTrade()
    {
        System.out.println("依次输入订单编号，商品编号，客户编号，数量，订单日期，总价：（空格分割，订单日期用年月日顺序，\"-\"分割）");
        PreparedStatement pS = null;
        String value = null;
        String[] row = null;
        try
        {
            while (true)
            {
                value = reader.readLine();
                row = value.split(" ");
                if (row.length != 6)
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
                pS = getConnection().prepareStatement("INSERT INTO Customer VALUES(?,?,?,?,?,?)");
                pS.setInt(1,Integer.parseInt(row[0]));
                pS.setInt(2,Integer.parseInt(row[1]));
                pS.setInt(3,Integer.parseInt(row[2]));
                pS.setInt(4,Integer.parseInt(row[3]));
                pS.setDate(5,Date.valueOf(row[4]));
                pS.setInt(6,Integer.parseInt(row[5]));
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
}
