Ext.namespace("dfwdsj.materialManage.category.panel");

dfwdsj.materialManage.category.panel.currentRecord=null;

dfwdsj.materialManage.category.panel.initGridPnl = function() {
	var className = dfwdsj.materialManage.category.panel;
	var gridBbar = new Ext.PagingToolbar({
		pageSize : whjn.PS,
		store : '',
		displayInfo : true,
		displayMsg : '第{0}条到第{1}条,一共{2}条',
		emptyMsg : '没有记录'
	});
	className.gridBbar = gridBbar;
	
	var editPlugin = Ext.create('Ext.grid.plugin.CellEditing', {
        clicksToEdit: 1
    });
	className.editPlugin = editPlugin;
	
	var gridPnl = Ext.create("Ext.grid.Panel", {
	    title:'数据列表',
	    region : 'center',
	    border : false,
	    stripeRows:true, //斑马线效果 
	    columnLines: true,
	    selType: "checkboxmodel",
	    columns : [],
		store : '',
		plugins: editPlugin,
	    bbar:gridBbar,
	    listeners : {
			cellclick : function(grid, rowIndex, columnIndex, e) {
				var record = grid.getStore().getAt(rowIndex); // 得到本行记录
				dfwdsj.materialManage.category.panel.currentRecord = record;
			}
		}
	});
	className.gridPnl=gridPnl;
	return gridPnl;
}

//重新加载面板-动态列

dfwdsj.materialManage.category.panel.reCreatePnl = function(record){
	// record对应的field
	var fields = [];
	// 表头
	var cols = [];
	// 请求地址
	var url='';
	if(record.raw.leaf =='1'){//叶子节点，显示分类的字段列表
		//获取字段列表
		url = '/dfwdsj/equipment/getEquipmentFieldList';
		fields.push({
			name : 'id',
			type : 'long'
		}, {
			name : 'equipmentType.id',
			type : 'long'
		},  'fieldName', 'fieldCode','fieldType','fieldLength','fieldContent');
		
		
		cols.push({ 
        	text: 'ID', 
        	hidden : true,
        	dataIndex: 'id', 
        	width:100, 
        	align : 'right'
	    },{ 
	        text: '分类ID', 
	        dataIndex: 'equipmentType.id', 
	        width:100, 
	        hidden :true,
	        align : 'right'
	    },{ 
	    	text: '字段名称', 
	    	dataIndex: 'fieldName' , 
	    	width:120,
	    	editor:new Ext.form.TextField({  
                allowBlank:false  
            })
	    },{ 
	    	text: '字段编码', 
	    	dataIndex: 'fieldCode', 
	    	width:120,
	    	editor:new Ext.form.TextField({  
                allowBlank:false  
            })
	    },{ 
	    	text: '字段类型', 
	    	dataIndex: 'fieldType' , //dfwdsj.materialManage.category.ZDLX
	    	width:120,
	    	editor: new Ext.form.ComboBox({  
                editable : false,
                valueField : "value",
        		displayField : "name",
    			store : dfwdsj.materialManage.category.ZDLX,
    			allowBlank : false,
    			
            }),
            renderer : function(value, p, r) {
				return whjn.getNameByCode(value,dfwdsj.materialManage.category.ZDLX);
			}
	    },{ 
	    	text: '字段长度', 
	    	dataIndex: 'fieldLength', 
	    	width:120,
	    	editor:new Ext.form.NumberField()
	    },{ 
	    	text: '字段内容', 
	    	dataIndex: 'fieldContent', 
	    	width:120,
	    	editor:new Ext.form.TextField({  
                allowBlank:true  
            })
	    });
		
		
		
	} else{//非叶子节点，显示分类列表
		//获取分类列表
		url = '/dfwdsj/equipment/getEquipmentTypeList';
		
		fields.push({
			name : 'id',
			type : 'long'
		}, {
			name : 'parentId',
			type : 'long'
		},  'typeCode', 'typeName','isLeaf', 'sysOrg.orgName',
		'createUser.realName','lastEditUser.realName',
		{
			name: 'createTime',
			type : 'datetime',
			dateFormat : 'Y-m-d H:i:s'
		},{
			name:'lastEditTime',
			type : 'datetime',
			dateFormat : 'Y-m-d H:i:s'
		});
		
		cols.push({ 
        	text: 'ID', 
        	hidden : true,
        	dataIndex: 'id', 
        	width:100, 
        	align : 'right'
	    },{ 
	        text: '上级节点ID', 
	        dataIndex: 'parentId', 
	        width:100, 
	        hidden :true,
	        align : 'right'
	    },{ 
	    	text: '分类名称', 
	    	dataIndex: 'typeName' , 
	    	width:120,
	    	editor:new Ext.form.TextField({  
                allowBlank:false  
            }) 
	    },{ 
	    	text: '分类编码', 
	    	dataIndex: 'typeCode', 
	    	width:120,
	    	editor:new Ext.form.TextField({  
                allowBlank:false  
            })
	    },{ 
	    	text: '是否叶节点', 
	    	dataIndex: 'isLeaf',
	    	width:120,
	    	editor: new Ext.form.ComboBox({  
                editable : false,
                valueField : "name",
        		displayField : "value",
    			store : dfwdsj.materialManage.category.yesOrNoArray,
    			allowBlank : false
            }),
            renderer : function(value, p, r) {
				return whjn.getNameByCode(value,dfwdsj.materialManage.category.yesOrNoArray);
			}
	    },{ 
	    	text: '创建人', 
	    	dataIndex: 'createUser.realName' , 
	    	width:90,
	    	hidden :true
	    },{ 
	    	text: '最后修改人', 
	    	dataIndex: 'lastEditUser.realName' , 
	    	width:90,
	    	hidden :true
	    },{ 
	    	text: '所属组织机构', 
	    	dataIndex: 'sysOrg.orgName' , 
	    	width:300,
	    	sortable : false
	    },{ 
	    	text: '创建时间', 
	    	dataIndex: 'createTime' , 
	    	width:160,
	    	sortable : false
	    },{ 
	    	text: '修改时间', 
	    	dataIndex: 'lastEditTime' , 
	    	width:160,
	    	sortable : false
	    });
	}
	
	Ext.define('model', {
		extend : 'Ext.data.Model',
		idProperty : 'id',
		fields : fields
	});
	
	var store = Ext.create("Ext.data.Store", {
	    model: "model",
	    pageSize: whjn.PS,
	    proxy: {
	        type: "ajax",
	        actionMethods: {  //默认提交方式为get，回引发前台汉字传到后台乱码的问题，故提交方式改为POST
	        	create : 'POST',  
	            read   : 'POST',   
	            update : 'POST',  
	            destroy: 'POST'  
	        },
	        url: webContextRoot + url,
	        reader: {
	        	type : 'json',
				root : 'data',
	            totalProperty: 'totalRecord'
	        }
	    },
	    listeners: {
	    	'beforeload': function(store, opration, eOpts){
	    		var node = dfwdsj.materialManage.category.tree.node;
	    		this.proxy.extraParams.nodeId = node.raw.id;
	    	}
	    }
	});
	store.load();
	var className = dfwdsj.materialManage.category.panel;
	// 绑定PagingToolbar的store
	className.gridBbar.bind(store);
	// 重新配置grid,使用不同的Store和ColumnModel
	className.gridPnl.reconfigure(store, cols);
}


