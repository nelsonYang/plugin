;
define(function(require, exports) {
    var $ = require('jquery');
    var REQUEST = require('request');
    var $validate = require('validation');
    var TIP = require('tip');
    var $editPasswordForm = $("#editPasswordForm");
    var $subBtn = $("#sub-btn");
    $editPasswordForm.validate({
        ignore: "",
        submitHandler: function(form) {
            $subBtn.addClass('sub-ing').val("提交中...");
            REQUEST.form(form, REQUEST.url('updatePassword'), {}, function(res) {
                if (res.resultCode == 0) {
                    TIP.show(1, "密码修改成功");
                    setTimeout((function() {
                        location.href = "login.html"
                    }), 500);
                } else {
                    TIP.show(2, "操作失败");
                    $subBtn.removeClass('sub-ing').val("确认提交");
                }
            });
        },
        rules: {
            oldPassword: {required: true},
            password: {required: true},
            comfirmPassword: {required: true, equalTo: "#password"}
        },
        messages: {
            oldPassword: {required: "请输入您当前的登录密码"},
            password: {required: "请输入新的登录密码"},
            comfirmPassword: {required: "请重复输入新的登录密码", equalTo: "两次输入的密码不一致"}
        }
    });
});