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
        <meta name="description" content="Movies Unlimited template">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="<c:url value="/styles/bootstrap-4.1.2/bootstrap.min.css"></c:url>">
        <link href="<c:url value="/plugins/font-awesome-4.7.0/css/font-awesome.min.css"></c:url>" rel="stylesheet" type="text/css">
        <link rel="stylesheet" type="text/css" href="<c:url value="/plugins/OwlCarousel2-2.2.1/owl.carousel.css"></c:url>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/plugins/OwlCarousel2-2.2.1/owl.theme.default.css"></c:url>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/plugins/OwlCarousel2-2.2.1/animate.css"></c:url>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/styles/main_styles.css"></c:url>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/styles/responsive.css"></c:url>">
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
                                <div class="section_title text-center">Product manager</div>
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
                                <p style="font-size: 150%;color: red;text-align: center">${messageError}</p>
                            <p style="font-size: 150%;color: blue;text-align: center">${messageSuccess}</p>
                            <f:form action="${pageContext.request.getContextPath()}/seller/add-product" method="post" modelAttribute="product" class="form-horizontal">
                                <div class="table-responsive">
                                    <table class="table table-bordered">
                                        <tr>
                                            <th>Name <span style="color: red">(*)</span></th>
                                            <td><input type="text" name="name" class="form-control" required/></td>
                                        </tr>
                                        <tr>
                                            <th>Brand</th>
                                            <td><input type="text" name="brand" class="form-control"/></td>
                                        </tr>
                                        <tr>
                                            <th>Code <span style="color: red">(*)</span></th>
                                            <td><input type="text" name="code" class="form-control" required/></td>
                                        </tr>
                                        <tr>
                                            <th>Category</th>
                                            <td>
                                                <select class="form-control" name="category.id">
                                                    <c:forEach var="category" items="${categorys}">
                                                        <option value="${category.id}">${category.name}</option>
                                                    </c:forEach>                                                    
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>Description</th>
                                            <td><textarea name="description" rows="3" class="form-control"></textarea></td>
                                        </tr>
                                        <tr>
                                            <th>Price <span style="color: red">(*)</span></th>
                                            <td><input type="number" name="price" class="form-control" required/></td>
                                        </tr>
                                        <tr>
                                            <th>Color</th>
                                            <td>
                                                <select class="form-control" name="color.id">
                                                    <c:forEach var="color" items="${colors}">
                                                        <option value="${color.id}">${color.color}</option>
                                                    </c:forEach>                                                    
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>Size</th>
                                            <td>
                                                <c:forEach items="${sizes}" var="size">
                                                    <label class="checkbox-inline"><input type="checkbox" name="size" value="${size.id}">${size.size}</label>                                                    
                                                </c:forEach>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>Images link <span style="color: red">(*)</span></th>
                                            <td><textarea name="imageLink" rows="6" class="form-control" required></textarea></td>
                                        </tr>
                                    </table>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-12 col-xs-12" style="text-align: center">
                                        <button type="submit" class="btn btn-primary">Add</button>                                      
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
            <script src="<c:url value="/js/jquery-3.2.1.min.js"></c:url>"></script>
        <script src="<c:url value="/styles/bootstrap-4.1.2/popper.js"></c:url>"></script>
        <script src="<c:url value="/styles/bootstrap-4.1.2/bootstrap.min.js"></c:url>"></script>
        <script src="<c:url value="/plugins/greensock/TweenMax.min.js"></c:url>"></script>
        <script src="<c:url value="/plugins/greensock/TimelineMax.min.js"></c:url>"></script>
        <script src="<c:url value="/plugins/scrollmagic/ScrollMagic.min.js"></c:url>"></script>
        <script src="<c:url value="/plugins/greensock/animation.gsap.min.js"></c:url>"></script>
        <script src="<c:url value="/plugins/greensock/ScrollToPlugin.min.js"></c:url>"></script>
        <script src="<c:url value="/plugins/OwlCarousel2-2.2.1/owl.carousel.js"></c:url>"></script>
        <script src="<c:url value="/plugins/easing/easing.js"></c:url>"></script>
        <script src="<c:url value="/plugins/progressbar/progressbar.min.js"></c:url>"></script>
        <script src="<c:url value="/plugins/parallax-js-master/parallax.min.js"></c:url>"></script>
        <script src="<c:url value="/js/custom.js"></c:url>"></script>
    </body>
</html>