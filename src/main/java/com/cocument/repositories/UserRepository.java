package com.cocument.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cocument.entities.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	 User findByUserName(String userName);

}
