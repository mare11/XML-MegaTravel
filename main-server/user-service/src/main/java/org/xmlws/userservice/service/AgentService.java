package org.xmlws.userservice.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.xmlws.dataservice.catalog.CatalogRepository;
import org.xmlws.userservice.exceptions.UserAlreadyExistsException;
import org.xmlws.userservice.exceptions.UserNotFoundException;
import org.xmlws.userservice.model.*;
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

    public AgentDto addAgent(Agent agent) {

//        CALL TO AUTH SERVICE FOR UNIQUE USERNAME AND EMAIL CHECK AND PASSWORD HASHING
        UserUniquenessDto userUniquenessDto = new UserUniquenessDto(agent.getUsername(), agent.getPassword(), agent.getEmail());
        userUniquenessDto = webClientBuilder.build()
                .post()
                .uri("http://authentication-service/authentication/user")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(userUniquenessDto))
                .retrieve()
                .bodyToMono(UserUniquenessDto.class)
                .doOnError(throwable -> {
                    throw new UserAlreadyExistsException(agent.getUsername(), agent.getEmail());
                })
                .block();

        agent.setId(catalogRepository.getCatalogId(agentRepository.getRootElementName()));
        agent.setPassword(userUniquenessDto.getPassword());
        agent.getAuthority().add(new Authority(AuthorityEnum.ROLE_AGENT));
        return mapper.map(agentRepository.save(agent), AgentDto.class);

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
