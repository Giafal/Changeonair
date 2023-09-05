package com.epicode.services;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.epicode.model.Video;
import com.epicode.repositories.VideoRepository;

@Service
public class VideoService {

	@Value("${uploadDirectory}")
    private String uploadDirectory;
	
	@Autowired
    private VideoRepository repo;
	
	@Autowired @Qualifier("videoBean") private ObjectProvider<Video> videoProvider;
	
	public void uploadVideo(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            
            String videoFileName = generateUniqueFileName(file.getOriginalFilename());

            
            Path videoFilePath = Paths.get(uploadDirectory, videoFileName);

            
            file.transferTo(videoFilePath);

            
            Video video = videoProvider.getObject();
            video.setNome(file.getOriginalFilename());
            video.setDataCaricamento(new Date());
            

            repo.save(video);
        } else {
            throw new IllegalArgumentException("Il file video è vuoto.");
        }
    }

    
    private String generateUniqueFileName(String originalFileName) {
        
        long timestamp = new Date().getTime();
        return timestamp + "_" + originalFileName;
    }
    
    public List<Video> getAllVideos() {
        
        return repo.findAll();
    }
}

