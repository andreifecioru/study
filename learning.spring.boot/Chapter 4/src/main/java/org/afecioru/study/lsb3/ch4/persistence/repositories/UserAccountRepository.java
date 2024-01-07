package org.afecioru.study.lsb3.ch4.persistence.repositories;

import org.afecioru.study.lsb3.ch4.persistence.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    UserAccount findByUsername(String username);
}
