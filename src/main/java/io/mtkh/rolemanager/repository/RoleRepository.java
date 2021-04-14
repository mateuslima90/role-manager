package io.mtkh.rolemanager.repository;

import io.mtkh.rolemanager.domain.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {

    Optional<Role> findByRoleName(String roleName);
}
