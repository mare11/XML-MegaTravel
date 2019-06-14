package org.xmlws.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmlws.dataservice.catalog.CatalogRepository;
import org.xmlws.userservice.model.Agent;
import org.xmlws.userservice.model.Authority;
import org.xmlws.userservice.repository.AgentRepository;

@Service
public class AgentService {

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private CatalogRepository catalogRepository;

    public Agent addAgent(Agent agent) {
        agent.setId(catalogRepository.getCatalogId(agentRepository.getRootElementName()));
        agent.getAuthority().add(new Authority("ROLE_AGENT"));
        return agentRepository.save(agent);
    }
}
