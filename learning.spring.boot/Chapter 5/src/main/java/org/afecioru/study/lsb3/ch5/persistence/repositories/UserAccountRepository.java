package org.afecioru.study.lsb3.ch5.persistence.repositories;

import org.afecioru.study.lsb3.ch5.persistence.entities.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserAccountRepository extends JpaRepository<UserAccountEntity, String> {
    UserAccountEntity findByUsername(String username);
}
