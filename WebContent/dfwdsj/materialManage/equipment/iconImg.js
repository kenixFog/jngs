Ext.namespace("dawdsj.materialManage.equipment.iconImg");

dawdsj.materialManage.equipment.iconImg.img_src = null;

dawdsj.materialManage.equipment.iconImg.uploadWin = function(config){
	if(config.img_src){
		dawdsj.materialManage.equipment.iconImg.img_src = config.img_src;
	}else{
		dawdsj.materialManage.equipment.iconImg.img_src = dawdsj.materialManage.equipment.entry.img;
	}
	Ext.apply(this, config);
	this.initUIComponents();
	dawdsj.materialManage.equipment.iconImg.uploadWin.superclass.constructor.call(this);
}

Ext.extend(dawdsj.materialManage.equipment.iconImg.uploadWin,Ext.Window,{
	/**
	 * 调用头像上传控件的组件ID
	 * @type 
	 */
	compId : undefined,
	/**
	 * 后台处理的URL
	 * @type 
	 */
	 url : undefined,
	 /**
	 * 传入后台的参数
	 * @type 
	 */
	 params : undefined,
	/**
	 * 上传成功后得到的ID
	 */
	uuId : null,
	
	initUIComponents : function(){
		
		this.addEvents(
            /**
             * @event afterUpload
             * 当头像上传成功之后触发
             * @param {atom.attachment.AttachmentPanel} this
             * @param {String} 上传文件名称
             */
            'afterUpload'
        );
		/**
		 * 头像上传表单
		 */
		this.formPanel = new Ext.form.FormPanel({  
//			bodyPadding: 15,
			bodyStyle : 'padding:5px 5px 0;overflow-x:hidden;',
			border : false,
			region : 'center',
			enctype : 'multipart/form-data',
			fileUpload : true,
		    layout: 'column', 
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
		                src : dawdsj.materialManage.equipment.iconImg.img_src,
						style : 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);',
		                id : 'imageBrowse'  
		            }   
		        }]  
		    }]
	    });
		
		/**
		 * 修改头像画面窗口
		 */
		this.showUploadWin = new Ext.Window({
			layout : 'border',
			region : 'center',
			maximizable : false,
			title : '头像维护',
			width : 285,	
			height : 350,
			autoScroll : true,
			closable : true,
			closeAction : 'destory',
			modal : true,
			tbar : {
				cls:'whjn-tbar',
				items:[ {
					text : whjn.constant.saveBtnText,
					iconCls : 'fa fa-floppy-o fa-lg',
					bizCode : 'save',
					handler : this.saveHandler()
				}, {
					text : whjn.constant.closeBtnText,
					iconCls : 'fa fa-times-circle fa-lg',
					bizCode : 'close',
					handler : function(){
						this.showUploadWin.close();
					}
				} ],
			},
			items : this.formPanel
		});
		this.showUploadWin.show();
	},
	
	saveHandler : function(){
		var file = Ext.getCmp("picFile").getValue();
		if(file==""){
			Ext.MessageBox.alert('提示', "请选择需要上传的图片!");
			return;
		}
		this.formPanel.getForm().submit({
			url : whjn.webContextRoot+ '/loginAction.do?method=uploadImg',
			method : "post",
			params : {
				qcId : dawdsj.materialManage.equipment.entry.objId,
				objTb : 'dfwdsj_equipment'
			},
			waitTitle : '提示',
			waitMsg : '正在向服务器提交数据...',
			success : function(form, action) {
//				var url = action.result.url;
//				this.uuId = action.result.uuId;
//				this.suffix = action.result.suffix;
//				this.fireEvent('afterUpload', this, this.uuId);
//				//此处路径被转码，需要转回来
//				var reg = new RegExp("&amp;",'g');
//				url = url.replace(reg,"&");
//		    	if(Ext.isIE){
//					$(this.compId).src = url;
//				}else{
//					Ext.get(this.compId).dom.src = Ext.BLANK_IMAGE_URL;
//					Ext.get(this.compId).dom.src = url;
//				}
				atom.dlg.showMomentDlg('上传成功!');
			}
		});
	}
	
});

