<%@ include file="common/header.jspf"%>
<%@ include file="common/navbar.jspf"%>

<div class="container">
    <div class="row">
        <div class="col-lg-12 m-t-100 m-b-50">
            <h1>Bine ai venit,&nbsp;${employee}!</h1>
        </div>

    </div>
    <br>
    <c:if test = "${isman == 'TRUE'}">
        <div class="row">
            <div class="col-lg-5 m-t-100 m-b-50">
                <h3>Selecteaza userul pentru care vrei sa faci modificari</h3>
            </div>

        </div>
        <div class="row">
            <div class="col-lg-12">
                <form action = "/welcome/send_user" method="POST">
                    <div class="form-group row ">
                    <div class="col-lg-2 welcome_align">
                        <select id="user_select_drop" name="user_select" class="select-css">
                            <c:forEach items="${user_list}" var="model">
                                <option value="${model}">${model}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-lg-5 upload_divs_align">
                        <input type="submit" class="btn btn-primary" value="Trimite">
                    </div>
                </div>
                    <div class="form-group row">
                        <label class="col-lg-2 col-form-label form-control-label no_padding_right">User curent:</label>
                        <div class="col-lg-2 shift_left">
                            <input class="form-control" type="text" value="${display_user}" readonly="readonly" name="display_user">
                        </div>
                    </div>
                </form>
                <br>
                <div class="table-responsive">
                    <h4>Salariile maxime pentru fiecare firma:</h4>
                    <br>
                    <table class="table table-striped" id="tblFileList">
                        <tbody>
                        <tr>
                            <th><a>Firma</a></th>
                            <th><a>Salariu (LEI)</a></th>
                        </tr>
                        <c:forEach items="${list_of_max}" var="map">
                            <tr>
                            <c:forEach items="${map}" var="entry">
                                <td><a value="${entry.value}"><c:out value = "${entry.value}"/></a></td>
                            </c:forEach>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <br>
                <div class="table-responsive">
                    <h4>Angajatii care detin salariile maxime din fiecare firma:</h4>
                    <br>
                    <table class="table table-striped" id="tblFileList">
                        <tbody>
                        <tr>
                            <th><a>Nume</a></th>
                            <th><a>Prenume</a></th>
                            <th><a>Salariu (LEI)</a></th>
                            <th><a>Firma</a></th>
                        </tr>
                        <c:forEach items="${list_of_maxs}" var="map">
                            <tr>
                                <c:forEach items="${map}" var="entry">
                                    <td><a value="${entry.value}"><c:out value = "${entry.value}"/></a></td>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>

                <form action = "/welcome/getdata" method="POST">
                    <h4>Afisare numarul de ore de invoire pentru o firma la alegere si pentru o luna la alegere:</h4>
                    <br>
                    <div class="form-group row ">
                        <div class="col-lg-3">
                            <select name="month" id="month" class="select-css">
                                <option value="1" selected>Ianuarie</option>
                                <option value="2">Februarie</option>
                                <option value="3">Martie</option>
                                <option value="4">Aprilie</option>
                                <option value="5">Mai</option>
                                <option value="6">Iunie</option>
                                <option value="7">Iulie</option>
                                <option value="8">August</option>
                                <option value="9">Septembrie</option>
                                <option value="10">Octombrie</option>
                                <option value="11">Noiembrie</option>
                                <option value="12">Decembrie</option>
                            </select>
                        </div>
                        <div class="col-lg-2">
                            <select name="year" id="year" class="select-css">
                                <option value="2015">2015</option>
                                <option value="2016">2016</option>
                                <option value="2017">2017</option>
                                <option value="2018">2018</option>
                                <option value="2019">2019</option>
                                <option value="2020">2020</option>
                            </select>
                        </div>
                        <div class="col-lg-2 welcome_align">
                            <select id="company_select_drop" name="company_select_drop" class="select-css">
                                <c:forEach items="${companies_list}" var="model">
                                        <option value="${model}">${model}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="col-lg-5 upload_divs_align">
                            <input type="submit" class="btn btn-primary" value="Afiseaza informatia">
                        </div>
                        <div class="col-lg-2 m-t-10">
                            <input class="form-control" type="text" value="${number_hours}" readonly="readonly" name="number_hours">
                        </div>
                    </div>
                </form>
                <br>
                <form action = "/welcome/getdata1" method="POST">
                    <h4>Afisare angajati care au facut cereri de invoire in intervalul specificat, s-au invoit mai mult de (numar de ore) si sunt de la firma (firma):</h4>
                    <br>
                    <div class="form-group row">
                        <div class="col-lg-3">
                            <input class="form-control" type="date" name="start_date" value="${start_date}">
                        </div>

                        <div class="col-lg-3">
                            <input class="form-control" type="date" name="end_date" value="${end_date}">
                        </div>
                        <div class="col-lg-1">
                            <input class="form-control" type="text" value="${nr_hours}" name="nr_hours">
                        </div>

                        <div class="col-lg-3 welcome_align">
                            <select id="company_select_drop1" name="company_select_drop1" class="select-css">
                                <c:forEach items="${companies_list1}" var="model">
                                    <option value="${model}">${model}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-lg-5 upload_divs_align">
                            <input type="submit" class="btn btn-primary" value="Afiseaza informatia">
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-striped" id="tblFileList">
                            <tbody>
                            <c:forEach items="${list_empl_c}" var="map">
                                <tr>
                                    <c:forEach items="${map}" var="entry">
                                        <td><a value="${entry.value}"><c:out value = "${entry.value}"/></a></td>
                                    </c:forEach>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </form>
                <br>
                <form action = "/welcome/getdata2" method="POST">
                    <h4>Vreau sa vad ce angajati au venit in ziua (ziua) de la ora (ora) si au stat mai mult de (ore) ore:</h4>
                    <br>
                    <div class="form-group row">
                        <div class="col-lg-3">
                            <input class="form-control" type="date" name="c_date" value="${c_date}">
                        </div>

                        <div class="col-lg-1">
                            <input class="form-control" type="text" value="${c_ora}" name="c_ora">
                        </div>
                        <div class="col-lg-1">
                            <input class="form-control" type="text" value="${c_ore}" name="c_ore">
                        </div>

                        <div class="col-lg-5 upload_divs_align">
                            <input type="submit" class="btn btn-primary" value="Afiseaza informatia">
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-striped" id="tblFileList">
                            <tbody>
                            <c:forEach items="${list_empl_c1}" var="map">
                                <tr>
                                    <c:forEach items="${map}" var="entry">
                                        <td><a value="${entry.value}"><c:out value = "${entry.value}"/></a></td>
                                    </c:forEach>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </form>
                <br>
                <form action = "/welcome/getdata3" method="POST">
                    <h4>Vreau sa vad cate zile de concediu fara plata si-au luat angajatii in anul (an):</h4>
                    <br>
                    <div class="form-group row">
                        <div class="col-lg-3">
                            <input class="form-control" type="text" name="c_year" value="${c_year}">
                        </div>
                        <div class="col-lg-5 upload_divs_align">
                            <input type="submit" class="btn btn-primary" value="Afiseaza informatia">
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-striped" id="tblFileList">
                            <tbody>
                            <c:forEach items="${list_empl_c2}" var="map">
                                <tr>
                                    <c:forEach items="${map}" var="entry">
                                        <td><a value="${entry.value}"><c:out value = "${entry.value}"/></a></td>
                                    </c:forEach>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </form>
                <br>
                <form action = "/welcome/getdata4" method="POST">
                    <h4>Vreau sa vad cate zile de concediu cu plata si-au luat angajatii pana la data (data) si cate zile mai au ramase:</h4>
                    <br>
                    <div class="form-group row">
                        <div class="col-lg-3">
                            <input class="form-control" type="date" name="c_date1" value="${c_date1}">
                        </div>
                        <div class="col-lg-5 upload_divs_align">
                            <input type="submit" class="btn btn-primary" value="Afiseaza informatia">
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-striped" id="tblFileList">
                            <tbody>
                            <c:forEach items="${list_empl_c3}" var="map">
                                <tr>
                                    <c:forEach items="${map}" var="entry">
                                        <td><a value="${entry.value}"><c:out value = "${entry.value}"/></a></td>
                                    </c:forEach>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </form>
                <form action = "/welcome/getdata5" method="POST">
                    <h4>Vreau sa vad angajatii care au un salariu mai mare decat media salariilor pe firma din care fac parte si sunt din echipa (echipa):</h4>
                    <br>
                    <div class="form-group row">
                        <div class="col-lg-3 welcome_align">
                            <select id="team" name="team" class="select-css">
                                <c:forEach items="${list_empl_c4}" var="model">
                                    <option value="${model}">${model}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-lg-5 upload_divs_align">
                            <input type="submit" class="btn btn-primary" value="Afiseaza informatia">
                        </div>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-striped" id="tblFileList">
                            <tbody>
                            <c:forEach items="${list_empl_c5}" var="map">
                                <tr>
                                    <c:forEach items="${map}" var="entry">
                                        <td><a value="${entry.value}"><c:out value = "${entry.value}"/></a></td>
                                    </c:forEach>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </form>
            </div>
        </div>
    </c:if>
</div>
<br>
<br>
<br>

<script src="webjars/jquery/3.3.1/jquery.min.js"></script>
<script>
    $(document).ready(function(){
        $('#user_select_drop').val("${display_user}");
        $("#company_select_drop").val("${company_select_drop}");
        $("#company_select_drop1").val("${company_select_drop1}");
        $("#team").val("${team}");
        $("#month").val("${month}");
        $("#year").val("${year}");
    });
</script>

<%@ include file="common/footer.jspf"%>