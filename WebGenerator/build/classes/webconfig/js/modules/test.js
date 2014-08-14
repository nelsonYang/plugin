;
define(function(require, exports) {
    var $ = require('jquery');
    $('body').on('load', function() {
        console.log('load')
    });
    $(document).ready(function() {
        console.log('ready')
    });
    var Template = require('plugins/arttemplate/template.min.js');
    var source =
            '<ul>'
            + '<% for (var i = 0; i < list.length; i ++) { %>'
            + '<li>索引 <%= i + 1 %> ：<%= list[i] %></li>'
            + '<% } %>'
            + '</ul>';

    var data = {
        list: ['文艺', '博客', '摄影', '电影', '民谣', '旅行', '吉他']
    };

    var render = Template.compile(source);
    var html = render(data);

    var a = '<div>xxx<%=5%></div>';
    var b = Template.compile(a)();
    console.log(b);

    console.log(html)
});