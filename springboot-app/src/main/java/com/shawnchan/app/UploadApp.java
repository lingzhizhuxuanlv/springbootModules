package com.shawnchan.app;

import com.shawnchan.core.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "/app")
public class UploadApp {

    @Autowired
    private Environment env;

    /**
     * 文件上传控制器
     */
    @PostMapping(value = "/upload")
    public Object upload(HttpServletRequest request) {
        try {
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
            Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
            List<String> resultUrls = new ArrayList<>();
            //迭代参数名
            while (iterator.hasNext()) {
                List<MultipartFile> files = multipartHttpServletRequest.getFiles(iterator.next());
                //遍历保存该参数的文件
                for(MultipartFile file:files){
                    String fileName = file.getOriginalFilename();
                    String newFileName = reFileName(fileName);
                    String path = saveFiles(file, newFileName);
                    resultUrls.add(getBaseUrl(request) + path);
                }
            }
            return Result.buildOK("保存成功",resultUrls);
        } catch (Exception e) {
            return Result.buildERROR("保存失败");
        }
    }

    /**
     * 保存文件
     */
    public String saveFiles(MultipartFile file, String newFilename) throws Exception {
        //本地路径
        String basePath = "";
        //日期文件夹
        String path = getUploadPath();
        //判断当前操作系统
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            basePath = env.getProperty("upload.w-upload");
        } else {
            basePath = env.getProperty("upload.l-upload");
        }
        File targetFile = new File(basePath + "/" + path + "/" + newFilename);
        //判断父目录是否存在
        if(!targetFile.getParentFile().exists()){
            targetFile.getParentFile().mkdirs();
        }
        //保存
        file.transferTo(targetFile);
        //返回请求路径
        String download = env.getProperty("download");
        return download + "/" + path + "/" + newFilename;
    }

    /**
     * 获取基本路径
     * http://localhost:8080/demo
     */
    public String getBaseUrl(HttpServletRequest request) {
        StringBuilder basePath = new StringBuilder();
        basePath.append(request.getScheme()).append("://");
        basePath.append(request.getServerName());
        //过滤默认端口号
        if (request.getServerPort() != 80 && request.getServerPort() != 443) {
            basePath.append(":").append(request.getServerPort());
        }
        basePath.append(request.getContextPath());
        return basePath.toString();
    }

    /**
     * 重命名文件避免重复
     * picture.png >>> 201808101020081199.png
     */
    public String reFileName(String fileName) {
        //当前时间
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = dateFormat.format(new Date());
        //4位随机数
        int random = new Random().nextInt(10000);
        //源文件后缀名
        int position = fileName.lastIndexOf(".");
        String extension = fileName.substring(position);
        //返回拼接后的新名字
        return dateString + random + extension;
    }

    /**
     * 根据日期创建文件保存路径
     * 2018-08-10
     */
    public String getUploadPath() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = dateFormat.format(new Date());
        String year = dateString.substring(0,4);
        String month = dateString.substring(4,6);
        String day = dateString.substring(6,8);
        String path = year + "-" + month + "-" + day;
        return path;
    }

}
