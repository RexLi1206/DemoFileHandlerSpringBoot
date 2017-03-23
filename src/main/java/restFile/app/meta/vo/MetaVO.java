package restFile.app.meta.vo;

import org.springframework.stereotype.Component;

import restFile.app.meta.entity.FileMeta;

@Component
public class MetaVO 
{
	

	Integer id;
	String name;
	Long size;
	String uploadTime;
	
	String user;
	String desc;
	String downloadLink;
	
	

	public MetaVO()
	{
		
	}
	
	public MetaVO(FileMeta meta)
	{
		
		if (meta != null)
		{
			this.id = meta.getId();
			this.name = meta.getName();
			this.size = meta.getSize();
			this.uploadTime = meta.getUploadTime().toLocalDateTime().toString();
			this.user = meta.getUser();
			this.desc = meta.getDesc();
			this.downloadLink = meta.getDownloadLink();
			
		}
		
		
	}

	public Integer getId() 
	{
		return id;
	}

	public void setId(Integer id) 
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

	public Long getSize() 
	{
		return size;
	}

	public void setSize(Long size) 
	{
		this.size = size;
	}

	public String getUploadTime() 
	{
		return uploadTime;
	}

	public void setUploadTime(String uploadTime) 
	{
		this.uploadTime = uploadTime;
	}
	
	public String getUser() 
	{
		return user;
	}
	
	public void setUser(String user) 
	{
		this.user = user;
	}
	
	public String getDesc() 
	{
		return desc;
	}
	
	public void setDesc(String desc) 
	{
		this.desc = desc;
	}
	
	public String getDownloadLink() 
	{
		return downloadLink;
	}

	public void setDownloadLink(String downloadLink) 
	{
		this.downloadLink = downloadLink;
	}



}
