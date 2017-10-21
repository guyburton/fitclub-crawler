package com.guyburton.fitclub.store.repository;

import com.guyburton.fitclub.store.entities.JpaUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<JpaUser, String> {
}
