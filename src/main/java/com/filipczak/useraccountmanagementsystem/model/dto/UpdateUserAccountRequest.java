package com.filipczak.useraccountmanagementsystem.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdateUserAccountRequest {
    @Pattern(regexp = "(?:Male|Female|Other)$", message = "Incorrect gender. Valid gender must be: Male, Female or Other")
    @NotNull(message = "Gender can't be null")
    private String gender;
    @NotNull(message = "Age can't be null")
    private Integer age;
}
