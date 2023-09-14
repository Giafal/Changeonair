package com.epicode.interfaces;

import java.util.Date;

import com.epicode.security.entity.User;

public interface CommentoProjection {
	
	Long getId();
	String getTesto();
	Date getDataCommento();
	User getUtente();
	

}
