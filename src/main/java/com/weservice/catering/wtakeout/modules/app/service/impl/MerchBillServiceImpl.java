package com.weservice.catering.wtakeout.modules.app.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.modules.app.constant.OrderEnum;
import com.weservice.catering.wtakeout.modules.app.dao.ReportDao;
import com.weservice.catering.wtakeout.modules.app.entity.*;
import com.weservice.catering.wtakeout.modules.app.entity.req.MerchBillReq;
import com.weservice.catering.wtakeout.modules.app.service.MerchBillService;
import com.weservice.catering.wtakeout.modules.app.service.OrderInfoService;
import com.weservice.catering.wtakeout.modules.app.utils.ExcelUtils;
import com.weservice.catering.wtakeout.modules.app.utils.QueryEntityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("merchBillService")
@Slf4j
public class MerchBillServiceImpl implements MerchBillService {
    @Autowired
    private ReportDao reportDao;
    @Autowired
    private OrderInfoService orderInfoService;
    static Map<String, String> typeMap = new HashMap<>();

    static {
        typeMap.put("mt", "美团");
        typeMap.put("el", "饿了吗");
    }

    ;

    @Override
    public PageUtils<MerchBillStatisticsEntity> queryPage(Map<String, Object> params) {
        Page<MerchBillStatisticsEntity> page = PageUtils.generatePage(params);
        MerchBillReq merchBillReq = QueryEntityUtil.generateQueryEntity(params, MerchBillReq.class);
        merchBillReq.setStatus(OrderEnum.SUCCESS.getStatus());
        IPage<MerchBillStatisticsEntity> merchBillStatistics = reportDao.getMerchBillStatistics(page, merchBillReq);
        return new PageUtils<>(merchBillStatistics);
    }

    @Override
    public void download(Map<String, Object> params, HttpServletResponse response) {
        try {

            MerchBillReq merchBillReq = QueryEntityUtil.generateQueryEntity(params, MerchBillReq.class);
            merchBillReq.setStatus(OrderEnum.SUCCESS.getStatus());
            List<OrderInfoEntity> orderList = reportDao.getOrderList(merchBillReq);
            List<MerchBillExcelEntity> res = orderList.stream().map(this::transfer2MerchBillWithOrderInfo).collect(Collectors.toList());
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String time = sdf.format(date);
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码
            String excelName = URLEncoder.encode(time, "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + excelName + ExcelTypeEnum.XLSX.getValue());
            EasyExcel.write(response.getOutputStream(), MerchBillExcelEntity.class).sheet("sheetName").doWrite(res);


//            EasyExcel.write(response.getOutputStream(), MerchBillExcelEntity.class).sheet("sheet0")
//                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
//                    .doWrite(res);
//            ExcelUtils.writeExcel(response,res,"test","bao",MerchBillExcelEntity.class);
        } catch (Exception e) {
            log.error("导出文件失败：", e);
        }
    }


    public MerchBillExcelEntity transfer2MerchBillWithOrderInfo(OrderInfoEntity orderInfoEntity) {
        MerchBillExcelEntity merchBillExcelEntity = new MerchBillExcelEntity();
        merchBillExcelEntity.setSourceType(typeMap.get(orderInfoEntity.getProductType()));
        merchBillExcelEntity.setOrderTime(orderInfoEntity.getUpdateTime());
        merchBillExcelEntity.setCost(orderInfoEntity.getCost() == null ? "0" : orderInfoEntity.getCost().toString());
        merchBillExcelEntity.setPraisePic(orderInfoEntity.getPraisePics().stream().map(PicInfoEntity::getPicUrl).reduce((x, y) -> x + ";" + y).orElseGet(()->""));
        merchBillExcelEntity.setTakeoutOrderId(orderInfoEntity.getTakeoutOrder());
        merchBillExcelEntity.setSubsidy(orderInfoEntity.getReturnPoint() == null ? "0" : orderInfoEntity.getReturnPoint().toString());
        merchBillExcelEntity.setProductName(orderInfoEntity.getProductName());
        return merchBillExcelEntity;
    }
}
