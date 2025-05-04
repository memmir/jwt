package com.security.jwt.service;

import com.security.jwt.dto.DtoUsers;
import com.security.jwt.jwtbusiness.AuthRequest;
import com.security.jwt.jwtbusiness.AuthResponse;

public interface IAuthService {

    public DtoUsers register(AuthRequest authRequest);

    public AuthResponse authenticate(AuthRequest authRequest);
}
