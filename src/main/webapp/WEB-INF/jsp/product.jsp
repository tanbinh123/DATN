checked<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html lang="en">
    <!-- head -->
    <head>
        <title>${product.name} - Little closet</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="description" content="Little Closet template">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="<c:url value="/styles/bootstrap-4.1.2/bootstrap.min.css"></c:url>">
        <link href="<c:url value="/plugins/font-awesome-4.7.0/css/font-awesome.min.css"></c:url>" rel="stylesheet" type="text/css">
        <link rel="stylesheet" type="text/css" href="<c:url value="/plugins/flexslider/flexslider.css"></c:url>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/styles/product.css"></c:url>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/styles/product_responsive.css"></c:url>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/styles/comment.css"></c:url>">
        </head>
        <body>

            <!-- Mobile Menu -->
        <jsp:include page="./include/mobile-menu.jsp"/>

            <div class="super_container">

                <!-- Header -->	
            <jsp:include page="./include/header.jsp"/>

                <div class="super_container_inner">
                    <div class="super_overlay"></div>


                    <!-- Product -->

                    <div class="product">
                        <div class="container">
                            <div class="row">

                                <!-- Product Image -->
                                <div class="col-lg-6">     
                                    <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
                                        <ol class="carousel-indicators">

                                                <li data-target="#carouselExampleIndicators" data-slide-to="${status.index}" class="active"></li>

                                    </ol>
                                    <div class="carousel-inner">
                                                <div class="carousel-item active">
                                                    <img class="d-block w-100" src="${product.image}">
                                                </div>
                                    </div>
                                    <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                        <span class="sr-only">Previous</span>
                                    </a>
                                    <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                        <span class="sr-only">Next</span>
                                    </a>
                                </div>

                            </div>

                            <!-- Product Info -->
                            <div class="col-lg-6 product_col">
                                <div class="product_info">
                                    <div class="product_name">${product.name}</div>
                                    <c:forEach var="category" items="${product.categories}">
                                        <div class="product_category">In <a href="<c:url value="/category?id=${category.id}"></c:url>">${category.name}</a></div>
                                    </c:forEach>
                                        <div class="product_rating_container d-flex flex-row align-items-center justify-content-start">                                            
                                            <div class="product_reviews"><i class="fa fa-heart fa-1x" style="color: #ff66a3" aria-hidden="true"></i> ${favorites}</div>                                        
                                    </div>
                                    <div class="product_price">$${product.price}</div>
                                    <div>
                                        <c:if test="${promotions != null && fn:length(promotions) > 0}">
                                            <p style="font-weight: bold;color: red">Promotions: </p>
                                            <c:forEach var="promo" items="${promotions}">
                                                <p>${promo.name} - ${promo.description}</p>
                                            </c:forEach>
                                        </c:if>                                        
                                    </div>
                                    <div class="product_text">
                                        <p>Description: ${product.description}</p>
                                    </div>
                                    <div class="product_buttons">
                                        <div class="text-right d-flex flex-row align-items-start justify-content-start">
                                            <div class="product_button product_fav text-center d-flex flex-column align-items-center justify-content-center" productId="${product.id}">
                                                <div><div>
                                                        <c:if test="${favorited == true}">
                                                            <i id="favicon" class="fa fa-heart fa-3x" style="color: #ff66a3" aria-hidden="true"></i>
                                                        </c:if>
                                                        <c:if test="${favorited == false}">
                                                            <i id="favicon" class="fa fa-heart-o fa-3x" style="color: #ff66a3" aria-hidden="true"></i>
                                                        </c:if>
                                                    </div></div>
                                            </div>
                                            <div class="product_button product_cart text-center d-flex flex-column align-items-center justify-content-center" onclick="location.href='<c:url value="/order/${product.id}/"></c:url>'">
                                                <div><div><img src="<c:url value="/images/cart.svg"></c:url>" class="svg" alt=""><div>+</div></div></div>
                                                </div>                                               
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>                  

                    <div class="comments" id="comments">
                        <div class='container'>
                        <c:if test="${comments != null && fn:length(comments) > 0}">
                            <h2 class="comment-header">${fn:length(comments)} Comments</h2>
                            <c:forEach var="comment" items="${comments}">
                                <div class="media comment-box">
                                    <div class="media-left">
                                        <a href="#">
                                            <img class="img-responsive user-photo" src="https://ssl.gstatic.com/accounts/ui/avatar_2x.png">
                                        </a>
                                    </div>
                                    <div class="media-body">
                                        <h4 class="media-heading">${comment.account.fullName}<span>${comment.commentDate}</span></h4>
                                        <p>${comment.content}</p>
                                    </div>
                                </div>      
                            </c:forEach>

                        </c:if>
                        <c:if test="${fn:length(comments) <= 0}">
                            <h2 class="comment-header">0 Comments</h2>
                        </c:if>  
                        <sec:authorize access="isAuthenticated()">
                            <form action="${pageContext.request.getContextPath()}/comment" method="post" class="form-horizontal">
                                <div class="form-group">
                                    <label for="comment">Comment:</label>
                                    <textarea class="form-control" rows="5" id="content" name="content"></textarea>
                                </div>
                                <div class="form-group">
                                    <div style="text-align: center">
                                        <button type="submit" class="btn btn-primary">Add Comment</button>                                      
                                    </div>
                                </div>
                                <input type="hidden" name="productId" value="${product.id}"/>
                            </form>
                        </sec:authorize>

                    </div>
                </div>
                <!-- Footer -->
                <jsp:include page="./include/footer.jsp"/>
                </div>

            </div>

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
        <script src="<c:url value="/plugins/flexslider/jquery.flexslider-min.js"></c:url>"></script>
        <script src="<c:url value="/js/product.js"></c:url>"></script>
        <script src="<c:url value="/js/product-script-custom.js"></c:url>"></script>
    </body>
</html>
