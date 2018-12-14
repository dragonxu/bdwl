var _SG = {
	"loadSiteSetting": function(){
		// 载入站点设置
		var fsize = $(window).width() * 100 / 750; 
		var autoFixStyle = '<style type="text/css">html{font-size:'+fsize+'px;}</style>';
		document.write(autoFixStyle);
	}
};