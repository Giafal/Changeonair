package com.epicode.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.epicode.interfaces.CommentoProjection;
import com.epicode.model.Commento;

public interface CommentoRepository extends JpaRepository<Commento, Long>{
	
	@Query("SELECT c FROM Commento c WHERE c.video.id = :videoid")
	public List<Commento> getCommentiByVideoId(Long videoid);
	
	public List<CommentoProjection> findAllByVideoId(Long videoId);

}
