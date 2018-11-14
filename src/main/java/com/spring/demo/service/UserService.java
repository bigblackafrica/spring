package com.spring.demo.service;

import com.spring.demo.dao.UserDao;
import com.spring.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public List<User> getAllUsers(){
        return (List<User>) userDao.findAll();
    }

    public User getById(int id){
        return userDao.getById(id);
    }

    public User saveOrUpdate(User user){
         return userDao.save(user);

    }

    public void deleteById(int id){
        userDao.deleteById(id);
    }
}
