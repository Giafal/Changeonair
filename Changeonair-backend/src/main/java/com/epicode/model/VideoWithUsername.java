package com.epicode.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VideoWithUsername {
	
	private Long id;
    private String nome;
    private String descrizione;
    private Date dataCaricamento;
    private Long visualizzazioni;
    private String url;
    private String organizzazione;
    private Long utente;

}
