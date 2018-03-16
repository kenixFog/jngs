/*
 * 存放每个模块必须引入的函数,和常用的函数
 * @include "comdlg.js"
 * @include "constant.js"
 */
Ext.namespace("whjn");


/**
 * 打开面板
 */
whjn.openModule=function(newMainPanelFn,initModuleDataFn,mdlCfg){
	Ext.onReady(function(){
		if (document.body==null){
			alert("当前函数必须在页面载入后调用");
			return;
		}
		Ext.QuickTips.init();
		entryFnTmp();
	});
	if (!mdlCfg){
		mdlCfg={needLoadUserInfo:true,funcBtnControl:true};
	}
	if (!whjn.openModule_chkCfg(mdlCfg)) return;
	var entryFnTmp=function(){
		var paramTmp={reqData:Ext.JSON.encode(mdlCfg)};
		Ext.Ajax.request({
			url: webContextRoot+'/sys/comCode/getComCode',
			method : 'POST',
			async: false,
			params : paramTmp,
			success : function(response, options) {
				var res = Ext.JSON.decode(response.responseText);
				var data=res.data;
				//设置公共代码的值
				whjn.openModule_SetCfgInitData(mdlCfg,data);
				//打开面板
				whjn.openModule_Impl(newMainPanelFn,initModuleDataFn,mdlCfg);
			},
			failure : function() {
				whjn.dlg.errTip("公共代码获取错误！")
			}
		});
	}
	
};

/**
 * 面板展现具体实现
 */
whjn.openModule_Impl=function(newMainPanelFn,initModuleDataFn,mdlCfg){
	var paintFnTmp=function(){
		var pnl=null;
		var returnPnl=!(mdlCfg && mdlCfg['needNewView']===false);
		try{
			pnl=whjn.openModule_GetPnl(newMainPanelFn,mdlCfg,returnPnl);
		}catch(e){
			whjn.alertErrorInfo(e,"初始化当前功能的页面出错");
		}
		if (!returnPnl) return;
		if (pnl){
			var viewport=new Ext.Viewport({
	           layout:'fit',
	           items:[pnl]
			});	
			if (mdlCfg.funcBtnControl!==false){
				var tbar = pnl.getDockedItems('toolbar[dock="top"]');
				if (tbar) {
					whjn.enableFuncBtn(tbar,null,null,mdlCfg.btnCtrlMode);
				}
			}
		}
	}
	if (initModuleDataFn){
		whjn.openModule_initFn(initModuleDataFn,paintFnTmp);
	}else{
		paintFnTmp();
	}	
}


/**
 * 通过bizCode设置工具栏中功能(包括菜单)的按钮状态
 * 有bizCode的按钮或菜单是需要判断权限的，没有权限设成灰色
 * @param {Ext.Toolbar} toolbar 要设置按钮所在的toolbar
 * @param {Number} userId 用户Id，可不传，默认currUser["userId"]
 * @param {Number} muneId 当前功能Id，可不传，默认request["nodeId"]
 * @param {String} btnCtrlMode 按钮控制模式（hide或disable）默认disable
 * @param {Boolean} hasObjNoOp 如果按钮对象有权限是否不操作，true不操作，false操作，默认false
 * @param {Array} funcObjCodes 按钮编码数据
 */
whjn.enableFuncBtn=function(toolbar,userId,muneId,btnCtrlMode,hasObjNoOp,funcObjCodes){
	if (!toolbar){
		alert("工具栏不存在");
	}
	var fnTmp=function(cntrObj,objCodeArr){
		var btns=toolbar[0].items;
		for (var i=0;i<btns.getCount();i++){
			var btnTmp=btns.get(i);
			var bizCode=null;
			if (btnTmp.initialConfig) bizCode=btnTmp.initialConfig["bizCode"];
			if (bizCode){
				if (btnCtrlMode=="hide"){
					if (objCodeArr.indexOf(bizCode)!=-1) {
						if (!hasObjNoOp){
							if (btnTmp.disabled) btnTmp.enable();
							if (btnTmp.hidden) btnTmp.show();
						}
					}
					else btnTmp.hide();
				}else{
					if (objCodeArr.indexOf(bizCode)!=-1) {
						if (!hasObjNoOp){
							if (btnTmp.disabled) btnTmp.enable();
							if (btnTmp.hidden) btnTmp.show();
						}
					}
					else btnTmp.disable();
				}
			}
//			if (btnTmp.menu){
//				fnTmp(btnTmp.menu,objCodeArr);
//			}
		}
	}
	//如果是二级页面传入的code则不需要后台处理，直接返回
	if (funcObjCodes && Ext.isArray(funcObjCodes)){
		fnTmp(toolbar,funcObjCodes);
		return;
	}
	//当前用户ID
	if(!userId) userId = currUser['userId'];
	//当前子页面ID
	if (!muneId) muneId = request['nodeId'];
	
	var surlTmp=webContextRoot + '/sys/menu/getButton?userId='+userId +'&muneId=' + muneId;
	Ext.Ajax.request({
		url: surlTmp,
		method : 'POST',
		async: false,
		success : function(response, options) {
			var obj = eval("(" + response.responseText + ")");
			fnTmp(toolbar,obj.objCodeArr);
		},
		failure : function() {
		}
	});
}


/**
 * 初始化函数的调用
 * @private
 */
whjn.openModule_initFn=function(initModuleDataFn,successFn){
	if (typeof initModuleDataFn=="function"){
		var formCfg=null;
		try{
			formCfg=initModuleDataFn();
		}catch(e){
			whjn.alertErrorInfo(e,"初始化数据的函数调用出错");
		}
		if (formCfg!=null){
			if (typeof formCfg=="object"){
				var fnTmp=formCfg["success"];
				formCfg["success"]=function(form, action){
					try{
						if (fnTmp) fnTmp(form, action);
						successFn();
					}catch(e){
						whjn.alertErrorInfo(e,"初始化数据的成功回调出错");
					}
				}
				if (formCfg["goOnWhenFailed"]===true){
					var failFnTmp=formCfg["failure"];
					formCfg["failure"]=function(form, action){
						try{
							if (failFnTmp) failFnTmp(form, action);
							successFn();
						}catch(e){
							whjn.alertErrorInfo(e,"初始化数据的失败回调出错");
						}
					}
				}
				var hiddenForm=whjn.getHiddenMainForm();
				try{
					hiddenForm.submit(formCfg);
				}catch(e){
					whjn.alertErrorInfo(e,"初始化数据发起请求出错");
				}
			}else{
				alert('初始化参数的函数必须返回JSON对象');
			}
		}else{
			alert('初始化参数的函数不能返回空值(Null)');
		}
	}else{
		alert('初始化参数必须是函数');
	}	
}


/**
 * 获得画面入口面板
 * @private
 */
whjn.openModule_GetPnl=function(newMainPanelFn,mdlCfg,returnPanel){
	if (typeof newMainPanelFn=="function"){
		var pnl;
		if (mdlCfg && mdlCfg.newPnlParam){
			if (Ext.isArray(mdlCfg.newPnlParam)){
				pnl=newMainPanelFn.apply(null,mdlCfg.newPnlParam);
			}else pnl=newMainPanelFn(mdlCfg.newPnlParam);
		}else{
			pnl=newMainPanelFn(null);
		}
		if (returnPanel!==false){
			if (!pnl){
				atom.showErrorDlg('传入的入口函数，没有返回Panel');
			}else{
				return pnl;
			}				
		}
	}else{
		alert('必须传入入口函数,当前是'+(typeof newMainPanelFn));
	}
	return null;
}


/**
 * 对公用代码和常量消息进行赋值
 * @param {} mdlCfg
 * @param {} data
 */
whjn.openModule_SetCfgInitData=function(mdlCfg,data){
	if (mdlCfg.getCodeCfg){//公用代码初始化
		var codeVar=mdlCfg.getCodeCfg.codeVar;
		var codeSign=mdlCfg.getCodeCfg.groupId || mdlCfg.getCodeCfg.groupCode;
		var script="";
		for (var i=0;i<codeVar.length;i++){
			script+=codeVar[i]+'='+Ext.JSON.encode(data.codeData["comCode_"+codeSign[i]])+";";
		}
		if (script) eval(script);
	}
	
};



/**
 * 检查公共代码参数是否匹配
 * @param {} mdlCfg
 * @return {Boolean}
 */
whjn.openModule_chkCfg=function(mdlCfg){
	if (mdlCfg){
		if (mdlCfg.getCodeCfg){
			var codeSign=mdlCfg.getCodeCfg.groupId || mdlCfg.getCodeCfg.groupCode;
			if (Ext.isArray(codeSign) && Ext.isArray(mdlCfg.getCodeCfg.codeVar) 
				&& codeSign.length==mdlCfg.getCodeCfg.codeVar.length){
			}else{
				atom.showErrorDlg("取公用代码配置参数传递错误，请修正！");
				return false;
			}
		}
	}
	return true;
}




/**
 * 得到一个隐藏的MainForm，用于发起请求
 * @return {Ext.form.BasicForm}
 */
whjn.getHiddenMainForm=function(){
	if (whjn.hiddenMainForm) {
		var form=whjn.hiddenMainForm.getEl().dom;
		if (form.enctype!="application/x-www-form-urlencoded")
			form.enctype="application/x-www-form-urlencoded";
		if (form.method!="post") form.method="post";
		whjn.clearFormInputValue(form);
		return whjn.hiddenMainForm;
	}
	else{
        var id = Ext.id();
        var form = document.createElement('form');
        form.id = id;
        form.name = id;
        form.className = 'x-hidden';
        form.method="post";
        document.body.appendChild(form);
        
		var f=new Ext.form.Basic(form,{});
		whjn.hiddenMainForm=f;
		return whjn.hiddenMainForm;
	}
}
/**
 * 得到一个隐藏的Frame，用于发起请求,一般用于附件下载
 * @return {Iframe的对象引用}
 */
whjn.getHiddenFrame=function(){
	if (whjn.hiddenFrame) return whjn.hiddenFrame;
	else{
        var id = Ext.id();
        var frame = document.createElement('iframe');
        frame.id = id;
        frame.name = id;
        frame.className = 'x-hidden';
        if(Ext.isIE){
            frame.src = Ext.SSL_SECURE_URL;
        }
        document.body.appendChild(frame);
        if(Ext.isIE){
           document.frames[id].name = id;
        }
        whjn.hiddenFrame=frame;
		return whjn.hiddenFrame;
	}
}
/**
 * 附件下载，数据库结构必须符合项目约定
 * @param {String} tabName 附件表名
 * @param {Number} accId 附件ID
 * @param {String} fileName 附件名(可以不传，对于1：1必传，1：n取数据库中的信息)
 * @return {Boolean}
 */
whjn.downAccData=function(tabName,accId,fileName){
	var surl=webContextRoot+'/commonAction.do?method=getAccData&tabName='
		+tabName+'&accId='+accId;
	if (fileName) surl+="&fileName="+encodeURIComponent(fileName);
	var frameTmp=atom.getHiddenFrame();
	frameTmp.contentWindow.location.href=surl;
	return false;
}


whjn.alertErrorInfo=function(e,strMsg){
	var descTmp=e.description;
	if (!descTmp) descTmp=e.toString();
	if (strMsg) strMsg+=";"+descTmp;
	else strMsg=descTmp;
	alert(strMsg);
}


/**
 * 设置form中所有对象都为不可用（disabled），一般用于查看界面
 * @param {Ext.form.FormPanel} formPanel
 */
whjn.setFormReadOnly = function(formPanel,disabled){
	var children=formPanel.items;
	if (!children) return;
	if (disabled===true) disabled=true;else disabled=false;
	for (var i=0;i<children.getCount();i++){
		var childTmp=children.get(i);
		if (childTmp.items){
			whjn.setFormReadOnly(childTmp,disabled);
		}
		var xtype=childTmp.getXType();
		if (childTmp.isFormField) {
			if (xtype=="hidden") continue;
			if(xtype == "btntextfield" || Ext.isEmpty(xtype)){
				if(childTmp.readOnly){
					var classType = "x-item-disabled";
					childTmp.getEl().dom.disabled = true;
					if(childTmp.getEl().dom.className.indexOf(classType) < 0)
						childTmp.getEl().dom.className += " " + classType;
				}
			}else{
				childTmp.setDisabled(disabled);
			}
			
			
		}else{
			if (xtype=="button") childTmp.setDisabled(disabled);
		}
	}	
};

/**
 * 验证Panel中的表单域中的输入是否有效，被disabled和不可见的不验证。
 * @param {Ext.Panel} formPanel
 */
whjn.validateForm=function(formPanel){
	var formFlds=[];
	var fn = function(c){
        if(c.doLayout && c != formPanel){
            if(c.items){
                c.items.each(fn);
            }
        }else if(c.isFormField){
            formFlds.push(c);
        }
    }
    formPanel.items.each(fn);
    if (formFlds.length==0) return "";
	var str=[];
	for (var i=0;i<formFlds.length;i++){
		var childTmp=formFlds[i];
		if (!childTmp.disabled && childTmp.isVisible() && !childTmp.validate()){
			var fieldLabel=childTmp.initialConfig["fieldLabel"];
			var b=true;
			for (var j=0;j<str.length;j++){
				if (str[j]==fieldLabel) {
					b=false;
					break;
				}
			}
			if (b) str.push(fieldLabel);
		}
	}
	if (str.length>0) return "有如下字段:"+str.toString()+";\n数据不合法,请查看提示，重新填写！";
	return "";        
};


/**
 * 在公用代码数组中插入一个空格，返回新数组，一般用于列表查询时
 * 公用代码数组格式为：[[代码，名称，是否在用(布尔值)],...]
 * @param {Array} comCodeArr 代码数组
 * @return {Array} 新的公用代码数组
 */
whjn.getAddBlankValue=function(comCodeArr){
	var newArr=[['','　',true]];//空格用中文全角空格
	if (!comCodeArr) {
		atom.showErrorDlg("传入的数组为空!");
		return newArr;
	}
	for(var i=0;i<comCodeArr.length;i++){
		newArr.push(comCodeArr[i]);
	}
	return newArr;	
}

/** 
 * 刷新树 刷新当前节点，展开或不展开当前节点，选中当前节点 
 * @param treePanel  
 * @param currentNodeId 当前节点id 
 */  
whjn.refreshTreePnl = function (treePanel, currentNodeId){  
	var currentNode = treePanel.getStore().getNodeById(currentNodeId); //当前节点删除了就不存在了  
    var path = currentNode.getPath('id'); 
    //刷新节点，展开节点，选中节点  
    treePanel.getStore().load({  
            node : currentNode,  
            callback:function(){  
//                if(currentNode && currentNode.data.expanded){  
//                    //之前展开的还是展开  
//                    treePanel.expandPath(path);  
//                } 
            	treePanel.expandPath(path); 
                treePanel.selectPath(path);  
            }  
        });  
}  


whjn.searchTreePnl = function(treePnl, nodeId){
	//根节点
	var root = treePnl.getStore().tree.root;
	//搜索节点
	whjn.searchNodeById(treePnl, root, nodeId);
	
}

whjn.searchNodeById = function(treePnl, node, id){
	var childs= node.childNodes;//获取子节点
	for(var i=0; i < childs.length; i++){
        var cNode = childs[i];
        if(cNode.raw.id == id){
        	treePnl.itemClick(cNode);
        	return;
        }
        cNode.expand();//展开当前节点
        whjn.searchNodeById(treePnl, cNode,id);//递归调用
     }
	
}

/**
 * 根据Code返回对应的Name
 * @param {String} code
 * @param {Array} codeNameArray 二维数组其中，一个数组中分别为：code和name
 * @return {String}
 */
whjn.getNameByCode=function(code,codeNameArray){
	if (!codeNameArray) return "";
	for (var i=0;i<codeNameArray.length;i++){
		var objTmp=codeNameArray[i];
		if (objTmp[0]==code) return objTmp[1];
	}
	return "";
}

