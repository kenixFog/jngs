Ext.namespace("sysManage.comCodeManage.entry");

//弹出二级窗口
sysManage.comCodeManage.entry.win = null;
//标识是新增还是(编辑和查看）
sysManage.comCodeManage.entry.currObjId = null;	
//父节点Id
sysManage.comCodeManage.entry.parentId = null;	


/**
 * 对菜单窗口的显示方式进行控制
 */
sysManage.comCodeManage.entry.showWin = function(titleText) {
	//命名空间
	var className = sysManage.comCodeManage.entry;
	//当前选中树节点
	var node = sysManage.comCodeManage.tree.node;
	//判断当前选中的节点类型
	if (node.isLeaf()) { //如果是叶子节点，只能增加数据
		alert("leaf"+node.raw.id);
	} else {
		alert("！leaf"+node.raw.id);
	}
	if(className.win){
		
	} else {
		//获取二级弹出窗口
		className.win = className.createWin(titleText);
	}
	
	//显示二级窗体
	className.setwinToolBar(titleText);
	//根据条件对窗体中表单进行数据加载
	className.setwinForm(titleText);
	className.win.show();
	//根据条件控制工具栏按钮
	return className.win;
}

/**
 * 生成二级窗口
 * @returns {Ext.Window}
 */
sysManage.comCodeManage.entry.createWin = function(titleText) {
	var winCfg = {
		layout : 'fit',   	  // 布局样式
		width : 400,	
		height : 360,
		title : '公共代码'+'-'+titleText,
		resizable : false,	 // 不允许用户允许拖动窗体边角来控制窗口大小
		autoScroll : true,   // 自动显示滚动条
		closeAction : 'hide',// 关闭时为隐藏操作
		modal : true,		 // 模态化显示：后方的区域不能点击和编辑
		tbar :{
			cls:'whjn-tbar',
			items:[ {
				text : whjn.constant.saveBtnText,
				iconCls : 'fa fa-floppy-o fa-lg',
				bizCode : 'save',
				handler : sysManage.comCodeManage.entry.saveHandler
			}, {
				text : whjn.constant.closeBtnText,
				iconCls : 'fa fa-times-circle fa-lg',
				bizCode : 'close',
				handler : sysManage.comCodeManage.entry.closeHandler
			} ],
		},
		items : sysManage.comCodeManage.entry.initInfoArea()
	};
	var win = new Ext.Window(winCfg);
	return win;
}		


/**
 * 初始化表单信息
 * @returns {Ext.form.FormPanel}
 */
sysManage.comCodeManage.entry.initInfoArea = function() {
	
	var className = sysManage.comCodeManage.entry;
	var formPnl = new Ext.form.Panel({
		bodyPadding: 20,
		border : false,
		fieldDefaults : {
			labelAlign : 'left',
			labelWidth : 80,
			anchor : '100%'
		},
		items : [{
			id : 'ID',
			xtype : 'hidden',
			name : 'ID'
		},{
			id:'parentId',
			xtype : 'hidden',
			name : 'parentId'
		},{
			xtype : 'textfield',
			name : 'code',
			fieldLabel : '编码',
			style: 'margin-top:10px',
			allowBlank : false,
			maxLength : 50,
			maxLengthText : "最大长度不超过50个字符"
		},{
			xtype : 'textfield',
			name : 'name',
			fieldLabel : '名称',
			style: 'margin-top:10px',
			allowBlank : false,
			maxLength : 50,
			maxLengthText : "最大长度不超过50个字符"
		},{
			xtype : 'combobox',
			name : 'type',
			fieldLabel : '类型',
			style: 'margin-top:10px',
			emptyText : '请选择...',
			allowBlank : false
		},{
			xtype : 'combobox',
			name : 'isLeaf',
			fieldLabel : '是否叶结点',
			style: 'margin-top:10px',
			emptyText : '请选择...',
			disabled : true,
			allowBlank : false
		},{
			xtype : 'combobox',
			name : 'statue',
			fieldLabel : '菜单状态',
			emptyText : '请选择...',
			allowBlank : false,
			style: 'margin-top:10px'
		},{
			xtype : 'textareafield',
			name : 'comments',
			fieldLabel : '备注',
			style: 'margin-top:10px',
			height: 60,
			allowBlank : false,
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
sysManage.comCodeManage.entry.setwinToolBar = function(titleText) {
	var className = sysManage.comCodeManage.entry;
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
sysManage.comCodeManage.entry.setwinForm = function(titleText) {
	var className = sysManage.comCodeManage.entry;
	var formPnl = className.formPnl;
	if ("编辑" == titleText) {
		var url = webContextRoot + '/sys/comCode/getComCodeInfo';
		formPnl.getForm().load({
			url : url,
			method : "post",
			params : {
				// 菜单Id
				comCodeId : className.currObjId
			},
			waitTitle : "提示",
			waitMsg : "正在从服务器提取数据...",
			failure : function(form, action) {
				className.win.hide();
			},
			success : function(form, action) {
				Ext.getCmp("ID").setValue(action.result.data.ID);
				Ext.getCmp("parentId").setValue(action.result.data.parentId);
			}
		});
	} else { //新增
		//树节点
		var parentNode = sysManage.comCodeManage.tree.node;
		formPnl.getForm().findField("parentId").setValue(parentNode.raw.id);
	}
	
}


/**
 * 保存新增或编辑后的信息
 */
sysManage.comCodeManage.entry.saveHandler = function() {
	var className = sysManage.comCodeManage.entry;
	//校验表单的正确性
	var str = whjn.validateForm(className.formPnl);
	if (str == "") { //如果校验通过
		var params = {};
		var qryNames = [ "ID", "parentId", "code", "name", 
			"type","isLeaf", "comments", "createTime", "lastEditTime", "statue"];
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
			alert(qryNames[i]+":"+objTmp.getValue());
		}
//		//操作类型，0 代表新增，非0代表编辑或删除
//		params["currObjId"] = className.currObjId;
//		//当前节点ID
//		params["nodeId"] = sysManage.comCodeManage.tree.curTreeNode.attributes["nodeId"];
//		className.formPnl.getForm().submit(
//				{
//					url : atom.webContextRoot
//							+ '/treeAndPanelsAction.do?method=saveStuInfo',
//					method : "post",
//					params : params,
//					waitTitle : "提示",
//					waitMsg : "正在从服务器提取数据...",
//					success : function(form, action) {
//						parent.Appframe.viewinfdlg.show("数据保存成功!");
//						//重新加载学生列表信息
//						sysManage.comCodeManage.stuPanel.mainGrid.getStore()
//								.load();
//						//调用关闭按钮
//						sysManage.comCodeManage.entry.closeHandler();
//					}
//				});
	} else {
		Ext.MessageBox.alert("提示", str);
	}
}

/**
 * 关闭按钮事件
 */
sysManage.comCodeManage.entry.closeHandler = function() {
	sysManage.comCodeManage.entry.win.close();
}

