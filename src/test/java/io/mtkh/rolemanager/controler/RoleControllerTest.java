package io.mtkh.rolemanager.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mtkh.rolemanager.builder.RoleDTOBuilder;
import io.mtkh.rolemanager.controller.RoleController;
import io.mtkh.rolemanager.domain.RoleDTO;
import io.mtkh.rolemanager.service.RoleService;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class RoleControllerTest {

    @Mock
    private RoleService roleService;

    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @InjectMocks
    private RoleController roleController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(roleController)
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    public void whenPOSTIsCalledThenABeerIsCreated() throws Exception {
        // given
        RoleDTO roleDTO = RoleDTOBuilder.builder().build().toDTO();

        // when
        when(roleService.createRole(any(RoleDTO.class))).thenReturn(roleDTO);

        // then
        mockMvc.perform(post("/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(roleDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.roleName", Is.is(roleDTO.getRoleName())))
                .andExpect(jsonPath("$.description", Is.is(roleDTO.getDescription())));
    }

    @Test
    public void whenGETIsCalledWithValidNameThenOkStatusIsReturned() throws Exception {
        // given
        RoleDTO roleDTO = RoleDTOBuilder.builder().build().toDTO();

        // when
        when(roleService.getRoleByRoleName(roleDTO.getRoleName())).thenReturn(roleDTO);

        // then
        mockMvc.perform(get("/role/" + roleDTO.getRoleName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.roleName", Is.is(roleDTO.getRoleName())))
                .andExpect(jsonPath("$.description", Is.is(roleDTO.getDescription())));
    }

    @Test
    public void whenGETAllIsCalledWithValidNameThenOkStatusIsReturned() throws Exception {
        // given
        RoleDTO roleDTO = RoleDTOBuilder.builder().build().toDTO();

        // when
        when(roleService.getAllRoles()).thenReturn(Arrays.asList(roleDTO));

        // then
        mockMvc.perform(get("/roles")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].roleName", Is.is(roleDTO.getRoleName())))
                .andExpect(jsonPath("$[0].description", Is.is(roleDTO.getDescription())));
    }
}
