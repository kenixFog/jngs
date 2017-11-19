/*
 * js常量定义，如公用的按钮名称等
 */
Ext.namespace("whjn.constant");
// 系统级js常量区域



/**
 * SmallInt最大值
 * 
 * @type {Number}
 */
whjn.constant.maxSmallInt = 32767;
/**
 * 附件属性 add by zhoux
 * 
 * @type Number
 */
whjn.constant.Addition_Property = 105333;
/**
 * Int最大值
 * 
 * @type {Number}
 */
whjn.constant.maxInt = 2147483647;

/**
 * 默认模式名
 * 
 * @type {String}
 */
whjn.constant.DFT_SCHEMA = "WHJN";
/**
 * 日期格式:Y-m-d
 * 
 * @type {String}
 */
whjn.constant.dataFormateYMD = "Y-m-d";
/**
 * 日期格式:Y-m-d H:i
 * 
 * @type {String}
 */
whjn.constant.dataFormateYMDHI = "Y-m-d H:i";
/**
 * 日期格式:Y-m-d H:i:s
 * 
 * @type {String}
 */
whjn.constant.dataFormateYMDHIS = "Y-m-d H:i:s";

/**  下面是按钮的文本定义区域，变量模式为：***BtnText  ***/
/**
 * 工具栏上按钮CSS风格
 * 
 * @type {String} x-btn-text : 文字按钮
 */
whjn.constant.SC_TXT_BTN = "x-btn-text";
/**
 * 工具栏上按钮CSS风格
 * 
 * @type {String} x-btn-icon : 图标按钮
 */
whjn.constant.SC_ICN_BTN = "x-tbar-raw x-btn-icon";
/**
 * 工具栏上按钮CSS风格
 * 
 * @type {String} x-btn-text-icon : 文字图标的按钮
 */
whjn.constant.SC_TXTICN_BTN = "x-tbar-raw x-btn-text-icon";

/**
 * 新增按钮文本和提示
 * 
 * @type {String}
 */
whjn.constant.addBtnText = "新增";
whjn.constant.addBtnTip = "增加一条新纪录";

/**
 * 下一个按钮文本和提示
 * 
 * @type {String}
 */
whjn.constant.nextBtnText = "下一个";
whjn.constant.nextBtnTip = "跳转至符合此条件的下一个树节点";

/**
 * 上一个按钮文本和提示
 * 
 * @type {String}
 */
whjn.constant.previousBtnText = "上一个";
whjn.constant.previousBtnTip = "跳转至符合此条件的上一个树节点";

/**
 * 编辑按钮文本和提示
 * 
 * @type {String}
 */
whjn.constant.editBtnText = "编辑";
whjn.constant.editBtnTip = "修改选中记录的第一条";
/**
 * 删除按钮文本和提示
 * 
 * @type {String}
 */
whjn.constant.delBtnText = "删除";
whjn.constant.delBtnTip = "删除选中记录";
/**
 * 查看按钮文本和提示
 * 
 * @type {String}
 */
whjn.constant.viewBtnText = "查看";
whjn.constant.viewBtnTip = "查看选中记录的第一条";

/**
 * 回复按钮文本和提示
 * 
 * @type {String}
 */
whjn.constant.backBtnText = "回复";
whjn.constant.backBtnTip = "回复选中记录";


/**
 * 保存按钮文本和提示
 * 
 * @type {String}
 */
whjn.constant.saveBtnText = "保存";
whjn.constant.saveBtnTip = "保存当前信息";

/**
 * 导出按钮文本和提示
 * 
 * @type {String}
 */
whjn.constant.exportBtnText = "导出";
whjn.constant.exportBtnTip = "导出当前查询结果到MS OFFICE";


/**
 * 保存并继续按钮文本和提示
 * 
 * @type {String}
 */
whjn.constant.saveGoOnBtnText = "保存并继续";
whjn.constant.saveGoOnBtnTip = "保存当前信息，并继续操作";
/**
 * 选择并继续按钮文本和提示
 */
whjn.constant.getGoOnBtnText="选择并继续";
whjn.constant.getGoOnBtnTip="保存选中的当前信息，并继续操作";
/**
 * 确定按钮文本和提示
 * 
 * @type {String}
 */
whjn.constant.yesBtnText = "确定";
whjn.constant.yesBtnTip = "确定当前操作";
/**
 * 关闭按钮文本和提示(主页面不要有此按钮，弹出框才有可能需要)
 * 
 * @type {String}
 */
whjn.constant.closeBtnText = "关闭";
whjn.constant.closeBtnTip = "关闭当前功能";

/**
 * 置顶文本和提示
 * 
 * @type {String}
 */
whjn.constant.topBtnText = "置顶";
whjn.constant.topBtnTip = "置顶所选行";
/**
 * 上移文本和提示
 * 
 * @type {String}
 */
whjn.constant.upBtnText = "上移";
whjn.constant.upBtnTip = "上移所选行";
/**
 * 下移文本和提示
 * 
 * @type {String}
 */
whjn.constant.downBtnText = "下移";
whjn.constant.downBtnTip = "下移所选行";
/**
 * 沉底文本和提示
 * 
 * @type {String}
 */
whjn.constant.bottomBtnText = "沉底";
whjn.constant.bottomBtnTip = "沉底所选行";
/**
 * 重新排序后保存文本和提示
 * 
 * @type {String}
 */
whjn.constant.orderRefreshBtnText = "刷新";
whjn.constant.orderRefreshBtnTip = "保存更改";


/**
 * 查看并反馈
 */
whjn.constant.feedBackBtnText = "处理";
whjn.constant.feedBackBtnTip = '处理一条记录';

/**
 * 版本发布
 */
whjn.constant.releaseBtnText = "发布版本";
whjn.constant.releaseBtnTip = '发布一条版本信息';

/**
 * 附件按钮文本
 * 
 * @type {String}
 */
whjn.constant.accBtnText = "附件";
/**
 * 刷新按钮文本
 * 
 * @type {String}
 */
whjn.constant.refreshBtnText = "刷新";
/**
 * 查询按钮文本
 * 
 * @type {String}
 */
whjn.constant.queryBtnText = "查询";


/**
 * 保存并提交按钮文本
 * 
 * @type {String}
 */
whjn.constant.saveAndSubmitBtnText = "保存并提交";

/**
 * 拷贝按钮文本
 * 
 * @type {String}
 */
whjn.constant.copyBtnText = "拷贝";
/**
 * 剪切按钮文本
 * 
 * @type {String}
 */
whjn.constant.cutBtnText = "剪切";
/**
 * 粘贴按钮文本
 * 
 * @type {String}
 */
whjn.constant.pasteBtnText = "粘贴";
/**
 * 打印按钮文本
 * 
 * @type {String}
 */
whjn.constant.printBtnText = "打印";

/**
 * 导入按钮文本
 * 
 * @type {String}
 */
whjn.constant.importBtnText = "导入";
/**
 * 排序按钮文本
 * 
 * @type {String}
 */
whjn.constant.sortBtnText = "排序";
/**
 * 清空按钮文本
 * 
 * @type {String}
 */
whjn.constant.clearTextBtnText = "清空";
/**
 * 作废按钮文本
 * 
 * @type {String}
 */
whjn.constant.cancelBtnText = "作废";
/**
 * 处理按钮文本
 * 
 * @type {String}
 */
whjn.constant.handleBtnText = "处理";
/**
 * 接收按钮文本
 * 
 * @type {String}
 */
whjn.constant.receiveBtnText = "接收";
/**
 * 提交按钮文本
 * 
 * @type {String}
 */
whjn.constant.submitBtnText = "提交";
/**
 * 审批按钮文本
 * 
 * @type {String}
 */
whjn.constant.checkBtnText = "审批";
/**
 * 通过按钮文本
 * 
 * @type {String}
 */
whjn.constant.passBtnText = "通过";
/**
 * 驳回按钮文本
 * 
 * @type {String}
 */
whjn.constant.rejectBtnText = "驳回";
/**
 * 统计按钮文本
 * 
 * @type {String}
 */
whjn.constant.statBtnText = "统计";
/**
 * 统计分析按钮文本
 * 
 * @type {String}
 */
whjn.constant.statAnalyseBtnText = "统计分析";
/**
 * 综合查询
 * 
 * @type String
 */
whjn.constant.colligateQueryBtnText = "综合查询";
/**
 * 汇总按钮文本
 * 
 * @type {String}
 */
whjn.constant.gatherBtnText = "汇总";
/**
 * 合并按钮文本
 * 
 * @type {String}
 */
whjn.constant.joinBtnText = "合并";
/**
 * 拆分按钮文本
 * 
 * @type {String}
 */
whjn.constant.divideBtnText = "拆分";
/**
 * 交换按钮文本
 * 
 * @type {String}
 */
whjn.constant.switchBtnText = "交换";
/**
 * 清空
 * 
 * @type{string}
 */
whjn.constant.clearBtnText = "清空";

/**
 * 重置文本
 * 
 * @type {String}
 */
whjn.constant.resetBtnText = "重置";
/**
 * 高级查询
 * @type {String}
 */
whjn.constant.advanceQryBtnText = "高级查询";


/**
 * 对应Ext.data.JsonReader中的totalProperty
 */
whjn.constant.PC = "totalCount";
/**
 *  对应Ext.data.JsonReader中的root
 * @type String
 */
whjn.constant.PR = "records";




/** 下面是正则表达式定义区域，变量模式为：regex***，和regexDesp *****/
/**
 * 不允许出现特殊字符Reg,一般用于名称 $结束符 +一次或多次 ^开始符 ()定义匹配模式 \w包括下划线的任何单词字符 |或者 [^]负值字符范围。
 * 
 * @type {RegExp}
 */
whjn.constant.regexNoSpecChar = /^(\w|[^ -~])+$/;
whjn.constant.regexDespNoSpecChar = '不允许出现特殊字符';
/**
 * 只能由字母数字及下划线组成Reg,一般用于编码
 * 
 * @type {RegExp}
 */
whjn.constant.regexCode = /^\w+$/;
whjn.constant.regexDespCode = '只能由字母数字及下划线组成';
/**
 * 只能由字母数字组成Reg
 * 
 * @type {RegExp}
 */
whjn.constant.regexLetterNum = /^[A-Za-z0-9]+$/;
whjn.constant.regexDespLetterNum = "只能由字母数字组成";
/**
 * 整数的Reg
 * 
 * @type {RegExp}
 */
whjn.constant.regexInt = /^[-\+]?\d+$/;
whjn.constant.regexDespInt = "只能是整数";
/**
 * 正整数的Reg
 * 
 * @type {RegExp}
 */
whjn.constant.regexPositiveInt = /^([1-9]\d*)$/;
whjn.constant.regexDespPositiveInt = "只能是正整数";
/**
 * 非负整数的Reg
 * 
 * @type {RegExp}
 */
whjn.constant.regexPositiveIntAndZero = /^([0-9]\d*)$/;
whjn.constant.regexDespPositiveIntAndZero = "只能是非负整数";
/**
 * 非负整数的Reg
 * 
 * @type {RegExp}
 */
whjn.constant.regexNotMinusInt = /^\d+$/;
whjn.constant.regexDespNotMinusInt = "只能是正整数或零";
/**
 * 非负数的Reg
 * 
 * @type RegExp
 */
whjn.constant.regexNotMinusFloat = /^\d+(\.\d+)?$/;
whjn.constant.regexDespNotMinusFloat = "只能是非负数";
/**
 * 正数的Reg
 * 
 * @type RegExp
 */
whjn.constant.regexPositiveFloat = /^[1-9]\d*(\.\d+)?$|^0\.\d*[1-9]\d*$/;
whjn.constant.regexDespPositiveFloat = "只能是正数";
/**
 * 浮点数的Reg
 * 
 * @type {RegExp}
 */
whjn.constant.regexFloat = /^[-\+]?\d+(\.\d+)?$/;
whjn.constant.regexDespFloat = "只能是浮点数";
/**
 * 1位小数浮点数的Reg
 * 
 * @type {RegExp}
 */
whjn.constant.regexFloatOne = /^[-\+]?\d+(\.\d)?$/;
whjn.constant.regexDespFloatOne = "只能是1位小数浮点数";
/**
 * 2位小数浮点数的Reg
 * 
 * @type {RegExp}
 */
whjn.constant.regexFloatTwo = /^[-\+]?\d+(\.\d(\d)?)?$/;
whjn.constant.regexDespFloatTwo = "只能是2位小数浮点数";
/**
 * 3位小数浮点数的Reg
 * 
 * @type {RegExp}
 */
whjn.constant.regexFloatThree = /^[-\+]?\d+(\.\d(\d)?(\d)?)?$/;
whjn.constant.regexDespFloatThree = "只能是3位小数浮点数";
/**
 * 正3位小数浮点数的Reg
 * 
 * @type {RegExp}
 */
whjn.constant.regexPlusFloatThree = /^\d+(\.\d(\d)?(\d)?)?$/;
whjn.constant.regexDespPlusFloatThree = "只能是正3位小数浮点数";
/**
 * 日期的Reg，格式：YYYY-MM-DD
 * 
 * @type {RegExp}
 */
whjn.constant.regexDate = /^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29))$/;
whjn.constant.regexDespDate = "日期格式不合法，格式：YYYY-MM-DD";
/**
 * 时间的Reg，格式：HH:MM:SS
 * 
 * @type {RegExp}
 */
whjn.constant.regexTime = /^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$/;
whjn.constant.regexDespTime = "时间格式不合法，格式：HH:MM:SS";
/**
 * 日期时间的Reg，格式：YYYY-MM-DD HH:MM:SS
 * 
 * @type {RegExp}
 */
whjn.constant.regexDateTime = /^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29)) ([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$/;
whjn.constant.regexDespDateTime = "日期时间格式不合法，格式：YYYY-MM-DD HH:MM:SS";
/**
 * 日期时间的Reg，格式：YYYY-MM-DD HH:MM
 * 
 * @type {RegExp}
 */
whjn.constant.regexDateTimeWithoutSec = /^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29)) ([0-1][0-9]|2[0-3]):([0-5][0-9])$/;
whjn.constant.regexDespDateTimeWithoutSec = "日期时间格式不合法，格式：YYYY-MM-DD HH:MM";
/**
 * E-mail的Reg
 * 
 * @type {RegExp}
 */
whjn.constant.regexEmail = /^(.+)@(.+)$/;
whjn.constant.regexDespEmail = "E-mail格式不合法";
/**
 * 手机号码Reg
 * 
 * @type {RegExp}
 */
whjn.constant.regexMobile = /^\d{11}$/;
whjn.constant.regexDespMobile = "手机号码格式不合法";
/**
 * 六位以内浮点数Reg
 * 
 * @type {RegExp}
 */
whjn.constant.regexFloatSix = /^\d*(\.\d{1,6})?$/;
whjn.constant.regexDespFloatSix = "小数点位数不能超过六位";


/**
 * 颜色值
 * @type {Number}
 */
whjn.constant.Color_gwl = -2492185;

/**
 * 获得上级节点
 */
whjn.constant.getSuperNodeBtnText = "获得上级节点";
whjn.constant.getSuperNodeBtnTip = "根据当前节点获得上级节点的信息";
/**
 * 获得下级节点
 */
whjn.constant.getLowNodeBtnText = "获得下级节点数组";
whjn.constant.getLowNodeBtnTip = "根据当前节点获得下级节点的数组信息";
/**
 * 节点数
 */
whjn.constant.getNodeNumBtnText = "节点数";
whjn.constant.getNodeNumBtnTip = "获得选中的节点总数量";
/**
 * 获得父节点
 */
whjn.constant.getParentNodeBtnText = "根据属性获得父节点";
whjn.constant.getParentNodeBtnTip = "根据属性获得当前节点的父节点的信息";

/**
 * 输出查询结果提示
 */
whjn.constant.exportClew = "请执行查询操作之后再执行输出查询结果操作！";

/**
 * 返回至登录页面
 */
whjn.constant.backToLoginBtn = "返回登录";
whjn.constant.backToLoginBtnTip = "返回至登录页面";