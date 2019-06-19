package org.xmlws.userservice.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.xmlws.dataservice.catalog.CatalogRepository;
import org.xmlws.userservice.exceptions.UserAlreadyExistsException;
import org.xmlws.userservice.exceptions.UserNotFoundException;
import org.xmlws.userservice.model.Agent;
import org.xmlws.userservice.model.AgentDto;
import org.xmlws.userservice.model.Authority;
import org.xmlws.userservice.model.AuthorityEnum;
import org.xmlws.userservice.repository.AgentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgentService {

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    private ModelMapper mapper = new ModelMapper();

    public List<AgentDto> findAllAgents() {
        List<Agent> agents = agentRepository.findAll();

        return agents.stream().map(agent ->
                mapper.map(agent, AgentDto.class)
        ).collect(Collectors.toList());
    }

    public Agent addAgent(Agent agent) {

//        CALLS TO AUTH SERVICE FOR UNIQUE USERNAME AND EMAIL CHECK
        ClientResponse usernameClientResponse = webClientBuilder.build()
                .get()
                .uri("http://authentication-service/authentication/username/" + agent.getUsername())
                .exchange()
                .block();

        if (usernameClientResponse.statusCode().equals(HttpStatus.OK)) {
            ClientResponse emailClientResponse = webClientBuilder.build()
                    .get()
                    .uri("http://authentication-service/authentication/email/" + agent.getEmail())
                    .exchange()
                    .block();

            if (emailClientResponse.statusCode().equals(HttpStatus.OK)) {
                agent.setId(catalogRepository.getCatalogId(agentRepository.getRootElementName()));
                agent.getAuthority().add(new Authority(AuthorityEnum.ROLE_AGENT));
                return agentRepository.save(agent);
            }
        }

        throw new UserAlreadyExistsException(agent.getUsername(), agent.getEmail());
    }

    public void addAccommodation(Long agentId, Long accommodationId) {
        Agent agent = getAgent(agentId);
        agent.getAccommodationId().add(accommodationId);
        agentRepository.save(agent);
    }

    public void removeAccommodation(Long agentId, Long accommodationId) {
        Agent agent = getAgent(agentId);
        agent.getAccommodationId().remove(accommodationId);
        agentRepository.save(agent);
    }

    private Agent getAgent(Long agentId) {
        List<Agent> agents = agentRepository.findWithFilter("[id = '" + agentId + "']");
        if (agents.isEmpty()) {
            throw new UserNotFoundException();
        }
        return agents.get(0);
    }
}
