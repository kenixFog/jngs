Ext.namespace("sysManage.orgManage.user.panel");
sysManage.orgManage.user.panel.initPanel = function() {
	var panel = new Ext.Panel({
		region : 'center',
		layout : 'border',
		items:[
			sysManage.orgManage.user.panel.initQryPnl(),
			sysManage.orgManage.user.panel.initGridPnl()
		]
	});
	return panel;
}




sysManage.orgManage.user.panel.initQryPnl = function(){
	var className = sysManage.orgManage.user.panel;
	var userQryPnl = Ext.create("Ext.form.Panel", {
	    xtype: "form",
	    title:'查询条件',
	    region : 'north',
	    bodyStyle:'padding:10px 0 0 10px',
	    height : 80,
	    border : false,
	    labelAlign :'right',
	    layout : 'column',
	    items : [{
	    	id : 'realName',
	    	labelWidth : 40,
	    	style: 'margin-left:20px',
	    	xtype : 'textfield',
			name : 'realName',
			fieldLabel : '姓名'
		},{
			id : 'statue',
			labelWidth : 40,
			style: 'margin-left:20px',
			xtype : 'combobox',
			name : 'statue',
			store : whjn.getAddBlankValue(sysManage.orgManage.user.statusArray),
			fieldLabel : '状态'
		},{
			style: 'margin-left:40px',
			xtype: 'button',  
	        text: '查询',  
	        handler: function() {  
	        	sysManage.orgManage.user.panel.loadRecord();
	        }  
		}]
	});
	className.userQryPnl=userQryPnl;
	return userQryPnl;
	
}


sysManage.orgManage.user.panel.getStore = function(){
	Ext.define('userModel', {
		extend : 'Ext.data.Model',
		idProperty : 'id',
		fields : [ {
			name : 'id',
			type : 'long'
		}, 'userName', 'realName', 'org.orgName','email', 
		'tel','statue', 
		{
			name: 'createTime',
			type : 'datetime',
			dateFormat : 'Y-m-d H:i:s'
		},{
			name: 'lastLoginTime',
			type : 'datetime',
			dateFormat : 'Y-m-d H:i:s'
		}]
	});

	var store = Ext.create("Ext.data.Store", {
	    model: "userModel",
	    pageSize: whjn.PS,
	    proxy: {
	        type: "ajax",
	        actionMethods: {  //默认提交方式为get，回引发前台汉字传到后台乱码的问题，故提交方式改为POST
	        	create : 'POST',  
	            read   : 'POST',   
	            update : 'POST',  
	            destroy: 'POST'  
	        },
	        url: webContextRoot + '/sys/user/getUserList',
	        reader: {
	        	type : 'json',
				root : 'data',
	            totalProperty: 'totalRecord'
	        }
	    },
	    listeners: {
	    	'beforeload': function(store, opration, eOpts){
	    		var node = sysManage.orgManage.user.tree.node;
	    		this.proxy.extraParams.nodeId = node.raw.id;
	    	}
	    }
	});
	return store;
}


sysManage.orgManage.user.panel.initGridPnl = function() {
	var className = sysManage.orgManage.user.panel;
	var store = className.getStore();
	var userGridPnl = Ext.create("Ext.grid.Panel", {
	    xtype: "grid",
	    store: store,
	    title:'用户列表',
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
	        text: '登录名', 
	        dataIndex: 'userName', 
	        width:100
	    },{ 
	    	text: '姓名', 
	    	dataIndex: 'realName', 
	    	width:80
	    },{ 
	    	text: '基准组织', 
	    	dataIndex: 'org.orgName', 
	    	width:220
	    },{ 
	    	text: '邮箱', 
	    	dataIndex: 'email' , 
	    	width:150
	    },{ 
	    		
	    	text: '电话', 
	    	dataIndex: 'tel' , 
	    	width:120
	    },{ 
	    	text: '状态', 
	    	dataIndex: 'statue', 
	    	width:50,
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
	    	text: '最后一次登录时间', 
	    	dataIndex: 'lastLoginTime' , 
	    	width:160,
	    	sortable : false
	    }],
	    listeners: {
	        itemdblclick: function (me, record, item, index, e, eOpts) {
	        	 //双击事件的操作
	        	sysManage.orgManage.user.main.edit();
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
	className.userGridPnl=userGridPnl;
	return userGridPnl;
}

sysManage.orgManage.user.panel.loadRecord = function(){
	var className = sysManage.orgManage.user.panel;
	var store = className.userGridPnl.getStore();
	store.proxy.extraParams.realName = Ext.getCmp("realName").getValue();
	store.proxy.extraParams.statue = Ext.getCmp("statue").getValue();
	store.load();
}