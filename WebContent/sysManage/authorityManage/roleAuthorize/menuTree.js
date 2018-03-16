Ext.namespace("sysManage.authorityManage.roleAuthorize.menuTree");
//选中的树节点
sysManage.authorityManage.roleAuthorize.menuTree.node = null;

sysManage.authorityManage.roleAuthorize.menuTree.initTree = function() {
	 var menuTreeStore = Ext.create('Ext.data.TreeStore', {  
	        proxy : {  
	                type : 'ajax',  
	                url : webContextRoot + '/sys/authority/getAuthorityTree',//请求  
	                reader : {  
	                    type : 'json'
	                } 
	            },  
	        root : {  
	        	id : -1,
	        	code : 'MENU_CODE',//节点编码
	            text : '功能菜单树', 
	    		type : 0, //节点类型				
	            expanded : false,
	            leaf : false
	        },  
	        listeners : {  
	            'beforeexpand' : function(node,eOpts){  
	            	//角色节点
	            	var roleNode = sysManage.authorityManage.roleAuthorize.tree.node;
	            	//点击菜单会将节点的id通过ajax请求 ,查询子菜单
	                this.proxy.extraParams.parentId = node.raw.id; 
	                //角色Id
	                this.proxy.extraParams.roleId = roleNode.raw.id;
	            }
	        }  
	    });
	 sysManage.authorityManage.roleAuthorize.menuTree.menuTreeStore=menuTreeStore;
	 var tree = Ext.create('Ext.tree.Panel', {
			id : 'menuTree',
			region : 'center',
			split : true,
			title : '功能菜单树',
			margins : '2 0 0 0',
			collapsible : true,
			animCollapse : true,
			rootVisible : true,
			store : menuTreeStore,
			  listeners : {  
		            'checkchange' : function(node, checked, eOpts ){
		            		sysManage.authorityManage.roleAuthorize.menuTree.setChecked(node,checked)
		            }
		        }  
	});
	sysManage.authorityManage.roleAuthorize.menuTree.tree = tree;
	return tree;
}

/**
 * 权限级联关系修改
 */
sysManage.authorityManage.roleAuthorize.menuTree.setChecked = function (node, flag){
	if(flag){
		var pNode  = node.parentNode;
		if(pNode){
			pNode.set('checked',flag);
			sysManage.authorityManage.roleAuthorize.menuTree.setChecked(pNode,flag);
		}
	} else {
		var cNodes = node.childNodes;
		if(cNodes.length>0){
			for(var i=0;i<cNodes.length;i++){  //从节点中取出子节点依次遍历
    			 cNodes[i].set('checked', flag);
    			 sysManage.authorityManage.roleAuthorize.menuTree.setChecked(cNodes[i], flag);
    		 }
		}
	}
}
