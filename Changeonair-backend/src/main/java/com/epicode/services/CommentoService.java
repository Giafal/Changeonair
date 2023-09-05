package com.epicode.services;

import java.util.Date;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.epicode.model.Commento;
import com.epicode.model.Video;
import com.epicode.repositories.CommentoRepository;
import com.epicode.security.entity.User;

import jakarta.transaction.Transactional;

@Service
public class CommentoService {
	
	@Autowired CommentoRepository repo;
	@Autowired @Qualifier("commentoBean") private ObjectProvider<Commento> commentoProvider;
	
	public Commento creaCommento(Video video, User utente, String testo) {
		Commento c = commentoProvider.getObject();
		c.setVideo(video);
		c.setUtente(utente);
		c.setTesto(testo);
		c.setDataCommento(new Date());
		repo.save(c);
		return c;
	}
	
	@Transactional
    public void rispondiACommento(Commento commentoPadre, User utente, String testo) {
        Commento risposta = commentoProvider.getObject();
        risposta.setVideo(commentoPadre.getVideo());
        risposta.setUtente(utente);
        risposta.setTesto(testo);
        risposta.setCommentoPadre(commentoPadre);
        risposta.setDataCommento(new Date());

        repo.save(risposta);
    }

}
