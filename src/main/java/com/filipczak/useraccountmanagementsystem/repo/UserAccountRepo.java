package com.filipczak.useraccountmanagementsystem.repo;

import com.filipczak.useraccountmanagementsystem.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepo extends JpaRepository<UserAccount, Integer> {
}
