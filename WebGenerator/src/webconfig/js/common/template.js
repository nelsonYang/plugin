//
var deleteAct = function(action, primaryKey,$tableList,$, REQUEST,TIP) {
    var deleteUrl = REQUEST.url(action);
    $tableList.on("click", ".opt", function() {
        var data = {};
        var $this = $(this);
        var id=  $this.attr("data-id");
        data[primaryKey] = id;
        REQUEST.post(deleteUrl, {"encryptType": 1,"data": data}, function(res) {
            if (res.resultCode == 0) {
                TIP.show(1, "操作成功");
                $this.parents("tr").remove();
            } else {
                TIP.show(2, "操作失败");
            }
        });
    });

};
//分页查询列表
var inquirePage = function(action, $tableList,$pageBox, REQUEST,template, TIP) {
    var listUrl = REQUEST.url(action);
    $pageBox.pager({
        form: "",
        url: listUrl,
        param: {"data": {"pageNo": 1, "pageCount": 10}, "encryptType": 1},
        callback: function(res) {
            if (res.resultCode == 0) {
                // console.log(res);
                res = res.data;
                if (res.dataList) {
                    var html = template.render('listTemplate', res);
                    $tableList.html(html);
                }
            } else {
                TIP.show(2, "操作失败");
            }
        }
    });
};
//查询列表
var inquireList = function(action, $tableList, REQUEST, template,  TIP) {
    var listUrl = REQUEST.url(action);
    REQUEST.post();
    $pageBox.pager({
        form: "",
        url: listUrl,
        param: {"data": {"pageNo": 1, "pageCount": 10}, "encryptType": 1},
        callback: function(res) {
            if (res.resultCode == 0) {
                // console.log(res);
                res = res.data;
                if (res.dataList) {
                    var html = template.render('listTemplate', res);
                    $tableList.html(html);
                }
            } else {
                TIP.show(2, "操作失败");
            }
        }
    });
};
//新增
var insertAct = function(insertAction,$entityForm,$subBtn,toUrl,rules,messages,REQUEST,TIP,JUMP) {
    $entityForm.validate({
        ignore: "",
        submitHandler: function(form) {
            $subBtn.addClass('sub-ing').val("提交中...");
            var param = {"encryptType": 1};
            REQUEST.form(form,  REQUEST.url(insertAction), param, function(res) {
                if (res.resultCode == 0) {
                    TIP.show(1, "操作成功");
                    setTimeout((function() {
                        JUMP.to(toUrl);
                    }), 1000);
                } else {
                    TIP.show(2, "操作失败");
                    $subBtn.removeClass('sub-ing').val("确认提交");
                }
            });
        },
        rules:rules,
        messages: messages
    });
};
//更新
var updateAct = function(updateAction,queryAction,primaryKey,id,$entityForm,$subBtn,toUrl,rules,messages,REQUEST,FILL,TIP,JUMP) {
  
     var data = {};
     data[primaryKey] = id;
     var updateUrl = REQUEST.url(updateAction);
        REQUEST.post(REQUEST.url(queryAction), {"encryptType": 1, "data": data}, function(res) {
            if (res.resultCode == 0) {
                var res = res.data;
                FILL.fill($entityForm, res);
            } else {
                TIP.show(2, "查询信息失败");
            }
        });
    $entityForm.validate({
        ignore: "",
        submitHandler: function(form) {
            $subBtn.addClass('sub-ing').val("提交中...");
            var param = {"encryptType": 1, "data": {"matchType": 1}};
            id && (param.data.primaryKey = id);
            REQUEST.form(form, updateUrl, param, function(res) {
                if (res.resultCode == 0) {
                    TIP.show(1, "操作成功");
                    setTimeout((function() {
                        JUMP.to(toUrl);
                    }), 1000);
                } else {
                    TIP.show(2, "操作失败");
                    $subBtn.removeClass('sub-ing').val("确认提交");
                }
            });
        },
        rules: rules,
        messages: messages
    });
};
//查询
var inquireByIdAct = function(action, $entityForm, primaryKey,id, REQUEST, FILL, TIP) {
    var data = {};
    data[primaryKey] = id;
    REQUEST.post(REQUEST.url(action), {"encryptType": 1, "data":data}, function(res) {
        if (res.resultCode == 0) {
            var res = res.data;
            FILL.fill($entityForm, res);
        } else {
            TIP.show(2, "查询信息失败");
        }
    });
};
