Ext.namespace("dfwdsj.tjfx.jkxxtj.totalChart");


//打开图表面板
dfwdsj.tjfx.jkxxtj.totalChart.openTab = function() {
	//汇总数据表传过来的数据
	var store = dfwdsj.tjfx.jkxxtj.totalPanel.store;
	//对数据进行处理，获取需要的数据
	dfwdsj.tjfx.jkxxtj.totalChart.getChartData(store);
	var mainPnl = dfwdsj.tjfx.jkxxtj.main.tpnl
	var _tab = mainPnl.getComponent('echartPnl');
	if (!_tab) {
		mainPnl.setLoading('Loading...');
		_tab = Ext.create('Ext.panel.Panel', {
			closable : true,
			id : 'echartPnl',
			title : '图表统计',
			layout : 'border',
			autoScroll : true,
			border : false,
			items : dfwdsj.tjfx.jkxxtj.totalChart.initChartPanel()
		});
		mainPnl.add(_tab);
		mainPnl.setLoading(false);
	}
}

dfwdsj.tjfx.jkxxtj.totalChart.initChartPanel = function() {
	var tPanel = new Ext.Panel({
		layout:{
           type:"vbox",
           align:"stretch",
           pack:"center"
	    }, 
		region : 'center',
		items:[{
		    xtype:'panel',
		    layout:{
		           type:"hbox",
		           align:"stretch",
		           pack:"center"
			    }, 
			id:'hbox',
		    flex: 1,
		    items : [{
			    flex: 1,
			    xtype:'panel',
			    id:'hbox-1',
			    layout:'fit',
			    border : true,
			    bodyStyle:'border-width:1px 1px 1px 1px;border-style: solid;',
			    afterRender: function(){
			    	
			    }
			},{
				flex: 1,
				border : true,
			    bodyStyle:'border-width:1px 1px 1px 0;border-style: solid;',
			    html: 'Content'
			}]
		},{
		    xtype:'panel',
		    flex: 1,
		    layout:{
		           type:"hbox",
		           align:"stretch",
		           pack:"center"
			    },
		    items : [{
		    	flex: 1,
		    	border : true,
			    bodyStyle:'border-width:0 1px 1px 1px;border-style: solid;',
				html: 'Content'
			},{
				flex: 1,
				border : true,
			    bodyStyle:'border-width:0 1px 1px 0;border-style: solid;',
			    html: 'Content'
			},{
				flex: 1,
				border : true,
			    bodyStyle:'border-width:0 1px 1px 0;border-style: solid;',
			    html: 'Content'
		    }]
		}]
	});
	return tPanel;
}

//封装数据
dfwdsj.tjfx.jkxxtj.totalChart.getChartData = function(store){
	
}