package com.platform.admin.web.controller;


import com.platform.admin.consumer.feignClient.GoodsService;
import com.platform.admin.consumer.feignClient.ManualMessageService;
import com.platform.admin.consumer.feignClient.UserService;
import com.platform.common.modal.manual.MessageParam;
import com.platform.common.modal.user.UserInfo;
import com.platform.common.contanst.PlatformEnum;
import com.platform.common.modal.manual.ManualMessageParam;
import com.platform.common.modal.goods.MyCategory;
import com.platform.common.modal.ResultInfo;
import com.platform.common.util.DateUtil;
import com.platform.common.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class IndexController {

    @Autowired
    UserService userService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    ManualMessageService manualMessageService;

    String solt = "abc";

    @RequestMapping({"", "/index"})
    public ModelAndView indexPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView view = new ModelAndView();
        Cookie[] cookies = request.getCookies();
        Cookie cookieName = getCookie(cookies, "userName");
        Cookie cookiePwd = getCookie(cookies, "uPwd");
        String userName = cookieName != null ? cookieName.getValue() : null;
        String passWd = cookiePwd != null ? cookiePwd.getValue() : null;
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(passWd)) {
            view.setViewName("view/login");
        } else {
            String md5 = "";
            try {
                md5 = SecurityUtil.desDecrypt(passWd);
            } catch (Exception e) {

            }
            chkUser(userName, md5, response, view);
        }

        return view;
    }

    @RequestMapping("/login")
    public ModelAndView login(String userName, String passWd, HttpServletResponse response) {
        ModelAndView view = new ModelAndView();
        chkUser(userName, passWd, response, view);

        return view;
    }

    private void chkUser(String userName, String passWd, HttpServletResponse response, ModelAndView view) {
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(passWd)) {
            view.setViewName("view/login");
            return;
        }
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
                cookie = new Cookie("userId", String.valueOf(userInfo.getUserId()));
                cookie.setHttpOnly(true);
                cookie.setMaxAge(60 * 60 * 24);
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

    private String getCurrentUserName(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Cookie cookieName = getCookie(cookies, "userName");
        String userName = cookieName != null ? cookieName.getValue() : null;
        return userName;
    }

    private Long getCurrentUserId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Cookie cookie = getCookie(cookies, "userId");
        String userId = cookie != null ? cookie.getValue() : null;
        try {
            return Long.valueOf(userId);
        } catch (NumberFormatException e) {
            return 0L;
        }
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

    @RequestMapping("/pushMsg")
    @ResponseBody
    public ResultInfo pushMsg(String msg, HttpServletRequest request) {
        ResultInfo resultInfo = new ResultInfo();
        if (StringUtils.isEmpty(msg)) {
            resultInfo = ResultInfo.failInfo("消息内容为空");
            return resultInfo;
        }
        MessageParam messageParam = new MessageParam();
        messageParam.setCreateTime(LocalDateTime.now());
        messageParam.setCreateUser(getCurrentUserId(request));
        messageParam.setCreateUserName(getCurrentUserName(request));
        LocalDateTime myEndTime = LocalDateTime.now().plusDays(1);
        messageParam.setEndTime(myEndTime);
        messageParam.setId(UUID.randomUUID().toString());
        messageParam.setMaxSendCount(1);
        messageParam.setMsg(msg);
        resultInfo = manualMessageService.add(messageParam);
        return resultInfo;
    }

    @RequestMapping("/getMyCategorys")
    @ResponseBody
    public ResultInfo<List<MyCategory>> getMyCategorys() {
        ResultInfo<List<MyCategory>> result = goodsService.getMyCategorys();
        return result;
    }

}
