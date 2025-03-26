package com.project.onlinebanking.controller;

import com.project.onlinebanking.dto.ChangePasswordDTO;
import com.project.onlinebanking.entity.User;
import com.project.onlinebanking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PatchMapping("/change-password")
    public ResponseEntity<String> changeUserPassword(@RequestBody ChangePasswordDTO changePasswordDTO, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        userService.changePassword(user, changePasswordDTO.getPassword());
        return ResponseEntity.ok("Password changed");
    }
}
