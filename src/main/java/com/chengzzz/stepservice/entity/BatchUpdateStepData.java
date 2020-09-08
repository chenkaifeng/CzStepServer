package com.chengzzz.stepservice.entity;

import java.util.List;

/**
 * 批量修改步数实体
 *
 * @author: Chenkf
 * @create: 2020/09/08
 **/
public class BatchUpdateStepData {

    private String phone;

    private String password;

    private List<UpdateStepData> list;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UpdateStepData> getList() {
        return list;
    }

    public void setList(List<UpdateStepData> list) {
        this.list = list;
    }
}
