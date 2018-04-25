﻿Ext.namespace("dawdsj.materialManage.equipment.entry");

dawdsj.materialManage.equipment.entry.fieldInfo=null;

dawdsj.materialManage.equipment.entry.img= webContextRoot + "/resources/img/qcIcon/123.jpg";

dawdsj.materialManage.equipment.entry.objId = -1;
/**
 * 对菜单窗口的显示方式进行控制
 */
dawdsj.materialManage.equipment.entry.showWin = function(titleText) {
	//命名空间
	var className = dawdsj.materialManage.equipment.entry;
	className.win = className.createWin(titleText);
	//根据条件控制工具栏按钮
	className.setwinToolBar(titleText);
	//根据条件对窗体中表单进行数据加载
	className.setwinForm(titleText);
	//显示二级窗体
	className.win.show();
}

/**
 * 生成二级窗口
 * @returns {Ext.Window}
 */
dawdsj.materialManage.equipment.entry.createWin = function(titleText) {
	
	var winCfg = {
		layout : 'fit',   	  // 布局样式
		width : 700,
		height:500,
		title : titleText,
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
				handler : dawdsj.materialManage.equipment.entry.saveHandler
			}, {
				text : whjn.constant.closeBtnText,
				iconCls : 'fa fa-times-circle fa-lg',
				bizCode : 'close',
				handler : dawdsj.materialManage.equipment.entry.closeHandler
			} ],
		},
		items : dawdsj.materialManage.equipment.entry.initInfoArea()
	};
	var win = new Ext.Window(winCfg);
	return win;
}		


/**
 * 初始化表单信息
 * @returns {Ext.form.FormPanel}
 */
dawdsj.materialManage.equipment.entry.initInfoArea = function() {
	var fieldsInfo =  dawdsj.materialManage.equipment.entry.fieldInfo;
	if(fieldsInfo.length==0){
		whjn.dlg.errTip("该类型未配置相应属性字段，请先在器材分类中维护后进行操作！");
		return;
	}
	var className = dawdsj.materialManage.equipment.entry;
	var formPnl = new Ext.form.Panel({
		bodyPadding: 20,
		border : false,
		anchor: '100%',  
	    layout: 'column', 
	    autoScroll:true,
		fieldDefaults : {
			labelAlign : 'left',
			labelWidth : 50
		},
		items: [{  
	        xtype: 'container',  
	        columnWidth: .35,  
			id:'col1',
	        layout: 'anchor',  
	        items: []  
	    },{  
	        xtype: 'container',  
	        columnWidth: .35,
			id:'col2',
	        layout: 'anchor',  
	        items: []  
	    },{  
	        xtype: 'container',  
	        columnWidth: .3,
			id:'col3',
	        layout: 'anchor',  
	        items: []  
	    }]  
	});
	
	//第一列
    var col1 = Ext.getCmp("col1");
    //第二列
    var col2 = Ext.getCmp("col2");
    //第三列
    var col3 = Ext.getCmp("col3");
    
	var flag =true;
	for(var i = 0;i< fieldsInfo.length;i++){//编码,名称,类型,长度,默认值
		var field;
		var readOnly = false;
		if(fieldsInfo[i][0]=='ID'){
			readOnly = true;
		}
		if(fieldsInfo[i][2]=='textfield'){//文本框
			field = {
				xtype : 'textfield', 
				name: fieldsInfo[i][0],
				id: fieldsInfo[i][0],
				readOnly:readOnly,
				style: 'margin-top:10px',
			    fieldLabel: fieldsInfo[i][1],  
			    anchor: '80%'  
			}; 
		} else if(fieldsInfo[i][2]=='datetime'){//时间
			field = {
				xtype : 'datetime', 
				name: fieldsInfo[i][0],
				id: fieldsInfo[i][0],
				readOnly:readOnly,
				style: 'margin-top:10px',
			    fieldLabel: fieldsInfo[i][1],  
			    anchor: '80%'  
			}; 
		}else if(fieldsInfo[i][2]=='combo'){//下拉框
			
			Ext.regModel('model',{
				fields:[
					{name:'comCodeCode'},  
				    {name:'comCodeValue'},  
				    {name:'comCodeName'} 
				    ]
			});
			var store = new Ext.data.Store({
				model: 'model',
			    proxy:{
			    	url: webContextRoot+'/sys/comCode/getComCodeByCode',
					type : 'ajax',
					extraParams  : {
						codeValue : fieldsInfo[i][4]
					},
					reader:new Ext.data.ArrayReader({model:'model'})
			    }
			}); 
			
			field = {
				xtype : 'combo', 
				name: fieldsInfo[i][0],
				id: fieldsInfo[i][0],
				triggerAction:'all',
				queryMode:'remote',
			    fieldLabel: fieldsInfo[i][1],  
			    anchor: '80%',
			    displayField:'comCodeName',
			    valueField:'comCodeValue',
			    readOnly:readOnly,
			    style: 'margin-top:10px',
				emptyText : '请选择...',
				editable : false,
				store: store
			}; 
		} else if(fieldsInfo[i][2]=='box'){//文件
			var id = fieldsInfo[i][0];
			field = [{
	        	xtype : 'box',  
				name : 'slt',
				style: 'margin-top:10px',
				fieldLabel:'缩略图',
                id : id,  
                border : true,
                autoEl : {  
                    width : 180,  
                    height : 180, 
                    tag : 'img',  
                    src : dawdsj.materialManage.equipment.entry.img  
              }  
	        },{
	        	xtype : 'button',
				text :'选择图片',
				handler : function() {
					
					var win = new dawdsj.materialManage.equipment.iconImg.uploadWin({
						compId : id
					});
//					win.on('afterUpload', function(v) {
//						templates.loginAndReg.register.headImgID = v.uuId;
//						templates.loginAndReg.register.headImgSuffix = v.suffix;
//					});
				}
	        }] 
		}
		
		if(fieldsInfo[i][2]=='box'){
			col3.add(field);
		} else {
			if(flag){
				 col1.add(field);
				 flag = false;
			} else if(!flag){
				 col2.add(field);
				 flag = true;
			}
		}
	}
    col1.doLayout();
    col2.doLayout();
    col3.doLayout();
	className.formPnl = formPnl;
	return formPnl;
}

/**
 * 设置工具栏按钮
 * @param titleText
 */
dawdsj.materialManage.equipment.entry.setwinToolBar = function(titleText) {
	var className = dawdsj.materialManage.equipment.entry;
	var toolBar = className.win.getDockedItems('toolbar[dock="top"]');
	var funcObjs = null;
	//根据不同操作，控制工具栏按钮
	if ("新增" == titleText || "编辑" == titleText) {
		funcObjs = [ 'save', 'close' ];
	} 
	if (funcObjs == null)
		funcObjs = [];
	whjn.enableFuncBtn(toolBar, null, null, "hide", null, funcObjs);
}

/**
 * 加载窗体的表单信息
 * @param titleText
 */
dawdsj.materialManage.equipment.entry.setwinForm = function(titleText) {
//	var className = dawdsj.materialManage.equipment.entry;
//	var formPnl = className.formPnl;
//	if ("编辑" == titleText) {
//		formPnl.getForm().load({
//			url : webContextRoot + '/sys/authority/getRoleInfo',
//			method : "post",
//			params : {
//				// 角色Id
//				roleId : className.currObjId,
//				nodeType : sysManage.authorityManage.role.tree.node.raw.nodeType
//
//			},
//			waitTitle : "提示",
//			waitMsg : "正在从服务器提取数据...",
//			failure : function(form, action) {
//				className.win.close();
//			},
//			success : function(form, action) {
//				Ext.getCmp("Id").setValue(action.result.data.ID);
//				Ext.getCmp("parentId").setValue(action.result.data.parentId);
//			}
//		});
//	}
}


/**
 * 保存新增或编辑后的信息
 */
dawdsj.materialManage.equipment.entry.saveHandler = function() {
	  
	 
	
	
	
	
	
//	var className = dawdsj.materialManage.equipment.entry;
//	//校验表单的正确性
//	var str = whjn.validateForm(className.formPnl);
//	var saveUrl='';
//	if (str == "") { //如果校验通过
//		var params = {};
//		var qryNames = [ "id",  "roleCode", "roleName", "comments"];
//		//循环遍历，获取表单信息
//		for ( var i = 0; i < qryNames.length; i++) {
//			var objTmp = className.formPnl.getForm().findField(qryNames[i]);
//			if (objTmp) {
//				if (objTmp.getXType() == 'datefield') {//如果是时间空间，直接获取时间格式的数据类型
//					params[qryNames[i]] = objTmp.getRawValue();
//				} else {
//					params[qryNames[i]] = objTmp.getValue();
//				}
//			}
//		}
//		params["nodeType"] = sysManage.authorityManage.role.tree.node.raw.nodeType;
//		params["nodeId"] = sysManage.authorityManage.role.tree.node.raw.id;
//		if(params["nodeType"] == 0){
//			saveUrl = webContextRoot + '/sys/authority/saveRoleTypeInfo'
//		}else {
//			saveUrl = webContextRoot + '/sys/authority/saveRoleInfo'
//		}
//		Ext.Ajax.request({
//			url : saveUrl,
//			params : params,
//			method : "POST",
//			success : function(response) {
//				if (response.responseText != '') {
//					var res = Ext.JSON.decode(response.responseText);
//					if (res.success) {
//						whjn.dlg.showMomentDlg("保存成功!");
//						dawdsj.materialManage.equipment.entry.closeHandler();
//						//获取数据列表窗口
//						var className = sysManage.authorityManage.role.panel;
//						//重新加载列表数据
//						className.loadRecord();
//						if(params["nodeType"] == 0){
//							//树面板
//							var treePnl = sysManage.authorityManage.role.tree.roleTree;
//							//点前选中的树节点
//							var node = sysManage.authorityManage.role.tree.node;
//							//设置需要加载的树节点Id
//							treePnl.getStore().proxy.extraParams.parentId = nnode.data.id;
//							//刷新当前的树节点
//							whjn.refreshTreePnl(treePnl, node.data.id);
//						}
//					} else {
//						whjn.dlg.errTip(res.message);
//					}
//				}
//			},
//			failure : function(response) {
//				whjn.dlg.errTip('操作失败！');
//			}
//		});
//	} else {
//		Ext.MessageBox.alert("提示", str);
//	}
}

/**
 * 关闭按钮事件
 */
dawdsj.materialManage.equipment.entry.closeHandler = function() {
	dawdsj.materialManage.equipment.entry.win.close();
}

