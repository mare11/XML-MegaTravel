package org.xmlws.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xmlws.userservice.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

}
