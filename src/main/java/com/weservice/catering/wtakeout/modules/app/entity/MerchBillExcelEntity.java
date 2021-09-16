package com.weservice.catering.wtakeout.modules.app.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class MerchBillExcelEntity {
    @ExcelProperty("店铺名称")
    private String productName;
    @ExcelProperty("途径")
    private String sourceType;
    @ExcelProperty("类型")
    private String type = "霸王餐";
    @ExcelProperty("外卖订单")
    private String takeoutOrderId;
    @ExcelProperty("实付金额")
    private String cost;
    @ExcelProperty("补贴")
    private String subsidy;
    @ExcelProperty("技术服务费")
    private String serviceCharge = "3.00";
    @ExcelProperty("评论截图")
    private String praisePic;
    @ExcelProperty("抢单时间")
    private Date orderTime;
}
