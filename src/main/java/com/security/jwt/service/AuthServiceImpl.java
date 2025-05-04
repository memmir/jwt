package com.security.jwt.service;

import com.security.jwt.dto.DtoUsers;
import com.security.jwt.jwtbusiness.AuthRequest;
import com.security.jwt.model.Users;
import com.security.jwt.repository.UsersRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements IAuthService{

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public DtoUsers register(AuthRequest authRequest) { // Burada DtoUSers dönmek sistem açığıdır. kaydettiğimiz kişinin şifresini return ediyoruz.
        // Bunun yerine String dönüp Başarı ile kaydedildi demek daha doğru olur.

        DtoUsers dtoUsers = new DtoUsers();
        Users users = new Users();

        users.setUsername(authRequest.getUserName());
        users.setPassword(passwordEncoder.encode(authRequest.getPassword())); // frontendden gelen şifreyi enkript ettik

        Users savedUser = usersRepository.save(users);

        BeanUtils.copyProperties(savedUser, dtoUsers);

        return dtoUsers;
    }
}
