package com.cgx.smart4j.modules.Customer.dto;

/**********
 * @program: smartframework
 * @description:
 * @author: cgx
 * @create: 2018-11-30 11:09
 **/
public class Customerdto {
    private  Long id;
    private String account;
    private String name;
    private String birthday;
    private String sex;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
