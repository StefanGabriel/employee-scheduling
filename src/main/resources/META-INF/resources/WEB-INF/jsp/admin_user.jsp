<%@ include file="common/header.jspf"%>
<%@ include file="common/navbar.jspf"%>
<script src="webjars/pickadate.js"></script>
<script src="webjars/jquery/3.3.1/jquery.min.js"></script>
<script src="webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<script src="webjars/font-awesome/5.12.0/js/fontawesome.min.js"></script>
<script src="webjars/bootstrap-datepicker/1.0.1/js/bootstrap-datepicker.js"></script>

<div class="container">
    <div class="tab-pane m-t-60">
        <div class="login100-error m-b-20">
            <span>${errorMessage}</span>
        </div>
        <div class="login100-error m-b-50">
            <span>Acum editezi datele angajatului cu numele ${employee}</span>
        </div>
        <form action = "/admin_user/updatedetails" method="POST">
            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label">Nume</label>
                <div class="col-lg-9">
                    <input class="form-control" type="text" value="${last_name}" name="last_name">
                </div>
            </div>
            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label">Prenume</label>
                <div class="col-lg-9">
                    <input class="form-control" type="text" value="${first_name}" name="first_name">
                </div>
            </div>
            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label">Email</label>
                <div class="col-lg-9">
                    <input class="form-control" type="email" value="${email}" name="email">
                </div>
            </div>
            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label">CNP</label>
                <div class="col-lg-9">
                    <input class="form-control" type="text" value="${cnp}" name="cnp">
                </div>
            </div>
            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label">Serie CI</label>
                <div class="col-lg-9">
                    <input class="form-control" type="text" value="${ci_series}" name="ci_series">
                </div>
            </div>
            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label">Numar CI</label>
                <div class="col-lg-9">
                    <input class="form-control" type="text" value="${ci_number}" name="ci_number">
                </div>
            </div>
            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label">Data nasterii</label>
                <div class="col-lg-9">
                    <input class="form-control" type="date" name="data" value="${birth_date}">
                </div>
            </div>
            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label">Functie</label>
                <div class="col-lg-9">
                    <input class="form-control" type="text" value="${position}" name="position">
                </div>
            </div>
            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label">Adresa</label>
                <div class="col-lg-7">
                    <input class="form-control" type="text" value="${street}" placeholder="Strada" name="street">
                </div>
                <div class="col-lg-2">
                    <input class="form-control" type="text" value="${number}" placeholder="Numar" name="number">
                </div>
            </div>
            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label"></label>
                <div class="col-lg-3">
                    <input class="form-control" type="text" value="${city}" placeholder="Localitate" name="city">
                </div>
                <div class="col-lg-3">
                    <input class="form-control" type="text" value="${county}" placeholder="Judet" name="county">
                </div>
                <div class="col-lg-3">
                    <input class="form-control" type="text" value="${country}" placeholder="Tara" name="country">
                </div>
            </div>
            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label">Username</label>
                <div class="col-lg-9">
                    <input class="form-control" type="text" value="${username}" readonly name="username">
                </div>
            </div>
            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label">Stare (Activ/Inactiv)</label>
                <div class="col-lg-9">
                    <input class="form-control" type="text" value="${state}" name="state">
                </div>
            </div>
            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label">Nume Firma</label>
                <div class="col-lg-9">
                    <input class="form-control" type="text" value="${company_name}" name="company_name">
                </div>
            </div>

            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label">Is Manager (0/1)</label>
                <div class="col-lg-9">
                    <input class="form-control" type="text" value="${is_manager}" name="is_manager">
                </div>
            </div>
            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label">Salariu</label>
                <div class="col-lg-9">
                    <input class="form-control" type="text" value="${money}" name="money">
                </div>
            </div>
            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label">Norma de lucru (numar de ore)</label>
                <div class="col-lg-9">
                    <input class="form-control" type="text" value="${working_hours}" name="working_hours">
                </div>
            </div>
            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label">Data angajarii</label>
                <div class="col-lg-9">
                    <input class="form-control" type="date" name="first_day" value="${first_day}">
                </div>
            </div>

            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label"></label>
                <div class="col-lg-9">
                    <input type="reset" class="btn btn-secondary" value="Cancel">
                    <input type="submit" class="btn btn-primary" value="Editeaza">
                </div>
            </div>
        </form>
    </div>
</div>

<%@ include file="common/footer.jspf"%>