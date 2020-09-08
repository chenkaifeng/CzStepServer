package com.chengzzz.stepservice.entity;

/**
 * 修改步数的实体
 *
 * @author: Chenkf
 * @create: 2020/09/08
 **/
public class UpdateStepData {

    /**
     * 变更步数的日期
     */
    private String date;

    /**
     * 变更后的步数
     */
    private String steps;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }
}
