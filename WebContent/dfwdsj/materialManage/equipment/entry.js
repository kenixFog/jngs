Ext.namespace("dfwdsj.materialManage.equipment.entry");

dfwdsj.materialManage.equipment.entry.fieldInfo=null;

dfwdsj.materialManage.equipment.entry.img="/resources/img/qcIcon/default.png";

dfwdsj.materialManage.equipment.entry.imgId = -1;

dfwdsj.materialManage.equipment.entry.objId = -1;
/**
 * 对菜单窗口的显示方式进行控制
 */
dfwdsj.materialManage.equipment.entry.showWin = function(titleText) {
	//命名空间
	var className = dfwdsj.materialManage.equipment.entry;
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
dfwdsj.materialManage.equipment.entry.createWin = function(titleText) {
	
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
				handler : dfwdsj.materialManage.equipment.entry.saveHandler
			}, {
				text : whjn.constant.closeBtnText,
				iconCls : 'fa fa-times-circle fa-lg',
				bizCode : 'close',
				handler : dfwdsj.materialManage.equipment.entry.closeHandler
			} ],
		},
		items : dfwdsj.materialManage.equipment.entry.initInfoArea()
	};
	var win = new Ext.Window(winCfg);
	return win;
}		


/**
 * 初始化表单信息
 * @returns {Ext.form.FormPanel}
 */
dfwdsj.materialManage.equipment.entry.initInfoArea = function() {
	var fieldsInfo =  dfwdsj.materialManage.equipment.entry.fieldInfo;
	if(fieldsInfo.length==0){
		whjn.dlg.errTip("该类型未配置相应属性字段，请先在器材分类中维护后进行操作！");
		return;
	}
	var className = dfwdsj.materialManage.equipment.entry;
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
	for(var i = 0;i< fieldsInfo.length;i++){//编码,名称,类型,长度,默认值,是否必填
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
				allowBlank : fieldsInfo[i][5]=='否'? false :true,
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
				allowBlank : fieldsInfo[i][5]=='否'? false :true,
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
				allowBlank : fieldsInfo[i][5]=='否'? false :true,
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
                id : 'slt',  
                border : true,
                autoEl : {  
                    width : 180,  
                    height : 180, 
                    tag : 'img',  
                    src : webContextRoot + dfwdsj.materialManage.equipment.entry.img  
              }  
	        },{
	        	id : 'chosePic',
	        	xtype : 'button',
	        	hidden : dfwdsj.materialManage.equipment.entry.objId == -1 ? true : false,
				text :'选择图片',
				handler : function() {
					dfwdsj.materialManage.equipment.iconImg.uploadWin();
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
dfwdsj.materialManage.equipment.entry.setwinToolBar = function(titleText) {
	var className = dfwdsj.materialManage.equipment.entry;
	var toolBar = className.win.getDockedItems('toolbar[dock="top"]');
	var funcObjs = null;
	//根据不同操作，控制工具栏按钮
	if ("新增" == titleText || "编辑" == titleText) {
		funcObjs = [ 'save', 'close' ];
	} else {
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
dfwdsj.materialManage.equipment.entry.setwinForm = function() {
	if (dfwdsj.materialManage.equipment.entry.objId==-1) {//新增，不加载数据
		return;
	}else{//id存在，加载数据
		var fieldsInfo =  dfwdsj.materialManage.equipment.entry.fieldInfo;
		var fields = new Array();
		for(var i = 0;i< fieldsInfo.length;i++){//编码,名称,类型,长度,默认值
			fields.push(fieldsInfo[i][0]);
		}
		var className = dfwdsj.materialManage.equipment.entry;
		var formPnl = className.formPnl;
		formPnl.getForm().load({
			url : webContextRoot + '/dfwdsj/equipment/getEquipmentInfo',
			method : "post",
			params : {
				objId : className.objId,
				fields : fields
			},
			waitTitle : "提示",
			waitMsg : "正在从服务器提取数据...",
			failure : function(form, action) {
				className.win.close();
			},
			success : function(form, action) {
				var data = action.result.data;
				//图片路径
				var url = dfwdsj.materialManage.equipment.entry.img;
				if(data.sltId == -1){
					url = dfwdsj.materialManage.equipment.entry.img;
				} else {
					url = data.slt;
					sltId = data.sltId;
				}
				dfwdsj.materialManage.equipment.entry.imgId = data.sltId;
				Ext.get('slt').dom.src = appBaseUri + url;
			}
		});
	}
	
}


/**
 * 保存新增或编辑后的信息
 */
dfwdsj.materialManage.equipment.entry.saveHandler = function() {

	var fieldsInfo =  dfwdsj.materialManage.equipment.entry.fieldInfo;
	var fields=new Array();
	for(var i = 0;i< fieldsInfo.length;i++){//编码,名称,类型,长度,默认值
		if(fieldsInfo[i][2].indexOf('box') > -1){//包含图片字段，排除掉，单独保存
//			fieldsInfo.remove(i);
		} else {
			fields.push(fieldsInfo[i][0]);
		}
	}
	
	
	var className = dfwdsj.materialManage.equipment.entry;
	//校验表单的正确性
	var str = whjn.validateForm(className.formPnl);
	if (str == "") { //如果校验通过
		var params = {};
		//循环遍历，获取表单信息
		for ( var i = 0; i < fields.length; i++) {
			var objTmp = className.formPnl.getForm().findField(fields[i]);
			if (objTmp) {
				if (objTmp.getXType() == 'datefield') {//如果是时间，直接获取时间格式的数据类型
					params[fields[i]] = objTmp.getRawValue();
				} else {
					params[fields[i]] = objTmp.getValue();
				}
			}
		}
		params["typeId"] = dfwdsj.materialManage.equipment.tree.node.raw.id;
		params["fields"] = fields;
		Ext.Ajax.request({
			url : webContextRoot + '/dfwdsj/equipment/saveEquipment',
			params : params,
			method : "POST",
			success : function(response) {
				if (response.responseText != '') {
					var res = Ext.JSON.decode(response.responseText);
					if (res.success) {
						dfwdsj.materialManage.equipment.entry.objId = res.id;
						whjn.dlg.showMomentDlg("保存成功!");
						Ext.getCmp("chosePic").show();
						dfwdsj.materialManage.equipment.entry.setwinForm();
						//获取数据列表窗口
						var className = dfwdsj.materialManage.equipment.panel;
						//重新加载列表数据
						className.gridPnl.getStore().reload();
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
dfwdsj.materialManage.equipment.entry.closeHandler = function() {
	dfwdsj.materialManage.equipment.entry.win.close();
}

