package com.presence.controller;

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

import java.util.HashMap;

@Controller
public class AdminUserController {
    @Autowired
    private UsersInfoService userInfo;

    //acest controller se ocupa de o pagina
    //asemanatoare cu cea pentru datele personale ale angajatului
    //doar ca aici managerul poate modifica sau insera date suplimentare
    //cu privire la firma, sau alte date despre angajat
    @RequestMapping(value = "/admin_user", method = RequestMethod.GET)
    public String account(Model model) {
        model.addAttribute("isman", userInfo.isManager());
        model.addAttribute("slider", "on");
        model.addAttribute("employee", userInfo.getUserToEdit());
        userInfo.employeeDetailsForAdmin().forEach((attribute, value) -> {
            model.addAttribute(attribute, value);
        });

        return "admin_user";
    }

    @RequestMapping(value = "/admin_user/updatedetails", method = RequestMethod.POST)
    public RedirectView changeUserDetails(@RequestParam(value = "last_name") String last_name,
                                          @RequestParam(value = "first_name") String first_name,
                                          @RequestParam(value = "email") String email,
                                          @RequestParam(value = "cnp") String cnp,
                                          @RequestParam(value = "street") String street,
                                          @RequestParam(value = "number") String number,
                                          @RequestParam(value = "city") String city,
                                          @RequestParam(value = "county") String county,
                                          @RequestParam(value = "country") String country,
                                          @RequestParam(value = "data") String data,
                                          @RequestParam(value = "position") String position,
                                          @RequestParam(value = "state") String state,
                                          @RequestParam(value = "company_name") String company_name,
                                          @RequestParam(value = "ci_series") String ci_series,
                                          @RequestParam(value = "ci_number") String ci_number,
                                          @RequestParam(value = "is_manager") String is_manager,
                                          @RequestParam(value = "money") String money,
                                          @RequestParam(value = "first_day") String first_day,
                                          @RequestParam(value = "working_hours") String working_hours,
                                          RedirectAttributes redirect){

        HashMap<String, String> buffer = new HashMap<>();
        if (StringUtils.isBlank(last_name) || last_name.length() < 3) {
            redirect.addFlashAttribute("errorMessage", "Numele trebuie sa fie format din cel putin 3 caractere!");
            return new RedirectView("/admin_user");
        }

        if (StringUtils.isBlank(first_name) || first_name.length() < 3) {
            redirect.addFlashAttribute("errorMessage", "Prenumele trebuie sa fie format din cel putin 3 caractere!");
            return new RedirectView("/admin_user");
        }

        if (StringUtils.isBlank(email)) {
            redirect.addFlashAttribute("errorMessage", "Email invalid!");
            return new RedirectView("/admin_user");
        }

        if (StringUtils.isBlank(cnp) || cnp.length() != 13) {
            redirect.addFlashAttribute("errorMessage", "CNP-ul trebuie sa fie format din 13 caractere!");
            return new RedirectView("/admin_user");
        }

        if (StringUtils.isBlank(street)) {
            redirect.addFlashAttribute("errorMessage", "Introduceti o strada valida!");
            return new RedirectView("/admin_user");
        }

        if (StringUtils.isBlank(number)) {
            redirect.addFlashAttribute("errorMessage", "Introduceti un numar valid!");
            return new RedirectView("/admin_user");
        }

        if (StringUtils.isBlank(county)) {
            redirect.addFlashAttribute("errorMessage", "Introduceti un judet valid!");
            return new RedirectView("/admin_user");
        }

        if (StringUtils.isBlank(country)) {
            redirect.addFlashAttribute("errorMessage", "Introduceti o tara valida!");
            return new RedirectView("/admin_user");
        }

        if (StringUtils.isBlank(city)) {
            redirect.addFlashAttribute("errorMessage", "Introduceti o localitate valida!");
            return new RedirectView("/admin_user");
        }

        if (StringUtils.isBlank(data)) {
            redirect.addFlashAttribute("errorMessage", "Data invalida!");
            return new RedirectView("/admin_user");
        }

        if (StringUtils.isBlank(position)) {
            redirect.addFlashAttribute("errorMessage", "Functie invalida!");
            return new RedirectView("/admin_user");
        }
        if (StringUtils.isBlank(state) && (state != "TRUE" || state != "FALSE")) {
            redirect.addFlashAttribute("errorMessage", "Starea trebuie sa fie TRUE sau FALSE!");
            return new RedirectView("/admin_user");
        }

        if (StringUtils.isBlank(ci_series)) {
            redirect.addFlashAttribute("errorMessage", "Serie invalida!");
            return new RedirectView("/admin_user");
        }

        if (StringUtils.isBlank(ci_number)) {
            redirect.addFlashAttribute("errorMessage", "Numar invalid!");
            return new RedirectView("/admin_user");
        }

        if (StringUtils.isBlank(is_manager) && (is_manager != "TRUE" || is_manager != "FALSE")) {
            redirect.addFlashAttribute("errorMessage", "Is Manager trebuie sa fie TRUE sau FALSE!");
            return new RedirectView("/admin_user");
        }

        if (StringUtils.isBlank(money)) {
            redirect.addFlashAttribute("errorMessage", "Salariu invalid!");
            return new RedirectView("/admin_user");
        }

        if (StringUtils.isBlank(first_day)) {
            redirect.addFlashAttribute("errorMessage", "Data angajarii este invalida!");
            return new RedirectView("/admin_user");
        }

        if (StringUtils.isBlank(working_hours)) {
            redirect.addFlashAttribute("errorMessage", "Norma de lucru este invalida!");
            return new RedirectView("/admin_user");
        }

        if(!userInfo.checkFirmExists(company_name)){
            redirect.addFlashAttribute("errorMessage", "Firma nu exista!");
            return new RedirectView("/admin_user");
        }
        if (StringUtils.isBlank(company_name)) {
            redirect.addFlashAttribute("errorMessage", "Completati numele firmei!");
            return new RedirectView("/admin_user");
        }

        buffer.put("nume", last_name);
        buffer.put("prenume", first_name);
        buffer.put("cnp", cnp);
        buffer.put("functie", position);
        buffer.put("strada", street);
        buffer.put("numar", number);
        buffer.put("localitate", city);
        buffer.put("judet", county);
        buffer.put("tara", country);
        buffer.put("stare", state);
        buffer.put("id_firma", userInfo.getCompanyId(company_name));
        buffer.put("email", email);
        buffer.put("data_nasterii", data);
        buffer.put("serie", ci_series);
        buffer.put("nr", ci_number);
        buffer.put("is_manager", is_manager);
        buffer.put("salariu", money);
        buffer.put("data_angajarii", first_day);
        buffer.put("norma_de_lucru", working_hours);

        userInfo.changeUserInfoByManager(buffer);

        redirect.addFlashAttribute("errorMessage", "Informatii updatate cu succes!");
        return new RedirectView("/admin_user");
    }


}
