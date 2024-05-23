package com.big_event.controller;

import com.big_event.pojo.Result;
import com.big_event.pojo.User;
import com.big_event.service.UserService;
import com.big_event.utils.JwtUtil;
import com.big_event.utils.Md5Util;
import com.big_event.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    //注册功能
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        //先查找用户名是否重复
        System.out.println(username+"米玛："+password);
        User user = userService.findUserByName(username);
        if (user == null) {
            userService.add(username, password);
            return Result.success();
        } else {
            return Result.error("用户名已被占用");
        }
    }

    //登录功能
    @PostMapping("/login")
    public Result<String> login(String username, String password) {
        User user = userService.findUserByName(username);
        if (user == null) {
            return Result.error("用户名错误");
        }
        if (Md5Util.getMD5String(password).equals(user.getPassword())) {
            Map<String, Object> claims=new HashMap<>();
            claims.put("id",user.getId());
            claims.put("username",user.getUsername());
            String token = JwtUtil.genToken(claims);
            //将token存入redis
             String key = user.getId().toString();
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(key,token,1, TimeUnit.HOURS);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }
    //获取用户详细信息
    @GetMapping("/userInfo")
    public Result<User> userInfo(){
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String)map.get("username");
        User user = userService.findUserByName(username);
        return Result.success(user);
    }
    //更新用户信息
    @PostMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }
    //更换头像
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
      userService.updateAvatar(avatarUrl);
      return Result.success();
    }
    //重置密码
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params){
        //获取里面元素
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");
        //校验
        if(!StringUtils.hasLength(oldPwd)||!StringUtils.hasLength(newPwd)||!StringUtils.hasLength(rePwd)){
            return Result.error("缺少必要参数");
        }
        //比对原密码是否一致
        Map<String,Object> map=ThreadLocalUtil.get();
        String username = (String)map.get("username");
        User user = userService.findUserByName(username);
        String password = user.getPassword();
        if(!Md5Util.getMD5String(oldPwd).equals(password)){
            return Result.error("原密码不一致");
        }
        //对比新密码是否一致
        if(!rePwd.equals(newPwd)){
            return Result.error("两次新密码不一致");
        }
        //更新操作
        String new_pwd = Md5Util.getMD5String(newPwd);
        userService.updatePwd(new_pwd);
        //删除redis令牌
        String  id= (String)map.get("id");
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.getOperations().delete(id);
        return Result.success();
    }
}
