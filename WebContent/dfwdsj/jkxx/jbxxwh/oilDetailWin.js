Ext.ns("dfwdsj.jkxx.jbxxwh.oilDetailWin");

dfwdsj.jkxx.jbxxwh.oilDetailWin.objId = null;

dfwdsj.jkxx.jbxxwh.oilDetailWin.detailCount = 0;

dfwdsj.jkxx.jbxxwh.oilDetailWin.showWin = function(oilWellId){
	dfwdsj.jkxx.jbxxwh.oilDetailWin.objId = oilWellId;
	var winCfg = {
			layout : 'fit',   	  // 布局样式
			width : 1400,	
			height : 600, 
			title : '井矿-小层信息',
			resizable : false,	 // 不允许用户允许拖动窗体边角来控制窗口大小
			autoScroll : true,   // 自动显示滚动条
			closeAction : 'destroy',// 关闭时为隐藏操作
			modal : true,		 // 模态化显示：后方的区域不能点击和编辑
			tbar :{
				cls:'whjn-tbar',
				items:[ {
					text : whjn.constant.addBtnText,
					iconCls : 'fa fa-plus fa-lg',
					bizCode : 'save',
					handler : dfwdsj.jkxx.jbxxwh.oilDetailWin.addHandler
				}, {
					text : whjn.constant.editBtnText,
					iconCls : 'fa fa-pencil fa-lg',
					bizCode : 'edit',
					handler : dfwdsj.jkxx.jbxxwh.oilDetailWin.editHandler
				}, 
//				{
//					text : whjn.constant.delBtnText,
//					iconCls : 'fa fa-trash-o fa-lg',
//					bizCode : 'del',
//					handler : dfwdsj.jkxx.jbxxwh.oilDetailWin.delHandler
//				}, 
				{
					text : whjn.constant.closeBtnText,
					iconCls : 'fa fa-times-circle fa-lg',
					bizCode : 'close',
					handler : dfwdsj.jkxx.jbxxwh.oilDetailWin.closeHandler
				} ],
			},
			items : dfwdsj.jkxx.jbxxwh.oilDetailWin.initPanel()
		};
		var win = new Ext.Window(winCfg);
		dfwdsj.jkxx.jbxxwh.oilDetailWin.win = win;
		win.show();
}


dfwdsj.jkxx.jbxxwh.oilDetailWin.initPanel = function() {
	var className = dfwdsj.jkxx.jbxxwh.oilDetailWin;
	var cols = className.getCols();
	var store = className.getStore();
	var gridBbar = new Ext.PagingToolbar({
		pageSize : whjn.PS,
		store : store,
		displayInfo : true,
		displayMsg : '第{0}条到第{1}条,一共{2}条',
		emptyMsg : '没有记录'
	});
	className.gridBbar = gridBbar;
	
	var gridPnl = Ext.create("Ext.grid.Panel", {
	    region : 'center',
	    border : false,
	    stripeRows:true, //斑马线效果 
	    columnLines: true,
	    selType: "checkboxmodel",
	    columns : cols,
		store : store,
	    bbar:gridBbar
	});
	
	dfwdsj.jkxx.jbxxwh.oilDetailWin.panel = gridPnl;
	return gridPnl;
}


dfwdsj.jkxx.jbxxwh.oilDetailWin.getCols = function(){
	var cols = [];
	cols.push({ 
	   	xtype: 'rownumberer',
	   	text: '序号',
	   	dataIndex: 'xh',
	   	width:40
    },{ 
    	text: 'ID', 
    	hidden : true,
    	dataIndex: 'id', 
    	width:60, 
    	align : 'right'
    },
//    { 
//        text: '油井ID', 
//        dataIndex: 'oilWell.id', 
//        width:60, 
//        hidden :true,
//        align : 'right'
//    },
    { 
    	text: '层位', 
    	dataIndex: 'cw' , 
    	width:80
    },{ 
    	text: '小层编号', 
    	dataIndex: 'xcbh', 
    	width:80
    },{ 
    	text: '层段起始(m)', 
    	dataIndex: 'cdqs', 
    	width:100
    },{ 
    	text: '层段截至(m)', 
    	dataIndex: 'cdjz', 
    	width:100
    },{ 
    	text: '油层倾角(°)', 
    	dataIndex: 'ycqj', 
    	width:100
    },{ 
    	text: '孔数(个)', 
    	dataIndex: 'ks', 
    	width:70
    },{ 
    	text: '小层压力(Pa)', 
    	dataIndex: 'yl', 
    	width:100
    },{ 
    	text: '地层系数', 
    	dataIndex: 'xs', 
    	width:80
    },{ 
    	text: '射开厚度(m)', 
    	dataIndex: 'skhd', 
    	width:100
    },{ 
    	text: '有效厚度(m)', 
    	dataIndex: 'yxhd', 
    	width:100
    },{ 
    	text: '对应斜度(°)', 
    	dataIndex: 'dyxd', 
    	width:100
    },{ 
    	text: '夹层厚度(m)', 
    	dataIndex: 'jchd', 
    	width:100
    },{ 
    	text: '有效渗透率(μm2)', 
    	dataIndex: 'yxstl', 
    	width:130
    },{ 
    	text: '孔隙度', 
    	dataIndex: 'kxd', 
    	width:80
    });
	return cols;
}

dfwdsj.jkxx.jbxxwh.oilDetailWin.getStore = function(){
	
	Ext.define('OilWellDetailModel', {
		extend : 'Ext.data.Model',
		idProperty : 'id',
		fields : [ {
			name : 'id',
			type : 'long'
		}, 
		'xh',
//		{
//			name : 'oilWell.id',
//			type : 'long'
//		},  
		'cw', //层位
		'xcbh',//小层编号
		'cdqs',//层段起始
		'cdjz',//层段截至
		'ycqj', //油层倾角
		'ks',//孔数
		'yl',//小层压力
		'xs',//地层系数
		'skhd', //射开厚度
		'yxhd',//有效厚度
		'dyxd',//对应斜度
		'jchd',//夹层厚度
		'yxstl', //有效渗透率
		'kxd'	//孔隙度
		]
	});

	var store = Ext.create("Ext.data.Store", {
	    model: "OilWellDetailModel",
	    pageSize: whjn.PS,
	    proxy: {
	        type: "ajax",
	        actionMethods: {  //默认提交方式为get，回引发前台汉字传到后台乱码的问题，故提交方式改为POST
	        	create : 'POST',  
	            read   : 'POST',   
	            update : 'POST',  
	            destroy: 'POST'  
	        },
	        url: webContextRoot + '/dfwdsj/oilWell/getOilWellDetailList',
	        reader: {
	        	type : 'json',
				root : 'data',
	            totalProperty: 'totalRecord'
	        }
	    },
	    listeners: {
	    	'beforeload': function(store, opration, eOpts){
	    		this.proxy.extraParams.oilWellId = dfwdsj.jkxx.jbxxwh.oilDetailWin.objId;
	    	}
	    }
	});
	store.load();
	return store;
}

/**
 * 新增
 */
dfwdsj.jkxx.jbxxwh.oilDetailWin.addHandler = function(){
	dfwdsj.jkxx.jbxxwh.oilDetailEntry.currObjId = -1;
	dfwdsj.jkxx.jbxxwh.oilDetailEntry.showWin("新增");
}

/**
 * 编辑
 */
dfwdsj.jkxx.jbxxwh.oilDetailWin.editHandler = function(){
	//菜单面板
	var gridPnl = dfwdsj.jkxx.jbxxwh.oilDetailWin.panel;
	//定义命名空间
	var className = dfwdsj.jkxx.jbxxwh.oilDetailEntry;
	// 判断选择的记录条数
	var rec = dfwdsj.jkxx.jbxxwh.oilDetailWin.getSelected(gridPnl);
	if (rec) {
		if(rec[0].data.isEdit==0){//为0，不可编辑
			Ext.MessageBox.alert('提示', "该记录不可编辑");
		} else {
			//获取菜单Id
			className.currObjId = rec[0].data.id;
			//调用编辑二级页面
			dfwdsj.jkxx.jbxxwh.oilDetailEntry.showWin("编辑");
		}
	}
}

/**
 * 删除
 */
dfwdsj.jkxx.jbxxwh.oilDetailWin.delHandler =function(){
	var className = dfwdsj.jkxx.jbxxwh.oilDetailWin;
	var sm = className.panel.getSelectionModel();
	if (sm.getCount() == 0) {//如果没有选中
		whjn.dlg.infoTip("请选择需要删除的记录!");
		return false;
	} else {
		var delURL = webContextRoot + '/dfwdsj/oilWell/delOilWellDetailByIds';
		Ext.MessageBox.confirm('确认', '您确定要删除选择信息吗？',
			function(btn) {
				//根据选择不同按钮进行操作
				if (btn == 'yes') {//点击确定
					//用来保存需要删除数据的ID
					var ids = [];
					var sel = sm.getSelection();
					for (var i = 0, r; r = sel[i]; i++) {
						ids.push(r.get("id"));
					}
					Ext.Ajax.request({
						url : delURL,
						params : {
							ids : ids.join(',')
						},
						success : function(response) {
							if (response.responseText != '') {
								var res = Ext.JSON.decode(response.responseText);
								if (res.success) {
									whjn.dlg.showMomentDlg("删除成功!");
									//获取数据列表窗口
									var className = dfwdsj.jkxx.jbxxwh.panel;
									//重新加载列表数据
									className.loadRecord();
								} else {
									whjn.dlg.errTip(res.message);
								}
							}
						}
					});
				}
		});
	}
}


/**
 * 关闭
 */
dfwdsj.jkxx.jbxxwh.oilDetailWin.closeHandler= function(){
	dfwdsj.jkxx.jbxxwh.oilDetailWin.win.close();
}


/**
 * 判断选择的信息是否单选
 * @returns {记录(rec)}
 */
dfwdsj.jkxx.jbxxwh.oilDetailWin.getSelected = function(grid){
	//选择条数大于一
	if (grid.getSelectionModel().getCount() > 1) {
		Ext.MessageBox.alert("提示", "一次只能选择一条记录!");
		return null;
	}
	// 没有选择
	if (grid.getSelectionModel().getCount() < 1) {
		Ext.MessageBox.alert("提示", "请选择一条记录!");
		return null;
	} else {//只选择一条
		//获取这条记录
		var rec = grid.getSelectionModel().getSelection();
		return rec;
	}
}


/**
 * 加载数据
 */
dfwdsj.jkxx.jbxxwh.oilDetailWin.loadRecord = function(){
	var className = dfwdsj.jkxx.jbxxwh.oilDetailWin;
	var store = className.panel.getStore();
	store.load();
}