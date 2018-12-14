/*个人信息页面*/
var _pPersonal = {
	"prepare": function(){
		var uinfo = _SG["user"].realInfo;
		$("#personalOpListBox").html(template("op-list-tpl", {"c": uinfo}));
	}
};
_SG["doneReadyer"].addFunc(function(){
	_pPersonal.prepare();
});
_SG["isDoneFuncReady"] = true;
_SG.doDoneAction();	