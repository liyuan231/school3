//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.school.dto;

public class SimplePage<T> {
    private Integer size;
    private T data;
    private Integer total_sign;

    public SimplePage(Integer size, T data,Integer total_sign) {
    	this.setTotal_size(total_sign);
        this.size = size;
        this.data = data;
    }
    
    public SimplePage(Integer size, T data) {
        this.size = size;
        this.data = data;
    }

    public Integer getSize() {
        return this.size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

	public Integer getTotal_sign() {
		return total_sign;
	}

	public void setTotal_size(Integer total_sign) {
		this.total_sign = total_sign;
	}
}
