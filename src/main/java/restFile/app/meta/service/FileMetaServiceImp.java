package restFile.app.meta.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import restFile.app.controller.MainRestController;
import restFile.app.meta.dao.FileMetaDAO;
import restFile.app.meta.entity.FileMeta;
import restFile.app.meta.vo.MetaVO;

@Service
public class FileMetaServiceImp implements FileMetaService 
{
	@Autowired
	FileMetaDAO metaDAO;
	
	@Override
	public FileMeta buildMeta(MultipartFile file, String user, String desc, Timestamp time)
    {
    	FileMeta meta = new FileMeta();
    	meta.setName(file.getOriginalFilename());
    	meta.setSize(file.getSize());
    	meta.setUploadTime(time);
        meta.setUser((null == user)?"anonymousUser":user);
        meta.setDesc((null == desc)?"":desc);
        String link = MvcUriComponentsBuilder.fromMethodName(MainRestController.class, "serveFile", meta.getName().toString()).build().toString();
        meta.setDownloadLink(link);
    	return meta;
    }
	
	@Override
	@Transactional
	public void saveMeta(FileMeta meta) 
	{
		metaDAO.save(meta);
	}
	
	@Override
	@Transactional
	public List<MetaVO> loadAll() 
	{
		List<MetaVO> list = new ArrayList<>();
		for (FileMeta meta: metaDAO.findAll())
			list.add(new MetaVO(meta));
		return list;
	}
	
	@Override
	@Transactional
	public MetaVO getMetaById(Integer id) 
	{
		MetaVO metavo;
		FileMeta meta = metaDAO.getOne(id);
		if (meta != null)
			metavo = new MetaVO(meta);
		else 
			metavo = null;
		return metavo;
	}
	
	@Override
	@Transactional
	public List<Integer> getMetaIdByKeyword(String keyword) 
	{

		List<Integer> list = metaDAO.findIdByKeyword(keyword);
		return list;
		
	}
	
	@Override
	public List<Integer> getMetaIdByUser(String user) 
	{
		List<Integer> list = metaDAO.findIdByUser(user);
		return list;
	}
	
	@Override
	public List<MetaVO> getMetaUploadedInLastHour() 
	{
		List<MetaVO> list = new ArrayList<>();
		Timestamp hourago = new Timestamp(System.currentTimeMillis());
		final Long duration = 60 * 60 * 1000l;
		hourago.setTime(hourago.getTime() - duration);
		for (FileMeta meta: metaDAO.findByLastHour(hourago) )
			list.add(new MetaVO(meta));
		return list;
	}

}
