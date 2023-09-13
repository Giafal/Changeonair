package com.epicode.model;

import java.util.Date;

import lombok.Data;

@Data
public class VideoDTO {

	private Long id;
    private String nome;
    private String descrizione;
    private Date dataCaricamento;
    private int likeCount;
    private Long visualizzazioni;
    private String url;
    private String organizzazione;
    private Long utente;
}
