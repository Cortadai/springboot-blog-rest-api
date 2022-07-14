package com.springboot.blog.repository;

import com.springboot.blog.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/*
we do NOT need to add @Repository annotation to this interface because the
jpaRepository interface has an implementation class called SimpleJpaRepository
and it is internally annotated with @Repository annotation and @Transactional
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

}
