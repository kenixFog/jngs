Ext.namespace("portal.tablePanle");

portal.tablePanle.tpl=null;

var mainPortal = Ext.create('Ext.form.Panel', {
	title : '平面效果图',
	autoScroll : true,
	defaults : {
		defaults : {
			ui : 'light',
			closable : false
		}
	},
	items : [ {
		id : 'c1',
		items : [ {
			id : 'p1',
			//title : '欢迎语',
			style : 'padding:10px; line-height:22px;',
			html : '<center><img src = "' + webContextRoot + '/leaflet/images/lksbig.jpg" width = "501" height = "650"/></center>'
		} ]
	} ],
	isReLayout : false
});


portal.tablePanle.createMainTabPanel = function(){
	var tPanel = Ext.create('Ext.TabPanel', {
		region : 'center',
		margins : '2 0 0 0',
		deferredRender : false,
		activeTab : 0,
		plugins : Ext.create('Ext.ux.TabCloseMenu', {
			closeTabText : '关闭面板',
			closeOthersTabsText : '关闭其他',
			closeAllTabsText : '关闭所有'
		}),
		items : [mainPortal],
		listeners : {
//			tabchange : onTabChange,
//			afterrender : onAfterRender
		}
	});
	portal.tablePanle.tpl = tPanel;
	return tPanel;
}

//打开tab
portal.tablePanle.openTab = function(tabId, tabTitle, tabUrl) {
	
	var _tab = portal.tablePanle.tpl.getComponent(tabId);
    if (!_tab) {
    	_tab = portal.tablePanle.tpl.add(Ext.create('Ext.ux.IFrame',{
            xtype: 'iframepanel',
            id: tabId,
            title: tabTitle,
            closable: true,
            layout: 'fit',
            loadMask: '页面加载中...',
            border: false
        }));
    	portal.tablePanle.tpl.setActiveTab(_tab);
    	_tab.load(tabUrl);
    } else {
    	portal.tablePanle.tpl.setActiveTab(_tab);
    }
}
