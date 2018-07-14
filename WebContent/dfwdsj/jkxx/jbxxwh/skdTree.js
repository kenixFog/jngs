Ext.ns("dfwdsj.jkxx.jbxxwh.skdTree");



dfwdsj.jkxx.jbxxwh.skdTree.lastTreeNode=null;

dfwdsj.jkxx.jbxxwh.skdTree.showWin = function(btn){
	var winCfg = {
			layout : 'fit',   	  // 布局样式
			width : 300,	
			height : 400, 
			title : '射孔弹选择窗口',
			resizable : false,	 // 不允许用户允许拖动窗体边角来控制窗口大小
			autoScroll : true,   // 自动显示滚动条
			closeAction : 'destroy',// 关闭时为隐藏操作
			modal : true,		 // 模态化显示：后方的区域不能点击和编辑
			tbar :{
				cls:'whjn-tbar',
				items:[ {
					text : whjn.constant.yesBtnText,
					iconCls : 'fa fa-check fa-lg',
					bizCode : 'save',
					handler : function(){
						dfwdsj.jkxx.jbxxwh.skdTree.setValue(btn)
					}
				}, {
					text : whjn.constant.closeBtnText,
					iconCls : 'fa fa-times-circle fa-lg',
					bizCode : 'close',
					handler : dfwdsj.jkxx.jbxxwh.skdTree.closeHandler
				} ],
			},
			items : dfwdsj.jkxx.jbxxwh.skdTree.initTree()
		};
		var win = new Ext.Window(winCfg);
		dfwdsj.jkxx.jbxxwh.skdTree.win = win;
		win.show();
}

dfwdsj.jkxx.jbxxwh.skdTree.initTree = function() {
	 var skqlxStore = Ext.create('Ext.data.TreeStore', {  
	        autoLoad : true,  
	        proxy : {  
	                type : 'ajax',  
	                url : webContextRoot + '/dfwdsj/equipment/getSkd',//请求  
	                reader : {  
	                    type : 'json'
	                } 
	            },  
	        root : {  
	        	id : -1,
	        	code : 'SKD',//节点编码
	            text : '射孔弹选择树', 
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
	            	dfwdsj.jkxx.jbxxwh.skdTree.tree.getSelectionModel().select(item);
//	            	if(dfwdsj.jkxx.jbxxwh.entry.skdId !=-1){
	            		var cNodes = node.childNodes;
	            		for(var i=0;i < cNodes.length;i++){
		                      var n = cNodes[i];
		                      if(n.get("name") == dfwdsj.jkxx.jbxxwh.entry.skd){
		                          n.set("checked" , true);
		                      }
		               }
//	            	}
				}
	        }  
	    });
	 var tree = Ext.create('Ext.tree.Panel', {
			id : 'skqlxTree',
			region : 'center',
			split : true,
			width : 300,
			rootVisible : false,
			useArrows: true,
			store : skqlxStore,
			listeners : {  
	            'checkchange' : function(node, checked, eOpts ){
	            	var checkedNodes = this.getChecked();
	            	for(var i=0;i < checkedNodes.length;i++){
	                      var n = checkedNodes[i];
	                      if(node.get("name") != n.get("name")){
	                          n.set("checked" , false);
	                      }
	               }
	            }
	        } 
	});
	dfwdsj.jkxx.jbxxwh.skdTree.tree = tree;
	return tree;
}


dfwdsj.jkxx.jbxxwh.skdTree.setValue = function(btn){
	var checkedNode = dfwdsj.jkxx.jbxxwh.skdTree.tree.getChecked();
	if(checkedNode.length > 0){
//		dfwdsj.jkxx.jbxxwh.entry.skdId = checkedNode[0].data.id;
		btn.setValue( checkedNode[0].data.text);
		whjn.dlg.showMomentDlg("确认成功！");
		dfwdsj.jkxx.jbxxwh.skdTree.closeHandler();
	} else {
		whjn.dlg.infoTip("请选择射孔弹后再点击确认按钮！");
		return;
	}
	
}


dfwdsj.jkxx.jbxxwh.skdTree.closeHandler= function(){
	dfwdsj.jkxx.jbxxwh.skdTree.win.close();
}
