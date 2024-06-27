package com.example.ecommerce.controller;

import com.example.ecommerce.dto.userDetail.UpdateUserDetailDto;
import com.example.ecommerce.dto.userDetail.UserDetailDto;
import com.example.ecommerce.exception.EntityNotFoundException;
import com.example.ecommerce.service.UserDetailService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserDetailControllerTest extends BaseControllerTest {

    @MockBean
    private UserDetailService userDetailService;

    private final UUID id = UUID.fromString("8eafdf29-ef2e-4872-9a27-83bee1f3d92c");

    private final String jsonContent = "{ " +
            "\"id\": \"8eafdf29-ef2e-4872-9a27-83bee1f3d92c\", " +
            "\"fullName\": \"Tom\"" +
            "}";

    private final String USER_DETAIL_PATH_ID = "/users/user-details/" + id;

    private final UserDetailDto userDetailDto = new UserDetailDto(id, "Tom");

    @AfterEach
    public void teamDown() {
        verifyNoMoreInteractions(userDetailService);
    }

    @Test
    public void getUserDetailByValidIdReturnsExpectedResult() throws Exception {
        doReturn(userDetailDto).when(userDetailService).getUserDetail(id);

        mockMvc.perform(get(USER_DETAIL_PATH_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonContent));

        verify(userDetailService).getUserDetail(id);
    }

    @Test
    public void getUserDetailByInValidIdReturnsExpectedResult() throws Exception {
        doThrow(new EntityNotFoundException(id)).when(userDetailService).getUserDetail(id);

        mockMvc.perform(get(USER_DETAIL_PATH_ID))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.errorType").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Entity with UUID '" + id + "' not found"));

        verify(userDetailService).getUserDetail(id);
    }

    @Test
    public void updateUserDetailByValidIdReturnsExpectedResult() throws Exception {
        UpdateUserDetailDto updateUserDetailDto = new UpdateUserDetailDto("Yan");

        doReturn(userDetailDto).when(userDetailService).updateUserDetail(id, updateUserDetailDto);

        mockMvc.perform(put(USER_DETAIL_PATH_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserDetailDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonContent));

        verify(userDetailService).updateUserDetail(id, updateUserDetailDto);
    }

    @Test
    public void updateUserDetailByInValidIdReturnsExpectedResult() throws Exception {
        UpdateUserDetailDto updateUserDetailDto = new UpdateUserDetailDto("Yan");

        doThrow(new EntityNotFoundException(id)).when(userDetailService).updateUserDetail(id, updateUserDetailDto);

        mockMvc.perform(put(USER_DETAIL_PATH_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserDetailDto)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.errorType").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Entity with UUID '" + id + "' not found"));

        verify(userDetailService).updateUserDetail(id, updateUserDetailDto);
    }
}
