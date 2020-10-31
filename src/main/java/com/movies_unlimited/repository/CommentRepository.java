package com.movies_unlimited.repository;

import com.movies_unlimited.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    List<CommentEntity> findByProduct_id(int id);
}
