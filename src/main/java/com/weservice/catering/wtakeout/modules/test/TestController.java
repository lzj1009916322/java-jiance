package com.weservice.catering.wtakeout.modules.test;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ListObjectsRequest;
import com.qcloud.cos.model.ObjectListing;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.modules.app.dao.OrderInfoDao;
import com.weservice.catering.wtakeout.modules.app.entity.OrderInfoEntity;
import com.weservice.catering.wtakeout.modules.app.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private COSClient client;
    @Autowired
    private OrderInfoDao orderInfoDao;
    @Autowired
    private OrderInfoService orderInfoService;

    @PostMapping("/test")
    public void upload(@RequestBody MultipartFile file) {
        String bucketName = "wservice-1300628366";
        String fileName = file.getOriginalFilename();
//        File dest = new File(filePath + fileName);
        try {
            InputStream inputStream = file.getInputStream();
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream, new ObjectMetadata());
            client.putObject(putObjectRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        client.putObject(bucketName, key, dest);
    }

    @GetMapping("/list")
    public ObjectListing list() {
        String bucketName = "wservice-1300628366";
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
// 设置bucket名称
        listObjectsRequest.setBucketName(bucketName);
// prefix表示列出的object的key以prefix开始
//        listObjectsRequest.setPrefix("images/");
// deliter表示分隔符, 设置为/表示列出当前目录下的object, 设置为空表示列出所有的object
        listObjectsRequest.setDelimiter("/");
// 设置最大遍历出多少个对象, 一次listobject最大支持1000
        listObjectsRequest.setMaxKeys(1000);
        try {
            ObjectListing objectListing = client.listObjects(listObjectsRequest);
            return objectListing;
        } catch (CosServiceException e) {
            e.printStackTrace();
            throw new RuntimeException("list error");
        }
    }

    @GetMapping("/testList")
    public PageUtils test() {
//        IPage<OrderInfoEntity> orderInfoEntityIPage = orderInfoDao.queryByPage(new Page<>(), new HashMap());
//        return new PageUtils(orderInfoEntityIPage);
        return orderInfoService.queryPage(new HashMap<>());
    }
}
