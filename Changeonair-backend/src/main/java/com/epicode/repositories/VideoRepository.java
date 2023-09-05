package com.epicode.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epicode.model.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {

}
