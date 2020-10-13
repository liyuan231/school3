//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.utils;

public enum RoleEnum {
    ADMINISTRATOR(1),
    USER(2);

    private int val;

    private RoleEnum(int val) {
        this.val = val;
    }

    public int value() {
        return this.val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
