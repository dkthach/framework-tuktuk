package com.mg.studio.tuktuk.util;

public class Number {
    private static final int[] MESS_NUM = new int[] { 7, 5, 0, 4, 1, 6, 2, 3, 9, 8 };
    private static final int[] NUM_INDEX = new int[] { 2, 4, 6, 7, 3, 1, 5, 0, 9, 8 };

    String myNum;

    public Number(int num) {
        myNum = convertNormalNumber(num);
    }

    public void addValue(int value) {
        myNum = convertNormalNumber(convertMyNumber(myNum) + value);
    }
    
    public void setValue(int value)
    {
        myNum = convertNormalNumber(value);
    }
    
    public int getNormalValue()
    {
        return convertMyNumber(myNum);
    }

    /**
     * Chuyển 1 số thông thư�?ng thành dạng đặc biệt
     * 
     * @param num
     * @return
     */
    public static String convertNormalNumber(int num) {
        String sNum = String.valueOf(num);
        String re = "";
        for (int i = 0; i < sNum.length(); i++) {
            re = re + NUM_INDEX[Integer.parseInt(("" + sNum.charAt(i)))];
        }
        return re;
    }

    /**
     * Chuyển 1 số dạng My number sang số thông thư�?ng
     * 
     * @param myNumber
     * @return
     */
    public static int convertMyNumber(String myNumber) {
        String re = "";
        for (int i = 0; i < myNumber.length(); i++) {
            re = re + MESS_NUM[Integer.parseInt(("" + myNumber.charAt(i)))];
        }
        try {
            return Integer.parseInt(re);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
