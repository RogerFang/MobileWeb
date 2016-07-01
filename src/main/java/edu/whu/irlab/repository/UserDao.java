package edu.whu.irlab.repository;

import edu.whu.irlab.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Roger on 2016/7/1.
 */
public interface UserDao extends PagingAndSortingRepository<User, Integer>, JpaSpecificationExecutor<User> {

    User findByUsername(String username);
}
