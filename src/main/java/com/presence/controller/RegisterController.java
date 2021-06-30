package com.presence.controller;


import com.presence.service.UsersInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

@Controller
public class RegisterController {

    @Autowired
    private UsersInfoService userInfo;

    //trimitem pagina pentru inregistrare
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    //preluam datele introduse de utilizator,
    //le verificam si daca toate conditiile trec,
    //introducem datele in baza de date
    @PostMapping("/register")
    public RedirectView register(@RequestParam(value = "last_name") String last_name,
                                 @RequestParam(value = "first_name") String first_name,
                                 @RequestParam(value = "email") String email,
                                 @RequestParam(value = "cnp") String cnp,
                                 @RequestParam(value = "street") String street,
                                 @RequestParam(value = "number") String number,
                                 @RequestParam(value = "city") String city,
                                 @RequestParam(value = "county") String county,
                                 @RequestParam(value = "country") String country,
                                 @RequestParam(value = "birth_date") String birth_date,
                                 @RequestParam(value = "username") String username,
                                 @RequestParam(value = "password") String password,
                                 RedirectAttributes redirectAttributes) {

        if (StringUtils.isBlank(last_name) || last_name.length() < 3) {
            redirectAttributes.addFlashAttribute("errorMessage", "Numele trebuie sa fie format din cel putin 3 caractere!");
            return new RedirectView("/register");
        }

        if (StringUtils.isBlank(first_name) || first_name.length() < 3) {
            redirectAttributes.addFlashAttribute("errorMessage", "Prenumele trebuie sa fie format din cel putin 3 caractere!");
            return new RedirectView("/register");
        }

        if (StringUtils.isBlank(email)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email invalid!");
            return new RedirectView("/register");
        }

        if(userInfo.checkCnp(cnp) == true){
            redirectAttributes.addFlashAttribute("errorMessage", "CNP-ul exista!");
            return new RedirectView("/register");
        }

        if (StringUtils.isBlank(cnp) || cnp.length() != 13) {
            redirectAttributes.addFlashAttribute("errorMessage", "CNP-ul trebuie sa fie format din 13 caractere!");
            return new RedirectView("/register");
        }

        if (StringUtils.isBlank(street)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Introduceti o strada valida!");
            return new RedirectView("/register");
        }

        if (StringUtils.isBlank(number)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Introduceti un numar valid!");
            return new RedirectView("/register");
        }

        if (StringUtils.isBlank(county)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Introduceti un judet valid!");
            return new RedirectView("/register");
        }

        if (StringUtils.isBlank(country)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Introduceti o tara valida!");
            return new RedirectView("/register");
        }

        if (StringUtils.isBlank(city)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Introduceti o localitate valida!");
            return new RedirectView("/register");
        }
        if(userInfo.checkUsername(username) == true){
            redirectAttributes.addFlashAttribute("errorMessage", "Username-ul exista!");
            return new RedirectView("/register");
        }

        if (StringUtils.isBlank(username) || username.length() < 3) {
            redirectAttributes.addFlashAttribute("errorMessage", "Username-ul trebuie sa fie format din cel putin 3 caractere!");
            return new RedirectView("/register");
        }
        if (StringUtils.isBlank(password) || password.length() < 6) {
            redirectAttributes.addFlashAttribute("errorMessage", "Parola trebuie sa fie formata din cel putin 6 caractere!");
            return new RedirectView("/register");
        }
        if (StringUtils.isBlank(birth_date)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Data invalida!");
            return new RedirectView("/register");
        }

        userInfo.newUser(last_name, first_name, cnp, street, number, city, county, country, email, username, birth_date, password);
        new File("src/main/download/" + username).mkdirs();
        new File("src/main/download/" + username + "/invoiri").mkdirs();
        new File("src/main/download/" + username + "/fara_plata").mkdirs();
        new File("src/main/download/" + username + "/cu_plata").mkdirs();
        new File("src/main/download/" + username + "/contracte").mkdirs();

        redirectAttributes.addFlashAttribute("errorMessage", "Account successfully created! Please login.");
        return new RedirectView("/login");
    }

}
