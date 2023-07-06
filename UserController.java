package org.algotithmcontestdatacollect.displaybackend.Controllers;

import org.algotithmcontestdatacollect.displaybackend.Entities.ApplicationEntity;
import org.algotithmcontestdatacollect.displaybackend.Entities.NormalUserEntity;
import org.algotithmcontestdatacollect.displaybackend.Utils.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import org.algotithmcontestdatacollect.displaybackend.Repositories.ApplicationRepository;
import org.algotithmcontestdatacollect.displaybackend.Repositories.SchoolRepository;
import org.algotithmcontestdatacollect.displaybackend.Repositories.UserRepository;
import org.algotithmcontestdatacollect.displaybackend.Utils.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private UserRepository normalUserRepository;
    @Autowired
    JWTUtil jwtUtil;
    @Autowired
    private ApplicationRepository applicationRepository;

    @PostMapping("/api/register")
    public String register(@RequestBody JSONObject data) {
        String username = data.getString("username");
        String password = data.getString("password");
        Long school = data.getLong("school");
        String classname = data.getString("classname");
        Integer year = data.getInteger("year");
        String stuno = data.getString("stuno");
        String realname = data.getString("realname");
        if (username == null || password == null || school == null || classname == null || year == null || stuno == null || realname== null) {
            return ResponseUtil.JSONReturn(404,"参数错误");
        }
        if (schoolRepository.countById(school) == 0) {
            return ResponseUtil.JSONReturn(404,"学校不存在");
        }
        if(normalUserRepository.existsBySchoolAndStuNo(school,stuno)){
            return ResponseUtil.JSONReturn(404,"学号已存在");
        }
        if (normalUserRepository.findByUsername(username) != null) {
            return ResponseUtil.JSONReturn(404, "username already exists");
        }
        ApplicationEntity application = ApplicationEntity.createNewUser(username, password, school, classname, year, stuno,realname);
        application = applicationRepository.saveAndFlush(application);
        if (application == null) {
            return ResponseUtil.JSONReturn(404, "register failed");
        }
        return ResponseUtil.JSONReturn(200, "register success");
    }
    @PostMapping("/api/login")
    public String login(@RequestBody JSONObject data) {
        logger.info("login:{}",data.getString("username"));
        String username = data.getString("username");
        String password = data.getString("password");
        if (checkExistAndEmpty(username)||checkExistAndEmpty(password)){
            return ResponseUtil.JSONReturn(404,"参数不足");
        }
        NormalUserEntity userEntity;
        try {
            userEntity = checkUserNameAndPassword(username,password);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (userEntity == null) {
            return ResponseUtil.JSONReturn(404,"用户名或密码错误");
        }
        var ret = new JSONObject();
        Map<String,String> info = new HashMap<>();
        info.put("username",userEntity.getUsername());
        info.put("id",userEntity.getId().toString());
        info.put("school",userEntity.getSchool().toString());
        ret.put("token", jwtUtil.createToken(info));
        ret.put("username",userEntity.getUsername());
        ret.put("id",userEntity.getId());
        return ResponseUtil.JSONReturn(200,ret);
    }
    @PostMapping("/api/user/flush")
    public String rewrite(HttpServletRequest request) {
        var ret = new JSONObject();
        Map<String,String> info = new HashMap<>();
        info.put("username",(String) request.getAttribute("username"));
        info.put("id",(String) request.getAttribute("id"));
        info.put("school",(String) request.getAttribute("school"));
        ret.put("token",jwtUtil.createToken(info));
        return  ResponseUtil.JSONReturn(200,ret);
    }

    @PostMapping("/api/user/resetPassword")
    public String resetPassword(@RequestBody JSONObject data, HttpServletRequest request) {
        var uid = Long.parseLong((String) request.getAttribute("id"));
        var user = normalUserRepository.findById(uid);
        if (user.isPresent()) {
            var userEntity = user.get();
            if(!userEntity.getPassword().equals(data.getString("oldPassword"))){
                return ResponseUtil.JSONReturn(401,"原密码错误");
            }
            userEntity.setPassword(data.getString("newPassword"));
            try{
                normalUserRepository.saveAndFlush(userEntity);
            }catch (Exception e) {
                return ResponseUtil.JSONReturn(404,"数据库异常");
            }
            return ResponseUtil.JSONReturn(200,"修改成功");
        }
        return ResponseUtil.JSONReturn(404,"用户不存在");
    }
    @GetMapping("/api/user/getInfo")
    public String getInfo(HttpServletRequest request) {
        var uid = Long.parseLong((String) request.getAttribute("id"));
        var user = normalUserRepository.findById(uid);
        if (user.isPresent()) {
            var userEntity = user.get();
            var ret = new JSONObject();
            ret.put("username",userEntity.getUsername());
            ret.put("school",userEntity.getSchool());
            ret.put("classname",userEntity.getClassname());
            ret.put("year",userEntity.getYear());
            ret.put("stuno",userEntity.getStuNo());
            ret.put("realname",userEntity.getRealname());
            return ResponseUtil.JSONReturn(200,ret);
        }
        return ResponseUtil.JSONReturn(404,"用户不存在");
    }
    @PostMapping("/api/user/modifyInfo")
    public String modifyInfo(@RequestBody JSONObject data, HttpServletRequest request) {
        var uid = Long.parseLong((String) request.getAttribute("id"));
        var user = normalUserRepository.findById(uid);
        if (user.isPresent()) {
            var userEntity = user.get();
            userEntity.setClassname(data.getString("classname"));
            userEntity.setYear(data.getInteger("year"));
            userEntity.setStuNo(data.getString("stuno"));
            userEntity.setRealname(data.getString("realname"));
            try{
                normalUserRepository.saveAndFlush(userEntity);
            }catch (Exception e) {
                return ResponseUtil.JSONReturn(404,"数据库异常");
            }
            return ResponseUtil.JSONReturn(200,"修改成功");
        }
        return ResponseUtil.JSONReturn(404,"用户不存在");
    }

    private boolean checkExistAndEmpty(String param){
        return param == null || param.equals("");
    }

    private NormalUserEntity checkUserNameAndPassword(String username,String password) throws InterruptedException {
        final boolean[] end = {false};
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                end[0] = true;
            }
        },250);
        var user = normalUserRepository.findByUsername(username);
        if (user != null) {
            if (!user.getPassword().equals(password) ) {
                user = null;
            }
        }
        while (!end[0]){
            Thread.sleep(0);
        }
        return user;
    }

}
