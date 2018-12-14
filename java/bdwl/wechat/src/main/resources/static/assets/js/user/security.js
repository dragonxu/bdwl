/*安全设置页面*/
var _pSecurity = {
	"prepare": function(){
		var uinfo = _SG["user"].realInfo;
		$("#securityOpListBox").html(template("op-list-tpl", {"c": uinfo}));
	}
};
_SG["doneReadyer"].addFunc(function(){
	_pSecurity.prepare();
});
_SG["isDoneFuncReady"] = true;
_SG.doDoneAction();	