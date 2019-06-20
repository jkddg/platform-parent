package com.platform.front.web.controller.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Huangyonghao on 2019/6/20 15:33.
 */
@Controller
public class IndexController {

    @RequestMapping({"","/index"})
    public ModelAndView indexPage(){
        ModelAndView view=new ModelAndView();
        view.setViewName("view/index");
        return view;
    }
}
