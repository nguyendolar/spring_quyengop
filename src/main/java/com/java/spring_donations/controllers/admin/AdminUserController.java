package com.java.spring_donations.controllers.admin;

import com.java.spring_donations.domain.Role;
import com.java.spring_donations.domain.User;
import com.java.spring_donations.domain.UserDonation;
import com.java.spring_donations.services.RoleService;
import com.java.spring_donations.services.UserDonationService;
import com.java.spring_donations.services.UserService;
import com.java.spring_donations.utils.EncrytedPasswordUtils;
import com.java.spring_donations.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.jws.WebParam;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.java.spring_donations.utils.Middleware.middlewareAdmin;

@Controller
@RequestMapping("/ql-user")
public class AdminUserController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserDonationService userDonationService;

    @Autowired
    public JavaMailSenderImpl javaMailSenderImpl;

    EncrytedPasswordUtils encrytedPasswordUtils = new EncrytedPasswordUtils();

    @GetMapping({"/" , "/index"})
    public ModelAndView index(HttpServletRequest request)
    {
        ModelAndView mv = new ModelAndView();
        boolean auth = middlewareAdmin(request);
        if (auth) {
            mv = new ModelAndView("admin/account");
        } else {
            mv = new ModelAndView("admin/login");
        }
        List<User> userList = userService.getAll();
        List<Role> roleList = roleService.findAll();
        mv.addObject("list",userList);
        mv.addObject("roleList",roleList);
        return mv;
    }

    @PostMapping("/add")
    public ModelAndView add(@ModelAttribute("user") User user, @RequestParam("idRole") int idRole, RedirectAttributes rd){
        ModelAndView mv = new ModelAndView("redirect:/ql-user/");
        User userExist = userService.checkEmailExist(user.getEmail());
        User userExist1 = userService.findUserByUserName(user.getUserName());
        String passwordMd5 = encrytedPasswordUtils.md5(user.getPassword());
        user.setPassword(passwordMd5);
        if (!Objects.isNull(userExist) || !Objects.isNull(userExist1)){
            rd.addFlashAttribute("msg", "error");
        }else{
            Role role = userService.findRoleById(idRole);
            user.setRole(role);
            userService.save(user);
            rd.addFlashAttribute("msg", "success");
        }
        return mv;
    }

    @PostMapping("/update")
    public ModelAndView update(@ModelAttribute("user") User user, @RequestParam("idRole") int idRole,@RequestParam("idUser") int idUser, RedirectAttributes rd){
        ModelAndView mv = new ModelAndView("redirect:/ql-user/");
        user.setId(idUser);
        Role role = userService.findRoleById(idRole);
        user.setRole(role);
        userService.save(user);
        rd.addFlashAttribute("msg", "success");
        return mv;
    }

    @PostMapping("/delete")
    public ModelAndView delete(@RequestParam("idUser") int idUser, RedirectAttributes rd){
        ModelAndView mv = new ModelAndView("redirect:/ql-user/");
        User u = userService.getUserById(idUser);
        List<UserDonation> check = userDonationService.findUserDonationByUser(u);
        if(check.size() > 0){
            rd.addFlashAttribute("msg", "error");
        }
        else{
            userService.delete(idUser);
            rd.addFlashAttribute("msg", "success");
        }
        return mv;
    }

    @PostMapping("/lock")
    public ModelAndView lock(@RequestParam("idUser") int idUser, RedirectAttributes rd){
        ModelAndView mv = new ModelAndView("redirect:/ql-user/");
        User user = userService.getUserById(idUser);
        user.setStatus(0);
        userService.save(user);
        rd.addFlashAttribute("msg", "success");
        return mv;
    }

    @PostMapping("/un-lock")
    public ModelAndView unLock(@RequestParam("idUser") int idUser, RedirectAttributes rd){
        ModelAndView mv = new ModelAndView("redirect:/ql-user/");
        User user = userService.getUserById(idUser);
        user.setStatus(1);
        userService.save(user);
        rd.addFlashAttribute("msg", "success");
        return mv;
    }
    @PostMapping("/send-mail")
    public ModelAndView sendMail(@RequestParam("idUser") int idUser, RedirectAttributes rd,@RequestParam("note") String note){
        ModelAndView mv = new ModelAndView("redirect:/ql-user/");
        User user = userService.getUserById(idUser);
        user.setNote(note);
        userService.save(user);
        try {
            MailUtil.sendHtmlMail(this.javaMailSenderImpl,user.getEmail(),"Mời bạn tham gia hội quyên góp",note);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        rd.addFlashAttribute("msg", "success");
        return mv;
    }

}
