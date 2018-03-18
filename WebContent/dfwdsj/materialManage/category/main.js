Ext.namespace("dawdsj.materialManage.category.main");

dawdsj.materialManage.category.main.initMainPanel = function(){  
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
				handler : dawdsj.materialManage.category.main.add
			},{
				bizCode : "EDIT",
				text:'保存',
				iconCls : 'fa fa-floppy-o fa-lg',
	//  					text : atom.constant.editStuBtnText,
	//  					iconCls : 'btnIconEdit',
	//  					tooltip: atom.constant.editStuBtnTip,
	  			handler : dawdsj.materialManage.category.main.save
			}, {
				bizCode : "DELE",
				text:'删除',
				iconCls : 'fa fa-trash-o fa-lg', 
	//  					text : atom.constant.delStuBtnText,
	//  					tooltip: atom.constant.delStuBtnTip,
				handler : dawdsj.materialManage.category.main.del
			}]
		},
		items:[             							
				dawdsj.materialManage.category.tree.initTree(),        
				dawdsj.materialManage.category.panel.initGridPnl()
		      ]
	});
	dawdsj.materialManage.category.main.mainPnl = mainPanel;
	return mainPanel;
}	

//新增
dawdsj.materialManage.category.main.add = function(){
	var className = dawdsj.materialManage.category.panel;
	var node = dawdsj.materialManage.category.tree.node;
	var data = [];
	if(node.raw.leaf=='1'){//叶子节点，分类属性字段
		data = {
				id : '',
				typeId: node.raw.id,
				fieldName:'',
				fieldCode:'',
				fieldType:'',
				fieldLength:''
			};
	} else{//非叶子节点，分类字段
		data = {
				id : '',
				parentId: node.raw.id,
				typeName:'',
				typeCode:'',
				isLeaf:'',
				createTime:'',
				lastEditTime:''
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
dawdsj.materialManage.category.main.save=function(){
	var className = dawdsj.materialManage.category.panel;
	//点前选中的树节点
	var node = dawdsj.materialManage.category.tree.node;
	var url='';
	if(node.raw.leaf=='1'){
		url = '/dfwdsj/equipment/saveEquipmentField';
	} else {
		url = '/dfwdsj/equipment/saveEquipmentType';
	}
	var modifiedItems = className.gridPnl.getStore().getModifiedRecords();
	var resultData = dawdsj.materialManage.category.main.convertRecordsToJson(modifiedItems);
	if(modifiedItems.length != 0){
		Ext.Ajax.request({
			url : webContextRoot + url,
			params : {
				paramArray:resultData
			},
			method : "POST",
			success : function(response) {
				if (response.responseText != '') {
					var res = Ext.JSON.decode(response.responseText);
					if (res.success) {
						whjn.dlg.showMomentDlg("保存成功!");
						var className = dawdsj.materialManage.category.panel;
						var store = className.gridPnl.getStore();
						store.load();
						//树面板
						var treePnl = dawdsj.materialManage.category.tree.qcTree;
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
		Ext.MessageBox.alert("提示", str);
	}
}

//删除已选中的数据
dawdsj.materialManage.category.main.del=function(){
	
	
}

//行记录转为json字符串
dawdsj.materialManage.category.main.convertRecordsToJson = function(items){
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