package com.filipczak.useraccountmanagementsystem.service;

import com.filipczak.useraccountmanagementsystem.exception.UserAccountNotFoundException;
import com.filipczak.useraccountmanagementsystem.mapper.UserAccountMapper;
import com.filipczak.useraccountmanagementsystem.model.UserAccount;
import com.filipczak.useraccountmanagementsystem.model.dto.UpdateUserAccountRequest;
import com.filipczak.useraccountmanagementsystem.model.dto.UserAccountDTO;
import com.filipczak.useraccountmanagementsystem.repo.UserAccountRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserAccountServiceTest {

    @InjectMocks
    private UserAccountServiceImpl userAccountService;

    @Mock
    private UserAccountRepo userAccountRepo;

    @Mock
    private UserAccountMapper userAccountMapper;

    @Mock
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUserAccount() {
        UserAccountDTO userAccountDTO = new UserAccountDTO();
        UserAccount userAccount = new UserAccount();
        userAccount.setId(1);
        userAccount.setEmail("test@example.com");
        when(userAccountMapper.mapToUserAccount(userAccountDTO)).thenReturn(userAccount);
        when(userAccountMapper.mapToUserAccountDto(userAccount)).thenReturn(userAccountDTO);

        UserAccountDTO result = userAccountService.createUserAccount(userAccountDTO);

        assertNotNull(result);
        verify(userAccountRepo, times(1)).save(userAccount);
        verify(emailService, times(1)).sendMailWithActivationLink(userAccount.getId(), userAccount.getEmail());
    }

    @Test
    void testGetUserAccount() {
        int id = 1;
        UserAccount userAccount = new UserAccount();
        UserAccountDTO userAccountDTO = new UserAccountDTO();
        when(userAccountRepo.findById(id)).thenReturn(Optional.of(userAccount));
        when(userAccountMapper.mapToUserAccountDto(userAccount)).thenReturn(userAccountDTO);

        UserAccountDTO result = userAccountService.getUserAccount(id);

        assertNotNull(result);
        verify(userAccountRepo, times(1)).findById(id);
    }

    @Test
    void testGetUserAccountNotFound() {
        int id = 1;
        when(userAccountRepo.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserAccountNotFoundException.class, () -> userAccountService.getUserAccount(id));
        verify(userAccountRepo, times(1)).findById(id);
    }

    @Test
    void testDeleteUserAccountById() {
        int id = 1;
        UserAccount userAccount = new UserAccount();
        when(userAccountRepo.findById(id)).thenReturn(Optional.of(userAccount));

        userAccountService.deleteUserAccountById(id);

        verify(userAccountRepo, times(1)).delete(userAccount);
    }

    @Test
    void testDeleteUserAccountByIdNotFound() {
        int id = 1;
        when(userAccountRepo.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserAccountNotFoundException.class, () -> userAccountService.deleteUserAccountById(id));
        verify(userAccountRepo, times(1)).findById(id);
    }

    @Test
    void testUpdateUserAccountById() {
        int id = 1;
        UpdateUserAccountRequest request = new UpdateUserAccountRequest();
        request.setGender("Male");
        request.setAge(25);
        UserAccount userAccount = new UserAccount();
        UserAccountDTO userAccountDTO = new UserAccountDTO();
        when(userAccountRepo.findById(id)).thenReturn(Optional.of(userAccount));
        when(userAccountMapper.mapToUserAccountDto(userAccount)).thenReturn(userAccountDTO);

        UserAccountDTO result = userAccountService.updateUserAccountById(request, id);

        assertNotNull(result);
        assertEquals("Male", userAccount.getGender());
        assertEquals(25, userAccount.getAge());
        verify(userAccountRepo, times(1)).save(userAccount);
    }

    @Test
    void testUpdateUserAccountByIdNotFound() {
        int id = 1;
        UpdateUserAccountRequest request = new UpdateUserAccountRequest();
        when(userAccountRepo.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserAccountNotFoundException.class, () -> userAccountService.updateUserAccountById(request, id));
        verify(userAccountRepo, times(1)).findById(id);
    }

    @Test
    void testActivateUserAccount() {
        int code = 1;
        UserAccount userAccount = new UserAccount();
        when(userAccountRepo.findById(code)).thenReturn(Optional.of(userAccount));

        String result = userAccountService.activateUserAccount(code);

        assertEquals("Your account has been activated successfully!", result);
        assertTrue(userAccount.isActivated());
        verify(userAccountRepo, times(1)).save(userAccount);
    }

    @Test
    void testActivateUserAccountNotFound() {
        int code = 1;
        when(userAccountRepo.findById(code)).thenReturn(Optional.empty());

        assertThrows(UserAccountNotFoundException.class, () -> userAccountService.activateUserAccount(code));
        verify(userAccountRepo, times(1)).findById(code);
    }
}
