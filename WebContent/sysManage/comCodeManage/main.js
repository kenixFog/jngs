Ext.namespace("sysManage.comCodeManage.main");

sysManage.comCodeManage.main.initMainPanel = function(){  
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
				handler : sysManage.comCodeManage.main.add
			}, {
				bizCode : "EDIT",
				text:'编辑',
				iconCls : 'fa fa-pencil fa-lg',
	//  					text : atom.constant.editStuBtnText,
	//  					iconCls : 'btnIconEdit',
	//  					tooltip: atom.constant.editStuBtnTip,
	  			handler : sysManage.comCodeManage.main.edit
			}, {
				bizCode : "DELE",
				text:'删除',
				iconCls : 'fa fa-trash-o fa-lg', 
	//  					text : atom.constant.delStuBtnText,
	//  					tooltip: atom.constant.delStuBtnTip,
				handler : sysManage.comCodeManage.main.del
			},{             
				bizCode : "BTNTEST",  //按钮的编码
				text:'测试',
	//  					text : atom.constant.addStuBtnText,         //按钮上显示的文字
	//  					iconCls : 'btnIconAdd',                     //一个样式类，提供在按钮上显示的图标
	//  					tooltip: atom.constant.addStuBtnTip,        //鼠标悬停在按钮上时的提示信息
				handler : sysManage.comCodeManage.main.test
			}]
		},
		items:[             								//用来存放主面板中包含子面板的 数组，
				sysManage.comCodeManage.tree.initTree(),        //课程树面板
				sysManage.comCodeManage.panel.initPanel()       //包含双列表的面板   
		      ]
	});
	sysManage.comCodeManage.main.mainPnl = mainPanel;
	return mainPanel;
}	
					

sysManage.comCodeManage.main.add = function(){
	sysManage.comCodeManage.entry.showWin("新增");
}


sysManage.comCodeManage.main.edit = function(){
	//菜单面板
	var comCodeGridPnl = sysManage.comCodeManage.panel.comCodeGridPnl;
	//定义命名空间
	var className = sysManage.comCodeManage.entry;
	// 判断选择的记录条数
	var rec = sysManage.comCodeManage.main.getSelected(comCodeGridPnl);
	if (rec) {
		if(rec[0].data.isEdit==0){//为0，不可编辑
			Ext.MessageBox.alert('提示', "该记录不可编辑");
		} else {
			//获取菜单Id
			className.currObjId = rec[0].data.id;
			//调用编辑二级页面
			sysManage.comCodeManage.entry.showWin("编辑");
		}
	}
}

sysManage.comCodeManage.main.del = function(){
//	//定义命名空间
//	var className = sysManage.comCodeManage.panel.comCodeGridPnl;
//	//判断菜单信息是否被选中
//	if (className.getSelectionModel().getCount() == 0) {//如果没有选中
//		Ext.MessageBox.alert('提示', "请选择需要删除的记录!");
//		return false;
//	} else {//记录被选中
//		var delURL = atom.webContextRoot+ '/treeAndPanelsAction.do?method=delStuRecord';
//		Ext.MessageBox.confirm('确认', '您确定要删除选择的这些菜单吗？',
//			//回调函数
//			function(btn) {
//		        //根据选择不同按钮进行操作
//				if (btn == 'yes') {//点击确定
//					//用来保存删除的菜单ID
//					var delDataId = '';
//					//循环遍历，拼接学生ID字符串
//					for ( var i = 0; i < className.sm.getCount(); i++) {
//						if (i == 0)//第一个只需要直接存入
//							delDataId = className.sm.getSelections()[i].data.id;
//						else//第二个之后，拼装逗号
//							delDataId += ","+ className.sm.getSelections()[i].data.id;
//					}
//					// 通过Action提交请求
//					atom.getHiddenMainForm().submit({
//						url : delURL,	//请求路径
//						params : {      //传入后台的参数
//							delIds : delDataId  //学生ID  
//						},
//						waitTitle : '提示',
//						waitMsg : '正在向服务器提交数据...',
//						reset : false,
//						success : function(form, action) { //返回成功进行的操作
//							//提示
//							atom.dlg.showMomentDlg("操作成功");
//							//清空家长信息面板中学生学号
//							templates.stuMaintain.familyPanel.qryPnl.find("name", "stuId")[0].setValue("");
//							//定义命名空间
//							var className = templates.stuMaintain.stuPanel;
//							//重新加载数据，刷新学生面板
//							className.mainGrid.getStore().reload({
//								params : {
//									start : 0
//								}
//							});
//						}
//					});
//				}
//		});
//	}
}

sysManage.comCodeManage.main.test = function(){
	alert('test');
}
	
/**
 * 判断选择的信息是否单选
 * @returns {记录(rec)}
 */
sysManage.comCodeManage.main.getSelected = function(grid){
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

