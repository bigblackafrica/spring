package com.spring.demo.controller;

import com.spring.demo.entity.User;
import com.spring.demo.service.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    @NonNull
    private UserService userService;

    @RequestMapping(value = "/all",produces = "application/json",method = RequestMethod.GET)
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @RequestMapping(method = RequestMethod.POST,produces = "application/json")
    public User saveUser(@RequestBody User user){
        return userService.save(user);
    }
}
