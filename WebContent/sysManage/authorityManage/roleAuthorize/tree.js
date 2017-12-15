Ext.namespace("sysManage.authorityManage.role.tree");
//选中的树节点
sysManage.authorityManage.role.tree.node = null;

sysManage.authorityManage.role.tree.initTree = function() {
	 var roleTreeStore = Ext.create('Ext.data.TreeStore', {  
	        autoLoad : true,  
	        proxy : {  
	                type : 'ajax',  
	                url : webContextRoot + '/sys/authority/getRoleTreeByParentId',//请求  
	                reader : {  
	                    type : 'json'
	                } 
	            },  
	        root : {  
	        	id : -1,
	        	code : 'ROLE_CODE',//节点编码
	            text : '角色树', 
	    		nodeType : 0, //节点类型	0：组织结构，1：角色分组, 2角色			
	            expanded : true,
	            leaf : false
	        },  
	        listeners : {  
	            'beforeexpand' : function(node,eOpts){  
	            	//点击菜单会将节点的id通过ajax请求 ,查询子菜单
	                this.proxy.extraParams.parentId = node.raw.id; 
	                this.proxy.extraParams.nodeType = node.raw.type;
	            },
	            'expand' : function(node,eOpts){
	            	var item = this.getNodeById(node.raw.id);
	            	//选中当前菜单
	            	sysManage.authorityManage.role.tree.roleTree.getSelectionModel().select(item);
				}
	        }  
	    });
	 sysManage.authorityManage.role.tree.roleTreeStore=roleTreeStore;
	 var roleTree = Ext.create('Ext.tree.Panel', {
			id : 'roleTree',
			region : 'west',
			split : true,
			title : '角色树',
			width : 300,
			margins : '2 0 0 0',
			collapsible : true,
			animCollapse : true,
			xtype : 'treepanel',
			rootVisible : false,
			store : roleTreeStore,
			listeners : {
				'select' : function(e, record){
					var className = sysManage.authorityManage.role.panel;
					//保存当前树节点信息至页面全局变量
					sysManage.authorityManage.role.tree.node = record;
					className.loadRecord();
				}
			}
	});
	sysManage.authorityManage.role.tree.roleTree = roleTree;
	return roleTree;
}
