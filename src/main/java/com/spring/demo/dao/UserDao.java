package com.spring.demo.dao;

import com.spring.demo.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserDao extends CrudRepository<User,Integer> {

    User getById(int id);

    User getByEmailAddress(String emailAddress);

    @Transactional
    void deleteById(int userId);

    @Transactional
    @Modifying
    @Query(value = "insert into token (token, userId, expirationTime) values (token, userId, now() + interval 1 DAY )", nativeQuery = true)
    void savePasswordToken(
            @Param(value = "token") String token,
            @Param(value = "userId") int userId
    );

    @Query(value = "select * from token where emailAddress=username",nativeQuery = true)
    String getTokenByUserName(@Param(value = "username") String username);
}
