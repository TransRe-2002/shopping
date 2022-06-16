import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class Trade extends DBDao
{
    private int Trno;
    private int Cono;
    private int Cuno;
    private int Amount;
    private LocalDate OrderDate;
    private int TotalPrice;

    public int getTrno()
    {
        return Trno;
    }

    public int getCono()
    {
        return Cono;
    }

    public int getCuno()
    {
        return Cuno;
    }

    public int getAmount()
    {
        return Amount;
    }

    public LocalDate getOrderDate()
    {
        return OrderDate;
    }

    public int getTotalPrice()
    {
        return TotalPrice;
    }

    public void setTrno(int Trno)
    {
        this.Trno = Trno;
    }

    public void setCono(int Cono)
    {
        this.Cono = Cono;
    }

    public void setCuno(int Cuno)
    {
        this.Cuno = Cuno;
    }

    public void setAmount(int Amount)
    {
        this.Amount = Amount;
    }

    public void setOrderDate(LocalDate OrderDate)
    {
        this.OrderDate = OrderDate;
    }

    public void setTotalPrice(int TotalPrice)
    {
        this.TotalPrice = TotalPrice;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Trade)
        {
            Trade to = (Trade) o;
            return this.Trno == to.getTrno() && this.Cono == to.getCono()
                    && this.Cuno == to.getCuno() && this.Amount == to.getAmount()
                    && this.OrderDate.equals(to.getOrderDate()) && this.TotalPrice == to.getTotalPrice();
        }
        else
            return false;
    }

    @Override
    public String toString()
    {
        String Coname = null;
        String Cuname = null;
        try
        {
            PreparedStatement pS = getConnection().prepareStatement("SELECT ? FROM ? WHERE ? = ?");
            pS.setString(1,"Coname");
            pS.setString(2,"Commodity");
            pS.setString(3,"Cono");
            pS.setInt(4,Cono);
            Coname = pS.executeQuery().getString(1);
            pS.setString(1,"Cuname");
            pS.setString(2,"Customer");
            pS.setString(3,"Cuno");
            pS.setInt(4,Cuno);
            Cuname = pS.executeQuery().getString(1);
        }
        catch (SQLException e)
        {
            return "商品号或客户号有误！";
        }
        return "交易编号：" + Trno + "\n客户名：" + Cuname + "\n商品名：" + Coname + "\n交易时间："
                + OrderDate.toString() + "\n数量：" + Amount + "\n总价：" + TotalPrice;
    }

    public Trade(int Trno) throws SQLException
    {
        this.Trno = Trno;
        PreparedStatement pS = getConnection().prepareStatement("SELECT * FROM Trade WHERE Trno = ?");
        pS.setInt(1,Trno);
        var result = pS.executeQuery();
        while (result.next())
        {
            Cono = result.getInt("Cono");
            Cuno = result.getInt("Cuno");
            Amount = result.getInt("Amount");
            OrderDate = result.getDate("OrderDate").toLocalDate();
        }
        pS = getConnection().prepareStatement("SELECT Price FROM Commodity WHERE Cono = ?");
        pS.setInt(1,Cono);
        result = pS.executeQuery();
        TotalPrice = result.getInt(1) * Amount;
    }
}
