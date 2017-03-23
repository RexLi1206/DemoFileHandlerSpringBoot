package restFile.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import restFile.app.storage.exception.*;

@ControllerAdvice
public class ErrorController 
{
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException ex) 
    {
        return ResponseEntity.notFound().build();
    }
	
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(StorageException.class)
    public ResponseEntity handleStorage(StorageException ex) 
    {
		return ResponseEntity.badRequest().body("Upload failed! " + ex.getMessage());
    }
	
	
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity handleValidationException(MethodArgumentNotValidException e)
    {
        return ResponseEntity.badRequest().body("Bad Request! " + e.getMessage());
    }

    @SuppressWarnings("rawtypes")
	@ExceptionHandler
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity handleException(Exception e)
    {
        return ResponseEntity.notFound().build();
        
    }
}
