package com.cydeo.service.impl;

import com.sparkle.dto.RoleDTO;
import com.sparkle.entity.Role;
import com.sparkle.mapper.MapperUtil;
import com.sparkle.repository.RoleRepository;
import com.sparkle.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    RoleRepository roleRepository;
    @Mock
    MapperUtil mapperUtil;
    @InjectMocks
    RoleServiceImpl roleService;

    @ParameterizedTest
    void findById_Test(Long id){

        //Given
        Role role = new Role();

        when(roleRepository.findById(id)).thenReturn(Optional.of(role));
        when(mapperUtil.convert(role, RoleDTO.class)).thenReturn(RoleDTO.class);

        roleService.findById(id);

        verify(roleRepository.findById(id));
        verify(mapperUtil).convert(role, RoleDTO.class);
    }

}