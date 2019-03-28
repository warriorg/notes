package me.warriorg.sbm.department.service;

import me.warriorg.sbm.department.domain.Department;
import me.warriorg.sbm.dto.department.DepartmentParam;

import java.util.List;

/**
 * @author: warriorg
 * @date: 2019-02-11
 */
public interface DepartmentService {

    /***
     *
     * @return
     */
    List<Department> findAll();

    /***
     *
     * @param deptNo
     * @return
     */
    Department findById(String deptNo);

    /***
     *
     * @param param
     * @return
     */
    Department insert(DepartmentParam param);

    /***
     *
     * @param deptNo
     * @param param
     * @return
     */
    Department update(String deptNo, DepartmentParam param);

    /***
     *
     * @param deptNo
     */
    void removeById(String deptNo);
}
