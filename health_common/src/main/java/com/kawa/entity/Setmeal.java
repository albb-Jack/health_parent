package com.kawa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 
 * @TableName t_setmeal
 */
@TableName(value ="t_setmeal")
@Data
public class Setmeal implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField(value = "name")
    private String name;

    /**
     * 
     */
    @TableField(value = "code")
    private String code;

    /**
     *
     */
    @TableField(value = "helpCode")
    private String helpCode;

    /**
     * 
     */
    @TableField(value = "sex")
    private String sex;

    /**
     * 
     */
    @TableField(value = "age")
    private String age;

    /**
     * 
     */
    @TableField(value = "price")
    private Double price;

    /**
     * 
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 
     */
    @TableField(value = "attention")
    private String attention;

    /**
     * 
     */
    @TableField(value = "img")
    private String img;

    /**
     * 一对多 一个套餐 对应 对个检查组
     */
    @TableField(exist = false)
    private List<Checkgroup> checkgroupList;
}