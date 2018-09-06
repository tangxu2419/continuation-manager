package com.continuation.manager.service;

import com.continuation.manager.domain.po.mysql.ClassPO;
import com.continuation.manager.domain.po.mysql.ClassRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tangxu
 * @Title: 班级管理操作
 * @date 2018/9/417:33
 */
@Slf4j
@Service
@AllArgsConstructor
public class ClassService {


    private final ClassRepository classRepository;

    /**
     *  根据年份查询班级信息
     * @param year  学年
     * @return  班级集合
     */
    public List<ClassPO> findByYear(String year) {
        return classRepository.findAllByYear(year);
    }

    public List<ClassPO> findAll() {
        return (List<ClassPO>) classRepository.findAll();
    }
}
