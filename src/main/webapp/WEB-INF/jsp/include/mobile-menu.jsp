<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="menu">
    <!-- Search -->
    <div class="menu_search">
        <form action="<c:url value="/search" />" method="post" id="menu_search_form" class="menu_search_form">
            <input type="hidden" name="action" value="searchProduct" />
            <input type="text" name="searchText" class="search_input" placeholder="Search Item" required="required">
            <button class="menu_search_button"><img src="<c:url value="/images/search.png"></c:url>" alt=""></button>
            </form>
        </div>
        <!-- Navigation -->
        <div class="menu_nav">
            <ul>
                <li><a href="<c:url value="/category?id=2&page=1"></c:url>">Action</a></li>
                <li><a href="<c:url value="/category?id=3&page=1"></c:url>">Adventure</a></li>
                <li><a href="<c:url value="/category?id=4&page=1"></c:url>">Animation</a></li>
                <li><a href="<c:url value="/category?id=5&page=1"></c:url>">Children's</a></li>
                <li><a href="<c:url value="/category?id=6&page=1"></c:url>">Comedy</a></li>
                <li><a href="<c:url value="/category?id=7&page=1"></c:url>">Crime</a></li>
                <li><a href="<c:url value="/category?id=8&page=1"></c:url>">Documentary</a></li>
                <li><a href="<c:url value="/category?id=9&page=1"></c:url>">Drama</a></li>
                <li><a href="<c:url value="/category?id=10&page=1"></c:url>">Fantasy</a></li>
                <li><a href="<c:url value="/category?id=11&page=1"></c:url>">Film-Noir</a></li>
                <li><a href="<c:url value="/category?id=12&page=1"></c:url>">Horror</a></li>
                <li><a href="<c:url value="/category?id=13&page=1"></c:url>">Musical</a></li>
                <li><a href="<c:url value="/category?id=14&page=1"></c:url>">Musical</a></li>
                <li><a href="<c:url value="/category?id=15&page=1"></c:url>">Romance</a></li>
                <li><a href="<c:url value="/category?id=16&page=1"></c:url>">Sci-Fi</a></li>
<!--                <li><a href="<c:url value="/category?id=17&page=1"></c:url>">Thriller</a></li>-->
<!--                <li><a href="<c:url value="/category?id=18&page=1"></c:url>">War</a></li>-->
<!--                <li><a href="<c:url value="/category?id=18&page=1"></c:url>">Western</a></li>-->
            </ul>
        </div>
        <!-- Contact Info -->
        <div class="menu_contact">
            <div class="menu_phone d-flex flex-row align-items-center justify-content-start">
                <div>
                    <div><img src="<c:url value="/images/phone.svg"></c:url>" alt=""></div>
                </div>
                <div>+84 774.093.482</div>
            </div>
        <div class="menu_social">
            <ul class="menu_social_list d-flex flex-row align-items-start justify-content-start">
                <li><a href="#"><i class="fa fa-facebook" aria-hidden="true"></i></a></li>
                <li><a href="#"><i class="fa fa-youtube-play" aria-hidden="true"></i></a></li>
                <li><a href="#"><i class="fa fa-google-plus" aria-hidden="true"></i></a></li>
                <li><a href="#"><i class="fa fa-instagram" aria-hidden="true"></i></a></li>
            </ul>
        </div>
    </div>
</div>