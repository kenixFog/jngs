Ext.namespace("sysManage.orgManage.org.panel");
sysManage.orgManage.org.panel.initPanel = function() {
	var panel = new Ext.Panel({
		region : 'center',
		layout : 'border',
		items:[
			sysManage.orgManage.org.panel.initGridPnl()
		]
	});
	return panel;
}




sysManage.orgManage.org.panel.getStore = function(){
	
	Ext.define('OrgModel', {
		extend : 'Ext.data.Model',
		idProperty : 'id',
		fields : [ {
			name : 'id',
			type : 'long'
		}, {
			name : 'parentId',
			type : 'long'
		},  'orgCode', 'orgName','attr', 
		'type','statue', 
		{
			name: 'createTime',
			type : 'datetime',
			dateFormat : 'Y-m-d H:i:s'
		}]
	});

	var store = Ext.create("Ext.data.Store", {
	    model: "OrgModel",
	    pageSize: whjn.PS,
	    proxy: {
	        type: "ajax",
	        actionMethods: {  //默认提交方式为get，回引发前台汉字传到后台乱码的问题，故提交方式改为POST
	        	create : 'POST',  
	            read   : 'POST',   
	            update : 'POST',  
	            destroy: 'POST'  
	        },
	        url: webContextRoot + '/sys/org/getOrgList',
	        reader: {
	        	type : 'json',
				root : 'data',
	            totalProperty: 'totalRecord'
	        }
	    },
	    listeners: {
	    	'beforeload': function(store, opration, eOpts){
	    		var node = sysManage.orgManage.org.tree.node;
	    		this.proxy.extraParams.nodeId = node.raw.id;
	    	}
	    }
	});
	return store;
}


sysManage.orgManage.org.panel.initGridPnl = function() {
	var className = sysManage.orgManage.org.panel;
	var store = className.getStore();
	var orgGridPnl = Ext.create("Ext.grid.Panel", {
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
	    	text: '基准组织编码', 
	    	dataIndex: 'orgCode', 
	    	width:130
	    },{ 
	    	text: '基准组织名称', 
	    	dataIndex: 'orgName' , 
	    	width:250
	    },{ 
	    		
	    	text: '性质', 
	    	dataIndex: 'attr' , 
	    	width:120,
	    	sortable : false,
	    	renderer : function(v) {
				if (v == 1) {
					return '公司';
				} else if(v == 2 ){
					return '部门';
				} else {
					return '班组'; 
				}
			}
	    },{ 
	    	text: '类型', 
	    	dataIndex: 'type' , 
	    	width:120,
	    	sortable : false,
	    	renderer : function(v) {
				if (v == 1) {
					return '内部组织';
				} else {
					return '外部组织';
				}
			}
	    },{ 
	    	text: '状态', 
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
	    }],
	    listeners: {
	        itemdblclick: function (me, record, item, index, e, eOpts) {
	        	 //双击事件的操作
//	        	sysManage.orgManage.org.main.edit();
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
	className.orgGridPnl=orgGridPnl;
	return orgGridPnl;
}

sysManage.orgManage.org.panel.loadRecord = function(){
	var className = sysManage.orgManage.org.panel;
	var store = className.orgGridPnl.getStore();
	store.load();
}