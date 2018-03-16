Ext.namespace("sysManage.authorityManage.roleAuthorize.main");

sysManage.authorityManage.roleAuthorize.main.initMainPanel = function(){  
	//定义面板
	var mainPanel = Ext.create('Ext.Panel',{
		layout : 'border',                //布局类型 
		border : true, 
		tbar :{
			cls:'whjn-tbar',
			items:[{             
				bizCode : "SAVE",  //按钮的编码
				text:'保存授权',
				disaled:true,
	  			iconCls : 'fa fa-floppy-o fa-lg',                 
				handler : sysManage.authorityManage.roleAuthorize.main.save
			}]
		},//隐藏边框
		items:[             								//用来存放主面板中包含子面板的 数组，
				sysManage.authorityManage.roleAuthorize.tree.initTree(),        //角色树面板
				sysManage.authorityManage.roleAuthorize.menuTree.initTree()       //菜单树面板   
		      ]
	});
	sysManage.authorityManage.roleAuthorize.main.mainPnl = mainPanel;
	return mainPanel;
}	
					

sysManage.authorityManage.roleAuthorize.main.save = function(){
	var roleNode = sysManage.authorityManage.roleAuthorize.tree.node;
	var authorityTree = sysManage.authorityManage.roleAuthorize.menuTree.tree;
	var rootNode = authorityTree.getRootNode();
	var ids = [];
	sysManage.authorityManage.roleAuthorize.main.getChecked(rootNode,ids);
	var saveURL = webContextRoot + '/sys/authority/saveAuthorityMenu';
	Ext.MessageBox.confirm('确认', '您确定要保存么？',
		//回调函数
		function(btn) {
	        //根据选择不同按钮进行操作
			if (btn == 'yes') {//点击确定
				//用来保存需要删除数据的ID
				Ext.Ajax.request({
					url : saveURL,
					params : {
						ids : ids.join(','),
						roleId : roleNode.raw.id
					},
					success : function(response) {
						if (response.responseText != '') {
							var res = Ext.JSON.decode(response.responseText);
							if (res.success) {
								whjn.dlg.showMomentDlg("保存成功!");
							} else {
								whjn.dlg.errTip(res.message);
							}
						}
					}
				});
			}
	});
}


sysManage.authorityManage.roleAuthorize.main.getChecked = function(node, ids){
	var cNode = node.childNodes;
	for (var i = 0;i<cNode.length;i++) {
		if(cNode[i].hasChildNodes()){//如果存在子节点
			sysManage.authorityManage.roleAuthorize.main.getChecked(cNode[i],ids);
		} 
		if(cNode[i].get("checked")){
			ids.push(cNode[i].raw.id);
		}
	}
}