package com.security.jwt.controller;

import com.security.jwt.dto.DtoUsers;
import com.security.jwt.jwtbusiness.AuthRequest;

public interface IRestAuthController {

    public DtoUsers register(AuthRequest request);

}
