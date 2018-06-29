Ext.namespace("dfwdsj.jkxx.jbxxwh.entry");

//弹出二级窗口
dfwdsj.jkxx.jbxxwh.entry.win = null;
//标识是新增还是(编辑和查看）
dfwdsj.jkxx.jbxxwh.entry.currObjId = -1;
//射孔枪Id
dfwdsj.jkxx.jbxxwh.entry.skqLxId = -1;
//射孔弹Id
dfwdsj.jkxx.jbxxwh.entry.skdId = -1;
/**
 * 对菜单窗口的显示方式进行控制
 */
dfwdsj.jkxx.jbxxwh.entry.showWin = function(titleText) {
	//命名空间
	var className = dfwdsj.jkxx.jbxxwh.entry;
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
dfwdsj.jkxx.jbxxwh.entry.createWin = function(titleText) {
	var winCfg = {
		layout : 'fit',   	  // 布局样式
		width : 1200,	
		autoHeight : true, 
		title : '基本信息'+'-'+titleText,
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
				handler : dfwdsj.jkxx.jbxxwh.entry.saveHandler
			},
//			{
//				text : whjn.constant.editDetaiBtnText,
//				iconCls : 'fa fa-times-circle fa-lg',
//				bizCode : 'close',
//				handler : dfwdsj.jkxx.jbxxwh.entry.editDetaiHandler
//			},
			{
				text : whjn.constant.closeBtnText,
				iconCls : 'fa fa-times-circle fa-lg',
				bizCode : 'close',
				handler : dfwdsj.jkxx.jbxxwh.entry.closeHandler
			} ],
		},
		items : dfwdsj.jkxx.jbxxwh.entry.initInfoArea()
	};
	var win = new Ext.Window(winCfg);
	return win;
}		


/**
 * 初始化表单信息
 * @returns {Ext.form.FormPanel}
 */
dfwdsj.jkxx.jbxxwh.entry.initInfoArea = function() {
	
//	Ext.override(Ext.form.field.Base,{
//        initComponent:function(){
//            if(this.allowBlank!==undefined && !this.allowBlank){
//                if(this.fieldLabel){
//                    this.fieldLabel += '<font color=red>*</font>';
//                }
//            }
//            this.callParent(arguments);
//        }
//    });
	
	var className = dfwdsj.jkxx.jbxxwh.entry;
	var formPnl = new Ext.form.Panel({
		bodyPadding: 15,
		border : false,
		columnLines: true,
		items : [{
			xtype : "fieldset",
			autoHeight : true, 
			collapsible: true,//可折叠
            title : "基本信息",  
            layout : "form",
            fieldDefaults : {
    			labelAlign : 'right',
    			labelWidth : 100,
    			style: 'margin:7px 0 0 0'
    		},
            items : [{
            	layout : "column",
            	items:[{
                	columnWidth : 0.25, 
                    xtype:"hidden",  
                    id:"id",
                    name:'id',
                    value : dfwdsj.jkxx.jbxxwh.entry.currObjId
                },{
                	columnWidth : 0.25, 
                    xtype:"textfield",  
                    fieldLabel:"井名", 
                    name:'jm',
                    allowBlank:false 
                },{
                	columnWidth : 0.25, 
                    xtype:"textfield",  
                    fieldLabel:"油田", 
                    name:'yt',
                    allowBlank:false 
                },{
                	columnWidth : 0.25, 
                    xtype:"textfield",
                    fieldLabel:"区块", 
                    name:'qk',
                    allowBlank:false 
                },{
                	columnWidth : 0.25, 
                	xtype:"numberfield",  
                    fieldLabel:"射孔段数", 
                    minValue:1,
                    name:'skds', 
                    allowBlank:false 
                },{
                	columnWidth : 0.25, 
                    fieldLabel:"最大井斜",
                    xtype:"numberfield", 
                    allowDecimals: true,
                    decimalPrecision: 2,
                    minValue:1,
                    name:'zdjx',
                    allowBlank:false 
                },{
                	columnWidth : 0.25, 
                    xtype:"textfield", 
                    fieldLabel:"套管钢级", 
                    name:'tggj',
                    allowBlank:false 
                },{
                	columnWidth : 0.25, 
                	xtype:"numberfield", 
                    allowDecimals: true,
                    decimalPrecision: 2,
                    minValue:1,
                    fieldLabel:"套管规格(mm)", 
                    name:'tggg',
                    allowBlank:false 
                },{
                	columnWidth : 0.25, 
                    xtype:"combobox", 
                    emptyText : '请选择...',
                    displayField : 'TEXT',
        			valueField : 'VALUE',
        			store : dfwdsj.jkxx.jbxxwh.WJFS,
        			editable : false,
                    fieldLabel:"完井方式", 
                    name:'wjfs',
                    allowBlank:false 
                },{
                	columnWidth : 0.25, 
                    xtype:"combobox", 
                    emptyText : '请选择...',
                    displayField : 'TEXT',
        			valueField : 'VALUE',
        			store : dfwdsj.jkxx.jbxxwh.CSFS,
        			editable : false,
                    fieldLabel:"传输方式", 
                    name:'csfs',
                    allowBlank:false 
                },{
                	columnWidth : 0.25, 
                	xtype:"numberfield", 
                    allowDecimals: true,
                    decimalPrecision: 2,
                    minValue:1, 
                    fieldLabel:"油管规格(mm)", 
                    name:'yggg',
                    allowBlank:false 
                },{
                	columnWidth : 0.25, 
                	xtype:"numberfield", 
                    allowDecimals: true,
                    decimalPrecision: 2,
                    minValue:1,
                    fieldLabel:"试压压力(Pa)", 
                    name:'syyl', 
                    allowBlank:false 
                },{
                	columnWidth : 0.25, 
                    xtype:"combobox", 
                    emptyText : '请选择...',
                    displayField : 'TEXT',
        			valueField : 'VALUE',
        			store : dfwdsj.jkxx.jbxxwh.TCFS,
        			editable : false,
                    fieldLabel:"投产方式", 
                    name:'tcfs', 
                    allowBlank:false 
                },{
                	columnWidth : 0.25, 
                    xtype:"textfield", 
                    fieldLabel:"诱喷措施", 
                    name:'ypcs',
                    allowBlank:false 
                },{
                	columnWidth : 0.25, 
                    xtype:"datefield", 
                    fieldLabel:"固井时间", 
                    format : 'Y-m-d',
//                    minValue : new Date(),
                    name:'gjsj', 
                    allowBlank:false 
                },{
                	columnWidth : 0.25, 
                    xtype:"textfield", 
                    fieldLabel:"水泥返深(m)", 
                    name:'snfs', 
                    allowBlank:false 
                },{
                	columnWidth : 0.25, 
                    xtype:"textfield", 
                    fieldLabel:'固井质量', 
                    name:'gjzl', 
                    allowBlank:false 
                },{
                	columnWidth : 0.25, 
                	xtype:"numberfield", 
                    allowDecimals: true,
                    decimalPrecision: 2,
                    minValue:1,
                    fieldLabel:'试压时间(分)',
                    name:'sysj', 
                    allowBlank:false 
                },{
                	columnWidth : 0.25, 
                    xtype:"textfield", 
                    fieldLabel:'套管程序', 
                    name:'tgcx', 
                    allowBlank:false 
                },{
                	columnWidth : 0.25, 
                	xtype:"numberfield", 
                    allowDecimals: true,
                    decimalPrecision: 2,
                    minValue:1,
                    fieldLabel: '井斜方位角(°)', 
                    name:'jxfwj', 
                    allowBlank:false 
                },{
                	columnWidth : 0.25, 
                    xtype:"textfield", 
                    fieldLabel: '射孔方位', 
                    name:'skfw', 
                    allowBlank:false 
                },{
                	columnWidth : 0.25, 
                	xtype:"combobox", 
                    emptyText : '请选择...',
                    displayField : 'TEXT',
        			valueField : 'VALUE',
        			store : dfwdsj.jkxx.jbxxwh.DXFS,
        			editable : false,
                    fieldLabel:'定向方式', 
                    name:'dxfs', 
                    allowBlank:false 
                },{
                	columnWidth : 0.25, 
                    xtype:"datefield", 
                    style: 'margin:7px 0 8px 0',
                    fieldLabel:'创建日期', 
                    format : 'Y-m-d H:i:s',
                    editable : false,
                    readOnly:true,
                    value : new Date(),
                    name:'cjrq'
                }]
            }]
		},{
			xtype : "fieldset",
			autoHeight : true, 
			collapsible: true,//可折叠
            title : "完井液信息",  
            layout : "form",
            fieldDefaults : {
    			labelAlign : 'right',
    			labelWidth : 100,
    			style: 'margin:7px 0 0 0px'
    		},
            items : [{
            	layout : "column",
            	items:[{
                	columnWidth : 0.25, 
                	xtype:"combobox", 
                    emptyText : '请选择...',
                    displayField : 'TEXT',
        			valueField : 'VALUE',
        			store : dfwdsj.jkxx.jbxxwh.WJYLX,
        			editable : false,
                    fieldLabel:'类型', 
                    name:'wjylx', 
                    allowBlank:false 
                },{
                	columnWidth : 0.25, 
                	xtype:"numberfield", 
                    allowDecimals: true,
                    decimalPrecision: 2,
                    minValue:1, 
                    fieldLabel:'密度(g/ml)', 
                    name:'wjymd', 
                    allowBlank:false 
                },{
                	columnWidth : 0.25, 
                	xtype:"numberfield", 
                    allowDecimals: true,
                    decimalPrecision: 2,
                    minValue:1,
                    fieldLabel:'防膨率(%)', 
                    name:'fpl', 
                    allowBlank:false 
                },{
                	columnWidth : 0.25, 
                	xtype:"numberfield", 
                    allowDecimals: true,
                    decimalPrecision: 2,
                    minValue:1, 
                    fieldLabel: '液量(t)', 
                    name:'yl', 
                    allowBlank:false 
                },{
                	columnWidth : 0.25, 
                    xtype:"textfield", 
                    fieldLabel:'PH值', 
                    name:'ph', 
                    style: 'margin:7px 0 8px 0px',
                    allowBlank:false 
                }]
            }]
		},{
			xtype : "fieldset",
			autoHeight : true, 
			collapsible: true,//可折叠
            title : "射孔参数",  
            layout : "form",
            fieldDefaults : {
            	labelAlign : 'right',
    			labelWidth : 100,
    			style: 'margin:7px 0 0 0px'
    		},
            items : [{
            	layout : "column",
            	items:[{
                	columnWidth : 0.25, 
                	xtype:"combobox", 
                    emptyText : '请选择...',
                    displayField : 'TEXT',
        			valueField : 'VALUE',
        			store : dfwdsj.jkxx.jbxxwh.BKFS,
        			editable : false,
                    fieldLabel:'布孔方式', 
                    name:'bkfs', 
                    allowBlank:false 
                },{
                	columnWidth : 0.25, 
                	xtype:"combobox", 
                    emptyText : '请选择...',
                    displayField : 'TEXT',
        			valueField : 'VALUE',
        			store : dfwdsj.jkxx.jbxxwh.XW,
        			editable : false,
                    fieldLabel:'相位', 
                    name:'skqxw',  
                    allowBlank:false 
                },{
                	columnWidth : 0.25, 
                	xtype:"combobox", 
                    emptyText : '请选择...',
                    displayField : 'TEXT',
        			valueField : 'VALUE',
        			store : dfwdsj.jkxx.jbxxwh.KM,
        			editable : false,
                    fieldLabel:'孔密',  
                    name:'km',  
                    allowBlank:false 
                },{
                	columnWidth : 0.25, 
                	xtype:"numberfield", 
                    allowDecimals: true,
                    decimalPrecision: 2,
                    minValue:1,
                    fieldLabel: '射角(°)', 
                    name:'sj', 
                    allowBlank:false 
                },{
                	columnWidth : 0.25, 
                	xtype : "btntextfield",
                    fieldLabel:'射孔枪', 
                    name:'skqlx.id',
                    editable : false,
                    style: 'margin:7px 0 8px 0px',
                    allowBlank:false,
                    listeners : {
    					btnclick : function() {
    						dfwdsj.jkxx.jbxxwh.skqTree.showWin(this);
    					}
    				}
                },{
                	columnWidth : 0.25, 
                    xtype : "btntextfield",
                    fieldLabel:'射孔弹', 
                    style: 'margin:7px 0 8px 0px',
                    editable : false,
                    name:'skd.propertyValue', 
                    allowBlank:false,
                    listeners : {
    					btnclick : function() {
    						dfwdsj.jkxx.jbxxwh.skdTree.showWin(this);
    					}
    				}
                }]
            }]
		},{
			xtype : "fieldset",
			autoHeight : true, 
			collapsible: true,//可折叠
            title : "产值预测和结果",  
            layout : "form",
            fieldDefaults : {
            	labelAlign : 'right',
    			labelWidth : 100,
    			style: 'margin:7px 0 8px 0px'
    		},
            items : [{
            	layout : "column",
            	items:[{
                	columnWidth : 0.25, 
                	xtype:"numberfield",
                	allowDecimals: true,
                    //这里允许保留3位小数，所以你输入11.996就不会进位了
                    decimalPrecision: 4,
                    minValue:1,
                    fieldLabel:'预测产液(t)',  
                    name:'yccy'
                },{
                	columnWidth : 0.25, 
                    xtype:"numberfield", 
                    allowDecimals: true,
                    //这里允许保留3位小数，所以你输入11.996就不会进位了
                    decimalPrecision: 4,
                    minValue:1,
                    fieldLabel: '预测产油(t)', 
                    name:'ycco'
                },{
                	columnWidth : 0.25, 
                    xtype:"numberfield", 
                    allowDecimals: true,
                    //这里允许保留3位小数，所以你输入11.996就不会进位了
                    decimalPrecision: 4,
                    minValue:1,
                    fieldLabel:'实际产液(t)', 
                    name:'sjcy'
                },{
                	columnWidth : 0.25, 
                    xtype:"numberfield", 
                    allowDecimals: true,
                    //这里允许保留3位小数，所以你输入11.996就不会进位了
                    decimalPrecision: 4,
                    minValue:1,
                    fieldLabel:'实际产油(t)', 
                    name:'sjco'
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
dfwdsj.jkxx.jbxxwh.entry.setwinToolBar = function(titleText) {
	var className = dfwdsj.jkxx.jbxxwh.entry;
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
dfwdsj.jkxx.jbxxwh.entry.setwinForm = function(titleText) {
	var className = dfwdsj.jkxx.jbxxwh.entry;
	var formPnl = className.formPnl;
	if ("编辑" == titleText) {
		formPnl.getForm().load({
			url :  webContextRoot + '/dfwdsj/oilWell/getOilWellInfo',
			method : "post",
			params : {
				// 菜单Id
				id : className.currObjId
			},
			waitTitle : "提示",
			waitMsg : "正在从服务器提取数据...",
			failure : function(form, action) {
				className.win.close();
			},
			success : function(form, action) {
				var data = action.result.data;
				var cjrq = data.cjrq.substr(0,data.cjrq.lastIndexOf("."));
				var gjsj = data.gjsj.substr(0,data.gjsj.lastIndexOf(" "));
				formPnl.getForm().findField("cjrq").setValue(cjrq);
				formPnl.getForm().findField("gjsj").setValue(gjsj);
				//枪类型ID
				dfwdsj.jkxx.jbxxwh.entry.skqLxId = data.skqlx.id;
				formPnl.getForm().findField("skqlx.id").setValue(data.skqlx.typeName);
				//弹ID
				dfwdsj.jkxx.jbxxwh.entry.skdId = data.skd.qcId;
				formPnl.getForm().findField("skd.propertyValue").setValue(data.skd.propertyValue);
			}
		});
	} else { //新增
	}
	
}


/**
 * 保存新增或编辑后的信息
 */
dfwdsj.jkxx.jbxxwh.entry.saveHandler = function() {
	var className = dfwdsj.jkxx.jbxxwh.entry;
	//校验表单的正确性
	var str = whjn.validateForm(className.formPnl);
	if (str == "") { //如果校验通过
		var params = {};
		var qryNames = [
			'id',
			'jm',
			'yt',
			'qk',
			'skds',
			'zdjx',
			'tggj',
			'tggg',
			'wjfs',
			'csfs',
			'yggg',
			'syyl',
			'tcfs',
			'ypcs',
			'gjsj',
			'snfs',
			'gjzl',
			'sysj',
			'tgcx',
			'jxfwj',
			'skfw',
			'dxfs',
			'cjrq',
//			'user',
//			'org',
//			'state',
			'wjylx',
			'wjymd',
			'fpl',
			'yl',
			'ph',
			'bkfs',
			'skqxw',
			'km',
			'sj',
			'skqlx.id',
			'skd.propertyValue',
			'yccy',
			'ycco',
			'sjcy',
			'sjco'
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
		params['skqlx.id'] = dfwdsj.jkxx.jbxxwh.entry.skqLxId;
		params['skd.propertyValue'] = dfwdsj.jkxx.jbxxwh.entry.skdId;
		Ext.Ajax.request({
			url : webContextRoot + '/dfwdsj/oilWell/saveOilWellInfo',
			params : params,
			method : "POST",
			success : function(response) {
				if (response.responseText != '') {
					var res = Ext.JSON.decode(response.responseText);
					if (res.success) {
						whjn.dlg.showMomentDlg("保存成功!");
//						dfwdsj.jkxx.jbxxwh.entry.closeHandler();
						//获取数据列表窗口
						var className = dfwdsj.jkxx.jbxxwh.panel;
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
 * 关闭按钮事件
 */
dfwdsj.jkxx.jbxxwh.entry.closeHandler = function() {
	dfwdsj.jkxx.jbxxwh.entry.win.close();
}

