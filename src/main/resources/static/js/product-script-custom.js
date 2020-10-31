function getSizeSelected() {
    var radios = document.getElementsByName('product_size_radio');
    for (var i = 0, length = radios.length; i < length; i++)
    {
        if (radios[i].checked)
        {
            return radios[i].value;
        }
    }
    return "";
}
$(document).ready(function ()
{
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
                                favicon.addClass("fa fa-heart fa-3x");
                            } else if (data === "unfavorited") {
                                favicon.removeClass();
                                favicon.addClass("fa fa-heart-o fa-3x");
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