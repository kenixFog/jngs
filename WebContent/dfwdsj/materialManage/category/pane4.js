Ext.namespace("dawdsj.materialManage.category.panel");

dawdsj.materialManage.category.panel.initPanel = function() {
	var panel = new Ext.Panel({
		region : 'center',
		layout : 'border',
		items:[
			dawdsj.materialManage.category.panel.initGridPnl()
		]
	});
	return panel;
}



dawdsj.materialManage.category.panel.getStore = function(){
	
	Ext.define('MenuModel', {
		extend : 'Ext.data.Model',
		idProperty : 'id',
		fields : [ {
			name : 'id',
			type : 'long'
		}, {
			name : 'parentId',
			type : 'long'
		},  'typeCode', 'typeName','isLeaf', 
		{
			name: 'createTime',
			type : 'datetime',
			dateFormat : 'Y-m-d H:i:s'
		},{
			name:'lastEditTime',
			type : 'datetime',
			dateFormat : 'Y-m-d H:i:s'
		}]
	});

	var store = Ext.create("Ext.data.Store", {
	    model: "MenuModel",
	    pageSize: whjn.PS,
	    proxy: {
	        type: "ajax",
	        actionMethods: {  //默认提交方式为get，回引发前台汉字传到后台乱码的问题，故提交方式改为POST
	        	create : 'POST',  
	            read   : 'POST',   
	            update : 'POST',  
	            destroy: 'POST'  
	        },
	        url: webContextRoot + '/dfwdsj/equipment/getEquipmentTypeList',
	        reader: {
	        	type : 'json',
				root : 'data',
	            totalProperty: 'totalRecord'
	        }
	    },
	    listeners: {
	    	'beforeload': function(store, opration, eOpts){
	    		var node = dawdsj.materialManage.category.tree.node;
	    		this.proxy.extraParams.nodeId = node.raw.id;
	    	}
	    }
	});
	return store;
}


dawdsj.materialManage.category.panel.initGridPnl = function() {
	var className = dawdsj.materialManage.category.panel;
	var store = className.getStore();
	var typeGridPnl = Ext.create("Ext.grid.Panel", {
	    xtype: "grid",
	    store: store,
	    title:'数据列表',
	    region : 'center',
	    border : false,
	    columnLines: true,
	    selType: "checkboxmodel",
	    columns: [{ 
        	text: 'ID', 
        	hidden : true,
        	dataIndex: 'id', 
        	width:30, 
        	align : 'right'
	    },{ 
	        text: '上级节点ID', 
	        dataIndex: 'parentId', 
	        width:50, 
	        hidden :true,
	        align : 'right'
	    },{ 
	    	text: '分类名称', 
	    	dataIndex: 'typeName' , 
	    	width:120
	    },{ 
	    	text: '分类编码', 
	    	dataIndex: 'typeCode', 
	    	width:120
	    },{ 
	    	text: '是否叶节点', 
	    	dataIndex: 'isLeaf',
	    	width:120,
	    	renderer : function(v) {
				if (v == 1) {
					return '是';
				}else {
					return '否';
				}
			}
	    },{ 
	    	text: '创建时间', 
	    	dataIndex: 'createTime' , 
	    	width:160,
	    	sortable : false
	    },{ 
	    	text: '修改时间', 
	    	dataIndex: 'lastEditTime' , 
	    	width:160,
	    	sortable : false
	    }],
	    listeners: {
//	        itemdblclick: function (me, record, item, index, e, eOpts) {
//	        	 //双击事件的操作
//	        	dawdsj.materialManage.category.main.edit();
//	        }
	    },
	    bbar:new Ext.PagingToolbar({
			pageSize : whjn.PS,
			store : store,
			displayInfo : true,
			displayMsg : '第{0}条到第{1}条,一共{2}条',
			emptyMsg : '没有记录'
	    })
	});
	className.typeGridPnl=typeGridPnl;
	return typeGridPnl;
}

dawdsj.materialManage.category.panel.loadRecord = function(){
	var className = dawdsj.materialManage.category.panel;
	var store = className.typeGridPnl.getStore();
	store.load();
}