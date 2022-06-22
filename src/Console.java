import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Console extends DBDao implements Runnable
{
    private void login() throws IOException,SQLException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
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
            if (result.next())
            {
                System.out.println("欢迎进入购物管理系统！");
                return;
            }
            else
            {
                System.out.println("用户名或密码有误！重试(r),退出(q):");
                while(true)
                {
                    char choose = reader.readLine().toLowerCase().charAt(0);
                    switch (choose)
                    {
                        case 'r':
                            break;
                        case 'q':
                            System.out.println("Bye！");
                            return;
                        default:
                            System.out.println("请输入正确选项!");
                    }
                }
            }
        }
    }
    @Override
    public void run()
    {
        try
        {
            login();
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
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        char choose;
        System.out.println
                (
                        "-----选择执行的任务-----\n" +
                                "1:查询购物单号/用户信息/商品信息/快递单号，\n" +
                                "2:修改购物单号/用户信息/商品信息/快递单号，\n" +
                                "3:删除购物单号/用户信息/商品信息/快递单号，\n" +
                                "4:添加购物单号/用户信息/商品信息/快递单号，\n" +
                                "5:输出购物单号/用户信息/商品信息/快递单号信息，\n" +
                                "6:自由执行SQL语句，\n"+
                                "q:退出\n"
                );
        System.out.println("Bye");
    }
}