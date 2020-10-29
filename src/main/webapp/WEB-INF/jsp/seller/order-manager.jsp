<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f" %>
<!DOCTYPE html>
<html lang="en">
    <!-- head -->
    <head>
        <title>Order manager</title>
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
            <style>
                /*            td{
                                text-align: center;
                            }*/
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
                            <div class="section_title text-center">Order manager</div>
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
                    <div class="row" style="margin-top: 3em">
                        <div class="col-xs-6 col-sm-6">
                            <a href="<c:url value="/downloadOrderExcel"/>"><i class="fa fa-download" aria-hidden="true"></i> Export to Excel</a>
                        </div>
                        <div class="col-xs-6 col-sm-6">
                            <form action="${pageContext.request.getContextPath()}/search" class="form-inline" style="float: right">
                                <div class="form-group">
                                    <input type="hidden" name="action" value="searchOrderSeller" />
                                    <input type="text" name="searchText" class="form-control" />
                                    <button type="submit" class="btn btn-primary" style="margin-left: 5px">Search</button>  
                                </div>                                    
                            </form> 
                        </div>
                    </div>
                    <div class="row mainmain">
                        <div class="table-responsive">
                            <table class="table table-bordered">
                                <tr>
                                    <th>Order ID</th>
                                    <th>Order Date</th>
                                    <th>User</th>
                                    <th>Product</th>
                                    <th>Size</th>
                                    <th>Quantity</th>
                                    <th>Price</th>
                                    <th>Total price</th>
                                    <th>Status</th>
                                    <th>Action</th>
                                </tr>
                                <c:forEach var="o" items="${orders}">
                                    <c:forEach var="od" items="${o.orderDetails}" varStatus="status">
                                        <tr>
                                            <c:if test="${status.index == 0}">
                                                <td class="align-middle" rowspan="${o.orderDetails.size()}">${o.id}</td>
                                                <td class="align-middle" rowspan="${o.orderDetails.size()}">${o.orderDate}</td>
                                                <td class="align-middle" rowspan="${o.orderDetails.size()}">${o.account.username}</td>
                                            </c:if>
                                            <td class="align-middle"><a href="<c:url value="/product?id=${od.product.id}"/>">${od.product.name}</a></td>
                                            <td class="align-middle">${od.size.size}</td>
                                            <td class="align-middle">${od.quantity}</td>
                                            <td class="align-middle">${od.product.price}</td>
                                            <c:if test="${status.index == 0}">
                                                <td class="align-middle" rowspan="${o.orderDetails.size()}">${o.totalPrice}</td>
                                                <td class="align-middle" rowspan="${o.orderDetails.size()}">${o.status}</td>
                                                <td class="align-middle" rowspan="${o.orderDetails.size()}">
                                                    <button onclick="window.location.href = 'seller?action=update-order&id=' + ${o.id};" class="btn"><i class="fa fa-pencil-square-o"></i></button>
                                                </td>
                                            </c:if>
                                        </tr>
                                    </c:forEach>

                                </c:forEach>                               
                            </table>
                        </div>     
                    </div>
                    <div class="row page_nav_row">
                        <div class="col">
                            <div class="page_nav">
                                <ul class="d-flex flex-row align-items-start justify-content-center">
                                    <c:forEach begin="1" end="${page}" varStatus="status">
                                        <c:choose>
                                            <c:when test="${param.page==null && status.index==1}">
                                                <li class="active"><a href="<c:url value="/seller?action=order-manager&page=${status.index}"/>">${status.index}</a></li>
                                                </c:when>
                                                <c:when test="${param.page==null && status.index!=1}">
                                                <li><a href="<c:url value="/seller?action=order-manager&page=${status.index}"/>">${status.index}</a></li>
                                                </c:when>
                                                <c:when test="${param.page!=null && param.page==status.index}">
                                                <li class="active"><a href="<c:url value="/seller?action=order-manager&page=${status.index}"/>">${status.index}</a></li>
                                                </c:when>                                                   
                                                <c:otherwise>
                                                <li><a href="<c:url value="/seller?action=order-manager&page=${status.index}"/>">${status.index}</a></li>
                                                </c:otherwise>
                                            </c:choose>                                          
                                        </c:forEach>                                        
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- profile -->



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