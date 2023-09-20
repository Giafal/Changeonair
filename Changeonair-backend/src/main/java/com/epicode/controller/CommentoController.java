package com.epicode.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.epicode.interfaces.CommentoProjection;
import com.epicode.model.Commento;
import com.epicode.model.Video;
import com.epicode.model.VideoDTO;
import com.epicode.security.entity.User;
import com.epicode.services.CommentoService;
import com.epicode.services.UserService;
import com.epicode.services.VideoService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/commenti")
@CrossOrigin(origins = "*")
public class CommentoController {
	
	@Autowired VideoService videoService;
	@Autowired UserService userService;
	@Autowired CommentoService commentoService;

	
	@PostMapping("/crea")
	public ResponseEntity<Commento> creaCommento(
	    @RequestParam Long videoId,
	    @RequestParam Long utenteId,
	    @RequestParam String testo) {
	    
	    
	    Video video = videoService.getVideoById(videoId);
	    User utente = userService.getUserById(utenteId);

	    
	    Commento nuovoCommento = commentoService.creaCommento(video, utente, testo);

	    return new ResponseEntity<>(nuovoCommento, HttpStatus.CREATED);
	}
	
	@PostMapping("/rispondi")
	public ResponseEntity<Commento> rispondiACommento(
	    @RequestParam Long commentoPadreId,
	    @RequestParam Long utenteId,
	    @RequestParam String testo) {
	    
	    
	    Commento commentoPadre = commentoService.getCommentoById(commentoPadreId);
	    User utente = userService.getUserById(utenteId);

	    
	    commentoService.rispondiACommento(commentoPadre, utente, testo);

	    return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@DeleteMapping("/cancellaCommento/{id}")
	public ResponseEntity<String> deleteCommento(@PathVariable Long id) {
		String msg = commentoService.deleteCommento(id);
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}
	
	@GetMapping("/getCommenti/{videoId}")
	public ResponseEntity<List<CommentoProjection>> getCommentiByVideoId(@PathVariable Long videoId) {
		List<CommentoProjection> lista = commentoService.getCommentiByVideoId(videoId);
		return new ResponseEntity<List<CommentoProjection>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/getCommenti")
	public ResponseEntity<List<Commento>> getAllCommenti() {
		List<Commento> lista = commentoService.getAllCommenti();
		return new ResponseEntity<List<Commento>>(lista, HttpStatus.OK);
	}
}
