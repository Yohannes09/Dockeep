package com.dockeep.authorization.repository;

import com.dockeep.authorization.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
