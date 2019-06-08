package org.xmlws.userservice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.xmlws.userservice.dto.UserDto;
import org.xmlws.userservice.model.User;
import org.xmlws.userservice.repository.UserRepository;
import org.xmlws.userservice.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(UserService.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private ModelMapper modelMapper = new ModelMapper();

    private User user1;

    private User user2;

    @Before
    public void init() {
        user1 = new User("joca", "123", "jovan@gmail.com", "Jovan", "Jovanovic", true, new ArrayList<>(), false);
        user2 = new User("iva", "123", "ivan@gmail.com", "Ivan", "Ivanovic", true, new ArrayList<>(), false);
    }

    @Test
    public void testFindAllUsers() {
        List<User> users = new ArrayList<>(Arrays.asList(user1, user2));
        List<UserDto> usersDto = new ArrayList<>(
                Arrays.asList(modelMapper.map(user1, UserDto.class), modelMapper.map(user2, UserDto.class)));

        when(userRepository.findAll()).thenReturn(users);

        List<UserDto> retUsers = userService.findAllUsers();

        Assert.assertEquals(retUsers, usersDto);
        verify(userRepository).findAll();

    }

    @Test
    public void testFindOneUser() {
        when(userRepository.findWithFilter("[username = '" + user1.getUsername() + "']")).thenReturn(Arrays.asList(user1));

        UserDto retUser = userService.findOneUser(user1.getUsername());

        Assert.assertEquals(retUser, modelMapper.map(user1, UserDto.class));
        verify(userRepository).findWithFilter("[username = '" + user1.getUsername() + "']");
    }

    @Test
    public void testUpdateUser() {
        UserDto userDto = modelMapper.map(user2, UserDto.class);
        when(userRepository.findWithFilter("[username = '" + user2.getUsername() + "']")).thenReturn(Arrays.asList(user2));
        when(userRepository.save(user2)).thenReturn(user2);

        UserDto updatedUser = userService.updateUser(userDto);

        Assert.assertEquals(updatedUser, userDto);
        verify(userRepository).save(user2);
    }

}
