import java.sql.PreparedStatement;
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
            PreparedStatement pS = getConnection().prepareStatement("SELECT ? FROM ? WHERE ? = ?");
            pS.setString(1,"Cuname");
            pS.setString(2,"Customer");
            pS.setString(3,"Cuno");
            pS.setInt(4,Cuno);
            Cuname = pS.executeQuery().getString(1);
            pS.setString(1,"Crname");
            pS.setString(2,"Courier");
            pS.setString(3,"Crno");
            pS.setInt(4,Crno);
            Crname = pS.executeQuery().getString(1);
            pS.setString(1,"ECname");
            pS.setString(2,"Express_Company");
            pS.setString(3,"ECno");
            pS.setInt(4,ECno);
            ECname = pS.executeQuery().getString(1);
        }
        catch (SQLException e)
        {
            return "数据库运行有误！";
        }
        return "快递编号：" + Eno + "\n交易编号：" + Trno + "\n客户名：" + Cuname + "\n快递员姓名：" + Crname
                + "\n快递公司：" + ECname + "\n签收地址：" + Address;
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
