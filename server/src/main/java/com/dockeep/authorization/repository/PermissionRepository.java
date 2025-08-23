package com.dockeep.authorization.repository;

import com.dockeep.authorization.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
