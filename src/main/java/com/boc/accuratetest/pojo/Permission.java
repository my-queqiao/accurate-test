package com.boc.accuratetest.pojo;

public class Permission {
    private Integer id;

    private String rankName;

    private String rankDesc;
    private Integer checked = 0; // 页面显示被选中
    
    public Integer getChecked() {
		return checked;
	}

	public void setChecked(Integer checked) {
		this.checked = checked;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName == null ? null : rankName.trim();
    }

    public String getRankDesc() {
        return rankDesc;
    }

    public void setRankDesc(String rankDesc) {
        this.rankDesc = rankDesc == null ? null : rankDesc.trim();
    }
}