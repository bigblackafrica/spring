package com.spring.demo.controller;

import com.spring.demo.dto.LoginRequest;
import com.spring.demo.entity.User;
import com.spring.demo.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    @NonNull
    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/authenticate",method = RequestMethod.POST,produces = "application/json")
    public ResponseEntity<Map> authenticate(@RequestBody LoginRequest request){
        Map<String, Object> response = new LinkedHashMap<>();

        User existingUser;
        try {
            existingUser = userService.getUserByUsername(request.getEmailAddress());
            if (existingUser == null || !existingUser.getPasswordHash().equals(hash(request.getPassword()))) {
                throw new RuntimeException("Incorrect username or password");
            }
        } catch (Exception e) {
            throw new RuntimeException("Incorrect username or password");
        }
        String token = Jwts.builder()
                .setSubject(request.getEmailAddress())
                .setExpiration(new Date(System.currentTimeMillis() +864_000_000))
                .signWith(SignatureAlgorithm.HS512,"SuperSecretStretchSecret".getBytes() )
                .compact();
        response.put("token", token);
        response.put("user", existingUser);
        userService.savePasswordToken(token,existingUser.getId());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @RequestMapping(value = "/all",produces = "application/json",method = RequestMethod.GET)
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @RequestMapping(value="/username/{username}",produces = "application/json",method = RequestMethod.GET)
    public User getByUserName(@PathVariable String username){
        return userService.getUserByUsername(username);
    }

    @RequestMapping(value = "/id/{id}",produces = "application/json",method = RequestMethod.GET)
    public User getById(@PathVariable int id){
        return userService.getById(id);
    }

    @RequestMapping(method = RequestMethod.POST,produces = "application/json")
    public User saveOrUpdate(@RequestBody User user){
        boolean isNewUser = user.getId()==null;

        if (isNewUser && user.getEmailAddress()!=null && user.getPassword()!=null) {
            user.setPasswordHash(hash(user.getPassword()));
            User savedUser =userService.saveOrUpdate(user);
            return savedUser;
        }
         else {
            return userService.saveOrUpdate(user);
        }

    }

    @RequestMapping(value = "/id/{id}",method = RequestMethod.DELETE)
    public void deleteById(@PathVariable int id){
              userService.deleteById(id);
    }

    private String hash(String password) {
        String hash = "";
        if (null == password)
            return null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(password.getBytes(), 0, password.length());
            hash = new BigInteger(1, digest.digest()).toString(16);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
    }

    private String getTokenByUserName(String username){
        return userService.getTokenByUserName(username);
    }


}
