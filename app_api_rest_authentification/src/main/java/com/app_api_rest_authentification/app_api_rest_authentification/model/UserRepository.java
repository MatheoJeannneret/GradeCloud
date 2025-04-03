package com.app_api_rest_authentification.app_api_rest_authentification.model;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
}
