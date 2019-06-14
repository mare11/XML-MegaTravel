package org.xmlws.gatewayservice.security;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserState {	
    private String accessToken;
    private String username;
    private List<String> authorities;
}