package com.jyong.springboot.web.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.jyong.springboot.dao.model.User;
import com.jyong.springboot.service.UserService;
import com.jyong.springboot.web.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Jyong
 * @date 2024/8/11 10:34
 * @description
 */
@Controller
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private UserService userService;

    @GetMapping("/queryByName.json")
    Result<List<User>> queryByName(@RequestParam String name) {
        Result<List<User>> result = new Result<>();
        result.setSuccess(true);

        return result;
    }


    @GetMapping(value = "/exportUser")
    public Result export(@RequestParam String name) {
        List<User> users = userService.selectByName(name);
        exportUserData(name, users);
        return Result.successResult("导入成功");
    }

    @GetMapping(value = "/export-users", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void exportUserData(@RequestParam("name") String name,
                               HttpServletResponse response) throws IOException {
        // 准备数据
        List<User> users = userService.selectByName(name);
        List<List<String>> data = prepareData(users);

        // 创建文件名
        String fileName = createFileName(name);

        try (OutputStream out = response.getOutputStream()) {
            // 设置响应头
            String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString()).replaceAll("\\+", "%20");
            response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFileName);
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());

            // 创建 ExcelWriter
            ExcelWriter excelWriter = EasyExcel.write(out)
                    .build();

            // 创建 WriteSheet
            WriteSheet writeSheet = EasyExcel.writerSheet(name).build();

            // 写入数据
            excelWriter.write(data, writeSheet);

            // 关闭 ExcelWriter
            excelWriter.finish();
        } catch (Exception e) {
            throw new RuntimeException("导出数据失败: " + e.getMessage(), e);
        }
    }
    private static final String DATE_PATTERN = "yyyyMMddHHmmss";

    /**
     * 导出用户数据到 Excel 文件。
     *
     * @param name 用户名，用于生成文件名。
     * @param users 用户列表。
     */
    private void exportUserDataWithTemplate(String name, List<User> users) {
        // 准备数据
        List<List<String>> data = prepareData(users);

        // 创建文件名
        String fileName = createFileName(name);

        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            // 创建 ExcelWriter
            ExcelWriter excelWriter = EasyExcel.write(fileOutputStream)
                    .withTemplate("template.xlsx")  //指定模版文件
                    .build();

            // 创建 WriteSheet
            WriteSheet writeSheet = EasyExcel.writerSheet(name).build();

            // 填充数据
            excelWriter.fill(data, new FillConfig(), writeSheet);

            // 关闭 ExcelWriter
            excelWriter.finish();
        } catch (Exception e) {
            throw new RuntimeException("导出数据失败: " + e.getMessage(), e);
        }
    }

    private void exportUserData(String name, List<User> users) {
        // 准备数据
        List<List<String>> data = prepareData(users);

        // 创建文件名
        String fileName = createFileName(name);

        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
            // 创建 ExcelWriter
            ExcelWriter excelWriter = EasyExcel.write(fileOutputStream)
                    .build();

            // 创建 WriteSheet
            WriteSheet writeSheet = EasyExcel.writerSheet(name).build();

            // 写入数据
            excelWriter.write(data, writeSheet);

            // 关闭 ExcelWriter
            excelWriter.finish();
        } catch (Exception e) {
            throw new RuntimeException("导出数据失败: " + e.getMessage(), e);
        }
    }
    /**
     * 准备数据。
     *
     * @param users 用户列表。
     * @return 数据列表。
     */
    private List<List<String>> prepareData(List<User> users) {
        List<List<String>> data = new ArrayList<>();
        List<String> header = new ArrayList<>();
        header.add("主键");
        header.add("名字");
        header.add("年龄");
        header.add("地址");
        header.add("邮箱");
        header.add("电话");
        data.add(header);
        for (User user : users) {
            List<String> row = new ArrayList<>();
            row.add(user.getId().toString());
            row.add(user.getName());
            row.add(user.getAge().toString());
            row.add(user.getAddress());
            row.add(user.getEmail());
            row.add(user.getPhone());
            data.add(row);
        }
        return data;
    }

    /**
     * 创建文件名。
     *
     * @param name 用户名。
     * @return 文件名。
     */
    private String createFileName(String name) {
        String dt = DateUtil.format(new Date(), DATE_PATTERN);
        return name + "_" + "用户表" + "_" + dt + ".xlsx";
    }
}
