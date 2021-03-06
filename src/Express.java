import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Express)
        {
            Express eo = (Express) o;
            return this.Eno == eo.getEno() && this.Trno == eo.getTrno()
                    && this.Cuno == eo.getCuno() && this.Crno == eo.getCrno()
                    && this.ECno == eo.getECno() && this.Address.equals(eo.getAddress());
        }
        else
            return false;
    }
    @Override
    public String toString()
    {
        String Cuname = null;
        String Crname = null;
        String ECname = null;
        try
        {
            PreparedStatement pS = getConnection().prepareStatement("SELECT Cuname FROM Customer WHERE Cuno = ?");
            pS.setInt(1,Cuno);
            ResultSet rS = pS.executeQuery();
            while (rS.next() && rS.getString(1) != null)
            {
                Cuname = rS.getString(1);
            }
            pS = getConnection().prepareStatement("SELECT Crname FROM Courier WHERE Crno = ?");
            pS.setInt(1,Crno);
            rS = pS.executeQuery();
            while (rS.next() && rS.getString(1) != null)
            {
                Crname = rS.getString(1);
            }
            pS = getConnection().prepareStatement("SELECT ECname FROM Express_Company WHERE ECno = ?");
            pS.setInt(1,ECno);
            rS = pS.executeQuery();
            while (rS.next() && rS.getString(1) != null)
            {
                ECname = rS.getString(1);
            }
        }
        catch (SQLException e)
        {
            return "????????????????????????";
        }
        return "???????????????" + Eno + "\n???????????????" + Trno + "\n????????????" + Cuname + "\n??????????????????" + Crname
                + "\n???????????????" + ECname + "\n???????????????" + Address;
    }

    public Express(int Eno) throws SQLException
    {
        this.Eno = Eno;
        PreparedStatement pS = getConnection().prepareStatement("SELECT * FROM Express WHERE Eno = ?");
        pS.setInt(1,Eno);
        var result = pS.executeQuery();
        while (result.next())
        {
            Trno = result.getInt("Trno");
            Cuno = result.getInt("Cuno");
            Crno = result.getInt("Crno");
            ECno = result.getInt("ECno");
            Address = result.getString("Address");
        }
    }
}
