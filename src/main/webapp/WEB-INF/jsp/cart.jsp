<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Cart</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="description" content="Little Closet template">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="<c:url value="/styles/bootstrap-4.1.2/bootstrap.min.css"></c:url>">
        <link href="<c:url value="/plugins/font-awesome-4.7.0/css/font-awesome.min.css"></c:url>" rel="stylesheet" type="text/css">
        <link rel="stylesheet" type="text/css" href="<c:url value="/styles/cart.css"></c:url>">
        <link rel="stylesheet" type="text/css" href="<c:url value="/styles/cart_responsive.css"></c:url>">

        </head>
        <body>

            <!-- Mobile Menu -->
        <jsp:include page="./include/mobile-menu.jsp"/>

            <div class="super_container">

                <!-- Header -->
            <jsp:include page="./include/header.jsp"/>

                <div class="super_container_inner">
                    <div class="super_overlay"></div>

                    <!-- Home -->

                    <div class="home">
                        <div class="home_container d-flex flex-column align-items-center justify-content-end">
                            <div class="home_content text-center">
                                <div class="home_title">Shopping Cart</div>
                                <div class="breadcrumbs d-flex flex-column align-items-center justify-content-center">
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Cart -->

                    <div class="cart_section">
                        <div class="container">
                            <div class="row">
                                <div class="col">
                                    <div class="cart_container">
                                        <p style="font-size: 150%;color: red;text-align: center">${messageError}</p>
                                    <p style="font-size: 150%;color: blue;text-align: center">${messageSuccess}</p>
                                    <!-- Cart Bar -->
                                    <div class="cart_bar">
                                        <ul class="cart_bar_list item_list d-flex flex-row align-items-center justify-content-end">
                                            <li class="mr-auto">Product</li>
                                            <li>Price</li>
                                            <li>Quantity</li>
                                            <li>Total</li>
                                            <li style="width: 100px">Action</li>
                                        </ul>
                                    </div>

                                    <!-- Cart Items -->
                                    <div class="cart_items">
                                        <ul class="cart_items_list">
                                            <c:forEach var="orderDetail" items="${order.orderDetails}">
                                                <!-- Cart Item -->
                                                <li class="cart_item item_list d-flex flex-lg-row flex-column align-items-lg-center align-items-start justify-content-lg-end justify-content-start">
                                                    <div class="product d-flex flex-lg-row flex-column align-items-lg-center align-items-start justify-content-start mr-auto">

                                                        <div><div class="product_image"><img src="${orderDetail.product.image}" alt=""></div></div>
                                                        <div class="product_name_container">
                                                            <div class="product_name"><a href="<c:url value="/product?id=${orderDetail.product.id}"></c:url>">${orderDetail.product.name}</a></div>
                                                            <!--<div class="product_text">${orderDetail.product.description}</div>-->
                                                        </div>
                                                    </div>
                                                    <div class="product_price product_text"><span>Price: </span>$${orderDetail.product.price}</div>
                                                    <div class="product_quantity_container">
                                                        <div class="product_quantity ml-lg-auto mr-lg-auto text-center">
                                                            <span class="product_text product_num">${orderDetail.quantity}</span>
                                                            <div class="qty_sub qty_button trans_200 text-center"><span>-</span></div>
                                                            <div class="qty_add qty_button trans_200 text-center"><span>+</span></div>
                                                        </div>
                                                    </div>
                                                    <div class="product_total product_text"><span>Total: </span>$${orderDetail.total}</div>
                                                    <div style="width: 100px">
                                                        <input type="hidden" name="cart_product_id" value="${orderDetail.product.id}" />
                                                        <button class="btn"><i class="fa fa-refresh"></i></button>
                                                        <button class="btn"><i class="fa fa-trash"></i></button>
                                                    </div>
                                                </li>
                                            </c:forEach>
                                            <c:if test="${order == null || fn:length(order.orderDetails) == 0}">
                                                <li style="text-align: center"><h3>Your cart is empty</h3></li>
                                                </c:if>
                                        </ul>
                                    </div>

                                    <!-- Cart Buttons -->
                                    <div class="cart_buttons d-flex flex-row align-items-start justify-content-start">
                                        <div class="cart_buttons_inner ml-sm-auto d-flex flex-row align-items-start justify-content-start flex-wrap">
                                            <c:if test="${order != null || fn:length(order.orderDetails) > 0}">
                                                <div class="button button_clear trans_200"><a href="<c:url value="/order/clear"></c:url>">clear cart</a></div>
                                            </c:if>
                                            <div class="button button_continue trans_200"><a href="<c:url value="/home"></c:url>">continue shopping</a></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        <c:if test="${order != null && fn:length(order.orderDetails) > 0}">
                            <div class="row cart_extra_row">

                                <div class="col-lg-6 cart_extra_col">
                                    <div class="cart_extra cart_extra_2">
                                        <div class="cart_extra_content cart_extra_total">
                                            <div class="cart_extra_title">Cart Total</div>
                                            <ul class="cart_extra_total_list">
                                                <li class="d-flex flex-row align-items-center justify-content-start">
                                                    <div class="cart_extra_total_title">Subtotal</div>
                                                    <div class="cart_extra_total_value ml-auto">$${order.total}</div>
                                                </li>
                                                <li class="d-flex flex-row align-items-center justify-content-start">
                                                    <div class="cart_extra_total_title">Shipping</div>
                                                    <div class="cart_extra_total_value ml-auto">Free</div>
                                                </li>
                                                <li class="d-flex flex-row align-items-center justify-content-start">
                                                    <div class="cart_extra_total_title">Total</div>
                                                    <div class="cart_extra_total_value ml-auto">$${order.total}</div>
                                                </li>
                                            </ul>
                                            <div class="checkout_button trans_200"><a href="<c:url value="/checkout"></c:url>">proceed to checkout</a></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                        </c:if>
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
        <script src="<c:url value="/plugins/easing/easing.js"></c:url>"></script>
        <script src="<c:url value="/plugins/parallax-js-master/parallax.min.js"></c:url>"></script>
        <script src="<c:url value="/js/cart.js"></c:url>"></script>
        <script src="<c:url value="/js/cart-script-custom.js"></c:url>"></script>
    </body>
</html>