package com.tencent.hr.tests;

/*
 * @Author: demussong
 * @Description:
 * @Date: 2022/8/4 16:51
 */

import com.tencent.hr.vo.TestRegx;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class MongoTemplateHelper {

    @Getter
    @Setter
    private MongoTemplate mongoTemplate;

    public MongoTemplateHelper(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 保存记录
     *
     * @param params
     * @param collectionName
     */
    public void saveRecord(Map<String, Object> params, String collectionName) {
        mongoTemplate.save(params, collectionName);
    }

    /**
     * 精确查询方式
     *
     * @param query
     * @param collectionName
     */
    public void queryRecord(Map<String, Object> query, String collectionName) {
        Criteria criteria = null;
        for (Map.Entry<String, Object> entry : query.entrySet()) {
            if (criteria == null) {
                criteria = Criteria.where(entry.getKey()).is(entry.getValue());
            } else {
                criteria.and(entry.getKey()).is(entry.getValue());
            }
        }

        Query q = new Query(criteria);
        Map result = mongoTemplate.findOne(q, Map.class, collectionName);
        log.info("{}", result);
    }

    /**
     *  select * from table where id is #{id}
     */
    public void queryById(String val, Object obj, String collectionName, String val2, Object obj2) {
        Criteria criteria = null;
        criteria = Criteria.where(val).is(obj).andOperator(Criteria.where(val2).is(obj2));
//        criteria.andOperator()
        Query query = new Query(criteria);
        Map one = mongoTemplate.findOne(query, Map.class, collectionName);

        log.info("{}", one);
    }

    /**
     * 解决bug  模糊匹配问题
     */
    public void queryWithRegx(String val){
        Criteria criteria = Criteria.where("code").is(val);
        Query query = new Query(criteria);
        // 查询字段
        ProjectionOperation project = Aggregation.project("id","code","name","age");

        MatchOperation match = Aggregation.match(criteria);
        Aggregation aggregation = Aggregation.newAggregation(project,match);

        AggregationResults<TestRegx> test_regx = mongoTemplate.aggregate(aggregation, "test_regx", TestRegx.class);

        List<TestRegx> mappedResults = test_regx.getMappedResults();

        System.out.println(mappedResults);
    }
}
