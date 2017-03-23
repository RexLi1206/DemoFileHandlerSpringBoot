package restFile.app.meta.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "meta")
public class FileMeta 
{
	Integer id;
	String name;
	Long size;
	Timestamp uploadTime;
	
	String user;
	String desc;
	String downloadLink;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "file_id")
	public Integer getId() 
	{
		return this.id;
	}
	
	public void setId(Integer id) 
	{
		this.id = id;
	}
	
	@Column(name = "file_name")
	public String getName() 
	{
		return this.name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	@Column(name = "file_size")
	public Long getSize() 
	{
		return this.size;
	}
	
	public void setSize(Long size) 
	{
		this.size = size;
	}
	
	@Column(name = "upload_time")
	public Timestamp getUploadTime() 
	{
		return this.uploadTime;
	}
	
	public void setUploadTime(Timestamp time) 
	{
		this.uploadTime = time;
	}
	
	@Column(name = "upload_user")
	public String getUser() 
	{
		return user;
	}
	
	public void setUser(String user) 
	{
		this.user = user;
	}
	
	@Column(name = "description")
	public String getDesc() 
	{
		return desc;
	}
	
	public void setDesc(String desc) 
	{
		this.desc = desc;
	}
	
	@Column(name = "download_link")
	public String getDownloadLink() 
	{
		return downloadLink;
	}
	
	public void setDownloadLink(String downloadLink) 
	{
		this.downloadLink = downloadLink;
	}
	
}
