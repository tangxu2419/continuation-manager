package com.continuation.manager.domain.po.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author tangxu
 * @date 2018/8/149:01
 */
@Repository
public interface TeacherRepository extends JpaRepository<TeacherPO, Long> ,JpaSpecificationExecutor<TeacherPO> {

    /**
     * 根据工号/密码，查询教师信息
     *
     * @param workNumber 工号
     * @param password   密码
     * @return TeacherPO 教师信息
     */
    Optional<TeacherPO> findFirstByWorkNumberAndPasswordAndVoidedFalse(String workNumber, String password);
}
