Ext.namespace("sysManage.orgManage.user.entry");

//弹出二级窗口
sysManage.orgManage.user.entry.win = null;
//标识是新增还是(编辑和查看）
sysManage.orgManage.user.entry.currObjId = -1;
//父节点Id
sysManage.orgManage.user.entry.parentId = null;	
//基准组织树
sysManage.orgManage.user.entry.orgComb = null;
//基准组织ID
sysManage.orgManage.user.entry.orgId = null;



/**
 * 对菜单窗口的显示方式进行控制
 */
sysManage.orgManage.user.entry.showWin = function(titleText) {
	//命名空间
	var className = sysManage.orgManage.user.entry;
	//当前选中树节点
	var node = sysManage.orgManage.user.tree.node;
	//获取二级弹出窗口
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
sysManage.orgManage.user.entry.createWin = function(titleText) {
	var winCfg = {
		layout : 'fit',   	  // 布局样式
		width : 400,	
		height : 330,
		title : '用户信息'+'-'+titleText,
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
				handler : sysManage.orgManage.user.entry.saveHandler
			}, {
				text : whjn.constant.closeBtnText,
				iconCls : 'fa fa-times-circle fa-lg',
				bizCode : 'close',
				handler : sysManage.orgManage.user.entry.closeHandler
			} ],
		},
		items : sysManage.orgManage.user.entry.initInfoArea()
	};
	var win = new Ext.Window(winCfg);
	return win;
}		


/**
 * 初始化表单信息
 * @returns {Ext.form.FormPanel}
 */
sysManage.orgManage.user.entry.initInfoArea = function() {
	var className = sysManage.orgManage.user.entry;
	
	var orgTreeStore = Ext.create('Ext.data.TreeStore', {   
        root: {  
        	id : -1,
            text : '基准组织树', 
            expanded : true
        },  
        proxy: {   
        	type : 'ajax',  
            url : webContextRoot +  '/sys/org/getOrgTreeByParentId',//请求  
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
	
	
	var orgComb = new Ext.create("Ext.ux.ComboBoxTree", {
        name : 'orgId',
        store : orgTreeStore,
        fieldLabel : '基准组织',
        style: 'margin-top:10px',
		editable : false,
		allowBlank : false,
		multiCascade : false,
		multiSelect : false,
		labelWidth : 70
	})
	sysManage.orgManage.user.entry.orgComb = orgComb;
	
	var formPnl = new Ext.form.Panel({
		bodyPadding: 20,
		border : false,
		fieldDefaults : {
			labelAlign : 'left',
			labelWidth : 70,
			anchor : '100%'
		},
		items : [{
			id : 'id',
//			xtype : 'hidden',
			name : 'id',
			value : sysManage.orgManage.user.entry.currObjId
		},{
			xtype : 'textfield',
			name : 'userName',
			fieldLabel : '用户名',
			readOnly : true, 
			style: 'margin-top:10px',
			allowBlank : false,
			maxLength : 50,
			maxLengthText : "最大长度不超过50个字符"
		},{
			xtype : 'textfield',
			name : 'realName',
			fieldLabel : '姓名',
			style: 'margin-top:10px',
			allowBlank : false,
			maxLength : 50,
			maxLengthText : "最大长度不超过50个字符"
		}, orgComb , {
			xtype : 'textfield',
			name : 'email',
			fieldLabel : '邮箱',
			style: 'margin-top:10px',
			allowBlank : false,
			maxLength : 50,
			maxLengthText : "最大长度不超过50个字符"
		},{
			xtype : 'textfield',
			name : 'tel',
			fieldLabel : '电话',
			style: 'margin-top:10px',
			allowBlank : false,
			maxLength : 50,
			maxLengthText : "最大长度不超过50个字符"
		},{
			xtype : 'combobox',
			name : 'statue',
			fieldLabel : '状态',
			emptyText : '请选择...',
			editable : false,
			store : sysManage.orgManage.user.statusArray,
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
sysManage.orgManage.user.entry.setwinToolBar = function(titleText) {
	var className = sysManage.orgManage.user.entry;
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
sysManage.orgManage.user.entry.setwinForm = function(titleText) {
	var className = sysManage.orgManage.user.entry;
	var formPnl = className.formPnl;
	if ("编辑" == titleText) {
		formPnl.getForm().load({
			url :  webContextRoot + '/sys/user/getUserInfo',
			method : "post",
			params : {
				// 菜单Id
				userId : className.currObjId
			},
			waitTitle : "提示",
			waitMsg : "正在从服务器提取数据...",
			failure : function(form, action) {
				className.win.close();
			},
			success : function(form, action) {
				formPnl.getForm().findField("userName").setReadOnly(true);
//				Ext.getCmp("ID").setValue(action.result.data.ID);
				whjn.searchTreePnl(sysManage.orgManage.user.entry.orgComb, action.result.data.orgId);
			}
		});
	} else { //新增
		formPnl.getForm().findField("userName").setReadOnly(false);
		//树节点
//		var parentNode = sysManage.orgManage.user.tree.node;
//		formPnl.getForm().findField("org").setValue(parentNode.raw.id);
	}
	
}


/**
 * 保存新增或编辑后的信息
 */
sysManage.orgManage.user.entry.saveHandler = function() {
	var className = sysManage.orgManage.user.entry;
	//校验表单的正确性
	var str = whjn.validateForm(className.formPnl);
	if (str == "") { //如果校验通过
		var params = {};
		var qryNames = [ "id", "userName","realName", "orgId", "email", 
			"tel","statue"];
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
			url : webContextRoot + '/sys/user/saveUserInfo',
			params : params,
			method : "POST",
			success : function(response) {
				if (response.responseText != '') {
					var res = Ext.JSON.decode(response.responseText);
					if (res.success) {
						whjn.dlg.showMomentDlg("保存成功!");
						sysManage.orgManage.user.entry.closeHandler();
						//获取数据列表窗口
						var className = sysManage.orgManage.user.panel;
						//重新加载列表数据
						className.loadRecord();
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
sysManage.orgManage.user.entry.closeHandler = function() {
	sysManage.orgManage.user.entry.win.close();
}

