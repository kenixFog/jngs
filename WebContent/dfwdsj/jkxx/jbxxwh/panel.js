Ext.namespace("dfwdsj.jkxx.jbxxwh.panel");
dfwdsj.jkxx.jbxxwh.panel.initPanel = function() {
	var panel = new Ext.Panel({
		region : 'center',
		layout : 'border',
		items:[
			dfwdsj.jkxx.jbxxwh.panel.initQryPnl(), //查询面板
			dfwdsj.jkxx.jbxxwh.panel.initGridPnl()
		]
	});
	return panel;
}


dfwdsj.jkxx.jbxxwh.panel.initQryPnl = function(){
	
	var qryPnl = new Ext.form.FormPanel({
		region : 'north',
		border : false,
//		bodyStyle : 'padding:7px 0 0 35px',
		layout : 'column',
		items : [ {
			columnWidth : .2,
			layout : 'form',
			border : false,
			labelWidth : 30,
			items : [ {
				anchor : '80%',
				xtype : 'textfield',
				id : 'jm',
				name : 'jm',
				fieldLabel : '井名'
			}]
		}, {
			columnWidth : .2,
			layout : 'form',
			border : false,
			labelWidth : 30,
			items : [ {
				anchor : '80%',
				xtype : 'textfield',
				name : 'name',
				fieldLabel : '姓名'
			}]
		}]
	});
	dfwdsj.jkxx.jbxxwh.panel.qryPnl = qryPnl;
	return qryPnl;
	
}



dfwdsj.jkxx.jbxxwh.panel.getStore = function(){
	
	Ext.define('jkModel', {
		extend : 'Ext.data.Model',
		idProperty : 'id',
		fields : [ {
			name : 'id',//ID
			type : 'long'
		}, 
//		{
//			name : 'xh',//序号
//			type : 'long'
//		}, 
		'jm',// 井名
		'yt',// 油田
		'qk',// 区块
		'zdjx',// 最大井斜
		'tggj',// 套管钢级
		'tggg',// 套管规格
		'wjfs',// 完井方式
		'csfs',// 传输方式
		'yggg',// 油管规格
		'syyl',// 试压压力
		'tcfs',// 投产方式
		'ypcs',// 及时诱喷措施
		{
			name: 'gjsj',// 固井时间
			type : 'datetime',
			dateFormat : 'Y-m-d'
		},
		'snfs',// 水泥返深
		'gjzl',// 固井质量
		'sysj',// 试压时间
		'tgcx',// 套管程序
		'bkfs',// 布孔方式
		'jxfwj',// 井斜方位角
		'skfw',// 射孔方位
		'dxfs',// 定向方式
		'skds',// 射孔段数
		{
			name: 'cjrq',// 创建日期
			type : 'datetime',
			dateFormat : 'Y-m-d H:i:s'
		},
		'user.userName',// 创建人
//		'org.orgName',// 所属组织机构
		'yccy',// 预测产液
		'ycco',// 预测产油
		'sjcy',// 实际产液
		'sjco',// 实际产油
		'wjylx',// 完井液类型
		'wjymd',// 完井液密度
		'fpl',// 防膨率
		'yl',// 液量
		'ph',// PH值
		'skqxw',// 射孔器相位
		'km',// 孔密
		'sj',// 射角
		'skqlx.typeName', // 射孔枪
		'skd.propertyValue' // 射孔弹
		]
	});

	var store = Ext.create("Ext.data.Store", {
	    model: "jkModel",
	    pageSize: whjn.PS,
	    proxy: {
	        type: "ajax",
	        actionMethods: {  //默认提交方式为get，回引发前台汉字传到后台乱码的问题，故提交方式改为POST
	        	create : 'POST',  
	            read   : 'POST',   
	            update : 'POST',  
	            destroy: 'POST'  
	        },
	        url: webContextRoot + '/dfwdsj/oilWell/getJkjcsjList',
	        reader: {
	        	type : 'json',
				root : 'data',
	            totalProperty: 'totalRecord'
	        }
	    }
	});
	store.load();
	return store;
}


dfwdsj.jkxx.jbxxwh.panel.getCol = function(){
	var cols = [{ 
	   	xtype: 'rownumberer',
	   	text: '序号',
	   	width:60, 
	    locked: true
    },{ 
    	text: 'ID', 
    	hidden : true,
    	dataIndex: 'id', 
    	width:60, 
    	align : 'right',
    	locked: true
    },{ 
        text: '井名', 
        dataIndex: 'jm', 
        width:150,
        locked: true
    },{ 
        text: '射孔段数', 
        dataIndex: 'skds', 
        width:80,
        locked: true,
        renderer:function(value,meta,record){     //'"+record.get('id')+"'    onclick='dfwdsj.jkxx.jbxxwh.oilDetailWin.showWin(5)'
        	var oileWellId = record.get('id');
        	return "<a href='#' onclick='dfwdsj.jkxx.jbxxwh.oilDetailWin.showWin("+ oileWellId+ ")'>"+value+"</a>"; 
          }     
    },{
		text : '基本信息',
		columns:[{ 
	        text: '油田', 
	        dataIndex: 'yt', 
	        width:100
	    },{ 
	        text: '区块', 
	        dataIndex: 'qk', 
	        width:100
	    },{ 
	        text: '最大井斜', 
	        dataIndex: 'zdjx', 
	        width:80
	    },{ 
	        text: '套管钢级', 
	        dataIndex: 'tggj', 
	        width:80
	    },{ 
	        text: '套管规格(mm)', 
	        dataIndex: 'tggg', 
	        width:110
	    },{ 
	        text: '完井方式', 
	        dataIndex: 'wjfs', 
	        width:120,
	        renderer : function(value, p, r) {
				return whjn.getNameByCode(value,dfwdsj.jkxx.jbxxwh.WJFS);
			}
	    },{ 
	        text: '传输方式', 
	        dataIndex: 'csfs', 
	        width:120,
	        renderer : function(value, p, r) {
				return whjn.getNameByCode(value,dfwdsj.jkxx.jbxxwh.CSFS);
			}
	    },{ 
	        text: '油管规格(mm)', 
	        dataIndex: 'yggg', 
	        width:110
	    },{ 
	        text: '试压压力(Pa)', 
	        dataIndex: 'syyl', 
	        width:100
	    },{ 
	        text: '投产方式', 
	        dataIndex: 'tcfs', 
	        width:80,
	        renderer : function(value, p, r) {
				return whjn.getNameByCode(value,dfwdsj.jkxx.jbxxwh.TCFS);
			}
	    },{ 
	        text: '诱喷措施', 
	        dataIndex: 'ypcs', 
	        width:120
	    },{ 
	        text: '固井时间', 
	        dataIndex: 'gjsj', 
	        width:120
	    },{ 
	        text: '水泥返深(m)', 
	        dataIndex: 'snfs', 
	        width:100
	    },{ 
	        text: '固井质量', 
	        dataIndex: 'gjzl', 
	        width:80
	    },{ 
	        text: '试压时间(分)', 
	        dataIndex: 'sysj', 
	        width:100
	    },{ 
	        text: '套管程序', 
	        dataIndex: 'tgcx', 
	        width:80
	    },{ 
	        text: '井斜方位角(°)', 
	        dataIndex: 'jxfwj', 
	        width:100
		},{ 
	        text: '射孔方位', 
	        dataIndex: 'skfw', 
	        width:80
	    },{ 
	        text: '定向方式', 
	        dataIndex: 'dxfs', 
	        width:120,
	        renderer : function(value, p, r) {
				return whjn.getNameByCode(value,dfwdsj.jkxx.jbxxwh.DXFS);
			}
	    },{ 
	        text: '创建日期', 
	        dataIndex: 'cjrq', 
	        width:120
	    },{ 
	        text: '创建人', 
	        dataIndex: 'user.userName', 
	        width:80
	    }
//	    ,{ 
//	        text: '组织机构', 
//	        dataIndex: 'org.orgName', 
//	        width:150
//	    }
	    ]
	},{
		text : '完井液信息',
		columns:[{ 
	        text: '类型', 
	        dataIndex: 'wjylx', 
	        width:100,
	        renderer : function(value, p, r) {
				return whjn.getNameByCode(value,dfwdsj.jkxx.jbxxwh.WJYLX);
			}
	    },{ 
	        text: '密度(g/ml)', 
	        dataIndex: 'wjymd', 
	        width:100
	    },{ 
	        text: '防膨率(%)', 
	        dataIndex: 'fpl', 
	        width:100
	    },{ 
	        text: '液量(t)', 
	        dataIndex: 'yl', 
	        width:80
	    },{ 
	        text: 'PH值', 
	        dataIndex: 'ph', 
	        width:80
	    }]
	},{
		text : '射孔参数',
		columns:[{ 
	        text: '布孔方式', 
	        dataIndex: 'bkfs', 
	        width:100,
	        renderer : function(value, p, r) {
				return whjn.getNameByCode(value,dfwdsj.jkxx.jbxxwh.BKFS);
			}
	    },{ 
	        text: '相位', 
	        dataIndex: 'skqxw', 
	        width:80,
	        renderer : function(value, p, r) {
				return whjn.getNameByCode(value,dfwdsj.jkxx.jbxxwh.XW);
			}
	    },{ 
	        text: '孔密', 
	        dataIndex: 'km', 
	        width:70,
	        renderer : function(value, p, r) {
				return whjn.getNameByCode(value,dfwdsj.jkxx.jbxxwh.KM);
			}
	    },{ 
	        text: '射角(°)', 
	        dataIndex: 'sj', 
	        width:70
	    },{ 
	        text: '射孔枪类型', 
	        dataIndex: 'skqlx.typeName', 
	        width:100
	    },{ 
	        text: '射孔弹', 
	        dataIndex: 'skd.propertyValue', 
	        width:100
	    }]
	},{
		text : '产值预测和结果',
		columns:[{ 
	        text: '预测产液(t)', 
	        dataIndex: 'yccy', 
	        width:100
	    },{ 
	        text: '预测产油(t)', 
	        dataIndex: 'ycco', 
	        width:100
	    },{ 
	        text: '实际产液(t)', 
	        dataIndex: 'sjcy', 
	        width:100
	    },{ 
	        text: '实际产油(t)', 
	        dataIndex: 'sjco', 
	        width:100
	    }]
	}];
	return cols;
}


dfwdsj.jkxx.jbxxwh.panel.initGridPnl = function() {
	var className = dfwdsj.jkxx.jbxxwh.panel;
	var store = className.getStore();
	var gridPnl = Ext.create("Ext.grid.Panel", {
//	    xtype: "grouped-header-grid",
	    xtype: "big-data-grid",
	    requires: [
	        'Ext.grid.RowNumberer'
	    ],
	    store: store,
	    title:'井矿信息列表',
	    region : 'center',
	    border : false,
	    columnLines: true,
	    selType: "checkboxmodel",
	    columns: dfwdsj.jkxx.jbxxwh.panel.getCol(),
	    listeners: {
	        itemdblclick: function (me, record, item, index, e, eOpts) {
	        	 //双击事件的操作
//	        	dfwdsj.jkxx.jbxxwh.main.edit();
	        }
	    },
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

dfwdsj.jkxx.jbxxwh.panel.loadRecord = function(){
	var className = dfwdsj.jkxx.jbxxwh.panel;
	var store = className.gridPnl.getStore();
	store.load();
}