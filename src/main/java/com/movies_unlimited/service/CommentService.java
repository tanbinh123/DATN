package com.movies_unlimited.service;

import com.movies_unlimited.entity.CommentEntity;
import com.movies_unlimited.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentEntity save(CommentEntity comment) {
        return commentRepository.save(comment);
    }

    public List<CommentEntity> getCommentsByProductId(int id) {
        return commentRepository.findByProduct_id(id);
    }
}
