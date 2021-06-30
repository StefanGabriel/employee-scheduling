package com.presence.controller;

import com.presence.database.DatabaseConnection;
import com.presence.service.CompaniesService;
import com.presence.service.TeamsService;
import com.presence.service.UsersInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

@Controller
public class TeamsController {
    @Autowired
    private UsersInfoService userInfo;

    @Autowired
    private TeamsService teamsService;

    @Autowired
    private CompaniesService company;

    private String current_company = "nothing";
    private String current_team = "nothing";

    //in pagina echipelor utilizatorul selecteaza mai intai compania
    //pentru care doreste sa afiseze echipele
    @RequestMapping(value = "/teams", method = RequestMethod.GET)
    public String teams(Model model) throws ParseException {
        List<String> list  = company.getCompanies();
        model.addAttribute("companies_list", list);
        model.addAttribute("current_company", current_company);
        model.addAttribute("current_team", current_team);

        model.addAttribute("isman", userInfo.isManager());

        if(current_company != "nothing"){
            model.addAttribute("listOfEntries", teamsService.getTeams());
            model.addAttribute("employee_list", teamsService.getEmployees());
            if(current_team != "nothing"){
                model.addAttribute("team_employees", teamsService.getTeamEmployees());
                model.addAttribute("employee_add_list", teamsService.getEmployeesNotInTeam());
                teamsService.teamDetails().forEach((attribute, value) -> {
                    model.addAttribute(attribute, value);
                });
            }
        }

        return "teams";
    }

    //preluam firma selectata de catre utilizator
    @RequestMapping(value = "/teams/companies/list", method = RequestMethod.POST)
    public RedirectView teamsCompaniesList(@RequestParam(value = "company_select_drop") String company_select_drop){

        current_company = company_select_drop;
        teamsService.setCompany(current_company);
        current_team = "nothing";
        teamsService.setTeam(current_team);
        return new RedirectView("/teams");
    }

    //utilizatorul selecteaza echipa si ii vom afisa
    //informatii referitoare la aceasta echipa
    @RequestMapping(value = "/teams/teamselect", method = RequestMethod.POST)
    public RedirectView teamSelect(@RequestParam(value = "team_select") String team_select){

        current_team = team_select;
        teamsService.setTeam(current_team);
        return new RedirectView("/teams");
    }

    //stergem echipa selectata de utilizator
    @RequestMapping(value = "/teams/teamdelete", method = RequestMethod.POST)
    public RedirectView teamDelete(@RequestParam(value = "team_delete") String team_delete){

        if(current_team == team_delete)
            current_team = "nothing";
        teamsService.deleteTeam(team_delete);
        return new RedirectView("/teams");
    }

    //stergem angajat din echipa
    @RequestMapping(value = "/teams/employeedelete", method = RequestMethod.POST)
    public RedirectView employeeDelete(@RequestParam(value = "employee_delete") String employee_delete){

        teamsService.deleteEmployee(employee_delete);
        return new RedirectView("/teams");
    }

    //modificam informatiile despre echipa
    @RequestMapping(value = "/teams/updateteam", method = RequestMethod.POST)
    public RedirectView updateLocation(@RequestParam(value = "team_name") String team_name,
                                       @RequestParam(value = "team_leader") String team_leader,
                                       RedirectAttributes redirect){
        HashMap<String, String> buffer = new HashMap<>();
        if (StringUtils.isBlank(team_name)) {
            redirect.addFlashAttribute("errorMessage", "Introduceti numele echipei!");
            return new RedirectView("/teams");
        }

        if(!teamsService.checkEmployee(team_leader)){
            redirect.addFlashAttribute("errorMessage", "Angajatul nu exista la aceasta firma. Selecteaza un angajat valid!");
            return new RedirectView("/teams");
        }

        buffer.put("team_name", team_name);
        buffer.put("team_leader", team_leader);
        teamsService.updateTeam(buffer);

        current_team = team_name;
        teamsService.setTeam(current_team);

        redirect.addFlashAttribute("errorMessage", "Informatiile despre echipa au fost editate!");
        return new RedirectView("/teams");
    }

    //adaugam o echipa noua firmei
    @RequestMapping(value = "/teams/newteam", method = RequestMethod.POST)
    public RedirectView newTeam(@RequestParam(value = "team_name_new") String team_name_new,
                                @RequestParam(value = "employee_list") String employee_list,
                                RedirectAttributes redirect){
        HashMap<String, String> buffer = new HashMap<>();

        if (StringUtils.isBlank(team_name_new)) {
            redirect.addFlashAttribute("errorMessage", "Introduceti numele echipei!");
            return new RedirectView("/teams");
        }

        buffer.put("team_name", team_name_new);
        buffer.put("team_leader", employee_list);
        teamsService.newTeam(buffer);

        current_team = team_name_new;
        teamsService.setTeam(current_team);

        redirect.addFlashAttribute("errorMessage", "Echipa creata cu succes!");
        return new RedirectView("/teams");
    }

    //adaugam angajat la firma curenta
    @RequestMapping(value = "/teams/addemployee", method = RequestMethod.POST)
    public RedirectView addEmployee(@RequestParam(value = "employee_add_list") String employee_add_list,
                                    RedirectAttributes redirect){
        teamsService.addEmployee(employee_add_list);

        redirect.addFlashAttribute("errorMessage", "Angajat adaugat cu succes!");
        return new RedirectView("/teams");
    }
}
