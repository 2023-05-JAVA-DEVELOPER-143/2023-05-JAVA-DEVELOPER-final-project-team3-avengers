 /**************** 회원가입 **********************/
var account_write_action_rest=function(params){
		console.log('33333');
		
		$.ajax({url:'user_write_action_rest',
				method:'POST',
				dataType:'json',
				data:params,
				success:function(resultJson){
					if(resultJson.result==-1){
						$('#userId').css('border','0.5px solid orange');
						$('#userId').tooltip({'trigger':'hover', 'title': resultJson.msg});
			    		$('#userId').tooltip('show');
					}else{
						$('#form-account-regist').tooltip({'trigger':'hover', 'title': resultJson.msg});
			    		$('#form-account-regist').tooltip('show');
			    		$('#form-account-regist input').each(function(i,e){
			    			$(e).fadeOut('slow',function(){
			    				$(e).val("");
			    				$(e).fadeIn('slow',function(){
			    					$('#form-account-regist').tooltip('dispose');
			    					$("#loginUserId").focus();
			    				});
			    			});
			    			
			    		});
		   			}
				}
			});
			
};
 
 /****************회원로그인 **********************/
	var account_login_action_rest=function(memberResponseDto){
			$.ajax({
    				url:'/member/login_rest',
    				data:JSON.stringify(memberResponseDto),
    				method:'POST',
    				dataType: "json",
    				contentType: 'application/json;chrset=UTF-8',
    				success:function(resultJson){
						console.log('post 요청')
    					$('#form-account-login').css('border','');
    					$('#loginUserId').tooltip('dispose');
    					$('#loginPassword').tooltip('dispose');
    					/*
    		    		 * 0 아이디존재안함
    		    		 * 1 패쓰워드불일치
    		    		 * 2 패쓰워드일치(로그인성공)
    		    		 */
    		    		 if(resultJson.result==0){
    		    			 $('#loginUserId').css('border','0.5px solid orange');
    		    			 $('#loginUserId').tooltip({'trigger':'hover', 'title': resultJson.member.userName+' 는 존재하지않는 아이디입니다.'});
    		    			 $('#loginUserId').tooltip('show');
    		    		 }else if(resultJson.result==1){
    		    			 $('#loginPassword').css('border','0.5px solid orange');
    		    			 $('#loginPassword').tooltip({'trigger':'hover', 'title': '패쓰워드가 일치하지않습니다.'});
    		    			 $('#loginPassword').tooltip('show');
    		    		 }else if(resultJson.result==2){
    		    			 $('#form-account-login input').css('border','');
    	    				 $('#loginUserId').tooltip('dispose');
    	    				 $('#loginPassword').tooltip('dispose');
    		    			 location.href='index';
    		    		 }
    				}
    			});
};
 /**************** account 왼쪽메뉴변경 **********************/
 var account_left_sidebar_select=function(select_menu){
	    $(".list-group a[href='"+select_menu+"']").addClass('active');
 }
/***************프로필변경 [/user_profile_modify_action_rest]******* */
 var user_profile_modify_action_rest=function(params){
	
	$.ajax({
		url:'user_profile_modify_action_rest',
		method:'POST',
		dataType:'json',
		data:params,
		success:function(user){
			if(user.userId==null){
				$('#account-pass').css('border','0.5px dotted orange');
				$('#account-confirm-pass').css('border','0.5px dotted orange');
				$('#account-pass').tooltip({'trigger':'hover', 'title':'패쓰워드가 일치하지 않습니다.'});
    			$('#account-pass').tooltip('show');
    			/*
    			$('.btn-account-profile-update').attr('data-toast-type','warning');
    			$('.btn-account-profile-update').attr('data-toast-message','패쓰워드가 일치하지 않습니다.');
    			*/
			}else{
				$('#account-pass').css('border','');
				$('#account-confirm-pass').css('border','');
				$('#account-confirm').tooltip('dispose');
				
				$('.user-info-wrapper .user-info>.user-data h4').html(user.name);
				$('.sub-menu-user .user-info>.user-name').html(user.name);
				common_header_cart_info_change();		
			}
	    	
		}
	});
};

