
Ext.define('Ext.ux.form.btntextfield',{
	extend: 'Ext.form.TriggerField',
	alias: 'widget.btntextfield',
	triggerCls : "x-form-btn-trigger",	
    // private
    initComponent: function(){
        Ext.ux.form.btntextfield.superclass.initComponent.call(this);
        
        this.addEvents(
            /**
             * @event btnclick
             * 当按钮被点击后触发此事件
             * @param {Ext.ux.form.NewBtnTextField} this
             */
            'btnclick'
        );
    },
    
    onTriggerClick: function(){
        if(this.disabled){
            return;
        }    	
    	this.fireEvent('btnclick', this);
    }
})

//
//
//
//Ext.ux.form.NewBtnTextField = Ext.extend(Ext.form.TriggerField,  {
//    /**
//     * @cfg {String} triggerClass
//     * An additional CSS class used to style the trigger button.  The trigger will always get the
//     * class 'x-form-trigger' and triggerClass will be <b>appended</b> if specified (defaults to 'x-form-btn-trigger')
//     * 
//     */
//    triggerClass : 'x-form-btn-trigger',	
//    // private
//    initComponent: function(){
//        Ext.ux.form.NewBtnTextField.superclass.initComponent.call(this);
//        
//        this.addEvents(
//            /**
//             * @event btnclick
//             * 当按钮被点击后触发此事件
//             * @param {Ext.ux.form.NewBtnTextField} this
//             */
//            'btnclick'
//        );
//    },
//    
//    onTriggerClick: function(){
//        if(this.disabled){
//            return;
//        }    	
//    	this.fireEvent('btnclick', this);
//    }
//});
//Ext.define('btntextfield', Ext.ux.form.NewBtnTextField);