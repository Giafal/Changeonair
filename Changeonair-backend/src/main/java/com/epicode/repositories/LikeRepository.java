package com.epicode.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epicode.model.Video_like;

public interface LikeRepository extends JpaRepository<Video_like, Long> {

}
