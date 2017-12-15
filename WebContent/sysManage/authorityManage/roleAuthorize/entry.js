Ext.namespace("sysManage.authorityManage.role.entry");

//弹出二级窗口
sysManage.authorityManage.role.entry.win = null;
//标识是新增还是(编辑和查看）-1是新增
sysManage.authorityManage.role.entry.currObjId = null;	
//父节点Id
sysManage.authorityManage.role.entry.parentId = null;	


/**
 * 对菜单窗口的显示方式进行控制
 */
sysManage.authorityManage.role.entry.showWin = function(titleText) {
	//命名空间
	var className = sysManage.authorityManage.role.entry;
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
sysManage.authorityManage.role.entry.createWin = function(titleText) {
	var nodeType = sysManage.authorityManage.role.tree.node.raw.nodeType;
	var typeTilte='';
	if(nodeType==0){
		typeTilte = '分组';
	} else {
		typeTilte = '角色';
	}
	var winCfg = {
		layout : 'fit',   	  // 布局样式
		width : 300,	
		height : 250,
		title : titleText + typeTilte,
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
				handler : sysManage.authorityManage.role.entry.saveHandler
			}, {
				text : whjn.constant.closeBtnText,
				iconCls : 'fa fa-times-circle fa-lg',
				bizCode : 'close',
				handler : sysManage.authorityManage.role.entry.closeHandler
			} ],
		},
		items : sysManage.authorityManage.role.entry.initInfoArea()
	};
	var win = new Ext.Window(winCfg);
	return win;
}		


/**
 * 初始化表单信息
 * @returns {Ext.form.FormPanel}
 */
sysManage.authorityManage.role.entry.initInfoArea = function() {
	
	var className = sysManage.authorityManage.role.entry;
	var formPnl = new Ext.form.Panel({
		bodyPadding: 20,
		border : false,
		fieldDefaults : {
			labelAlign : 'left',
			labelWidth : 50,
			anchor : '100%'
		},
		items : [{
			id : 'id',
			xtype : 'hidden',
			name : 'id',
			value : sysManage.authorityManage.role.entry.currObjId
		},{
			xtype : 'textfield',
			name : 'roleCode',
			fieldLabel : '编码',
			style: 'margin-top:10px',
			allowBlank : false,
			maxLength : 50,
			maxLengthText : "最大长度不超过50个字符"
		},{
			xtype : 'textfield',
			name : 'roleName',
			fieldLabel : '名称',
			style: 'margin-top:10px',
			allowBlank : false,
			maxLength : 50,
			maxLengthText : "最大长度不超过50个字符"
		},{
			xtype : 'textareafield',
			name : 'comments',
			fieldLabel : '备注',
			style: 'margin-top:10px',
			height: 60,
			maxLength : 200,
			maxLengthText : "最大长度不超过200个字符"
		}]
	});
	className.formPnl = formPnl;
	return formPnl;
}

/**
 * 设置工具栏按钮
 * @param titleText
 */
sysManage.authorityManage.role.entry.setwinToolBar = function(titleText) {
	var className = sysManage.authorityManage.role.entry;
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
sysManage.authorityManage.role.entry.setwinForm = function(titleText) {
	var className = sysManage.authorityManage.role.entry;
	var formPnl = className.formPnl;
	if ("编辑" == titleText) {
		formPnl.getForm().load({
			url : webContextRoot + '/sys/authority/getRoleInfo',
			method : "post",
			params : {
				// 角色Id
				roleId : className.currObjId,
				nodeType : sysManage.authorityManage.role.tree.node.raw.nodeType

			},
			waitTitle : "提示",
			waitMsg : "正在从服务器提取数据...",
			failure : function(form, action) {
				className.win.close();
			},
			success : function(form, action) {
				Ext.getCmp("Id").setValue(action.result.data.ID);
				Ext.getCmp("parentId").setValue(action.result.data.parentId);
			}
		});
	}
}


/**
 * 保存新增或编辑后的信息
 */
sysManage.authorityManage.role.entry.saveHandler = function() {
	var className = sysManage.authorityManage.role.entry;
	//校验表单的正确性
	var str = whjn.validateForm(className.formPnl);
	var saveUrl='';
	if (str == "") { //如果校验通过
		var params = {};
		var qryNames = [ "id",  "roleCode", "roleName", "comments"];
		//循环遍历，获取表单信息
		for ( var i = 0; i < qryNames.length; i++) {
			var objTmp = className.formPnl.getForm().findField(qryNames[i]);
			if (objTmp) {
				if (objTmp.getXType() == 'datefield') {//如果是时间空间，直接获取时间格式的数据类型
					params[qryNames[i]] = objTmp.getRawValue();
				} else {
					params[qryNames[i]] = objTmp.getValue();
				}
			}
		}
		params["nodeType"] = sysManage.authorityManage.role.tree.node.raw.nodeType;
		params["nodeId"] = sysManage.authorityManage.role.tree.node.raw.id;
		if(params["nodeType"] == 0){
			saveUrl = webContextRoot + '/sys/authority/saveRoleTypeInfo'
		}else {
			saveUrl = webContextRoot + '/sys/authority/saveRoleInfo'
		}
		Ext.Ajax.request({
			url : saveUrl,
			params : params,
			method : "POST",
			success : function(response) {
				if (response.responseText != '') {
					var res = Ext.JSON.decode(response.responseText);
					if (res.success) {
						whjn.dlg.showMomentDlg("保存成功!");
						sysManage.authorityManage.role.entry.closeHandler();
						//获取数据列表窗口
						var className = sysManage.authorityManage.role.panel;
						//重新加载列表数据
						className.loadRecord();
						//树面板
						var treePnl = sysManage.authorityManage.role.tree.comCodeTree;
						//点前选中的树节点
						var node = sysManage.authorityManage.role.tree.node;
						//设置需要加载的树节点Id
						treePnl.getStore().proxy.extraParams.parentId = node.data.id;
						//刷新当前的树节点
						whjn.refreshTreePnl(treePnl, node.data.id);
					} else {
						whjn.dlg.errTip(res.message);
					}
				}
			},
			failure : function(response) {
				whjn.dlg.errTip('操作失败！');
			}
		});
	} else {
		Ext.MessageBox.alert("提示", str);
	}
}

/**
 * 关闭按钮事件
 */
sysManage.authorityManage.role.entry.closeHandler = function() {
	sysManage.authorityManage.role.entry.win.close();
}

