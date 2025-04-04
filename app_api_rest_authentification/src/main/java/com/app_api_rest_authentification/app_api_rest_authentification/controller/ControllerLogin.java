package com.app_api_rest_authentification.app_api_rest_authentification.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app_api_rest_authentification.app_api_rest_authentification.worker.WorkerLogin;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(path = "/user")
public class ControllerLogin {

    private final WorkerLogin wrkLogin;

    @Autowired
    public ControllerLogin(WorkerLogin wrkLogin) {
        this.wrkLogin = wrkLogin;
    }

    @GetMapping("/checkuser")
    public ResponseEntity<String> checkUser(@RequestParam String username, @RequestParam String password) {
        if (wrkLogin.checkUsername(username)) {
            if (wrkLogin.checkUser(username, password)) {
                return ResponseEntity.ok("User correct");
            } else
                return ResponseEntity.badRequest().body("User incorrect");
        } else
            return ResponseEntity.badRequest().body("User introuvable");

    }

    @GetMapping("/isadmin")
    public ResponseEntity<String> isAdmin(@RequestParam String username) {
        if (wrkLogin.checkUsername(username)) {
            if (wrkLogin.isUserAdmin(username)) {
                return ResponseEntity.ok("User is admin");
            } else
                return ResponseEntity.badRequest().body("User is not admin");
        } else
            return ResponseEntity.badRequest().body("user introuvable");

    }

    @GetMapping("/getinfo")
    public ResponseEntity<HashMap<String, String>> getInfos(@RequestParam String username) {
        HashMap<String, String> infos = wrkLogin.getUserInfo(username);
        if (infos != null) {
            return ResponseEntity.ok(infos);
        } else {
            HashMap<String, String> errorResponse = new HashMap<String, String>();
            errorResponse.put("error", "Utilisateur non trouvé");
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

}
