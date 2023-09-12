package com.epicode.services;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Service;


import com.epicode.model.Video;
import com.epicode.model.VideoWithUsername;
import com.epicode.repositories.VideoRepository;
import com.epicode.security.entity.User;


import jakarta.persistence.EntityNotFoundException;

@Service
public class VideoService {
	
	@Autowired VideoRepository repo;
	@Autowired @Qualifier("videoBean") private ObjectProvider<Video> videoProvider;
	
	
	public Video creaVideo(String nome, String descrizione, String url, String organizzazione, Long visualizzazioni, Date dataCaricamento, User utente) {
		Video v = videoProvider.getObject();
		v.setNome(nome);
		v.setDescrizione(descrizione);
		v.setUrl(url);
		v.setOrganizzazione(organizzazione);
		v.setVisualizzazioni(visualizzazioni);
		v.setDataCaricamento(dataCaricamento);
		v.setUtente(utente);
		repo.save(v);
		return v;
	}
	
	public List<Video> getAllVideo() {
		List<Video> lista = repo.findAll();
		for (Video video : lista) {
			
		}
		return lista;
	}
	
	public Video getVideoById(Long id) {
		if(!repo.existsById(id)) {
			throw new EntityNotFoundException("Video doesn't exists!!!");
		}
		Video v = repo.findById(id).get();
		return v;
	}
	
	public List<Video> getByName(String nome) {
		List<Video> lista = repo.getByNome(nome);
		return lista;
	}
	
	public List<Video> getByUtente(Long idUtente) {
		List<Video> lista = repo.findByUtente(idUtente);
		return lista;
	}
	
	public List<Video> getByUsernameUtente(String nome) {
		List<Video> lista  = repo.findByUsernameUtente(nome);
		return lista;
	}
	
	public String deleteVideo(Long id) {
		if(!repo.existsById(id)) {
			throw new EntityNotFoundException("Video doesn't exists!!!");
		}
		Video v = getVideoById(id);
		repo.delete(v);
		return "Video deleted!!";
		
	}
	
	public Video updateVideo(Long id, Video video) {
		if(!repo.existsById(id)) {
			throw new EntityNotFoundException("Video doesn't exists!!!");
		}
		if(id != video.getId()) {
			throw new EntityNotFoundException("Id and ContactID do not match!");
		}
		return  repo.save(video);
		
	}
	
	public List<VideoWithUsername> getVideosWithUsername() {
        List<Video> videos = repo.findAll(); // Recupera i video
        List<VideoWithUsername> videosWithUsername = new ArrayList<>();

        for (Video video : videos) {
            VideoWithUsername videoWithUsername = new VideoWithUsername();
            videoWithUsername.setId(video.getId());
            videoWithUsername.setNome(video.getNome());
            videoWithUsername.setDescrizione(video.getDescrizione());
            videoWithUsername.setDataCaricamento(video.getDataCaricamento());
            videoWithUsername.setVisualizzazioni(video.getVisualizzazioni());
            videoWithUsername.setUrl(video.getUrl());
            videoWithUsername.setOrganizzazione(video.getOrganizzazione());
            videoWithUsername.setUtente(video.getUtente().getId()); // Ottieni l'ID dell'utente

            videosWithUsername.add(videoWithUsername);
        }

        return videosWithUsername;
    }
	
	

	
}

