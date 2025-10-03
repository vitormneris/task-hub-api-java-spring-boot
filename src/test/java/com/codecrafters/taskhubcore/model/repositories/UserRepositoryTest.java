package com.codecrafters.taskhubcore.model.repositories;

import com.codecrafters.taskhubcore.model.UserEntity;
import com.codecrafters.taskhubcore.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Should get User successfully from DB")
    void findByEmailCase1() {
        String email = "test@example.com";
        UserEntity entity = UserEntity.builder()
                .name("Joao")
                .email(email)
                .phone("119948464324")
                .password("12345678")
                .imageUrl("https://example.com/image.jpg")
                .build();

        entityManager.persist(entity);
        Optional<UserEntity> result = userRepository.findByEmail(email);
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get User from DB when User not exist")
    void findByEmailCase2() {
        String email = "test@example.com";

        Optional<UserEntity> result = userRepository.findByEmail(email);

        assertThat(result.isEmpty()).isTrue();
    }
}