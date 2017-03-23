package restFile.app.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import restFile.app.meta.service.FileMetaService;
import restFile.app.meta.vo.MetaVO;
import restFile.app.storage.service.StorageService;

@RestController
public class MainRestController 
{

	private final StorageService storageService;
	private final FileMetaService metaService;
    
    @Autowired
    public MainRestController(StorageService storageService, FileMetaService metaService) 
    {
        this.storageService = storageService;
        this.metaService = metaService;
    }
	    
    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload (@RequestParam("file") MultipartFile file,
    												@RequestParam(value = "user", required = false) String user, 
    												@RequestParam(value = "desc", required = false) String desc) 
    {
    	Timestamp uploadTime = new Timestamp(System.currentTimeMillis());
    	storageService.store(file);
    	metaService.saveMeta(metaService.buildMeta(file, user, desc, uploadTime));
    	
    	return ResponseEntity.ok().body("You successfully uploaded " + file.getOriginalFilename() + "!");
    }
    
    @GetMapping("/files/all")
    public ResponseEntity<List<String>> listAllFileLinks()  
    {
    	
    	List<String> fileList = storageService
                .loadAll()
                .map(path ->
                        MvcUriComponentsBuilder
                                .fromMethodName(MainRestController.class, "serveFile", path.getFileName().toString())
                                .build().toString())
                .collect(Collectors.toList());
    	
    	return ResponseEntity.ok().body(fileList); 
    }
    
    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename)  
    {

    	Resource file = storageService.loadAsResource(filename);
    	
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file); 
        
    }
    
    @GetMapping("/meta/all")
    public ResponseEntity<List<MetaVO>> serveAllMeta() 
    {
    	return ResponseEntity.ok().body(metaService.loadAll());	
    }
    
    @GetMapping(value = "/meta", params={"id"})
    public  ResponseEntity<MetaVO> serveMetaById(@RequestParam("id") Integer id) 
    {
    	return ResponseEntity.ok().body(metaService.getMetaById(id)); 	
    }
    
    @GetMapping("/meta/lasthour")
    public ResponseEntity<List<MetaVO>> serveMetaLastHour() 
    {
    	return ResponseEntity.ok().body(metaService.getMetaUploadedInLastHour());
    }
    
    
    @GetMapping(value = "/meta_id", params={"keyword"})
    public ResponseEntity<List<Integer>> serveMetaByKeyword(@RequestParam("keyword") String keyword) 
    {
    	return ResponseEntity.ok().body(metaService.getMetaIdByKeyword(keyword));
    }
    
    @GetMapping(value = "/meta_id", params={"user"})
    public ResponseEntity<List<Integer>> serveMetaByUploadUser(@RequestParam("user") String user) 
    {
    	return ResponseEntity.ok().body(metaService.getMetaIdByUser(user));
    }
    
    
    
}
