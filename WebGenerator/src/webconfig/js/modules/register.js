;
define(function(require, exports) {
    var $ = require('jquery');
    var STATIC = require('common/static.js');
    var REQUEST = require('request');
    var TEMP = require('common/temp.js');
    var $validate = require('validation');
    var $loginForm = $("#loginForm");
    var $subBtn = $("#sub-btn");
    var TIP = require('tip');
    $loginForm.validate({
        ignore: "",
        submitHandler: function(form) {
            $subBtn.addClass('sub-ing').val("注册中...");
            REQUEST.form(form, REQUEST.url('register'), {"encryptType": 2}, function(res) {
                if (res.resultCode == 0) {
                    var data = res.data;
                    var userName = $('#userName').val();
                    var password = $('#password').val();
                    TIP.show(1, "注册成功 1 秒后跳转到登录界面");
                    location.href = "login.html?userName=" + userName + "&password=" + password;
                } else if (res.resultCode = 1000) {
                    TIP.show(2, "用户已存在");
                    $subBtn.removeClass('sub-ing').val("注册");
                } else {
                    TIP.show(2, "操作失败");
                    $subBtn.removeClass('sub-ing').val("注册");
                }
            });
        },
        rules: {
            companyName: {required: true},
            province: {required: true},
            city: {required: true},
            region: {required: true},
            street: {required: true},
            linkMobile: {required: true},
            email: {required: true},
            password: {required: true}
        },
        messages: {
            companyName: {required: '请输入公司名称'},
            province: {required: '请输入省份'},
            city: {required: '请输入市区'},
            region: {required: '请输入地区'},
            street: {required: '请输入街道'},
            linkMobile: {required: '请输入联系电话'},
            email: {required: '请输入邮箱'},
            password: {required: '请输入密码'}
        }
    });

});