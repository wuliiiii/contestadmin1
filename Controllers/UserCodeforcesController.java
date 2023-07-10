package org.algotithmcontestdatacollect.displaybackend.Controllers;

import com.alibaba.fastjson.JSONObject;
import org.algotithmcontestdatacollect.displaybackend.Services.CfAccountService;
import org.algotithmcontestdatacollect.displaybackend.Utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserCodeforcesController {
    @Autowired
    private CfAccountService cfAccountService;

    @PostMapping("/api/user/codeforces/setMain")
    public String setMainCodeforcesAccount(@RequestBody JSONObject body, HttpServletRequest request) {
        Long uid = Long.parseLong((String) request.getAttribute("id"));
        Long id = body.getLong("codeforcesAccountId");
        try{
            if (cfAccountService.existsByUidAndId(id,uid)){
                cfAccountService.setMainAccount(id, uid);
            }else{
                ResponseUtil.JSONReturn(401,"账户不属于该用户");
            }

        }catch (Exception e) {
            ResponseUtil.JSONReturn(404,"数据库错误");
        }
        return ResponseUtil.JSONReturn(200, "修改成功");

    }
}
