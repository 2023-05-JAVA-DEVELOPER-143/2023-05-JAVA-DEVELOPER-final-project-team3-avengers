import {API_BASE_URL,REMOVE_RECENT_VIEW,REMOVE_WISHLIST,
    TAP_HEART,UNTAP_HEART,CHILD_CATEGORY,SHOW_OPTIONS,SEARCH, ADD_TO_CART} from "./api-config.js";
function call(api, method,request){
    let headers = new Headers({
        "Content-Type": "application/json",
    })
    // const accessToken = localStorage.getItem("ACCESS_TOKEN");
    // if( accessToken &&accessToken !==null){
    //     headers.append("Authorization","Bearer " + accessToken);
    // }
    let options = {
        headers: headers,
        url: API_BASE_URL +api,
        method: method, 
    }; 
    if(request){
        options.body = JSON.stringify(request);
    }
    return fetch(options.url, options).then((response) => {
        if(response.status === 200||response.status ===201){
            return response.json();
        }else if(response.status===401){
			alert('로그인이 필요한 서비스입니다.');
            window.location.href="/member/login/form";// redirect
        }else if(response.stataus===404){
			window.location.href="/404.html";			
		}else if(response.msg=='WRONG_PARAMETER'){
			alert('잘못된 요청입니다. 입력값을 확인해주세요.');
		}else{
            Promise.reject(response);
            throw Error(response);
        }
    }).catch((error) => {
        console.log("http error");
        console.log(error);
    });
}

export function tapHeart(optionSetId, callback) {//디테일에서//클릭이벤트핸들러에서 이미지로 어떤 함수 쓸건지 결정
	return call(TAP_HEART.url.replace('@optionSetId', optionSetId), TAP_HEART.method, null)
		.then((response) => {
			console.log('추가성공');
			callback();
		});
}
export function untapHeart(optionSetId, callback) {//디테일에서//그리고 애초에 서버에서 이미지 뿌릴때 좋아요 여부 확인해서 이미지 알맞게 뿌려야함
	return call(UNTAP_HEART.url.replace("@optionSetId", optionSetId), UNTAP_HEART.method, null)
		.then((response) => {
			console.log('제거성공');
			callback();
			//하트 이미지 바꾸는 코드
		});//그리고 하트 누르는 서비스에서 반환값은 없어도 됨 있으려면 차라리 하트이미지 경로를 주던가
}
export function removewish(optionSetId, callback) {//마이페이지에서//그리고 애초에 서버에서 이미지 뿌릴때 좋아요 여부 확인해서 이미지 알맞게 뿌려야함
	return call(REMOVE_WISHLIST.url.replace("@optionSetId", optionSetId), REMOVE_WISHLIST.method, null)
		.then((response) => {
			callback();
			//하트 이미지 바꾸는 코드
		});//그리고 하트 누르는 서비스에서 반환값은 없어도 됨 있으려면 차라리 하트이미지 경로를 주던가
}
export function removeRecentView(optionSetId, callback) {//최근본상품 하나 삭제
	return call(REMOVE_RECENT_VIEW.url.replace("@optionSetId", optionSetId), REMOVE_RECENT_VIEW.method, null)
		.then((response) => {
			callback();
		});
}

export function subCategory(categoryId) {//대분류 선택하면 발생할 api
	return call(CHILD_CATEGORY.url.replace("@categoryId", categoryId), CHILD_CATEGORY.method, null)
		.then((response) => {
			//옆 섹션에 자식카테고리들 뿌리기 (templateId="#guest-main-template",jsonResult={},contentId="#content")
			$('#option-choice-template-position').html('');
			let template = Handlebars.compile($('#subcategory-template').html());
			let mixedTemplate = template(response);
			$('#subcategory-template-position').html(mixedTemplate);
		});
}

export function showOptions(categoryId) {
	return call(SHOW_OPTIONS.url.replace("@categoryId", categoryId), SHOW_OPTIONS.method, null)
		.then((response) => {
			let template = Handlebars.compile($('#option-choice-template-unique').html());
			let mixedTemplate = template(response);
			$('#option-choice-template-position').html(mixedTemplate);
			//옆 섹션에 선택 가능한 옵션들 뿌리기
		});
}
export function searchResult(filterDto) {//검색결과 보여주기
filterDto.firstResult=0;
			console.log(filterDto);
	return call(SEARCH.url, SEARCH.method, filterDto)
		.then((response) => {
			console.log(response);
			let template = Handlebars.compile($('#main-product-item-template').html());
			let mixedTemplate = template(response);
			$('#main-product-item-template-position').html(mixedTemplate);
		});
}
export function continueSearchResult(filterDto,observer) {//검색결과 보여주기
			console.log(filterDto);
	return call(SEARCH.url, SEARCH.method, filterDto)
		.then((response) => {
			console.log(response);
			if(response.error=='end'){
				observer.unobserve($('#product-list-observed'));
			}
			let template = Handlebars.compile($('#main-product-item-template').html());
			let mixedTemplate = template(response);
			$('#product-list-observed').before(mixedTemplate);
		});
}
export function addToCart(optionSetId, qty){
	let cartDto={
		"optionSetId": optionSetId,
		"qty": qty
	}
	return call(ADD_TO_CART.url,ADD_TO_CART.method,cartDto)
	.then((response)=>{
		//location.href="http://localhost:80/cart_list"
		if(response.status==500){
			alert('장바구니에는 5개이상 못담아요');
		}
	}).catch((error)=>{
		
	})
}
