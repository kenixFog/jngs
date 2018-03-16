Ext.namespace("sysManage.menuManage.panel");
sysManage.menuManage.panel.initPanel = function() {
	var panel = new Ext.Panel({
		region : 'center',
		layout : 'border',
		items:[
			sysManage.menuManage.panel.initGridPnl()
		]
	});
	return panel;
}




sysManage.menuManage.panel.getStore = function(){
	
	Ext.define('MenuModel', {
		extend : 'Ext.data.Model',
		idProperty : 'id',
		fields : [ {
			name : 'id',
			type : 'long'
		}, {
			name : 'parentId',
			type : 'long'
		},  'menuCode', 'menuName','isEdit', 
		'isDelete','type','statue','url', 
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
	        url: webContextRoot + '/sys/menu/getMenuList',
	        reader: {
	        	type : 'json',
				root : 'data',
	            totalProperty: 'totalRecord'
	        }
	    },
	    listeners: {
	    	'beforeload': function(store, opration, eOpts){
	    		var node = sysManage.menuManage.tree.node;
	    		this.proxy.extraParams.nodeId = node.raw.id;
	    	}
	    }
	});
	return store;
}


sysManage.menuManage.panel.initGridPnl = function() {
	var className = sysManage.menuManage.panel;
	var store = className.getStore();
	var menuGridPnl = Ext.create("Ext.grid.Panel", {
	    xtype: "grid",
	    store: store,
	    title:'菜单列表',
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
	    	text: '菜单名称', 
	    	dataIndex: 'menuName' , 
	    	width:120
	    },{ 
	    	text: '菜单编码', 
	    	dataIndex: 'menuCode', 
	    	width:120
	    },{ 
	    		
	    	text: '菜单路径', 
	    	dataIndex: 'url' , 
	    	flex:1,
	    	sortable : false
	    		
	    },{ 
	    	text: '菜单类型', 
	    	dataIndex: 'type' , 
	    	width:90,
	    	sortable : false,
	    	renderer : function(v) {
				if (v == 1) {
					return '页面菜单';
				} else if(v == 2){
					return '按钮';
				} else {
					return '分层菜单';
				}
			}
	    },{ 
	    	text: '是否可编辑', 
	    	dataIndex: 'isEdit' , 
	    	width:90,
	    	sortable : false,
	    	renderer : function(v) {
				if (v == 1) {
					return '是';
				} else {
					return '否';
				}
			}
	    },{ 
	    	text: '是否可删除', 
	    	dataIndex: 'isDelete' , 
	    	width:90,
	    	sortable : false,
	    	renderer : function(v) {
				if (v == 1) {
					return '是';
				} else {
					return '否';
				}
			}
	    },{ 
	    	text: '菜单状态', 
	    	dataIndex: 'statue', 
	    	width:90,
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
	    	width:160,
	    	sortable : false
	    },{ 
	    	text: '修改时间', 
	    	dataIndex: 'lastEditTime' , 
	    	width:160,
	    	sortable : false
	    }],
	    listeners: {
	        itemdblclick: function (me, record, item, index, e, eOpts) {
	        	 //双击事件的操作
	        	sysManage.menuManage.main.edit();
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
	className.menuGridPnl=menuGridPnl;
	return menuGridPnl;
}

sysManage.menuManage.panel.loadRecord = function(){
	var className = sysManage.menuManage.panel;
	var store = className.menuGridPnl.getStore();
	store.load();
}