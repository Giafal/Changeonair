package com.epicode.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoData {
	
	private String nome;
    private String descrizione;
    private String organizzazione;

}
