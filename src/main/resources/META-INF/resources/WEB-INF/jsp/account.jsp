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
        <form action = "/account/update_user_details" method="POST">
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
                <label class="col-lg-3 col-form-label form-control-label">Data nasterii</label>
                <div class="col-lg-9">
                    <input class="form-control" type="date" name="data" value="${birth_date}">
                </div>
            </div>
            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label">Functie</label>
                <div class="col-lg-9">
                    <input class="form-control" type="text" readonly value="${position}" name="position">
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
                    <input class="form-control" type="text" readonly="readonly" value="${username}">
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
    <div class="tab-pane m-t-60">
        <form action = "/account/change_password" method="POST">
            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label">Parola veche</label>
                <div class="col-lg-9">
                    <input class="form-control" required type="password" name="oldpass">
                </div>
            </div>
            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label">Parola noua</label>
                <div class="col-lg-9">
                    <input class="form-control" required type="password" name="newpass">
                </div>
            </div>
            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label">Confima parola</label>
                <div class="col-lg-9">
                    <input class="form-control" required type="password" name="newpassconfirm">
                </div>
            </div>

            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label"></label>
                <div class="col-lg-9">
                    <input type="reset" class="btn btn-secondary" value="Cancel">
                    <input type="submit" class="btn btn-primary" value="Schimba Parola">
                </div>
            </div>
        </form>
    </div>
</div>

<%@ include file="common/footer.jspf"%>