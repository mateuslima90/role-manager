package io.mtkh.rolemanager.service;

import io.mtkh.rolemanager.builder.RoleDTOBuilder;
import io.mtkh.rolemanager.configuration.RoleMapper;
import io.mtkh.rolemanager.domain.Role;
import io.mtkh.rolemanager.domain.RoleDTO;
import io.mtkh.rolemanager.exception.RoleAlreadyExistsException;
import io.mtkh.rolemanager.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    private RoleMapper roleMapper = RoleMapper.getInstance();

    @InjectMocks
    private RoleService roleService;

    @Test
    public void whenRoleInformedThenItShouldBeCreated() {
        //give
        RoleDTO expectedRoleDTO = RoleDTOBuilder.builder().build().toDTO();
        Role expectedRole = roleMapper.toModel(expectedRoleDTO);

        //when
        when(roleRepository.findByRoleName(expectedRole.getRoleName())).thenReturn(Optional.empty());
        when(roleRepository.save(any(Role.class))).thenReturn(expectedRole);

        //then
        RoleDTO savedRole = roleService.createRole(expectedRoleDTO);

        assertThat(savedRole.getId(), is(equalTo(expectedRole.getId())));
        assertThat(savedRole.getRoleName(), is(equalTo(expectedRole.getRoleName())));
    }

    @Test
    public void whenRoleInformedThenAlreadyExist() {
        //give
        RoleDTO expectedRoleDTO = RoleDTOBuilder.builder().build().toDTO();
        Role duplicatedRole = roleMapper.toModel(expectedRoleDTO);

        //when
        when(roleRepository.findByRoleName(expectedRoleDTO.getRoleName())).thenReturn(Optional.of(duplicatedRole));

        //then
        assertThrows(RoleAlreadyExistsException.class, () -> roleService.createRole(expectedRoleDTO));
    }
}
