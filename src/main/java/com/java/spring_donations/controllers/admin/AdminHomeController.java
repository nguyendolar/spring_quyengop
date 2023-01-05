package com.java.spring_donations.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static com.java.spring_donations.utils.Middleware.middlewareAdmin;

@Controller
@RequestMapping("admin")
public class AdminHomeController {
    @GetMapping({"/" , "/index"})
    public ModelAndView loadHomePage(HttpServletRequest request)
    {
        ModelAndView mv = new ModelAndView();
        boolean auth = middlewareAdmin(request);
        if (auth) {
            mv = new ModelAndView("admin/home");

        } else {
            mv = new ModelAndView("admin/login");
        }


        return mv;
    }
}
