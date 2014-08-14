;
define(function(require, exports) {
    var $ = require('jquery');
    var REQUEST = require('request');
    exports.showMenu = showMenu;
    function showMenu($menu) {
        REQUEST.post(REQUEST.url('inquireResourcesMenuList'), {"encryptType": 0, "data": {}}, function(res) {
            var selectHtml = [];
            selectHtml.push('<select name="relatedId">');
            selectHtml.push('<option value="-1">--请选择--</option>');
            if (res.resultCode == 0) {
                if (res.data) {
                    selectHtml.push('<optgroup label="---------------系统业务模块---------------">');
                    if (res.data.businessResource) {
                        for (var index in res.data.businessResource) {
                            selectHtml.push('<option value = "' + res.data.businessResource[index].resourcesId + '">' + res.data.businessResource[index].resourcesName + "</option>")
                        }
                    }
                    selectHtml.push('</optgroup>');
                    selectHtml.push('<optgroup label="---------------自定义图文消息---------------">');
                    if (res.data.systemResource) {
                        for (var index in res.data.systemResource) {
                            selectHtml.push('<option value = "' + res.data.systemResource[index].resourcesId + '">' + res.data.systemResource[index].resourcesName + "</option>")
                        }
                    }
                    selectHtml.push('</optgroup>');
                }
            }
            selectHtml.push('</select> ');
            $menu.html(selectHtml.toString());
        });
    }
});
