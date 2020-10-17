//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.utils;

public enum FileEnum {
    AVATAR_URL(1),
    LOGO(2),
    SIGNATURE(3),
    TEMPLATE(4);

    private int code;

    private FileEnum(int code) {
        this.code = code;
    }

    public int value() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
