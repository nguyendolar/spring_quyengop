package com.java.spring_donations.controllers.publics;

import com.java.spring_donations.constants.CommonConstants;
import com.java.spring_donations.domain.User;
import com.java.spring_donations.domain.UserDonation;
import com.java.spring_donations.services.UserDonationService;
import com.java.spring_donations.services.UserService;
import com.java.spring_donations.utils.EncrytedPasswordUtils;
import com.java.spring_donations.utils.MailUtil;
import com.java.spring_donations.utils.Middleware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserDonationService userDonationService;

    @Autowired
    MessageSource messageSource;

    EncrytedPasswordUtils encrytedPasswordUtils = new EncrytedPasswordUtils();

    @Autowired
    public JavaMailSenderImpl javaMailSenderImpl;

    @GetMapping("/info/{id}")
    public ModelAndView info(@PathVariable int id, HttpServletRequest request){

        ModelAndView mv = new ModelAndView();
        boolean auth = Middleware.middleware(request);

        if (auth) {
            User userInformation = userService.getUserById(id);
            List<UserDonation> userDonationList = userDonationService.findUserDonationByUser(userInformation);
            mv = new ModelAndView("public/profile");
            mv.addObject("userInformation",userInformation);
            mv.addObject("userDonationList",userDonationList);
        } else {
            mv = new ModelAndView("redirect:/user/login");
        }
        return mv;
    }

    @GetMapping("/donate/{id}")
    public ModelAndView donate(@PathVariable int id, HttpServletRequest request){

        ModelAndView mv = new ModelAndView();
        boolean auth = Middleware.middleware(request);

        if (auth) {
            User userInformation = userService.getUserById(id);
            List<UserDonation> userDonationList = userDonationService.findUserDonationByUser(userInformation);
            mv = new ModelAndView("public/donate");
            mv.addObject("userInformation",userInformation);
            mv.addObject("userDonationList",userDonationList);
        } else {
            mv = new ModelAndView("redirect:/user/login");
        }
        return mv;
    }

    @PostMapping("/update-profile")
    public ModelAndView updateProfile(@ModelAttribute("user") User user, RedirectAttributes rd, HttpServletRequest request){
        User userCheck = userService.checkEmailExist(user.getEmail());
        userCheck.setFullName(user.getFullName());
        userCheck.setAddress(user.getAddress());
        userCheck.setPhoneNumber(user.getPhoneNumber());
        String url = "redirect:info/" + userCheck.getId();
        userService.save(userCheck);
        HttpSession session = request.getSession(false);
        session.removeAttribute("user");
        HttpSession session1 = request.getSession();
        session1.setAttribute(CommonConstants.SESSION_USER, userCheck);
        rd.addFlashAttribute(CommonConstants.SUCCESS,
                messageSource.getMessage("update_success", null, Locale.getDefault()));
        ModelAndView mv = new ModelAndView(url);
        return mv;
    }

    @GetMapping("/change-password/{id}")
    public ModelAndView changePassword(@PathVariable int id, HttpServletRequest request){

        ModelAndView mv = new ModelAndView();
        boolean auth = Middleware.middleware(request);

        if (auth) {
            User userInformation = userService.getUserById(id);
            mv = new ModelAndView("public/changePassword");
            mv.addObject("userInformation",userInformation);
        } else {
            mv = new ModelAndView("redirect:/user/login");
        }
        return mv;
    }

    @GetMapping("/forgot-password/{id}")
    public ModelAndView forgotPassword(@PathVariable int id, HttpServletRequest request){

            ModelAndView mv = new ModelAndView();
            User userInformation = userService.getUserById(id);
            mv = new ModelAndView("public/postForgotPassword");
            mv.addObject("userInformation",userInformation);

            return mv;
    }

    @PostMapping("/change-password/{id}")
    public ModelAndView changePasswordPost(@PathVariable int id, HttpServletRequest request,
                                           @RequestParam("rePassword") String rePassword,
                                           @RequestParam("password") String password,RedirectAttributes rd){

        String url = "redirect:/user/info/" + id;
        System.out.println(rePassword);
        System.out.println(password);
        ModelAndView mv = new ModelAndView();
        boolean auth = Middleware.middleware(request);
        if (auth) {
            User userInformation = userService.getUserById(id);
            if (!password.equalsIgnoreCase(rePassword)) {
                rd.addFlashAttribute(CommonConstants.MSG_REGISTER_ERROR, messageSource.getMessage("password_and_repassword", null, Locale.getDefault()));
                mv = new ModelAndView(url);
            } else {
                String passwordMD5 = encrytedPasswordUtils.md5(password);
                userInformation.setPassword(passwordMD5);
                User userSave = userService.save(userInformation);
                rd.addFlashAttribute(CommonConstants.SUCCESS, messageSource.getMessage("update_success", null, Locale.getDefault()));
                mv = new ModelAndView(url);
            }
        } else {
            mv = new ModelAndView("redirect:/user/login");
        }
        return mv;
    }

    @PostMapping("/forgot-password/{id}")
    public ModelAndView forgotPasswordPost(@PathVariable int id, HttpServletRequest request,
                                           @RequestParam("rePassword") String rePassword,
                                           @RequestParam("password") String password,RedirectAttributes rd){

        String url = "redirect:/user/login";
        System.out.println(rePassword);
        System.out.println(password);
        ModelAndView mv = new ModelAndView();
            User userInformation = userService.getUserById(id);
            if (!password.equalsIgnoreCase(rePassword)) {
                rd.addFlashAttribute(CommonConstants.MSG_REGISTER_ERROR, messageSource.getMessage("password_and_repassword", null, Locale.getDefault()));
                mv = new ModelAndView(url);
            } else {
                String passwordMD5 = encrytedPasswordUtils.md5(password);
                userInformation.setPassword(passwordMD5);
                User userSave = userService.save(userInformation);
                rd.addFlashAttribute(CommonConstants.MSG_REGISTER_SUCCESS, messageSource.getMessage("register_success", null, Locale.getDefault()));
                mv = new ModelAndView(url);
            }
        return mv;
    }

    @GetMapping("/forgot-password")
    public  ModelAndView getFogot(){
        ModelAndView mv = new ModelAndView("public/forgotPassword");
        return mv;
    }

    @PostMapping("/forgot-password")
    public  ModelAndView comfirm(HttpServletRequest request, RedirectAttributes rd){
        String email = request.getParameter("email");
        User user = userService.checkEmailExist(email);
        ModelAndView mv = new ModelAndView();
        if (user != null){
            String link = "http://localhost:8080/user/forgot-password/" + user.getId();
            String html = "<div  class=\"container-fluid\" style=\"text-align: center\">\n" +
                    "    <p style=\"font-size: 20px;font-weight: bold;color: #aaa;margin-top: 10px\">Click <a href="+link+" style=\\\"color: white;text-decoration: none\\\">here</a> to reset password</p>\n" +
                    "</div>";

            try {
                MailUtil.sendHtmlMail(this.javaMailSenderImpl,email,"Thông báo",html);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            rd.addFlashAttribute(CommonConstants.CONFIRM_AWAIT,
                    messageSource.getMessage("check_mail", null, Locale.getDefault()));
            mv = new ModelAndView("redirect:/user/forgot-password");
        } else{

            mv = new ModelAndView("redirect:/user/forgot-password");
            rd.addFlashAttribute(CommonConstants.FOGOT_ERROR,
                    messageSource.getMessage("email_not_exited", null, Locale.getDefault()));
        }


        return mv;
    }
}
