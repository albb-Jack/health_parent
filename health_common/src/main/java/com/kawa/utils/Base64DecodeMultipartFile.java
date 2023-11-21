package com.kawa.utils;

import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.util.UUID;

/**
 * base64转为multipartFile工具类
 * base64Convert
 * https://www.cnblogs.com/antao/p/13777997.html
 */

public class Base64DecodeMultipartFile implements MultipartFile {
    private final byte[] imgContent;
    private final String header;

    public Base64DecodeMultipartFile(byte[] imgContent, String header) {
        this.imgContent = imgContent;
        this.header = header.split(";")[0];
    }

    /**
     * base64转multipartFile
     *
     * @param base64
     * @return
     */
    public static MultipartFile base64Convert(String base64) {
        String[] baseStrs = base64.split(",");

        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = new byte[0];
        try {
            b = decoder.decodeBuffer(baseStrs[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {
                b[i] += 256;
            }
        }
        return new Base64DecodeMultipartFile(b, baseStrs[0]);
    }

    public static void main(String[] args) throws IOException {
        /**
         * base64转为multipartFile
         */
        MultipartFile file = base64Convert("data:video/mp4;base64,AAAAAAAA....==");//很长

        if (file.isEmpty()) {
            System.out.println("没有上传文件");
        }

        /**
         * 获取文件后缀
         */
        String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1)
                .toLowerCase();

        /**
         * 重构文件名称
         */
        String pikId = UUID.randomUUID().toString().replaceAll("-", "");
        String fileName = pikId + "." + fileExt;

        /**
         * 存储路径可在配置文件中指定
         */
        File filePath = new File("D:/temp/test/");
        if (!filePath.exists()) {
            filePath.mkdirs();
        }

        File fileLast = new File(filePath, fileName);

        /**
         * 指定好存储路径
         * File file = new File(fileName);
         */
        try {
            /**
             * 保存文件
             * 使用此方法保存必须要绝对路径且文件夹必须已存在,否则报错
             */
            file.transferTo(fileLast);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return System.currentTimeMillis() + Math.random() + "." + header.split("/")[1];
    }

    @Override
    public String getOriginalFilename() {
        return System.currentTimeMillis() + (int) Math.random() * 10000 + "." + header.split("/")[1];
    }

    @Override
    public String getContentType() {
        return header.split(":")[1];
    }

    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize() {
        return imgContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return imgContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        new FileOutputStream(dest).write(imgContent);
    }
}