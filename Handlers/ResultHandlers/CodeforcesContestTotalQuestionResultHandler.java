package org.algorithmcontestdatacollect.crawlerendpoint2.Handlers.ResultHandlers;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.algorithmcontestdatacollect.crawlerendpoint2.Handlers.AbstractHandler;
import org.algorithmcontestdatacollect.crawlerendpoint2.Repositories.CodeforcesProblemRepository;
import org.algorithmcontestdatacollect.crawlerendpoint2.Repositories.CodeforcesProblemsTagMapRepository;
import org.algorithmcontestdatacollect.crawlerendpoint2.Repositories.NoRecommendContestRepository;
import org.algorithmcontestdatacollect.crawlerendpoint2.Repositories.ProblemTagRepository;
import org.algorithmcontestdatacollect.crawlerendpoint2.TableEntity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CodeforcesContestTotalQuestionResultHandler extends AbstractHandler {
    Logger logger = LoggerFactory.getLogger(CodeforcesContestTotalQuestionResultHandler.class);
    @Autowired
    CodeforcesProblemRepository codeforcesProblemRepository;
    @Autowired
    ProblemTagRepository problemTagRepository;

    @Autowired
    CodeforcesProblemsTagMapRepository codeforcesProblemsTagMapRepository;
    @Autowired
    NoRecommendContestRepository noRecommendContestRepository;
    @Override
    public void handle(String result) {
        JSONArray res = (JSONArray) JSONArray.parse(result);
        var problems = codeforcesProblemRepository.findAll();
        var tags = problemTagRepository.findAll();
        Set<Long> records = noRecommendContestRepository.findAll().stream().map(a -> a.getCid()).collect(Collectors.toSet());
        HashSet<Long> fresh = new HashSet<>();
        Set<String> problemSet = new HashSet<>();
        Map<String,Integer> tagMp = new HashMap<>();
        for(var problem : problems) {
            String metaInfo = "" + problem.getCid() + "-" + problem.getQindex() + "-" + problem.getDifficulty();
            problemSet.add(metaInfo);
        }
        for(var tag:tags) {
            tagMp.put(tag.getTagName(),tag.getId());
        }
        for(int i = 0; i < res.size(); i++) {
            JSONObject problem = res.getJSONObject(i);
            if(!problem.getString("type").equals("PROGRAMMING")){
                continue;
            }
            Integer difficulty = problem.getInteger("rating");
            if(difficulty == null) {
                difficulty = 0;
                fresh.add(problem.getLong("contestId"));
            }
            String metaInfo = "" + problem.getLong("contestId") + "-" + problem.getString("index") + "-" + difficulty;
            if(!problemSet.contains(metaInfo)) {
                if(codeforcesProblemRepository.existsByCidAndQindex(problem.getLong("contestId"), problem.getString("index"))) {
                    logger.info("更新CodeforcesProblem{}", metaInfo);
                    var row = codeforcesProblemRepository.getByCidAndQindex(problem.getLong("contestId"), problem.getString("index"));
                    row.setDifficulty(difficulty);
                    codeforcesProblemRepository.save(row);
                } else {
                    logger.info("添加CodeforcesProblem{}", metaInfo);
                    CodeforcesProblemsEntity entity = new CodeforcesProblemsEntity(problem.getLong("contestId"), problem.getString("index"), difficulty, problem.getString("name"));
                    var codeforcesProblem = codeforcesProblemRepository.saveAndFlush(entity);
                    var problem_tags = problem.getJSONArray("tags");
                    for(int j = 0;j<problem_tags.size();j++) {
                        String tag = problem_tags.getString(j);
                        if(!tagMp.containsKey(tag)) {
                            logger.info("添加CodeforcesProblemTag{}", tag);
                            var e = problemTagRepository.saveAndFlush(new ProblemTagEntity(tag));
                            tagMp.put(tag,e.getId());
                        }
                        var newPK = new CodeforcesProblemsTagMapEntity(entity.getId(), tagMp.get(tag));
                        codeforcesProblemsTagMapRepository.saveAndFlush(newPK);
                    }
                }
            }
        }
        fresh.removeAll(records);
        if(fresh.size()>0){
            Set<NoRecommendContest> collect = fresh.stream().map(a -> new NoRecommendContest(a)).collect(Collectors.toSet());
            noRecommendContestRepository.saveAllAndFlush(collect);
        }
        codeforcesProblemRepository.flush();
    }
}
