package com.cti.statscsgo.database;


/**
 * Created by anton on 21.08.15.
 */
public class Coder {
    private char[] ch = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j' };

    public Coder() {

    }

    public String toCode(String number) {
        String str = "";
        char c[] = new char[number.length()];
        number.getChars(0, number.length(), c, 0);
        for (int i = 0, n = c.length; i < n; i++) {
            str+=ch[Integer.valueOf(String.valueOf(c[i]))];
        }

        return str;
    }

    public String deCode(String number) {
        String str = "";
        char[] c = new char[number.length()];
        number.getChars(0, number.length(), c, 0);
        for (int i = 0, n = c.length; i < n; i++) {
            for (int j = 0; j < ch.length; j++) {
                if (c[i] == ch[j]) {
                    str += String.valueOf(j);
                    break;
                }
            }
        }

        return str;
    }
}
