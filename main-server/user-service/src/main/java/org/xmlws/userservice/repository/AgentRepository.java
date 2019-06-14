package org.xmlws.userservice.repository;

import org.springframework.stereotype.Repository;
import org.xmlws.dataservice.config.exist.ExistXQJRepository;
import org.xmlws.dataservice.entity.info.XMLEntityInformation;
import org.xmlws.userservice.model.Agent;

@Repository
public class AgentRepository extends ExistXQJRepository<Agent> {

    public AgentRepository() {
        super(new XMLEntityInformation<Agent>(Agent.class));
    }
}
