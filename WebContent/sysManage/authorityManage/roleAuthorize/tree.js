Ext.namespace("sysManage.authorityManage.roleAuthorize.tree");
//选中的树节点
sysManage.authorityManage.roleAuthorize.tree.node = null;

sysManage.authorityManage.roleAuthorize.tree.initTree = function() {
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
	            	sysManage.authorityManage.roleAuthorize.tree.roleTree.getSelectionModel().select(item);
				}
	        }  
	    });
	 sysManage.authorityManage.roleAuthorize.tree.roleTreeStore=roleTreeStore;
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
					//保存当前树节点信息至页面全局变量
					sysManage.authorityManage.roleAuthorize.tree.node = record;
					var toolbar = sysManage.authorityManage.roleAuthorize.main.mainPnl.getDockedItems('toolbar[dock="top"]');
					var btns=toolbar[0].items;
					//选中的是角色，可以修改权限菜单
					if(record.raw.nodeType == 3){
						for (var i=0;i<btns.getCount();i++){
							var btnTmp=btns.get(i);
							btnTmp.enable();
						}
						//权限树
						var authorityTree = sysManage.authorityManage.roleAuthorize.menuTree.tree;
						authorityTree.setDisabled(false);
						var root = authorityTree.getRootNode();
						authorityTree.getStore().proxy.extraParams.parentId = root.raw.id;
						authorityTree.getStore().proxy.extraParams.roleId = record.raw.id;
						authorityTree.getStore().load({  
					            callback:function(){ 
					            	root.collapse(true,function(){ 
					            		authorityTree.expandAll();
					            	})
					            }  
					        });  
					} else {//权限菜单不可编辑
						for (var i=0;i<btns.getCount();i++){
							var btnTmp=btns.get(i);
							btnTmp.disable();
						}
						sysManage.authorityManage.roleAuthorize.menuTree.tree.setDisabled(true);
					}

				}
			}
	});
	sysManage.authorityManage.roleAuthorize.tree.roleTree = roleTree;
	return roleTree;
}
