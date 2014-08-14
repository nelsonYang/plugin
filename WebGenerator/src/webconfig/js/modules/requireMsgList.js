;
define(function(require, exports) {
    var $ = require('jquery');
    var REQUEST = require('request');
    var $template = require('template');
    var $pager = require('pager');
    var $pageBox = $('#page-box');
    var $subBtn = $("#sub-btn");
    $pageBox.pager({
        form: '',
        url: REQUEST.url("inquirePageResourcesList"),
        param: {"data": {"pageNo": 1, "pageCount": 10}, "encryptType": 1},
        callback: function(oData) {
            if (oData.resultCode == 0) {
                console.log(oData);
            } else {

            }
        }
    });
});