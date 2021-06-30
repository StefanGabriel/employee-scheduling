<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
    <head>
        <title>Attendance</title>
        <link href="webjars/bootstrap/4.4.1/css/bootstrap.min.css" rel="stylesheet">
        <link href="webjars/font-awesome/5.12.0/css/fontawesome.min.css"></link>
        <link rel="stylesheet" type="text/css" href="../../css/util.css">
        <link rel="stylesheet" type="text/css" href="../../css/main.css">
    </head>
    <body>
        <div class="container-login100">
            <div class="wrap-login100 p-t-70 p-b-10">
                <form class="login100-form" method = "POST">
                    <span class="login100-form-title p-b-70">
                            Bine ati venit
                    </span>
                    <div class="login100-error">
                        <span>${errorMessage}</span>
                        <span>${logoutMessage}</span>
                    </div>

                    <div class="wrap-input100 validate-input m-t-85 m-b-35">
                        <input class="input100" type="text" name="username">
                        <span class="focus-input100" data-placeholder="Username"></span>
                    </div>

                    <div class="wrap-input100 validate-input m-b-50">
                        <input class="input100" type="password" name="password">
                        <span class="focus-input100" data-placeholder="Password"></span>
                    </div>

                    <div class="container-login100-form-btn">
                        <button type = "submit" class="login100-form-btn">
                            Autentificare
                        </button>
                    </div>

                    <ul class="login-more p-t-80">
                        <li>
                            <span class="txt1">
                                Nu ai inca un cont?
                            </span>
                            <a href="/register" class="txt2">
                                Inregistreaza-te!
                            </a>
                        </li>
                    </ul>
                </form>
            </div>
        </div>

        <script src="webjars/jquery/3.3.1/jquery.min.js"></script>
        <script src="webjars/bootstrap/4.4.1/js/bootstrap.min.js"></script>
        <script src="webjars/bootstrap-datepicker/1.0.1/js/bootstrap-datepicker.js"></script>
    </body>
</html>