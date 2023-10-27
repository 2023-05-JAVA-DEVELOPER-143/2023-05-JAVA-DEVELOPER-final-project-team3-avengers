import * as main from './main.js';
import * as product from './product.js';

$(window).on('load', function(e) {
		alert('load  event:' + e);
});
main.init();
product.init();
