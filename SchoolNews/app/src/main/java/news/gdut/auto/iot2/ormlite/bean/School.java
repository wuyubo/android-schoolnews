package news.gdut.auto.iot2.ormlite.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2016/5/15.
 */
@DatabaseTable(tableName = "School")
public class School {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "url")
    private String url;


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

    public String geturl()
    {
        return url;
    }
    public void seturl(String url)
    {
        this.url = url;
    }

    @Override
    public String toString()
    {
        return "School [id=" + id + ", name=" + name + ", url=" + url
                + "]";
    }
}
