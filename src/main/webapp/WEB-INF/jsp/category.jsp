<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>${category.name}</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="description" content="Little Closet template">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="<c:url value="/styles/bootstrap-4.1.2/bootstrap.min.css"></c:url>">
        <link href="<c:url value="/plugins/font-awesome-4.7.0/css/font-awesome.min.css"></c:url>" rel="stylesheet" type="text/css">
        <link rel="stylesheet" type="text/css" href="<c:url value="/plugins/OwlCarousel2-2.2.1/owl.carousel.css"></c:url>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/plugins/OwlCarousel2-2.2.1/owl.theme.default.css"></c:url>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/plugins/OwlCarousel2-2.2.1/animate.css"></c:url>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/styles/category.css"></c:url>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/styles/category_responsive.css"></c:url>">
        </head>
        <body>

            <!-- Mobile Menu -->
        <jsp:include page="./include/mobile-menu.jsp"/>

        <div class="super_container">

            <!-- Header -->
            <jsp:include page="./include/header.jsp"/>

            <div class="super_container_inner">
                <div class="super_overlay"></div>

                <!-- Products -->

                <div class="products" style="margin-top: 10px">
                    <div class="container">
                        <div class="row" style="margin-top: 130px; margin-bottom: 15px;">
                            <div class="col-xl-12 col-md-12">
                                <div class="dropdown float-right">
                                    <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        Sort by
                                    </button>
                                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                        <a class="dropdown-item" href="<c:url value="/category?id=${param.id}&sort=Latest"/>">Latest</a>
                                        <a class="dropdown-item" href="<c:url value="/category?id=${param.id}&sort=Oldest"/>">Oldest</a>
                                        <a class="dropdown-item" href="<c:url value="/category?id=${param.id}&sort=HightoLow"/>">Price: High to Low</a>
                                        <a class="dropdown-item" href="<c:url value="/category?id=${param.id}&sort=LowtoHigh"/>">Price: Low to High</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row products_row products_container grid">

                            <c:forEach var="product" items="${products}">
                                <!-- Product -->
                                <div class="col-xl-4 col-md-6 grid-item new">
                                    <div class="product">
                                        <div class="product_image"><a href="<c:url value="/product?id=${product.id}"></c:url>"><img src="https://picsum.photos/530/591" alt=""></div>
                                        <!-- div class="product_image"><a href="<c:url value="/product?id=${product.id}"></c:url>"><img src="${product.image}" alt=""></div> -->
                                                    <div class="product_content">
                                                        <div class="product_info d-flex flex-row align-items-start justify-content-start">
                                                            <div>
                                                                <div>
                                                                    <div class="product_name"><a href="<c:url value="/product?id=${product.id}"></c:url>">${product.name}</a></div>
                                                                <div class="product_category">In <a href="<c:url value="/category?id=${product.categories[0].id}&page=1"></c:url>">${product.categories[0].name}</a></div>
                                                                </div>
                                                            </div>
                                                            <div class="ml-auto text-right">
                                                                <div class="rating_r rating_r_4 home_item_rating">${product.totalFavorite} favorite</div>
                                                            <div class="product_price text-right">$${product.price}</div>
                                                        </div>
                                                    </div>
                                                </div>
                                        </div>
                                    </div>
                                </c:forEach>

                            </div>
                            <div class="row page_nav_row">
                                <div class="col">
                                    <div class="page_nav">
                                        <ul class="d-flex flex-row align-items-start justify-content-center">
                                            <c:forEach begin="1" end="${page}" varStatus="status">
                                                <c:choose>
                                                    <c:when test="${param.page==null && status.index==1}">
                                                        <li class="active"><a href="<c:url value="/category?id=${param.id}&page=${status.index}&sort=${param.sort}"/>">${status.index}</a></li>
                                                        </c:when>
                                                        <c:when test="${param.page==null && status.index!=1}">
                                                        <li><a href="<c:url value="/category?id=${param.id}&page=${status.index}&sort=${param.sort}"/>">${status.index}</a></li>
                                                        </c:when>
                                                        <c:when test="${param.page!=null && param.page==status.index}">
                                                        <li class="active"><a href="<c:url value="/category?id=${param.id}&page=${status.index}&sort=${param.sort}"/>">${status.index}</a></li>
                                                        </c:when>
                                                        <c:otherwise>
                                                        <li><a href="<c:url value="/category?id=${param.id}&page=${status.index}&sort=${param.sort}"/>">${status.index}</a></li>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Footer -->
                    <jsp:include page="./include/footer.jsp"/>

                </div>

            </div>

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
            <script src="<c:url value="/plugins/Isotope/isotope.pkgd.min.js"></c:url>"></script>
            <script src="<c:url value="/plugins/Isotope/fitcolumns.js"></c:url>"></script>
            <script src="<c:url value="/js/category.js"></c:url>"></script>
            <script src="<c:url value="/js/product-script-custom.js"></c:url>"></script>
    </body>
</html>