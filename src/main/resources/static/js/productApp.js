import * as api from "./p_apiService.js"

let hash = window.location.hash
let path = hash.substring(1);
let html = '';
let firstResult=0;
let filterDto = {};
filterDto["orderType"] = "판매순";
filterDto["firstResult"] = firstResult;

/*
초기실행메쏘드
*/
export function init() {
	registEvent();
}

function registEvent() {

	$('#otherOptions option').each(function(){
		let $this = $(this);
		$this.attr('title', $this.data('tooltip'));
	});
	$('#orderTypeSearch').change(function() {
		filterDto["orderType"] = $('#orderTypeSearch option:selected').val();
		api.searchResult(filterDto);
	});
	$('#minPriceSearch').keydown(function() {
		filterDto["minPrice"] = $('#minPridceSearch').val();
	});
	$('#maxPriceSearch').keydown(function() {
		filterDto["maxPrice"] = $('#maxPriceSearch').val();
	});
	$('#nameKeywordSearch').keydown(function() {
		filterDto["nameKeyword"] = $('#nameKeywordSearch').val();
	});


	$(window).on('hashchange', function(e) {
		hash = window.location.hash
		path = hash.substring(1);
		console.log(path);
		navigate();
	});
	$(document).on('click', function(e) {
		console.log(e.target);
		if ($(e.target).attr('categoryId')) {
			let categoryId = $(e.target).attr('categoryId');
			api.subCategory(categoryId);
			filterDto["category"] = {};
			filterDto.optionset = [];

		} else if ($(e.target).attr('sub-categoryId')) {
			let categoryId = $(e.target).attr('sub-categoryId');
			api.showOptions(categoryId);
			let subCategoryName = $(e.target).attr('sub-categoryName');
			filterDto["category"] = {};
			filterDto["category"]["id"] = categoryId;
			filterDto["category"]["name"] = subCategoryName;
			api.searchResult(filterDto);
			firstResult=0;
			filterDto.optionset = [];
			console.log(filterDto);

		} else if ($(e.target).attr('product-heart')) {
			let optionSetId = $(e.target).attr('product-heart');
			api.untapHeart(optionSetId, function callback() {
				let button = $(e.target);
				// product-heart-yet 속성의 값을 product-heart로 옮기기
				button.attr('product-heart-yet', button.attr('product-heart'));
				button.removeAttr('product-heart');
				button.removeClass('active');
			});
		} else if ($(e.target).attr('product-heart-yet')) {
			let optionSetId = $(e.target).attr('product-heart-yet');
			api.tapHeart(optionSetId, function callback() {
				let button = $(e.target);
				// product-heart-yet 속성의 값을 product-heart로 옮기기
				button.attr('product-heart', button.attr('product-heart-yet'));
				button.removeAttr('product-heart-yet');
				button.addClass('active');
			});//비회원 로그인경고처리
		} else if ($(e.target).attr('data-wishlist-remove')) {
			let optionSetId = $(e.target).attr('data-wishlist-remove');
			console.log(optionSetId);
			api.removewish(optionSetId, function callback() {
				$(e.target).closest("tr").remove();
			});
		} else if ($(e.target).attr('data-recentview-remove')) {
			let optionSetId = $(e.target).attr('data-recentview-remove');
			console.log(optionSetId);
			api.removeRecentView(optionSetId, function callback() {
				$(e.target).closest("tr").remove();
			});
		} else if ($(e.target).attr('data-optionValue')) {
			let optionName = $(e.target).closest('tr').find('th').text();
			let optionValue = $(e.target).attr('data-optionValue');
			let checked = $(e.target).is(":checked");
			console.log(optionName,optionValue,checked);
			updateQueryDataDto(optionName, optionValue, checked);
			console.log(filterDto);
			firstResult=0;
			api.searchResult(filterDto);
		} else if ($(e.target).attr('id') == 'main-search-btn') {
			firstResult=0;
			api.searchResult(filterDto);
		} else if ($(e.target).attr('data-toast-message') == "successfuly added to cart!") {
			let optionSetId = $(e.target).attr('data-optionSetId');
			let qty = 1;
			if ($(e.target).attr('data-cart-qty')) {
				qty = $('#qty option:selected').text();
				optionSetId = $('#otherOptions option:selected').val();
			}
			e.stopPropagation();
			api.addToCart(optionSetId, qty);
		} else if ($(e.target).attr('heart')) {
			let parentButton = undefined;
			if ($(e.target).parent().attr('product-heart')) {
				parentButton = $(e.target).parent();
				let optionSetId = parentButton.attr('product-heart');
				api.untapHeart(optionSetId, function callback() {
					// product-heart-yet 속성의 값을 product-heart로 옮기기
					parentButton.attr('product-heart-yet', parentButton.attr('product-heart'));
					parentButton.removeAttr('product-heart');
					parentButton.removeClass('active');
				});
			} else if ($(e.target).parent().attr('product-heart-yet')) {
				parentButton = $(e.target).parent();
				let optionSetId = parentButton.attr('product-heart-yet');
				api.tapHeart(optionSetId, function callback() {
					// product-heart-yet 속성의 값을 product-heart로 옮기기
					parentButton.attr('product-heart', parentButton.attr('product-heart-yet'));
					parentButton.removeAttr('product-heart-yet');
					parentButton.addClass('active');
				});
			}
		} else if ($(e.target).attr('toOrder')) {
			/*let qty = $('#qty option:selected').text();
			let	optionSetId = $('#otherOptions option:selected').val();
			e.stopPropagation();
			api.productOrder(optionSetId, qty);*/
			$('#productOrderForm').submit();
		}
	});


}

function updateQueryDataDto(optionName, optionValue, checked) {
	if (!filterDto.optionset) {
		filterDto.optionset = [];
	}
	let existingOption = filterDto.optionset.find(opt => opt.optionName === optionName);
	if (checked) {
		if (existingOption) {
			if (!Array.isArray(existingOption.optionValue)) {
				existingOption.optionValue = [existingOption.optionValue];
			}
			existingOption.optionValue.push(optionValue);
		} else {
			filterDto.optionset.push({
				"optionName": optionName,
				"optionValue": [optionValue]
			});
		}
	} else if (!checked && existingOption) {
		if (Array.isArray(existingOption.optionValue)) {
			existingOption.optionValue = existingOption.optionValue.filter(value => value !== optionValue);
			if (existingOption.optionValue.length === 0) {
				filterDto.optionset = filterDto.optionset.filter(opt => opt !== existingOption);
			}
		} else if (existingOption.optionValue === optionValue) {
			filterDto.optionset = filterDto.optionset.filter(opt => opt !== existingOption);
		}
	}
}

const $result = $("#toObserve");
const $end = document.createElement("div");
$end.id='product-list-observed';
$result.append($end);

const callback = (entries, observer) => {
  entries.forEach((entry) => {
    if (entry.isIntersecting) {
	  firstResult+=20;
	  filterDto.firstResult=firstResult;
      api.continueSearchResult(filterDto,observer);
    }
  });
}
const options = {
    root: null, // 뷰포트를 기준으로 타켓의 가시성 검사
    rootMargin: '0px 0px 0px 0px', // 확장 또는 축소 X
    threshold: 1 // 타켓의 가시성 0%일 때 옵저버 실행
};
const observer = new IntersectionObserver(callback, options);
observer.observe($end);
//init();
