package com.security.jwt.controller;

import com.security.jwt.dto.DtoUsers;
import com.security.jwt.jwtbusiness.AuthRequest;
import com.security.jwt.service.IAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestAuthControllerImpl implements IRestAuthController{

    @Autowired
    private IAuthService authService;

    @PostMapping("/register")
    @Override
    public DtoUsers register(@Valid @RequestBody AuthRequest request) {// Burada DtoUSers dönmek sistem açığıdır. kaydettiğimiz kişinin şifresini return ediyoruz.
        // Bunun yerine String dönüp Başarı ile kaydedildi demek daha doğru olur.
        return authService.register(request);
    }
}
