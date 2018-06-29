Ext.namespace("portal.tree");


portal.tree.createTreePanel = function(){
	
	var menuTreeStore = Ext.create('Ext.data.TreeStore', {  
		autoLoad : true,  
		proxy : {  
		        type : 'ajax',  
		        url : webContextRoot + '/sys/menu/getUseMenuByAuthority',//请求  
		        reader : {  
		            type : 'json'
		        } 
		    },  
		root : {  
			id:-1,
		    text : '科研管理平台',  
		    expanded : true           
		},  
		listeners : {  
		    'beforeexpand' : function(node,eOpts){  
		    	//点击父亲节点的菜单会将节点的id通过ajax请求 
		        this.proxy.extraParams.parentId = node.raw.id;  
		        this.proxy.extraParams.userId = currUser['userId'];  
		    }  
		}  
}); 
	
	
	var treePanel = Ext.create('Ext.tree.Panel', {
		id : 'menuTree',
		region : 'west',
		split : true,
		title : '功能导航',
		width : 220,
		margins : '2 0 0 0',
		collapsible : true,
		animCollapse : true,
		useArrows: true,
		xtype : 'treepanel',
		rootVisible : false,
		store : menuTreeStore,
//		tbar : [ treeFilterField ],
		listeners : {
			'itemclick' : function(e, record) {
				if (record.data.leaf) {
					//叶子结点的id
					var nodeId = record.data.id;
					//叶子结点的text
					var text = record.data.text;
					//叶子结点的url
					var url =  webContextRoot + record.raw.url + '?nodeId='+ nodeId;
					portal.tablePanle.openTab(nodeId, text, url);
				}
			}
		}
	});
	return treePanel;
}
