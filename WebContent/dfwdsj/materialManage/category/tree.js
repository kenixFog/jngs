Ext.namespace("dfwdsj.materialManage.category.tree");

//选中的树节点
dfwdsj.materialManage.category.tree.node = null;

dfwdsj.materialManage.category.tree.initTree = function() {
	 var qcTreeStore = Ext.create('Ext.data.TreeStore', {  
	        autoLoad : true,  
	        proxy : {  
	                type : 'ajax',  
	                url : webContextRoot + '/dfwdsj/equipment/getEquipmentType',//请求  
	                reader : {  
	                    type : 'json'
	                } 
	            },  
	        root : {  
	        	id : -1,
	        	code : 'QC_CODE',//节点编码
	            text : '器材分类', 
	    		type : 0, //节点类型				
	            expanded : true,
	            leaf : 0
	        },  
	        listeners : {  
	            'beforeexpand' : function(node,eOpts){  
	            	//点击菜单会将节点的id通过ajax请求 ,查询子菜单
	                this.proxy.extraParams.parentId = node.raw.id; 
	            },
	            'expand' : function(node,eOpts){
	            	var item = this.getNodeById(node.raw.id);
	            	//选中当前菜单
	            	dfwdsj.materialManage.category.tree.qcTree.getSelectionModel().select(item);
				}
	        }  
	    });
	 dfwdsj.materialManage.category.tree.qcTreeStore=qcTreeStore;
	 var qcTree = Ext.create('Ext.tree.Panel', {
			id : 'menuTree',
			region : 'west',
			split : true,
			title : '器材树面板',
			width : 220,
			margins : '2 0 0 0',
			collapsible : true,
			animCollapse : true,
			xtype : 'treepanel',
			rootVisible : true,
			store : qcTreeStore,
			listeners : {
				'select' : function(e, record){
					
//					var className = dfwdsj.materialManage.category.panel;
//					//保存当前树节点信息至页面全局变量
//					dfwdsj.materialManage.category.tree.node =  record;
//					className.loadRecord();
					
					
					//保存当前树节点信息至页面全局变量
					dfwdsj.materialManage.category.tree.node =  record;
					//重新创建面板
					dfwdsj.materialManage.category.panel.reCreatePnl(record);
					
				}
			}
		});
	dfwdsj.materialManage.category.tree.qcTree = qcTree;
	return qcTree;
}