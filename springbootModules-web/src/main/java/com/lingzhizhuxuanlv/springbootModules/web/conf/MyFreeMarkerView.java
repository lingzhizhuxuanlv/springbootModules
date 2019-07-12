package com.lingzhizhuxuanlv.springbootModules.web.conf;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class MyFreeMarkerView extends FreeMarkerView {

    @Override
    protected void exposeHelpers(Map<String, Object> model,HttpServletRequest request) throws Exception {
        StringBuilder basePath = new StringBuilder();
        basePath.append(request.getScheme()).append("://");
        basePath.append(request.getServerName());
        //默认端口过滤端口号
        if (request.getServerPort() != 80 && request.getServerPort() != 443) {
            basePath.append(":").append(request.getServerPort());
        }
        basePath.append(request.getContextPath());
        model.put("base",basePath);
        super.exposeHelpers(model, request);
    }

}
