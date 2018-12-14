
function del(orderId) {
    layer.confirm('确认要删除吗？', {
        btn: ['确认','取消'] //按钮
    }, function(){
        confirmDel(orderId)
    }, function(){

    });
}

function confirmDel(orderId) {
    $.ajax(
        {
            type:"post",
            url:ORDER_DELETE,
            data:{
                'orderId':orderId,
            },
            error:function(){
                layer.msg("系统繁忙");
            },
            success:function(data){
                if(data.code == 0){
                    layer.msg(data.msg, {icon: 1});
                    location.reload();
                }else{
                    layer.msg(data.msg);
                }
            }
        }
    );
}