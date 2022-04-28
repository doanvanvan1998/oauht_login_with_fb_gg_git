package com.example.quanlysanpham.Configuration;

import com.example.quanlysanpham.Entity.RoleEntity;
import com.example.quanlysanpham.Entity.UserEntity;
import com.example.quanlysanpham.Repository.RoleRepository;
import com.example.quanlysanpham.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashSet;
import java.util.Set;

@Controller
public class LoginController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    RoleRepository roleRepository;

//    @RequestMapping(method = RequestMethod.GET, value = "/add")
//    public String add(){
//        UserEntity userEntity = new UserEntity();
//        RoleEntity r = roleRepository.findById(1L).get();
//        Set<RoleEntity> roles = new HashSet<>();
//        roles.add(r);
//        userEntity.setUsername("admin");
//        userEntity.setPassword(bCryptPasswordEncoder.encode("admin"));
//        userEntity.setRoles(roles);
//        userEntity.setEnable(true);
//        userRepository.save(userEntity);
//        return "login";
//    }WWW

    @GetMapping("/login")
    public String loginView(){
        return "login";
    }

    @GetMapping("/403")
    public String forbiedanView(){
        return "403";
    }

}
