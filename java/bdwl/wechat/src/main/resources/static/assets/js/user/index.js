/*用户首页*/
var _pUserIndex = {
	"prepare": function(){
		var uinfo = _SG["user"].realInfo;
		var ubObj = $("#userBaseDetailBox");
		ubObj.find(".user-icon").attr("src", getDisplayPicUrl(uinfo["user_icon"]));
		ubObj.find(".name").html(_SG.getSafeData(uinfo["user_name"]));
		ubObj.find(".phone").html(_SG.getSafeData(uinfo["phone"]));
	}
};
_SG["doneReadyer"].addFunc(function(){
	_pUserIndex.prepare();
});
_SG["isDoneFuncReady"] = true;
_SG.doDoneAction();