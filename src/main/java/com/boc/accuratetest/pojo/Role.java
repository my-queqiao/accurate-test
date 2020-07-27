package com.boc.accuratetest.pojo;

import java.util.List;

public class Role {
    private Integer id;

    private String roleName;

    private Integer createUserId;

    private String createTime;

    private List<Permission> ps; // 一个角色拥有多个许可证（每一个controller有一个访问许可）
    
    public List<Permission> getPs() {
		return ps;
	}

	public void setPs(List<Permission> ps) {
		this.ps = ps;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }
}