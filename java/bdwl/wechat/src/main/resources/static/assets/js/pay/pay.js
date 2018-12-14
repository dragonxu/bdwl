$(function () {
    $(".paytype .one-item").click(function () {
        $(".paytype .one-item").each(function () {
            $(this).removeClass("active");
        })
        $(this).addClass("active");
        $("#payType").val($(this).attr("value"));
    })
})


function pay(successUrl) {
    var payType = $("#payType").val();
    if(payType == "PAY_WITH_BALANCE"){
        payWithBalance(successUrl);
    }else{
        payWithWechat(successUrl);
    }
}
//余额支付
function payWithBalance(successUrl){
    var orderId = $("#orderId").val();
    $.ajax(
        {
            type:"post",
            url:PAY_WITH_BALANCE,
            data:{
                'orderId':orderId,
            },
            error:function(){
                layer.msg("系统繁忙");
            },
            success:function(data){
                if(data.code == 0){
                    location.href = successUrl;
                }else{
                    layer.msg(data.msg);
                }
            }
        }
    );
}

//微信支付
function payWithWechat(successUrl){
    var orderId = $("#orderId").val();
    $.ajax(
        {
            type:"post",
            url:PAY_WITH_WECHAT,
            data:{
                'orderId':orderId,
            },
            error:function(){
                layer.msg("系统繁忙");
            },
            success:function(data){
                if(data.code == 0){
                    orderPay(data.data.signPackage,data.data.payMessage,successUrl)
                }else{
                    layer.msg(data.msg);
                }
            }
        }
    );
}

function orderPay(signPackage,payMessage,paySuccessUrl){
    /*
     * 注意：
     * 1. 所有的JS接口只能在公众号绑定的域名下调用，公众号开发者需要先登录微信公众平台进入“公众号设置”的“功能设置”里填写“JS接口安全域名”。
     * 2. 如果发现在 Android 不能分享自定义内容，请到官网下载最新的包覆盖安装，Android 自定义分享接口需升级至 6.0.2.58 版本及以上。
     * 3. 常见问题及完整 JS-SDK 文档地址：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html
     *
     * 开发中遇到问题详见文档“附录5-常见错误及解决办法”解决，如仍未能解决可通过以下渠道反馈：
     * 邮箱地址：weixin-open@qq.com
     * 邮件主题：【微信JS-SDK反馈】具体问题
     * 邮件内容说明：用简明的语言描述问题所在，并交代清楚遇到该问题的场景，可附上截屏图片，微信团队会尽快处理你的反馈。
     */
    wx.config({
        debug: false,
        appId: signPackage.appId,
        timestamp: signPackage.timestamp,
        nonceStr: signPackage.nonceStr,
        signature: signPackage.signature,
        jsApiList: [
            // 所有要调用的 API 都要加到这个列表中
            'chooseWXPay',
        ]
    });
    wx.ready(function () {
        wx.chooseWXPay({
            timestamp: payMessage.time, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
            nonceStr: payMessage.nonce_str, // 支付签名随机串，不长于 32 位
            package: payMessage.prepay_id, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
            signType: payMessage.signType, // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
            paySign: payMessage.sign, // 支付签名
            success: function (res) {
                // 支付成功后的回调函数
                location.href = paySuccessUrl;
            }
        });

    });
}