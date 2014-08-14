;
define(function(require, exports) {
    var $ = require('jquery');
    var STATIC = require('common/static.js');
    var REQUEST = require('request');
    var SEARCH = require('search');
    var TEMP = require('common/temp.js');
    var $validate = require('validation');
    var $loginForm = $("#loginForm");
    var $subBtn = $("#sub-btn");
    var TIP = require('tip');
    var param = SEARCH.getParam(location.search);
    var userName = param.userName;
    var password = param.password;
    if (userName) {
        $('#userName').val(userName);
    }
    if (password) {
        $('#password').val(password);
    }
    $loginForm.validate({
        ignore: "",
        submitHandler: function(form) {
            $subBtn.addClass('sub-ing').val("登录中...");
            REQUEST.form(form, REQUEST.url('login'), {"encryptType": 2}, function(res) {
                if (res.resultCode == 0) {
                    var data = res.data;
                    TEMP.set(STATIC.WXPT_SESSION_NAME, data.session);
                    TEMP.set(STATIC.WXPT_AES_KEY_NAME, data.key);
                    location.href = "index.html";
                } else if (res.resultCode = 1011) {
                    TIP.show(2, "用户名或者密码错误");
                    $subBtn.removeClass('sub-ing').val("登录");
                } else {
                    TIP.show(2, "操作失败");
                    $subBtn.removeClass('sub-ing').val("登录");
                }
            });
        },
        rules: {
            userName: {required: true},
            password: {required: true}
        },
        messages: {
        }
    });

});