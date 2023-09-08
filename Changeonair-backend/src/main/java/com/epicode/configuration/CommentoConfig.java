package com.epicode.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.epicode.model.Commento;

@Configuration
public class CommentoConfig {
   
	 @Bean("commentoBean")
	 @Scope("prototype")
	 public Commento commento() {
		 return new Commento();
	 }
}
