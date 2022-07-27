import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static java.lang.System.exit;

public class Console extends DBDao implements Runnable
{
    private void login() throws IOException,SQLException
    {
        while (true)
        {
            System.out.println("请输入用户名：");
            String admin = reader.readLine();
            System.out.println("请输入密码：");
            String passwd = reader.readLine();
            PreparedStatement pS = getConnection().prepareStatement("SELECT count(*) FROM Admin WHERE USER_NAME = ? AND PASSWD = ? LIMIT 1");
            pS.setString(1, admin);
            pS.setString(2, passwd);
            var result = pS.executeQuery();
            if (result.next() && result.getInt(1) == 1)
            {
                System.out.println("欢迎进入购物管理系统！");
                return;
            }
            else
            {
                System.out.println("用户名或密码有误！重试(r)，退出(q):");
                char choose = reader.readLine().toLowerCase().charAt(0);
                while(true)
                {
                    if (choose == 'r')
                        break;
                    else if (choose == 'q')
                    {
                        System.out.println("Bye！");
                        exit(0);
                    }
                    else
                    {
                        System.out.println("请输入正确选项!");
                        System.out.println("用户名或密码有误！重试(r)，退出(q):");
                    }
                    choose = reader.readLine().toLowerCase().charAt(0);
                }
            }
        }
    }

    private int chooseTable() throws IOException
    {
        System.out.println
                (
                        "输入要修改的信息：\n" +
                                "1.购物信息\n" +
                                "2.用户信息\n" +
                                "3.商品信息\n" +
                                "4.快递信息\n" +
                                "5.取消修改"
                );
        int choose = Integer.parseInt(reader.readLine());
        while (choose > 5 || choose < 1 )
        {
            System.out.println("请重新输入！");
            System.out.println
                    (
                            "输入要查询或修改的信息：\n" +
                                    "1.购物信息\n" +
                                    "2.用户信息\n" +
                                    "3.商品信息\n" +
                                    "4.快递信息\n" +
                                    "5.取消修改"
                    );
            choose = Integer.parseInt(reader.readLine());
        }
        return choose;
    }
    @Override
    public void run()
    {
        try
        {
            login();
            System.out.println
                    (
                            "-----选择执行的任务-----\n" +
                                    "1:查询购物单号/用户信息/商品信息/快递单号，\n" +
                                    "2:修改购物单号/用户信息/商品信息/快递单号，\n" +
                                    "3:删除购物单号/用户信息/商品信息/快递单号，\n" +
                                    "4:添加购物单号/用户信息/商品信息/快递单号，\n" +
                                    "5:输出购物单号/用户信息/商品信息/快递单号信息，\n" +
                                    "6:退出"
                    );
            int choose = Integer.parseInt(reader.readLine());
            while (true)
            {
                if (choose == 6)
                    break;
                else if (choose > 6 || choose < 1)
                {
                    System.out.println("请重新输入！");
                    System.out.println
                            (
                                    "-----选择执行的任务-----\n" +
                                            "1:查询购物单号/用户信息/商品信息/快递单号，\n" +
                                            "2:修改购物单号/用户信息/商品信息/快递单号，\n" +
                                            "3:删除购物单号/用户信息/商品信息/快递单号，\n" +
                                            "4:添加购物单号/用户信息/商品信息/快递单号，\n" +
                                            "5:输出购物单号/用户信息/商品信息/快递单号信息，\n" +
                                            "6:退出"
                            );
                    choose = Integer.parseInt(reader.readLine());
                }
                else {
                    int choose2 = chooseTable();
                    if (choose == 1 && choose2 == 1)
                        TradeDao.searchTrade();
                    else if (choose == 2 && choose2 == 1)
                        TradeDao.changeTrade();
                    else if (choose == 3 && choose2 == 1)
                        TradeDao.deleteTrade();
                    else if (choose == 4 && choose2 == 1)
                        TradeDao.insertTrade();
                    else if (choose == 5 && choose2 == 1)
                        TradeDao.printTrade();
                    else if (choose == 1 && choose2 == 2)
                        CustomerDao.searchCustomer();
                    else if (choose == 2 && choose2 == 2)
                        CustomerDao.changeCustomer();
                    else if (choose == 3 && choose2 == 2)
                        CustomerDao.deleteCustomer();
                    else if (choose == 4 && choose2 == 2)
                        CustomerDao.insertCustomer();
                    else if (choose == 5 && choose2 == 2)
                        CustomerDao.printCustomer();
                    else if (choose == 1 && choose2 == 3)
                        CommodityDao.searchCommodity();
                    else if (choose == 2 && choose2 == 3)
                        CommodityDao.changeComodity();
                    else if (choose == 3 && choose2 == 3)
                        CommodityDao.deleteCommodity();
                    else if (choose == 4 && choose2 == 3)
                        CommodityDao.insertComodity();
                    else if (choose == 5 && choose2 == 3)
                        CommodityDao.printCommodity();
                    else if (choose == 1 && choose2 == 4)
                        ExpressDao.searchExpress();
                    else if (choose == 2 && choose2 == 4)
                        ExpressDao.changeExpress();
                    else if (choose == 3 && choose2 == 4)
                        ExpressDao.deleteExpress();
                    else if (choose == 4 && choose2 == 4)
                        ExpressDao.insertExpress();
                    else
                        ExpressDao.printExpress();
                    System.out.println("继续使用？(y/n)");
                    char c;
                    while (true)
                    {
                        c = reader.readLine().toLowerCase().charAt(0);
                        if (c == 'y')
                            break;
                        else if (c == 'n')
                        {
                            System.out.println("Bye");
                            return;
                        }
                        else
                            System.out.println("输入错误请重试！");
                    }
                    System.out.println
                            (
                                    "-----选择执行的任务-----\n" +
                                            "1:查询购物单号/用户信息/商品信息/快递单号，\n" +
                                            "2:修改购物单号/用户信息/商品信息/快递单号，\n" +
                                            "3:删除购物单号/用户信息/商品信息/快递单号，\n" +
                                            "4:添加购物单号/用户信息/商品信息/快递单号，\n" +
                                            "5:输出购物单号/用户信息/商品信息/快递单号信息，\n" +
                                            "6:退出"
                            );
                    choose = Integer.parseInt(reader.readLine());
                }
            }
            System.out.println("Bye");
        }
        catch (IOException e1)
        {
            System.err.println("输入有误！");
            e1.printStackTrace();
        }
        catch (SQLException e2)
        {
            System.err.println("数据库连接有误");
            e2.printStackTrace();
        }
    }
}