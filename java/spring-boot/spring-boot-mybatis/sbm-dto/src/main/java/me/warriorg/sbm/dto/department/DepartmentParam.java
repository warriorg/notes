package me.warriorg.sbm.dto.department;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: warriog
 * @date: 2019-02-11
 */
@Data
public class DepartmentParam implements Serializable {
    /***
     * 部门名称
     */
    private String deptName;
}
