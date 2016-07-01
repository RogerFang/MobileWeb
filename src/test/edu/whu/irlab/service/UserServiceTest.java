package edu.whu.irlab.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Roger on 2016/7/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml", "classpath:applicationContext-jpa.xml"})
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void findByUsername() throws Exception {
        System.out.println(userService.findByUsername("admin").getId());
    }

}