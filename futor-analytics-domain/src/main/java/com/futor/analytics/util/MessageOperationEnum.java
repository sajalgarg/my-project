package com.futor.analytics.util;

public enum MessageOperationEnum {
	CONCEPTTIME;
	@Override
	public String toString() {
	  switch(this) {
	    case CONCEPTTIME: return "CONCEPTTIME";
	    default: throw new IllegalArgumentException();
	  }
	}

}
