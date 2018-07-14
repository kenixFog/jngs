Ext.namespace("dfwdsj.tjfx.jkxxtj.tablePanel");

dfwdsj.tjfx.jkxxtj.tablePanel.openTab = function(tabId, tabTitle, tab, config) {
	
	var _tab = mainTab.getComponent('tab' + tabId);
	if (!_tab) {
		mainTab.setLoading('Loading...');
		_tab = Ext.create('Ext.panel.Panel', {
			closable : true,
			id : 'tab' + tabId,
			title : '图表概览',
			layout : 'fit',
			autoScroll : true,
			border : false,
			items : typeof (tab) == 'string' ? Ext.create('Forestry.app.' + tab, config) : tab
		});
		mainTab.add(_tab);
		mainTab.setLoading(false);
	}
	mainTab.setActiveTab(_tab);
}