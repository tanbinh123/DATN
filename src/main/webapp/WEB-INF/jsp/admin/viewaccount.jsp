<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<!DOCTYPE html>
<html lang="en">
    <!-- head -->
    <head>
        <title>Edit account</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="description" content="Little Closet template">
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
                                <div class="section_title text-center">Edit account</div>
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
                            <f:form action="${pageContext.request.getContextPath()}/admin/update-account" method="post" modelAttribute="account" class="form-horizontal">
                                <div class="table-responsive">
                                    <table class="table table-bordered">
                                        <tr>
                                            <th>Full name <span style="color: red">(*)</span></th>
                                            <td><input type="text" name="fullName" value="${account.fullName}" pattern="[A-Za-z ]{3,50}" title="Only letters and space are allowed, length 3-50" class="form-control" required/></td>
                                        </tr>
                                        <tr>
                                            <th>Email <span style="color: red">(*)</span></th>
                                            <td><input type="email" name="email" value="${account.email}" class="form-control" required/></td>
                                        </tr>
                                        <tr>
                                            <th>Phone</th>
                                            <td><input type="text" name="phone" value="${account.phone}" pattern="[0-9]{10,11}" title="Only numbers are allowed, length 10-11" class="form-control"/></td>
                                        </tr>
                                        <tr>
                                            <th>Birthday</th>
                                            <td><input type="date" name="birthday" value="${account.birthdayFormat}" class="form-control"/></td>
                                        </tr>
                                        <tr>
                                            <th>Address</th>
                                            <td><input type="text" name="address" value="${account.address}" class="form-control"/></td>
                                        </tr>
                                        <tr>
                                            <th>Role</th>
                                            <td>
                                                <c:forEach items="${roles}" var="role">
                                                    <label class="radio-inline" style="margin-right: 7px">
                                                        <input type="radio" name="roleradio" value="${role.id}" <c:if test="${account.roleString==role.roleString}">checked</c:if>>${role.roleString}
                                                        </label>                                        
                                                </c:forEach>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>Status</th>
                                            <td>
                                                <c:forEach var="status" items="${activeStatus}">
                                                    <label class="radio-inline" style="margin-right: 7px">
                                                        <input type="radio" name="statusradio" value="${status}" <c:if test="${account.status==status}">checked</c:if>>${status}
                                                        </label>  
                                                </c:forEach>                                                 
                                            </td>
                                        </tr>                                       
                                    </table>
                                </div>
                                <input type="hidden" name="id" value="${account.id}" />
                                <div class="form-group">
                                    <div class="col-sm-12 col-xs-12" style="text-align: center">
                                        <button type="submit" class="btn btn-primary">Update</button>                                      
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