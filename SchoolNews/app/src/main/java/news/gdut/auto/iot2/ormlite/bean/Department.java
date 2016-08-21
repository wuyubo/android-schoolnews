package news.gdut.auto.iot2.ormlite.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2016/5/15.
 */
@DatabaseTable(tableName = "Department")
public class Department {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(canBeNull = true, foreign = true, columnName = "school_id", foreignAutoRefresh = true)
    private School school;

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

    public School getSchool()
    {
        return school;
    }
    public void setSchool(School school)
    {
        this.school = school;
    }

    @Override
    public String toString()
    {
        return "Major [id=" + id + ", name=" + name
                + "]";
    }
}
