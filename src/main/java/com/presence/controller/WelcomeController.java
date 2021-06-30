package com.presence.controller;

import com.presence.service.CompaniesService;
import com.presence.service.TeamsService;
import com.presence.service.UsersInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.List;

@Controller
public class WelcomeController {
    @Autowired
    UsersInfoService usersInfoService;
    String current_user = "";
    String nr_hours = "";
    String c_month = "";
    String c_year = "";
    String c_companay = "";
    List<HashMap<Integer, String>> list5;
    List<HashMap<Integer, String>> list6;
    List<HashMap<Integer, String>> list7;
    List<HashMap<Integer, String>> list8;
    List<HashMap<Integer, String>> list9;
    String start_date1 = "";
    String end_date1 = "";
    String company1 = "";
    String nr_hours1 = "";
    String c_date1 = "";
    String c_ora1 = "";
    String c_ore1 = "";
    String c_year1 = "";
    String c_date2 = "";
    String team1 = "";

    @Autowired
    private CompaniesService company;

    @Autowired
    private TeamsService teamsService;

    //prima pagina a aplicatiei unde afisam numele utlizatorului logat
    //iar daca acesta este manager, vom afisa anumite informatii suplimentare
    //cu privire la angajatii firmei
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcome(Model model) {
        List<String> list  = usersInfoService.getUsers();
        List<String> listt  = teamsService.getAllTeams();
        model.addAttribute("list_empl_c4", listt);

        model.addAttribute("user_list", list);
        model.addAttribute("employee", usersInfoService.getLoggedInUser());

        List<String> list1  = company.getCompanies();
        model.addAttribute("companies_list", list1);
        model.addAttribute("companies_list1", list1);

        model.addAttribute("display_user", usersInfoService.getEmployeeToEdit());

        model.addAttribute("isman", usersInfoService.isManager());
        model.addAttribute("list_of_max", usersInfoService.getSalMaxPerCompany());
        model.addAttribute("list_of_maxs", usersInfoService.getEmployeesWithMaxSalary());

        if(nr_hours != "") {
            model.addAttribute("number_hours", nr_hours);
            model.addAttribute("company_select_drop", c_companay);
            model.addAttribute("month", c_month);
            model.addAttribute("year", c_year);
        }
        if(list5 != null) {
            model.addAttribute("list_empl_c", list5);
            model.addAttribute("company_select_drop1", company1);
            model.addAttribute("start_date", start_date1);
            model.addAttribute("end_date", end_date1);
            model.addAttribute("nr_hours", nr_hours1);
        }
        if(list6 != null) {
            model.addAttribute("list_empl_c1", list6);
            model.addAttribute("c_date", c_date1);
            model.addAttribute("c_ora", c_ora1);
            model.addAttribute("c_ore", c_ore1);
        }
        if(list7 != null) {
            model.addAttribute("list_empl_c2", list7);
            model.addAttribute("c_year", c_year1);
        }
        if(list8 != null) {
            model.addAttribute("list_empl_c3", list8);
            model.addAttribute("c_date1", c_date2);
        }

        if(list9 != null) {
            model.addAttribute("list_empl_c5", list9);
            model.addAttribute("team", team1);
        }
        return "welcome";
    }


    //managerul poate selecta utilizatul pentru care acesta doreste sa ii modifice informatiile personale
    //sau cele referitoare la firma
    @RequestMapping(value = "/welcome/send_user", method = RequestMethod.POST)
    public RedirectView changeUserDetails(@RequestParam(value = "user_select") String user_select){

        current_user = user_select;
        usersInfoService.setEmployeeToEdit(current_user);
        return new RedirectView("/");
    }

    //avem cateva interogari complexe afisate explicit in pagina web
    @RequestMapping(value = "/welcome/getdata", method = RequestMethod.POST)
    public RedirectView getData(@RequestParam(value = "month") String month,
                                @RequestParam(value = "year") String year,
                                @RequestParam(value = "company_select_drop") String company){

        List<HashMap<Integer, String>> list2 = usersInfoService.hoursWorkedAtFirmInMonth(company, month, year);
        if(list2.size() == 1)
            nr_hours = list2.get(0).get(1);

        c_month = month;
        c_year = year;
        c_companay = company;
        return new RedirectView("/");
    }

    @RequestMapping(value = "/welcome/getdata1", method = RequestMethod.POST)
    public RedirectView getData1(@RequestParam(value = "start_date") String start_date,
                                 @RequestParam(value = "end_date") String end_date,
                                 @RequestParam(value = "company_select_drop1") String company,
                                 @RequestParam(value = "nr_hours") String nr_hours){

        list5 = usersInfoService.getData1(start_date, end_date, company, nr_hours);
        start_date1 = start_date;
        end_date1 = end_date;
        company1 = company;
        nr_hours1 = nr_hours;

        return new RedirectView("/");
    }

    @RequestMapping(value = "/welcome/getdata2", method = RequestMethod.POST)
    public RedirectView getData2(@RequestParam(value = "c_date") String c_date,
                                 @RequestParam(value = "c_ora") String c_ora,
                                 @RequestParam(value = "c_ore") String c_ore){

        list6 = usersInfoService.getData2(c_date, c_ora, c_ore);
        c_date1 = c_date;
        c_ora1 = c_ora;
        c_ore1 = c_ore;

        return new RedirectView("/");
    }

    @RequestMapping(value = "/welcome/getdata3", method = RequestMethod.POST)
    public RedirectView getData3(@RequestParam(value = "c_year") String c_year){

        list7 = usersInfoService.getData3(c_year);
        c_year1 = c_year;

        return new RedirectView("/");
    }

    @RequestMapping(value = "/welcome/getdata4", method = RequestMethod.POST)
    public RedirectView getData4(@RequestParam(value = "c_date1") String data){

        list8 = usersInfoService.getData4(data);
        c_date2 = data;

        return new RedirectView("/");
    }

    @RequestMapping(value = "/welcome/getdata5", method = RequestMethod.POST)
    public RedirectView getData5(@RequestParam(value = "team") String team){

        list9 = usersInfoService.getData5(team);
        team1 = team;

        return new RedirectView("/");
    }
}