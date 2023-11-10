package com.cs362.tuffyride.controller;

import com.cs362.tuffyride.model.User;
import com.cs362.tuffyride.model.UserRole;
import com.cs362.tuffyride.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    private RegisterService registerService;
    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }
    @PostMapping("/register/guest") //to indicate the API suports method and maps to path.
    public void addGuest(@RequestBody User user) { // to help convert the request body from JSON format to java object.
        registerService.add(user, UserRole.ROLE_GUEST);
    }
    @PostMapping("/register/driver")
    public void addDriver(@RequestBody User user) {
        registerService.add(user, UserRole.ROLE_DRIVER);
    }

}
