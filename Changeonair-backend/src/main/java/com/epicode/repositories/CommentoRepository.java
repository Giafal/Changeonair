package com.epicode.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epicode.model.Commento;

public interface CommentoRepository extends JpaRepository<Commento, Long>{

}
