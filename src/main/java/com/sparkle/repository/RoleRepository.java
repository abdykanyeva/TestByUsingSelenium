package com.sparkle.repository;

import com.sparkle.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

List<Role> findRoleByDescriptionIsNot(String description);

    List<Role>findAllByDescription(String description);
}
