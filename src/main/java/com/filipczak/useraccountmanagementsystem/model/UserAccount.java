package com.filipczak.useraccountmanagementsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String username;
    private String email;
    private String gender;
    private Integer age;
    @Column(name = "ACCOUNT_CREATION_TIMESTAMP")
    private LocalDateTime accountCreationTimestamp;
    private boolean isActivated = false;
}
