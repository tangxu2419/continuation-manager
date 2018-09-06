package com.continuation.manager.domain.po.mysql;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tangxu
 * @date 2018/8/148:59
 */
@Repository
public interface ClassRepository extends CrudRepository<ClassPO,Long> {

    /**
     * 根据年份查询班级信息
     * @param year  学年
     * @return  班级集合
     */
    List<ClassPO> findAllByYear(String year);
}
