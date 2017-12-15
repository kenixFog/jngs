Ext.namespace("sysManage.authorityManage.role.panel");
sysManage.authorityManage.role.panel.initPanel = function() {
	var panel = new Ext.Panel({
		region : 'center',
		layout : 'border',
		items:[
			sysManage.authorityManage.role.panel.initGridPnl()
		]
	});
	return panel;
}

sysManage.authorityManage.role.panel.getStore = function(){
	Ext.define('comCodeModel', {
		extend : 'Ext.data.Model',
		idProperty : 'id',
		fields : [ {
			name : 'id',
			type : 'long'
		}, 'roleCode', 'roleName','comments', 
		{
			name: 'createTime',
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
	        url: webContextRoot + '/sys/authority/getRoleList',
	        reader: {
	        	type : 'json',
				root : 'data',
	            totalProperty: 'totalRecord',
	            successProperty : "success"
	        }
	    },
	    listeners: {
	    	'beforeload': function(store, opration, eOpts){
	    		var node = sysManage.authorityManage.role.tree.node;
	    		this.proxy.extraParams.nodeId = node.raw.id;
	    		this.proxy.extraParams.nodeType = node.raw.nodeType;
	    	}
	    }
	});
	return store;
}


sysManage.authorityManage.role.panel.initGridPnl = function() {
	var className = sysManage.authorityManage.role.panel;
	var store = className.getStore();
	var roleGridPnl = Ext.create("Ext.grid.Panel", {
	    xtype: "grid",
	    store: store,
	    title:'角色列表',
	    region : 'center',
	    tbar :{
			cls:'whjn-tbar',
			items:[ {
				text : "关联用户",
				iconCls : 'fa fa-user-circle fa-lg',
				bizCode : 'relate',
				handler : sysManage.authorityManage.role.panel.relateHandler
			},{
				text : "角色授权",
				iconCls : 'fa fa-key fa-lg',
				bizCode : 'grant',
				handler : sysManage.authorityManage.role.panel.grantHandler
			}]
		},
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
	    	text: '编码', 
	    	dataIndex: 'roleCode', 
	    	width:150
	    },{ 
	    	text: '名称', 
	    	dataIndex: 'roleName' , 
	    	width:120
	    },{ 
	    	text: '创建时间', 
	    	dataIndex: 'createTime' , 
	    	width:160,
	    	sortable : false
	    },{ 
	    		
	    	text: '备注', 
	    	dataIndex: 'comments' , 
	    	width:200,
	    	sortable : false
	    }],
	    listeners: {
	        itemdblclick: function (me, record, item, index, e, eOpts) {
	            //双击事件的操作
	        	sysManage.authorityManage.role.main.edit();
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
	className.roleGridPnl=roleGridPnl;
	return roleGridPnl;
}

sysManage.authorityManage.role.panel.loadRecord = function(){
	var className = sysManage.authorityManage.role.panel;
	var store = className.roleGridPnl.getStore();
	store.load();
}


sysManage.authorityManage.role.panel.relateHandler = function(){
	var node = sysManage.authorityManage.role.tree.node;
	var nodeType = node.raw.nodeType;
	if(nodeType == 0){//选中的是组织机构，不能关联用户
		whjn.dlg.infoTip('无法将分组关联至用户！');
	}else {
		
	}
}

sysManage.authorityManage.role.panel.grantHandler = function() {
	var node = sysManage.authorityManage.role.tree.node;
	var nodeType = node.raw.nodeType;
	if(nodeType == 0){//选中的是组织机构，不能授权
		whjn.dlg.infoTip('无法将功能授权给分组！');
	}else {
		
	}
}