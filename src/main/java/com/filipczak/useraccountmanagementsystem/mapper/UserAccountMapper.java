package com.filipczak.useraccountmanagementsystem.mapper;

import com.filipczak.useraccountmanagementsystem.model.UserAccount;
import com.filipczak.useraccountmanagementsystem.model.dto.UserAccountDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAccountMapper {

    UserAccountDTO mapToUserAccountDto(UserAccount userAccount);
    UserAccount mapToUserAccount(UserAccountDTO userAccountDTO);
}
