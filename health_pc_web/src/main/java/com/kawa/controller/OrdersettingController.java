package com.kawa.controller;

import com.kawa.constant.MessageConstant;
import com.kawa.entity.Ordersetting;
import com.kawa.pojo.Result;
import com.kawa.service.OrdersettingService;
import com.kawa.utils.POIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/ordersetting")
public class OrdersettingController {

    @Autowired
    private OrdersettingService ordersettingService;

    /**
     * 根据月份查询预约设置列表信息
     */
    @GetMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date){
       List<Ordersetting> ordersettingList = ordersettingService.getOrderSettingByMonth(date);
       return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS,ordersettingList);
    }

    /**
     * 根据时间单个设置 可预约的人数
     */
    @PostMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody Ordersetting ordersetting){
        ordersettingService.editNumberByDate(ordersetting);
        return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
    }

    /**
     * 批量预约设置
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam MultipartFile excelFile){
       return ordersettingService.upload(excelFile);
    }

















}
