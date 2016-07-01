package edu.whu.irlab.service.impl;

import edu.whu.irlab.entity.User;
import edu.whu.irlab.repository.UserDao;
import edu.whu.irlab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Roger on 2016/7/1.
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
