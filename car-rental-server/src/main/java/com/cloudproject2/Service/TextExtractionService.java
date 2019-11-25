package com.cloudproject2.Service;

import com.cloudproject2.Model.Userlicense;

public interface TextExtractionService {

	Userlicense extractTextFromImage(String photo,boolean isBlacklisted);
	
}



