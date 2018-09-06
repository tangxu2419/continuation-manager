Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
};

function changeStampDateFormat(cellval) {
    var t = new Date(cellval);
    return t.format("yyyy-MM-dd hh:mm:ss");
}

$.fn.initInputGroup = function (options) {
    //1.Settings 初始化设置
    var c = $.extend({
        'widget': 'input',
        'add': "<span class=\"glyphicon glyphicon-plus\"></span>",
        'del': "<span class=\"glyphicon glyphicon-minus\"></span>"
    }, options);

    var _this = $(this);

    //添加序号为1的输入框组
    addInputGroup(_this, c, String.fromCharCode(65), '');


};

/**
 * 添加序号为order的输入框组
 * @param element 目标元素
 * @param c 输入框组的序号
 * @param order 输入框组的序号
 * @param inputValue 输入框组的值
 */
function addInputGroup(element, c, order, inputValue) {
    //1.创建输入框组
    var inputGroup = $("<div class='input-group' style='margin: 10px 0'></div>");
    //2.输入框组的序号
    var inputGroupAddon1 = $("<span class='input-group-addon'></span>");
    //3.设置输入框组的序号
    inputGroupAddon1.html(" " + order + " ");

    //4.创建输入框组中的输入控件（input）
    var widget = $("<input class='form-control' type='text' data-parsley-required='true' value='" + inputValue + "'/>");
    var inputGroupAddon2 = $("<span class='input-group-btn'></span>");

    //5.创建输入框组中最后面的操作按钮
    if (element.children('.input-group').length > 0) {
        element.children('.input-group').find('button').html(c.del);
    }
    var addBtn = $("<button class='btn btn-default' type='button'>" + c.add + "</button>");
    addBtn.appendTo(inputGroupAddon2).on('click', function () {
        //6.响应删除和添加操作按钮事件
        if ($(this).html() === c.del) {
            $(this).parents('.input-group').remove();
        } else if ($(this).html() === c.add) {
            $(this).html(c.del);
            addInputGroup(element, c, order + 1, '');
        }
        //7.重新排序输入框组的序号
        resort(element);
    });
    inputGroup.append(inputGroupAddon1).append(widget).append(inputGroupAddon2);
    element.append(inputGroup);
}

function resort(element) {
    var child = element.children();
    $.each(child, function (i) {
        $(this).find(".input-group-addon").eq(0).html(' ' + String.fromCharCode(i + 65) + ' ');
    });
}


function initSelect(element){
    element.empty();
    element.append($("<option>", {value: ""}).text("-"));
}

var _LEVEL_MAP = {
    "1": "小学一年级",
    "2": "小学二年级",
    "3": "小学三年级",
    "4": "小学四年级",
    "5": "小学五年级",
    "6": "小学六年级",
    "11": "初中一年级",
    "12": "初中二年级",
    "13": "初中三年级",
    "21": "高中一年级",
    "22": "高中二年级",
    "23": "高中三年级"
};
var _SUBJECT_MAP = {
    "MATH": "数学",
    "CHINESE": "语文",
    "ENGLISH": "英语"
};
var _UNIT_MAP = {
    "ARITHMETIC": "算术",
    "EQUATION": "方程式",
    "POETRY": "诗词",
    "SPELL": "拼音",
    "IDIOM": "成语",
    "TRANSLATE": "翻译",
    "FILL_IN_BLANKS": "填空"
};
