package com.continuation.manager.service;

import com.continuation.manager.domain.po.mysql.TeacherPO;
import com.continuation.manager.domain.po.mysql.TeacherRepository;
import com.continuation.manager.exception.RequestParamException;
import com.continuation.manager.exception.UserNotFoundException;
import com.continuation.manager.utils.IdCardUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author tangxu
 * @Title: ${file_name}
 * @date 2018/8/2011:15
 */
@Slf4j
@AllArgsConstructor
@Service
public class TeacherInfoService {
    private static final String ASC = "asc";
    private static final String DESC = "desc";

    private final TeacherRepository teacherRepository;
    private final ObjectMapper objectMapper;

    public void updateTeacher(TeacherPO teacherPO) throws Exception {
        teacherPO.setSex(IdCardUtil.getSexByIDCard(teacherPO.getIdentityNumber()));
        teacherPO.setUpdatedDate(new Date());
        if (null == teacherPO.getId()) {
            teacherPO.setCreatedDate(teacherPO.getUpdatedDate());
        } else {
            TeacherPO po = teacherRepository.findById(teacherPO.getId()).orElseThrow(() ->
                    new UserNotFoundException(String.format("未找到对应用户信息:%s",teacherPO.getId())));
            teacherPO.setCreatedDate(po.getCreatedDate());
        }
        teacherRepository.save(teacherPO);
    }

    public Page<TeacherPO> search(String queryString, int page, int size, String sortBy, String order) throws IOException {
        TeacherPO teacherPO = objectMapper.readValue(queryString, TeacherPO.class);

        Sort sort = Sort.unsorted();
        if (ASC.equals(order)) {
            sort = new Sort(Sort.Direction.ASC, sortBy);
        } else if (DESC.equals(order)) {
            sort = new Sort(Sort.Direction.DESC, sortBy);
        }
        Pageable pageable = PageRequest.of(page, size, sort);

        return teacherRepository.findAll((Specification<TeacherPO>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (StringUtils.isNotBlank(teacherPO.getWorkNumber())) {
                list.add(criteriaBuilder.equal(root.get("workNumber").as(String.class), teacherPO.getWorkNumber()));
            }
            if (StringUtils.isNotBlank(teacherPO.getLevel())) {
                list.add(criteriaBuilder.equal(root.get("level").as(String.class), teacherPO.getLevel()));
            }
            if (StringUtils.isNotBlank(teacherPO.getSubject())) {
                list.add(criteriaBuilder.equal(root.get("subject").as(String.class), teacherPO.getSubject()));
            }
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));

        }, pageable);
    }

    public TeacherPO searchById(String id) {
        return teacherRepository.findById(Long.valueOf(id)).orElse(null);
    }
}
