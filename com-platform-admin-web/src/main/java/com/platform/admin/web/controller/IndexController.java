package com.platform.admin.web.controller;


import com.platform.admin.service.modal.user.UserInfo;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {

    private String uname = "jkddg";
    private String upwd = "jkddg123456";

    @RequestMapping({"", "/index"})
    public ModelAndView indexPage(HttpServletRequest request) {
        ModelAndView view = new ModelAndView();
        Cookie[] cookies = request.getCookies();
        Cookie cookieName = getCookie(cookies, "userName");
        Cookie cookiePwd = getCookie(cookies, "uPwd");
        String userName = cookieName != null ? cookieName.getValue() : null;
        String passWd = cookiePwd != null ? cookiePwd.getValue() : null;
        String md5 = DigestUtils.md5DigestAsHex(upwd.getBytes());
        if (uname.equals(userName) && md5.equals(passWd)) {
            view.setViewName("view/index");
        } else {
            view.setViewName("view/login");
        }
        return view;
    }

    @RequestMapping("/login")
    public ModelAndView login(String userName, String passWd, HttpServletResponse response) {
        ModelAndView view = new ModelAndView();
        if (userName.equals(uname) && passWd.equals(upwd)) {
            Cookie cookie = new Cookie("userName", userName);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(60 * 60 * 24);
            response.addCookie(cookie);
            String md5 = DigestUtils.md5DigestAsHex(passWd.getBytes());
            cookie = new Cookie("uPwd", md5);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(60 * 60 * 24);
            response.addCookie(cookie);
            view.setViewName("view/index");
        } else {
            view.setViewName("view/login");
        }
        return view;
    }

    private Cookie getCookie(Cookie[] cookies, String name) {
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }
        return null;
    }
}
