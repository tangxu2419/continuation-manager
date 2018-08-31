package com.continuation.manager.service;

import com.continuation.manager.domain.po.mysql.TeacherPO;
import com.continuation.manager.domain.po.mysql.TeacherRepository;
import com.continuation.manager.exception.RequestParamException;
import com.continuation.manager.utils.IdCardUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tangxu
 * @Title: ${file_name}
 * @date 2018/8/2011:15
 */
@Slf4j
@AllArgsConstructor
@Service
public class TeacherInfoService {

    private final TeacherRepository teacherRepository;
    private final MongoTemplate mongoTemplate;

    public void updateTeacher(TeacherPO teacherPO) throws RequestParamException {
        teacherPO.setSex(IdCardUtil.getSexByIDCard(teacherPO.getIdentityNumber()));
        teacherRepository.save(teacherPO);
    }

    public List<TeacherPO> search(String workNumber, String teacherLevel, Boolean voided) {
        Query query = new Query();
        if(StringUtils.isNotBlank(workNumber)){
            query.addCriteria(Criteria.where("workNumber").is(workNumber));
        }
        if(StringUtils.isNotBlank(teacherLevel)){
            query.addCriteria(Criteria.where("teacherLevel").is(teacherLevel));
        }
        if(StringUtils.isNotBlank(workNumber)){
            query.addCriteria(Criteria.where("voided").is(voided));
        }
        return mongoTemplate.find(query, TeacherPO.class);
    }




















}
