package com.tencent.hr;

/*
 * @Author: demussong
 * @Description:
 * @Date: 2022/8/4 16:52
 */

import com.tencent.hr.tests.MongoTemplateHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class MongoApplication {

    private static final String COLLECTION_NAME = "personal_info";

    public static void main(String[] args) {
        SpringApplication.run(MongoApplication.class, args);
    }

    public MongoApplication(MongoTemplateHelper mongoTemplateHelper) {
    //    insert(mongoTemplateHelper);

    //    query(mongoTemplateHelper);
//        test(mongoTemplateHelper);
        testRegx(mongoTemplateHelper);
    }

    public void insert(MongoTemplateHelper mongoTemplateHelper) {
        Map<String, Object> records = new HashMap<>(4);
        records.put("name", "demussong");
        records.put("github", "https://github.com/demussong");
        records.put("time", LocalDateTime.now());

        mongoTemplateHelper.saveRecord(records, COLLECTION_NAME);
    }

    public void query(MongoTemplateHelper mongoTemplateHelper) {
        Map<String, Object> query = new HashMap<>(4);
        query.put("name", "demussong");

        mongoTemplateHelper.queryRecord(query, COLLECTION_NAME);
    }

    public void test(MongoTemplateHelper mongoTemplateHelper) {
        mongoTemplateHelper.queryById("name", "demussong", COLLECTION_NAME, "github","https://github.com/demussong");
    }

    public void testRegx(MongoTemplateHelper mongoTemplateHelper){
        mongoTemplateHelper.queryWithRegx("0002");
    }
}

