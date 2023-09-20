package com.epicode.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.multipart.MultipartFile;

import com.epicode.model.Video;
import com.epicode.model.VideoDTO;
import com.epicode.model.VideoWithUsername;
import com.epicode.payload.FileUploadResponse;
import com.epicode.security.entity.User;
import com.epicode.security.repository.UserRepository;
import com.epicode.services.VideoService;
import com.epicode.util.FileDownloadUtil;
import com.epicode.util.FileUploadUtil;



@RestController
@RequestMapping("/api/videos")
@CrossOrigin(origins = "*")
public class VideoController {
	
	@Autowired VideoService svc;
	@Autowired UserRepository userRepository;
	
	@PostMapping("/uploadFile")
    public ResponseEntity<FileUploadResponse> uploadFile(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam("nome") String nome,
            @RequestParam("descrizione") String descrizione,
            @RequestParam("organizzazione") String organizzazione
            
            
           )
                    throws IOException {
         
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();
         
        String filecode = FileUploadUtil.saveFile(fileName, multipartFile);
        
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        
        
        
        User user = userRepository.findByEmail(currentUserEmail);
        
        
        
         
        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(fileName);
        response.setSize(size);
        response.setDownloadUri("/downloadFile/" + filecode);
        
        svc.creaVideo(nome, 
  		      descrizione, 
  		      "http://localhost:8080/api/videos" + response.getDownloadUri(), 
  		      organizzazione, 
  		      0l,
  		      new Date(),
  		      user);
         
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
//	@GetMapping("/getVideos") 
//	public ResponseEntity<List<Video>> getAllVideo() {
//		List<Video> lista = svc.getAllVideo();
//		return new ResponseEntity<List<Video>>(lista, HttpStatus.OK);
//	}
	
	@GetMapping("/getVideos")
	public ResponseEntity<List<VideoWithUsername>> getVideos() {
	    List<VideoWithUsername> videos = svc.getVideosWithUsername(); // VideoWithUsername è una nuova classe DTO che contiene l'ID dell'utente insieme ai dati del video
	    return ResponseEntity.ok(videos);
	}
	
	@GetMapping("/getVideo/{id}")
	public ResponseEntity<VideoDTO> getVideo(@PathVariable Long id) {
		VideoDTO v = svc.getVideoDTOById(id);
		return new ResponseEntity<VideoDTO>(v, HttpStatus.OK);
	}
	
	@GetMapping("/getVideosByNome/{nome}")
	public ResponseEntity<List<VideoDTO>> getByName(@PathVariable String nome) {
		String parametroConJolly = "%" + nome + "%";
		List<VideoDTO> lista = svc.getByName(parametroConJolly);
		ResponseEntity<List<VideoDTO>> result = new ResponseEntity<List<VideoDTO>>(lista, HttpStatus.OK);
		return result;
	}
	
	@GetMapping("/getVideosByUtente/{idUtente}")
	public ResponseEntity<List<VideoDTO>> getByUtente(@PathVariable Long idUtente) {
		List<VideoDTO> lista = svc.getByUtente(idUtente);
		return new ResponseEntity<List<VideoDTO>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/getVideosByUsernameUtente/{username}")
	public ResponseEntity<List<VideoDTO>> getByUsernameUtente(@PathVariable String username) {
		String parametroConJolly = "%" + username + "%";
		List<VideoDTO> lista = svc.getByUsernameUtente(parametroConJolly);
		return new ResponseEntity<List<VideoDTO>>(lista, HttpStatus.OK);
		
		
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
	
	@DeleteMapping("/deleteVideo/{id}")
	public ResponseEntity<?> deleteVideo(@PathVariable Long id) {
		String msg = svc.deleteVideo(id);
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}
	
	@PutMapping("/updateVideo/{id}")
	public ResponseEntity<?> updateVideo(@PathVariable Long id, @RequestBody Video video) {
		Video v = svc.updateVideo(id, video);
		return new ResponseEntity<Video>(v, HttpStatus.OK);
	}
	
	@PutMapping("/addVisualizzazione/{videoId}")
	public ResponseEntity<?> addVisualizzazione(@PathVariable Long videoId) {
		Video v = svc.addVisualizzazione(videoId);
		return new ResponseEntity<Video>(v, HttpStatus.OK);
	}
	
	@GetMapping("/{videoId}/visualizzazioni")
	public Long getVisualizzazioni(@PathVariable Long videoId) {
		Video video = svc.getVideoById(videoId);
		return video.getVisualizzazioni();
	}
}

    
