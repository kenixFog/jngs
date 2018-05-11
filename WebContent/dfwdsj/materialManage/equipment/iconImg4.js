Ext.namespace("dfwdsj.materialManage.equipment.iconImg");

dfwdsj.materialManage.equipment.iconImg.img_src = null;

dfwdsj.materialManage.equipment.iconImg.uploadWin = function(config){
	
	if(config.img_src){
		dfwdsj.materialManage.equipment.iconImg.img_src = config.img_src;
	}else{
		dfwdsj.materialManage.equipment.iconImg.img_src = dfwdsj.materialManage.equipment.entry.img;
	}
	//命名空间
	var className = dfwdsj.materialManage.equipment.iconImg;
	className.win = className.createWin();
	className.win.show();
}


dfwdsj.materialManage.equipment.iconImg.createWin = function() {
	var winCfg = {
		layout : 'fit',   	  // 布局样式
		width : 285,	
		height : 350,
		title : '图片上传',
		resizable : false,	 // 不允许用户允许拖动窗体边角来控制窗口大小
		autoScroll : true,   // 自动显示滚动条
		closeAction : 'destroy',// 关闭时为销毁操作，hide为隐藏操作
		modal : true,		 // 模态化显示：后方的区域不能点击和编辑
		tbar :{
			cls:'whjn-tbar',
			items:[ {
				text : whjn.constant.saveBtnText,
				iconCls : 'fa fa-floppy-o fa-lg',
				bizCode : 'save',
				handler : function(btn) {
					dfwdsj.materialManage.equipment.iconImg.saveHandler(btn);
				}
			}, {
				text : whjn.constant.closeBtnText,
				iconCls : 'fa fa-times-circle fa-lg',
				bizCode : 'close',
				handler : dfwdsj.materialManage.equipment.iconImg.closeHandler
			} ],
		},
		items : dfwdsj.materialManage.equipment.iconImg.initform()
	};
	var win = new Ext.Window(winCfg);
	return win;
}

dfwdsj.materialManage.equipment.iconImg.initform=function(){
	
	var fileForm = new Ext.form.FormPanel({  
		bodyPadding: 15,
		border : false,
		anchor: '100%',  
		enctype : 'multipart/form-data',
		fileUpload : true,
	    layout: 'column', 
	    xtype : 'form',
        id : "fileUploadForm", 
        items : [{  
        	xtype: 'container',  
	        columnWidth: 1,  
	        layout: 'anchor',  
	        items: [{
	        	id : 'picFile',  
	            name : 'picFile', 
	            inputType : "file",  
	            labelWidth : 60,
	            fieldLabel : '选择图片',  
	            xtype : 'textfield',
	            listeners : {    
	            	'render': function (e) {
	                    var path = Ext.getCmp('picFile').getValue()
	                    var url = 'file:///' + path;
	                    Ext.getCmp('picFile').on('change', function (field, newValue, oldValue) {//上传图片的控件对象,刚刚选择的图片仿真路径，上次选择的图片仿真路径
	                        alert(this.value);
	                    	var show = Ext.getCmp('browseImage');
	                        if (Ext.isIE) {
	                        	Ext.getCmp('browseImage').getEl().dom.src = url;
	                        } else {//获取选择文件的路径信息？ 将路径绑定到显示图片的box内加载
	                            var obj = Ext.getCmp('picFile').inputEl.dom.files;
	                            var imgurl = window.URL.createObjectURL(obj[0]);
	                            Ext.getCmp('browseImage').getEl().dom.src = imgurl;
	                        }
	                    }, this);//这是选择文件 
	            	}    
                }
	        }]  
	    }, {  
	    	xtype: 'container',  
	        columnWidth: 1,  
	        style: 'margin:20px 0 0 30px',
	        layout: 'anchor',  
	        items: [{
	        	xtype : 'box',  
	            id : 'browseImage', 
	            border : true,
	            autoEl : {
	                width : 180,  
	                height : 180,  
	                tag : 'img',  
	                src : dfwdsj.materialManage.equipment.iconImg.img_src,  
	                id : 'imageBrowse'  
	            }   
	        }]  
	    }]
    });
	dfwdsj.materialManage.equipment.iconImg.fileForm = fileForm;
	return fileForm;
}

dfwdsj.materialManage.equipment.iconImg.saveHandler = function(btn){
	var file = Ext.getCmp("picFile").getValue();
	if(file==""){
		Ext.MessageBox.alert('提示', "请选择需要上传的图片!");
		return;
	}
	var windowObj = btn.up('window');// 获取Window对象
	var formObj = btn.up('form');// 获取Form对象
	var fileForm = dfwdsj.materialManage.equipment.iconImg.fileForm
//	fileForm.form.doAction('submit',{
//		url : webContextRoot + '/sys/file/upLoad',
//		method : 'POST',
//		submitEmptyText : false,
//		waitMsg : '正在上传,请稍候...',
//		timeout : 60000, // 60s
//		params: {
////			file : file,
//        	objId : dfwdsj.materialManage.equipment.entry.objId, //数据ID
// 			objTb : 'dfwdsj_equipment',//业务表
// 			instOrUd : 'update'//附件更新还是直接插入，insert插入，update更新
//        },
//		success : function(response, options) {
//			Ext.Msg.alert("系统提示", "文件上传成功！");
//		},
//		failure : function(response, options) {
//			 Ext.Msg.alert("系统提示", "文件上传失败！");
//		}
//	})
	
//	var fileForm = dfwdsj.materialManage.equipment.iconImg.fileForm;
//	fileForm.getForm().submit({
//		method:'post',
//		url: webContextRoot + '/sys/file/upLoad',
//        waitMsg:'上传中...',
//        params: {
//        	filePath : file,
//        	objId : dfwdsj.materialManage.equipment.entry.objId, //数据ID
// 			objTb : 'dfwdsj_equipment',//业务表
// 			instOrUd : 'update'//附件更新还是直接插入，insert插入，update更新
//        },
//        success: function(form, action) {
//            Ext.Msg.alert("系统提示", "文件上传成功！");
//        },
//        failure: function(form, action) {
//            Ext.Msg.alert("系统提示", "文件上传失败！");
//        }
//	});
	
	Ext.Ajax.request({
		//请求地址
		url: webContextRoot + '/sys/file/upLoad',
        method:'post',
        //提交参数组
        params: {
        	picFile : file,
        	objId : dfwdsj.materialManage.equipment.entry.objId, //数据ID
 			objTb : 'dfwdsj_equipment',//业务表
 			instOrUd : 'update'//附件更新还是直接插入，insert插入，update更新
        },
        //成功时回调
        success: function(response, options) {
        	whjn.dlg.showMomentDlg('上传成功!');
	    }
	});
}