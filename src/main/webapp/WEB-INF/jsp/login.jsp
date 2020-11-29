<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
    <!-- head -->
    <head>
        <title>Login</title>
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
        <jsp:include page="./include/mobile-menu.jsp"/>

            <div class="super_container">

                <!-- Header -->
            <jsp:include page="./include/header.jsp"/>

                <div class="super_container_inner">
                    <div class="super_overlay"></div>
                    <div class="container">
                        <div class="row" style="margin-top: 100px">
                            <div class="col-lg-6 offset-lg-3">
                                <div class="section_title text-center">Login</div>
                            </div>
                        </div>
                        <div class="row page_nav_row">
                            <div class="col">
                                <div class="page_nav">
                                    <ul class="d-flex flex-row align-items-start justify-content-center">
                                        <li class="active"><a href="<c:url value="/registration"></c:url>">Don't have an account? Register now!</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="row mainmain">
                            <div class="col-xs-12 col-sm-12">
                            <c:if test="${param.error != null}">
                                <p style="color: red;text-align: center"><c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/></p>
                            </c:if>
                            <form action="${contextPath}/login" method="post" class="form-horizontal">
                                <div class="table-responsive">
                                    <table class="table table-bordered">
                                        <tr>
                                            <th>Username <span style="color: red">(*)</span></th>
                                            <td><input type="text" name="username" class="form-control"/></td>
                                        </tr>
                                        <tr>
                                            <th>Password <span style="color: red">(*)</span></th>
                                            <td><input type="password" name="password" class="form-control"/></td>
                                        </tr>
                                    </table>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-12 col-xs-12" style="text-align: center">
                                        <button type="submit" class="btn btn-primary">Login</button>
                                    </div>
                                </div>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"
                            </form>
                        </div>
                    </div>
                </div>
                <!-- profile -->



            </div>
        </div>

        <!-- Footer -->
        <jsp:include page="./include/footer.jsp"/>

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
