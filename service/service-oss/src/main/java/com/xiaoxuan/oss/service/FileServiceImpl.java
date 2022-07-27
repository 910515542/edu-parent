package com.xiaoxuan.oss.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.xiaoxuan.oss.utils.ConstantProperties;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{
    @Override
    public String upload(MultipartFile file) {
        String endpoint = ConstantProperties.END_POINT;
        String accessKeyId = ConstantProperties.ACCESS_KEY_ID;
        String accessKeySecret = ConstantProperties.ACCESS_KEY_SECRET;
        String bucketName = ConstantProperties.BUCKET_NAME;
        //https://online-edu-0001.oss-cn-beijing.aliyuncs.com/aj.jpg
        String url = "";
        OSS ossClient = null;

        try{
            String fileName = file.getOriginalFilename();
            //通过UUID让文件名唯一
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName = uuid + fileName;
            //以时间对上传的文件进行文件夹分类（oss会自动创建对应文件夹）
            String datePath = new DateTime().toString("yyyy/MM/dd");
            fileName = datePath + "/" + fileName;
            //最后文件名形式：2020/12/12/uuid文件名.jpg

            InputStream inputStream = file.getInputStream();
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 上传文件。
            ossClient.putObject(bucketName, fileName, inputStream);
            url += "http://" + bucketName + "." + endpoint + "/" + fileName;

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(ossClient != null)
            ossClient.shutdown();
        }
        return url;
    }
}
