package com.example.photogram.service;

import com.example.photogram.domain.comment.Comment;
import com.example.photogram.domain.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Comment 댓글쓰기 () {
        return null;
    }

    @Transactional
    public Comment 댓글삭제 () {
        return null;
    }
}
