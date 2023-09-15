package com.epicode.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.epicode.model.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {
	
	@Query("SELECT v FROM Video v WHERE v.nome ILIKE :nome ")
	public List<Video> getByNome(String nome);
	
	@Query("SELECT v FROM Video v WHERE v.utente.id = :idUtente")
	public List<Video> findByIdUtente(@Param("idUtente")Long idUtente);
	
	@Query("SELECT v FROM Video v WHERE v.utente.username ILIKE :nome")
	public List<Video> findByUsernameUtente(String nome);
	
    

}
