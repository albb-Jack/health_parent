package com.kawa.pojo.resp;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 
 * @TableName t_ordersetting
 */
public class OrdersettingResp  {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 约预日期
     */
    private int date;

    /**
     * 可预约人数
     */
    private Integer number;

    /**
     * 已预约人数
     */
    private Integer reservations;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getReservations() {
        return reservations;
    }

    public void setReservations(Integer reservations) {
        this.reservations = reservations;
    }
}