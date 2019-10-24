package com.ecpbm.pojo;

import java.util.List;

public class TreeNode {
	private int id;  // 节点id
	private String text;  // 节点名称
	private int fid;   // 父节点id
	private List<TreeNode> children; // 包含的子节点

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

}
