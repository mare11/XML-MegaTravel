package org.xmlws.authenticationservice.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xmlws.authenticationservice.dto.TokenDataDto;
import org.xmlws.authenticationservice.dto.UserUniquenessDto;
import org.xmlws.authenticationservice.exceptions.UsernameNullPointerException;
import org.xmlws.authenticationservice.exceptions.WrongAuthorityException;
import org.xmlws.authenticationservice.model.Authority;
import org.xmlws.authenticationservice.model.UserEntity;
import org.xmlws.authenticationservice.security.AuthenticationRequest;
import org.xmlws.authenticationservice.security.TokenUtility;
import org.xmlws.authenticationservice.security.UserState;
import org.xmlws.authenticationservice.service.UserService;

@RestController
@RequestMapping(value = "/authentication")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenUtility tokenUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "/{token}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserState validateToken(@PathVariable String token)
            throws AuthenticationException, Exception {

        String username = tokenUtility.getUsernameFromToken(token);
        UserEntity user = (UserEntity) this.userService.loadUserByUsername(username);

        if (user == null) {
            return null;
        }

        List<String> authorities = tokenUtility.getAuthoritiesFromToken(token);
        if (!authorities.containsAll(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))) {
            return null;
        }

        return new UserState(token, username, user.getId(), authorities);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserState> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest)
            throws AuthenticationException, IOException {

        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                authenticationRequest.getPassword());
		
        //Try to authenticate user
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        if (!authentication.getAuthorities().contains(new Authority(authenticationRequest.getAuthority()))) {
            throw new WrongAuthorityException(authenticationRequest.getUsername(), authenticationRequest.getAuthority());
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Extract username and authorities and generate token if user is authenticated successfully
        String username = authentication.getName();
        List<String> authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        String token = tokenUtility.generateToken(username, authorities);

        //Return token, username and authorities to client side
        UserState userState = new UserState(token, username, ((UserEntity) authentication.getPrincipal()).getId(), authorities);
        return new ResponseEntity<UserState>(userState, HttpStatus.OK);
    }

    @RequestMapping(value = "/token/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String generateToken(@PathVariable String username) {

        return this.tokenUtility.generateToken(username, this.userService.loadUserByUsername(username)
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<UserUniquenessDto> checkUserUniquenessAndHashPassword(@RequestBody UserUniquenessDto uniquenessDto) throws UsernameNullPointerException {
        if (this.userService.checkUserUniqueness(uniquenessDto)) {
            return new ResponseEntity<>(this.userService.generatePassword(uniquenessDto), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/token/data/{token}", method = RequestMethod.GET)
    public ResponseEntity<TokenDataDto> getDateFromToken(@PathVariable String token) throws Exception {
        TokenDataDto tokenDataDto = new TokenDataDto(this.tokenUtility.getUsernameFromToken(token), this.tokenUtility.getExpirationDateFromToken(token));
        return new ResponseEntity<>(tokenDataDto, HttpStatus.OK);
    }
}