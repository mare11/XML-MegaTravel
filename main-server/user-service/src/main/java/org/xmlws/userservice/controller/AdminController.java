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
import org.xmlws.userservice.service.AdminService;

@RestController
@RequestMapping(value = "/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Administrator> addAdmin(@RequestBody Administrator administrator) {
        administrator = adminService.addAdmin(administrator);
        return new ResponseEntity<Administrator>(administrator, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteAdmin(@RequestBody Administrator administrator) {
        adminService.deleteAdmin(administrator);
        return new ResponseEntity<Administrator>(HttpStatus.OK);
    }
}
