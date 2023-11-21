package com.kawa.utils;


import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 七牛云工具类
 */
@Component
public class QiniuUtils {

    @Value("${qiniu.accessKey}")
    public String accessKey;


    @Value("${qiniu.secretKey}")
    public String secretKey;

    @Value("${qiniu.bucket}")
    public String bucket;

    //外链域名
    @Value("${qiniu.domainName}")
    public String domainName;



    /**
     * 将本地文件上传七牛云
     *
     * @param filePath --
     * @param fileName --
     */
    public void upload2Qiniu(String filePath, String fileName) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(filePath, fileName, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet =
                    new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        } catch (QiniuException ex) {
            Response r = ex.response;
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

    /**
     * 字节数组方式上传七牛云
     */
    public void upload2Qiniu(byte[] bytes, String fileName) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = fileName;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(bytes, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet =
                    new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

    /**
     * 删除文件
     */
    public void deleteFileFromQiniu(String fileName) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        String key = fileName;
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }

    /**
     * 以base64上传文件
     *
     * @param saveFolder
     * @param fileName
     * @param base64ImgContent
     * @return
     */
    public String uploadImgByBase64(String saveFolder, String fileName, String base64ImgContent) {
        if (StringUtils.isEmpty(fileName) || StringUtils.isEmpty(base64ImgContent)) {
            return null;
        }
        try {
            Matcher matcher = Pattern.compile("^data.(.*?);base64,").matcher(base64ImgContent);
            if (matcher.find()) {
                base64ImgContent = base64ImgContent.replace(matcher.group(), "");
            }

            byte[] bytes = Base64Utils.decodeFromString(base64ImgContent);

            this.upload2Qiniu(bytes, fileName);

            return saveFolder;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String md5Save2Qiniu(String coverUrl) {
        if (!coverUrl.startsWith("http")) {
            MultipartFile multipartFile = Base64DecodeMultipartFile.base64Convert(coverUrl);

            //文件上传到七牛云
            //2 组装上传图片的名称
            String filename = multipartFile.getOriginalFilename();

            //得到文件的后缀名
            String extensionName = org.springframework.util.StringUtils.getFilenameExtension(filename);
            //前缀名称
            String preName = UUID.randomUUID().toString().replace("-", "") + ".";
            //完整名称
            String fullName = preName + extensionName;

            //3 调用工具类进行文件上传
            try {
                this.upload2Qiniu(multipartFile.getBytes(), fullName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return fullName;
        } else {
            return coverUrl.replace(domainName, "");
        }
    }

}