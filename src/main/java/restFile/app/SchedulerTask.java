package restFile.app;



import java.sql.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import restFile.app.meta.service.FileMetaService;
import restFile.app.meta.vo.MetaVO;

@Component
public class SchedulerTask
{
	
	private final static Logger LOGGER = Logger.getLogger(SchedulerTask.class);
	
	Date now; 
	
	@Autowired
	FileMetaService metaService;
	
	@Scheduled(fixedRate = 30000)
    public void reportToEmail() throws JsonProcessingException 
	{
		List<MetaVO> metavoList = metaService.getMetaUploadedInLastHour();
		
		ObjectMapper mapper = new ObjectMapper();

		//Object to JSON in String
		String json = mapper.writeValueAsString(metavoList);
		
		LOGGER.info(json);
		// send email (will add in the future)
    }
	
	
}
