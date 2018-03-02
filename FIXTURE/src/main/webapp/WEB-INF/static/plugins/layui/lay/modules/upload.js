/*!

 @Title: layui.upload 单文件上传 - 全浏览器兼容版
 @Author: 贤心
 @License：LGPL

 */
 
layui.define('layer', function(exports){
  "use strict";
  
  var $ = layui.jquery;
  var layer = layui.layer;
  var device = layui.device();
  
  var elemDragEnter = 'layui-upload-enter';
  var elemIframe = 'layui-upload-iframe';
 
  var msgConf = {
    icon: 2
    ,shift: 6
  }, fileType = {
    file: '文件'
    ,video: '视频'
    ,audio: '音频'
  };
  
  var Upload = function(options){
    this.options = options;
  };
  
  //初始化渲染
  Upload.prototype.init = function(){
    var that = this, options = that.options;
    var body = $('body'), elem = $(options.elem || '.layui-upload-file');
    var iframe = $('<iframe id="'+ elemIframe +'" class="'+ elemIframe +'" name="'+ elemIframe +'"></iframe>');
    
    //插入iframe    
    $('#'+elemIframe)[0] || body.append(iframe);
    
    return elem.each(function(index, item){
      item = $(item);
      var form = '<form target="'+ elemIframe +'" method="'+ (options.method||'post') +'" key="set-mine" enctype="multipart/form-data" action="'+ (options.url||'') +'"></form>';
      
      var type = item.attr('lay-type') || options.type; //获取文件类型

      //包裹ui元素
      if(!options.unwrap){
        form = '<div class="layui-box layui-upload-button">' + form + '<span class="layui-upload-icon"><i class="layui-icon">&#xe608;</i>'+ (
          item.attr('lay-title') || options.title|| ('上传'+ (fileType[type]||'图片') )
        ) +'</span></div>';
      }
      
      form = $(form);
      
      //拖拽支持
      if(!options.unwrap){
        form.on('dragover', function(e){
          e.preventDefault();
          $(this).addClass(elemDragEnter);
        }).on('dragleave', function(){
          $(this).removeClass(elemDragEnter);
        }).on('drop', function(){
          $(this).removeClass(elemDragEnter);
        });
      }
      
      //如果已经实例化，则移除包裹元素
      if(item.parent('form').attr('target') === elemIframe){
        if(options.unwrap){
          item.unwrap();
        } else {
          item.parent().next().remove();
          item.unwrap().unwrap();
        }
      };
      
      //包裹元素
      item.wrap(form);
      
      //触发上传
      item.off('change').on('change', function(){
        that.action(this, type);
      });
    });
  };
  
  //提交上传
  Upload.prototype.action = function(input, type){
    var that = this, options = that.options, val = input.value;
    var item = $(input), ext = item.attr('lay-ext') || options.ext || ''; //获取支持上传的文件扩展名;

    if(!val){
      return;
    };
    
    //校验文件
    switch(type){
      case 'file': //一般文件
        if(ext && !RegExp('\\w\\.('+ ext +')$', 'i').test(escape(val))){
          layer.msg('不支持该文件格式', msgConf);
          return input.value = '';
        }
      break;
      case 'video': //视频文件
        if(!RegExp('\\w\\.('+ (ext||'avi|mp4|wma|rmvb|rm|flash|3gp|flv') +')$', 'i').test(escape(val))){
          layer.msg('不支持该视频格式', msgConf);
          return input.value = '';
        }
      break;
      case 'audio': //音频文件
        if(!RegExp('\\w\\.('+ (ext||'mp3|wav|mid') +')$', 'i').test(escape(val))){
          layer.msg('不支持该音频格式', msgConf);
          return input.value = '';
        }
      break;
      default: //图片文件
        if(!RegExp('\\w\\.('+ (ext||'jpg|png|gif|bmp|jpeg') +')$', 'i').test(escape(val))){
          layer.msg('不支持该图片格式', msgConf);
          return input.value = '';
        }
      break;
    }
    
    options.before && options.before(input);
    var filename1="";
    var pos1 = val.lastIndexOf('/');
    var pos2 = val.lastIndexOf('\\');
    var pos = Math.max(pos1, pos2);
    if (pos < 0) 
    {
    	filename1 = val;
    }
    else 
    {
    	filename1 = val.substring(pos + 1);
    }
    
    var filetype2="";
    var index = filename1.lastIndexOf('.');
    if (index != -1) {
    	filetype2 = filename1.substring(index + 1).toLowerCase();
    }
    var uri = {
            url:val,
            filename: filename1,
            filetype: filetype2,
            data: $(input)[0]
    };
    var fileUrl =options.imgetfile($(input)[0]);      // 将图片转化为二进制文件
    var allowType = {
        'jpg': true,
        'gif': true,
        'png': true,
        'bmp': true
    };
    var uploadUrl="";
    if (fileUrl.filetype.toLowerCase() in allowType) {
        var option2 = {
            apiUrl: options.apiOldUrl,
            file: fileUrl,
            to: options.toId,                       // 接收消息对象options.toId
            roomType: false,
            chatType: 'singleChat',
            onFileUploadError: function () {      // 消息上传失败
                console.log('onFileUploadError');
            },
            onFileUploadComplete: function (message) {   // 消息上传成功
                console.log('图片上传成功'+JSON.stringify(message));
                if(message != null && message !="")
                {
                	if(message.uri != null && message.uri !="")
                	{
                		var getData = message.entities;
                		if(getData != null && getData !="")
                		{
                			uploadUrl = message.uri+"/"+getData[0].uuid;
                			 console.log('图片上传,图片路径：'+uploadUrl);
                		}
                	}
                }
            },
            success: function (message) {                // 消息发送成功
                console.log('图片发送成功'+JSON.stringify(message));
                var res={code:"0",url:uploadUrl};
                options.success(res, input)
                
            },
            flashUpload: options.imfileup
        };
        options.msg.set(option2);
        console.log('图片发送消息体:'+JSON.stringify(options.msg.body));
        options.imConn.send(options.msg.body);
    }
    
    
    
    //item.parent().submit();

    /*var iframe = $('#'+elemIframe), timer = setInterval(function() {
        var res;
        try {
          res = iframe.contents().find('body').text();
        } catch(e) {
          layer.msg('上传接口存在跨域', msgConf);
          clearInterval(timer);
        }
        if(res){
          clearInterval(timer);
          iframe.contents().find('body').html('');
          try {
            res = JSON.parse(res);
          } catch(e){
            res = {};
            return layer.msg('请对上传接口返回JSON字符', msgConf);
          }
          typeof options.success === 'function' && options.success(res, input);
        }
      }, 30); */
      
      input.value = '';
  };
  
  //暴露接口
  exports('upload', function(options){
    var upload = new Upload(options = options || {});
    upload.init();
  });
});

