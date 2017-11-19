Ext.define("Ext.ux.comboboxtree", {  
    extend: "Ext.form.field.Picker", 
    xtype : "ux-comboboxtree",
    requires: ["Ext.tree.Panel"], 
    initComponent: function() {  
        var self = this;  
        Ext.apply(self, {  
            fieldLabel: self.fieldLabel,  
            labelWidth: self.labelWidth  
        });  
        self.callParent();  
    },
    submitValue:'',
    createPicker: function() {  
        var self = this;   
        self.picker = new Ext.tree.Panel({  
            height: 300,  
            border:true,
            autoScroll: true,  
            floating: true,  
            focusOnToFront: false,  
            shadow: true,  
            ownerCt: this.ownerCt,  
            useArrows: true,  
            store : self.store,  
            rootVisible: true,
            viewConfig: {
            	onCheckboxChange: function(e,t) {
            		if (self.multiSelect) {//多选，带复选框
            			var item = e.getTarget(this.getItemSelector(),this.getTargetEl()), record;
            			if (item) {
            				record = this.getRecord(item);//获取当前选中的节点
            				var checked = !record.get('checked');//获取当前复选框状态
            				record.set('checked',checked);
            				var cascade = self.cascade;   //级联方式:1.child子级联;2.parent,父级联；3,both全部级联;
            				if (checked == true) { //节点被选中，根据级联方式设置父子节点为选中  
                                if (cascade == 'both' || cascade == 'child' || cascade == 'parent') {  
                                    if (cascade == 'child' || cascade == 'both') {  
                                        if (!record.get("leaf") && checked) record.cascadeBy(function(record) {  
                                            record.set('checked', true);  
                                        });  
                                    }  
                                    if (cascade == 'parent' || cascade == 'both') {  
                                        pNode = record.parentNode;  
                                        for (; pNode != null; pNode = pNode.parentNode) {  
                                            pNode.set("checked", true);  
                                        }  
                                    }  
                                }  
                            } else if (checked == false) {  
                                if (cascade == 'both' || cascade == 'child' || cascade == 'parent') {  
                                    if (cascade == 'child' || cascade == 'both') {  
                                        if (!record.get("leaf") && !checked) record.cascadeBy(function(record) {  
                                            record.set('checked', false);  
                                        });  
                                    }  
                                }  
                            }
            			}
            			var records = self.picker.getView().getChecked(),  
                        names = [],  
                        values = [];  
                        Ext.Array.each(records,  
                        function(rec) {  
                            names.push(rec.get('text'));  
                            values.push(rec.get('id'));  
                        });  
                        self.setSubmitValue(values.join(',')); // 隐藏值  
                        self.setDisplayValue(names.join(',')); // 显示值
            		}
            	}
            }
        });  
        self.picker.on({
            itemclick: function(tree, record, item, index, e, options) {  
                var multiSelect = self.multiSelect; //单选或者多选
                var onlyChooseLeaf = self.onlyChooseLeaf;
            	if (!multiSelect) {  //单选
            		if(onlyChooseLeaf){//只能选择叶节点
            			if (record.get('leaf')) {  
                            self.setSubmitValue(record.get('id')); // 隐藏值  
                            self.setDisplayValue(record.get('text')); // 显示值  
                            self.eleJson = Ext.encode(record.raw);
        					self.collapse();    
                        } else {  
                            self.setSubmitValue(''); // 隐藏值  
                            self.setDisplayValue(''); // 显示值  
                        }
            		} else {
            			self.setSubmitValue(record.get('id')); // 隐藏值  
                        self.setDisplayValue(record.get('text')); // 显示值
                        self.eleJson = Ext.encode(record.raw);
    					self.collapse();    
            		}
                }
            }  
        });  
        return self.picker;  
    },  
    alignPicker: function() {  
        var me = this,  
        picker, isAbove, aboveSfx = '-above';  
        if (this.isExpanded) {  
            picker = me.getPicker();  
            if (me.matchFieldWidth) {  
                picker.setWidth(me.bodyEl.getWidth());  
            }  
            if (picker.isFloating()) {  
                picker.alignTo(me.inputEl, "", me.pickerOffset); // ""->tl  
                isAbove = picker.el.getY() < me.inputEl.getY();  
                me.bodyEl[isAbove ? 'addCls': 'removeCls'](me.openCls + aboveSfx);  
                picker.el[isAbove ? 'addCls': 'removeCls'](picker.baseCls + aboveSfx);  
            }  
        }  
    },
    
    getSubmitValue : function() {
		if(this.submitValue == undefined){
			this.submitValue = '';
		}
		return this.submitValue;
	},
	getDisplayValue : function(){
		if(this.value == undefined){
			this.value = '';
		}
		return this.value;
	},
	setSubmitValue : function(submitValue){
		this.submitValue = submitValue;
	},
	setDisplayValue : function(value){
		this.setValue(value);
	},
}); 