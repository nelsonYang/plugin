;
define(function(require, exports) {
    var $ = require('jquery');
    var REQUEST = require('request');
    var $template = require('template');
    var $pager = require('pager');
    var $queryMemberList = $("#queryMemberList");
    var $pageBox = $('#page-box');
    var $subBtn = $("#sub-btn");
    $queryMemberList.on("submit", function() {
        $pageBox.pager({
            form: this,
            url: REQUEST.url("inquirePageMemberList"),
            param: {"data": {"pageNo": 1, "pageCount": 10}, "encryptType": 1},
            callback: function(oData) {
                if (oData.resultCode == 0) {
                    console.log(oData);
                    // console.log(oData);
                } else {
                }
            }
        });
        return false;
    }).trigger('submit');
});