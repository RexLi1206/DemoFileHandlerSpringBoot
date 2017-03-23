package restFile.app;


import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import restFile.app.storage.exception.StorageFileNotFoundException;
import restFile.app.storage.service.StorageService;

import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class FileUploadTests 
{

	@Autowired
	private EmbeddedDatabase db;
	
    @Autowired
    private MockMvc mvc;

    @MockBean
    private StorageService storageService;

    @Test
    public void shouldListAllFiles() throws Exception 
    {
        given(this.storageService.loadAll())
                .willReturn(Stream.of(Paths.get("first.txt"), Paths.get("second.txt")));

        this.mvc.perform(get("/files/all"))
        		.andExpect(status().isOk())
        		.andReturn().getResponse().getContentAsString().contains("\"http://localhost/files/first.txt\",\"http://localhost/files/second.txt\"");
    }

    @Test
    public void shouldSaveUploadedFile() throws Exception 
    {
        MockMultipartFile multipartFile =
                new MockMultipartFile("file", "test.txt", "text/plain", "Spring Framework".getBytes());
        
        this.mvc.perform(fileUpload("/upload").file(multipartFile))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString().equals("You successfully uploaded test.txt!");

        then(this.storageService).should().store(multipartFile);
    }

    @SuppressWarnings("unchecked")
	@Test
    public void should404WhenMissingFile() throws Exception 
    {
        given(this.storageService.loadAsResource("test.txt"))
                .willThrow(StorageFileNotFoundException.class);

        this.mvc.perform(get("/files/test.txt"))
                .andExpect(status().isNotFound());
    }
    
    @After
    public void tearDown() 
    {
        db.shutdown();
    }
    
    
}
