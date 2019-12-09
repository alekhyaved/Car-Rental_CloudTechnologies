package com.cloudproject2.Service;

import com.cloudproject2.Model.Userlicense;

/** @author anvithak on 11/11/19 */

public interface TextExtractionService {

	Userlicense extractTextFromImage(String photo,boolean isBlacklisted);
	
}



