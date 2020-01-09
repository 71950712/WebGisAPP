package com.community.bean;

import java.util.ArrayList;
import java.util.List;
/*
    将分页查询从数据库返回的总条数和每一条数据放入到pagehelper类中，rows和total为bootstrap-table规定的变量名，否则无法识别
 */
public class PageHelper<T> {

    //实体类集合
    private List<T> rows = new ArrayList<T>();
    //数据总条数
    private int total;

    public PageHelper() {
        super();
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }


}
