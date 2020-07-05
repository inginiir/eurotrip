package com.kalita.projects.service;

import com.kalita.projects.domain.Role;
import com.kalita.projects.domain.User;
import com.kalita.projects.repos.UserRepo;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

class UserServiceTest {

    private UserRepo userRepo = mock(UserRepo.class);
    private MailSender mailService = mock(MailSender.class);
    private PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private UserService userService = new UserService(userRepo, mailService, passwordEncoder);
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void loadUserByUsername() {
        user.setUsername("Pavel");
        userService.addUser(user);

        Mockito.doReturn(new User("Pavel"))
                .when(userRepo)
                .findByUsername("Pavel");
        assertEquals(user, userService.loadUserByUsername("Pavel"));
    }

    @Test
    void loadUserByUsernameFail() {
        Mockito.doReturn(null)
                .when(userRepo)
                .findByUsername("Pavel");
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("Pavel"));
    }

    @Test
    void addUser() {
        user.setEmail("email@email.email");

        boolean isUserCreated = userService.addUser(user);

        assertTrue(isUserCreated);
        assertNotNull(user.getActivationCode());
        assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));

        Mockito.verify(userRepo, Mockito.times(1)).save(user);
        Mockito.verify(mailService, Mockito.times(1))
                .send(
                        ArgumentMatchers.eq(user.getEmail()),
                        ArgumentMatchers.eq("Activation code"),
                        ArgumentMatchers.contains("Welcome to EuroTrip!"));
    }

    @Test
    void addUserFailed() {
        user.setUsername("Pavel");
        user.setEmail("email@email.email");

        Mockito.doReturn(new User())
                .when(userRepo)
                .findByUsername("Pavel");

        boolean isUserCreated = userService.addUser(user);

        assertFalse(isUserCreated);

        Mockito.verify(userRepo, Mockito.never()).save(any(User.class));
        Mockito.verify(mailService, Mockito.never())
                .send(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString());
    }

    @Test
    void activateUser() {
        User user = new User();
        user.setActivationCode("activate");

        Mockito.doReturn(user)
                .when(userRepo)
                .findByActivationCode("activate");

        boolean isUserActivated = userService.activateUser("activate");

        assertTrue(isUserActivated);
        assertNull(user.getActivationCode());
        Mockito.verify(userRepo, Mockito.times(1)).save(user);
    }

    @Test
    void activateUserFail() {
        boolean isUserActivated = userService.activateUser("activate");

        assertFalse(isUserActivated);
        Mockito.verify(userRepo, Mockito.never()).save(any(User.class));

    }

    @Test
    void findAll() {
        userService.findAll();
        Mockito.verify(userRepo, Mockito.times(1)).findAll();
    }

    @Test
    void shouldUpdateUsernameRolesAndSaveUser() {
        Map<String, String> form = new HashMap<>();
        form.put("USER", "USER");
        form.put("ADMIN", "ADMIN");
        form.put("GUEST", "GUEST");
        user.setUsername("Ivan");
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        user.setRoles(roles);
        userService.saveUser(user, "Pavel", form);
        assertEquals("Pavel", user.getUsername());
        roles.add(Role.ADMIN);
        assertEquals(roles, user.getRoles());
        Mockito.verify(userRepo, Mockito.times(1)).save(user);
    }

    @Test
    void updateProfileEmail() {
        user.setEmail("mail@mail.ru");
        userService.updateProfile(user, "123", "email@email.com");
        assertEquals("email@email.com", user.getEmail());
        Mockito.verify(userRepo, Mockito.times(1)).save(user);
        Mockito.verify(mailService, Mockito.times(1))
                .send(
                        ArgumentMatchers.eq(user.getEmail()),
                        ArgumentMatchers.eq("Activation code"),
                        ArgumentMatchers.contains("Welcome to EuroTrip!"));
    }
    @Test
    void updateProfilePassword() {
        user.setEmail("email@email.com");
        user.setPassword(passwordEncoder.encode("123"));
        userService.updateProfile(user, "456", "email@email.com");
        assertEquals(passwordEncoder.encode("456"), user.getPassword());
        Mockito.verify(userRepo, Mockito.times(1)).save(user);
        Mockito.verify(mailService, Mockito.never())
                .send(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString());
    }
}