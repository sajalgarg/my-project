package com.futor.analytics.util;

public enum StudentReportEnum {
WEEK,MONTH,OVERALL;
@Override
public String toString() {
  switch(this) {
    case WEEK: return "WEEK";
    case MONTH: return "MONTH";
    case OVERALL: return "OVERALL";
    default: throw new IllegalArgumentException();
  }
}
}
