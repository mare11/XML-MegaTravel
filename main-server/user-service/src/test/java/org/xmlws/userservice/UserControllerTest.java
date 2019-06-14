package org.xmlws.userservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.xmlws.userservice.controller.UserController;
import org.xmlws.userservice.model.User;
import org.xmlws.userservice.model.UserDto;
import org.xmlws.userservice.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private ModelMapper modelMapper = new ModelMapper();

    private ObjectMapper objectMapper = new ObjectMapper();

    private User user1;

    private User user2;

    @Before
    public void init() {
        user1 = new User("joca", "123", "jovan@gmail.com", "Jovan", "Jovanovic", true, new ArrayList<>(), false, new ArrayList<>());
        user2 = new User("iva", "123", "ivan@gmail.com", "Ivan", "Ivanovic", true, new ArrayList<>(), false, new ArrayList<>());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<UserDto> usersDto = new ArrayList<>(Arrays.asList(modelMapper.map(user1, UserDto.class), modelMapper.map(user2, UserDto.class)));
        when(userService.findAllUsers()).thenReturn(usersDto);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());

        verify(userService).findAllUsers();
    }

    @Test
    public void testGetOneUser() throws Exception {
        when(userService.findOneUser(user1.getUsername())).thenReturn(modelMapper.map(user1, UserDto.class));

        mockMvc.perform(get("/users/{username}", user1.getUsername()))
                .andExpect(status().isOk());

        verify(userService).findOneUser(user1.getUsername());
    }

    @Test
    public void testUpdateUser() throws Exception {
        UserDto userDto = modelMapper.map(user2, UserDto.class);
        when(userService.updateUser(userDto)).thenReturn(userDto);

        mockMvc.perform(put("/users")
                .content(objectMapper.writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService).updateUser(userDto);
    }
}
