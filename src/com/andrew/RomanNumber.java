package com.andrew;

public enum RomanNumber {
    I("I", 1), II("II",2), III("II",3),IV("IV",4), V("V",5),
    VI("VI",6), VII("VII",7), VIII("VIII",8), IX("IX",9), X("X",10);
    private final String key;
    private final int value;
    RomanNumber(String key, int value) {
        this.key = key;
        this.value = value;
    }
    public String getKey() {
        return key;
    }
    public int getValue() {
        return value;
    }
}
