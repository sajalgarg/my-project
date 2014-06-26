package com.futor.analytics.util;

public enum MessageOperationParamEnum {
USERID,COURSENID;
@Override
public String toString() {
  switch(this) {
    case USERID: return "USERID";
    case COURSENID: return "COURSENID";
    default: throw new IllegalArgumentException();
  }
}
}
