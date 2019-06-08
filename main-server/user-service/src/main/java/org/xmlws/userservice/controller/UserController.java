package org.xmlws.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xmlws.userservice.dto.UserDto;
import org.xmlws.userservice.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = this.userService.findAllUsers();
        return new ResponseEntity<List<UserDto>>(users, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{username}")
    public ResponseEntity<UserDto> getOneUser(@PathVariable String username) {
        UserDto user = this.userService.findOneUser(username);
        return new ResponseEntity<UserDto>(user, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        UserDto user = this.userService.updateUser(userDto);
        return new ResponseEntity<UserDto>(user, HttpStatus.OK);
    }
}
