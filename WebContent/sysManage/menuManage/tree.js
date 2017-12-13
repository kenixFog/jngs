Ext.namespace("sysManage.menuManage.tree");
//选中的树节点
sysManage.menuManage.tree.node = null;

sysManage.menuManage.tree.initTree = function() {
	 var menuTreeStore = Ext.create('Ext.data.TreeStore', {  
	        autoLoad : true,  
	        proxy : {  
	                type : 'ajax',  
	                url : webContextRoot + '/sys/menu/getMenuTreeByParentId',//请求  
	                reader : {  
	                    type : 'json'
	                } 
	            },  
	        root : {  
	        	id : -1,
	        	code : 'MENU_CODE',//节点编码
	            text : '功能菜单树', 
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
	            	sysManage.menuManage.tree.menuTree.getSelectionModel().select(item);
				}
	        }  
	    });
	 sysManage.menuManage.tree.menuTreeStore=menuTreeStore;
	 var menuTree = Ext.create('Ext.tree.Panel', {
			id : 'menuTree',
			region : 'west',
			split : true,
			title : '功能菜单树',
			width : 220,
			margins : '2 0 0 0',
			collapsible : true,
			animCollapse : true,
			xtype : 'treepanel',
			rootVisible : false,
			store : menuTreeStore,
			listeners : {
				'select' : function(e, record){
					var className = sysManage.menuManage.panel;
					//保存当前树节点信息至页面全局变量
					sysManage.menuManage.tree.node =  record;
					className.loadRecord();
				}
			}
		});
	sysManage.menuManage.tree.menuTree = menuTree;
	return menuTree;
}
