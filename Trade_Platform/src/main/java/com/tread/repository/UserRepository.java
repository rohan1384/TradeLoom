package com.tread.repository;
import com.tread.model.User;


import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
	
	
}
