/*
 * 对话框相关
 */
Ext.namespace("whjn.dlg");

/*
 * dlgData 格式: { infType:1 //信息类型： 1警告 2错误 
 * showDetial:"",//显示提示对话框时默认是否显示详细信息
 * clientCode:"",//错误代码 
 * message:"",//错误信息 
 * detailMessage:"",//
 * 详细错认信息 callback:,//本提示对话框关闭后的回调函数 }
 */
/**
 * 提示窗口
 * @type 
 */
whjn.dlg.win = null;

/**
 * 即逝消息窗口
 * @type 
 */
whjn.dlg.momentWin = null;


/**
 * 获得"错误"图标窗口
 * @return {}
 */
whjn.dlg.getErrorIconPnl = function() {
	return new Ext.Panel({
				border : false,
				layout : 'fit',
				html : "<img src='" + whjn.ATOM_IMG_PATH
						+ "ss-error.png' width='50' height='50' />"
			});
};
/**
 * 获得"警告"图标窗口
 * @return {}
 */
whjn.dlg.getWarnIconPnl = function() {
	return new Ext.Panel({
				border : false,
				layout : 'fit',
				html : "<img src='" + whjn.ATOM_IMG_PATH
						+ "ss-warn.png' width='50' height='50' />"
			});
};
/**
 * 初始化窗口
 * @return {}
 */
whjn.dlg.initWin = function() {
	whjn.dlg.edits = [new Ext.form.TextArea({
						value : '',
						readOnly : true
					}), new Ext.form.TextArea({
						value : '',
						readOnly : true
					})];
	var groupManager = new Ext.WindowGroup();
	groupManager.zseed=99999;
	whjn.dlg.win = new Ext.Window({
				manager : groupManager,
				layout : 'border',
				title : '提示',
				width : 500,
				height : 155,
				closable : true,
				closeAction : 'hide',
				maximizable : true,
				modal : true,
				border : false,
				tbar : [{
							text : '关闭',
							iconCls : 'btnIconClose',
							handler : function() {
								whjn.dlg.win.hide();
							}
						}],
				items : [{
							layout : 'border',
							frame : 'true',
							autoScroll : true,
							region : 'north',
							height : 80,
							border : false,
							items : [{
										layout : 'fit',
										region : 'center',
										border : false,
										items : [whjn.dlg.edits[0]]
									}, {
										layout : 'fit',
										region : 'west',
										border : false,
										width : 70,
										bodyStyle : 'padding:10px'
									}]
						}, {
							layout : 'fit',
							autoScroll : true,
							region : 'center',
							collapsible : true,
							collapsed : true,
							title : "详细信息",
							border : false,
							listeners : {
								beforeexpand : function(p, animate) {
									whjn.dlg.win.setHeight(400);
								},
								beforecollapse : function(p, animate) {
									whjn.dlg.win.setHeight(155);
								}
							},
							items : [whjn.dlg.edits[1]]
						}],
				listeners : {
					beforehide : function() {
						if (typeof whjn.dlg.dlgData.callback == "function")
							whjn.dlg.dlgData.callback();
					}
				}
			});
};
/**
 * 获得消息的显示信息
 * @return {}
 */
whjn.dlg.getMessage = function() {
	var errdata = whjn.dlg.dlgData;
	var errinf = "";
	if (errdata.clientCode && errdata.clientCode.length > 0) {
		errinf = "代码：" + errdata.clientCode + "\n";
	}
	if (errdata.message && errdata.message.length > 0) {
		errinf += "信息：" + errdata.message;
	}
	if (errinf.length <= 0 && errdata.stackTrace
			&& errdata.stackTrace.length > 0) {
		errinf += "信息：" + errdata.stackTrace;
	}
	if (errinf.length <= 0 && errdata.detailMessage
			&& errdata.detailMessage.length > 0) {
		errinf += "信息：" + errdata.detailMessage;
	}
	return errinf;
};
/**
 * 获得消息的详细信息
 * @return {}
 */
whjn.dlg.getDetailMessage = function() {
	var res = "";
	if (whjn.dlg.dlgData.detailMessage
			&& whjn.dlg.dlgData.detailMessage.length > 0) {
		res = whjn.dlg.dlgData.detailMessage;
	}
	if (whjn.dlg.dlgData.stackTrace && whjn.dlg.dlgData.stackTrace.length > 0) {
		res = whjn.dlg.dlgData.stackTrace;
	}
	return res;
};
/**
 * 显示窗口
 * @param {} dlgData 数据对象
 */
whjn.dlg.showDlg = function(dlgData) {
	whjn.dlg.dlgData = dlgData;
	if (whjn.dlg.win == null) {
		whjn.dlg.initWin();
	}
	var icoPaneP = whjn.dlg.win.items.get(0).items.get(1);
	if (icoPaneP.items && icoPaneP.items.getCount() > 0) {
		icoPaneP.remove(0, true);
	}
	if (dlgData.infType && dlgData.infType == 1) {
		icoPaneP.add(whjn.dlg.getWarnIconPnl());
	} else {
		icoPaneP.add(whjn.dlg.getErrorIconPnl());
	}
	//窗口展现之后再进行子面板的收缩或展开操作
	whjn.dlg.win.on('show',function(){
		if (dlgData.showDetial) {
			whjn.dlg.win.items.get(1).expand(false);
		} else {
			whjn.dlg.win.restore();
			whjn.dlg.win.items.get(1).collapse(false);
		}
	});
	icoPaneP.doLayout(false);
	whjn.dlg.edits[0].setValue(whjn.dlg.getMessage());
	whjn.dlg.edits[1].setValue(whjn.dlg.getDetailMessage());
	whjn.dlg.win.show();
};

/**
 * 显示即逝窗口
 * @param {} msg 提示信息
 * @param {} backFn 回调函数
 */
whjn.dlg.showMomentDlg = function(msg, backFn) {
	if (whjn.dlg.momentWin == null) {
		whjn.dlg.momentWinEdit = new Ext.form.TextArea({
					region : 'center',
					value : '',
					readOnly : true
				});
		whjn.dlg.momentWin = new Ext.Window({
					layout : 'border',
					title : '提示',
					width : 200,
					height : 80,
					closable : true,
					closeAction : 'hide',
					modal : false,
					allowDomMove : false,
					border : false,
					resizable : false,
					minimizable : false,
					maximizable : false,
					items : [{
						layout : 'fit',
						region : 'west',
						width : 40,
						border : false,
						html : "<img src='" + whjn.ATOM_IMG_PATH
								+ "ss-right.png' width='40' height='40' />"
					}, whjn.dlg.momentWinEdit]
				});
		whjn.dlg.hideMomentWin = function() {
			if (whjn.dlg.momentWin.isVisible()) {
				whjn.dlg.momentWin.hide();
				if (backFn && typeof backFn == "function")
					backFn();
			}
		};
	}

	whjn.dlg.momentWinEdit.setValue(msg);
	whjn.dlg.momentWin.show();
	setTimeout(whjn.dlg.hideMomentWin, 800);
};


// 错误提示
whjn.dlg.errTip = function(msg, e) {
	Ext.MessageBox.show({
		title : '错误信息',
		msg : msg,
		buttons : Ext.MessageBox.OK,
		animateTarget : e,
		icon : Ext.MessageBox.ERROR
	});
};

// 一般提示
whjn.dlg.infoTip = function(msg, e) {
	Ext.MessageBox.show({
		title : '系统信息',
		msg : msg,
		buttons : Ext.MessageBox.OK,
		animateTarget : e,
		icon : Ext.MessageBox.INFO
	});
};

//警告
whjn.dlg.warnTip = function(msg, e) {
	Ext.MessageBox.show({
		title : '系统信息',
		msg : msg,
		buttons : Ext.MessageBox.OK,
		animateTarget : e,
		icon : Ext.MessageBox.WARNING
	});
};