/**
 *
 * echarts的公共panel
 */
Ext.define('ext.ux.echart.PanelEcharts', {
    extend: 'Ext.panel.Panel',
    requires: [],
    alias: [
        'widget.panelecharts'
    ],
    width: this.width,//当用afterrender方法时这里的width、height都是固定的需要自己给定。
    height: this.height,
    option: {},
    initComponent: function () {
        var me = this;
        me.on({
        //在extjs5中使用echarts的时候如何才能让图表随浏览器的改变？网上有很多方法，官网使用的是resize
            //自适应当前panel的宽和高，图形也会自适应
            resize: function (field,width,height ) {
                this.width=width
                this.height=height
                var overTimeChart = echarts.init(field.getEl().dom);
                var option = me.option;
                overTimeChart.setOption(option,true);

            },
        })
        me.callParent(arguments);
    }
});