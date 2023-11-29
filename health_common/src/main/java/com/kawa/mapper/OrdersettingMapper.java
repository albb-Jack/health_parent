package com.kawa.mapper;

import com.kawa.entity.Ordersetting;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author kuli
* @description 针对表【t_ordersetting】的数据库操作Mapper
* @createDate 2023-11-27 15:24:27
* @Entity com.kawa.entity.Ordersetting
*/
public interface OrdersettingMapper extends BaseMapper<Ordersetting> {

    //根据月份查询预约设置列表信息
    List<Ordersetting> getOrderSettingByMonth(@Param("startTime") String startTime, @Param("endTime") String endTime);

    //根据时间单个设置 可预约的数量
    void insertNumberByDate(Ordersetting ordersetting);

    //查询当天是否存在预约数据
    List<Ordersetting> getNumberByDate(Ordersetting ordersetting);

    //更新当天可预约的数量
    void updateNumberByDate(Ordersetting ordersetting);
}




