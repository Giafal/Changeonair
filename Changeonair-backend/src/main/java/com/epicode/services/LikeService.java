package com.epicode.services;

import java.util.Date;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.epicode.model.Video;
import com.epicode.model.Video_like;
import com.epicode.repositories.LikeRepository;
import com.epicode.repositories.VideoRepository;
import com.epicode.security.entity.User;

import jakarta.transaction.Transactional;

@Service
public class LikeService {

	@Autowired LikeRepository repo;
	@Autowired VideoRepository videoRepo;
	@Autowired @Qualifier("likeBean") private ObjectProvider<Video_like> likeProvider;
	
	
	@Transactional
    public void addLikeToVideo(Video video, User utente) {
        Video_like like = likeProvider.getObject();
        like.setVideo(video);
        like.setUtente(utente);
        like.setDataLike(new Date());
        repo.save(like);
        
        video.setLikeCount(video.getLikeCount() + 1);
        videoRepo.save(video);
    }
}
