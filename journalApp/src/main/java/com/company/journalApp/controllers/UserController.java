package com.company.journalApp.controllers;

import com.company.journalApp.entities.JournalEntry;
import com.company.journalApp.entities.User;
import com.company.journalApp.services.JournalEntryService;
import com.company.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping()
    public ResponseEntity<User> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User dbUser = userService.findByUserName(userName);

        dbUser.setUserName(user.getUserName());
        dbUser.setPassword(user.getPassword());
        userService.saveNewUser(dbUser);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
