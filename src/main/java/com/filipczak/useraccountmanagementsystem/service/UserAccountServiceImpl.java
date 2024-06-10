package com.filipczak.useraccountmanagementsystem.service;

import com.filipczak.useraccountmanagementsystem.exception.UserAccountNotFoundException;
import com.filipczak.useraccountmanagementsystem.mapper.UserAccountMapper;
import com.filipczak.useraccountmanagementsystem.model.UserAccount;
import com.filipczak.useraccountmanagementsystem.model.dto.UpdateUserAccountRequest;
import com.filipczak.useraccountmanagementsystem.model.dto.UserAccountDTO;
import com.filipczak.useraccountmanagementsystem.repo.UserAccountRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepo userAccountRepo;
    private final UserAccountMapper userAccountMapper;
    private final EmailService emailService;

    @Override
    public UserAccountDTO createUserAccount(UserAccountDTO userAccountDTO) {
        UserAccount userAccount = userAccountMapper.mapToUserAccount(userAccountDTO);
        userAccount.setAccountCreationTimestamp(LocalDateTime.now());
        userAccountRepo.save(userAccount);
        emailService.sendMailWithActivationLink(userAccount.getId(), userAccount.getEmail());
        return userAccountMapper.mapToUserAccountDto(userAccount);
    }

    @Override
    public UserAccountDTO getUserAccount(int id) {
        UserAccount userAccount = userAccountRepo.findById(id)
                .orElseThrow(
                        () -> new UserAccountNotFoundException("Account with given id could not be found")
                );
        return userAccountMapper.mapToUserAccountDto(userAccount);
    }

    @Override
    public void deleteUserAccountById(int id) {
        UserAccount userAccount = userAccountRepo.findById(id)
                .orElseThrow(
                        () -> new UserAccountNotFoundException("Account with given id could not be found")
                );
        userAccountRepo.delete(userAccount);
    }

    @Override
    public UserAccountDTO updateUserAccountById(UpdateUserAccountRequest request, int id) {
        UserAccount userAccount = userAccountRepo.findById(id)
                .orElseThrow(
                        () -> new UserAccountNotFoundException("Account with given id could not be found")
                );
        userAccount.setGender(request.getGender());
        userAccount.setAge(request.getAge());
        userAccountRepo.save(userAccount);
        return userAccountMapper.mapToUserAccountDto(userAccount);
    }

    /*
    Normally, I would implement account activation via e-mail using a generated token with an expiration date that would be saved in the database,
     but in this case, for a simpler implementation, I chose the "token" in the form of User ID
     */
    @Override
    public String activateUserAccount(int code) {
        UserAccount userAccount = userAccountRepo.findById(code)
                .orElseThrow(
                        () -> new UserAccountNotFoundException("Account with given id could not be found")
                );
        userAccount.setActivated(true);
        userAccountRepo.save(userAccount);
        return "Your account has been activated successfully!";
    }
}
