import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            PreparedStatement pS = getConnection().prepareStatement("SELECT Coname FROM Commodity WHERE Cono = ?");
            pS.setInt(1,Cono);
            ResultSet rs = pS.executeQuery();
            while (rs.next() && rs.getString(1) != null)
            {
                Coname = rs.getString(1);
            }
            pS = getConnection().prepareStatement("SELECT Cuname FROM Customer WHERE Cuno = ?");
            pS.setInt(1,Cuno);
            rs = pS.executeQuery();
            while (rs.next() && rs.getString(1) != null)
            {
                Cuname = rs.getString(1);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return "????????????????????????";
        }
        return "???????????????" + Trno + "\n????????????" + Cuname + "\n????????????" + Coname + "\n???????????????"
                + OrderDate.toString() + "\n?????????" + Amount + "\n?????????" + TotalPrice;
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
            TotalPrice = result.getInt("TotalPrice");
        }
    }
}
