package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;


/*
we do NOT need to add @Repository annotation to this interface because the
jpaRepository interface has an implementation class called SimpleJpaRepository
and it is internally annotated with @Repository annotation and @Transactional
 */
public interface PostRepository extends JpaRepository<Post, Long> {
}
