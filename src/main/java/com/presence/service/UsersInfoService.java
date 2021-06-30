package com.presence.service;

import com.presence.database.DatabaseConnection;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


import static com.presence.security.WebSecurityConfig.passwordEncoder;

@Service
public class UsersInfoService {
    @Autowired
    private InMemoryUserDetailsManager inMemoryUserDetailsManager;

    private String employee_to_edit = "";

    public String getLoggedInUser(){
        String username = ((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername();

        String query =
                "SELECT nume, prenume FROM Angajati WHERE user_id = (SELECT id_user FROM user_credentials WHERE username = '" + username + "')";

        List<HashMap<Integer, String>> userIdList =
                DatabaseConnection.getMultipleColumns(query, 2);

        return userIdList.get(0).get(1) + " " + userIdList.get(0).get(2);
    }

    public String getUserToEdit(){
        if(employee_to_edit == "")
            employee_to_edit = getCurrentUsername();

        String query =
                "SELECT nume, prenume FROM Angajati WHERE user_id = (SELECT id_user FROM user_credentials WHERE username = '" + employee_to_edit + "')";

        List<HashMap<Integer, String>> userIdList =
                DatabaseConnection.getMultipleColumns(query, 2);

        return userIdList.get(0).get(1) + " " + userIdList.get(0).get(2);
    }

    public String getLocation(){
        String username = ((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername();

        String query =
                "SELECT localitate, judet FROM Angajati WHERE user_id = (SELECT id_user FROM user_credentials WHERE username = '" + username + "')";

        List<HashMap<Integer, String>> userIdList =
                DatabaseConnection.getMultipleColumns(query, 2);

        return userIdList.get(0).get(1) + ", " + userIdList.get(0).get(2) + ",";
    }

    public List<HashMap<Integer, String>> getSalMaxPerCompany(){
        String query =
                        "select F.nume_firma, max(A.salariu)\n" +
                        "from Angajati A\n" +
                        "inner join firma F on A.id_firma= F.id_firma\n" +
                        "group by F.nume_firma";

        List<HashMap<Integer, String>> userIdList =
                DatabaseConnection.getMultipleColumns(query, 2);
        return userIdList;
    }

    public List<HashMap<Integer, String>> getEmployeesWithMaxSalary(){
        String query = "select A.nume, A.prenume, A.salariu,  F.nume_firma\n" +
                "from Angajati A\n" +
                "inner join firma F on A.id_firma= F.id_firma\n" +
                "where A.salariu in \n" +
                "(select max(AA.salariu)\n" +
                "from Angajati AA \n" +
                "where AA.id_firma = A.id_firma\n" +
                "group by AA.id_firma)\n" +
                "order by A.salariu desc";


        List<HashMap<Integer, String>> userIdList =
                DatabaseConnection.getMultipleColumns(query, 4);
        return userIdList;
    }

    public List<HashMap<Integer, String>> hoursWorkedAtFirmInMonth(String firma, String luna, String year){
        String query = "SELECT sum(datediff(hour, start, sfarsit))\n" +
                "from alterari_timp_lucru\n" +
                "where id_ang in \n" +
                "(select AA.id_ang\n" +
                "from Angajati AA\n" +
                "inner join firma F on F.id_firma = AA.id_firma\n" +
                "where F.nume_firma= '" + firma + "') AND tip_alterare = 'invoire' and month(data) = " + luna + " and year(data) = " + year;


        List<HashMap<Integer, String>> userIdList =
                DatabaseConnection.getMultipleColumns(query, 1);
        return userIdList;
    }

    public List<HashMap<Integer, String>> getData1(String start_date, String end_date, String company2, String nr_hours){
        String query = "select A.nume, A.prenume \n" +
                "from Angajati A\n" +
                "where A.id_ang in \n" +
                "(select AA.id_ang\n" +
                "from Angajati AA\n" +
                "inner join firma F on F.id_firma = AA.id_firma\n" +
                "inner join alterari_timp_lucru AL on AL.id_ang = AA.id_ang\n" +
                "where F.nume_firma = '" + company2 + "' and AL.tip_alterare = 'invoire' and AL.data between '" + start_date + "' and '" + end_date + "'\n" +
                "group by AA.id_ang\n" +
                "having sum(datediff(hour, AL.start, AL.sfarsit)) >= " + nr_hours + ")\n" +
                "order by A.nume, A.prenume desc";


        List<HashMap<Integer, String>> userIdList =
                DatabaseConnection.getMultipleColumns(query, 2);
        return userIdList;
    }

    public List<HashMap<Integer, String>> getData2(String data, String ora, String ore){
        String query = "select A.nume, A.prenume\n" +
                "from Angajati A\n" +
                "inner join pontaj P on P.id_ang = A.id_ang\n" +
                "where P.ora_start = '" + ora + ":00:00' and datediff(hour, P.ora_start, P.ora_end) >= " + ore + " and P.data = '" + data +"'";


        List<HashMap<Integer, String>> userIdList =
                DatabaseConnection.getMultipleColumns(query, 2);
        return userIdList;
    }

    public List<HashMap<Integer, String>> getData3(String data){
        String query =
                "select A.nume, A.prenume, sum(C.numar_zile)\n" +
                "from concediu C\n" +
                "inner join angajati A on A.id_ang = C.id_ang\n" +
                "where C.tip_alterare = 'fara plata' and year(C.start) = " + data + "\n" +
                "group by A.nume, A.prenume\n" +
                "order by sum(C.numar_zile) desc";


        List<HashMap<Integer, String>> userIdList =
                DatabaseConnection.getMultipleColumns(query, 3);
        return userIdList;
    }

    public List<HashMap<Integer, String>> getData4(String data){
        String query =
                "select A.nume, A.prenume, (datediff(day, A.data_angajarii, '" + data + "')*20)/365 - sum(C.numar_zile), sum(C.numar_zile)\n" +
                "from Angajati A\n" +
                "inner join concediu C on C.id_ang = A.id_ang\n" +
                "where C.tip_alterare = 'cu plata' and C.sfarsit <= '" + data + "'\n" +
                "group by A.nume, A.prenume";


        List<HashMap<Integer, String>> userIdList =
                DatabaseConnection.getMultipleColumns(query, 4);
        return userIdList;
    }

    public List<HashMap<Integer, String>> getData5(String data){
        String query =
                "select A.nume, A.prenume, A.salariu, F.nume_firma\n" +
                "from Angajati A\n" +
                "inner join firma F on F.id_firma = A.id_firma\n" +
                "inner join angajat_echipa AE on AE.id_ang = A.id_ang\n" +
                "inner join echipa E on E.id_echipa = AE.id_echipa\n" +
                "where A.salariu >= \n" +
                "(select AVG(AA.salariu)\n" +
                "from Angajati AA\n" +
                "where AA.id_firma = F.id_firma\n" +
                ") and E.nume='" + data + "'\n" +
                "order by A.nume, A.prenume desc";


        List<HashMap<Integer, String>> userIdList =
                DatabaseConnection.getMultipleColumns(query, 4);
        return userIdList;
    }

    public String getCi(){
        String username = ((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername();

        String query =
                "SELECT serie, nr FROM Angajati WHERE user_id = (SELECT id_user FROM user_credentials WHERE username = '" + username + "')";

        List<HashMap<Integer, String>> userIdList =
                DatabaseConnection.getMultipleColumns(query, 2);

        return userIdList.get(0).get(1) + ", numar " + userIdList.get(0).get(2) + ",";
    }

    public boolean checkUsername(String user){
        String query =
                "SELECT COUNT(1) FROM user_credentials WHERE username = '" + user + "'";

        List<HashMap<Integer, String>> userIdList =
                DatabaseConnection.getMultipleColumns(query, 1);
        if(Integer.parseInt(userIdList.get(0).get(1)) != 0)
            return true;
        else
            return false;
    }

    public boolean checkFirmExists(String firm_name){
        String query =
                "SELECT COUNT(1) FROM firma WHERE nume_firma = '" + firm_name + "'";

        List<HashMap<Integer, String>> userIdList =
                DatabaseConnection.getMultipleColumns(query, 1);
        if(Integer.parseInt(userIdList.get(0).get(1)) == 0)
            return false;
        else
            return true;
    }

    public boolean checkCnp(String cnp){
        String query =
                "SELECT COUNT(1) FROM angajati WHERE cnp = '" + cnp + "'";

        List<HashMap<Integer, String>> userIdList =
                DatabaseConnection.getMultipleColumns(query, 1);
        if(Integer.parseInt(userIdList.get(0).get(1)) != 0)
            return true;
        else
            return false;
    }

    public String getEmployeeId(){
        String username = ((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername();

        String query =
                "SELECT id_ang FROM Angajati WHERE user_id = (SELECT id_user FROM user_credentials WHERE username = '" + username + "')";

        List<HashMap<Integer, String>> userIdList =
                DatabaseConnection.getMultipleColumns(query, 1);

        return userIdList.get(0).get(1);
    }

    public String getCompanyId(String comp){
        String query =
                "SELECT id_firma FROM firma WHERE nume_firma = '" + comp + "'";

        List<HashMap<Integer, String>> userIdList =
                DatabaseConnection.getMultipleColumns(query, 1);

        return userIdList.get(0).get(1);
    }

    public String getEmployeeIdFromManager(){
        if(employee_to_edit == "")
            employee_to_edit = getCurrentUsername();

        String query =
                "SELECT id_ang FROM Angajati WHERE user_id = (SELECT id_user FROM user_credentials WHERE username = '" + employee_to_edit + "')";

        List<HashMap<Integer, String>> userIdList =
                DatabaseConnection.getMultipleColumns(query, 1);

        return userIdList.get(0).get(1);
    }

    public String getCompanyName(String com){
        String query =
                "SELECT nume_firma FROM firma WHERE id_firma = '" + com + "'";
        //System.out.println(query);
        List<HashMap<Integer, String>> userIdList =
                DatabaseConnection.getMultipleColumns(query, 1);

        return userIdList.get(0).get(1);
    }

    public String isManager(){
        String username = ((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername();

        String query =
                "SELECT is_manager FROM Angajati WHERE user_id = (SELECT id_user FROM user_credentials WHERE username = '" + username + "')";

        List<HashMap<Integer, String>> userIdList =
                DatabaseConnection.getMultipleColumns(query, 1);

        return userIdList.get(0).get(1);
    }

    public String getPosition(){
        String username = ((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername();

        String query =
                "SELECT functie FROM Angajati WHERE user_id = (SELECT id_user FROM user_credentials WHERE username = '" + username + "')";

        List<HashMap<Integer, String>> userIdList =
                DatabaseConnection.getMultipleColumns(query, 1);

        return userIdList.get(0).get(1);
    }

    public String getCnp(){
        String username = ((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername();

        String query =
                "SELECT cnp FROM Angajati WHERE user_id = (SELECT id_user FROM user_credentials WHERE username = '" + username + "')";

        List<HashMap<Integer, String>> userIdList =
                DatabaseConnection.getMultipleColumns(query, 1);

        return userIdList.get(0).get(1);
    }

    public String getCurrentUsername(){
        String username = ((UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername();

        return username;
    }

    public void changePassword(String oldpass, String newpass) {
        DatabaseConnection.executeQuery("UPDATE user_credentials SET password = '" + passwordEncoder().encode(newpass) +
                "' WHERE username = '" + getCurrentUsername() + "'");
        inMemoryUserDetailsManager.changePassword(oldpass, passwordEncoder().encode(newpass));
    }

    public void setEmployeeToEdit(String empl) {
        employee_to_edit = empl;
    }

    public String getEmployeeToEdit() {
        if(employee_to_edit == "")
            employee_to_edit = getCurrentUsername();

        return employee_to_edit;
    }
    public void changeUserInfo(HashMap<String, String> buffer) {
        buffer.forEach((attribute, value) -> {
            DatabaseConnection.executeQuery("UPDATE Angajati SET " + attribute + "= '" + value + "' WHERE id_ang = '" + getEmployeeId() + "'");
        });
    }

    public void changeUserInfoByManager(HashMap<String, String> buffer) {
        buffer.forEach((attribute, value) -> {
            DatabaseConnection.executeQuery("UPDATE Angajati SET " + attribute + "= '" + value + "' WHERE id_ang = '" + getEmployeeIdFromManager() + "'");
        });
        if(employee_to_edit == "")
            employee_to_edit = getCurrentUsername();

    }

    public void addAbsence(String absence_date, String hour1_absence, String hour2_absence, String recovery_date, String hour1_recovery, String hour2_recovery, String manager_name) {
        Date current_date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("_dd_MM_yyyy_HH_mm_ss");
        String buffer = getCurrentUsername() + (formatter.format(current_date)).toString() +"_not_signed.pdf";

        //new File("src/main/download/" + getCurrentUsername()).mkdirs();

        String absence_id = UUID.randomUUID().toString();
        String recovery_id = UUID.randomUUID().toString();

        absencePdf("src/main/download/" + getCurrentUsername() + "/invoiri/" + buffer, absence_date, hour1_absence, hour2_absence, recovery_date, hour1_recovery, hour2_recovery, current_date.toString(), manager_name);

        DatabaseConnection.executeQuery("INSERT INTO alterari_timp_lucru (id_inreg, id_ang, start, sfarsit, data, tip_alterare, fisier) VALUES " +
                "('" + absence_id + "', '" + getEmployeeId() + "', '" + hour1_absence + ":00', '" + hour2_absence + ":00', '" + absence_date + "', 'invoire', '" +
                buffer + "')");

        DatabaseConnection.executeQuery("INSERT INTO alterari_timp_lucru (id_inreg, id_ang, start, sfarsit, data, tip_alterare, fisier) VALUES " +
                "('" + recovery_id + "', '" + getEmployeeId() + "', '" + hour1_recovery + ":00', '" + hour2_recovery + ":00', '" + recovery_date + "', 'recuperare', '" +
                buffer + "')");
    }

    public void addRestGetPaid(String start_date, String finish_date, String days_number, String manager_name) {
        Date current_date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("_dd_MM_yyyy_HH_mm_ss");
        String buffer = getCurrentUsername() + (formatter.format(current_date)).toString() +"_not_signed.pdf";

        String absence_id = UUID.randomUUID().toString();
        String recovery_id = UUID.randomUUID().toString();

        restPdf("src/main/download/" + getCurrentUsername() + "/cu_plata/" + buffer, start_date, finish_date, days_number, current_date.toString(), manager_name);

        DatabaseConnection.executeQuery("INSERT INTO concediu (id_inreg, id_ang, start, sfarsit, numar_zile, tip_alterare, fisier) VALUES " +
                "('" + absence_id + "', '" + getEmployeeId() + "', '" + start_date + "', '" + finish_date + "', '" + days_number + "', 'cu plata', '" +
                buffer + "')");
    }

    public void addRestNotPaid(String start_date, String finish_date, String days_number, String manager_name) {
        Date current_date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("_dd_MM_yyyy_HH_mm_ss");
        String buffer = getCurrentUsername() + (formatter.format(current_date)).toString() +"_not_signed.pdf";

        String absence_id = UUID.randomUUID().toString();
        String recovery_id = UUID.randomUUID().toString();

        restNoPayPdf("src/main/download/" + getCurrentUsername() + "/fara_plata/" + buffer, start_date, finish_date, days_number, current_date.toString(), manager_name);

        DatabaseConnection.executeQuery("INSERT INTO concediu (id_inreg, id_ang, start, sfarsit, numar_zile, tip_alterare, fisier) VALUES " +
                "('" + absence_id + "', '" + getEmployeeId() + "', '" + start_date + "', '" + finish_date + "', '" + days_number + "', 'fara plata', '" +
                buffer + "')");
    }

    public boolean isPasswordCorrect(String password) {
        String oldPassword = inMemoryUserDetailsManager.loadUserByUsername(getCurrentUsername()).getPassword();
        return passwordEncoder().matches(password, oldPassword);
    }

    public HashMap<String, String> employeeDetails() {
        HashMap<String, String> buffer = new HashMap<>();
        String query =
                "SELECT nume, prenume, cnp, functie, strada, numar, localitate, judet, tara, email, data_nasterii FROM Angajati WHERE" +
                        " user_id = (SELECT id_user FROM user_credentials WHERE username = '" + getCurrentUsername() + "')";

        List<HashMap<Integer, String>> list =
                DatabaseConnection.getMultipleColumns(query, 11);

        buffer.put("last_name", list.get(0).get(1));
        buffer.put("first_name", list.get(0).get(2));
        buffer.put("cnp", list.get(0).get(3));
        buffer.put("position", list.get(0).get(4));
        buffer.put("street", list.get(0).get(5));
        buffer.put("number", list.get(0).get(6));
        buffer.put("city", list.get(0).get(7));
        buffer.put("county", list.get(0).get(8));
        buffer.put("country", list.get(0).get(9));
        buffer.put("email", list.get(0).get(10));
        buffer.put("username", getCurrentUsername());
        buffer.put("birth_date", list.get(0).get(11));

        return buffer;
    }

    public HashMap<String, String> employeeDetailsForAdmin() {
        HashMap<String, String> buffer = new HashMap<>();
        if(employee_to_edit == "")
            employee_to_edit = getCurrentUsername();
        String query =
               "SELECT nume, prenume, cnp, functie, strada, numar, localitate, judet, tara, stare, id_firma, email, data_nasterii, serie, nr, is_manager, salariu, data_angajarii, norma_de_lucru \n" +
               "FROM Angajati \n" +
               "INNER JOIN USER_CREDENTIALS U ON Angajati.user_id = U.id_user\n" +
               "WHERE U.username = '" + employee_to_edit + "'";

        List<HashMap<Integer, String>> list =
                DatabaseConnection.getMultipleColumns(query, 19);

        buffer.put("last_name", list.get(0).get(1));
        buffer.put("first_name", list.get(0).get(2));
        buffer.put("cnp", list.get(0).get(3));
        buffer.put("position", list.get(0).get(4));
        buffer.put("street", list.get(0).get(5));
        buffer.put("number", list.get(0).get(6));
        buffer.put("city", list.get(0).get(7));
        buffer.put("county", list.get(0).get(8));
        buffer.put("country", list.get(0).get(9));
        buffer.put("state", list.get(0).get(10));
        buffer.put("company_name", list.get(0).get(11) == null ? "" : getCompanyName(list.get(0).get(11)));
        buffer.put("email", list.get(0).get(12));
        buffer.put("birth_date", list.get(0).get(13));
        buffer.put("ci_series", list.get(0).get(14));
        buffer.put("ci_number", list.get(0).get(15));
        buffer.put("is_manager", list.get(0).get(16));
        buffer.put("money", list.get(0).get(17));
        buffer.put("first_day", list.get(0).get(18));
        buffer.put("working_hours", list.get(0).get(19));
        buffer.put("username", employee_to_edit);

        return buffer;
    }

    public List<String> getUsers() {
        String query =
                "SELECT username FROM user_credentials";

        List<HashMap<Integer, String>> list =
                DatabaseConnection.getMultipleColumns(query, 1);

        List<String> buffer = new ArrayList<>();
        list.forEach(linie -> {
            buffer.add(linie.get(1));
        });

        return buffer;
    }

    public void newUser(String last_name, String first_name, String cnp, String street, String number, String city, String county, String country, String email, String username, String birth_date, String password) {
        String id_user = UUID.randomUUID().toString();
        String id_ang = UUID.randomUUID().toString();

        DatabaseConnection.executeQuery("INSERT INTO user_credentials (id_user, username, password) VALUES " +
                "('" + id_user + "', '" + username + "', '" + passwordEncoder().encode(password) + "')");

        DatabaseConnection.executeQuery("INSERT INTO Angajati (id_ang, nume, prenume, cnp, strada, numar, localitate, judet, tara, user_id, email, data_nasterii) VALUES " +
                "('" + id_ang + "', '" + last_name + "', '" + first_name + "', '" + cnp + "', '" + street + "', '" + number + "', '" + city + "', '" + county + "', '" + country +
                "', '" + id_user + "', '" + email + "', '" + birth_date + "')");

        List<SimpleGrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("USER"));
        inMemoryUserDetailsManager.createUser(
                new User(username, passwordEncoder().encode(password), list)
        );
    }

    public void absencePdf(String filename, String absence_date, String hour1_absence, String hour2_absence, String recovery_date, String hour1_recovery, String hour2_recovery, String current_date, String manager_name){
        String title = "Cerere de invoire";
        String row1 = "Subsemnatul, " + getLoggedInUser().toUpperCase() + ", domiciliat in " + getLocation().toUpperCase();
        String row2 = "posesor al cartii de identitate seria " + getCi() + " CNP " + getCnp() + ",";
        String row3 = "va rog sa-mi aprobati cererea de invoire in data de:";
        String row4 = "- " + absence_date +" intre orele " + hour1_absence + " - " + hour2_absence + ";";
        String row5 = "urmand sa recuperez astfel:";
        String row6 = "- " + recovery_date +" intre orele " + hour1_recovery + " - " + hour2_recovery + ";";


        PDDocument doc = new PDDocument();
        try {
            PDPage page = new PDPage(new PDRectangle(612, 792));
            doc.addPage(page);

            PDFont font = PDType1Font.TIMES_ROMAN;

            PDPageContentStream contents = new PDPageContentStream(doc, page);
            contents.beginText();
            contents.setFont(font, 30);
            contents.newLineAtOffset(200, 700);
            contents.showText(title);
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(70, 600);
            contents.showText(row1);
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(60, 570);
            contents.showText(row2);
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(60, 540);
            contents.showText(row3);
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(80, 510);
            contents.showText(row4);
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(60, 480);
            contents.showText(row5);
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(80, 450);
            contents.showText(row6);
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(90, 320);
            contents.showText("Angajat:                                                                     Data:");
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(70, 300);
            contents.showText(getLoggedInUser().toUpperCase());
            contents.endText();
            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(60, 270);
            contents.showText("............................                                               " + current_date);
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(80, 230);
            contents.showText("Aprobata de Angajator :");
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(60, 200);
            contents.showText("OPTIMUS DIGITAL SRL, reprezentat prin administrator " + manager_name);
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(60, 170);
            contents.showText(".........................................................");
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(100, 130);
            contents.showText("Am primit un exemplar din cererea de învoire aprobata.");
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(90, 100);
            contents.showText("Angajat:                                                                                    Data:");
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(70, 80);
            contents.showText(getLoggedInUser().toUpperCase());
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(60, 50);
            contents.showText("................................                                                      " + current_date);
            contents.endText();

            contents.close();

            doc.save(filename);
            doc.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void restPdf(String filename, String start_date, String finish_date, String days_number, String current_date, String manager_name){
        String title = "Concediu cu plata";
        String row1 = "Subsemnatul, " + getLoggedInUser().toUpperCase() + ", domiciliat in " + getLocation().toUpperCase();
        String row2 = "posesor al cartii de identitate seria " + getCi() + " CNP " + getCnp() + ",";
        String row3 = "angajat al Optimus Digital SRL in functia de " + getPosition();
        String row4 = "va rog sa-mi aprobati efectuarea unui numar de " + days_number + " zile de concediu de odihna";
        String row5 = "in perioada " + start_date + " - " + finish_date + ".";


        PDDocument doc = new PDDocument();
        try {
            PDPage page = new PDPage(new PDRectangle(612, 792));
            doc.addPage(page);

            PDFont font = PDType1Font.TIMES_ROMAN;

            PDPageContentStream contents = new PDPageContentStream(doc, page);
            contents.beginText();
            contents.setFont(font, 30);
            contents.newLineAtOffset(200, 700);
            contents.showText(title);
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(70, 600);
            contents.showText(row1);
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(60, 570);
            contents.showText(row2);
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(60, 540);
            contents.showText(row3);
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(80, 510);
            contents.showText(row4);
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(60, 480);
            contents.showText(row5);
            contents.endText();



            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(90, 320);
            contents.showText("Angajat:                                                                     Data:");
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(70, 300);
            contents.showText(getLoggedInUser().toUpperCase());
            contents.endText();
            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(60, 270);
            contents.showText("............................                                               " + current_date);
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(80, 230);
            contents.showText("Aprobata de Angajator :");
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(60, 200);
            contents.showText("OPTIMUS DIGITAL SRL, reprezentat prin administrator " + manager_name);
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(60, 170);
            contents.showText(".........................................................");
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(100, 130);
            contents.showText("Am primit un exemplar din cererea de învoire aprobata.");
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(90, 100);
            contents.showText("Angajat:                                                                                    Data:");
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(70, 80);
            contents.showText(getLoggedInUser().toUpperCase());
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(60, 50);
            contents.showText("................................                                                      " + current_date);
            contents.endText();



            contents.close();

            doc.save(filename);
            doc.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void restNoPayPdf(String filename, String start_date, String finish_date, String days_number, String current_date, String manager_name){
        String title = "Concediu fara plata";
        String row1 = "Subsemnatul, " + getLoggedInUser().toUpperCase() + ", domiciliat in " + getLocation().toUpperCase();
        String row2 = "posesor al cartii de identitate seria " + getCi() + " CNP " + getCnp() + ",";
        String row3 = "angajat al Optimus Digital SRL in functia de " + getPosition();
        String row4 = "va rog sa-mi aprobati efectuarea unui numar de " + days_number + " zile de concediu de odihna";
        String row5 = "in perioada " + start_date + " - " + finish_date + ".";


        PDDocument doc = new PDDocument();
        try {
            PDPage page = new PDPage(new PDRectangle(612, 792));
            doc.addPage(page);

            PDFont font = PDType1Font.TIMES_ROMAN;

            PDPageContentStream contents = new PDPageContentStream(doc, page);
            contents.beginText();
            contents.setFont(font, 30);
            contents.newLineAtOffset(200, 700);
            contents.showText(title);
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(70, 600);
            contents.showText(row1);
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(60, 570);
            contents.showText(row2);
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(60, 540);
            contents.showText(row3);
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(80, 510);
            contents.showText(row4);
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(60, 480);
            contents.showText(row5);
            contents.endText();



            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(90, 320);
            contents.showText("Angajat:                                                                     Data:");
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(70, 300);
            contents.showText(getLoggedInUser().toUpperCase());
            contents.endText();
            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(60, 270);
            contents.showText("............................                                               " + current_date);
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(80, 230);
            contents.showText("Aprobata de Angajator :");
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(60, 200);
            contents.showText("OPTIMUS DIGITAL SRL, reprezentat prin administrator " + manager_name);
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(60, 170);
            contents.showText(".........................................................");
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(100, 130);
            contents.showText("Am primit un exemplar din cererea de învoire aprobata.");
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(90, 100);
            contents.showText("Angajat:                                                                                    Data:");
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(70, 80);
            contents.showText(getLoggedInUser().toUpperCase());
            contents.endText();

            contents.beginText();
            contents.setFont(font, 15);
            contents.newLineAtOffset(60, 50);
            contents.showText("................................                                                      " + current_date);
            contents.endText();



            contents.close();

            doc.save(filename);
            doc.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
