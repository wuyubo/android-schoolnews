package news.gdut.auto.iot2.schoolnews.domain;

/**
 * 新闻列表的bean类，一个News对象对应于一条item
 * @author linpeng123l
 *
 */
public class News {
	private String title;
	private String url;
	private String photoUrl;
	private String source;
	private String date;
	private String department;
	
	public News() {
		super();
	}
	public News(String title, String url, String photoUrl, String source,
			String date, String department) {
		super();
		this.title = title;
		this.url = url;
		this.photoUrl = photoUrl;
		this.source = source;
		this.date = date;
		this.department = department;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}

}
