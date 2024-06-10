package com.filipczak.useraccountmanagementsystem.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserAccountDTO {
    @NotNull(message = "Username can't be null")
    @Pattern(regexp = "^[A-Za-z]\\w{5,29}$", message = "Invalid username. Valid username can only contain alphanumeric characters and underscores (_).")
    private String username;
    @NotNull(message = "Email can't be null")
    @Email(message = "Invalid mail address")
    private String email;
    @NotNull(message = "Gender can't be null")
    @Pattern(regexp = "(?:Male|Female|Other)$", message = "Incorrect gender. Valid gender must be: Male, Female or Other")
    private String gender;
    @NotNull(message = "Age can't be null")
    private Integer age;
    private LocalDateTime accountCreationTimestamp;
    private boolean isActivated;

}
