package com.continuation.manager.domain.po.mysql;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author tangxu
 * @date 2018/8/149:00
 */
@Repository
public interface StudentRepository extends CrudRepository<StudentPO, Long>, JpaSpecificationExecutor<StudentPO> {

    /**
     * 根据学号/密码，查询学生信息
     *
     * @param studentNumber 学号
     * @param password      密码
     * @return StudentPO 学生信息
     */
    Optional<StudentPO> findFirstByStudentNumberAndPasswordAndVoidedFalse(String studentNumber, String password);

}
