<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<!DOCTYPE html>
<html lang="en">
    <!-- head -->
    <head>
        <title>Product manager</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="description" content="Little Closet template">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/styles/bootstrap-4.1.2/bootstrap.min.css"></c:url>">
        <link href="<c:url value="/resources/plugins/font-awesome-4.7.0/css/font-awesome.min.css"></c:url>" rel="stylesheet" type="text/css">
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/plugins/OwlCarousel2-2.2.1/owl.carousel.css"></c:url>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/plugins/OwlCarousel2-2.2.1/owl.theme.default.css"></c:url>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/plugins/OwlCarousel2-2.2.1/animate.css"></c:url>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/styles/main_styles.css"></c:url>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/resources/styles/responsive.css"></c:url>">
            <style>
                .productpic{
                    display: inline-block;
                }
                .form-control-fix{      
                    margin-top: 10px;
                    width: 70%;
                    padding: .375rem .75rem;
                    font-size: 1rem;
                    line-height: 1.5;
                    color: #495057;
                    background-color: #fff;
                    background-clip: padding-box;
                    border: 1px solid #ced4da;
                    border-radius: .25rem;
                    transition: border-color .15s ease-in-out,box-shadow .15s ease-in-out;
                }
            </style>
        </head>
        <body>
            <!-- Mobile Menu -->
        <jsp:include page="../include/mobile-menu.jsp"/>

        <div class="super_container">

            <!-- Header -->
            <jsp:include page="../include/header.jsp"/>

            <div class="super_container_inner">
                <div class="super_overlay"></div>		
                <div class="container">
                    <div class="row" style="margin-top: 100px">
                        <div class="col-lg-6 offset-lg-3">
                            <div class="section_title text-center">Edit Product</div>
                        </div>
                    </div>
                    <div class="row page_nav_row">
                        <div class="col">
                            <div class="page_nav">
                                <ul class="d-flex flex-row align-items-start justify-content-center">
                                    <jsp:include page="../include/account-menu.jsp"/>  
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="row mainmain">                            
                        <div class="col-xs-12 col-sm-12">
                            <p style="font-size: 150%;color: red;text-align: center">${param.messageError}</p>
                            <p style="font-size: 150%;color: blue;text-align: center">${param.messageSuccess}</p>
                            <f:form action="${pageContext.request.getContextPath()}/seller/edit-product" method="post" modelAttribute="product" id="form" class="form-horizontal">
                                <div class="table-responsive">
                                    <table class="table table-bordered">
                                        <input type="hidden" name="id" id="productid" value="${product.id}" />
                                        <tr>
                                            <th>Name <span style="color: red">(*)</span></th>
                                            <td><input type="text" name="name" value="${product.name}" class="form-control"/></td>
                                        </tr>
                                        <tr>
                                            <th>Brand</th>
                                            <td><input type="text" name="brand" value="${product.brand}" class="form-control"/></td>
                                        </tr>
                                        <tr>
                                            <th>Code <span style="color: red">(*)</span></th>
                                            <td><input type="text" name="code" value="${product.code}" class="form-control"/></td>
                                        </tr>
                                        <tr>
                                            <th>Category</th>
                                            <td>
                                                <select class="form-control" name="category.id">
                                                    <c:forEach var="category" items="${categorys}">
                                                        <option value="${category.id}" <c:if test="${product.category.id == category.id}">selected</c:if>>${category.name}</option>
                                                    </c:forEach>                                                    
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>Description</th>
                                            <td><textarea name="description" rows="3" class="form-control">${product.description}</textarea></td>
                                        </tr>
                                        <tr>
                                            <th>Price <span style="color: red">(*)</span></th>
                                            <td><input type="number" name="price" value="${product.price}" class="form-control"/></td>
                                        </tr>
                                        <tr>
                                            <th>Color</th>
                                            <td>
                                                <select class="form-control" name="color.id">
                                                    <c:forEach var="color" items="${colors}">
                                                        <option value="${color.id}" <c:if test="${product.color.id == color.id}">selected</c:if>>${color.color}</option>
                                                    </c:forEach>                                                    
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>Size <span style="color: red">(*)</span></th>
                                            <td>
                                                <c:forEach items="${sizes}" var="size" varStatus="status">
                                                    <c:choose>
                                                        <c:when test="${sizeBoolean[status.index]==true}">
                                                            <label class="checkbox-inline"><input type="checkbox" name="size" value="${size.id}" checked>${size.size}</label>
                                                            </c:when>
                                                            <c:otherwise>
                                                            <label class="checkbox-inline"><input type="checkbox" name="size" value="${size.id}">${size.size}</label>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>Images link <span style="color: red">(*)</span></th>
                                            <td>
                                                <div id="img_list">
                                                    <c:forEach var="img" items="${product.images}">
                                                        <div class="productpic" id="productpic_${img.id}">
                                                            <img src="${img.url}" class="img-thumbnail" width="150" height="200"/>
                                                            <button type="button" class="btn" imgid="${img.id}"><i class="fa fa-trash"></i></button>
                                                        </div>                                                    
                                                    </c:forEach>  
                                                </div>

                                                <div id="addnewimage">
                                                    <label style="margin-top: 10px" for="newImage">Add new image</label>
                                                    <input type="text" id="newImage" class="form-control-fix" placeholder="Enter link of image"/>
                                                    <button type="button" class="btn btn-info"><i class="fa fa-plus" aria-hidden="true"></i> Add</button>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>Status</th>
                                            <td>
                                                <c:forEach items="${status}" var="s">
                                                    <label class="radio-inline" style="margin-right: 7px">
                                                        <input type="radio" name="statusradio" value="${s}" <c:if test="${product.statusString==s}">checked</c:if>>${s}
                                                        </label>                                        
                                                </c:forEach>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-12 col-xs-12" style="text-align: center">
                                        <button type="button" id="btn_submit" class="btn btn-primary">Update</button>                                      
                                    </div>
                                </div>
                            </f:form>
                        </div>
                    </div>                   

                </div>

            </div>
        </div>

        <!-- Footer -->
        <jsp:include page="../include/footer.jsp"/>

        <!-- script -->
        <script src="<c:url value="/resources/js/jquery-3.2.1.min.js"></c:url>"></script>
        <script src="<c:url value="/resources/styles/bootstrap-4.1.2/popper.js"></c:url>"></script>
        <script src="<c:url value="/resources/styles/bootstrap-4.1.2/bootstrap.min.js"></c:url>"></script>
        <script src="<c:url value="/resources/plugins/greensock/TweenMax.min.js"></c:url>"></script>
        <script src="<c:url value="/resources/plugins/greensock/TimelineMax.min.js"></c:url>"></script>
        <script src="<c:url value="/resources/plugins/scrollmagic/ScrollMagic.min.js"></c:url>"></script>
        <script src="<c:url value="/resources/plugins/greensock/animation.gsap.min.js"></c:url>"></script>
        <script src="<c:url value="/resources/plugins/greensock/ScrollToPlugin.min.js"></c:url>"></script>
        <script src="<c:url value="/resources/plugins/OwlCarousel2-2.2.1/owl.carousel.js"></c:url>"></script>
        <script src="<c:url value="/resources/plugins/easing/easing.js"></c:url>"></script>
        <script src="<c:url value="/resources/plugins/progressbar/progressbar.min.js"></c:url>"></script>
        <script src="<c:url value="/resources/plugins/parallax-js-master/parallax.min.js"></c:url>"></script>
        <script src="<c:url value="/resources/js/custom.js"></c:url>"></script>
        <script>
            function is_url(str)
            {
                regexp = /^(?:(?:https?|ftp):\/\/)?(?:(?!(?:10|127)(?:\.\d{1,3}){3})(?!(?:169\.254|192\.168)(?:\.\d{1,3}){2})(?!172\.(?:1[6-9]|2\d|3[0-1])(?:\.\d{1,3}){2})(?:[1-9]\d?|1\d\d|2[01]\d|22[0-3])(?:\.(?:1?\d{1,2}|2[0-4]\d|25[0-5])){2}(?:\.(?:[1-9]\d?|1\d\d|2[0-4]\d|25[0-4]))|(?:(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)(?:\.(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)*(?:\.(?:[a-z\u00a1-\uffff]{2,})))(?::\d{2,5})?(?:\/\S*)?$/;
                if (regexp.test(str))
                {
                    return true;
                } else
                {
                    return false;
                }
            }
            $(document).ready(function ()
            {
                "use strict";
                initImageAction();
                initImageUpload();
                function initImageAction()
                {
                    if ($('.productpic').length) {
                        var productimgs = $('.productpic');
                        productimgs.each(function () {
                            var productimg = $(this);
                            var deletebutton = productimg.find('.btn')
                            deletebutton.on('click', function ()
                            {
                                var id = $(this).attr('imgid');
                                $.get("api/delete-product-img/" + id, function (data) {
                                    var img = $("#productpic_" + id);
                                    if (data === "true") {
                                        img.remove();
                                    } else {
                                        alert("Can't delete image");
                                    }
                                });
                            });
                        });
                    }
                }
                function initImageUpload()
                {
                    if ($('#addnewimage').length) {
                        var div = $('#addnewimage');
                        var button = div.find('button[type="button"]');
                        button.on('click', function ()
                        {
                            var url = $('#newImage').val();
                            if (!is_url(url)) {
                                alert("your url input is incorrect");
                                return;
                            }
                            var id = $('#productid').val();
                            $.get("api/add-product-img?id=" + id + "&url=" + url, function (data) {
                                var response = data.toString();
                                if (response.includes("true")) {
                                    var imgid = response.split("|")[1];
                                    var ele = '<div class="productpic" id="productpic_' + imgid + '">' + '<img src="' + url + '" class="img-thumbnail" width="150" height="200"/>' + '<button type="button" class="btn" imgid="' + imgid + '"><i class="fa fa-trash"></i></button></div>';
                                    $("#img_list").append(ele);
                                    $('#newImage').val("");
                                } else if (response === "error_img") {
                                    alert("your url input is incorrect");
                                } else {
                                    alert("Can't add image");
                                }
                            });
                        });
                    }
                }
                
                $('#btn_submit').on('click', function (){
                    if($('input[name="name"]').val()==""){
                        alert("Please input Name"); 
                        return;
                    }
                    if($('input[name="code"]').val()==""){
                        alert("Please input Code"); 
                        return;
                    }
                    if($('input[name="price"]').val()==""){
                        alert("Please input Price"); 
                        return;
                    }                 
                    var sizes = $('input[name="size"]:checked');
                    if(sizes.length == 0){
                         alert("Please select size"); 
                        return;
                    }
                    if ($('.productpic').length){                        
                        $('#form').submit();
                    }else{
                        alert("Please add image");                        
                    }
                });

            });
        </script>
    </body>
</html>