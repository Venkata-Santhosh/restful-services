package com.cocument.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.cocument.entities.User;
import com.cocument.exceptions.UserExistsException;
import com.cocument.exceptions.UserNameNotFoundException;
import com.cocument.exceptions.UserNotFoundException;
import com.cocument.services.UserService;

@RestController
@Validated
public class UserController {

	private UserService userServices;

	public UserController(UserService userServices) {
		this.userServices = userServices;
	}

	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userServices.getAllUsers();
	}

	@GetMapping("/users/{id}")
	public Optional<User> getUserById(@PathVariable("id") @Min(1) Long id) {

		try {
			return userServices.getUserByID(id);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@GetMapping("/users/byusername/{userName}")
	public User getUserByUserName(@PathVariable("userName") String userName) throws UserNameNotFoundException {

		User user = userServices.getUserByUserName(userName);

		if(user == null) {
			throw new UserNameNotFoundException("Username not found in user repository");
		}
		
		return user;
	}

	@PostMapping("/users1")
	public User createUser(@RequestBody User user) {
		try {
			return userServices.createUser(user);
		} catch (UserExistsException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/users")
	public ResponseEntity<Void> createUserWithLocationHeader(@RequestBody @Valid User user,
			UriComponentsBuilder uriComponentsBuilder) {

		try {
			userServices.createUser(user);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(uriComponentsBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri());

			return new ResponseEntity<>(headers, HttpStatus.CREATED);
		} catch (UserExistsException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}

	@PutMapping("/users/{id}")
	public User updateUserById(@PathVariable("id") Long id, @RequestBody User user) {
		try {
			return userServices.updateUserById(id, user);
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@DeleteMapping("/users/{id}")
	public void deleteUserById(@PathVariable("id") Long id) {
		userServices.deleteUserById(id);
	}

}
