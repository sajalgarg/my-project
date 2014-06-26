package com.futor.analytics.domain;

public enum StudentTransactionTypeEnum {
	STUDY,PRACTICE,REVISION;
	@Override
	public String toString() {
	  switch(this) {
	    case STUDY: return "STUDY";
	    case PRACTICE: return "PRACTICE";
	    case REVISION: return "REVISION";
	    default: throw new IllegalArgumentException();
	  }
	}
}