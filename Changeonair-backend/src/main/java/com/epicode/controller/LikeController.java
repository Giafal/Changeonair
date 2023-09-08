package com.epicode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epicode.model.Video;
import com.epicode.security.entity.User;
import com.epicode.services.LikeService;
import com.epicode.services.UserService;
import com.epicode.services.VideoService;

@RestController
@RequestMapping("/api/likes")
public class LikeController {
	
	@Autowired LikeService likeService;
	@Autowired VideoService videoService;
	@Autowired UserService userService;
	
	@PostMapping("/add")
	public ResponseEntity<String> addLikeToVideo(
	        @RequestParam Long videoId,
	        @RequestParam Long userId
	) {
	    
	    Video video = videoService.getVideoById(videoId);
	    User user = userService.getUserById(userId);

	    if (video != null && user != null) {
	        likeService.addLikeToVideo(video, user);
	        return ResponseEntity.ok("Like added successfully");
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Video or user not found");
	    }
	}

}
