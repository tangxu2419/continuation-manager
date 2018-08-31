package com.continuation.manager.domain.po.mysql;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author tangxu
 * @date 2018/8/149:02
 */
@Repository
public interface TeacherClassRelationshipRepository extends CrudRepository<TeacherClassRelationshipPO,Long> {
}
