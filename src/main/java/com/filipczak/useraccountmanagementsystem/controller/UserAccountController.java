package com.filipczak.useraccountmanagementsystem.controller;

import com.filipczak.useraccountmanagementsystem.model.dto.UpdateUserAccountRequest;
import com.filipczak.useraccountmanagementsystem.model.dto.UserAccountDTO;
import com.filipczak.useraccountmanagementsystem.service.UserAccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserAccountController {
    private final UserAccountService userAccountService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserAccountDTO createUserAccount(@Valid @RequestBody UserAccountDTO userAccountDTO) {
        return userAccountService.createUserAccount(userAccountDTO);
    }

    @GetMapping("/{id}")
    public UserAccountDTO getUserAccountById(@PathVariable int id) {
        return userAccountService.getUserAccount(id);
    }

    @PutMapping("/{id}")
    public UserAccountDTO updateUserAccountById(@Valid @RequestBody UpdateUserAccountRequest request, @PathVariable int id) {
        return userAccountService.updateUserAccountById(request, id);
    }

    @DeleteMapping("/{id}")
    public void deleteUserAccountById(@PathVariable int id) {
        userAccountService.deleteUserAccountById(id);
    }

    @GetMapping("/confirm-account")
    public String activateUserAccount(@RequestParam int code) {
        return userAccountService.activateUserAccount(code);
    }
}
