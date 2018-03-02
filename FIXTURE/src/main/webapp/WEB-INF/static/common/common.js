
//工具集合Tools
window.T = {};

//获取项目路径
window.basePath = $("#base").attr("href");


//重写alert
/*window.alert = function(msg, callback){
	parent.layer.alert(msg, function(index){
		parent.layer.close(index);
		if(typeof(callback) === "function"){
			callback("ok");
		}
	});
}*/
