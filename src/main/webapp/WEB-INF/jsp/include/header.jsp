<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<header class="header">
    <div class="header_overlay"></div>
    <div class="header_content d-flex flex-row align-items-center justify-content-start">
        <div class="logo">
            <a href="<c:url value="/"></c:url>">
                    <div class="d-flex flex-row align-items-center justify-content-start">
                        <div><img src="<c:url value="/images/logo_1.png"></c:url>" alt=""></div>
                        <div>Movies Unlimited</div>
                    </div>
                </a>	
            </div>
            <div class="hamburger"><i class="fa fa-bars" aria-hidden="true"></i></div>
            <nav class="main_nav">
                <ul class="d-flex flex-row align-items-start justify-content-start">
                   <li><a href="<c:url value="/category?id=2&page=1"></c:url>">Action</a></li>
                   <li><a href="<c:url value="/category?id=3&page=1"></c:url>">Adventure</a></li>
                   <li><a href="<c:url value="/category?id=4&page=1"></c:url>">Animation</a></li>
                   <li><a href="<c:url value="/category?id=5&page=1"></c:url>">Comedy</a></li>
                </ul>
            </nav>
            <div class="header_right d-flex flex-row align-items-center justify-content-start ml-auto">
                <!-- Search -->
                <div class="header_search">
                    <form action="<c:url value="/search"/>" id="header_search_form">
                    <input type="hidden" name="action" value="searchProduct" />
                    <input type="text" name="searchText" class="search_input" placeholder="Search Item" required="required">
                    <button class="header_search_button"><img src="<c:url value="/images/search.png"></c:url>" alt="">
                    </button>
                    </form>
                </div>           
                <!-- User -->
                <div><sec:authorize access="isAuthenticated()"><h4 style="color: #002752">Hi <sec:authentication property="principal.username" />!</h4></sec:authorize></div>
                <div class="user"><a href="<c:url value="/account"></c:url>">
                    <div><img src="<c:url value="/images/user.svg"></c:url>" alt=""></div>
                    </a></div>
                <!-- Cart -->
                <div class="cart"><a href="<c:url value="/cart"></c:url>">
                    <div><img class="svg" src="<c:url value="/images/cart.svg"></c:url>" alt="">
                        <c:if test="${order!=null}">
                            <div>${fn:length(order.orderDetails)}</div>
                        </c:if>
                    </div>
                    </a></div>
                <!-- Phone -->
                <div class="header_phone d-flex flex-row align-items-center justify-content-start">
                    <div>
                        <div><img src="<c:url value="/images/phone.svg"></c:url>" alt=""></div>
                    </div>
                    <div>+84 774.093.482</div>
                </div>
        </div>
    </div>
</header>