Ext.namespace("dfwdsj.jkxx.jbxxwh.main");

dfwdsj.jkxx.jbxxwh.main.initMainPanel = function(){  
	//定义面板
	var mainPanel = Ext.create('Ext.Panel',{
		layout : 'border',                //布局类型 
		border : true,                    //隐藏边框
		tbar :{
			cls:'whjn-tbar',
			items:[{             
				bizCode : "ADD",  //按钮的编码
				text:'新增',
	  			iconCls : 'fa fa-plus fa-lg',                  //一个样式类，提供在按钮上显示的图标
				handler : dfwdsj.jkxx.jbxxwh.main.add
			},{
				bizCode : "EDIT",
				text:'编辑',
				iconCls : 'fa fa-pencil fa-lg',
	  			handler : dfwdsj.jkxx.jbxxwh.main.edit
			}, 
//			{
//				bizCode : "DELE",
//				text:'删除',
//				iconCls : 'fa fa-trash-o fa-lg', 
//				handler : dfwdsj.jkxx.jbxxwh.main.del
//			},
			{
				bizCode : "EDITDETAIL",
				text:'维护小层信息',
				iconCls : 'fa fa-pencil fa-lg',
	  			handler : dfwdsj.jkxx.jbxxwh.main.editDetail
			}]
		},
		items:[             								//用来存放主面板中包含子面板的 数组，
				dfwdsj.jkxx.jbxxwh.panel.initPanel()       //数据面板   
		      ]
	});
	dfwdsj.jkxx.jbxxwh.main.mainPnl = mainPanel;
	return mainPanel;
}	
					
/**
 * 新增
 */
dfwdsj.jkxx.jbxxwh.main.add = function(){
	dfwdsj.jkxx.jbxxwh.entry.currObjId = -1;
	dfwdsj.jkxx.jbxxwh.entry.showWin("新增");
}


/**
 * 编辑
 */
dfwdsj.jkxx.jbxxwh.main.edit = function(){
	//菜单面板
	var gridPnl = dfwdsj.jkxx.jbxxwh.panel.gridPnl;
	//定义命名空间
	var className = dfwdsj.jkxx.jbxxwh.entry;
	// 判断选择的记录条数
	var rec = dfwdsj.jkxx.jbxxwh.main.getSelected(gridPnl);
	if (rec) {
		if(rec[0].data.isEdit==0){//为0，不可编辑
			Ext.MessageBox.alert('提示', "该记录不可编辑");
		} else {
			//获取菜单Id
			className.currObjId = rec[0].data.id;
			//调用编辑二级页面
			dfwdsj.jkxx.jbxxwh.entry.showWin("编辑");
		}
	}
}


/**
 * 维护小层信息
 */
dfwdsj.jkxx.jbxxwh.main.editDetail = function(){
	//菜单面板
	var gridPnl = dfwdsj.jkxx.jbxxwh.panel.gridPnl;
	//定义命名空间
	var className = dfwdsj.jkxx.jbxxwh.entry;
	// 判断选择的记录条数
	var rec = dfwdsj.jkxx.jbxxwh.main.getSelected(gridPnl);
	if (rec) {
		if(rec[0].data.isEdit==0){//为0，不可编辑
			Ext.MessageBox.alert('提示', "该记录不可编辑");
		} else {
			//获取菜单Id
			className.currObjId = rec[0].data.id;
			dfwdsj.jkxx.jbxxwh.oilDetailWin.showWin(className.currObjId);
		}
	}
}

/**
 * 删除油井信息
 */
dfwdsj.jkxx.jbxxwh.main.del = function(){
	var className = dfwdsj.jkxx.jbxxwh.panel;
	var sm = className.gridPnl.getSelectionModel();
	if (sm.getCount() == 0) {//如果没有选中
		whjn.dlg.infoTip("请选择需要删除的记录!");
		return false;
	} else {
		var delURL = webContextRoot + '/dfwdsj/oilWell/delOilWellByIds';
		Ext.MessageBox.confirm('确认', '您确定要删除选择信息吗？',
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
									var className = dfwdsj.jkxx.jbxxwh.panel;
									//重新加载列表数据
									className.loadRecord();
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
dfwdsj.jkxx.jbxxwh.main.getSelected = function(grid){
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

