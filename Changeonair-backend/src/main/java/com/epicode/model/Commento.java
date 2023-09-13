package com.epicode.model;

import java.util.Date;
import java.util.List;

import com.epicode.security.entity.User;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonSerialize
public class Commento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
    @JoinColumn(name = "video_id")
	private Video video;
	@ManyToOne
    @JoinColumn(name = "utente_id")
	private User utente;
	private String testo;
	private Date dataCommento;
	@ManyToOne
    @JoinColumn(name = "commento_padre_id")
	private Commento commentoPadre;
	@OneToMany(mappedBy = "commentoPadre", cascade = CascadeType.ALL)
	private List<Commento> risposte;
	
	

}
