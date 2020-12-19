package com.cocument.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.cocument.entities.User;
import com.cocument.exceptions.UserExistsException;
import com.cocument.exceptions.UserNameNotFoundException;
import com.cocument.exceptions.UserNotFoundException;
import com.cocument.repositories.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	public User createUser(User user) throws UserExistsException{
		
		User existingUser = userRepository.findByUserName(user.getUserName());
		
		if(existingUser != null) {
			throw new UserExistsException("User exists in user repository");
		}
		return userRepository.save(user);
	}
	
	public Optional<User> getUserByID(Long id) throws UserNotFoundException {
		
		
		Optional<User> user= userRepository.findById(id);
		
		if(!user.isPresent()) {
			throw new UserNotFoundException("user not found in user repository");
		}
		
		return user;
	}
	
	public User updateUserById( Long id,User user) throws UserNotFoundException {
		
		Optional<User> optionalUser= userRepository.findById(id);
		
		if(!optionalUser.isPresent()) {
			throw new UserNotFoundException("user not found in user repository, please provide valid userid");
		}
		//if user exists 
		user.setId(id);
		return userRepository.save(user);
	}
	
	public User getUserByUserName(String userName) throws UserNameNotFoundException{
		User user =  userRepository.findByUserName(userName);
		
		
		return user;
	}
	
	public void deleteUserById(Long id) {
		

		Optional<User> optionalUser= userRepository.findById(id);
		
		if(!optionalUser.isPresent()) {
			throw new ResponseStatusException
			(HttpStatus.BAD_REQUEST,
					"user not found in user repository, please provide valid userid");
		}
		
		userRepository.deleteById(id);
		
	}
}
