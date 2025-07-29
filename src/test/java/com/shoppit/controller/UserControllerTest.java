package com.shoppit.controller;


import com.shoppit.dto.UserDTO;
import com.shoppit.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@WithMockUser(username = "admin", roles = "ADMIN")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void testSuccessfulGetUsers() throws Exception {
        UserDTO userDTO1 = createUserDTO("test_user1", "test_password1", "ROLE_USER");
        UserDTO userDTO2 = createUserDTO("test_user2", "test_password2", "ROLE_USER");
        when(userService.getAllUsers()).thenReturn(Arrays.asList(userDTO1, userDTO2));
        mockMvc.perform(get("/api/users"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json")
                );
    }
    @Test
    void testFailureGetUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(null);
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isNotFound());
    }

    private UserDTO createUserDTO(String username, String password, String role) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setPassword(password);
        userDTO.setRole(role);
        return userDTO;
    }
}
