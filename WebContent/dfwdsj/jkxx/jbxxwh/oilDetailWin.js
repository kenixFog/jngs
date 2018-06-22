Ext.ns("dfwdsj.jkxx.jbxxwh.oilDetailWin");

dfwdsj.jkxx.jbxxwh.oilDetailWin.objId = null;

dfwdsj.jkxx.jbxxwh.oilDetailWin.showWin = function(oilWellId){
	dfwdsj.jkxx.jbxxwh.oilDetailWin.objId = oilWellId;
	var winCfg = {
			layout : 'fit',   	  // 布局样式
			width : 1400,	
			height : 600, 
			title : '井矿-小层信息',
			resizable : false,	 // 不允许用户允许拖动窗体边角来控制窗口大小
			autoScroll : true,   // 自动显示滚动条
			closeAction : 'destroy',// 关闭时为隐藏操作
			modal : true,		 // 模态化显示：后方的区域不能点击和编辑
			tbar :{
				cls:'whjn-tbar',
				items:[ {
					text : whjn.constant.saveBtnText,
					iconCls : 'fa fa-floppy-o fa-lg',
					bizCode : 'save',
					handler : dfwdsj.jkxx.jbxxwh.oilDetailWin.saveHandler
				}, {
					text : whjn.constant.closeBtnText,
					iconCls : 'fa fa-times-circle fa-lg',
					bizCode : 'close',
					handler : dfwdsj.jkxx.jbxxwh.oilDetailWin.closeHandler
				} ],
			},
			items : dfwdsj.jkxx.jbxxwh.oilDetailWin.initPanel()
		};
		var win = new Ext.Window(winCfg);
		dfwdsj.jkxx.jbxxwh.oilDetailWin.win = win;
		win.show();
}


dfwdsj.jkxx.jbxxwh.oilDetailWin.initPanel = function() {
	
	var className = dfwdsj.jkxx.jbxxwh.oilDetailWin;
	var cols = className.getCols();
	var store = className.getStore();
	var gridBbar = new Ext.PagingToolbar({
		pageSize : whjn.PS,
		store : store,
		displayInfo : true,
		displayMsg : '第{0}条到第{1}条,一共{2}条',
		emptyMsg : '没有记录'
	});
	className.gridBbar = gridBbar;
	
	var editPlugin = Ext.create('Ext.grid.plugin.CellEditing', {
        clicksToEdit: 1
    });
	className.editPlugin = editPlugin;
	
	var gridPnl = Ext.create("Ext.grid.Panel", {
	    region : 'center',
	    border : false,
	    stripeRows:true, //斑马线效果 
	    columnLines: true,
	    selType: "checkboxmodel",
	    columns : cols,
		store : store,
		plugins: editPlugin,
	    bbar:gridBbar
	});
	
	dfwdsj.jkxx.jbxxwh.oilDetailWin.panel = gridPnl;
	return gridPnl;
}


dfwdsj.jkxx.jbxxwh.oilDetailWin.getCols = function(){
	var cols = [];
	cols.push({ 
	   	xtype: 'rownumberer',
	   	text: '序号',
	   	width:40
    },{ 
    	text: 'ID', 
    	hidden : true,
    	dataIndex: 'id', 
    	width:60, 
    	align : 'right'
    },{ 
        text: '油井ID', 
        dataIndex: 'oilWell.id', 
        width:60, 
        hidden :true,
        align : 'right'
    },{ 
    	text: '层位', 
    	dataIndex: 'cw' , 
    	width:80,
    	editor:new Ext.form.TextField({  
            allowBlank:false  
        })
    },{ 
    	text: '小层编号', 
    	dataIndex: 'xcbh', 
    	width:80,
    	editor:new Ext.form.TextField({  
            allowBlank:false  
        })
    },{ 
    	text: '层段起始(m)', 
    	dataIndex: 'cdqs', 
    	width:100,
    	editor:new Ext.form.TextField({  
            allowBlank:false  
        })
    },{ 
    	text: '层段截至(m)', 
    	dataIndex: 'cdjz', 
    	width:100,
    	editor:new Ext.form.TextField({  
            allowBlank:false  
        })
    },{ 
    	text: '油层倾角(°)', 
    	dataIndex: 'ycqj', 
    	width:100,
    	editor:new Ext.form.TextField({  
            allowBlank:false  
        })
    },{ 
    	text: '孔数(个)', 
    	dataIndex: 'ks', 
    	width:70,
    	editor:new Ext.form.NumberField()
    },{ 
    	text: '小层压力(Pa)', 
    	dataIndex: 'yl', 
    	width:100,
    	editor:new Ext.form.TextField({  
            allowBlank:true  
        })
    },{ 
    	text: '地层系数', 
    	dataIndex: 'xs', 
    	width:80,
    	editor:new Ext.form.TextField({  
            allowBlank:false  
        })
    },{ 
    	text: '射开厚度(m)', 
    	dataIndex: 'skhd', 
    	width:100,
    	editor:new Ext.form.TextField({  
            allowBlank:true  
        })
    },{ 
    	text: '有效厚度(m)', 
    	dataIndex: 'yxhd', 
    	width:100,
    	editor:new Ext.form.TextField({  
            allowBlank:false  
        })
    },{ 
    	text: '对应斜度(°)', 
    	dataIndex: 'dyxd', 
    	width:100,
    	editor:new Ext.form.TextField({  
            allowBlank:true  
        })
    },{ 
    	text: '夹层厚度(m)', 
    	dataIndex: 'jchd', 
    	width:100,
    	editor:new Ext.form.TextField({  
            allowBlank:false  
        })
    },{ 
    	text: '有效渗透率(μm2)', 
    	dataIndex: 'yxstl', 
    	width:130,
    	editor:new Ext.form.TextField({  
            allowBlank:true  
        })
    },{ 
    	text: '孔隙度', 
    	dataIndex: 'kxd', 
    	width:80,
    	editor:new Ext.form.TextField({  
            allowBlank:false  
        })
    });
	return cols;
}

dfwdsj.jkxx.jbxxwh.oilDetailWin.getStore = function(){
	
	Ext.define('OilWellDetailModel', {
		extend : 'Ext.data.Model',
		idProperty : 'id',
		fields : [ {
			name : 'id',
			type : 'long'
		}, {
			name : 'oilWell.id',
			type : 'long'
		},  
		'cw', //层位
		'xcbh',//小层编号
		'cdqs',//层段起始
		'cdjz',//层段截至
		'ycqj', //油层倾角
		'ks',//孔数
		'yl',//小层压力
		'xs',//地层系数
		'skhd', //射开厚度
		'yxhd',//有效厚度
		'dyxd',//对应斜度
		'jchd',//夹层厚度
		'yxstl', //有效渗透率
		'kxd'	//孔隙度
		]
	});

	var store = Ext.create("Ext.data.Store", {
	    model: "OilWellDetailModel",
	    pageSize: whjn.PS,
	    proxy: {
	        type: "ajax",
	        actionMethods: {  //默认提交方式为get，回引发前台汉字传到后台乱码的问题，故提交方式改为POST
	        	create : 'POST',  
	            read   : 'POST',   
	            update : 'POST',  
	            destroy: 'POST'  
	        },
	        url: webContextRoot + '/dfwdsj/oilWell/getOilWellDetailList',
	        reader: {
	        	type : 'json',
				root : 'data',
	            totalProperty: 'totalRecord'
	        }
	    },
	    listeners: {
	    	'beforeload': function(store, opration, eOpts){
	    		this.proxy.extraParams.oilWellId = dfwdsj.jkxx.jbxxwh.oilDetailWin.objId;
	    	}
	    }
	});
	store.load();
	return store;
}



dfwdsj.jkxx.jbxxwh.oilDetailWin.saveHandler = function(btn){
	
}


dfwdsj.jkxx.jbxxwh.oilDetailWin.closeHandler= function(){
	dfwdsj.jkxx.jbxxwh.oilDetailWin.win.close();
}
