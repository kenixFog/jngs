Ext.namespace("dfwdsj.tjfx.jkxxtj.main");

dfwdsj.tjfx.jkxxtj.main.initMainPanel = function(){  
	//定义面板
	var mainPanel = Ext.create('Ext.TabPanel',{
		layout : 'border',                //布局类型 
		border : true,                    //隐藏边框
	    items: [{
	        title: '概况总览',
	        layout : 'border', 
	        items:[             								
				dfwdsj.tjfx.jkxxtj.totalPanel.initTotalPanel()       //数据面板   
			]
	    }]
		
		
//		items:[             								
//			dfwdsj.tjfx.jkxxtj.totalPanel.initTotalPanel()       //数据面板   
//		]
	});
	return mainPanel;
}	

