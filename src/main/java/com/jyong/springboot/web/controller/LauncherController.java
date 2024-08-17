package com.jyong.springboot.web.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import com.jyong.springboot.Util.LogUtil;
import com.jyong.springboot.config.CommonPropertiesConfig;
import com.jyong.springboot.dao.model.User;
import com.jyong.springboot.entity.StudentModel;
import com.jyong.springboot.service.designpattern.factory.ExecuteProcess;
import com.jyong.springboot.service.designpattern.strategy.ProcessMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping("/launcher")
public class LauncherController {


    @Autowired
    private ProcessMain processMain;


    @Autowired
    private ExecuteProcess executeProcess;




    @GetMapping("/app.run")
    public ResponseEntity<String> app() {
        return ResponseEntity.ok("<h1> app is running ...... </h1>");
    }

    @PostMapping("/test.json")
    @ResponseBody
    public ResponseEntity<String> test(@RequestBody List<User> users) {
        for (User user : users) {
            System.out.println(JSONUtil.toJsonStr(user));
        }
        return ResponseEntity.ok(JSON.toJSONString(users));
    }

    @GetMapping("/executeProcess.run")
    public void processMain(@RequestParam String type) {
        executeProcess.process(type);
    }

    @GetMapping("/processMain.run")
    public void processFactory(@RequestParam String type) {
        processMain.process(type);
    }


    @PostMapping("/test/model")
    public ResponseEntity app(@RequestBody StudentModel studentModel) {

        System.out.println("---参数校验----");
        String s = studentModel.toString();
        System.out.println(s);

        return ResponseEntity.ok(s);

    }

    @GetMapping("/app/processMainDo")
    public void processMainDo(String type) {
        processMain.process(type);
    }

    @PostMapping("/upload.json")
    public ResponseEntity upload(@RequestParam MultipartFile file) {
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        String name = file.getOriginalFilename();
        long size = file.getSize();

        LogUtil.info(this.getClass(), "开始上传文件！ 文件名：" + name+" ,size="+size);
        String newFileName = System.currentTimeMillis() + "-" + name;
        String type = name.split("\\.")[1];
        try {
            Assert.isTrue(StrUtil.equalsAny(type,"docx","doc","excel","png"),"文件类型不支持！！！");
            inputStream = file.getInputStream();

            //写入到本地文件
            fileOutputStream = new FileOutputStream("/Users/jyong/Desktop/jyong/workplace/test/" + newFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, length);
            }
            fileOutputStream.flush();
            LogUtil.info(this.getClass(), "文件上传成功！ 文件名：" + newFileName);
        } catch (Exception ex) {
            LogUtil.error(this.getClass(), ex, "文件上传失败！ 文件名：" + newFileName);
        } finally {
            // 关闭资源，这里使用try-with-resources语句自动管理资源
            try {
                if (inputStream != null) inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (fileOutputStream != null) fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ResponseEntity.ok("文件上传成功, 文件名：" + newFileName);
    }

    @GetMapping("/nacos")
    public ResponseEntity<String> nacos() {
        return ResponseEntity.ok("");
    }


}
