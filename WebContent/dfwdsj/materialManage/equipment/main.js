Ext.namespace("dawdsj.materialManage.equipment.main");

dawdsj.materialManage.equipment.main.initMainPanel = function(){  
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
				handler : dawdsj.materialManage.equipment.main.add
			},{
				bizCode : "EDIT",
				text:'编辑',
				iconCls : 'fa fa-floppy-o fa-lg',
	//  					text : atom.constant.editStuBtnText,
	//  					iconCls : 'btnIconEdit',
	//  					tooltip: atom.constant.editStuBtnTip,
	  			handler : dawdsj.materialManage.equipment.main.save
			}, {
				bizCode : "DELE",
				text:'删除',
				iconCls : 'fa fa-trash-o fa-lg', 
	//  					text : atom.constant.delStuBtnText,
	//  					tooltip: atom.constant.delStuBtnTip,
				handler : dawdsj.materialManage.equipment.main.del
			}]
		},
		items:[             							
				dawdsj.materialManage.equipment.tree.initTree(),        
				dawdsj.materialManage.equipment.panel.initGridPnl()
		      ]
	});
	dawdsj.materialManage.equipment.main.mainPnl = mainPanel;
	return mainPanel;
}	

//新增
dawdsj.materialManage.equipment.main.add = function(){
	dawdsj.materialManage.equipment.entry.objId = -1;
	dawdsj.materialManage.equipment.entry.showWin("新增");
}


//保存新增或编辑的数据
dawdsj.materialManage.equipment.main.save=function(){
	var className = dawdsj.materialManage.equipment.panel;
	//点前选中的树节点
	var node = dawdsj.materialManage.equipment.tree.node;
	var url='';
	if(node.raw.leaf=='1'){
		url = '/dfwdsj/equipment/saveEquipmentField';
	} else {
		url = '/dfwdsj/equipment/saveEquipmentType';
	}
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
						var className = dawdsj.materialManage.equipment.panel;
						var store = className.gridPnl.getStore();
						store.load();
						//树面板
						var treePnl = dawdsj.materialManage.equipment.tree.qcTree;
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
dawdsj.materialManage.equipment.main.del=function(){
	
	
	
}