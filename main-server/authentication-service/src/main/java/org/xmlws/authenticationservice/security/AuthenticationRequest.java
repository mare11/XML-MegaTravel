package org.xmlws.authenticationservice.security;

import org.xmlws.authenticationservice.model.AuthorityEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest 
{
    private String username;
    private String password;
    private AuthorityEnum authority;
}