package com.boc.accuratetest.demo;

public class GitInfo {
	private String gitUrl = "https://github.com/my-queqiao/springboot-saolei.git";
	private String gitName = "pengju1234";
	private String gitPass = "131122abcd";
	private String gitBranch;
	private String newVersion;
	public String getGitUrl() {
		return gitUrl;
	}
	public void setGitUrl(String gitUrl) {
		this.gitUrl = gitUrl;
	}
	public String getGitPass() {
		return gitPass;
	}
	public void setGitPass(String gitPass) {
		this.gitPass = gitPass;
	}
	public String getGitName() {
		return gitName;
	}
	public void setGitName(String gitName) {
		this.gitName = gitName;
	}
	public String getGitBranch() {
		return gitBranch;
	}
	public void setGitBranch(String gitBranch) {
		this.gitBranch = gitBranch;
	}
	public String getNewVersion() {
		return newVersion;
	}
	public void setNewVersion(String newVersion) {
		this.newVersion = newVersion;
	}
	
}
