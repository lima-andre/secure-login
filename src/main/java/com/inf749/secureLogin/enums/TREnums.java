package com.inf749.secureLogin.enums;

public enum TREnums {
	
	ONEDAY(24), ONEHOUR(1), RESPONSETIME(3), MILLISECONDS(1000);
	
	private final int value;

    private TREnums(int value) {
        this.value = value;
    }
    
    public int getValue() {
        return value;
    }
}
