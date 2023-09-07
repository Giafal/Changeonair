package com.epicode.services;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.epicode.model.Video;
import com.epicode.repositories.VideoRepository;

@Service
public class VideoService {
	
	@Autowired VideoRepository repo;
	@Autowired @Qualifier("videoBean") private ObjectProvider<Video> videoProvider;
	
	
	public Video creaVideo(String nome, String descrizione, String url, String organizzazione, Long visualizzazioni, Date dataCaricamento) {
		Video v = videoProvider.getObject();
		v.setNome(nome);
		v.setDescrizione(descrizione);
		v.setUrl(url);
		v.setOrganizzazione(organizzazione);
		v.setVisualizzazioni(visualizzazioni);
		v.setDataCaricamento(dataCaricamento);
		repo.save(v);
		return v;
	}
	
	public List<Video> getAllVideo() {
		List<Video> lista = repo.findAll();
		for (Video video : lista) {
			
		}
		return lista;
	}
	
	public Optional<Video> getVideoById(Long id) {
		Optional<Video> v = repo.findById(id);
		return v;
	}

	
}

