package com.bartek.restApi.adapter;

import com.bartek.restApi.model.Comment;
import com.bartek.restApi.repository.CommentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SqlCommentRepository extends CommentRepository, JpaRepository<Comment, Long> {

    @Override
    Comment save(Comment toSave);

    @Override
    void deleteById(int id);

    @Override
    List<Comment> findAllById(int id);
}
