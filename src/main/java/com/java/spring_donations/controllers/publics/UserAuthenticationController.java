package com.java.spring_donations.controllers.publics;

import com.java.spring_donations.constants.CommonConstants;
import com.java.spring_donations.domain.Role;
import com.java.spring_donations.domain.User;
import com.java.spring_donations.services.UserService;
import com.java.spring_donations.utils.EncrytedPasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Objects;

@Controller
@RequestMapping("user")
public class UserAuthenticationController {

    @Autowired
    UserService userService;

    @Autowired
    MessageSource messageSource;

    EncrytedPasswordUtils encrytedPasswordUtils = new EncrytedPasswordUtils();

    @GetMapping({"/login" , "/register"})
    public ModelAndView loginOrRegister()
    {
        ModelAndView mv = new ModelAndView("public/login");
        return mv;
    }

    @PostMapping({"/register1"})
    public ModelAndView register(@ModelAttribute("user") User user,
                                 @RequestParam("rePassword") String rePassword) {
        ModelAndView mv = new ModelAndView("public/login");
        Role role = userService.findRoleById(1);
        if (!user.getPassword().equalsIgnoreCase(rePassword)) {
            mv.addObject(CommonConstants.MSG_REGISTER_ERROR, messageSource.getMessage("password_and_repassword", null, Locale.getDefault()));
        } else {
            String passwordMD5 = encrytedPasswordUtils.md5(user.getPassword());
            user.setPassword(passwordMD5);
            User checkExistEmail = userService.checkEmailExist(user.getEmail());
            if (!Objects.isNull(checkExistEmail)) {
                mv.addObject(CommonConstants.MSG_REGISTER_ERROR, messageSource.getMessage("email_exited", null, Locale.getDefault()));
            } else {
                user.setRole(role);
                user.setStatus(1);
                User userSave = userService.save(user);
                mv.addObject(CommonConstants.MSG_REGISTER_SUCCESS, messageSource.getMessage("register_success", null, Locale.getDefault()));
            }
        }
        return mv;

    }

    @PostMapping({"/login"})
    public ModelAndView login(@ModelAttribute("user") User user, RedirectAttributes rd, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("public/login");
        String passwordMd5 = encrytedPasswordUtils.md5(user.getPassword());
        User userLogin = userService.checkLogin(user.getEmail(),passwordMd5);
        if (Objects.isNull(userLogin)) {
            mv.addObject(CommonConstants.MSG_REGISTER_ERROR, messageSource.getMessage("login_error", null, Locale.getDefault()));
        } else {
            rd.addFlashAttribute(CommonConstants.MSG_REGISTER_SUCCESS,
                    messageSource.getMessage("login_success", null, Locale.getDefault()));
            HttpSession session = request.getSession();
            session.setAttribute(CommonConstants.SESSION_USER, userLogin);
            userLogin.setCreatedAt(java.time.LocalDate.now().toString());
            userService.save(userLogin);
            mv = new ModelAndView("redirect:/index");
        }
        return mv;
    }
    @GetMapping(value = "logout")
    public ModelAndView getLogout(HttpServletRequest request){
        ModelAndView mv = new ModelAndView("redirect:/");
        HttpSession session = request.getSession(false);
        session.removeAttribute("user");
        return mv;
    }
}
