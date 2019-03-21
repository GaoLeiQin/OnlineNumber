package com.ledo.handlers;

import com.ledo.beans.User;
import com.ledo.common.UserManager;
import com.ledo.service.IOnlineNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 登录页面 控制器
 * @author qgl
 * @date 2018/10/29
 */
@Controller
public class LoginController {
    @Autowired
    @Qualifier("onlineNumberService")
    IOnlineNumberService onlineNumberService;

    @RequestMapping("/login.do")
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");
        return mv;
    }

    @RequestMapping("/verify.do")
    public @ResponseBody User verify(String userName, String passWord) {
        String name = null;
        String pwd = null;
        User user = new User(userName, passWord);
        int nameCount = onlineNumberService.referUserNameCount(user);
        int userCount = onlineNumberService.verifyUserInfo(user);
        if (nameCount > 0) {
            name = userName;
            if (userCount > 0) {
                pwd = passWord;
            }
        }
        return new User(name, pwd);
    }

    @RequestMapping("/adminVerify.do")
    public @ResponseBody User adminVerify(String userName, String passWord) {
        String name = null;
        String pwd = null;
        User user = new User(userName, passWord);
        int nameCount = onlineNumberService.referUserNameCount(user);
        int userCount = onlineNumberService.verifyUserInfo(user);
        if (nameCount > 0 && UserManager.isAdmin(userName)) {
            name = userName;
            if (userCount > 0) {
                pwd = passWord;
            }
        }
        return new User(name, pwd);
    }

}
