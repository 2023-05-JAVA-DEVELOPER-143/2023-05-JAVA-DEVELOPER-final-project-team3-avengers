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
    public String findPassMailSend(String mail){
    	
    	String randomPass = mailService.findPassSendMail(mail);
    	
    	return randomPass;
    }

}