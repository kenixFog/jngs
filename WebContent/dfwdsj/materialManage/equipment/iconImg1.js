/*
 * headImg.js
 * 版权所有：江苏电力信息技术有限公司 2014 - 2019
 * 江苏电力信息技术有限公司保留所有权利，未经允许不得以任何形式使用。
 */
Ext.namespace("templates.loginAndReg.headImg.headImg");
/**
 * 头像上传界面 <p>
 * 创建日期：2014-01-20<br>
 * 修改历史：<br>
 * 修改日期：<br>
 * 修改作者：<br>
 * 修改内容：<br>
 * @author Atom Group
 * @version 1.0
 */
templates.loginAndReg.headImg.headImg.img_src = null;

templates.loginAndReg.headImg.headImg.userId=null;
/**
 * 
 * @param {} config
 */
templates.loginAndReg.headImg.headImg.uploadWin = function(config) {
	if(config.img_src){
		templates.loginAndReg.headImg.headImg.img_src = config.img_src;
	}else{
		templates.loginAndReg.headImg.headImg.img_src = templates.loginAndReg.register.img;
	}
	Ext.apply(this, config);
	this.initUIComponents();
	templates.loginAndReg.headImg.headImg.uploadWin.superclass.constructor.call(this);
};

Ext.extend(templates.loginAndReg.headImg.headImg.uploadWin,Ext.Window,{
	/**
	 * 窗体宽度
	 * @type Number
	 */
	maxWidth : 220,
	/**
	 * 窗体高度
	 * @type Number
	 */
	maxHeight : 326,
	/**
	 * 默认图片路径
	 * @type String
	 */
	defaultUrl : templates.loginAndReg.headImg.headImg.img_src,
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
	 * 图片预览区域
	 * @type 
	 */
	curCrop : null,
	/**
	 * 上传成功后得到的UUID
	 */
	uuId : null,
	/**
	 * 删除已有crop
	 * @type Number
	 */
	removeCrop : function(){
		if( this.curCrop == null ){
		}
		else {
			this.curCrop.remove();
		}
	},
	/**
	 * 保存头像
	 */
	savePic : function(){
		var file = Ext.getCmp("picFile").getValue();
		if(file==""){
			Ext.MessageBox.alert('提示', "请选择需要上传的图片!");
			return;
		}
		if(templates.loginAndReg.register.loginId==null){//说明并非注册时修改头像，而是后来维护头像
			templates.loginAndReg.headImg.headImg.userId = atom.releasePlatform.bbs.loginId;
			templates.loginAndReg.headImg.headImg.loginName = atom.releasePlatform.bbs.loginName; 
		} else {//注册时维护头像
			templates.loginAndReg.headImg.headImg.userId = templates.loginAndReg.register.loginId;
			templates.loginAndReg.headImg.headImg.loginName = templates.loginAndReg.register.loginName;
		}
		
		
		if(this.url == undefined){
			this.formPanel.getForm().submit({
				url : atom.webContextRoot+ '/loginAction.do?method=uploadImg',
				method : "post",
				params : {
					x1 : this.x1,
					y1 : this.y1,
					maxWidth : this.maxWidth,
					maxHeight : this.maxHeight,
					height : this.height,
					width : this.width,
					userId : templates.loginAndReg.headImg.headImg.userId,
					loginName : templates.loginAndReg.headImg.headImg.loginName
				},
				waitTitle : '提示',
				waitMsg : '正在向服务器提交数据...',
				success : function(form, action) {
					var url = action.result.url;
					this.uuId = action.result.uuId;
					this.suffix = action.result.suffix;
					this.fireEvent('afterUpload', this, this.uuId);
					//此处路径被转码，需要转回来
					var reg = new RegExp("&amp;",'g');
					url = url.replace(reg,"&");
			    	if(Ext.isIE){
						$(this.compId).src = url;
					}else{
						Ext.get(this.compId).dom.src = Ext.BLANK_IMAGE_URL;
						Ext.get(this.compId).dom.src = url;
						
					}
					atom.dlg.showMomentDlg('上传成功!');
					this.removeCrop();
					this.showUploadWin.close();
				}.createDelegate(this)
			});
		}else{
			this.formPanel.getForm().submit({
				url : this.url,
				method : "post",
				params : this.params,
				waitTitle : '提示',
				waitMsg : '正在向服务器提交数据...',
				success : function(response, action) {
					this.fireEvent('afterUpload', this);
					atom.dlg.showMomentDlg('上传成功!');
					this.removeCrop();
					this.showUploadWin.close();
				}.createDelegate(this)
			});
		}
		
	},
	
	getToolBar : function(){
		var toolBarBtn = [];
		toolBarBtn.push({
			id : 'SAVE',
			text : '保存',
			iconCls : 'btnIconClose',
			handler : this.savePic.createDelegate(this)
		}, {
			id : 'CLOSE',
			text : '关闭',
			iconCls : 'btnIconClose',
			handler : function(){
				this.removeCrop();
				this.showUploadWin.close();
			}.createDelegate(this)
		});
		return new Ext.Toolbar({
			buttons : toolBarBtn
		});
	},
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
			layout : 'column',
			border : false,
			enctype : 'multipart/form-data',
			fileUpload : true,
			region : 'center',
			bodyStyle : 'padding:5px 5px 0;overflow-x:hidden;',
			labelWidth : 45,
			items : [{
				columnWidth : .667,
				layout : 'form',
				border : false,
				items : [{
					layout : 'form',
					border : false,
					bodyStyle : "padding:10px 0 5px 10px",
					items : [{
						xtype : 'textfield',
						id : 'picFile',
						name : 'picFile',
						fieldLabel : '请选择',
						inputType : 'file',
						listeners : {
							'render' : function(){
								var picFileCmp = Ext.get('picFile');
								picFileCmp.on('change',function(field,newValue,oldValue){
									var picPath = picFileCmp.getValue();
									var url = 'file:///'+picPath;
									if(Ext.isIE){
										$( 'pic' ).src = url;
									}else if (Ext.isChrome){
										alert('本组件在chrome浏览器下无法正常工作');
									}else {
										Ext.get('pic').dom.src = Ext.BLANK_IMAGE_URL;
										Ext.get('pic').dom.src = window.URL.createObjectURL(Ext.get('picFile').dom.files[0]);
										
									}
									this.removeCrop();
									this.curCrop = new Cropper.ImgWithPreview(
										'pic',
										{
											minWidth: 90, 
											minHeight: 133,
											ratioDim: { x: 90, y: 133 },
											displayOnInit: true, 
											onEndCrop: function(coords,dimensions){
												this.x1 = coords.x1;
												this.y1 = coords.y1;
												this.width = dimensions.width;
												this.height = dimensions.height;
											}.createDelegate(this),
											previewWrap: 'previewArea'
										}
									);
								}.createDelegate(this),this);
							}.createDelegate(this)
						}
				}]
			},{
					layout : 'form',
					bodyStyle : "margin-left:10px",
					border : false,
					items : [{
						xtype : 'box',
						id : 'pic',
						align : 'center',
						width : this.maxWidth,
						height : this.maxHeight,
						autoEl : {
							tag : 'img',
							src : templates.loginAndReg.headImg.headImg.img_src,
							style : 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);'
						}
				}]
			},{
				layout : 'form',
				bodyStyle : "padding:10px 0 0 10px",
				border : false,
				items : [{
					html : '<div style="line-height:16px"><b>请调整选择框的大小和位置，选中的部分将作为您的自定义头像。</b></div>',
					border : false
				}]
			}]
		},{
				columnWidth : .333,
				layout : 'form',
				border : false,
				items : [{
					border : false,
					layout : 'form',
					bodyStyle : "margin:40px 0 0 25px",
					items : [{
						html : '预览区域:',
						border : false
					}]
				},{
					layout : 'form',
					bodyStyle : "margin-left:25px;margin-top:10px;",
					border : true,
					items : [{
						xtype : 'box',
						id : 'previewArea',
						align : 'center',
						width: 90, 
						height: 133,
						autoEl : {
							tag : 'pre',
							style : 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);',
							src : this.defaultUrl
						}
					}]
				}]
			}],
			listeners : {
				render : function(){
					var picFileCmp = Ext.getCmp('picFile');
					picFileCmp.fireEvent("change");
				}
				
			}
		});
		
		/**
		 * 修改头像画面窗口
		 */
		this.showUploadWin = new Ext.Window({
			layout : 'border',
			region : 'center',
			maximizable : false,
			title : '头像维护',
			width : 380,
			height : 476,
			autoScroll : true,
			closable : true,
			closeAction : 'close',
			modal : true,
			tbar : this.getToolBar(),
			items : this.formPanel
		});
		this.showUploadWin.show();
	}
});