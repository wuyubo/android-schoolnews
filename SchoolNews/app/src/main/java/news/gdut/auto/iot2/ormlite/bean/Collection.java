package news.gdut.auto.iot2.ormlite.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Time;

/**
 * Created by Administrator on 2016/5/15.
 */
@DatabaseTable(tableName = "Collection")
public class Collection {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(canBeNull = true, foreign = true, columnName = "user_id", foreignAutoRefresh = true)
    private User user;
    @DatabaseField(canBeNull = true, foreign = true, columnName = "newsInform_id", foreignAutoRefresh = true)
    private NewsInform newsInform;
    @DatabaseField(columnName = "time")
    private String time;


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

    public User getUser()
    {
        return user;
    }
    public void setUser(User user)
    {
        this.user = user;
    }

    public NewsInform getNewsInform()
    {
        return newsInform;
    }
    public void setNewsInform(NewsInform newsInform)
    {
        this.newsInform = newsInform;
    }

    public String gettime()
    {
        return time;
    }
    public void settime(String time)
    {
        this.time = time;
    }
}
