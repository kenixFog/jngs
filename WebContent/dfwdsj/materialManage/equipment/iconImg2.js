Ext.namespace("dawdsj.materialManage.equipment.iconImg");







dawdsj.materialManage.equipment.iconImg.uploadWin = function(id){
	//命名空间
	var className = dawdsj.materialManage.equipment.iconImg;
	className.win = className.createWin();
	className.win.show();
}


dawdsj.materialManage.equipment.iconImg.createWin = function() {
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
				handler : dawdsj.materialManage.equipment.iconImg.saveHandler
			}, {
				text : whjn.constant.closeBtnText,
				iconCls : 'fa fa-times-circle fa-lg',
				bizCode : 'close',
				handler : dawdsj.materialManage.equipment.iconImg.closeHandler
			} ],
		},
		items : dawdsj.materialManage.equipment.iconImg.initform()
	};
	var win = new Ext.Window(winCfg);
	return win;
}

dawdsj.materialManage.equipment.iconImg.initform=function(){
	
	// 上传图片类型  
    var img_reg = /\.([jJ][pP][gG]){1}$|\.([jJ][pP][eE][gG]){1}$|\.([gG][iI][fF]){1}$|\.([pP][nN][gG]){1}$|\.([bB][mM][pP]){1}$/;
	
	var fileForm = new Ext.form.FormPanel({  
		bodyPadding: 15,
		border : false,
		anchor: '100%',  
		enctype : 'multipart/form-data',
		fileUpload : true,
	    layout: 'column', 
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
	                src : Ext.BLANK_IMAGE_URL,  
	                id : 'imageBrowse'  
	            }   
	        }]  
	    }]
    });
	dawdsj.materialManage.equipment.iconImg.fileForm = fileForm;
	return fileForm;
}

dawdsj.materialManage.equipment.iconImg.saveHandler = function(){
	var file = Ext.getCmp("picFile").getValue();
	if(file==""){
		Ext.MessageBox.alert('提示', "请选择需要上传的图片!");
		return;
	}
	var fileform = dawdsj.materialManage.equipment.iconImg.fileForm;
	fileform.getForm().submit({
		url : whjn.webContextRoot+ '/loginAction.do?method=uploadImg',
		method : "post",
		params : {
			qcId : dawdsj.materialManage.equipment.entry.objId,
			objTb : 'dfwdsj_equipment'
		},
		waitTitle : '提示',
		waitMsg : '正在向服务器提交数据...',
		success : function(form, action) {
//			var url = action.result.url;
//			this.uuId = action.result.uuId;
//			this.suffix = action.result.suffix;
//			this.fireEvent('afterUpload', this, this.uuId);
//			//此处路径被转码，需要转回来
//			var reg = new RegExp("&amp;",'g');
//			url = url.replace(reg,"&");
//	    	if(Ext.isIE){
//				$(this.compId).src = url;
//			}else{
//				Ext.get(this.compId).dom.src = Ext.BLANK_IMAGE_URL;
//				Ext.get(this.compId).dom.src = url;
//			}
			atom.dlg.showMomentDlg('上传成功!');
		}
	});
}