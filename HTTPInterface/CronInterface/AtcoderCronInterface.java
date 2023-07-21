package org.algorithmcontestdatacollect.crawlerendpoint2.HTTPInterface.CronInterface;


import org.algorithmcontestdatacollect.crawlerendpoint2.CronService.AtcoderCron;
import org.algorithmcontestdatacollect.crawlerendpoint2.Utils.ResponseUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//AtCoder定时任务，并将执行结果作为JSON格式的响应返回。
@RestController
@RequestMapping("/cron/atcoder")
public class AtcoderCronInterface {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AtcoderCronInterface.class);
    @Autowired
    private AtcoderCron atcoderCron;
    @GetMapping("/getPastList")
    public String getPastList(){
        try{
            atcoderCron.pastContestList();
        }catch (Exception e){
            LOGGER.error(e.toString());
            return ResponseUtil.JSONReturn(404,"error");
        }
        return ResponseUtil.JSONReturn(200,"ok");
    }

    @GetMapping("/getRecentList")
    public String getRecentList(){
        try{
            atcoderCron.preContestList();
        }catch (Exception e){
            LOGGER.error(e.toString());
            return ResponseUtil.JSONReturn(404,"error");
        }
        return ResponseUtil.JSONReturn(200,"ok");
    }

    @GetMapping("/getStuContest")
    public String getStuContest(){
        try{
            atcoderCron.getStuContest();
        }catch (Exception e){
            LOGGER.error(e.toString());
            return ResponseUtil.JSONReturn(404,"error");
        }
        return ResponseUtil.JSONReturn(200,"ok");
    }

    @GetMapping("/getRecent5StuContestSubmit")
    public String getRecent5StuContestSubmit(){
        try{
            atcoderCron.getRecent5StuContestSubmit();
        }catch (Exception e){
            LOGGER.error(e.toString());
            return ResponseUtil.JSONReturn(404,"error");
        }
        return ResponseUtil.JSONReturn(200,"ok");
    }

    @GetMapping("getRandom5ContestSubmit")
    public String getRandom5StuContestSubmit(){
        try{
            atcoderCron.getRandom5ContestSubmit();
        }catch (Exception e){
            LOGGER.error(e.toString());
            return ResponseUtil.JSONReturn(404,"error");
        }
        return ResponseUtil.JSONReturn(200,"ok");
    }

    @GetMapping("get500SubmitCode")
    public String get500SubmitCode(){
        try{
            atcoderCron.getSubmitCode();
        }catch (Exception e){
            LOGGER.error(e.toString());
            return ResponseUtil.JSONReturn(404,"error");
        }
        return ResponseUtil.JSONReturn(200,"ok");
    }
    @GetMapping("/getAllNoExistContestSubmit")
    public String getAllNoExistContestSubmit() {
        try {
            atcoderCron.getAllExistStuContestButNoExistContestSubmit();
        } catch (Exception e) {
            LOGGER.error(e.toString());
            return ResponseUtil.JSONReturn(404, "error");
        }
        return ResponseUtil.JSONReturn(200, "ok");
    }

}
