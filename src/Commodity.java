import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Commodity extends DBDao
{
    private int Cono;
    private String Coname;
    private int Price;
    private String Category;
    private int Bno;

    public int getCono()
    {
        return Cono;
    }

    public String getConame()
    {
        return Coname;
    }

    public int getPrice()
    {
        return Price;
    }

    public String getCategory()
    {
        return Category;
    }

    public int getBno()
    {
        return Bno;
    }

    public void setCono(int Cono)
    {
        this.Cono = Cono;
    }

    public void setConame(String Coname)
    {
        this.Coname = Coname;
    }

    public void setPrice(int Price)
    {
        this.Price = Price;
    }

    public void setCategory(String Category)
    {
        this.Category = Category;
    }

    public void setBno(int Bno)
    {
        this.Bno = Bno;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Commodity)
        {
            var co = (Commodity) o;
            return Cono == co.getCono() && Coname.equals(co.getConame())
                    && Price == co.getPrice() && Category.equals(co.getCategory())
                    && Bno == co.getBno();
        }
        else
            return false;
    }

    @Override
    public String toString()
    {
        String brand = null;
        try
        {
            PreparedStatement pS = getConnection().prepareStatement("SELECT Bname FROM Brand WHERE Bno = ?");
            pS.setInt(1,Cono);
            var r = pS.executeQuery();
            brand = r.getString(1);
        }
        catch (SQLException e)
        {
            return "商品品牌号异常，请检查";
        }
        return "商品信息：\n商品编号：" + Cono + "\n商品名称：" + Coname +
                "\n价格：" + Price + "\n商品种类：" + Category +
                "\n商品名：" + brand + "\n";
    }


    public Commodity(int Cono) throws SQLException
    {
        this.Cono = Cono;
        PreparedStatement s = getConnection().prepareStatement("SELECT * FROM Commodity WHERE Bno = ?");
        s.setInt(1, Cono);
        var result = s.executeQuery();
        while (result.next())
        {
            this.Coname = result.getString("Coname");
            this.Price = result.getInt("Price");
            this.Category = result.getString("Category");
            this.Bno = result.getInt("Bno");
        }
    }
}
