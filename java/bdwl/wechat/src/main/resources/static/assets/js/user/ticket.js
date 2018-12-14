/*票据列表页面*/
var _pUserTicket = {
	"pkey": "",
	"orderId": -1,
	"orderInfo": null,
	"orderTypeMap": {"play": 1, "waitting-pay": 2, "history": 3},
	"chosenIndexs": [],
	"showDetail": function(o){
		var _orderId = $(o).attr("data-orderid");
		window.location.href = _SG["siteUrl"]+"/user/ticketDetail.html?orderId="+_orderId;
	},
	"goReturnPage": function(o){
		var _orderId = $(o).attr("data-orderid");
		window.location.href = _SG["siteUrl"]+"/user/returnTicket.html?orderId="+_orderId;
	},
	"getList": function(page){
		jqScroller.block();
		var _data = {"order_type": this.orderTypeMap[this.pkey]};
		processCrossAjax({
			type: "post",
			url: _SG["apiPreUrl"]+"/user/UserOrderList",
			data: _data,
			dataType:'json',
			success:function(rs){
				var flag = false;
				if(_SG.isReplyOk(rs)){
					flag = true;
					var rsData = rs.data;
					var list = rsData.data || null;
					if(list != null && list.length > 0){
						var _tplData = {"pkey": _pUserTicket.pkey, "list": list};
						$("#ajaxTicketListBox").html(template("ticket-list-tpl", getDataWithExtraProps(_tplData)));
					}else{
						$("#ajaxTicketListBox").html(_SG["dataTip"].getNoDataHtml());
					}
					jqScroller.done();
				}
				if(!flag){
					jqScroller.unblock();
					_SG["dialog"].msg(rs.msg);
				}
			},
			error: function(){
				jqScroller.unblock();
				_SG["dialog"].msg(_SG["LangTxts"]["NETWORK_ERROR"]);
			}
		}, true);
	},
	"renderListPage": function(){
		var _params = getQueryParams();
		this.pkey = _params["key"] || "";
		$("#curPageHeadNameTxt").html(_SG["LangTxts"][this.pkey+"-ticket-bill"]);
		jqScroller.display( function(page){ _pUserTicket.getList(page); } );
	},
	"showChooseSeatPopup": function(){
		var items = $("#chooseSeatPopupBox .list-box .one-item");
		items.removeClass("active");
		for(var i = 0; i < this.chosenIndexs.length; i++){
			items.eq(this.chosenIndexs[i]).addClass("active");
		}
		_SG['dialog'].showPopup(_SG['dom'].get('chooseSeatPopupBox'));
	},
	"confirmChooseSeat": function(){
		this.chosenIndexs = [];
		$("#chooseSeatPopupBox .list-box .one-item").each(function(){
			var index = $(this).attr("data-index");
			if($(this).hasClass("active")){
				_pUserTicket.chosenIndexs.push(parseInt(index));
			}
		});
		if(this.chosenIndexs.length == 0){
			$("#seatSelectBox").html("请选择");
		}else{
			$("#seatSelectBox").html("已选择"+this.chosenIndexs.length+"个");
		}		
		_SG['dialog'].hidePopup();
	},
	"getChosenSeatIds": function(){
		var arr = [];
		var seats = this.orderInfo["order_goods"];
		var c;
		for(var i = 0; i < this.chosenIndexs.length; i++){
			c = seats[this.chosenIndexs[i]];
			arr.push(c["id"]);
		}
		return arr;
	},
	"doReturnTicket": function(){
		if(_SG["lock"].isBlocked("return-ticket")){
			return;
		}
		var _seatIds = this.getChosenSeatIds();
		if(_seatIds.length == 0){
			_SG["dialog"].msg("请至少选择一个座位！");
			return;
		}
		_SG["dialog"].load(_SG["LangTxts"]["IN_PROCESSING"]);
		_SG["lock"].block("return-ticket");
		var _data = {
			"order_id": this.orderId,
			"order_goods_ids": _seatIds.join(","),
		};
		// 退票
		processCrossAjax({
			type: "post",
			url: _SG["apiPreUrl"]+"/user/OrderRefund",
			dataType: 'json',
			data: _data,
			success:function(rs){
				_SG["dialog"].removeLoad();
				_SG["lock"].unblock("return-ticket");
				var flag = false;
				if(_SG.isReplyOk(rs)){
					flag = true;
				}
				if(!flag){
					_SG["dialog"].alert(rs.msg);
				}
			},
			error: function(){
				_SG["dialog"].removeLoad();
				_SG["lock"].unblock("return-ticket");
				_SG["dialog"].alert("退票时发生网络异常, 请稍后重试！");
			}
		}, true);
	},
	"loadDetailDoneAction": function(){
		if(_SG["curPageKey"] == "return-ticket"){
			$("#ticketMainContainer").html(template("ticket-info-tpl", getDataWithExtraProps({"c": this.orderInfo})));
			$("#chooseSeatPopupBox .list-box").html(template("seat-list-tpl", getDataWithExtraProps({"list": this.orderInfo["order_goods"]})));
			$("#chooseSeatPopupBox .list-box .one-item").on("click", function(){				
				if($(this).hasClass("active")){
					$(this).removeClass("active");
				}else{
					$(this).addClass("active");
				}
			});
			$("#chooseSeatPopupBox .confirm-btn").on("click", function(){
				_pUserTicket.confirmChooseSeat();
			});
			$("#returnSubmitBtn").on("click", function(){
				_pUserTicket.doReturnTicket();
			});
		}else{
			$("#ticketDetailBox").html(template("ticket-detail-tpl", getDataWithExtraProps({"c": this.orderInfo})));
		}
	},
	"getOrderDetail": function(){
		var _data = {"order_id": this.orderId};
		processCrossAjax({
			type: "post",
			url: _SG["apiPreUrl"]+"/user/OrderInfo",
			data: _data,
			dataType:'json',
			success:function(rs){
				var flag = false;
				if(_SG.isReplyOk(rs)){
					flag = true;
					var c = rs.data;
					_pUserTicket.orderInfo = c;
					_pUserTicket.loadDetailDoneAction();
				}
				if(!flag){
					_SG["dialog"].msg(rs.msg);
				}
			},
			error: function(){
				_SG["dialog"].msg(_SG["LangTxts"]["NETWORK_ERROR"]);
			}
		}, true);
	},
	"renderDetailPage": function(){
		var _params = getQueryParams();
		this.orderId = _params["orderId"] ? parseInt(_params["orderId"]) : 0;
		if(this.orderId == 0){
			_SG["dialog"].msg("缺少查询参数");
			return;
		}
		this.getOrderDetail();
	},
	"renderReturnTicketPage": function(){
		var _params = getQueryParams();
		this.orderId = _params["orderId"] ? parseInt(_params["orderId"]) : 0;
		if(this.orderId == 0){
			_SG["dialog"].msg("缺少查询参数");
			return;
		}
		this.getOrderDetail();
	},
	"prepare": function(){
		if(_SG["curPageKey"] == "list"){
			this.renderListPage();
		}else if(_SG["curPageKey"] == "detail"){
			this.renderDetailPage();
		}else if(_SG["curPageKey"] == "return-ticket"){
			this.renderReturnTicketPage();
		}
	}
}
_SG["doneReadyer"].addFunc(function(){
	_pUserTicket.prepare();
});
_SG["isDoneFuncReady"] = true;
_SG.doDoneAction();