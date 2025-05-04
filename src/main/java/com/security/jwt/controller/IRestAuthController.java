package com.security.jwt.controller;

import com.security.jwt.dto.DtoUsers;
import com.security.jwt.jwtbusiness.AuthRequest;
import com.security.jwt.jwtbusiness.AuthResponse;

public interface IRestAuthController {

    public DtoUsers register(AuthRequest request);

    public AuthResponse authenticate(AuthRequest request);

}
