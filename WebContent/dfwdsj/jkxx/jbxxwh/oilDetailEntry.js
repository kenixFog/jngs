Ext.namespace("dfwdsj.jkxx.jbxxwh.oilDetailEntry");

//弹出二级窗口
dfwdsj.jkxx.jbxxwh.oilDetailEntry.win = null;

//标识是新增还是(编辑和查看）
dfwdsj.jkxx.jbxxwh.oilDetailEntry.currObjId = -1;
/**
 * 对菜单窗口的显示方式进行控制
 */
dfwdsj.jkxx.jbxxwh.oilDetailEntry.showWin = function(titleText) {
	//命名空间
	var className = dfwdsj.jkxx.jbxxwh.oilDetailEntry;
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
dfwdsj.jkxx.jbxxwh.oilDetailEntry.createWin = function(titleText) {
	var winCfg = {
		layout : 'fit',   	  // 布局样式
		width : 600,	
		autoHeight : true, 
		title : titleText,
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
				handler : dfwdsj.jkxx.jbxxwh.oilDetailEntry.saveHandler
			},  {
				text : whjn.constant.saveGoOnBtnText,
				iconCls : 'fa fa-floppy-o fa-lg',
				bizCode : 'saveAndGoOn',
				handler : dfwdsj.jkxx.jbxxwh.oilDetailEntry.saveAndGoOnHandler
			}, {
				text : whjn.constant.closeBtnText,
				iconCls : 'fa fa-times-circle fa-lg',
				bizCode : 'close',
				handler : dfwdsj.jkxx.jbxxwh.oilDetailEntry.closeHandler
			} ],
		},
		items : dfwdsj.jkxx.jbxxwh.oilDetailEntry.initInfoArea()
	};
	var win = new Ext.Window(winCfg);
	return win;
}		


/**
 * 初始化表单信息
 * @returns {Ext.form.FormPanel}
 */
dfwdsj.jkxx.jbxxwh.oilDetailEntry.initInfoArea = function() {
	
	var className = dfwdsj.jkxx.jbxxwh.oilDetailEntry;
	var formPnl = new Ext.form.Panel({
		bodyPadding: 15,
		border : false,
		columnLines: true,
		items : [{
			xtype : "fieldset",
			autoHeight : true, 
			collapsible: true,//可折叠
            title : "小层信息",  
            layout : "form",
            fieldDefaults : {
    			labelAlign : 'right',
    			labelWidth : 120,
    			style: 'margin:7px 0 0 -10px'
    		},
            items : [{
            	layout : "column",
            	items:[{
                	columnWidth : 0.5, 
                    xtype:"hidden",  
                    id:"id",
                    name:'id',
                    value : dfwdsj.jkxx.jbxxwh.oilDetailEntry.currObjId
                },{
                	columnWidth : 0.5, 
                    xtype:"textfield",  
                    fieldLabel:"层位", 
                    name:'cw',
                    allowBlank:false 
                },{
                	columnWidth : 0.5, 
                    xtype:"textfield",  
                    fieldLabel:"小层编号", 
                    name:'xcbh',
                    allowBlank:false 
                },{
                	columnWidth : 0.5, 
                	xtype:"numberfield", 
                    allowDecimals: true,
                    decimalPrecision: 2,
                    minValue:1,
                    fieldLabel:"层段起始(m)", 
                    name:'cdqs',
                    allowBlank:false 
                },{
                	columnWidth : 0.5, 
                	xtype:"numberfield", 
                    allowDecimals: true,
                    decimalPrecision: 2,
                    fieldLabel:"层段截至(m)", 
                    minValue:1,
                    name:'cdjz', 
                    allowBlank:false 
                },{
                	columnWidth : 0.5, 
                    fieldLabel:"油层倾角(°)",
                    xtype:"numberfield", 
                    allowDecimals: true,
                    decimalPrecision: 2,
                    minValue:1,
                    name:'ycqj',
                    allowBlank:false 
                },{
                	columnWidth : 0.5, 
                    xtype:"numberfield", 
                    fieldLabel:"孔数(个)", 
                    minValue:1,
                    name:'ks',
                    allowBlank:false 
                },{
                	columnWidth : 0.5, 
                	xtype:"numberfield", 
                    allowDecimals: true,
                    decimalPrecision: 2,
                    minValue:0,
                    fieldLabel:"小层压力(Pa)", 
                    name:'yl',
                    allowBlank:false 
                },{
                	columnWidth : 0.5, 
                	xtype:"numberfield", 
                    allowDecimals: true,
                    decimalPrecision: 2,
                    minValue:0,
                    fieldLabel:"地层系数", 
                    name:'xs',
                    allowBlank:false 
                },{
                	columnWidth : 0.5, 
                	xtype:"numberfield", 
                    allowDecimals: true,
                    decimalPrecision: 2,
                    minValue:0,
                    fieldLabel:"射开厚度(m)", 
                    name:'skhd'
                },{
                	columnWidth : 0.5, 
                	xtype:"numberfield", 
                    allowDecimals: true,
                    decimalPrecision: 2,
                    minValue:0, 
                    fieldLabel:"有效厚度(m)", 
                    name:'yxhd'
                },{
                	columnWidth : 0.5, 
                	xtype:"numberfield", 
                    allowDecimals: true,
                    decimalPrecision: 2,
                    minValue:1,
                    fieldLabel:"对应斜度(°)", 
                    name:'dyxd', 
                    allowBlank:false 
                },{
                	columnWidth : 0.5, 
                	xtype:"numberfield", 
                    allowDecimals: true,
                    decimalPrecision: 2,
                    minValue:0, 
                    fieldLabel:"夹层厚度(m)", 
                    name:'jchd', 
                    allowBlank:false 
                },{
                	columnWidth : 0.5, 
                	xtype:"numberfield", 
                    allowDecimals: true,
                    decimalPrecision: 2,
                    minValue:0, 
                    fieldLabel:"有效渗透率(μm2)", 
                    name:'yxstl',
                    style: 'margin:7px 0 8px -10px',
                    allowBlank:false 
                },{
                	columnWidth : 0.5, 
                	xtype:"numberfield", 
                    allowDecimals: true,
                    decimalPrecision: 2,
                    minValue:0, 
                    fieldLabel:"孔隙度(%)", 
                    name:'kxd', 
                    style: 'margin:7px 0 8px -10px',
                    allowBlank:false 
                }]
    		}]
		}]
	});
	className.formPnl = formPnl;
	return formPnl;
}

/**
 * 设置工具栏按钮
 * @param titleText
 */
dfwdsj.jkxx.jbxxwh.oilDetailEntry.setwinToolBar = function(titleText) {
	var className = dfwdsj.jkxx.jbxxwh.oilDetailEntry;
	var toolBar = className.win.getDockedItems('toolbar[dock="top"]');
	var funcObjs = null;
	//根据不同操作，控制工具栏按钮
	if ("新增" == titleText) {
		funcObjs = [ 'save', 'saveAndGoOn', 'close' ];
	}else if ( "编辑" == titleText) {
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
dfwdsj.jkxx.jbxxwh.oilDetailEntry.setwinForm = function(titleText) {
	var className = dfwdsj.jkxx.jbxxwh.oilDetailEntry;
	var formPnl = className.formPnl;
	if ("编辑" == titleText) {
		formPnl.getForm().load({
			url :  webContextRoot + '/dfwdsj/oilWell/getOilWellDetailInfo',
			method : "post",
			params : {
				// 小层Id
				id : className.currObjId
			},
			waitTitle : "提示",
			waitMsg : "正在从服务器提取数据...",
			failure : function(form, action) {
				className.win.close();
			},
			success : function(form, action) {
				
			}
		});
	} else { //新增
	}
}


/**
 * 保存新增或编辑后的信息
 */
dfwdsj.jkxx.jbxxwh.oilDetailEntry.saveHandler = function() {
	var className = dfwdsj.jkxx.jbxxwh.oilDetailEntry;
	//校验表单的正确性
	var str = whjn.validateForm(className.formPnl);
	if (str == "") { //如果校验通过
		var params = {};
		var qryNames = [
			'id',
			'cw', //层位
			'xcbh',//小层编号
			'cdqs',//层段起始
			'cdjz',//层段截至
			'ycqj', //油层倾角
			'ks',//孔数
			'yl',//小层压力
			'xs',//地层系数
			'skhd', //射开厚度
			'yxhd',//有效厚度
			'dyxd',//对应斜度
			'jchd',//夹层厚度
			'yxstl', //有效渗透率
			'kxd'	//孔隙度
];
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
		params['oilWellId'] = dfwdsj.jkxx.jbxxwh.oilDetailWin.objId;
		Ext.Ajax.request({
			url : webContextRoot + '/dfwdsj/oilWell/saveOilWellDetailInfo',
			params : params,
			method : "POST",
			success : function(response) {
				if (response.responseText != '') {
					var res = Ext.JSON.decode(response.responseText);
					if (res.success) {
						whjn.dlg.showMomentDlg("保存成功!");
						dfwdsj.jkxx.jbxxwh.oilDetailEntry.closeHandler();
						//获取数据列表窗口
						var className = dfwdsj.jkxx.jbxxwh.oilDetailWin;
						//重新加载列表数据
						className.loadRecord();
						//树面板
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
 * 保存并继续
 */
dfwdsj.jkxx.jbxxwh.oilDetailEntry.saveAndGoOnHandler = function() {
	var className = dfwdsj.jkxx.jbxxwh.oilDetailEntry;
	//校验表单的正确性
	var form = className.formPnl;
	var str = whjn.validateForm(form);
	if (str == "") { //如果校验通过
		var params = {};
		var qryNames = [
			'id',
			'cw', //层位
			'xcbh',//小层编号
			'cdqs',//层段起始
			'cdjz',//层段截至
			'ycqj', //油层倾角
			'ks',//孔数
			'yl',//小层压力
			'xs',//地层系数
			'skhd', //射开厚度
			'yxhd',//有效厚度
			'dyxd',//对应斜度
			'jchd',//夹层厚度
			'yxstl', //有效渗透率
			'kxd'	//孔隙度
];
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
		params['oilWellId'] = dfwdsj.jkxx.jbxxwh.oilDetailWin.objId;
		Ext.Ajax.request({
			url : webContextRoot + '/dfwdsj/oilWell/saveOilWellDetailInfo',
			params : params,
			method : "POST",
			success : function(response) {
				if (response.responseText != '') {
					var res = Ext.JSON.decode(response.responseText);
					if (res.success) {
						whjn.dlg.showMomentDlg("保存成功!");
						//获取数据列表窗口
						var className = dfwdsj.jkxx.jbxxwh.oilDetailWin;
						//重新加载列表数据
						className.loadRecord();
						form.getForm().reset();
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
dfwdsj.jkxx.jbxxwh.oilDetailEntry.closeHandler = function() {
	dfwdsj.jkxx.jbxxwh.oilDetailEntry.win.close();
}

