package com.spring.demo.dao;

import com.spring.demo.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserDao extends CrudRepository<User,Integer> {

    User getById(int id);

    @Transactional
    void deleteById(int userId);

}
