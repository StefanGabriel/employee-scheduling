<%@ include file="common/header.jspf" %>
<%@ include file="common/navbar.jspf" %>

<style>
    .btn-file {
        position: relative;
        overflow: hidden;
    }

    .btn-file input[type=file] {
        position: absolute;
        top: 0;
        right: 0;
        min-width: 100%;
        min-height: 100%;
        font-size: 100px;
        text-align: right;
        filter: alpha(opacity = 0);
        opacity: 0;
        outline: none;
        background: white;
        cursor: inherit;
        display: block;
    }
</style>

<div class="container">
    <div class="tab-pane m-t-60">
        <div>
            <div class="table-responsive">
                <h4>Descarcare fisiere</h4>
                <br>
                <table class="table table-striped" id="tblFileList">

                    <tbody>
                    <c:forEach items="${listOfEntries}" var="entry">
                        <tr>
                            <td><a href="${entry.href}" value="${entry.hrefText}"><c:out value = "${entry.hrefText}"/></a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <br>


        <div class="col-md-12 no_padding_left">
                <h4>Incarcare fisiere</h4>
                <br>
                <form method="POST" enctype="multipart/form-data" action="/files/upload">
                        <div class="col-md-9 text-left upload_divs_align no_padding_left">
                            <div class="input-group">
                                <label class="input-group-btn"> <span
                                        class="btn btn-primary"> Browse&hellip; <input
                                        type="file" name="file" accept=".pdf" id="customFile"
                                        style="display: none;" />
                                    </span>
                                </label> <input type="text" class="form-control" id="customFileHolder"
                                                readonly="true" />
                            </div>
                            <span class="help-block"> Selectati fisierul pentru incarcare.
                                </span>
                        </div>
                        <div class="col-md-2 upload_divs_align">
                            <input type="submit" id="btnUploadFile" value="Upload"
                                   class="btn btn-primary" />
                        </div>
                        <div class="col-md-5 text-left">
                            <c:if test="${not empty message}">
                                <div text="${message}" class="alert alert-success" role="alert" id="divUploadSuccess">
                                    Upload Success.
                                </div>
                            </c:if>

                        </div>

                </form>
            </div>
    </div>
</div>
<%@ include file="common/footer.jspf" %>
<script type="text/javascript" src="../../js/upload.js"></script>
