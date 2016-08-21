package news.gdut.auto.iot2.ormlite.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by Administrator on 2016/5/15.
 */
@DatabaseTable(tableName = "NewsInform")
public class NewsInform {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "title")
    private String title;
    @DatabaseField(columnName = "type")
    private String type;
    @DatabaseField(columnName = "src_url")
    private String src_url;
    @DatabaseField(columnName = "img_url")
    private String img_url;
    @DatabaseField(columnName = "content")
    private String content;
    @DatabaseField(columnName = "is_valid")
    private Boolean is_valid;
    @DatabaseField(columnName = "time")
    private String time;
    @DatabaseField(canBeNull = true, foreign = true, columnName = "school_id", foreignAutoRefresh = true)
    private School school;
    @DatabaseField(canBeNull = true, foreign = true, columnName = "department_id", foreignAutoRefresh = true)
    private Department department;


    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }

    public String gettitle()
    {
        return title;
    }
    public void settitle(String title)
    {
        this.title = title;
    }

    public String gettype()
    {
        return img_url;
    }
    public void settype(String type)
    {
        this.type = type;
    }

    public String getsrc_url()
    {
        return src_url;
    }
    public void setsrc_url(String src_url)
    {
        this.src_url = src_url;
    }

   public String getimg_url()
    {
        return img_url;
    }
    public void setimg_url(String img_url)
    {
        this.img_url = img_url;
    }

    public String getcontent()
    {
        return content;
    }
    public void setcontent(String content)
    {
        this.content = content;
    }

    public Boolean getis_valid()
    {
        return is_valid;
    }
    public void setis_valid(Boolean is_valid)
    {
        this.is_valid = is_valid;
    }

    public String gettime()
    {
        return time;
    }
    public void settime(String time)
    {
        this.time = time;
    }

    public School getschool()
    {
        return school;
    }
    public void setschool(School school)
    {
        this.school = school;
    }

    public Department getdepartment()
    {
        return department;
    }
    public void setdepartment(Department department)
    {
        this.department = department;
    }

    @Override
    public String toString()
    {
        return "News [id=" + id + ", title=" + title + ", content=" + content
                + ", type=" + type+ ", time=" + time
                + "]";
    }

}
