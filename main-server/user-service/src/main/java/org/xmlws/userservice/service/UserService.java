package org.xmlws.userservice.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmlws.userservice.exceptions.UserNotFoundException;
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
        User user = getUser(username);
        return mapper.map(user, UserDto.class);
    }

    public UserDto updateUser(UserDto userDto) {
        User user = getUser(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user = userRepository.save(user);
        return mapper.map(user, UserDto.class);

    }

    public UserDto enableUser(UserDto userDto) {
        User user = getUser(userDto.getUsername());
        user.setEnabled(true);
        user = userRepository.save(user);
        return mapper.map(user, UserDto.class);
    }

    public UserDto disableUser(UserDto userDto) {
        User user = getUser(userDto.getUsername());
        user.setEnabled(false);
        user = userRepository.save(user);
        return mapper.map(user, UserDto.class);
    }

    public UserDto deleteUser(UserDto userDto) {
        User user = getUser(userDto.getUsername());
        user.setDeleted(true);
        user = userRepository.save(user);
        return mapper.map(user, UserDto.class);
    }

    //    ADD USER EXISTING CHECK FOR THESE 2 WITH USER ID
    public void addReservation(Long userId, Long reservationId) {
        User user = userRepository.findWithFilter("[id = '" + userId + "']").get(0);
        user.getReservationIds().add(reservationId);
        userRepository.save(user);
    }

    public void cancelReservation(Long userId, Long reservationId) {
        User user = userRepository.findWithFilter("[id = '" + userId + "']").get(0);
        user.getReservationIds().remove(reservationId);
        userRepository.save(user);
    }
    //

    private User getUser(String username) {
        List<User> users = userRepository.findWithFilter("[username = '" + username + "']");
        if (users.isEmpty()) {
            throw new UserNotFoundException(username);
        }
        return users.get(0);
    }
}
