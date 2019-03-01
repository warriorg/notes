package src.main.java.me.warriorg.sbm.xml.department.service.impl;

import com.github.pagehelper.PageHelper;
import me.warriorg.sbm.department.domain.Department;
import me.warriorg.sbm.department.service.DepartmentService;
import me.warriorg.sbm.dto.department.DepartmentParam;

import java.util.List;

/**
 * @author: warriorg
 * @date: 2019-02-11
 */
public class DepartmentServiceImpl implements DepartmentService {

    @Override
    public List<Department> findAll() {
        PageHelper.startPage(1, 20).doSelectPage(() -> return null);

        return null;
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
