Ext.namespace("sysManage.comCodeManage.tree");
//选中的树节点
sysManage.comCodeManage.tree.node = null;

sysManage.comCodeManage.tree.initTree = function() {
	//点击树节点触发的事件
//	var ncEvent = function(node, e) {
//		//选中当前节点
//		node.select();
//		//将当前点击的树节点保存在全局变量中，之后可以用到
//		templates.stuMaintain.tree.curTreeNode = node;
//		//获取节点的属性数组
//		var data = node.attributes;
//		//获取主面板的的工具栏控件
//		var toolBar = templates.stuMaintain.main.mainPnl.getTopToolbar();
//		//保存按钮编码的数组
//		var funcObjs = [];
//		//统一框架配置的按钮权限
//		var openModuleObjs = currUser.funcObjCodes;
//		//获取学生面板中选中的学生记录条目数
//		var count = templates.stuMaintain.stuPanel.mainGrid.getSelectionModel().getCount();
//		//对主面板上的按钮进行控制
//		if (node.isLeaf()) { //如果是叶子节点
//			funcObjs = [];
//			var funcObjs1 = [ 'ADD_STU', 'VIEW_STU', 'EDIT_STU', 'DEL_STU' ];
//			for(var i=0;i<funcObjs1.length;i++){
//				for(var j=0;j<openModuleObjs.length;j++){
//					if(funcObjs1[i] == openModuleObjs[j]){
//						funcObjs.push(funcObjs1[i]);
//						break;
//					}
//				}
//			}
//		} else { //非叶子节点
//			funcObjs = [];
//			var funcObjs2 = [ 'VIEW_STU', 'EDIT_STU', 'DEL_STU' ];
//			for(var i=0;i<funcObjs2.length;i++){
//				for(var j=0;j<openModuleObjs.length;j++){
//					if(funcObjs2[i] == openModuleObjs[j]){
//						funcObjs.push(funcObjs2[i]);
//						break;
//					}
//				}
//			}
//		}
//		//调用共通方法，控制按钮显示状况（除了funcObjects中按钮，其余的全部隐藏）
//		atom.enableFuncBtn(toolBar, null, null, "hide", null, funcObjs);
//		//获取学生信息面板的数据加载器
//		var store = templates.stuMaintain.stuPanel.mainGrid.getStore();
//		//当前节点的ID
//		store.baseParams["nodeId"] = data.nodeId;
//		//当前节点的编码
//		store.baseParams["code"] = data.code;
//		//当前节点的类型
//		store.baseParams["type"] = data.type;
//		//根节点的编码
//		store.baseParams["rootCode"] = data.rootCode;
//		//加载学生信息
//		store.load();
//		//清空家庭信息表单
//		templates.stuMaintain.familyPanel.mainGrid.getStore().removeAll();
//		//清空家长信息主面板中的学生学号
//		templates.stuMaintain.familyPanel.qryPnl.find("name", "stuId")[0].setValue("");
//		//清空家长信息主面板中的学生ID（隐藏域）
//		templates.stuMaintain.familyPanel.qryPnl.find("name", "Id")[0].setValue("");
//		//清空学生面板中的查询框数据
//		templates.stuMaintain.stuPanel.qryPnl.find("name", "stuName")[0].setValue("");
//	};

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
