public class Express extends DBDao
{
    private int Eno;
    private int Trno;
    private int Cuno;
    private int Crno;
    private int ECno;
    private String Address;

    public int getEno()
    {
        return Eno;
    }

    public int getTrno()
    {
        return Trno;
    }

    public int getCuno()
    {
        return Cuno;
    }

    public int getCrno()
    {
        return Crno;
    }

    public int getECno()
    {
        return ECno;
    }

    public String getAddress()
    {
        return Address;
    }

    public void setEno(int Eno)
    {
        this.Eno = Eno;
    }

    public void setTrno(int Trno)
    {
        this.Trno = Trno;
    }

    public void setCuno(int Cuno)
    {
        this.Cuno = Cuno;
    }

    public void setCrno(int Crno)
    {
        this.Crno = Crno;
    }

    public void setECno(int ECno)
    {
        this.ECno = ECno;
    }

    public void setAddress(String Address)
    {
        this.Address = Address;
    }


}
