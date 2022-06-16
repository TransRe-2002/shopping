import java.time.LocalDate;

public class Trade
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


}
