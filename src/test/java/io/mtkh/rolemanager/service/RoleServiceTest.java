package io.mtkh.rolemanager.service;

import io.mtkh.rolemanager.builder.RoleDTOBuilder;
import io.mtkh.rolemanager.builder.TransactionsBuilder;
import io.mtkh.rolemanager.configuration.RoleMapper;
import io.mtkh.rolemanager.domain.Role;
import io.mtkh.rolemanager.domain.RoleDTO;
import io.mtkh.rolemanager.domain.Transaction;
import io.mtkh.rolemanager.exception.RoleAlreadyExistsException;
import io.mtkh.rolemanager.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        //given
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
        //given
        RoleDTO expectedRoleDTO = RoleDTOBuilder.builder().build().toDTO();
        Role duplicatedRole = roleMapper.toModel(expectedRoleDTO);

        //when
        when(roleRepository.findByRoleName(expectedRoleDTO.getRoleName())).thenReturn(Optional.of(duplicatedRole));

        //then
        assertThrows(RoleAlreadyExistsException.class, () -> roleService.createRole(expectedRoleDTO));
    }

    @Test
    public void whenRoleInformedThenReturnNamedRole() {
        //given
        RoleDTO expectedRoleDTO = RoleDTOBuilder.builder().build().toDTO();
        Role expectedRole = roleMapper.toModel(expectedRoleDTO);

        //when
        when(roleRepository.findByRoleName(expectedRole.getRoleName())).thenReturn(Optional.of(expectedRole));

        //then
        RoleDTO retrievedRole = roleService.getRoleByRoleName(expectedRole.getRoleName());

        assertThat(retrievedRole.getId(), is(equalTo(expectedRole.getId())));
        assertThat(retrievedRole.getRoleName(), is(equalTo(expectedRole.getRoleName())));
        assertThat(retrievedRole.getDescription(), is(equalTo(expectedRole.getDescription())));
    }

    @Test
    public void whenRoleInformedThenReturnException() {
        //given
        RoleDTO expectedRoleDTO = RoleDTOBuilder.builder().build().toDTO();
        Role expectedRole = roleMapper.toModel(expectedRoleDTO);

        //when
        when(roleRepository.findByRoleName(expectedRole.getRoleName())).thenReturn(Optional.empty());

        //then
        assertThrows(Exception.class, () -> roleService.getRoleByRoleName(expectedRole.getRoleName()));
    }

    @Test
    public void whenAskedAllRolesThenReturnRoleList() {
        //given
        RoleDTO expectedRoleDTO = RoleDTOBuilder.builder().build().toDTO();
        Role expectedRole = roleMapper.toModel(expectedRoleDTO);

        //when
        when(roleRepository.findAll()).thenReturn(Arrays.asList(expectedRole));

        //then
        List<RoleDTO> roles = roleService.getAllRoles();

        assertThat(roles.size(), is(equalTo(1)));
    }

    @Test
    public void whenAskedAllRolesThenReturnEmptyList() {
        //given

        //when
        when(roleRepository.findAll()).thenReturn(Arrays.asList());

        //then
        List<RoleDTO> roles = roleService.getAllRoles();

        assertThat(roles.size(), is(equalTo(0)));
    }

    @Test
    public void whenUpdateValueInRoleReturnUpdatedRole() throws Exception {
        //given
        RoleDTO expectedRoleDTO = RoleDTOBuilder.builder().build().toDTO();
        expectedRoleDTO.setDescription("developer2");
        Role expectedRole = roleMapper.toModel(expectedRoleDTO);

        //when
        when(roleRepository.findByRoleName(expectedRole.getRoleName())).thenReturn(Optional.of(expectedRole));
        when(roleRepository.save(any(Role.class))).thenReturn(expectedRole);

        //then
        RoleDTO updatedRoleDTO = roleService.updateRole(expectedRoleDTO);

        assertThat(updatedRoleDTO.getId(), is(equalTo(expectedRole.getId())));
        assertThat(updatedRoleDTO.getRoleName(), is(equalTo(expectedRole.getRoleName())));
        assertThat(updatedRoleDTO.getDescription(), is(equalTo(expectedRole.getDescription())));
    }

    @Test
    public void whenUpdateValueInRoleReturnException() throws Exception {
        //given
        RoleDTO expectedRoleDTO = RoleDTOBuilder.builder().build().toDTO();
        expectedRoleDTO.setDescription("developer2");
        Role expectedRole = roleMapper.toModel(expectedRoleDTO);

        //when
        when(roleRepository.findByRoleName(expectedRole.getRoleName())).thenReturn(Optional.empty());

        //then
        assertThrows(Exception.class, () -> roleService.updateRole(expectedRoleDTO));
    }

    @Test
    public void whenAddTransactionInRoleReturnRoleWithTransactions() {
        //given
        RoleDTO expectedRoleDTO = RoleDTOBuilder.builder().build().toDTO();
        Role expectedRole = roleMapper.toModel(expectedRoleDTO);
        Transaction t1 = TransactionsBuilder.builder().build().toModel();
        Set<Transaction> transactions = Stream.of(t1).collect(Collectors.toSet());
        expectedRole.setTransactions(transactions);

        //when
        when(roleRepository.findByRoleName(expectedRole.getRoleName())).thenReturn(Optional.of(expectedRole));
        when(roleRepository.save(any(Role.class))).thenReturn(expectedRole);

        //then
        Role updatedRoleDTO = roleService.addTransactionInRoleByRoleName(t1, expectedRoleDTO.getRoleName());

        assertThat(updatedRoleDTO.getId(), is(equalTo(expectedRole.getId())));
        assertThat(updatedRoleDTO.getRoleName(), is(equalTo(expectedRole.getRoleName())));
        assertThat(updatedRoleDTO.getDescription(), is(equalTo(expectedRole.getDescription())));
        assertThat(updatedRoleDTO.getTransactions().size(), is(equalTo(1)));
    }

    @Test
    public void whenAddTransactionInRoleReturnException() {
        //given
        RoleDTO expectedRoleDTO = RoleDTOBuilder.builder().build().toDTO();
        Role expectedRole = roleMapper.toModel(expectedRoleDTO);
        Transaction t1 = TransactionsBuilder.builder().build().toModel();
        Set<Transaction> transactions = Stream.of(t1).collect(Collectors.toSet());
        expectedRole.setTransactions(transactions);

        //when
        when(roleRepository.findByRoleName(expectedRole.getRoleName())).thenReturn(Optional.empty());

        //then
        assertThrows(Exception.class, () -> roleService.updateRole(expectedRoleDTO));
    }

    @Test
    public void whenDeleteTransactionInRoleReturnRoleWithTransactions() {
        //given
        RoleDTO expectedRoleDTO = RoleDTOBuilder.builder().build().toDTO();
        Role expectedRole = roleMapper.toModel(expectedRoleDTO);
        Transaction t1 = TransactionsBuilder.builder().build().toModel();
        Set<Transaction> transactions = Stream.of(t1).collect(Collectors.toSet());
        expectedRole.setTransactions(transactions);

        //when
        when(roleRepository.findByRoleName(expectedRole.getRoleName())).thenReturn(Optional.of(expectedRole));
        when(roleRepository.save(any(Role.class))).thenReturn(expectedRole);

        //then
        RoleDTO updatedRoleDTO = roleService.deleteTransactionInRole(t1.getTransactionName(), expectedRoleDTO.getRoleName());

        assertThat(updatedRoleDTO.getTransactions().size(), is(equalTo(0)));
    }

    @Test
    public void whenDeleteTransactionInRoleReturnException() {
        //given
        RoleDTO expectedRoleDTO = RoleDTOBuilder.builder().build().toDTO();
        Role expectedRole = roleMapper.toModel(expectedRoleDTO);
        Transaction t1 = TransactionsBuilder.builder().build().toModel();
        Set<Transaction> transactions = Stream.of(t1).collect(Collectors.toSet());
        expectedRole.setTransactions(transactions);

        //when
        when(roleRepository.findByRoleName(expectedRole.getRoleName())).thenReturn(Optional.empty());

        //then
        assertThrows(Exception.class, () -> roleService.updateRole(expectedRoleDTO));
    }
 }
