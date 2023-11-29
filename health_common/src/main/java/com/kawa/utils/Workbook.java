package com.kawa.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 对Excel进行读写操作
 */
public class Workbook {

    /**
     * 写入
     * @param args
     */
    public static void main(String[] args) {

//        read();

        write();
    }


    public static void write(){
        //1创建工作簿对象
        XSSFWorkbook workbook = new XSSFWorkbook();
        //2创建工作表对象
        XSSFSheet xssfSheet = workbook.createSheet("学生名单");
        //3创建行
        XSSFRow row1 = xssfSheet.createRow(0);
        //4创建列,设置内容
        row1.createCell(0).setCellValue("姓名");
        row1.createCell(1).setCellValue("性别");
        row1.createCell(2).setCellValue("地址");

        XSSFRow row2 = xssfSheet.createRow(1);
        row2.createCell(0).setCellValue("张三");
        row2.createCell(1).setCellValue("男");
        row2.createCell(2).setCellValue("深圳");

        XSSFRow row3 = xssfSheet.createRow(2);
        row3.createCell(0).setCellValue("李四");
        row3.createCell(1).setCellValue("男");
        row3.createCell(2).setCellValue("广州");

        //5通过输出流对象写到磁盘
        try {
            FileOutputStream os = new FileOutputStream("/Users/kuli/Test测试.xlsx");
            workbook.write(os);
            os.flush();
            os.close();

            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void read(){
        try {
            //1创建工作簿对象
            XSSFWorkbook workbook = new XSSFWorkbook("/Users/kuli/Test测试.xlsx");

            //先拿到 表
            XSSFSheet rows = workbook.getSheetAt(0);

            //遍历行
            for (Row row : rows) {
                //遍历列 (单元格对象)
                for (Cell cell : row) {
                    System.out.println(cell.getStringCellValue()+"\t");
                }
            }
            //关闭
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
