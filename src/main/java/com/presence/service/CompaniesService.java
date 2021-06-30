package com.presence.service;

import com.presence.database.DatabaseConnection;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CompaniesService {
    private String current_company_sel = "";
    private String current_location_sel = "";

    public List<String> getCompanies() {
        String query =
                "SELECT nume_firma FROM firma";

        List<HashMap<Integer, String>> list =
                DatabaseConnection.getMultipleColumns(query, 1);

        List<String> buffer = new ArrayList<>();
        list.forEach(linie -> {
            buffer.add(linie.get(1));
        });

        return buffer;
    }

    public void setCompany(String comp){
        current_company_sel = comp;
    }
    public void setLocation(String loc){
        current_location_sel = loc;
    }

    public HashMap<String, String> companyDetails() {
        HashMap<String, String> buffer = new HashMap<>();
        String query =
                "SELECT cui, cif, adresa_sediu_social FROM firma WHERE" +
                        " nume_firma = '" + current_company_sel + "'";

        List<HashMap<Integer, String>> list =
                DatabaseConnection.getMultipleColumns(query, 3);

        buffer.put("name", current_company_sel);
        buffer.put("cui", list.get(0).get(1));
        buffer.put("cif", list.get(0).get(2));
        buffer.put("address", list.get(0).get(3));

        return buffer;
    }

    public HashMap<String, String> locationDetails() throws ParseException {
        HashMap<String, String> buffer = new HashMap<>();
        String query =
                "SELECT adresa, ora_inceput_program, ora_sfarsit_program, ora_minima_recuperare, ora_maxima_recuperare FROM punct_de_lucru WHERE" +
                        " nume = '" + current_location_sel + "'";

        List<HashMap<Integer, String>> list =
                DatabaseConnection.getMultipleColumns(query, 5);

        SimpleDateFormat simpleDateFormatArrivals = new SimpleDateFormat("HH:mm");
        Date date1 = simpleDateFormatArrivals.parse(list.get(0).get(2));
        Date date2 = simpleDateFormatArrivals.parse(list.get(0).get(3));
        Date date3 = simpleDateFormatArrivals.parse(list.get(0).get(4));
        Date date4 = simpleDateFormatArrivals.parse(list.get(0).get(5));

        buffer.put("point_name", current_location_sel);
        buffer.put("point_address", list.get(0).get(1));
        buffer.put("hour_start_program", simpleDateFormatArrivals.format(date1));
        buffer.put("hour_end_program", simpleDateFormatArrivals.format(date2));
        buffer.put("hour_start_recovery", simpleDateFormatArrivals.format(date3));
        buffer.put("hour_end_recovery", simpleDateFormatArrivals.format(date4));

        return buffer;
    }

    public void updateCompany(HashMap<String, String> buffer) {
        buffer.forEach((attribute, value) -> {
            DatabaseConnection.executeQuery("UPDATE firma SET " + attribute + "= '" + value + "' WHERE nume_firma = '" + current_company_sel + "'");
        });
    }

    public void updateLocation(HashMap<String, String> buffer) {
        buffer.forEach((attribute, value) -> {
            DatabaseConnection.executeQuery("UPDATE punct_de_lucru SET " + attribute + "= '" + value + "' WHERE nume = '" + current_location_sel + "'");
        });
    }

    public void deleteLocation() {
        DatabaseConnection.executeQuery("DELETE FROM punct_de_lucru WHERE nume = '" + current_location_sel + "'");
        current_location_sel = "";
    }

    public void newCompany(HashMap<String, String> buffer) {
        String id_firma = UUID.randomUUID().toString();

        DatabaseConnection.executeQuery("INSERT INTO firma (id_firma, nume_firma, cui, cif, adresa_sediu_social) VALUES " +
                "('" + id_firma + "', '" + buffer.get("nume_firma") + "', '" + buffer.get("cui") + "', '" + buffer.get("cif") + "', '" + buffer.get("adresa_sediu_social") + "')");

    }

    public void newLocation(HashMap<String, String> buffer) {
        String id_punct = UUID.randomUUID().toString();

        DatabaseConnection.executeQuery("INSERT INTO punct_de_lucru (id_punct, nume, adresa, id_firma, ora_inceput_program, ora_sfarsit_program, ora_minima_recuperare, ora_maxima_recuperare) VALUES " +
                "('" + id_punct + "', '" + buffer.get("nume") + "', '" + buffer.get("adresa") + "', '" + getCurrentCompanyId() + "', '" + buffer.get("ora_inceput_program")
                + "', '" + buffer.get("ora_sfarsit_program") + "', '" + buffer.get("ora_minima_recuperare") + "', '" + buffer.get("ora_maxima_recuperare") + "')");

    }
    public String getCurrentCompanyId(){
        String query =
                "SELECT id_firma FROM firma WHERE nume_firma = '" + current_company_sel + "'";

        List<HashMap<Integer, String>> userIdList =
                DatabaseConnection.getMultipleColumns(query, 1);

        return userIdList.get(0).get(1);
    }

    public List<String> getLocations() {
        String query =
                        "SELECT P.nume \n" +
                        "FROM punct_de_lucru P\n" +
                        "INNER JOIN firma F ON P.id_firma = F.id_firma\n" +
                        "WHERE F.nume_firma = '" + current_company_sel + "'";

        List<HashMap<Integer, String>> list =
                DatabaseConnection.getMultipleColumns(query, 1);

        List<String> buffer = new ArrayList<>();
        list.forEach(linie -> {
            buffer.add(linie.get(1));
        });

        return buffer;
    }
}
