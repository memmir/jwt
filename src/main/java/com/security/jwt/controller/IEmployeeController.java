package com.security.jwt.controller;

import com.security.jwt.dto.DtoEmployee;

public interface IEmployeeController {

    DtoEmployee findEmployeeById(Long id);
}
