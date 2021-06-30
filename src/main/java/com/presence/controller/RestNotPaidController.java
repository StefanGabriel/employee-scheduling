package com.presence.controller;

import com.presence.service.UsersInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RestNotPaidController {
    @Autowired
    UsersInfoService userInfo;

    @GetMapping("/rest_not_paid")
    public String register(Model model) {
        model.addAttribute("isman", userInfo.isManager());
        return "rest_not_paid";
    }

    //preluam datele introduse de utilizator si le ducem in baza de date
    //in tabela corespunzatoare
    //in aceasta pagina utiliatorul isi poate crea cereri de concediu fara plata
    @RequestMapping(value = "/rest_not_paid/send", method = RequestMethod.POST)
    public RedirectView addRestGetPaid(@RequestParam(value = "start_date") String start_date,
                                       @RequestParam(value = "finish_date") String finish_date,
                                       @RequestParam(value = "days_number") String days_number,
                                       @RequestParam(value = "manager_name") String manager_name,
                                       final RedirectAttributes redirect){

        if (StringUtils.isBlank(start_date)) {
            redirect.addFlashAttribute("errorMessage", "Data de inceput este invalida!");
            return new RedirectView("/rest_not_paid");
        }

        if (StringUtils.isBlank(finish_date)) {
            redirect.addFlashAttribute("errorMessage", "Data de sfarsit este invalida!");
            return new RedirectView("/rest_not_paid");
        }

        if (StringUtils.isBlank(days_number)) {
            redirect.addFlashAttribute("errorMessage", "Numarul de zile lipseste!");
            return new RedirectView("/rest_not_paid");
        }

        if (StringUtils.isBlank(manager_name)) {
            redirect.addFlashAttribute("errorMessage", "Numele managerului lipseste!");
            return new RedirectView("/rest_not_paid");
        }

        userInfo.addRestNotPaid(start_date, finish_date, days_number, manager_name);

        redirect.addFlashAttribute("errorMessage", "Cererea a fost inregistrata cu succes. Daca ceva este in neregula cu aceasta cerere, veti fi contactat de catre un manager!");
        return new RedirectView("/rest_not_paid");
    }
}