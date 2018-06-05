Ext.namespace("dfwdsj.materialManage.category.main");

dfwdsj.materialManage.category.main.initMainPanel = function(){  
	//定义面板
	var mainPanel = Ext.create('Ext.Panel',{
		layout : 'border',                //布局类型 
		border : true,                    //隐藏边框
		tbar :{
			cls:'whjn-tbar',
			items:[{             
				bizCode : "ADD",  //按钮的编码
				text:'新增',
	//  					text : atom.constant.addStuBtnText,         //按钮上显示的文字
	  			iconCls : 'fa fa-plus fa-lg',                  //一个样式类，提供在按钮上显示的图标
	//  					tooltip: atom.constant.addStuBtnTip,        //鼠标悬停在按钮上时的提示信息
				handler : dfwdsj.materialManage.category.main.add
			},{
				bizCode : "SAVE",
				text:'保存',
				iconCls : 'fa fa-floppy-o fa-lg',
	//  					text : atom.constant.editStuBtnText,
	//  					iconCls : 'btnIconEdit',
	//  					tooltip: atom.constant.editStuBtnTip,
	  			handler : dfwdsj.materialManage.category.main.save
			}, {
				bizCode : "DELE",
				text:'删除',
				iconCls : 'fa fa-trash-o fa-lg', 
	//  					text : atom.constant.delStuBtnText,
	//  					tooltip: atom.constant.delStuBtnTip,
				handler : dfwdsj.materialManage.category.main.del
			}]
		},
		items:[             							
				dfwdsj.materialManage.category.tree.initTree(),        
				dfwdsj.materialManage.category.panel.initGridPnl()
		      ]
	});
	dfwdsj.materialManage.category.main.mainPnl = mainPanel;
	return mainPanel;
}	

//新增
dfwdsj.materialManage.category.main.add = function(){
	var className = dfwdsj.materialManage.category.panel;
	var node = dfwdsj.materialManage.category.tree.node;
	var data = [];
	if(node.raw.leaf=='1'){//叶子节点，分类属性字段
		data = {
				id : '',
				typeId:'',
				fieldName:'',
				fieldCode:'',
				fieldType:'',
				fieldLength:'',
				fieldContent:''
			};
	} else{//非叶子节点，分类字段
		data = {
				id : '',
				parentId:'',
				typeName:'',
				typeCode:'',
				isLeaf:''
			};
	}
	
	var rec = new model(data);
	
	className.editPlugin.cancelEdit();
	var index = 0;
	
	className.gridPnl.getStore().insert(0, rec);
	
	className.editPlugin.startEditByPosition({
        row: 0,
        column: 1
    });
}


//保存新增或编辑的数据
dfwdsj.materialManage.category.main.save=function(){
	var className = dfwdsj.materialManage.category.panel;
	//点前选中的树节点
	var node = dfwdsj.materialManage.category.tree.node;
	var url='';
	if(node.raw.leaf=='1'){
		url = '/dfwdsj/equipment/saveEquipmentField';
	} else {
		url = '/dfwdsj/equipment/saveEquipmentType';
	}
	var modifiedItems = className.gridPnl.getStore().getModifiedRecords();
	var resultData = dfwdsj.materialManage.category.main.convertRecordsToJson(modifiedItems);
	if(modifiedItems.length != 0){
		Ext.Ajax.request({
			url : webContextRoot + url,
			params : {
				paramArray:resultData,
				nodeId : node.raw.id
			},
			method : "POST",
			success : function(response) {
				if (response.responseText != '') {
					var res = Ext.JSON.decode(response.responseText);
					if (res.success) {
						whjn.dlg.showMomentDlg("保存成功!");
						var className = dfwdsj.materialManage.category.panel;
						var store = className.gridPnl.getStore();
						store.load();
						//树面板
						var treePnl = dfwdsj.materialManage.category.tree.qcTree;
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
				whjn.dlg.errTip('操作失败，请联系管理员！');
			}
		});
	} else {
		whjn.dlg.warnTip('请确认新增或修改的参数有效！');
	}
}

//删除已选中的数据
dfwdsj.materialManage.category.main.del=function(){
	var className = dfwdsj.materialManage.category.panel;
	var sm = className.gridPnl.getSelectionModel();
	if (sm.getCount() == 0) {//如果没有选中
		whjn.dlg.infoTip("请选择需要删除的记录!");
		return false;
	} else {
		//点前选中的树节点
		var node = dfwdsj.materialManage.category.tree.node;
		var ul='';
		var msg = '您确定要删除选择的这些记录么？';
		if(node.raw.leaf=='1'){
			ul = '/dfwdsj/equipment/delEquipmentField';
			msg = '删除该字段后，该器材对应的属性数据将被删除，确定要删除所选记录？';
		} else {
			ul = '/dfwdsj/equipment/delEquipmentType';
		}
		
		var delURL = webContextRoot + ul;
		Ext.MessageBox.confirm('确认', msg,
			function(btn) {
				//根据选择不同按钮进行操作
				if (btn == 'yes') {//点击确定
					//用来保存需要删除数据的ID
					var data = [];
					var sel = sm.getSelection();
					for (var i = 0, r; r = sel[i]; i++) {
						if(node.raw.leaf=='1'){
							if(r.get("fieldCode")=="ID"||r.get("fieldCode")=="name"||
									r.get("fieldCode")=="code"){
								whjn.dlg.errTip("ID,name,code,slt不允许删除！");
								return;
							}else{
								data.push(r.get("fieldCode"));
							}
						} else {
							data.push(r.get("id"));
						}
					}
					Ext.Ajax.request({
						url : delURL,
						params : {
							data : data.join(','),
							typeId : node.raw.id
						},
						success : function(response) {
							if (response.responseText != '') {
								var res = Ext.JSON.decode(response.responseText);
								if (res.success) {
									whjn.dlg.showMomentDlg("删除成功!");
									if(node.raw.leaf=='0'){
										treePnl = dfwdsj.materialManage.category.tree.qcTree;
										//刷新当前的树节点
										whjn.refreshTreePnl(treePnl, node.raw.id);
									} 
									//重新加载列表数据
									className.gridPnl.getStore().reload();
									//树面板
								} else {
									whjn.dlg.errTip(res.message);
								}
							}
						}
					});
				}
		});
	}
	
}

//行记录转为json字符串
dfwdsj.materialManage.category.main.convertRecordsToJson = function(items){
	if (items.length == 0) {
        return '[]';
    }
    var jsonData = "[";
    
    for(var i = 0; i < items.length; i++) {
        var record = items[i];
        jsonData += Ext.JSON.encode(record.data) + ",";
    }
    jsonData = jsonData.substring(0, jsonData.length - 1) + "]";
    return jsonData;
}