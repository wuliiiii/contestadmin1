package org.algotithmcontestdatacollect.displaybackend.Controllers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSON;
import org.algotithmcontestdatacollect.displaybackend.Entities.FutureContestEntity;
import org.algotithmcontestdatacollect.displaybackend.Repositories.FutureContestRepository;
import org.algotithmcontestdatacollect.displaybackend.Utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FutureContestController {

    @Autowired
    FutureContestRepository futureContestRepository;
    @GetMapping("/api/futureContest")
    public String getFutureContest() {
        List<FutureContestEntity> futureContestEntities = futureContestRepository.findAll();
        return ResponseUtil.JSONReturn(200,(JSONArray) JSON.toJSON(futureContestEntities));
    }
}
