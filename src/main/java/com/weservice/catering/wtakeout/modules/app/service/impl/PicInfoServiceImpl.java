package com.weservice.catering.wtakeout.modules.app.service.impl;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.weservice.catering.wtakeout.modules.app.dao.PicInfoDao;
import com.weservice.catering.wtakeout.modules.app.entity.PicInfoEntity;
import com.weservice.catering.wtakeout.modules.app.entity.res.COSPicUrlRes;
import com.weservice.catering.wtakeout.modules.app.service.PicInfoService;
import com.weservice.catering.wtakeout.modules.app.utils.QueryEntityUtil;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.common.utils.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;


@Service("picInfoService")
@Slf4j
public class PicInfoServiceImpl extends ServiceImpl<PicInfoDao, PicInfoEntity> implements PicInfoService {
    @Value("${cloud.cos.bucketName}")
    private String bucketName;
    @Value("${cloud.cos.bucketUrl}")
    private String cosUrl;
    @Autowired
    private COSClient cosClient;
    @Autowired
    private PicInfoDao picInfoDao;

    @Override
    public PageUtils<PicInfoEntity> queryPage(Map<String, Object> params) {
        if (params.get("bizId") != null && params.get("bizId") instanceof Integer) {
            params.put("bizId", String.valueOf(params.get("bizId")));
        }
        IPage<PicInfoEntity> page = this.page(
                new Query<PicInfoEntity>().getPage(params),
                new QueryWrapper(params)
        );

        return new PageUtils<>(page);
    }

    @Override
    public Map<String, List<PicInfoEntity>> queryMapByBizId(@NotNull String bizId) {
        PicInfoEntity picInfoEntityReq = new PicInfoEntity();
        picInfoEntityReq.setBizId(bizId);
        QueryWrapper<PicInfoEntity> queryWrapper = new QueryWrapper<>(picInfoEntityReq);
        return picInfoDao.selectList(queryWrapper).stream().collect(Collectors.groupingBy(PicInfoEntity::getType));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void uploadFile(MultipartFile file, String bizId, String type) {
        String fileName = file.getOriginalFilename();
//        File dest = new File(filePath + fileName);
        try {
            PicInfoEntity picInfoEntity = new PicInfoEntity();
            picInfoEntity.setBizId(bizId);
            picInfoEntity.setPicUrl(cosUrl + bizId + fileName);
            picInfoEntity.setType(type);
            picInfoDao.insert(picInfoEntity);
            InputStream inputStream = file.getInputStream();
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, bizId + fileName, inputStream, new ObjectMetadata());
            cosClient.putObject(putObjectRequest);
        } catch (IOException e) {
            log.error("上传图片：", e);
//            cosClient.deleteObject(bucketName,bizId+fileName);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteFile(String fullPath) {
        try {
//            todo 文件名称处理
//            fullPath.
            cosClient.deleteObject(bucketName, fullPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public COSPicUrlRes uploadFile2COs(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        InputStream inputStream = null;
        long l = System.currentTimeMillis();
        String key = String.valueOf(l) + fileName;
        try {
            inputStream = file.getInputStream();
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, new ObjectMetadata());
            cosClient.putObject(putObjectRequest);
            return new COSPicUrlRes(key);
        } catch (Exception e) {
            log.error("上传图片失败", e);
            throw new RuntimeException("");
        }
    }
}