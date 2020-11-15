<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<sec:authorize access="isAuthenticated()">
    <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_SELLER')">
        <li><a href="<c:url value="/account?action=profile"></c:url>">Account</a></li>
        <li><a href="<c:url value="/account?action=changepassword"></c:url>">Change Password</a></li>
    </sec:authorize>
    <sec:authorize access="hasRole('ROLE_USER')">
        <li><a href="<c:url value="/account?action=myorder"></c:url>">My Order</a></li> 
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_SELLER')">
        <li><a href="<c:url value="/seller?action=product-manager"></c:url>">Product</a></li>    
        <li><a href="<c:url value="/seller?action=order-manager"></c:url>">Order</a></li>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
        <li><a href="<c:url value="/admin?action=account-manager"></c:url>">Account manager</a></li>       
        </sec:authorize>
    <li><a href="<c:url value="/logout"></c:url>">Logout</a></li>
</sec:authorize>
