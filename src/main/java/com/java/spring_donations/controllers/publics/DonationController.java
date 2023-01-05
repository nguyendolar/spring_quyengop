package com.java.spring_donations.controllers.publics;

import com.java.spring_donations.domain.Donation;
import com.java.spring_donations.domain.User;
import com.java.spring_donations.domain.UserDonation;
import com.java.spring_donations.services.DonationService;
import com.java.spring_donations.services.UserDonationService;
import com.java.spring_donations.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/donation")
public class DonationController {

    @Autowired
    DonationService donationService;

    @Autowired
    UserDonationService userDonationService;

    @Autowired
    UserService userService;

    @GetMapping({"/detail/{id}" })
    public ModelAndView getDetail(@PathVariable int id, HttpServletRequest request)
    {
        ModelAndView mv = new ModelAndView("public/detail");
        Donation donation = donationService.findDonationsById(id);
        List<UserDonation> userDonationList = userDonationService.findUserDonationByDonation(donation);
        mv.addObject("donation",donation);
        mv.addObject("userDonationList",userDonationList);
        return mv;
    }

    @PostMapping("/add")
    public ModelAndView add(@ModelAttribute("UserDonation") UserDonation userDonation,
                            @RequestParam("idUser") int idUser, @RequestParam("idDonation") int idDonation,
                            RedirectAttributes rd){
        String url = "redirect:/donation/detail/" + idDonation;
        ModelAndView mv = new ModelAndView(url);
        if (idUser != 0){
            User user = userService.getUserById(idUser);
            Donation donation = donationService.findDonationsById(idDonation);
            userDonation.setUser(user);
            userDonation.setDonation(donation);
            userDonation.setStatus(0);
            userDonation.setCreatedAt(java.time.LocalDate.now().toString());
            userDonationService.save(userDonation);
            rd.addFlashAttribute("msg", "success");
        }else{
            Donation donation = donationService.findDonationsById(idDonation);
            userDonation.setDonation(donation);
            userDonation.setStatus(0);
            userDonation.setCreatedAt(java.time.LocalDate.now().toString());
            userDonationService.save(userDonation);
            rd.addFlashAttribute("msg", "success");
        }
        return mv;
    }

    @PostMapping("/add-home")
    public ModelAndView addHome(@ModelAttribute("UserDonation") UserDonation userDonation,
                            @RequestParam("idUser") int idUser, @RequestParam("idDonation") int idDonation,
                            RedirectAttributes rd){
        ModelAndView mv = new ModelAndView("redirect:/");
        if (idUser != 0){
            User user = userService.getUserById(idUser);
            Donation donation = donationService.findDonationsById(idDonation);
            userDonation.setUser(user);
            userDonation.setDonation(donation);
            userDonation.setStatus(0);
            userDonation.setCreatedAt(java.time.LocalDate.now().toString());
            userDonationService.save(userDonation);
            rd.addFlashAttribute("msg", "success");
        }else{
            Donation donation = donationService.findDonationsById(idDonation);
            userDonation.setDonation(donation);
            userDonation.setStatus(0);
            userDonation.setCreatedAt(java.time.LocalDate.now().toString());
            userDonationService.save(userDonation);
            rd.addFlashAttribute("msg", "success");
        }
        return mv;
    }
}
