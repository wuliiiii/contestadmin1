package org.algotithmcontestdatacollect.displaybackend.Controllers;


import com.alibaba.fastjson.JSONArray;
import org.algotithmcontestdatacollect.displaybackend.Entities.ApplicationWithUserinfoEntity;
import org.algotithmcontestdatacollect.displaybackend.Repositories.ApplicationWithUserinfoRepository;
import org.algotithmcontestdatacollect.displaybackend.Utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class ApplicationController {
    @Autowired
    private ApplicationWithUserinfoRepository applicationWithUserinfoRepository;

    @GetMapping("/api/user/application")
    public String getApplication(HttpServletRequest request){
        Long uid = Long.parseLong((String) request.getAttribute("id"));
        List<ApplicationWithUserinfoEntity> datas = applicationWithUserinfoRepository.getApplicationWithUserinfoEntitiesByUid(uid);
        return ResponseUtil.JSONReturn(200, (JSONArray) JSONArray.toJSON(datas));
    }
}
