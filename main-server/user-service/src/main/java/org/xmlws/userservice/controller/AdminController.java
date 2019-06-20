package org.xmlws.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xmlws.userservice.model.Administrator;
import org.xmlws.userservice.model.Agent;
import org.xmlws.userservice.model.AgentDto;
import org.xmlws.userservice.model.UserDto;
import org.xmlws.userservice.service.AdminService;
import org.xmlws.userservice.service.AgentService;
import org.xmlws.userservice.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AgentService agentService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Administrator>> getAllAdmins() {
        List<Administrator> admins = this.adminService.findAllAdmins();
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Administrator> addAdmin(@RequestBody Administrator administrator) {
        administrator = adminService.addAdmin(administrator);
        return new ResponseEntity<>(administrator, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Administrator> deleteAdmin(@RequestBody Administrator administrator) {
        adminService.deleteAdmin(administrator);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/agents", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AgentDto>> getAllAgents() {
        List<AgentDto> agents = this.agentService.findAllAgents();
        return new ResponseEntity<>(agents, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/agents", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AgentDto> addAgent(@RequestBody Agent agent) {
        AgentDto agentDto = agentService.addAgent(agent);
        return new ResponseEntity<>(agentDto, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = this.userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "users/enable", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> enableUser(@RequestBody UserDto userDto) {
        UserDto user = userService.enableUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "users/disable", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> disableUser(@RequestBody UserDto userDto) {
        UserDto user = userService.disableUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "users/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> deleteUser(@RequestBody UserDto userDto) {
        UserDto user = userService.deleteUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
