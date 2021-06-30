<%@ include file="common/header.jspf"%>
<%@ include file="common/navbar.jspf"%>

<div class="container">
    <div class="tab-pane m-t-60">
        <div class="login100-error m-b-20">
            <span>${errorMessage}</span>
        </div>
        <div class="login100-error m-b-50">
            <h4>Concediu cu plata</h4>
        </div>
        <form action = "/rest_get_paid/send" method="POST">
            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label">Data inceput concediu</label>
                <div class="col-lg-9">
                    <input class="form-control" type="date" name="start_date" value="">
                </div>
            </div>

            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label">Data inceput concediu</label>
                <div class="col-lg-9">
                    <input class="form-control" type="date" name="finish_date" value="">
                </div>
            </div>

            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label">Numar de zile</label>
                <div class="col-lg-9">
                    <input class="form-control" type="text" value="" name="days_number">
                </div>
            </div>

            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label">Nume Manager</label>
                <div class="col-lg-9">
                    <input class="form-control" type="text" value="" name="manager_name">
                </div>
            </div>

            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label"></label>
                <div class="col-lg-9">
                    <input type="reset" class="btn btn-secondary" value="Cancel">
                    <input type="submit" class="btn btn-primary" value="Trimite">
                </div>
            </div>
        </form>
        <br>
        <br>
        <div class="row justify-content-center">
            <a href="/storerest">
                <button class="btn btn-primary">Descarca/incarca cereri de concediu</button>
            </a>
        </div>
    </div>
</div>

<%@ include file="common/footer.jspf"%>