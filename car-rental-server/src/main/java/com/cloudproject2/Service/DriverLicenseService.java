package com.cloudproject2.Service;

import com.cloudproject2.Model.Userlicense;

/** @author anvithak on 11/27/19 */

public interface DriverLicenseService {
	
	public boolean isImageDL(String s3Key);
	public Userlicense storeDriverDetails(String firstName, String lastName, String license, long expiry);

}
