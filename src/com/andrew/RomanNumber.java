package com.andrew;


public enum RomanNumber {
    I("I", 1), II("II",2), III("II",3),IV("IV",4), V("V",5),
    VI("VI",6), VII("VII",7), VIII("VIII",8), IX("IX",9), X("X",10),
    L("L", 50), C("C", 100), D("D", 500);
    private final String key;
    private final int value;

    RomanNumber(String key, int value) {
        this.key = key;
        this.value = value;
    }

    public static String convertArabic(int value) throws ExpressionException {
        if (value <= RomanNumber.X.value) {
            return RomanNumber.values()[value-1].key;
        } else if (value <= RomanNumber.L.value) {
            if (value == RomanNumber.L.value)
                return RomanNumber.L.key;
            int count = value / RomanNumber.X.value;
            int mod = value % RomanNumber.X.value;
            String preString;
            if (count > 3) {
                preString = RomanNumber.X.key + RomanNumber.L.key;
            } else {
                preString = RomanNumber.X.key.repeat(Math.max(0, count));
            }
            if (mod != 0)
                preString += convertArabic(mod);
            return preString;
        } else if (value <= RomanNumber.C.value) {
            if (value == RomanNumber.C.value)
                return RomanNumber.C.key;
            int count = value - RomanNumber.L.value;
            return RomanNumber.L.key + convertArabic(count);
        } else if (value <= RomanNumber.D.value) {
            if (value == RomanNumber.D.value)
                return RomanNumber.D.key;
            int count = value / RomanNumber.C.value;
            int mod = value % RomanNumber.C.value;
            String preString;
            if (count > 3) {
                preString = RomanNumber.C.key + RomanNumber.D.key;
            } else {
                preString = RomanNumber.C.key.repeat(Math.max(0, count));
            }
            if (mod != 0)
                preString += convertArabic(mod);
            return preString;
        } else {
            throw new ExpressionException("The number is too large.");
        }
    }

    public String getKey() {
        return key;
    }

    public int getValue() {
        return value;
    }
}
