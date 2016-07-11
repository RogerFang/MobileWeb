package edu.whu.irlab.service;

import edu.whu.irlab.entity.User;

/**
 * Created by Roger on 2016/7/1.
 */
public interface UserService {

    User findByUsername(String username);
}
