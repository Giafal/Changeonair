package com.epicode.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.epicode.model.Video;
import com.epicode.payload.FileUploadResponse;
import com.epicode.services.VideoService;
import com.epicode.util.FileDownloadUtil;
import com.epicode.util.FileUploadUtil;

@RestController
@RequestMapping("/api/videos")
@CrossOrigin(origins = "*")
public class VideoController {
	
	@Autowired VideoService svc;
	
	@PostMapping("/uploadFile")
    public ResponseEntity<FileUploadResponse> uploadFile(
            @RequestParam("file") MultipartFile multipartFile)
                    throws IOException {
         
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();
         
        String filecode = FileUploadUtil.saveFile(fileName, multipartFile);
        
        
         
        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(fileName);
        response.setSize(size);
        response.setDownloadUri("/downloadFile/" + filecode);
        
        svc.creaVideo(fileName, 
  		      "", 
  		      "http://localhost:8080/api/videos" + response.getDownloadUri(), 
  		      "", 
  		      0l,
  		      new Date());
         
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	@GetMapping("/getVideos") 
	public ResponseEntity<List<Video>> getAllVideo() {
		List<Video> lista = svc.getAllVideo();
		return new ResponseEntity<List<Video>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/getVideos/{id}")
	public ResponseEntity<Optional<Video>> getVideo(@PathVariable Long id) {
		Optional<Video> v = svc.getVideoById(id);
		return new ResponseEntity<Optional<Video>>(v, HttpStatus.OK);
	}
	
	@GetMapping("/downloadFile/{fileCode}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode) {
        FileDownloadUtil downloadUtil = new FileDownloadUtil();
         
        Resource resource = null;
        try {
            resource = downloadUtil.getFileAsResource(fileCode);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
         
        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }
         
        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";
         
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);       
    }
}

    
