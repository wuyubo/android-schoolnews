package news.gdut.auto.iot2.ormlite.bean;

import java.util.Collection;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_user")
public class User 
{
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(columnName = "name")
	private String name;
	@DatabaseField(columnName = "sex")
	private String sex;
	@DatabaseField(columnName = "professional")
	private String professional;
	@DatabaseField(canBeNull = true, foreign = true, columnName = "school_id", foreignAutoRefresh = true)
	private School school;
	@DatabaseField(canBeNull = true, foreign = true, columnName = "college_id", foreignAutoRefresh = true)
	private College college;
	@DatabaseField(canBeNull = true, foreign = true, columnName = "major_id", foreignAutoRefresh = true)
	private Major major;


	public User()
	{

	}

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

	public String getsex()
	{
		return sex;
	}
	public void setsex(String sex)
	{
		this.sex = sex;
	}

	public String getprofessional()
	{
		return professional;
	}
	public void setprofessional(String professional)
	{
		this.professional = professional;
	}

	public School getschool()
	{
		return school;
	}
	public void setschool(School school)
	{
		this.school = school;
	}

	public College getcollege()
	{
		return college;
	}
	public void setcollege(College college)
	{
		this.college = college;
	}

	public Major getmajor()
	{
		return major;
	}
	public void setmajor(Major major)
	{
		this.major = major;
	}

	@Override
	public String toString()
	{
		return "User [id=" + id + ", name=" + name + ", sex=" + sex
				+ ", professional=" + professional
				+ "]";
	}
}
