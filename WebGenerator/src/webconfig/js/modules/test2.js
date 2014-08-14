;
define(function(require, exports, module) {
//    document.write = function (str) {
//        if (/^<script.*<\/script>$/.test(str)) {
//            document.getElementsByName('head')[0].appendChild(str);
//            return;
//        }
////        document.write(str);
//    }
    return function() {
//        document.write('<' + 'script src="js/modules/test.js"><' + '/script>')
        require('request');
        require('http://map.qq.com/api/js?v=2.exp');
        console.log('test2.js');
    }
});
