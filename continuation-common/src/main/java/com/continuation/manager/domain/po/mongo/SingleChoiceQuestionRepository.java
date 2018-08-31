package com.continuation.manager.domain.po.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tangxu
 * @Title: 单项选择试题
 * @date 2018/8/1420:07
 */
@Repository
public interface SingleChoiceQuestionRepository extends MongoRepository<SingleChoiceQuestionPO, String> {

    /**
     * 根据科目/单元，获取所有试题
     *
     * @param subject 科目
     * @param unit    单元
     * @return 试题集合
     */
    List<SingleChoiceQuestionPO> findAllBySubjectAndUnitAndVoidedFalse(String subject, String unit);
}
