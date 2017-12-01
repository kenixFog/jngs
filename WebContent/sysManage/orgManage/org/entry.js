Ext.namespace("sysManage.orgManage.org.entry");

//弹出二级窗口
sysManage.orgManage.org.entry.win = null;
//标识是新增还是(编辑和查看）
sysManage.orgManage.org.entry.currObjId = null;
//父节点Id
sysManage.orgManage.org.entry.parentId = null;	


/**
 * 对菜单窗口的显示方式进行控制
 */
sysManage.orgManage.org.entry.showWin = function(titleText) {
	//命名空间
	var className = sysManage.orgManage.org.entry;
	//当前选中树节点
	var node = sysManage.orgManage.org.tree.node;
	//获取二级弹出窗口
	className.win = className.createWin(titleText);
	//根据条件控制工具栏按钮
	className.setwinToolBar(titleText);
	//根据条件对窗体中表单进行数据加载
	className.setwinForm(titleText);
	//判断当前选中的节点类型
	if (node.isLeaf()) { //选中末级页面结点，点击新增
		//只能新增按钮类型
		className.formPnl.getForm().findField("type").setValue('2');
		//类型下拉框不可用
		className.formPnl.getForm().findField("type").setDisabled(true);
	}
	//显示二级窗体
	className.win.show();
}

/**
 * 生成二级窗口
 * @returns {Ext.Window}
 */
sysManage.orgManage.org.entry.createWin = function(titleText) {
	var winCfg = {
		layout : 'fit',   	  // 布局样式
		width : 400,	
		height : 380,
		title : '菜单信息'+'-'+titleText,
		resizable : false,	 // 不允许用户允许拖动窗体边角来控制窗口大小
		autoScroll : true,   // 自动显示滚动条
		closeAction : 'destroy',// 关闭时为隐藏操作
		modal : true,		 // 模态化显示：后方的区域不能点击和编辑
		tbar :{
			cls:'whjn-tbar',
			items:[ {
				text : whjn.constant.saveBtnText,
				iconCls : 'fa fa-floppy-o fa-lg',
				bizCode : 'save',
				handler : sysManage.orgManage.org.entry.saveHandler
			}, {
				text : whjn.constant.closeBtnText,
				iconCls : 'fa fa-times-circle fa-lg',
				bizCode : 'close',
				handler : sysManage.orgManage.org.entry.closeHandler
			} ],
		},
		items : sysManage.orgManage.org.entry.initInfoArea()
	};
	var win = new Ext.Window(winCfg);
	return win;
}		


/**
 * 初始化表单信息
 * @returns {Ext.form.FormPanel}
 */
sysManage.orgManage.org.entry.initInfoArea = function() {
	var className = sysManage.orgManage.org.entry;
	var formPnl = new Ext.form.Panel({
		bodyPadding: 20,
		border : false,
		fieldDefaults : {
			labelAlign : 'left',
			labelWidth : 80,
			anchor : '100%'
		},
		items : [{
			id : 'id',
			xtype : 'hidden',
			name : 'id',
			value : sysManage.orgManage.org.entry.currObjId
		},{
			id:'parentId',
			xtype : 'hidden',
			name : 'parentId'
		},{
			xtype : 'textfield',
			name : 'menuCode',
			fieldLabel : '菜单编码',
			style: 'margin-top:10px',
			allowBlank : false,
			maxLength : 50,
			maxLengthText : "最大长度不超过50个字符"
		},{
			xtype : 'textfield',
			name : 'menuName',
			fieldLabel : '菜单名称',
			style: 'margin-top:10px',
			allowBlank : false,
			maxLength : 50,
			maxLengthText : "最大长度不超过50个字符"
		},{
			xtype : 'combobox',
			name : 'type',
			fieldLabel : '菜单类型',
			style: 'margin-top:10px',
			emptyText : '请选择...',
			editable : false,
			store : sysManage.orgManage.org.typeArray,
			allowBlank : false,
			listeners : {
				'change' : function (field, newValue, oldValue, eOpts ) {
					if(!sysManage.orgManage.org.tree.node.isLeaf() && newValue == 2){//非叶子节点（分层菜单）
						whjn.dlg.errTip("分层菜单下，不允许新增按钮!");
						className.formPnl.getForm().findField("type").setValue();
						return;//分层菜单下，不允许新增按钮
					} else {
						if(newValue==1){//末级页面
							//url字段可用
//							className.formPnl.getForm().findField("url").setDisabled(false);
							//不允许为空
							className.formPnl.getForm().findField("url").allowBlank = false;
						} else {//分层节点 或 按钮
							//清空url路径
//							className.formPnl.getForm().findField("url").setValue();
							//url字段不可用
//							className.formPnl.getForm().findField("url").setDisabled(true);
							//允许为空
							className.formPnl.getForm().findField("url").allowBlank = true;
						}
					}
				}
			}
		},{
			xtype : 'textfield',
			name : 'url',
			fieldLabel : '菜单路径',
			style: 'margin-top:10px',
//			disabled : true,//默认不可用
			maxLength : 200,
			maxLengthText : "最大长度不超过200个字符"
		},{
			xtype : 'combobox',
			name : 'isEdit',
			fieldLabel : '是否可编辑',
			emptyText : '请选择...',
			editable : false,
			store : sysManage.orgManage.org.yesOrNoArray,
			value: '1',
			allowBlank : false,
			style: 'margin-top:10px'
		},{
			xtype : 'combobox',
			name : 'isDelete',
			fieldLabel : '是否可删除',
			emptyText : '请选择...',
			editable : false,
			store : sysManage.orgManage.org.yesOrNoArray,
			value: '1',
			allowBlank : false,
			style: 'margin-top:10px'
		},{
			xtype : 'combobox',
			name : 'statue',
			fieldLabel : '菜单状态',
			emptyText : '请选择...',
			editable : false,
			store : sysManage.orgManage.org.statusArray,
			value: '1',
			allowBlank : false,
			style: 'margin-top:10px'
		}]
	});
	className.formPnl = formPnl;
	return formPnl;
}

/**
 * 设置工具栏按钮
 * @param titleText
 */
sysManage.orgManage.org.entry.setwinToolBar = function(titleText) {
	var className = sysManage.orgManage.org.entry;
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
sysManage.orgManage.org.entry.setwinForm = function(titleText) {
	var className = sysManage.orgManage.org.entry;
	var formPnl = className.formPnl;
	if ("编辑" == titleText) {
		formPnl.getForm().load({
			url :  webContextRoot + '/sys/menu/getMenuInfo',
			method : "post",
			params : {
				// 菜单Id
				menuId : className.currObjId
			},
			waitTitle : "提示",
			waitMsg : "正在从服务器提取数据...",
			failure : function(form, action) {
				className.win.close();
			},
			success : function(form, action) {
				Ext.getCmp("ID").setValue(action.result.data.ID);
				Ext.getCmp("parentId").setValue(action.result.data.parentId);
			}
		});
	} else { //新增
		//树节点
		var parentNode = sysManage.orgManage.org.tree.node;
		formPnl.getForm().findField("parentId").setValue(parentNode.raw.id);
	}
	
}


/**
 * 保存新增或编辑后的信息
 */
sysManage.orgManage.org.entry.saveHandler = function() {
	var className = sysManage.orgManage.org.entry;
	//校验表单的正确性
	var str = whjn.validateForm(className.formPnl);
	if (str == "") { //如果校验通过
		var params = {};
		var qryNames = [ "id", "parentId", "menuCode", "menuName", 
			"type","url", "isEdit", "isDelete", "statue"];
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
		Ext.Ajax.request({
			url : webContextRoot + '/sys/menu/saveMenuInfo',
			params : params,
			method : "POST",
			success : function(response) {
				if (response.responseText != '') {
					var res = Ext.JSON.decode(response.responseText);
					if (res.success) {
						whjn.dlg.showMomentDlg("保存成功!");
						sysManage.orgManage.org.entry.closeHandler();
						//获取数据列表窗口
						var className = sysManage.orgManage.org.panel;
						//重新加载列表数据
						className.loadRecord();
						//树面板
						var treePnl = sysManage.orgManage.org.tree.menuTree;
						//点前选中的树节点
						var node = sysManage.orgManage.org.tree.node;
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
sysManage.orgManage.org.entry.closeHandler = function() {
	sysManage.orgManage.org.entry.win.close();
}

