package com.presence.controller;

import com.presence.service.CompaniesService;
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
public class CompaniesController {
    @Autowired
    private UsersInfoService userInfo;

    @Autowired
    private CompaniesService company;

    private String current_company = "nothing";
    private String current_location = "nothing";

    //un alt controller care se ocupa de firme
    //in prima instanta afisam in pagina web
    //intr-un drop-down list, firmele disponibile
    @RequestMapping(value = "/companies", method = RequestMethod.GET)
    public String companies(Model model) throws ParseException {
        List<String> list  = company.getCompanies();
        model.addAttribute("companies_list", list);
        model.addAttribute("current_company", current_company);
        model.addAttribute("current_location", current_location);

        model.addAttribute("isman", userInfo.isManager());
        if(current_company != "nothing"){
            company.companyDetails().forEach((attribute, value) -> {
                model.addAttribute(attribute, value);
            });
            if(current_location != "nothing"){
                company.locationDetails().forEach((attribute, value) -> {
                    model.addAttribute(attribute, value);
                });
            }
            model.addAttribute("listOfEntries", company.getLocations());
        }

        return "companies";
    }

    //aceasta mapare se ocupa de un formular care ne trimite compania selectata de catre utilizator
    //si vom sea anumite conditii pentru a-i oferi utilizatorului datele despre acea firma
    @RequestMapping(value = "/companies/list", method = RequestMethod.POST)
    public RedirectView companiesList(@RequestParam(value = "company_select_drop") String company_select_drop){
        current_company = company_select_drop;
        company.setCompany(current_company);
        current_location = "nothing";
        company.setLocation(current_location);
        return new RedirectView("/companies");
    }

    //mapare pentru updatarea informatiilor despre firma
    @RequestMapping(value = "/companies/update", method = RequestMethod.POST)
    public RedirectView updateCompany(@RequestParam(value = "name") String name,
                                          @RequestParam(value = "cui") String cui,
                                          @RequestParam(value = "cif") String cif,
                                          @RequestParam(value = "address") String address,
                                          RedirectAttributes redirect){
        HashMap<String, String> buffer = new HashMap<>();
        if (StringUtils.isBlank(name)) {
            redirect.addFlashAttribute("errorMessage", "Introduceti numele firmei!");
            return new RedirectView("/companies");
        }
        if (StringUtils.isBlank(cui)) {
            redirect.addFlashAttribute("errorMessage", "Introduceti cui-ul firmei!");
            return new RedirectView("/companies");
        }
        if (StringUtils.isBlank(cif)) {
            redirect.addFlashAttribute("errorMessage", "Introduceti cif-ul firmei!");
            return new RedirectView("/companies");
        }
        if (StringUtils.isBlank(address)) {
            redirect.addFlashAttribute("errorMessage", "Introduceti adresa firmei!");
            return new RedirectView("/companies");
        }

        buffer.put("nume_firma", name);
        buffer.put("cui", cui);
        buffer.put("cif", cif);
        buffer.put("adresa_sediu_social", address);
        company.updateCompany(buffer);

        current_company = name;
        company.setCompany(current_company);

        redirect.addFlashAttribute("errorMessage", "Companie updatata cu succes!");
        return new RedirectView("/companies");
    }

    //mapare pentru updatarea informatiilor punctului de lucru selectat
    @RequestMapping(value = "/companies/updatepoint", method = RequestMethod.POST)
    public RedirectView updateLocation(@RequestParam(value = "point_name") String point_name,
                                       @RequestParam(value = "point_address") String point_address,
                                       @RequestParam(value = "hour_start_program") String hour_start_program,
                                       @RequestParam(value = "hour_end_program") String hour_end_program,
                                       @RequestParam(value = "hour_start_recovery") String hour_start_recovery,
                                       @RequestParam(value = "hour_end_recovery") String hour_end_recovery,
                                       RedirectAttributes redirect){
        HashMap<String, String> buffer = new HashMap<>();
        if (StringUtils.isBlank(point_address)) {
            redirect.addFlashAttribute("errorMessage", "Introduceti adresa punctului de lucru!");
            return new RedirectView("/companies");
        }
        if (StringUtils.isBlank(point_name)) {
            redirect.addFlashAttribute("errorMessage", "Introduceti numele punctului de lucru!");
            return new RedirectView("/companies");
        }

        buffer.put("nume", point_name);
        buffer.put("adresa", point_address);
        buffer.put("ora_inceput_program", hour_start_program + ":00");
        buffer.put("ora_sfarsit_program", hour_end_program + ":00");
        buffer.put("ora_minima_recuperare", hour_start_recovery + ":00");
        buffer.put("ora_maxima_recuperare", hour_end_recovery + ":00");
        company.updateLocation(buffer);

        current_location = point_name;
        company.setLocation(current_location);

        redirect.addFlashAttribute("errorMessage", "Punct updatat cu succes!");
        return new RedirectView("/companies");
    }

    //mapare pentru crearea unui nou punct de lucru
    @RequestMapping(value = "/companies/newpoint", method = RequestMethod.POST)
    public RedirectView newLocation(@RequestParam(value = "point_name_new") String point_name_new,
                                       @RequestParam(value = "point_address_new") String point_address_new,
                                       @RequestParam(value = "hour_start_program_new") String hour_start_program_new,
                                       @RequestParam(value = "hour_end_program_new") String hour_end_program_new,
                                       @RequestParam(value = "hour_start_recovery_new") String hour_start_recovery_new,
                                       @RequestParam(value = "hour_end_recovery_new") String hour_end_recovery_new,
                                       RedirectAttributes redirect){
        HashMap<String, String> buffer = new HashMap<>();
        if (StringUtils.isBlank(point_address_new)) {
            redirect.addFlashAttribute("errorMessage", "Introduceti adresa punctului de lucru!");
            return new RedirectView("/companies");
        }
        if (StringUtils.isBlank(point_name_new)) {
            redirect.addFlashAttribute("errorMessage", "Introduceti numele punctului de lucru!");
            return new RedirectView("/companies");
        }

        buffer.put("nume", point_name_new);
        buffer.put("adresa", point_address_new);
        buffer.put("ora_inceput_program", hour_start_program_new + ":00");
        buffer.put("ora_sfarsit_program", hour_end_program_new + ":00");
        buffer.put("ora_minima_recuperare", hour_start_recovery_new + ":00");
        buffer.put("ora_maxima_recuperare", hour_end_recovery_new + ":00");
        company.newLocation(buffer);

        current_location = point_name_new;
        company.setLocation(current_location);

        redirect.addFlashAttribute("errorMessage", "Punct adaugat!");
        return new RedirectView("/companies");
    }

    //metoda pentru crearea unei noi companii
    @RequestMapping(value = "/companies/newcompany", method = RequestMethod.POST)
    public RedirectView createCompany(@RequestParam(value = "new_name") String name,
                                          @RequestParam(value = "new_cui") String cui,
                                          @RequestParam(value = "new_cif") String cif,
                                          @RequestParam(value = "new_address") String address,
                                          RedirectAttributes redirect){
        HashMap<String, String> buffer = new HashMap<>();
        if (StringUtils.isBlank(name)) {
            redirect.addFlashAttribute("errorMessage", "Introduceti numele firmei!");
            return new RedirectView("/companies");
        }
        if (StringUtils.isBlank(cui)) {
            redirect.addFlashAttribute("errorMessage", "Introduceti cui-ul firmei!");
            return new RedirectView("/companies");
        }
        if (StringUtils.isBlank(cif)) {
            redirect.addFlashAttribute("errorMessage", "Introduceti cif-ul firmei!");
            return new RedirectView("/companies");
        }
        if (StringUtils.isBlank(address)) {
            redirect.addFlashAttribute("errorMessage", "Introduceti adresa firmei!");
            return new RedirectView("/companies");
        }

        buffer.put("nume_firma", name);
        buffer.put("cui", cui);
        buffer.put("cif", cif);
        buffer.put("adresa_sediu_social", address);
        company.newCompany(buffer);

        current_company = name;
        company.setCompany(current_company);

        redirect.addFlashAttribute("errorMessage", "Companie adaugata!");
        return new RedirectView("/companies");
    }

    //metoda pentru selectarea punctului de lucru de catre utilizator
    @RequestMapping(value = "/companies/selectlocation", method = RequestMethod.POST)
    public RedirectView locationDetails(@RequestParam(value = "location_select") String location_select){
        current_location = location_select;
        company.setLocation(current_location);
        return new RedirectView("/companies");
    }

    //metoda pentru stergerea punctului de lucru selectat in momentul de fata
    @RequestMapping(value = "/companies/deletepoint", method = RequestMethod.POST)
    public RedirectView deleteLocation(){
        company.deleteLocation();
        current_location = "nothing";
        return new RedirectView("/companies");
    }
}
