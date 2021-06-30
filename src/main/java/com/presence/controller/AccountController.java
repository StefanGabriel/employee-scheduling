package com.presence.controller;

import com.presence.database.DatabaseConnection;
import com.presence.service.UsersInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.List;

@Controller
public class AccountController {
    @Autowired
    private UsersInfoService userInfo;

    //preluam din baza de date informatiile angajatului
    //si le introducem in pagina web
    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public String account(Model model) {
        model.addAttribute("isman", userInfo.isManager());
        userInfo.employeeDetails().forEach((attribute, value) -> {
            model.addAttribute(attribute, value);
        });

        return "account";
    }

    //avem un formular cu ajutorul caruia utilizatorul isi poate modifica
    //parola. Parola veche este verificata
    @RequestMapping(value = "/account/change_password", method = RequestMethod.POST)
    public RedirectView changePass(@RequestParam(value = "oldpass") String oldpass,
                                   @RequestParam(value = "newpass") String newpass,
                                   @RequestParam(value = "newpassconfirm") String newpassconfirm,
                                   final RedirectAttributes redirect){
        if (!userInfo.isPasswordCorrect(oldpass)) {
            redirect.addFlashAttribute("errorMessage", "The current password you inserted is wrong!");
            return new RedirectView("/account");
        }

        if (newpass == null || newpass.length() < 5) {
            redirect.addFlashAttribute("errorMessage", "Password must be at least 5 characters long!");
            return new RedirectView("/account");
        }

        if (!newpass.equals(newpassconfirm)) {
            redirect.addFlashAttribute("errorMessage", "Passwords must match!");
            return new RedirectView("/account");
        }

        userInfo.changePassword(oldpass, newpass);

        redirect.addFlashAttribute("errorMessage", "Password successfully updated!");
        return new RedirectView("/account");
    }

    //utilizatorul isi poate modifica datele personale (cele care nu depind de manager)
    //fiecare informatie este verificata pentru indeplinirea anumitor conditii si pe urma
    //datele sunt updatate in baza de date
    @RequestMapping(value = "/account/update_user_details", method = RequestMethod.POST)
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
                                          RedirectAttributes redirect){
        HashMap<String, String> buffer = new HashMap<>();

        if (StringUtils.isBlank(last_name) || last_name.length() < 3) {
            redirect.addFlashAttribute("errorMessage", "Numele trebuie sa fie format din cel putin 3 caractere!");
            return new RedirectView("/account");
        }

        if (StringUtils.isBlank(first_name) || first_name.length() < 3) {
            redirect.addFlashAttribute("errorMessage", "Prenumele trebuie sa fie format din cel putin 3 caractere!");
            return new RedirectView("/account");
        }

        if (StringUtils.isBlank(email)) {
            redirect.addFlashAttribute("errorMessage", "Email invalid!");
            return new RedirectView("/account");
        }

        if (StringUtils.isBlank(cnp) || cnp.length() != 13) {
            redirect.addFlashAttribute("errorMessage", "CNP-ul trebuie sa fie format din 13 caractere!");
            return new RedirectView("/account");
        }

        if (StringUtils.isBlank(street)) {
            redirect.addFlashAttribute("errorMessage", "Introduceti o strada valida!");
            return new RedirectView("/account");
        }

        if (StringUtils.isBlank(number)) {
            redirect.addFlashAttribute("errorMessage", "Introduceti un numar valid!");
            return new RedirectView("/account");
        }

        if (StringUtils.isBlank(county)) {
            redirect.addFlashAttribute("errorMessage", "Introduceti un judet valid!");
            return new RedirectView("/account");
        }

        if (StringUtils.isBlank(country)) {
            redirect.addFlashAttribute("errorMessage", "Introduceti o tara valida!");
            return new RedirectView("/account");
        }

        if (StringUtils.isBlank(city)) {
            redirect.addFlashAttribute("errorMessage", "Introduceti o localitate valida!");
            return new RedirectView("/account");
        }

        if (StringUtils.isBlank(data)) {
            redirect.addFlashAttribute("errorMessage", "Data invalida!");
            return new RedirectView("/account");
        }

        buffer.put("nume", last_name);
        buffer.put("prenume", first_name);
        buffer.put("cnp", cnp);
        buffer.put("strada", street);
        buffer.put("numar", number);
        buffer.put("localitate", city);
        buffer.put("judet", county);
        buffer.put("tara", country);
        buffer.put("email", email);
        buffer.put("data_nasterii", data);
        userInfo.changeUserInfo(buffer);

        redirect.addFlashAttribute("errorMessage", "Informatii updatate cu succes!");
        return new RedirectView("/account");
    }
}
