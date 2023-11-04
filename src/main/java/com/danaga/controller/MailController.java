package com.danaga.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.danaga.service.MailService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;
    
//    @GetMapping("/mailpage")
//    public String MailPage(){
//        return "Mail";
//    }
    @ResponseBody
    @PostMapping("/emailAuthentication")
    public String joinMailSend(String mail){

       int number = mailService.joinSendMail(mail);

       String num = "" + number;

       return num;
    }
    @ResponseBody
    @PostMapping("/findEmailAuthentication")
    public String findPassMailSend(String mail) throws Exception{
    	
    	String randomPass = null;
		try {
			randomPass = mailService.findPassSendMail(mail);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println("##################"+randomPass);
    	
    	return randomPass;
    }
    @ResponseBody
    @PostMapping("/findidEmailAuthentication")
    public String findidMailSend(String mail) throws Exception{
    	
    	String id = null;
		id = mailService.findIdSendMail(mail);
    	
    	return id;
    }

}