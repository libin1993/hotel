package com.tdr.hotel.bean;

/**
 * Author：Libin on 2019/6/12 09:35
 * Description：
 */
public class RoomListBean {
    private int num;
    private boolean isSelect;

    public int getNum() {
        return num;
    }

    public RoomListBean(int num, boolean isSelect) {
        this.num = num;
        this.isSelect = isSelect;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
