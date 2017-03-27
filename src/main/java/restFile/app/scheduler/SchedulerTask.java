package restFile.app.scheduler;

//import java.nio.charset.Charset;
import java.sql.Date;
import java.util.List;

//import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.service.EmailService;
import restFile.app.meta.service.FileMetaService;
import restFile.app.meta.vo.MetaVO;

@Component
public class SchedulerTask
{
	
	private final static Logger LOGGER = Logger.getLogger(SchedulerTask.class);
	
	Date now; 
	
	@Autowired
	FileMetaService metaService;
	
	@Autowired
	public EmailService emailService;
	
	
	@Scheduled(fixedRate = 30000)
    public void reportToEmail() throws JsonProcessingException 
	{
		List<MetaVO> metavoList = metaService.getMetaUploadedInLastHour();
		
		ObjectMapper mapper = new ObjectMapper();

		//Object to JSON in String
		String json = mapper.writeValueAsString(metavoList);
		
		LOGGER.info(json);
		
		// send email if json is not empty
		
		if (!json.equals("[]"))
		{
			
			try 
			{
				final Email email = DefaultEmail.builder()
				        .from(new InternetAddress("rex.li1206@gmail.com"))
				        .replyTo(new InternetAddress("rex.li1206@gmail.com"))
				        .to(Lists.newArrayList(new InternetAddress("rex.li1206@gmail.com")))
				        .subject("Files Uploaded in Last Hour")
				        .body(json)
				        .build();
				emailService.send(email);
				
				
			} 
			catch (Exception e) 
			{
				LOGGER.error(e.getMessage());
			}

		}
		
		
		
		
    }
	
	
}
