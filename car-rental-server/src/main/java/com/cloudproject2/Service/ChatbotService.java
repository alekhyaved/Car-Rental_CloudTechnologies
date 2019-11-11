package com.cloudproject2.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lexruntime.AmazonLexRuntime;
import com.amazonaws.services.lexruntime.AmazonLexRuntimeClientBuilder;
import com.amazonaws.services.lexruntime.model.PostTextRequest;
import com.amazonaws.services.lexruntime.model.PostTextResult;

@Service
public class ChatbotService {
	
	@Value("${chatBotRegion}")
    private String endpointUrl;
    @Value("${chatBotaccessKey}")
    private String accessKey;
    @Value("${chatBotsecretKey}")
    private String secretKey;
    @Value("${botName}")
    private String botName;
    @Value("${botAlias}")
    private String botAlias;
    @Value("${userId}")
    private String userId;  
    
	
    		
	public String Chatbot(String message) {
		

	    AWSCredentials awsCredss = new BasicAWSCredentials(this.accessKey, this.secretKey);
		
		AmazonLexRuntime client = AmazonLexRuntimeClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCredss)).withRegion(Regions.US_EAST_1).build();
		
		PostTextRequest text = new PostTextRequest();
		text.setBotName(this.botName);
		text.setBotAlias(this.botAlias);
		text.setUserId(this.userId);
		text.setInputText(message);
		PostTextResult result= client.postText(text);
		return result.getMessage();
    }

}
