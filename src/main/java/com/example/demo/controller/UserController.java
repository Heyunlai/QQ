package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Controller
public class UserController {
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @RequestMapping("/register")
    public String register(){
        return "register";
    }
    @RequestMapping("/doRegister")
    public String doRegister(User user, ModelMap map, MultipartFile faceFile) throws IOException {
        //判断数据库是否存在
          boolean isExist = redisTemplate.hasKey(user.getUserName());
          if(isExist){
              //存在。。。。
              map.addAttribute("msg","你注册的用户已存在！");
          }else {
              if(user.getUserName()==null||user.getUserName().equals("")){
                  map.addAttribute("msg","用户名不可为空！");
              }else {
                  user.setFace(faceFile.getBytes());
                  redisTemplate.opsForValue().set("" + user.getUserName(), user);
                  return "login";
              }
          }
        return "register";
    }
    @RequestMapping("/login")
    public String login(){

        return "login";
    }
    @RequestMapping("/doLogin")
    public String doLogin(User user, ModelMap map,HttpSession session){
        boolean isExist = redisTemplate.hasKey(user.getUserName());
        if(isExist){
            User u= (User) redisTemplate.opsForValue().get(user.getUserName());
            if (u.getPassword().equals(user.getPassword())){
                session.setAttribute("user",u);
                return "chat";
            }else {
                map.addAttribute("msg","密码错误！");
            }
        }else {
            map.addAttribute("msg","用户不存在！");
        }
        return "login";
    }

    @RequestMapping("/getHead/{userName}")
    @ResponseBody
    public byte[] getHead(@PathVariable("userName") String userName){
        if(redisTemplate.hasKey(userName)){
            User user= (User) redisTemplate.opsForValue().get(userName);
            byte[] face=user.getFace();
            return face;
        }
        return null;
    }
    @RequestMapping("/chat1")
    public String chat(){
        return "chat";
    }
    @RequestMapping("weixin/add_item")
    @ResponseBody
    public String weixin(@RequestBody JSONObject jsonObject){
        System.out.println(jsonObject.toJSONString());
        return "succ";
    }
//    public String weixin(HttpServletRequest request){
//        System.out.println(request.getParameter("address"));
//        return "succ";
//    }
}
