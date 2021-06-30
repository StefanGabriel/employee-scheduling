package com.presence.config;

import com.presence.database.DatabaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

@Component
public class OnApplicationStartUp {
    @Autowired
    private InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        addUsersToAuthenticationManager();
    }

    //la pornirea aplicatiei, vom adauga userii in serviciul din memorie
    //pentru managementul userilor pentru o executie mai rapida
    private void addUsersToAuthenticationManager() {
        List<HashMap<Integer, String>> credentials = DatabaseConnection.
                getMultipleColumns("SELECT username, password FROM USER_CREDENTIALS", 2);
        for (HashMap<Integer, String> map : credentials) {
                List<SimpleGrantedAuthority> list = new ArrayList<>();
                list.add(new SimpleGrantedAuthority("USER"));
                inMemoryUserDetailsManager.createUser(
                        new User(map.get(1), map.get(2), list)
                );
        }
    }
}