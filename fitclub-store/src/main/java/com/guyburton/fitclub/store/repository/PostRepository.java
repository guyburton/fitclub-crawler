package com.guyburton.fitclub.store.repository;

import com.guyburton.fitclub.store.entities.JpaPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<JpaPost, Integer> {

    List<JpaPost> findByUsername(String username);
}
