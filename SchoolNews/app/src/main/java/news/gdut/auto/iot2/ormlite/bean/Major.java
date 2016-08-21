package news.gdut.auto.iot2.ormlite.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2016/5/15.
 */
@DatabaseTable(tableName = "Major")
public class Major {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "keys")
    private String keys;

    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getkeys()
    {
        return keys;
    }
    public void setkeys(String keys)
    {
        this.keys = keys;
    }

    @Override
    public String toString()
    {
        return "Major [id=" + id + ", name=" + name + ", keys=" + keys
                + "]";
    }
}
