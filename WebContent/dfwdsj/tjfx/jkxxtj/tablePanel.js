Ext.namespace("dfwdsj.tjfx.jkxxtj.tablePanel");

dfwdsj.tjfx.jkxxtj.tablePanel.initPanel = function() {
	var tPanel = Ext.create('Ext.TabPanel', {
		region : 'center',
		margins : '2 0 0 0',
		deferredRender : false,
		activeTab : 0,
		items : [
//			dfwdsj.jkxx.jbxxwh.totalPanel.initPnl() 
		]
	});
	dfwdsj.tjfx.jkxxtj.tablePanel.tPanel = tPanel;
	return tPanel;
}
