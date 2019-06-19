package org.xmlws.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xmlws.userservice.service.AgentService;

@RestController
@RequestMapping(value = "/agents")
public class AgentController {

    @Autowired
    private AgentService agentService;

    @RequestMapping(method = RequestMethod.PUT, value = "/{agentId}/accommodations/{accommodationId}")
    public ResponseEntity addAccommodation(@PathVariable Long agentId, @PathVariable Long accommodationId) {
        agentService.addAccommodation(agentId, accommodationId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{agentId}/accommodations/{accommodationId}")
    public ResponseEntity removeAccommodation(@PathVariable Long agentId, @PathVariable Long accommodationId) {
        agentService.removeAccommodation(agentId, accommodationId);
        return new ResponseEntity(HttpStatus.OK);
    }

}
