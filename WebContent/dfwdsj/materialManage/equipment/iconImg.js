Ext.namespace("dfwdsj.materialManage.equipment.iconImg");

dfwdsj.materialManage.equipment.iconImg.img_src = null;

dfwdsj.materialManage.equipment.iconImg.uploadWin = function(){
	//命名空间
	var className = dfwdsj.materialManage.equipment.iconImg;
	className.win = className.createWin();
	className.win.show();
}

dfwdsj.materialManage.equipment.iconImg.createWin = function(){
	var win = Ext.create('Ext.window.Window', {
		width : 400,
		height : 200,
		title : '上传图片',
		layout : 'fit',
		autoShow : true,
		modal : true,
		resizable : false,
		maximizable : false,
		constrain : true,
		plain : true,
		enableTabScroll : true,
		border : false,
		items : [ {
				xtype : 'form',
				layout : 'column',
				autoScroll : true,
				border : false,
				defaults : {
					columnWidth : 1,
					labelWidth : 80,
					labelAlign : 'left',
					padding : 5,
					allowBlank : false
				},
				tbar :{
					cls:'whjn-tbar',
					items:[ {
						text : whjn.constant.saveBtnText,
						action : 'btn_save',
						iconCls : 'fa fa-floppy-o fa-lg',
						handler : function(btn,compId) {
							dfwdsj.materialManage.equipment.iconImg.saveUploadImg(btn);
						}
					}, {
						text : whjn.constant.closeBtnText,
						iconCls : 'fa fa-times-circle fa-lg',
						handler : function(btn) {
							btn.up('window').close();
						}
					} ],
				},
				items : [ {
					id : 'uploadAttachment',
					xtype : 'fileuploadfield',
					fieldLabel : '选择文件',
					buttonText : '请选择...',
					name : 'uploadAttachment',
					emptyText : '请选择图片',
					blankText : '图片不能为空',
					listeners : {
						change : function(view, value, eOpts) {
							dfwdsj.materialManage.equipment.iconImg.uploadImgCheck(view, value);
						}
					}
				},{
					columnWidth : 1,
					xtype : 'fieldset',
					title : '上传须知',
					layout : {
						type : 'table',
						columns : 1
					},
					collapsible : false,// 是否可折叠
					defaultType : 'label',// 默认的Form表单组件
					items : [ {
						html : '1、上传图片大小不超过2MB.'
					}, {
						html : '2、支持以下格式的图片:jpg,jpeg,png,gif,bmp.'
					} ]
				} ]
		}]
	});
	return win;
}

//判断上传文件类型是否正确
dfwdsj.materialManage.equipment.iconImg.uploadImgCheck = function(fileObj, fileName){
	var className = dfwdsj.materialManage.equipment.iconImg;
	if (!(className.getImgTypeCheck(className.getImgHZ(fileName)))) {
		whjn.dlg.errTip('上传图片类型有误！');
		fileObj.reset();// 清空上传内容
		return;
	}
	if(className.getImgSize(fileObj)){//判断文件大小
		fileObj.reset();// 清空上传内容
		whjn.dlg.errTip('上传图片大小超过2M！');
		return;
	}
}

//获取后缀
dfwdsj.materialManage.equipment.iconImg.getImgHZ = function(imgName) {
	// 后缀
	var hz = '';
	// 图片名称中最后一个.的位置
	var index = imgName.lastIndexOf('.');
	if (index != -1) {
		// 后缀转成小写
		hz = imgName.substr(index + 1).toLowerCase();
	}
	return hz;
}

//获取后缀
dfwdsj.materialManage.equipment.iconImg.getImgSize = function(obj) {
	var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
	if (isIE && !obj.files) {
        var filePath = obj.value;
        var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
        var file = fileSystem.GetFile (filePath);
        fileSize = file.Size;
    }else {
       var field = document.getElementById('uploadAttachment'); 
   	 //取控件中的input元素  
       var inputs = field.getElementsByTagName('input');  
       var fileInput = null;  
       var il = inputs.length;  
       //取出input 类型为file的元素  
       for(var i = 0; i < il; i ++){  
           if(inputs[i].type == 'file'){  
               fileInput = inputs[i];  
               break;  
           }  
       }  
        fileSize = fileInput.files[0].size;
    }
    fileSize=Math.round(fileSize/1024*100)/100; //单位为KB
    if(fileSize>=2048){
        return true;
    }
	return false;
}


dfwdsj.materialManage.equipment.iconImg.getImgTypeCheck = function(hz) {
	var typestr = 'jpg,jpeg,png,gif,bmp';
	var types = typestr.split(',');// 图片类型
	for (var i = 0; i < types.length; i++) {
		if (hz == types[i]) {
			return true;
		}
	}
	return false;
}





dfwdsj.materialManage.equipment.iconImg.saveUploadImg = function(btn){
	var windowObj = btn.up('window');// 获取Window对象
	var formObj = btn.up('form');// 获取Form对象
	if (formObj.isValid()) { // 验证Form表单
		formObj.form.doAction('submit', {
			url : webContextRoot + '/sys/file/uploadAttachement',
			method : 'POST',
			submitEmptyText : false,
			waitMsg : '正在上传图片,请稍候...',
			timeout : 60000, // 60s
			success : function(response, options) {
				var result = options.result;
				if (!result.success) {
					globalObject.errTip(result.msg);
					return;
				}
				var url = result.data;
				Ext.get('slt').dom.src = appBaseUri + '/static/img/upload/' + url;
				windowObj.close();// 关闭窗体
			},
			failure : function(response, options) {
				whjn.dlg.errTip(options.result.msg);
			}
		});
	}
};
















