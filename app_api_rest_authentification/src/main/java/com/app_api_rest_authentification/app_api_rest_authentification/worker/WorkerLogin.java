package com.app_api_rest_authentification.app_api_rest_authentification.worker;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app_api_rest_authentification.app_api_rest_authentification.model.User;
import com.app_api_rest_authentification.app_api_rest_authentification.model.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class WorkerLogin {

    private final UserRepository userRepository;

    @Autowired
    public WorkerLogin(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean checkUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return true;
        } else
            return false;
    }

    @Transactional
    public boolean checkUser(String username, String pwd) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(pwd)) {
            return true;
        } else
            return false;
    }

    @Transactional
    public boolean isUserAdmin(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.isAdmin() == true) {
            return true;
        } else
            return false;
    }

    @Transactional
    public HashMap<String, String> getUserInfo(String username) {
        HashMap<String, String> userInfos = null;
        User user = userRepository.findByUsername(username);
        if (user != null) {
            userInfos = new HashMap<String, String>();
            userInfos.put("username", user.getUsername());
            userInfos.put("Admin", String.valueOf(user.isAdmin()));
        }
        return userInfos;
    }
}
