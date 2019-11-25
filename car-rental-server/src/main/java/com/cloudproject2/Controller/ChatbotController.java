package com.cloudproject2.Controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.cloudproject2.Service.ChatbotService;

@RestController
@CrossOrigin(origins = "*")
public class ChatbotController {
	
	Logger logger = Logger.getLogger("mylog"); 
	
	@Autowired
	private ChatbotService chatbotService;
	
	@RequestMapping(value = "/chatbot/{message}", method = RequestMethod.POST)
	public String Chatbot(@PathVariable String message) {
		try {
			return chatbotService.Chatbot(message);
		}catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, "lex connection failed");
		}
		return "";
	}
			
			

}
