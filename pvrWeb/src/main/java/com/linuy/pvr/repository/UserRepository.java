package com.linuy.pvr.repository;

import com.linuy.pvr.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

/**
 * @author LongTeng
 * @date 2021/02/09
 **/
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

     List<User> findByUsernameAndPassword(String username, String password);

     List<User> findByUsername(String username);
}
