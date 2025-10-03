package com.codecrafters.taskhubcore.service;

import com.codecrafters.taskhubcore.exceptions.DataIntegrationViolatedException;
import com.codecrafters.taskhubcore.controller.user.dto.UserDTO;
import com.codecrafters.taskhubcore.controller.user.mapper.UserWebMapper;
import com.codecrafters.taskhubcore.model.UserEntity;
import com.codecrafters.taskhubcore.repositories.UserRepository;
import com.codecrafters.taskhubcore.enums.RuntimeErrorEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserWebMapper mapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create a new User when the e-mail doesn't exit in database")
    void saveCase1() {
        UserDTO userDTO = UserDTO.builder()
                .id("1")
                .name("Joao")
                .email("email@gmail.com")
                .phone("5511948464245")
                .imageUrl("https://www.imageurl.com/imageexample.jpg")
                .password("passowrd")
                .build();

        UserEntity userEntity = UserEntity.builder()
                .id("1")
                .name("Joao")
                .email("email@gmail.com")
                .phone("5511948464245")
                .imageUrl("https://www.imageurl.com/imageexample.jpg")
                .password("passowrd")
                .build();

        when(userRepository.findByEmail(userDTO.email())).thenReturn(Optional.empty());
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(mapper.toEntity(userDTO)).thenReturn(userEntity);
        when(mapper.toDTO(userEntity)).thenReturn(userDTO);

        UserDTO userSaved = userService.save(userDTO);
        Assertions.assertEquals(userDTO, userSaved);
    }

    @Test
    @DisplayName("Should throw Exception when the e-mail exits in database")
    void saveCase2() {
        UserDTO userDTO = UserDTO.builder()
                .id("1")
                .name("Joao")
                .email("email@gmail.com")
                .phone("5511948464245")
                .imageUrl("https://www.imageurl.com/imageexample.jpg")
                .password("passowrd")
                .build();

        when(userRepository.findByEmail(userDTO.email())).thenThrow(new DataIntegrationViolatedException(RuntimeErrorEnum.ERR0004));

        Assertions.assertThrows(DataIntegrationViolatedException.class, () -> userService.save(userDTO));
    }
}