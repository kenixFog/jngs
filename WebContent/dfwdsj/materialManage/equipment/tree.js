Ext.namespace("dawdsj.materialManage.equipment.tree");

//选中的树节点
dawdsj.materialManage.equipment.tree.node = null;

dawdsj.materialManage.equipment.tree.initTree = function() {
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
	            text : '器材树', 
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
	            	dawdsj.materialManage.equipment.tree.qcTree.getSelectionModel().select(item);
				}
	        }  
	    });
	 dawdsj.materialManage.equipment.tree.qcTreeStore=qcTreeStore;
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
//					var className = dawdsj.materialManage.equipment.panel;
//					//保存当前树节点信息至页面全局变量
//					dawdsj.materialManage.equipment.tree.node =  record;
//					className.loadRecord();
					if(record.raw.leaf=='1'){
						Ext.Ajax.request({
							url:  webContextRoot + '/dfwdsj/equipment/getEquipmentFields',//请求  ,
							method : 'POST',
							params: {
						        typeId: record.raw.id
						    },
							success : function(response, options) {
								var res = Ext.JSON.decode(response.responseText);
								//保存当前树节点信息至页面全局变量
								dawdsj.materialManage.equipment.tree.node =  record;
								//对应类型的属性字段
								dawdsj.materialManage.equipment.entry.fieldInfo = res.data['fieldsInfo']
								//重新创建面板
								dawdsj.materialManage.equipment.panel.reCreatePnl(record,res.data);
							},
							failure : function() {
							}
						});
					}
				}
			}
		});
	dawdsj.materialManage.equipment.tree.qcTree = qcTree;
	return qcTree;
}