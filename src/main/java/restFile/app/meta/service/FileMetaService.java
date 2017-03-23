package restFile.app.meta.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import restFile.app.meta.entity.FileMeta;
import restFile.app.meta.vo.MetaVO;

public interface FileMetaService 
{
	
	public void saveMeta(FileMeta meta);

	public List<MetaVO> loadAll();

	public MetaVO getMetaById(Integer id);

	public List<Integer> getMetaIdByKeyword(String keyword);
	
	public List<Integer> getMetaIdByUser(String user);

	FileMeta buildMeta(MultipartFile file, String user, String desc, Timestamp time);

	public List<MetaVO> getMetaUploadedInLastHour();

	
}
