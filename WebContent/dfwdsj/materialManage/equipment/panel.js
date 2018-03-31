Ext.namespace("dawdsj.materialManage.equipment.panel");

dawdsj.materialManage.equipment.panel.currentRecord=null;

dawdsj.materialManage.equipment.panel.initGridPnl = function() {
	var className = dawdsj.materialManage.equipment.panel;
	var gridBbar = new Ext.PagingToolbar({
		pageSize : whjn.PS,
		store : '',
		displayInfo : true,
		displayMsg : '第{0}条到第{1}条,一共{2}条',
		emptyMsg : '没有记录'
	});
	className.gridBbar = gridBbar;
	
	
	var gridPnl = Ext.create("Ext.grid.Panel", {
	    title:'数据列表',
	    region : 'center',
	    border : false,
	    stripeRows:true, //斑马线效果 
	    columnLines: true,
	    selType: "checkboxmodel",
	    columns : [],
		store : '',
	    bbar:gridBbar
	});
	className.gridPnl=gridPnl;
	return gridPnl;
}

//重新加载面板-动态列

dawdsj.materialManage.equipment.panel.reCreatePnl = function(record,data){
	// 字段数组
	var fieldsInfo = data['fieldsInfo'];
	// 字段
	var fields=[];
	// 表头
	var cols = [];
	// 请求地址
	var url='';
	if(record.raw.leaf=='1'){//叶子节点，去数据库加载节点所对应的字段
		url = '/dfwdsj/equipment/getEquipments';
		for(var i = 0;i<fieldsInfo.length;i++){//编码,名称,类型,长度,默认值
			fields.push(fieldsInfo[i][0]);
			cols.push({
				text: fieldsInfo[i][1], 
	        	dataIndex: fieldsInfo[i][0], 
	        	width:Number(fieldsInfo[i][3])
			});
		}
		
		Ext.define('model', {
			extend : 'Ext.data.Model',
			idProperty : 'id',
			fields : fields
		});
		
		var store = Ext.create("Ext.data.Store", {
		    model: "model",
		    pageSize: whjn.PS,
		    proxy: {
		        type: "ajax",
		        actionMethods: {  //默认提交方式为get，回引发前台汉字传到后台乱码的问题，故提交方式改为POST
		        	create : 'POST',  
		            read   : 'POST',   
		            update : 'POST',  
		            destroy: 'POST'  
		        },
		        url: webContextRoot + url,
		        reader: {
		        	type : 'json',
					root : 'data',
		            totalProperty: 'totalRecord'
		        }
		    },
		    listeners: {
		    	'beforeload': function(store, opration, eOpts){
		    		var node = dawdsj.materialManage.equipment.tree.node;
		    		this.proxy.extraParams.nodeId = node.raw.id;
		    		this.proxy.extraParams.fields = fields;
		    		
		    	}
		    }
		});
		store.load();
		var className = dawdsj.materialManage.equipment.panel;
		// 绑定PagingToolbar的store
		className.gridBbar.bind(store);
		// 重新配置grid,使用不同的Store和ColumnModel
		className.gridPnl.reconfigure(store, cols);
	} else {
		return;
	}
}


