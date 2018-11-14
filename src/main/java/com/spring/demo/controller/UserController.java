package com.spring.demo.controller;

import com.spring.demo.entity.User;
import com.spring.demo.service.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/id/{id}")
    public User getById(@PathVariable int id){
        return userService.getById(id);
    }

    @RequestMapping(method = RequestMethod.POST,produces = "application/json")
    public User saveOrUpdate(@RequestBody User user){
        return userService.saveOrUpdate(user);
    }

    @RequestMapping(value = "/id/{id}",method = RequestMethod.DELETE)
    public void deleteById(@PathVariable int id){
              userService.deleteById(id);
    }

}
