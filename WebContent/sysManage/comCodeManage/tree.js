Ext.namespace("sysManage.comCodeManage.tree");
//选中的树节点
sysManage.comCodeManage.tree.node = null;
//选中的树节点父节点
sysManage.comCodeManage.tree.parentNode = null;

sysManage.comCodeManage.tree.initTree = function() {
	 var comCodeTreeStore = Ext.create('Ext.data.TreeStore', {  
	        autoLoad : true,  
	        proxy : {  
	                type : 'ajax',  
	                url : webContextRoot + '/sys/comCode/getComCodeTreeByParentId',//请求  
	                reader : {  
	                    type : 'json'
	                } 
	            },  
	        root : {  
	        	id : -1,
	        	code : 'comCode_CODE',//节点编码
	            text : '公共代码树', 
	    		type : 0, //节点类型				
	            expanded : true,
	            leaf : false
	        },  
	        listeners : {  
	            'beforeexpand' : function(node,eOpts){  
	            	//点击菜单会将节点的id通过ajax请求 ,查询子菜单
	                this.proxy.extraParams.parentId = node.raw.id; 
	            },
	            'expand' : function(node,eOpts){
	            	var item = this.getNodeById(node.raw.id);
	            	//选中当前菜单
	            	sysManage.comCodeManage.tree.comCodeTree.getSelectionModel().select(item);
				}
	        }  
	    });
	 sysManage.comCodeManage.tree.comCodeTreeStore=comCodeTreeStore;
	 var comCodeTree = Ext.create('Ext.tree.Panel', {
			id : 'comCodeTree',
			region : 'west',
			split : true,
			title : '公共代码',
			width : 220,
			margins : '2 0 0 0',
			collapsible : true,
			animCollapse : true,
			xtype : 'treepanel',
			rootVisible : true,
			store : comCodeTreeStore,
			listeners : {
				'select' : function(e, record){
					var className = sysManage.comCodeManage.panel;
					//保存当前树节点信息至页面全局变量
					sysManage.comCodeManage.tree.node = record;
					className.loadRecord();
				}
			}
	});
	sysManage.comCodeManage.tree.comCodeTree = comCodeTree;
	return comCodeTree;
}
