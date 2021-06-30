package com.presence.service;

import com.presence.database.DatabaseConnection;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class TeamsService {
    private String current_company_sel = "";
    private String current_team_sel = "";

    public void setCompany(String comp){
        current_company_sel = comp;
    }

    public String getLeaderId(String name){
        String[] splits = name.split("\\s+");
        String query =
                "SELECT id_ang from Angajati WHERE nume = '" + splits[0] + "' AND prenume = '" + splits[1] + "'";


        List<HashMap<Integer, String>> userIdList =
                DatabaseConnection.getMultipleColumns(query, 1);

        return userIdList.get(0).get(1) ;
    }

    public String getFirmId(){
        String query =
                "SELECT id_firma from firma WHERE nume_firma = '" + current_company_sel + "'";


        List<HashMap<Integer, String>> userIdList =
                DatabaseConnection.getMultipleColumns(query, 1);

        return userIdList.get(0).get(1);
    }

    public String getTeamId(){
        String query =
                "SELECT id_echipa from echipa WHERE nume = '" + current_team_sel + "'";


        List<HashMap<Integer, String>> userIdList =
                DatabaseConnection.getMultipleColumns(query, 1);

        return userIdList.get(0).get(1);
    }

    public String getLeaderName(String id){
        String query =
                "SELECT nume, prenume from Angajati WHERE id_ang = '" + id + "'";


        List<HashMap<Integer, String>> userIdList =
                DatabaseConnection.getMultipleColumns(query, 2);

        return userIdList.get(0).get(1) + " " + userIdList.get(0).get(2);
    }

    public List<String> getEmployees(){
        String query =
                "SELECT nume, prenume FROM Angajati";

        List<HashMap<Integer, String>> list =
                DatabaseConnection.getMultipleColumns(query, 2);

        List<String> buffer = new ArrayList<>();
        list.forEach(linie -> {
            buffer.add(linie.get(1) + " " + linie.get(2));
        });

        return buffer;
    }

    public boolean checkEmployee(String empl){
        String query =
                "SELECT nume, prenume FROM Angajati where id_firma = '" + getFirmId() + "'";

        List<HashMap<Integer, String>> list =
                DatabaseConnection.getMultipleColumns(query, 2);
        if(list.isEmpty()){
            return false;
        }
        List<String> buffer = new ArrayList<>();
        list.forEach(linie -> {
            buffer.add(linie.get(1) + " " + linie.get(2));
        });
        for(int i = 0; i<buffer.size(); i++){
            if((buffer.get(i)).compareTo(empl) == 0)
                return true;
        }

        return false;
    }


    public List<String> getTeams() {
        String query =
                        "SELECT E.nume \n" +
                        "FROM echipa E\n" +
                        "INNER JOIN firma F ON E.id_firma = F.id_firma\n" +
                        "WHERE F.nume_firma = '" + current_company_sel + "'";

        List<HashMap<Integer, String>> list =
                DatabaseConnection.getMultipleColumns(query, 1);

        List<String> buffer = new ArrayList<>();
        list.forEach(linie -> {
            buffer.add(linie.get(1));
        });

        return buffer;
    }

    public List<String> getAllTeams() {
        String query =
                "SELECT DISTINCT nume FROM echipa";

        List<HashMap<Integer, String>> list =
                DatabaseConnection.getMultipleColumns(query, 1);

        List<String> buffer = new ArrayList<>();
        list.forEach(linie -> {
            buffer.add(linie.get(1));
        });

        return buffer;
    }

    public List<String> getTeamEmployees() {
        String query = "SELECT A.nume, A.prenume\n" +
                "FROM Angajati A\n" +
                "INNER JOIN angajat_echipa AG ON AG.id_ang = A.id_ang\n" +
                "where id_echipa = '" +  getTeamId() + "' AND id_firma = '" + getFirmId() + "'\n" +
                "GROUP BY A.nume, A.prenume";

        List<HashMap<Integer, String>> list =
                DatabaseConnection.getMultipleColumns(query, 2);

        List<String> buffer = new ArrayList<>();
        list.forEach(linie -> {
            buffer.add(linie.get(1) + " " + linie.get(2));
        });

        return buffer;
    }

    public List<String> getEmployeesNotInTeam() {
        String query = "SELECT nume, prenume\n" +
                "FROM Angajati\n" +
                "where id_ang NOT IN \n" +
                "(SELECT A.id_ang\n" +
                "FROM Angajati A\n" +
                "INNER JOIN angajat_echipa AG ON AG.id_ang = A.id_ang\n" +
                "where id_echipa = '" + getTeamId() + "'\n" +
                "GROUP BY A.id_ang) AND id_firma = '" + getFirmId() + "'";

        List<HashMap<Integer, String>> list =
                DatabaseConnection.getMultipleColumns(query, 2);

        List<String> buffer = new ArrayList<>();
        list.forEach(linie -> {
            buffer.add(linie.get(1) + " " + linie.get(2));
        });

        return buffer;
    }

    public void setTeam(String te){
        current_team_sel = te;
    }

    public void updateTeam(HashMap<String, String> buffer) {
        DatabaseConnection.executeQuery("UPDATE echipa SET id_team_leader = '" + getLeaderId(buffer.get("team_leader")) + "' WHERE nume = '" + current_team_sel + "'");
        DatabaseConnection.executeQuery("UPDATE echipa SET nume = '" + buffer.get("team_name") + "' WHERE nume = '" + current_team_sel + "'");
    }

    public HashMap<String, String> teamDetails() {
        HashMap<String, String> buffer = new HashMap<>();
        String query =
                "SELECT id_team_leader FROM echipa WHERE" +
                        " nume = '" + current_team_sel + "'";

        List<HashMap<Integer, String>> list =
                DatabaseConnection.getMultipleColumns(query, 1);

        buffer.put("team_name", current_team_sel);
        buffer.put("firm_name", current_company_sel);
        buffer.put("team_leader", getLeaderName(list.get(0).get(1)));

        return buffer;
    }

    public void newTeam(HashMap<String, String> buffer) {
        String id_echipa = UUID.randomUUID().toString();

        DatabaseConnection.executeQuery("INSERT INTO echipa (id_echipa, nume, id_firma, id_team_leader) VALUES " +
                "('" + id_echipa + "', '" + buffer.get("team_name") + "', '" + getFirmId() + "', '" + getLeaderId(buffer.get("team_leader")) + "')");

    }

    public void addEmployee(String buffer) {
        String id = UUID.randomUUID().toString();

        DatabaseConnection.executeQuery("INSERT INTO angajat_echipa (id, id_ang, id_echipa) VALUES " +
                "('" + id + "', '" + getLeaderId(buffer) + "', '" + getTeamId() + "')");

    }

    public void deleteTeam(String delete_team) {
        DatabaseConnection.executeQuery("DELETE FROM echipa WHERE nume = '" + delete_team + "'");
        if(delete_team == current_team_sel)
                current_team_sel = "";
    }
    public void deleteEmployee(String delete_employee) {
        DatabaseConnection.executeQuery("DELETE FROM angajat_Echipa WHERE id_ang = '" + getLeaderId(delete_employee) + "' AND id_echipa = '" + getTeamId() + "'");
    }
}
