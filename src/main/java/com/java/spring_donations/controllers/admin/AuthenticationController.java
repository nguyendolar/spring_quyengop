package com.java.spring_donations.controllers.admin;

import com.java.spring_donations.constants.CommonConstants;
import com.java.spring_donations.domain.User;
import com.java.spring_donations.services.UserService;
import com.java.spring_donations.utils.EncrytedPasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

@Controller
@RequestMapping("admin")
public class AuthenticationController {

    @Autowired
    MessageSource messageSource;

    @Autowired
    UserService userService;


    EncrytedPasswordUtils encrytedPasswordUtils = new EncrytedPasswordUtils();


    @GetMapping({"/login"})
    public ModelAndView loadHomePage()
    {
        ModelAndView mv = new ModelAndView("admin/login");
        return mv;
    }

    @PostMapping({"/login"})
    public ModelAndView login(RedirectAttributes rd, HttpServletRequest request, @ModelAttribute("user") User user) {
        ModelAndView mv = new ModelAndView("admin/login");
        String passwordMD5 = encrytedPasswordUtils.md5(user.getPassword());
        user.setPassword(passwordMD5);
        User userLogin = userService.checkLoginAdmin(user.getEmail(),passwordMD5);
        if (userLogin != null) {
            HttpSession session = request.getSession();
            session.setAttribute(CommonConstants.SESSION_ADMIN, userLogin);
            rd.addFlashAttribute(CommonConstants.MSG_REGISTER_SUCCESS,
                    messageSource.getMessage("login_success", null, Locale.getDefault()));
            mv = new ModelAndView("redirect:/ql-donation/index");
        } else {
            mv.addObject("error","fail");
        }
        return mv;
    }
    @GetMapping(value = "logout")
    public ModelAndView getLogout(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("redirect:/admin/login");
        HttpSession session = request.getSession(false);
        session.removeAttribute("admin");
        return mv;
    }

}
