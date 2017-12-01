Ext.namespace("sysManage.orgManage.org.tree");
//选中的树节点
sysManage.orgManage.org.tree.node = null;

sysManage.orgManage.org.tree.initTree = function() {
	 var orgTreeStore = Ext.create('Ext.data.TreeStore', {  
	        autoLoad : true,  
	        proxy : {  
	                type : 'ajax',  
	                url : webContextRoot + '/sys/org/getOrgTreeByParentId',//请求  
	                reader : {  
	                    type : 'json'
	                } 
	            },  
	        root : {  
	        	id : -1,
	        	code : 'ORG_CODE',//节点编码
	            text : '基准组织树', 
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
	            	sysManage.orgManage.org.tree.orgTree.getSelectionModel().select(item);
				}
	        }  
	    });
	 sysManage.orgManage.org.tree.orgTreeStore=orgTreeStore;
	 var orgTree = Ext.create('Ext.tree.Panel', {
			id : 'orgTree',
			region : 'west',
			split : true,
			title : '基准组织树',
			width : 280,
			margins : '2 0 0 0',
			collapsible : true,
			animCollapse : true,
			xtype : 'treepanel',
			rootVisible : true,
			store : orgTreeStore,
			listeners : {
				'select' : function(e, record){
					var className = sysManage.orgManage.org.panel;
					//保存当前树节点信息至页面全局变量
					sysManage.orgManage.org.tree.node =  record;
//					className.loadRecord();
				}
			}
		});
	sysManage.orgManage.org.tree.orgTree = orgTree;
	return orgTree;
}
