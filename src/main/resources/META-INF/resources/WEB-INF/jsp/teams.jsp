<%@ include file="common/header.jspf"%>
<%@ include file="common/navbar.jspf"%>
<script src="webjars/pickadate.js"></script>
<script src="webjars/jquery/3.3.1/jquery.min.js"></script>
<script src="webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<script src="webjars/font-awesome/5.12.0/js/fontawesome.min.js"></script>
<script src="webjars/bootstrap-datepicker/1.0.1/js/bootstrap-datepicker.js"></script>
<script>
    $(document).ready(function(){
        $("#company_select_drop").val("${current_company}");
        $("#team_select").val("${current_team}");

        if($("#employee_add_list").val() == null){
            $("#addbutton").attr("disabled", true);
        }
        else{
            $("#addbutton").attr("disabled", false);
        }
        // if($("#company_select_drop").val() == null){
        //     $("#send1").attr("disabled", true);
        // }
        // else{
        //     $("#send1").attr("disabled", false);
        // }

    });
</script>
<div class="container">
    <div class="tab-pane m-t-10">
        <div class="login100-error m-b-20">
            <span>${errorMessage}</span>
        </div>
        <c:if test = "${isman == 'TRUE'}">
        <div class="row">
            <div class="col-lg-5 m-t-10 m-b-10">
                <h3>Selecteaza compania</h3>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <form action = "/teams/companies/list" method="POST">
                    <div class="form-group row ">
                        <div class="col-lg-2 welcome_align">
                            <select id="company_select_drop" name="company_select_drop" class="select-css">
                                <c:forEach items="${companies_list}" var="model">
                                    <option value="${model}">${model}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-lg-5 upload_divs_align">
                            <input type="submit" id="send1" class="btn btn-primary" value="Trimite">
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <c:if test = "${current_company ne 'nothing'}">
            <div class="table-responsive">
                <h4>Echipe</h4>
                <br>
                <table class="table table-striped" id="tblFileList">

                    <tbody>
                    <c:forEach items="${listOfEntries}" var="entry">
                        <tr>
                            <td><a value="${entry}"><c:out value = "${entry}"/></a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="row">
                <div class="col-lg-5 m-t-10 m-b-10">
                    <h3>Selecteaza echipa pe care doresti sa o stergi</h3>
                </div>
            </div>
            <form action = "/teams/teamdelete" method="POST">
                <div class="form-group row ">
                    <div class="col-lg-3 welcome_align">
                        <select id="team_delete" name="team_delete" class="select-css">
                            <c:forEach items="${listOfEntries}" var="model">
                                <option value="${model}">${model}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-lg-5 upload_divs_align">
                        <input type="submit" class="btn btn-primary" value="Sterge">
                    </div>
                </div>
            </form>
            <br>
            <div class="row">
                <div class="col-lg-5 m-t-10 m-b-10">
                    <h3>Selecteaza echipa</h3>
                </div>
            </div>
            <form action = "/teams/teamselect" method="POST">
                <div class="form-group row ">
                    <div class="col-lg-3 welcome_align">
                        <select id="team_select" name="team_select" class="select-css">
                            <c:forEach items="${listOfEntries}" var="model">
                                <option value="${model}">${model}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-lg-5 upload_divs_align">
                        <input type="submit" class="btn btn-primary" value="Trimite">
                    </div>
                </div>
            </form>
            <c:if test = "${current_team ne 'nothing'}">
                <div class="row">
                    <div class="col-lg-12 m-t-30 m-b-20 text-center">
                        <h3>Detalii echipa</h3>
                    </div>
                </div>

                <form action = "/teams/updateteam" method="POST">
                    <div class="form-group row">
                        <label class="col-lg-3 col-form-label form-control-label">Nume echipa</label>
                        <div class="col-lg-9">
                            <input class="form-control" type="text" value="${team_name}" name="team_name">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-lg-3 col-form-label form-control-label">Nume firma</label>
                        <div class="col-lg-9">
                            <input class="form-control" type="text" readonly value="${firm_name}" name="firm_name">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-lg-3 col-form-label form-control-label">Team leader</label>
                        <div class="col-lg-9">
                            <input class="form-control" type="text" value="${team_leader}" name="team_leader">
                        </div>
                    </div>

                    <div class="form-group row">
                        <label class="col-lg-3 col-form-label form-control-label"></label>
                        <div class="col-lg-9">
                            <input type="submit" class="btn btn-primary" value="Editeaza">
                        </div>
                    </div>
                </form>
                <div class="table-responsive">
                    <h4>Angajatii echipei</h4>
                    <br>
                    <table class="table table-striped" id="tblFileList">
                        <tbody>
                        <c:forEach items="${team_employees}" var="entry">
                            <tr>
                                <td><a value="${entry}"><c:out value = "${entry}"/></a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="row">
                    <div class="col-lg-5 m-t-10 m-b-10">
                        <h3>Selecteaza angajatul pe care doresti sa il stergi</h3>
                    </div>
                </div>
                <form action = "/teams/employeedelete" method="POST">
                    <div class="form-group row ">
                        <div class="col-lg-3 welcome_align">
                            <select id="employee_delete" name="employee_delete" class="select-css">
                                <c:forEach items="${team_employees}" var="entry">
                                    <option value="${entry}">${entry}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-lg-5 upload_divs_align">
                            <input type="submit" class="btn btn-primary" value="Sterge">
                        </div>
                    </div>
                </form>
                <br>
                <form action = "/teams/addemployee" method="POST">
                    <div class="col-lg-12 m-t-30 m-b-20 no_padding_left">
                        <h4>Adauga angajat la echipa</h4>
                    </div>
                    <div class="form-group row">
                        <div class="col-lg-3 welcome_align">
                            <select id="employee_add_list" name="employee_add_list" class="select-css">
                                <c:forEach items="${employee_add_list}" var="model">
                                    <option value="${model}">${model}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-lg-5 welcome_align">
                            <input type="submit" id="addbutton" class="btn btn-primary" value="Adauga">
                        </div>
                    </div>
                </form>
            </c:if>
            <div class="row">
                <div class="col-lg-12 m-t-30 m-b-20 text-center">
                    <h3>Adauga echipa</h3>
                </div>
            </div>

            <form action = "/teams/newteam" method="POST">
                <div class="form-group row">
                    <label class="col-lg-3 col-form-label form-control-label">Nume echipa</label>
                    <div class="col-lg-9">
                        <input class="form-control" type="text" value="${team_name_new}" name="team_name_new">
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-lg-3 col-form-label form-control-label">Team leader</label>
                    <div class="col-lg-5 welcome_align">
                        <select id="employee_list" name="employee_list" class="select-css">
                            <c:forEach items="${employee_list}" var="model">
                                <option value="${model}">${model}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="form-group row">
                    <label class="col-lg-3 col-form-label form-control-label"></label>
                    <div class="col-lg-9">
                        <input type="submit" class="btn btn-primary" value="Editeaza">
                    </div>
                </div>
            </form>

        </c:if>

    </c:if>
    </div>
</div>

<%@ include file="common/footer.jspf"%>