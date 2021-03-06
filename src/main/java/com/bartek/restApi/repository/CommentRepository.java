package com.bartek.restApi.repository;

import com.bartek.restApi.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository <Comment, Long> {

    Comment save(Comment toSave);
    void deleteById(int id);
    List<Comment> findAllById(int id);
}
