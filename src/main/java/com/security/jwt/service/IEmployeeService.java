package com.security.jwt.service;

import com.security.jwt.dto.DtoEmployee;

public interface IEmployeeService {

    DtoEmployee findEmployeeById(Long id);
}
