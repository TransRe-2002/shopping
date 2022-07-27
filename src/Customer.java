import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Customer extends DBDao
{
    private int Cuno;
    private String Cuname;
    private String Tel;

    public void setCuno(int Cuno)
    {
        this.Cuno = Cuno;
    }

    public void setCuname(String Cuname)
    {
        this.Cuname = Cuname;
    }

    public void setTel(String Tel)
    {
        this.Tel = Tel;
    }

    public int getCuno()
    {
        return Cuno;
    }

    public String getCuname()
    {
        return Cuname;
    }

    public String getTel()
    {
        return Tel;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Customer)
        {
            Customer co = (Customer) o;
            return this.Cuno == co.getCuno() && this.getCuname().equals(co.getCuname())
                    && this.getTel().equals(co.getTel());
        }
        else
            return false;
    }

    @Override
    public String toString()
    {
        return "客户信息：\n客户编号：" + Cuno + "\n客户名：" + Cuname  + "\n联系电话：" + Tel + "\n";
    }

    public Customer(int Cuno) throws SQLException
    {
        this.Cuno = Cuno;
        PreparedStatement pS = getConnection().prepareStatement("SELECT * FROM Customer WHERE Cuno = ?");
        pS.setInt(1,Cuno);
        var result = pS.executeQuery();
        while (result.next())
        {
            this.Cuname = result.getString("Cuname");
            this.Tel = result.getString("Tel");
        }
    }
}
