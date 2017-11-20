Ext.namespace("sysManage.comCodeManage.panel");
sysManage.comCodeManage.panel.initPanel = function() {
	var panel = new Ext.Panel({
		region : 'center',
		layout : 'border',
		items:[
			sysManage.comCodeManage.panel.initGridPnl()
		]
	});
	return panel;
}

sysManage.comCodeManage.panel.getStore = function(){
	Ext.define('comCodeModel', {
		extend : 'Ext.data.Model',
		idProperty : 'id',
		fields : [ {
			name : 'id',
			type : 'long'
		}, {
			name : 'parentId',
			type : 'long'
		},  'code', 'name', 'comments',{
			name : 'isLeaf',
			type : 'boolean'
		}, 'type', 'statue', 
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
	    model: "comCodeModel",
	    pageSize: whjn.PS,
	    proxy: {
	        type: "ajax",
	        actionMethods: {  //默认提交方式为get，回引发前台汉字传到后台乱码的问题，故提交方式改为POST
	        	create : 'POST',  
	            read   : 'POST',   
	            update : 'POST',  
	            destroy: 'POST'  
	        },
	        url: webContextRoot + '/sys/comCode/getComCodeList',
	        reader: {
	        	type : 'json',
				root : 'data',
	            totalProperty: 'totalRecord',
	            successProperty : "success"
	        }
	    },
	    listeners: {
	    	'beforeload': function(store, opration, eOpts){
	    		var className = sysManage.comCodeManage.tree;
	    		this.proxy.extraParams.nodeId = className.node.raw.id;
	    	}
	    }
	});
	return store;
}


sysManage.comCodeManage.panel.initGridPnl = function() {
	var className = sysManage.comCodeManage.panel;
	var store = className.getStore();
	var comCodeGridPnl = Ext.create("Ext.grid.Panel", {
	    xtype: "grid",
	    store: store,
	    title:'公共代码列表',
	    region : 'center',
	    border : false,
	    columnLines: true,
	    selType: "checkboxmodel",
	    columns: [{ 
        	text: 'ID', 
        	dataIndex: 'id', 
        	hidden : true,
        	width:30, 
        	align : 'right'
	    },{ 
	        text: '父节点ID', 
	        dataIndex: 'parentId', 
	        width:50, 
	        hidden :true,
	        align : 'right'
	    },{ 
	    	text: '编码', 
	    	dataIndex: 'code', 
	    	width:120
	    },{ 
	    	text: '名称', 
	    	dataIndex: 'name' , 
	    	width:120
	    },{ 
	    	text: '是否叶结点', 
	    	dataIndex: 'isLeaf' , 
	    	width:100,
	    	sortable : false,
	    	renderer : function(v) {
				if (v == true) {
					return '是';
				} else {
					return '否';
				}
			}
	    },{ 
	    	text: '类型', 
	    	dataIndex: 'type' , 
	    	width:100,
	    	sortable : false,
	    	renderer : function(v) {
				if (v == 0) {
					return '分层';
				} else if(v == 1) {
					return '代码';
				} else {
					return '数据';
				}
			}
	    },{ 
	    		
	    	text: '备注', 
	    	dataIndex: 'comments' , 
	    	flex:1,
	    	sortable : false
	    		
	    },{ 
	    	text: '状态', 
	    	dataIndex: 'statue', 
	    	width:100,
	    	renderer : function(v) {
				if (v == 0) {
					return '停用';
				} else {
					return '启用';
				}
			}
	    },{ 
	    	text: '创建时间', 
	    	dataIndex: 'createTime' , 
	    	width:150,
	    	sortable : false
	    },{ 
	    	text: '修改时间', 
	    	dataIndex: 'lastEditTime' , 
	    	width:150,
	    	sortable : false
	    }],
	    listeners: {
	        itemdblclick: function (me, record, item, index, e, eOpts) {
	            //双击事件的操作
	        	alert(123);
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
	className.comCodeGridPnl=comCodeGridPnl;
	return comCodeGridPnl;
}

sysManage.comCodeManage.panel.loadRecord = function(){
	var className = sysManage.comCodeManage.panel;
	var store = className.comCodeGridPnl.getStore();
	store.load();
}