package com.epicode.services;

import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.epicode.model.Commento;
import com.epicode.model.Video;
import com.epicode.model.VideoDTO;
import com.epicode.repositories.CommentoRepository;
import com.epicode.repositories.VideoRepository;
import com.epicode.security.entity.User;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CommentoService {
	
	@Autowired CommentoRepository repo;
	@Autowired VideoRepository videoRepo;
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
	
	
    public void rispondiACommento(Commento commentoPadre, User utente, String testo) {
        Commento risposta = commentoProvider.getObject();
        risposta.setVideo(commentoPadre.getVideo());
        risposta.setUtente(utente);
        risposta.setTesto(testo);
        risposta.setCommentoPadre(commentoPadre);
        risposta.setDataCommento(new Date());

        repo.save(risposta);
    }
	
	public Commento getCommentoById(Long id) {
		if(!repo.existsById(id)) {
			throw new EntityNotFoundException("User doesn't exists!!!");
		}
		Commento c = repo.findById(id).get();
		return c;
	}
	
	public String deleteCommento(Long id) {
		if(!repo.existsById(id)) {
			throw new EntityNotFoundException("Video doesn't exists!!!");
		}
		Commento c = getCommentoById(id);
		repo.delete(c);
		return "Commento deleted!!";
		
	}
	
	public List<Commento> getCommentiByVideoId(Long id) {
		Video v = videoRepo.findById(id).get();
		List<Commento> lista = v.getCommenti();
		return lista;
	}

}
