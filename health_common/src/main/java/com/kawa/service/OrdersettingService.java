package com.kawa.service;

import com.kawa.entity.Ordersetting;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kawa.pojo.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
* @author kuli
* @description 针对表【t_ordersetting】的数据库操作Service
* @createDate 2023-11-27 15:24:27
*/
public interface OrdersettingService extends IService<Ordersetting> {

    //根据月份查询预约设置列表信息
    List<Ordersetting> getOrderSettingByMonth(String date);

    //根据时间单个设置 可预约的人数
    void editNumberByDate(Ordersetting ordersetting);

    //批量预约设置
    Result upload(MultipartFile excelFile);
}
