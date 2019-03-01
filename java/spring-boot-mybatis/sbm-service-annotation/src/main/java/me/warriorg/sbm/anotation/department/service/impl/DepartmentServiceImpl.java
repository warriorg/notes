package me.warriorg.sbm.anotation.department.service.impl;

import com.github.pagehelper.PageHelper;
import me.warriorg.sbm.anotation.department.dao.DepartmentMapper;
import me.warriorg.sbm.anotation.department.domain.Department;
import me.warriorg.sbm.anotation.department.service.DepartmentService;
import me.warriorg.sbm.dto.department.DepartmentParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: warriorg
 * @date: 2019-02-11
 */
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    DepartmentMapper departmentMapper;

    @Override
    public List<Department> findAll() {
        return departmentMapper.find();
    }

    @Override
    public Department findById(String deptNo) {
        return null;
    }

    @Override
    public Department insert(DepartmentParam param) {
        return null;
    }

    @Override
    public Department update(String deptNo, DepartmentParam param) {
        return null;
    }

    @Override
    public void removeById(String deptNo) {

    }
}
