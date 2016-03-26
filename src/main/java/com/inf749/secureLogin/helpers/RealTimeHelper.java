package com.inf749.secureLogin.helpers;

public class RealTimeHelper {
	
	public static final long MAX_RESPONSE_TIME = 2;
	public static final long MILLISECONDS = 1000;
	
	public static long timePassed(long initialTime, long finalTime){
		
		if(initialTime == 0 || finalTime == 0){
			return 0;
		}
		
		return ((finalTime - initialTime) / MILLISECONDS);
	} 
	
	public static Runnable t1 = new Runnable() {
        public void run() {
            try{
                Thread.sleep(MAX_RESPONSE_TIME * MILLISECONDS);
            } catch (Exception e){}

        }
    };
}
