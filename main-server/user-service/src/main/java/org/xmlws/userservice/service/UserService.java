package org.xmlws.userservice.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmlws.userservice.model.User;
import org.xmlws.userservice.model.UserDto;
import org.xmlws.userservice.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

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
        User user = userRepository.findWithFilter("[username = '" + username + "']").get(0);
        return mapper.map(user, UserDto.class);
    }

    public UserDto updateUser(UserDto userDto) {
        User user = userRepository.findWithFilter("[username = '" + userDto.getUsername() + "']").get(0);
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user = userRepository.save(user);
        return mapper.map(user, UserDto.class);

    }

}
