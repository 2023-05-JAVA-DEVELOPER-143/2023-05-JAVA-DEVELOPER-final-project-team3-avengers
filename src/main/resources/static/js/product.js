import {common_header_user_cart} from './main.js';
import { product_list_grid_view } from './view/view-product-list-grid.js';
import { product_list_list_view } from './view/view-product-list-list.js';
import {ajaxRequest} from './request.js';
const PRODUCT_GRID_LIST = { method: 'GET', url: 'api/product-grid-ls' };
const PRODUCT_LIST_LIST = { method: 'GET', url: 'api/product-list-ls' };


let hash = window.location.hash
let path = hash.substring(1);
let html = '';
/*
초기실행메쏘드
*/
function init() {
	registEvent();
	navigate();
	//common_header_user_cart();
}
/*
이벤트등록
*/
function registEvent() {
	
	$(window).on('hashchange', function(e) {
		alert('hashchange event:' + e);
		hash = window.location.hash
		path = hash.substring(1);
		navigate();
	});
	$(document).on('click', function(e) {
		if ($(e.target).attr('data-navigate')) {
			alert('click event --> hash변경:' + $(e.target).attr('data-navigate'));
		}
	});
 	
}

/*
	이벤트발생시 처리메쏘드
*/
function navigate() {
	if (path == '/api/shop-list-ns') {
		/**************** /shop-list-ns******************/
		//let resultJsonObject=ajaxRequest("GET","");
		html = product_list_list_view();
		$('#page_list_content').html(html);
	} else if (path == '/api/shop-grid-ns') {
		/**************** /shop-grid-ns******************/
		html = product_list_grid_view();
		$('#page_list_content').html(html);
	}
}


init();
