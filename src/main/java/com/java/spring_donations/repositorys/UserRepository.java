package com.java.spring_donations.repositorys;

import com.java.spring_donations.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    List<User> findAll();

    User findUserByEmail(String email);

    User findUserByEmailAndPassword(String email,String password);

    @Query(value = "SELECT * from  user  WHERE email = ? and password = ? and role_id != 1 ",nativeQuery = true)
    User checkLoginAdmin(String email,String password);

    User findUserById(int id);

    User findUserByUserName(String userName);

    void deleteById(int id);


}
