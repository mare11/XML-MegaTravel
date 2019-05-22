package org.xmlws.userservice.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmlws.userservice.dto.UserDto;
import org.xmlws.userservice.model.User;
import org.xmlws.userservice.repository.UserRepository;

@Service
public class UserService {

	private ModelMapper mapper = new ModelMapper();

	@Autowired
	private UserRepository userRepository;

	public List<UserDto> findAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserDto> usersDto = new ArrayList<>();

		for (User user : users) {
			usersDto.add(mapper.map(user, UserDto.class));
		}
		return usersDto;
	}

	public UserDto findOneUser(String username) {
		User user = userRepository.findByUsername(username);
		return mapper.map(user, UserDto.class);
	}

	public UserDto updateUser(UserDto userDto) {
		User user = userRepository.findByUsername(userDto.getUsername());
		user.setName(userDto.getName());
		user.setLastname(userDto.getLastname());

		user = userRepository.save(user);
		return mapper.map(user, UserDto.class);

	}

}
