package com.continuation.manager.domain.po.mysql;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author tangxu
 * @date 2018/8/148:59
 */
@Repository
public interface ClassRepository extends CrudRepository<ClassPO,Long> {
}
