package com.security.jwt.service;

import com.security.jwt.dto.DtoUsers;
import com.security.jwt.jwtbusiness.AuthRequest;

public interface IAuthService {

    public DtoUsers register(AuthRequest authRequest);
}
