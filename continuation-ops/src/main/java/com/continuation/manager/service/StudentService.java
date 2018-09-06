package com.continuation.manager.service;

import com.continuation.manager.domain.po.mysql.StudentPO;
import com.continuation.manager.domain.po.mysql.StudentRepository;
import com.continuation.manager.exception.UserNotFoundException;
import com.continuation.manager.utils.ContinuationUtil;
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
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author tangxu
 * @Title: ${file_name}
 * @date 2018/8/2117:57
 */
@Slf4j
@Service
@AllArgsConstructor
public class StudentService {

    private static final String ASC = "asc";
    private static final String DESC = "desc";
    private final StudentRepository studentRepository;
    private final ObjectMapper objectMapper;


    /**
     * 更新/添加学员信息
     */
    public void updateStudent(StudentPO studentPO) throws Exception {
        studentPO.setSex(IdCardUtil.getSexByIDCard(studentPO.getIdentityNumber()));
        studentPO.setUpdatedDate(new Date());
        if (null == studentPO.getId()) {
            studentPO.setStudentNumber(initStudentNumber(studentPO.getClassId()));
            studentPO.setCreatedDate(studentPO.getUpdatedDate());
        } else {
            StudentPO po = studentRepository.findById(studentPO.getId()).orElseThrow(() ->
                    new UserNotFoundException(String.format("未找到对应用户信息:%s", studentPO.getId())));
            studentPO.setCreatedDate(po.getCreatedDate());
        }
        studentRepository.save(studentPO);
    }

    private String initStudentNumber(String classId) {
        Long count = studentRepository.count((Specification<StudentPO>) (root, query, criteriaBuilder) -> criteriaBuilder.and(criteriaBuilder.equal(root.get("classId").as(String.class), classId)));
        int year = Calendar.getInstance().get(Calendar.YEAR);
        return String.format("%s%s%s", year, ContinuationUtil.autoGenericCode(classId, 3), ContinuationUtil.autoGenericCode(String.valueOf(count.intValue() + 1), 3));
    }


    /**
     * 条件分页查询学员信息
     */
    public Page<StudentPO> search(String queryString, int page, int size, String sortBy, String order) throws IOException {
        StudentPO studentPO = objectMapper.readValue(queryString, StudentPO.class);

        Sort sort = Sort.unsorted();
        if (ASC.equals(order)) {
            sort = new Sort(Sort.Direction.ASC, sortBy);
        } else if (DESC.equals(order)) {
            sort = new Sort(Sort.Direction.DESC, sortBy);
        }
        Pageable pageable = PageRequest.of(page, size, sort);

        return studentRepository.findAll((Specification<StudentPO>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (StringUtils.isNotBlank(studentPO.getStudentNumber())) {
                list.add(criteriaBuilder.equal(root.get("studentNumber").as(String.class), studentPO.getStudentNumber()));
            }
            if (StringUtils.isNotBlank(studentPO.getClassId())) {
                list.add(criteriaBuilder.equal(root.get("classId").as(String.class), studentPO.getClassId()));
            }
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));

        }, pageable);
    }

    public StudentPO searchById(String id) {
        return studentRepository.findById(Long.valueOf(id)).orElse(null);
    }

}
