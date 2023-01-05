package com.java.spring_donations.controllers.admin;

import com.java.spring_donations.domain.Donation;
import com.java.spring_donations.domain.Role;
import com.java.spring_donations.domain.User;
import com.java.spring_donations.domain.UserDonation;
import com.java.spring_donations.services.DonationService;
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

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

import static com.java.spring_donations.utils.Middleware.middlewareAdmin;

@Controller
@RequestMapping("/ql-donation")
public class AdminDonationController {

    @Autowired
    DonationService donationService;

    @Autowired
    UserDonationService userDonationService;

    EncrytedPasswordUtils encrytedPasswordUtils = new EncrytedPasswordUtils();

    @GetMapping({"/" , "/index"})
    public ModelAndView index(HttpServletRequest request)
    {
        ModelAndView mv = new ModelAndView();
        mv = new ModelAndView("admin/donation");
        List<Donation> donationList = donationService.findAll();
        mv.addObject("list",donationList);
        return mv;
    }

    @PostMapping("/add")
    public ModelAndView add(HttpServletRequest request, RedirectAttributes rd){
        ModelAndView mv = new ModelAndView("redirect:/ql-donation/");
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        String tochuc = request.getParameter("tochuc");
        String sdt = request.getParameter("sdt");
        String noidung = request.getParameter("noidung");
        String startday = java.time.LocalDate.now().toString();
        Donation donation = new Donation();
        donation.setCode(code);
        donation.setName(name);
        donation.setStartDate(startday);
        //donation.setEndDate(endday);
        donation.setOrganizationName(tochuc);
        donation.setPhoneNumber(sdt);
        donation.setDescription(noidung);
        donation.setCreatedAt(java.time.LocalDate.now().toString());
        donation.setMoney(0);
        donation.setStatus(1);
        donationService.save(donation);
        rd.addFlashAttribute("msg", "success");
        return mv;
    }

    @PostMapping("/update")
    public ModelAndView update(HttpServletRequest request, RedirectAttributes rd){
        ModelAndView mv = new ModelAndView("redirect:/ql-donation/");
        int id = Integer.parseInt(request.getParameter("id"));
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        String startday = request.getParameter("start");
        String endday = request.getParameter("end");
        String tochuc = request.getParameter("tochuc");
        String sdt = request.getParameter("sdt");
        String noidung = request.getParameter("noidung");
        Donation donation = donationService.findDonationsById(id);
        donation.setCode(code);
        donation.setName(name);
        donation.setOrganizationName(tochuc);
        donation.setPhoneNumber(sdt);
        donation.setDescription(noidung);
        donationService.save(donation);
        rd.addFlashAttribute("msg", "success");
        return mv;
    }

    @PostMapping("/delete")
    public ModelAndView delete(@RequestParam("idDonation") int idDonation, RedirectAttributes rd){
        ModelAndView mv = new ModelAndView("redirect:/ql-donation/");
        Donation dona = donationService.findDonationsById(idDonation);
        List<UserDonation> check = userDonationService.findUserDonationByDonation(dona);
        if(check.size() > 0){
            rd.addFlashAttribute("msg", "error");
        }
        else{
            donationService.delete(idDonation);
            rd.addFlashAttribute("msg", "success");
        }
        return mv;
    }

    @PostMapping("/donating")
    public ModelAndView donating(@RequestParam("idD") int idD, RedirectAttributes rd){
        ModelAndView mv = new ModelAndView("redirect:/ql-donation/");
        Donation donation = donationService.findDonationsById(idD);
        donation.setStatus(1);
        donationService.save(donation);
        rd.addFlashAttribute("msg", "success");
        return mv;
    }

    @PostMapping("/endDo")
    public ModelAndView endDo(@RequestParam("idD") int idD, RedirectAttributes rd){
        ModelAndView mv = new ModelAndView("redirect:/ql-donation/");
        Donation donation = donationService.findDonationsById(idD);
        String endDay = java.time.LocalDate.now().toString();
        donation.setStatus(2);
        donation.setEndDate(endDay);
        donationService.save(donation);
        rd.addFlashAttribute("msg", "success");
        return mv;
    }

    @PostMapping("/closeDo")
    public ModelAndView closeDo(@RequestParam("idD") int idD, RedirectAttributes rd){
        ModelAndView mv = new ModelAndView("redirect:/ql-donation/");
        Donation donation = donationService.findDonationsById(idD);
        donation.setStatus(3);
        donationService.save(donation);
        rd.addFlashAttribute("msg", "success");
        return mv;
    }

    @GetMapping({"/detail/{id}" })
    public ModelAndView getDetail(@PathVariable int id, HttpServletRequest request)
    {
        ModelAndView mv = new ModelAndView("admin/detail");
        Donation donation = donationService.findDonationsById(id);
        List<UserDonation> userDonationList = userDonationService.findUserDonationByDonation(donation);
        mv.addObject("donation",donation);
        mv.addObject("userDonationList",userDonationList);
        return mv;
    }
    @PostMapping("/accept")
    public ModelAndView accept(@RequestParam("idUD") int idUD,@RequestParam("idDo") int idDo,@RequestParam("donationmoney") int donationmoney, RedirectAttributes rd){
        String url = "redirect:/ql-donation/detail/" + idDo ;
        ModelAndView mv = new ModelAndView(url);
        UserDonation userDonation = userDonationService.findUserDonationById(idUD);
        userDonation.setStatus(1);
        userDonationService.save(userDonation);
        Donation donation = donationService.findDonationsById(idDo);
        int money = donation.getMoney() + donationmoney;
        donation.setMoney(money);
        donationService.save(donation);
        rd.addFlashAttribute("msg", "success");
        return mv;
    }

    @PostMapping("/cancel")
    public ModelAndView cancel(@RequestParam("idUD") int idUD,@RequestParam("idDo") int idDo, RedirectAttributes rd){
        String url = "redirect:/ql-donation/detail/" + idDo ;
        ModelAndView mv = new ModelAndView(url);
        UserDonation userDonation = userDonationService.findUserDonationById(idUD);
        userDonation.setStatus(2);
        userDonationService.save(userDonation);
        rd.addFlashAttribute("msg", "success");
        return mv;
    }
}
