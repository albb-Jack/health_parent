package com.kawa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName t_ordersetting
 */
@TableName(value ="t_ordersetting")
@Data
public class Ordersetting implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 约预日期
     */
    @TableField(value = "orderDate")
    private Date orderDate;

    /**
     * 可预约人数
     */
    @TableField(value = "number")
    private Integer number;

    /**
     * 月当中几号
     */
    @TableField(value = "date")
    private Integer date;

    /**
     * 已预约人数
     */
    @TableField(value = "reservations")
    private Integer reservations;

}