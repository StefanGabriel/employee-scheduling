<%@ include file="common/header.jspf" %>

<div class="container">
    <div class="tab-pane m-t-60 col-lg-9 m-l-100">
        <span class="login100-form-title p-b-70">
                            Introduceti datele noului cont
                    </span>
        <div class="login100-error m-b-20">
            <span>${errorMessage}</span>
        </div>
        <form action = "/register" method="POST">
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
                    <input class="form-control" type="date" name="birth_date" id="birth_date" value="${birth_date}">
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
                    <input class="form-control" type="text" value="${username}" name="username">
                </div>
            </div>
            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label">Parola</label>
                <div class="col-lg-9">
                    <input class="form-control" type="password" value="${password}" name="password">
                </div>
            </div>

            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label"></label>
                <div class="col-lg-9">
                    <input type="reset" class="btn btn-secondary" value="Cancel">
                    <input type="submit" class="btn btn-primary" value="Creeaza utilizator">
                </div>

            </div>
            <ul class="login-more p-t-10 m-l-190">
                <li>
                            <span class="txt1">
                                Ai deja un cont?
                            </span>
                    <a href="/login" class="txt2">
                        Autentifica-te!
                    </a>
                </li>
            </ul>
        </form>
    </div>
</div>

<%@ include file="common/footer.jspf" %>