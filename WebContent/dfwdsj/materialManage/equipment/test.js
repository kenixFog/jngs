Ext.namespace("dfwdsj.materialManage.equipment.test");

dfwdsj.materialManage.equipment.test.uploadWin =function(){
	var winCfg = {
			layout : 'fit',   	  // 布局样式
			width : 800,	
			height : 600,
			title : '公共代码',
			resizable : false,	 // 不允许用户允许拖动窗体边角来控制窗口大小
			autoScroll : true,   // 自动显示滚动条
			closeAction : 'destroy',// 关闭时为销毁操作，hide为隐藏操作
			modal : true,		 // 模态化显示：后方的区域不能点击和编辑
			items : sysManage.comCodeManage.entry.initInfoArea()
//			html:""
		};
		var win = new Ext.Window(winCfg);
		win.show();
}
