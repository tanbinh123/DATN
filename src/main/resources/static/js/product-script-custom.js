$(document).ready(function ()
{
    initRating();
    function initRating(){
        $.get("api/get-rating/" + $('input[name=productId]').val(), function (data) {
            $("#review").rating({
                "value": data,
                "click": function (e) {
                    $.get("api/rating/" + $('input[name=productId]').val() +"/" + e.stars)
                    $("#starsInput").val(e.stars);
                }
            })
        });
    }

    "use strict";
    initFavorite();
    function initFavorite()
    {
        if ($('.product_fav').length)
        {
            var qtys = $('.product_fav');
            qtys.each(function () {
                var qty = $(this);
                var productId = qty.attr("productId");
                qty.on('click', function ()
                {                    
                    if (productId.length) {
                        $.get("api/favorite/" + productId, function (data) {
                            var favicon = $("#favicon");                            
                            if (data === "favorited") {
                                favicon.removeClass();
                                favicon.addClass("fas fa-heart fa-3x");
                            } else if (data === "unfavorited") {
                                favicon.removeClass();
                                favicon.addClass("far fa-heart fa-3x");
                            } else {
                                alert("Please login first");
                            }
                        });
                    } else {
                        alert("Error find product id");
                    }
                });
            });
        }
    }
});