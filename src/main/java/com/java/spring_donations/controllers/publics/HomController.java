package com.java.spring_donations.controllers.publics;


import com.java.spring_donations.domain.Donation;
import com.java.spring_donations.services.impl.DonationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class HomController {

    @Autowired
    DonationServiceImpl donationService;

    @ModelAttribute
    public void addModel(Model model, @RequestParam("page") Optional<Integer> page)
    {
        Sort sort = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(page.orElse(0), 5);
        Page<Donation> donationPage = donationService.findAll(pageable);
        List<Donation> list = donationService.findAll();

        int numberPage = list.size() / 5;
        if (list.size() % 5 != 0){
            numberPage = numberPage +1;
        }
        List<Donation> donationList = list.stream().limit(numberPage).collect(Collectors.toList());
        model.addAttribute("donationList", donationList);
        model.addAttribute("list", donationPage);
        model.addAttribute("size", list);
        model.addAttribute("numberPage",page.orElse(0).intValue());
    }

    @GetMapping({"/" , "/index"})
    public ModelAndView loadHomePage()
    {
        ModelAndView mv = new ModelAndView("public/home");
        mv.addObject("activeHome",true);
        return mv;
    }
}
