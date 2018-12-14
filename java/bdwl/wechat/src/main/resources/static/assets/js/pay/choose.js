$(function () {
    $(".one-item").click(function () {
        $(".one-item").each(function () {
            $(this).removeClass("active");
        })
        $(this).addClass("active");
        $(".choose-btn").attr("value",$(this).attr("value"));
    });
})

function choose_btn(div,url) {
    var userCouponId = $(div).attr("value");
    if(userCouponId == "" || userCouponId == undefined){
        layer.msg("请选择优惠券");
    }else{
        location.href = url + "?userCouponId="+userCouponId;
    }

}