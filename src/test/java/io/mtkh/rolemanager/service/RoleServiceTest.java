package io.mtkh.rolemanager.service;

import io.mtkh.rolemanager.builder.RoleDTOBuilder;
import io.mtkh.rolemanager.configuration.RoleMapper;
import io.mtkh.rolemanager.domain.Role;
import io.mtkh.rolemanager.domain.RoleDTO;
import io.mtkh.rolemanager.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.lenient;
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
        //Role expectedRole = new Role("123", "developer", "developer");
        Role expectedRole = roleMapper.toModel(expectedRoleDTO);
        //RoleDTO expectedRoleDTO = roleMapper.toDTO(expectedRole);

        //when
        //when(roleRepository.findByRoleName(expectedRole.getRoleName())).thenReturn(Optional.empty());
        when(roleRepository.save(expectedRole)).thenReturn(expectedRole);

        //then
        RoleDTO savedRole = roleService.createRole(expectedRoleDTO);
        //Role savedRole = roleService.createRole2(expectedRole);
        //RoleDTO savedRole = roleService.createRole3(expectedRole);

        assertThat(savedRole.getId(), is(equalTo(expectedRole.getId())));
        assertThat(savedRole.getRoleName(), is(equalTo(expectedRole.getRoleName())));

    }

}
