package com.filipczak.useraccountmanagementsystem.service;

import com.filipczak.useraccountmanagementsystem.model.dto.UpdateUserAccountRequest;
import com.filipczak.useraccountmanagementsystem.model.dto.UserAccountDTO;

public interface UserAccountService {

    UserAccountDTO createUserAccount(UserAccountDTO userAccountDTO);
    UserAccountDTO getUserAccount(int id);
    void deleteUserAccountById(int id);
    UserAccountDTO updateUserAccountById(UpdateUserAccountRequest request, int id);
    String activateUserAccount(int code);
}
