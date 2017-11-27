Ext.namespace("sysManage.menuManage.entry");

//弹出二级窗口
sysManage.menuManage.entry.win = null;
//标识是新增还是(编辑和查看）
sysManage.menuManage.entry.currObjId = null;	


/**
 * 对菜单窗口的显示方式进行控制
 */
sysManage.menuManage.entry.showWin = function(titleText) {
	//命名空间
	var className = sysManage.menuManage.entry;
	//当前选中树节点
	var node = sysManage.menuManage.tree.node;
	//判断当前选中的节点类型
	if (node.isLeaf()) { //如果是叶子节点，只能增加页面的按钮
		
		alert("leaf"+node.raw.id);
//		//通过当前节点信息获取年级名称
//		className.grade = sysManage.menuManage.tree.curTreeNode.parentNode.attributes.text;
//		//通过当前节点信息获取班级名称
//		className.clas = sysManage.menuManage.tree.curTreeNode.attributes.text;
	} else {
		alert("！leaf"+node.raw.id);
//		//通过当前列面信息获取年级名称
//		className.grade = rec.get("GRADE");
//		//通过当前列面信息获取班级名称
//		className.clas = rec.get("CLASSNUMBER");
	}
	//获取二级弹出窗口
	className.win = className.createWin(titleText);
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
sysManage.menuManage.entry.createWin = function(titleText) {
	var winCfg = {
		layout : 'fit',   	  // 布局样式
		width : 400,	
		height : 430,
		title : '菜单信息'+'-'+titleText,
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
				handler : sysManage.menuManage.entry.saveHandler
			}, {
				text : whjn.constant.closeBtnText,
				iconCls : 'fa fa-times-circle fa-lg',
				bizCode : 'close',
				handler : sysManage.menuManage.entry.closeHandler
			} ],
		},
		items : sysManage.menuManage.entry.initInfoArea()
	};
	var win = new Ext.Window(winCfg);
	return win;
}		


/**
 * 初始化表单信息
 * @returns {Ext.form.FormPanel}
 */
sysManage.menuManage.entry.initInfoArea = function() {
	
	var menuTreeStore = Ext.create('Ext.data.TreeStore', {   
        root: {  
        	id : -1,
            text : '功能菜单树', 
    		type : 0, //节点类型				
            expanded : true,
            leaf : false  
        },  
        proxy: {   
        	type : 'ajax',  
            url : webContextRoot + '/sys/menu/getMenuTreeByParentId',//请求  
            reader : {  
                type : 'json'
            } 
        },
        listeners : {  
            'beforeexpand' : function(node,eOpts){  
            	//点击父亲节点的菜单会将节点的id通过ajax请求 
                this.proxy.extraParams.parentId = node.raw.id;  
            }
        }
    });
	
	var className = sysManage.menuManage.entry;
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
		},new Ext.create("Ext.ux.ComboBoxTree", { 
	        name : 'parentName',  
//	        storeUrl : webContextRoot + '/sys/menu/getMenuTreeByParentId',  
	        multiSelect: false, 
	        onlyChooseLeaf : false, 
	        fieldLabel : '上级菜单', 
	        labelWidth : 80,
	        editable : false,
	        store : menuTreeStore,
	        listeners:{
	        	change : function(field, newValue, oldValue, eOpts){
	        		Ext.getCmp("parentId").setValue(this.getSubmitValue());
	        	}
	        }
	    }),{
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
			xtype : 'textfield',
			name : 'url',
			fieldLabel : '菜单路径',
			style: 'margin-top:10px',
			allowBlank : false,
			readOnly : true,
			maxLength : 200,
			maxLengthText : "最大长度不超过200个字符"
		},{
			xtype : 'combobox',
			name : 'isEdit',
			fieldLabel : '是否可编辑',
			emptyText : '请选择...',
			allowBlank : false,
			style: 'margin-top:10px'
		},{
			xtype : 'combobox',
			name : 'isDelete',
			fieldLabel : '是否可删除',
			emptyText : '请选择...',
			allowBlank : false,
			style: 'margin-top:10px'
		},{
			xtype : 'combobox',
			name : 'statue',
			fieldLabel : '菜单状态',
			emptyText : '请选择...',
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
sysManage.menuManage.entry.setwinToolBar = function(titleText) {
	var className = sysManage.menuManage.entry;
	var toolBar = className.win.getDockedItems('toolbar[dock="top"]');
	var funcObjs = null;
	//根据不同操作，控制工具栏按钮
	if ("新增" == titleText || "编辑" == titleText) {
		funcObjs = [ 'save', 'close' ];
	} else if ("查看" == titleText) {
		funcObjs = [ 'close' ];
	}
	if (funcObjs == null)
		funcObjs = [];
	whjn.enableFuncBtn(toolBar, null, null, "hide", null, funcObjs);
}

/**
 * 加载窗体的表单信息
 * @param titleText
 */
sysManage.menuManage.entry.setwinForm = function(titleText) {
	var className = sysManage.menuManage.entry;
	var formPnl = className.formPnl;
	if ("查看" == titleText || "编辑" == titleText) {
		var url = webContextRoot + '/sys/menu/getMenuInfo';
		formPnl.getForm().load({
			url : url,
			method : "post",
			params : {
				// 菜单Id
				menuId : className.currObjId
			},
			waitTitle : "提示",
			waitMsg : "正在从服务器提取数据...",
			failure : function(form, action) {
				className.win.hide();
			},
			success : function(form, action) {
				Ext.getCmp("ID").setValue(action.result.data.ID);
				Ext.getCmp("parentId").setValue(action.result.data.parentId);
				if ("查看" == titleText) {
					// 设置表单里面的控件为只读
					whjn.setFormReadOnly(formPnl, true);
				} else if ("编辑" == titleText) {
					whjn.setFormReadOnly(formPnl, false);
					if(action.result.data.type==1){//如果是按钮
						var childTmp = formPnl.getForm().findField("parentName");

					}
				}
			}
		});
	} else { //新增
		//树节点
		var parentNode = sysManage.menuManage.tree.node;
		formPnl.getForm().findField("parentId").setValue(parentNode.raw.id);
		formPnl.getForm().findField("parentName").setValue(parentNode.raw.text);
	}
	
}


/**
 * 保存新增或编辑后的信息
 */
sysManage.menuManage.entry.saveHandler = function() {
	var className = sysManage.menuManage.entry;
	//校验表单的正确性
	var str = whjn.validateForm(className.formPnl);
	if (str == "") { //如果校验通过
		var params = {};
		var qryNames = [ "ID", "parentId", "parentName", "menuCode", "menuName", 
			"type","isLeaf", "url", "isEdit", "isDelete", "statue"];
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
//		params["nodeId"] = sysManage.menuManage.tree.curTreeNode.attributes["nodeId"];
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
//						sysManage.menuManage.stuPanel.mainGrid.getStore()
//								.load();
//						//调用关闭按钮
//						sysManage.menuManage.entry.closeHandler();
//					}
//				});
	} else {
		Ext.MessageBox.alert("提示", str);
	}
}

/**
 * 关闭按钮事件
 */
sysManage.menuManage.entry.closeHandler = function() {
	sysManage.menuManage.entry.win.close();
}

