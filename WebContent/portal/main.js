Ext.namespace("portal.main");

/*portal.maintokenDelimiter = ':';*/

Ext.onReady(function() {
	/*Ext.QuickTips.init();
	Ext.History.init();*/
	
	var viewport = Ext.create('Ext.Viewport', {
		layout : 'border',
		items : [ {
			region : 'north',
			height : 75,
			items : [ {
				html : '<img src = "' + webContextRoot + '/resources/img/portal/logo.png" width="278" height="70" />',
				width : 278
			}]
		}, 
		portal.tree.createTreePanel(), 
		portal.tablePanle.createMainTabPanel(),
		{
			region : 'south',
			border : false,
			items : [ Ext.create('Ext.ux.statusbar.StatusBar', {
				border : false,
				style : 'background:#3892D3;',
				defaults : {
					style : 'color:#000;font-weight:900;font-size:14px'
				},
				items : ['Copyright © 2018  西安物华巨能爆破器材有限责任公司', '->']
			}) ]
		} ]
	});
	return viewport;
});