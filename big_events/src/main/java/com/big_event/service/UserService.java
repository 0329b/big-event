package com.big_event.service;

import com.big_event.pojo.User;

public interface UserService {
    //查找用户是否重复
    User findUserByName(String username);

    //注册用户
    void add(String username, String password);

    void update(User user);

    void updateAvatar(String avatarUrl);

    void updatePwd(String newPwd);
}
