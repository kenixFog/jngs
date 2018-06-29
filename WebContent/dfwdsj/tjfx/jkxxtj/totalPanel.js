Ext.namespace("dfwdsj.tjfx.jkxxtj.totalPanel");

dfwdsj.tjfx.jkxxtj.totalPanel.initTotalPanel = function() {
	var tPanel = new Ext.Panel({
		layout : 'border',
		region : 'center',
		items : [
			dfwdsj.tjfx.jkxxtj.totalPanel.initQryPnl(), 
			dfwdsj.tjfx.jkxxtj.totalPanel.initGridPnl()
		]
	});
	dfwdsj.tjfx.jkxxtj.totalPanel.tPanel = tPanel;
	return tPanel;
}

dfwdsj.tjfx.jkxxtj.totalPanel.initQryPnl = function(){
	var className = dfwdsj.tjfx.jkxxtj.totalPanel;
	var qryPnl = Ext.create("Ext.form.Panel", {
	    xtype: "form",
	    region : 'north',
	    bodyStyle:'padding:10px 0 10px 10px',
	    autoHeight : true,
	    border : false,
	    labelAlign :'right',
	    layout : 'column',
	    items : [{
	    	id : 'startDate',
	    	labelWidth : 60,
	    	style: 'margin-left:20px',
	    	xtype : 'datefield',
	    	format : 'Y-m',
			name : 'startDate',
			fieldLabel : '起始时间'
		},{
	    	id : 'endDate',
	    	labelWidth : 60,
	    	style: 'margin-left:20px',
	    	xtype : 'datefield',
	    	format : 'Y-m',
			name : 'endDate',
			fieldLabel : '截至时间'
		},{
			style: 'margin-left:40px',
			xtype: 'button',  
	        text: '查询',  
	        handler: function() {  
	        	dfwdsj.jkxx.jbxxwh.totalPanel.loadRecord();
	        }  
		}]
	});
	className.qryPnl=qryPnl;
	return qryPnl;
}


dfwdsj.tjfx.jkxxtj.totalPanel.getStore = function(){
	var kmInfo = dfwdsj.tjfx.jkxxtj.KM;
	var km= new Array();
	var fields=[ 
		'dw',// 单位
		'skhd',// 射开厚度
		'yxhd',// 有效厚度
		'zj',// 直井
		'xj',// 斜井
		'a',// 小层厚度小于等于3m
		'b',// 小层大于3m小于等于5m
		'c',// 小层大于5m
		'd',// 小层深度小于等于500m
		'e',// 小层深度大于500m小于等于1000m
		'f',// 小层深度大于1000m小于等于1500m
		'g',// 小层深度大于1500m小于等于2000m
		'h'// 小层深度大于2000m
		];
	for(var i = 0;i< kmInfo.length;i++){//value，name
		fields.push(kmInfo[i][0]+'zs');//总数
		fields.push(kmInfo[i][0]+'zj');//直井
		km.push(kmInfo[i][0]);
	}
	
	Ext.define('tjModel', {
		extend : 'Ext.data.Model',
		fields : fields
	});

	var store = Ext.create("Ext.data.Store", {
	    model: "tjModel",
	    pageSize: whjn.PS,
	    proxy: {
	        type: "ajax",
	        actionMethods: {  //默认提交方式为get，回引发前台汉字传到后台乱码的问题，故提交方式改为POST
	        	create : 'POST',  
	            read   : 'POST',   
	            update : 'POST',  
	            destroy: 'POST'  
	        },
	        url: webContextRoot + '/dfwdsj/oilWellAnalyse/getTotalList',
	        params : {
				km : km
			},
	        reader: {
	        	type : 'json',
				root : 'data',
	            totalProperty: 'totalRecord'
	        }
	    }
	});
	return store;
}


dfwdsj.tjfx.jkxxtj.totalPanel.getCol = function(){
	var cols = [{ 
	   	xtype: 'rownumberer',
	   	text: '序号',
	   	width:50
    },{ 
        text: '客户单位', 
        dataIndex: 'dw', 
        flex:1
    },{ 
        text: '射开厚度(m)', 
        dataIndex: 'skhd', 
        width:100
    },{ 
        text: '有效厚度(m)', 
        dataIndex: 'yxhd', 
        width:100
    },{ 
        text: '直井(个)', 
        dataIndex: 'zj', 
        width:80
    },{ 
        text: '斜井(个)', 
        dataIndex: 'xj', 
        width:80
    },{
		text : '小层厚度(个)',
		columns:[{ 
	        text: 'A', 
	        dataIndex: 'a', 
	        tooltip:'(0,3m]',
	        width:50
	    },{ 
	        text: 'B', 
	        dataIndex: 'b', 
	        tooltip:'(3m,5m]',
	        width:50
	    },{ 
	        text: 'C', 
	        dataIndex: 'c', 
	        tooltip:'(5m,+)',
	        width:50
	    }]
	},{
		text : '小层深度(个)',
		columns:[{ 
	        text: 'D', 
	        dataIndex: 'd', 
	        tooltip:'(0,500m]',
	        width:50
	    },{ 
	        text: 'E', 
	        dataIndex: 'e', 
	        tooltip:'(500m,1000m]',
	        width:50
	    },{ 
	        text: 'F', 
	        dataIndex: 'f',
	        tooltip:'(1000m,1500m]',
	        width:50
	    },{ 
	        text: 'G', 
	        dataIndex: 'g',
	        tooltip:'(1500m,2000m]',
	        width:50
	    },{ 
	        text: 'H', 
	        dataIndex: 'h', 
	        tooltip:'(2000m,+)',
	        width:50
	    }]
	}];
	var km = dfwdsj.tjfx.jkxxtj.KM;
	var kmCol=[];
	for(var i = 0;i< km.length;i++){//value，name
		var kmDetailCol=[];
		kmDetailCol.push({
			text: '总计', 
	        dataIndex: km[i][0]+'zs', 
	        width:50
		});
		kmDetailCol.push({
			text: '直井', 
	        dataIndex: km[i][0]+'zj',
	        width:50
		});
		
		kmCol.push({
			text: km[i][1]+'孔/m', 
			columns:kmDetailCol
		});
	}
	cols.push({
		text : '孔密(个)',
		columns:kmCol
	})
	return cols;
}


dfwdsj.tjfx.jkxxtj.totalPanel.initGridPnl = function() {
	var className = dfwdsj.tjfx.jkxxtj.totalPanel;
	var store = className.getStore();
	var gridPnl = Ext.create("Ext.grid.Panel", {
	    xtype: "big-data-grid",
	    requires: [
	        'Ext.grid.RowNumberer'
	    ],
	    selType: "checkboxmodel",
	    store: store,
	    title:'井矿信息汇总表',
	    region : 'center',
	    stripeRows:true, //斑马线效果 
	    columnLines: true,
	    border : false,
	    columns: dfwdsj.tjfx.jkxxtj.totalPanel.getCol(),
	    bbar:new Ext.PagingToolbar({
			pageSize : whjn.PS,
			store : store,
			displayInfo : true,
			displayMsg : '第{0}条到第{1}条,一共{2}条',
			emptyMsg : '没有记录'
	    })
	});
	className.gridPnl=gridPnl;
	return gridPnl;
}

/**
 * 加载数据
 */
dfwdsj.tjfx.jkxxtj.totalPanel.loadRecord = function(){
	var className = dfwdsj.tjfx.jkxxtj.totalPanel;
	var store = className.gridPnl.getStore();
	store.proxy.extraParams.startDate = Ext.getCmp("startDate").getValue();
	store.proxy.extraParams.endDate = Ext.getCmp("endDate").getValue();
	store.load();
}

