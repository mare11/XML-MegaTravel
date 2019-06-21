package org.xmlws.authenticationservice.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserState {	
    private String accessToken;
    private String username;
    private Long id;
}