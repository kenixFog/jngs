Ext.namespace("sysManage.menuManage.main");

sysManage.menuManage.main.initMainPanel = function(){  
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
				handler : sysManage.menuManage.main.add
			},{
				bizCode : "EDIT",
				text:'编辑',
				iconCls : 'fa fa-pencil fa-lg',
	//  					text : atom.constant.editStuBtnText,
	//  					iconCls : 'btnIconEdit',
	//  					tooltip: atom.constant.editStuBtnTip,
	  			handler : sysManage.menuManage.main.edit
			}, {
				bizCode : "DELE",
				text:'删除',
				iconCls : 'fa fa-trash-o fa-lg', 
	//  					text : atom.constant.delStuBtnText,
	//  					tooltip: atom.constant.delStuBtnTip,
				handler : sysManage.menuManage.main.del
			}]
		},
		items:[             								//用来存放主面板中包含子面板的 数组，
				sysManage.menuManage.tree.initTree(),        //课程树面板
				sysManage.menuManage.panel.initPanel()       //包含双列表的面板   
		      ]
	});
	sysManage.menuManage.main.mainPnl = mainPanel;
	return mainPanel;
}	
					
/**
 * 新增
 */
sysManage.menuManage.main.add = function(){
	sysManage.menuManage.entry.currObjId = -1;
	sysManage.menuManage.entry.showWin("新增");
}


/**
 * 编辑
 */
sysManage.menuManage.main.edit = function(){
	//菜单面板
	var menuGridPnl = sysManage.menuManage.panel.menuGridPnl;
	//定义命名空间
	var className = sysManage.menuManage.entry;
	// 判断选择的记录条数
	var rec = sysManage.menuManage.main.getSelected(menuGridPnl);
	if (rec) {
		if(rec[0].data.isEdit==0){//为0，不可编辑
			Ext.MessageBox.alert('提示', "该记录不可编辑");
		} else {
			//获取菜单Id
			className.currObjId = rec[0].data.id;
			//调用编辑二级页面
			sysManage.menuManage.entry.showWin("编辑");
		}
	}
}

sysManage.menuManage.main.del = function(){
	var className = sysManage.menuManage.panel;
	var sm = className.menuGridPnl.getSelectionModel();
	if (sm.getCount() == 0) {//如果没有选中
		whjn.dlg.infoTip("请选择需要删除的记录!");
		return false;
	} else {
		var delURL = webContextRoot + '/sys/menu/delMenuByIds';
		Ext.MessageBox.confirm('确认', '您确定要删除选择的这些菜单吗？',
			function(btn) {
				//根据选择不同按钮进行操作
				if (btn == 'yes') {//点击确定
					//用来保存需要删除数据的ID
					var ids = [];
					var sel = sm.getSelection();
					for (var i = 0, r; r = sel[i]; i++) {
						ids.push(r.get("id"));
					}
					Ext.Ajax.request({
						url : delURL,
						params : {
							ids : ids.join(',')
						},
						success : function(response) {
							if (response.responseText != '') {
								var res = Ext.JSON.decode(response.responseText);
								if (res.success) {
									whjn.dlg.showMomentDlg("删除成功!");
									//获取数据列表窗口
									var className = sysManage.menuManage.panel;
									//重新加载列表数据
									className.loadRecord();
									//树面板
									var treePnl = sysManage.menuManage.tree.menuTree;
									//点前选中的树节点
									var node = sysManage.menuManage.tree.node;
									//设置需要加载的树节点Id
									treePnl.getStore().proxy.extraParams.parentId = node.data.id;
									//刷新当前的树节点
									whjn.refreshTreePnl(treePnl, node.data.id);
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

	
/**
 * 判断选择的信息是否单选
 * @returns {记录(rec)}
 */
sysManage.menuManage.main.getSelected = function(grid){
	//选择条数大于一
	if (grid.getSelectionModel().getCount() > 1) {
		Ext.MessageBox.alert("提示", "一次只能选择一条记录!");
		return null;
	}
	// 没有选择
	if (grid.getSelectionModel().getCount() < 1) {
		Ext.MessageBox.alert("提示", "请选择一条记录!");
		return null;
	} else {//只选择一条
		//获取这条记录
		var rec = grid.getSelectionModel().getSelection();
		return rec;
	}
}

