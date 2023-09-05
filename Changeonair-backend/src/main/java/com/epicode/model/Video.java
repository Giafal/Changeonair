package com.epicode.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Video {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String descrizione;
	private Date dataCaricamento;
	@OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
	private List<Video_like> likes;
	@OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
	private List<Commento> commenti;
	private int likeCount;
	private Long visualizzazioni;
	private String url;

}
