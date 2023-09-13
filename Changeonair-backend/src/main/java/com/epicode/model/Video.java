package com.epicode.model;

import java.util.Date;
import java.util.List;

import com.epicode.security.entity.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
	@JsonIgnore
	private List<Video_like> likes;
	@OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Commento> commenti;
	private int likeCount;
	private Long visualizzazioni;
	private String url;
	private String organizzazione;
	@ManyToOne
	@JoinColumn(name = "utente_id")
	@JsonProperty("utente_id")
	private User utente;

}
