package org.xmlws.authenticationservice.repository;

import org.springframework.stereotype.Repository;
import org.xmlws.authenticationservice.model.Agent;
import org.xmlws.dataservice.config.exist.ExistXQJRepository;
import org.xmlws.dataservice.entity.info.XMLEntityInformation;

@Repository
public class AgentRepository extends ExistXQJRepository<Agent> {

	public AgentRepository() {
		super(new XMLEntityInformation<Agent>(Agent.class));
	}
}