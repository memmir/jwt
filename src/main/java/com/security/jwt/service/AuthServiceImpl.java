package com.security.jwt.service;

import com.security.jwt.dto.DtoUsers;
import com.security.jwt.jwtbusiness.AuthRequest;
import com.security.jwt.jwtbusiness.AuthResponse;
import com.security.jwt.jwtbusiness.JwtService;
import com.security.jwt.model.Users;
import com.security.jwt.repository.UsersRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtService jwtService;

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        try{
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()); // kullanıcı adı ve şifreyi dbdekilerle aynı mı diye kontrol ediyor
            authenticationProvider.authenticate(auth); // dbdeki verilerle eşleştiyse yani authenticate olduysa aşağıdaki generateToken metodu ile token üretiyoruz.

            Optional<Users> optionalUsers = usersRepository.findByUsername(authRequest.getUserName());

            String token = jwtService.generateToken(optionalUsers.get()); //dbdeki verilerle bilgileri uyuşan kişinin verileriyle token oluşturuyoruz.


            return new AuthResponse(token);
        }catch (Exception e){
            System.out.println("Kullanıcı adı veya şifre hatalı");
        }
        return null;
    }


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
