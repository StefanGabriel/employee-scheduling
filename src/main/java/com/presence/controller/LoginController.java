package com.presence.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {
    //trimitem pagina de login catre utilizator
    //managerul de useri din memorie va verifica daca
    //username si parola sunt valide, iar in caz contrar va
    //arunca o eroare
    @RequestMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {

        if (error != null) {
            model.addAttribute("errorMessage", "Username sau parola gresite!");
        } else if (logout != null) {
            model.addAttribute("logoutMessage", "Ati iesit cu succes din cont");
        }

        return "login";
    }
}
