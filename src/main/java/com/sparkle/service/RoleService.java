package com.sparkle.service;

import com.sparkle.dto.RoleDTO;

import java.util.List;

public interface RoleService {

    RoleDTO findById(Long id);
    List<RoleDTO> listRoles();
}
