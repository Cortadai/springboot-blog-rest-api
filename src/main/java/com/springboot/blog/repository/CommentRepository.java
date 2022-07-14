package com.springboot.blog.repository;

import com.springboot.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/*
we do NOT need to add @Repository annotation to this interface because the
jpaRepository interface has an implementation class called SimpleJpaRepository
and it is internally annotated with @Repository annotation and @Transactional
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostId(long postId);

}
