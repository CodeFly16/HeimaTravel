package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {

    //根据用户名查询用户信息
    User findByUsername(String name);

    //用户保存
    void save(User user);

    User findByCode(String code);

    void updateStatus(User user);

    User findByUsernameAndPassword(String username, String password);
}
