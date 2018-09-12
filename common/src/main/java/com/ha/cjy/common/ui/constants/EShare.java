package com.ha.cjy.common.ui.constants;

/**
 * 自定义的第三方的分享枚举
 */
public enum EShare {
    E_NON(6), E_SINA(0),E_WEIXI(1),E_QQ(4),E_PYQ(2),E_SHOT(5);
    public static EShare mapIntToValue(final int EUsetTypeInt) {
        for (EShare value : EShare.values()) {
            if (EUsetTypeInt == value.getIntValue()) {
                return value;
            }
        }

        // If not, return default
        return E_NON;
    }

    private int mIntValue;

    EShare(int intValue) {
        mIntValue = intValue;
    }

    public int getIntValue() {
        return mIntValue;
    }

    @Override
    public String toString() {
        return String.valueOf(mIntValue);
    }
}
