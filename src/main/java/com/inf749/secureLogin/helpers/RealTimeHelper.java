package com.inf749.secureLogin.helpers;

import com.inf749.secureLogin.enums.TREnums;

public class RealTimeHelper {
	
	public static long timePassed(long initialTime, long finalTime){
		
		if(initialTime == 0 || finalTime == 0){
			return 0;
		}
		
		return ((finalTime - initialTime) / TREnums.MILLISECONDS.getValue());
	}

	public static Boolean isOneDayBlocked(Integer oneDayBlockByUser) {
		
		if(oneDayBlockByUser != null && oneDayBlockByUser > 0){
			return true;
		}
		return false;		
	}

	public static Boolean isOneHourBlocked(Integer oneHourBlockByUser) {
		
		if(oneHourBlockByUser != null && oneHourBlockByUser > 0){
			return true;
		}
		return false;
		
	} 
	
}
