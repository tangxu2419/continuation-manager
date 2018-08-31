package com.continuation.manager.service;

import com.continuation.manager.domain.po.mongo.SingleChoiceQuestionPO;
import com.continuation.manager.domain.po.mongo.SingleChoiceQuestionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.continuation.manager.service.RedisService.TEST_PAPER_HEADER;

/**
 * @author tangxu
 * @Title: ${file_name}
 * @date 2018/8/2117:28
 */
@Slf4j
@Service
@AllArgsConstructor
public class QuestionService {

    private static final String ASC = "asc";
    private static final String DESC = "desc";

    private final ObjectMapper objectMapper;
    private final MongoTemplate mongoTemplate;
    private final RedisService redisService;
    private final SingleChoiceQuestionRepository singleChoiceRepository;

    public void updateSingleChoice(SingleChoiceQuestionPO questionPO) {
        if( StringUtils.isBlank(questionPO.getId()) ){
            questionPO.setCreatedDate(new Date());
        }
        redisService.del(TEST_PAPER_HEADER.concat(questionPO.getSubject()).concat(questionPO.getUnit()));
        questionPO.setUpdatedDate(new Date());
        questionPO.setVoided(false);
        singleChoiceRepository.save(questionPO);
    }

    public Page<SingleChoiceQuestionPO> searchSingleChoice(String queryString, int page, int size, String sortBy, String order) throws IOException {
        SingleChoiceQuestionPO questionPO = objectMapper.readValue(queryString, SingleChoiceQuestionPO.class);

        Sort sort = Sort.unsorted();
        if (ASC.equals(order)) {
            sort = new Sort(Sort.Direction.ASC, sortBy);
        } else if (DESC.equals(order)) {
            sort = new Sort(Sort.Direction.DESC, sortBy);
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        List<Criteria> criterion = new ArrayList<>();
        if (questionPO.getLevel() != null) {
            criterion.add(Criteria.where("level").is(questionPO.getLevel()));
        }
        if (StringUtils.isNotBlank(questionPO.getSubject())) {
            criterion.add(Criteria.where("subject").is(questionPO.getSubject()));
        }
        if (StringUtils.isNotBlank(questionPO.getUnit())) {
            criterion.add(Criteria.where("unit").is(questionPO.getUnit()));
        }

        Criteria criteria = new Criteria();
        if (criterion.size() > 0) {
            criteria = criteria.andOperator(criterion.toArray(new Criteria[0]));
        }
        Query query = new Query(criteria).with(pageable);
        List<SingleChoiceQuestionPO> list = mongoTemplate.find(query, SingleChoiceQuestionPO.class);
        return PageableExecutionUtils.getPage(list, pageable, () -> mongoTemplate.count(query, SingleChoiceQuestionPO.class));
    }

    public SingleChoiceQuestionPO searchSingleChoice(String id) {
        return singleChoiceRepository.findById(id).orElse(null);
    }
}
