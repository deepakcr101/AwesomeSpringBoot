package com.deepak.exceptionhandling.repository;

import com.deepak.exceptionhandling.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
