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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class AbsenceController {
    @Autowired
    UsersInfoService userInfo;

    //fiecare pagina web are un controller atasat
    //care returneaza pagina web si completeaza
    //tagurile JSTL cu informatiile luate
    //in mare parte din baza de date
    @GetMapping("/absence")
    public String absence(Model model) {
        model.addAttribute("isman", userInfo.isManager());
        return "absence";
    }

    //aceasta mapare este folosita pentru a prelua datele introduse
    //de catre utilizator in pagina web intr-un form
    //datele sunt preluate, verificate cu baza de date daca indeplinesc
    //constrangerile, daca sunt nule si apoi sunt introduse in baza de date
    @RequestMapping(value = "/absence/send", method = RequestMethod.POST)
    public RedirectView addAbsence(@RequestParam(value = "absence_date") String absence_date,
                                   @RequestParam(value = "hour1_absence") String hour1_absence,
                                   @RequestParam(value = "hour2_absence") String hour2_absence,
                                   @RequestParam(value = "recovery_date") String recovery_date,
                                   @RequestParam(value = "hour1_recovery") String hour1_recovery,
                                   @RequestParam(value = "hour2_recovery") String hour2_recovery,
                                   @RequestParam(value = "manager_name") String manager_name,
                                   final RedirectAttributes redirect) throws ParseException {

        SimpleDateFormat obj = new SimpleDateFormat("HH:mm");
        Date date1 = obj.parse(hour1_absence);
        Date date2 = obj.parse(hour2_absence);
        Date date3 = obj.parse(hour1_recovery);
        Date date4 = obj.parse(hour2_recovery);

        if (StringUtils.isBlank(absence_date)) {
            redirect.addFlashAttribute("errorMessage", "Data de invoire invalida!");
            return new RedirectView("/absence");
        }

        if (StringUtils.isBlank(recovery_date)) {
            redirect.addFlashAttribute("errorMessage", "Data de recuperare invalida!");
            return new RedirectView("/absence");
        }

        if(date1.compareTo(date2) == 0){
            redirect.addFlashAttribute("errorMessage", "Orele de invoire sunt egale!");
            return new RedirectView("/absence");
        }
        if(date1.compareTo(date2) > 0){
            redirect.addFlashAttribute("errorMessage", "Orele de invoire nu sunt in ordinea potrivita!");
            return new RedirectView("/absence");
        }

        if(date3.compareTo(date4) == 0){
            redirect.addFlashAttribute("errorMessage", "Orele de recuperare sunt egale!");
            return new RedirectView("/absence");
        }
        if(date3.compareTo(date4) > 0){
            redirect.addFlashAttribute("errorMessage", "Orele de invoire nu sunt in ordinea potrivita!");
            return new RedirectView("/absence");
        }
        long l1 = date1.getTime() - date2.getTime();
        long l2 = date3.getTime() - date4.getTime();
        if(l1 != l2){
            redirect.addFlashAttribute("errorMessage", "Intervalele nu sunt egale!");
            return new RedirectView("/absence");
        }

        userInfo.addAbsence(absence_date, hour1_absence, hour2_absence, recovery_date, hour1_recovery, hour2_recovery, manager_name);

        redirect.addFlashAttribute("errorMessage", "Cerere creata cu succes!");
        return new RedirectView("/absence");
    }
}