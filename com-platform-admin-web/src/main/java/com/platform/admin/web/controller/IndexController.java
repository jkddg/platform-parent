package com.platform.admin.web.controller;


import com.platform.admin.service.iface.UserService;
import com.platform.admin.service.modal.user.UserInfo;
import com.platform.common.modal.ResultInfo;
import com.platform.common.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {

    @Autowired
    UserService userService;
    String solt = "abc";

    @RequestMapping({"", "/index"})
    public ModelAndView indexPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView view = new ModelAndView();
        Cookie[] cookies = request.getCookies();
        Cookie cookieName = getCookie(cookies, "userName");
        Cookie cookiePwd = getCookie(cookies, "uPwd");
        String userName = cookieName != null ? cookieName.getValue() : null;
        String passWd = cookiePwd != null ? cookiePwd.getValue() : null;
        String md5 = "";
        try {
            md5 = SecurityUtil.desDecrypt(passWd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        chkUser(userName, passWd, response, view);
        return view;
    }

    @RequestMapping("/login")
    public ModelAndView login(String userName, String passWd, HttpServletResponse response) {
        ModelAndView view = new ModelAndView();
        chkUser(userName, passWd, response, view);

        return view;
    }

    private void chkUser(String userName, String passWd, HttpServletResponse response, ModelAndView view) {
        ResultInfo<UserInfo> userResult = userService.getUserByAccout(userName);
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(passWd)) {
            view.setViewName("view/login");
        } else if (userResult.getData() == null) {
            view.setViewName("view/login");
        } else {
            UserInfo userInfo = userResult.getData();
            if (userName.equals(userInfo.getUserName()) && chkDbPwd(passWd, userInfo.getUserPwd())) {
                Cookie cookie = new Cookie("userName", userName);
                cookie.setHttpOnly(true);
                cookie.setMaxAge(60 * 60 * 24);
                response.addCookie(cookie);
                String md5 = "";
                try {
                    md5 = SecurityUtil.desEncrypt(passWd);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                cookie = new Cookie("uPwd", md5);
                cookie.setHttpOnly(true);
                cookie.setMaxAge(60 * 60 * 24);
                response.addCookie(cookie);
                view.setViewName("view/index");
            } else {
                view.setViewName("view/login");
            }
        }
    }

    private boolean chkDbPwd(String pagePwd, String dbPwd) {
        String md5 = DigestUtils.md5DigestAsHex((pagePwd + solt).getBytes());
        if (md5.equals(dbPwd)) {
            return true;
        }
        return false;
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
