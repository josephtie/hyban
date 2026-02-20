package com.nectux.mizan.hyban.controllers;



import com.nectux.mizan.hyban.parametrages.dto.AuthResponse;
import com.nectux.mizan.hyban.parametrages.dto.LoginRequest;
import com.nectux.mizan.hyban.parametrages.dto.ProfileDto;
import com.nectux.mizan.hyban.parametrages.dto.RegisterRequest;
import com.nectux.mizan.hyban.parametrages.dto.RefreshRequest;
import com.nectux.mizan.hyban.parametrages.dto.ForgotPasswordRequest;
import com.nectux.mizan.hyban.parametrages.dto.ResetPasswordRequest;
import com.nectux.mizan.hyban.parametrages.dto.ChangePasswordRequest;
import com.nectux.mizan.hyban.parametrages.dto.UpdateProfileRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController

@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request){
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request){
        return authService.login(request);
    }

    @GetMapping("/me")
    public ProfileDto me(@AuthenticationPrincipal String username){
        return authService.profile(username);
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody RefreshRequest request){
        return authService.refresh(request);
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader(value = "Authorization", required = false) String authHeader){
        String token = null;
        if(authHeader != null){
            if(authHeader.startsWith("Bearer ")){
                token = authHeader.substring(7);
            } else {
                token = authHeader;
            }
        }
        authService.logout(token);
    }

    @GetMapping("/verify")
    public Map<String, Object> verify(@RequestParam("token") String token){
        boolean valid = authService.verifyToken(token);
        Map<String, Object> resp = new HashMap<>();
        resp.put("valid", valid);
        return resp;
    }

    @PostMapping("/forgot-password")
    public void forgotPassword(@RequestBody ForgotPasswordRequest request){
        authService.forgotPassword(request);
    }

    @PostMapping("/reset-password")
    public void resetPassword(@RequestBody ResetPasswordRequest request){
        authService.resetPassword(request);
    }

    @PostMapping("/change-password")
    public void changePassword(@AuthenticationPrincipal String username, @RequestBody ChangePasswordRequest request){
        authService.changePassword(username, request);
    }

    @GetMapping("/profile")
    public ProfileDto profile(@AuthenticationPrincipal String username){
        return authService.profile(username);
    }

    @PutMapping("/profile")
    public ProfileDto updateProfile(@AuthenticationPrincipal String username, @RequestBody UpdateProfileRequest request){
        return authService.updateProfile(username, request);
    }
}
