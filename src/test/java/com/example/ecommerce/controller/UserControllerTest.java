package com.example.ecommerce.controller;

import com.example.ecommerce.dto.user.CreateUserDto;
import com.example.ecommerce.dto.user.UserDto;
import com.example.ecommerce.dto.userDetail.CreateUserDetailDto;
import com.example.ecommerce.exception.EntityByGivenNameExistException;
import com.example.ecommerce.exception.EntityNotFoundException;
import com.example.ecommerce.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.verifyNoMoreInteractions;

public class UserControllerTest extends BaseControllerTest {

    @MockBean
    private UserService userService;

    private final UUID id = UUID.fromString("8eafdf29-ef2e-4872-9a27-83bee1f3d92c");

    private final String jsonContent = "{ " +
            "\"id\": \"8eafdf29-ef2e-4872-9a27-83bee1f3d92c\", " +
            "\"username\": \"Tom\"" +
            "}";

    private final String USER_PATH = "/users";

    private final String USER_PATH_ID = "/users/" + id;

    private final UserDto userDto = new UserDto(id, "Tom");

    @AfterEach
    public void teamDown() {
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void getUsers() throws Exception {
        final String jsonContentList = "[" + jsonContent + "]";
        List<UserDto> userDtoList = List.of(userDto);

        when(userService.getUsers()).thenReturn(userDtoList);

        mockMvc.perform(get(USER_PATH))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonContentList));

        verify(userService).getUsers();
    }

    @Test
    public void getUserByValidIdReturnsExpectedResult() throws Exception {
        when(userService.getUser(id)).thenReturn(userDto);

        mockMvc.perform(get(USER_PATH_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonContent));
        verify(userService).getUser(id);
    }

    @Test
    public void getUserByInValidIdReturnsExpectedResult() throws Exception {
        doThrow(new EntityNotFoundException(id)).when(userService).getUser(id);

        mockMvc.perform(get(USER_PATH_ID))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.errorType").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Entity with UUID '" + id + "' not found"));

        verify(userService).getUser(id);
    }

    @Test
    public void deleteUserByValidIdReturnsExpectedResult() throws Exception {
        doNothing().when(userService).deleteUserById(id);

        mockMvc.perform(delete(USER_PATH_ID))
                .andExpect(status().isOk());

        verify(userService).deleteUserById(id);
    }

    @Test
    public void deleteUserByInValidIdReturnsExpectedResult() throws Exception {
        doThrow(new EntityNotFoundException(id)).when(userService).deleteUserById(id);

        mockMvc.perform(delete(USER_PATH_ID))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.errorType").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Entity with UUID '" + id + "' not found"));

        verify(userService).deleteUserById(id);
    }

    @Test
    public void createUserByValidNameReturnsExpectedResult() throws Exception {
        CreateUserDto createUserDto = new CreateUserDto("Tom", "123", null);

        doReturn(userDto).when(userService).createUser(createUserDto);

        mockMvc.perform(post(USER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserDto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", USER_PATH_ID))
                .andExpect(content().json(jsonContent));

        verify(userService).createUser(createUserDto);
    }

    @Test
    public void createUserByValidNameAndWithUserDetailReturnsExpectedResult() throws Exception {
        CreateUserDto createUserDto = new CreateUserDto("Tom", "123", new CreateUserDetailDto("Jon"));

        doReturn(userDto).when(userService).createUser(createUserDto);

        mockMvc.perform(post(USER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserDto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", USER_PATH_ID))
                .andExpect(content().json(jsonContent));

        verify(userService).createUser(createUserDto);
    }


    @Test
    public void createUserByInExistNameReturnsExpectedResult() throws Exception {
        CreateUserDto createUserDto = new CreateUserDto("Tom", "123", null);

        doThrow(new EntityByGivenNameExistException(createUserDto.getUsername())).when(userService)
                .createUser(createUserDto);

        mockMvc.perform(post(USER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserDto)))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.errorType").value("ENTITY_ALREADY_EXIST"))
                .andExpect(jsonPath("$.message").value("Entity with name '" + createUserDto.getUsername()
                        + "' already exist."));

        verify(userService).createUser(createUserDto);
    }
}
