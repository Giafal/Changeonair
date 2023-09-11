package com.epicode.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.epicode.model.Video;

@Configuration
public class VideoConfig {
        
	@Bean("videoBean")
	@Scope("prototype")
	public Video video() {
		return new Video();
	}
}
