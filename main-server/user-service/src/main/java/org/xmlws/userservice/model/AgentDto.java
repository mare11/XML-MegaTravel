package org.xmlws.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentDto {

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    protected String adress;
    protected Integer bussinesID;

}
