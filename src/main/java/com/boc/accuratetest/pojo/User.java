package com.boc.accuratetest.pojo;

import java.util.List;

public class User {
    private Integer id;

    private String userName;

    private String passWord;

    private String tel;

    private String createTime;
    
    private Role role; // 一个用户只有一个角色
    
    private List<Permission> ps; // 这个字段冗余了，应该只放到role下
    
    public List<Permission> getPs() {
		return ps;
	}

	public void setPs(List<Permission> ps) {
		this.ps = ps;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord == null ? null : passWord.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }
}