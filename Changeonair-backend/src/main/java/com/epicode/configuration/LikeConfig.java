package com.epicode.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.epicode.model.Video_like;

@Configuration
public class LikeConfig {
     
	@Bean("likeBean")
	@Scope("prototype")
	public Video_like like() {
		return new Video_like();
	}
}
