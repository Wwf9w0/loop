package com.loop.service.persistence.jpa.mysql.service;

import com.loop.service.persistence.jpa.mysql.entity.UserEntity;
import com.loop.service.persistence.jpa.mysql.entity.UserPreferences;
import com.loop.service.persistence.jpa.mysql.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserPersistenceService {

    private final UserRepository userRepository;

    public void save(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> findById(Long userId) {
        return Optional.ofNullable(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found!")));
    }

    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }

    public void updatePreferences(UserPreferences preferences, Long userId) {
        UserEntity user = findById(userId).get();
        UserPreferences userPreferences = user.getUserPreferences();
        userPreferences.setTheme(preferences.getTheme());
        userPreferences.setNotificationEnabled(preferences.getNotificationEnabled());
        userRepository.save(user);
    }
}
