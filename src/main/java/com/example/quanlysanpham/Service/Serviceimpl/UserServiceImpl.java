package com.example.quanlysanpham.Service.Serviceimpl;

import com.example.quanlysanpham.Entity.RoleEntity;
import com.example.quanlysanpham.Entity.UserEntity;
import com.example.quanlysanpham.Repository.RoleRepository;
import com.example.quanlysanpham.Repository.UserRepository;
import com.example.quanlysanpham.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    public void checkExistUserOauth(String username, String method){
        UserEntity existUser = userRepository.getByUsername(username);
        if(existUser == null){
            UserEntity u = new UserEntity();

            u.setUsername(username);
            u.setProvider(method);
            u.setEnable(true);
            u = userRepository.save(u);
            u = userRepository.findById(u.getId()).get();
            RoleEntity role = roleRepository.findById(2L).get();
            Set<RoleEntity> roleSet = new HashSet<>();
            roleSet.add(role);
            if(u.getRoles() != null && u.getRoles().size() > 0){
                u.getRoles().clear();
            }
            u.setRoles(roleSet);
            u = userRepository.save(u);
        }
    }
}
