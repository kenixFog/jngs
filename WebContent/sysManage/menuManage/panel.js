Ext.namespace("sysManage.menuManage.panel");
sysManage.menuManage.panel.initPanel = function() {
	var panel = new Ext.Panel({
		region : 'center',
		layout : 'border',
		items:[
			sysManage.menuManage.panel.initQueryPnl(),
			sysManage.menuManage.panel.initGridPnl()
		]
	});
	return panel;
}


/**
 * 初始查询区域
 * @return {Ext.Panel}
 */
sysManage.menuManage.panel.initQueryPnl = function() {
	var queryPnl = new Ext.form.FormPanel({
		region : 'north', //在父面板中的位置
		border : false,
		height : 72,
		title:'查询区域',
		bodyStyle : 'padding:3px',
		layout : 'column', //此面板作为容器的布局方式，为百分比布局
		//定义存放控件的容器
		items : [ {
			columnWidth : .25, //存放此此控件容器的宽度 
			layout : 'form', //存放此控件的容器的布局方式，表单布局
			border : true,
			bodyStyle : 'padding-left:15px',
			border : false,
			//定义控件
			items : [{
				xtype : 'textfield', //类型为文本框
				id : 'menuName',
				name : 'menuName',
				fieldLabel : '菜单名称',
				labelWidth : 80,
				anchor : '100%' ,//大小比例 
				listeners : {
				   'specialkey' : function(f, e) {   //获取Enter键事件，调用查询方法
					if(e.getKey() == e.ENTER || e.getKey() == e.RETURN){
						sysManage.menuManage.panel.loadRecord();
					  };
				   }
				}
			}]
		}, {
			columnWidth : .15,
			layout : 'form',
			border : true,
			bodyStyle : 'padding-left:5px',
			border : false,
			items : [{
				xtype : 'button', //按钮
				text : '查询', //按钮上显示的文字
				handler : function() { //点击按钮触发的事件
					sysManage.menuManage.panel.loadRecord();
				}
			}]
		}]
	});
	sysManage.menuManage.panel.qryPnl = queryPnl;
	return queryPnl;
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
		},  'menuCode', 'menuName', {
			name : 'isEdit',
			type : 'boolean'
		},  {
			name : 'isDelete',
			type : 'boolean'
		},  {
			name : 'isLeaf',
			type : 'boolean'
		}, 'type','statue','url' ]
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
	    		var className = sysManage.menuManage.panel;
	    		this.proxy.extraParams.nodeId = className.nodeId;
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
	    	text: '菜单编码', 
	    	dataIndex: 'menuCode', 
	    	width:120
	    },{ 
	    	text: '菜单名称', 
	    	dataIndex: 'menuName' , 
	    	width:120
	    },{ 
	    		
	    	text: '菜单路径', 
	    	dataIndex: 'url' , 
	    	width:400,
	    	sortable : false
	    		
	    },{ 
	    	text: '是否可编辑', 
	    	dataIndex: 'isEdit' , 
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
	    	text: '是否可删除', 
	    	dataIndex: 'isDelete' , 
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
	    	text: '菜单类型', 
	    	dataIndex: 'type' , 
	    	width:100,
	    	sortable : false,
	    	renderer : function(v) {
				if (v == 0) {
					return '菜单';
				} else {
					return '按钮';
				}
			}
	    },{ 
	    	text: '菜单状态', 
	    	dataIndex: 'statue', 
	    	width:100,
	    	renderer : function(v) {
				if (v == 0) {
					return '停用';
				} else {
					return '启用';
				}
			}
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
	className.menuGridPnl=menuGridPnl;
	return menuGridPnl;
}

sysManage.menuManage.panel.loadRecord = function(){
	var className = sysManage.menuManage.panel;
	var store = className.menuGridPnl.getStore();
	var obj = Ext.getCmp("menuName");
	store.proxy.extraParams.qryName = obj.getValue().trim();
	store.load();
}