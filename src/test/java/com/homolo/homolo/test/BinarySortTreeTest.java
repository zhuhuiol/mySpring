package com.homolo.homolo.test;

import lombok.Data;

/**
 * 二叉树.
 */
public class BinarySortTreeTest {

	public static void main(String[] args) {
		final int[] values = { 1, 3, 4, 5, 2, 8, 6, 7, 9, 0 };
		//创建二叉树
		Node node = createBinaryTree(values);
		//遍历数
		inOrderTransval(node);
	}

	public static Node createBinaryTree(int[] values) {
		// TODO:
		//取其首个完成构建
		Node node = new Node(values[0]);
		//循环添加节点
		for (int i = 1; i < values.length; i++) {
			Node node1 = new Node(values[i]);
			node.addNode(node1);
		}
		return node;
	}

	//中序遍历方法
	public static void inOrderTransval(Node node) {
		// TODO:
		if (node == null) {
			return;
		}
		//先遍历左边小的
		inOrderTransval(node.getLeft());
		//中间打印自己
		System.out.print(node.getValue() + ",");
		//最后遍历右边大的
		inOrderTransval(node.getRight());
	}
}
@Data
class Node {
	// 节点值
	private int value;
	// 左节点
	private Node left;
	// 右节点
	private Node right;

	public Node() {
	}

	public Node(int value) {
		this.value = value;
	}
	// 方法集成在类里.
	// add Method
	public void addNode(Node node) {
		if (node == null) {
			return;
		}
		// 如果入参小于当前值则放在左边
		if (node.getValue() < this.value) {
			if (this.left == null) {
				this.left = node;
			} else {
				this.left.addNode(node);
			}
		} else {
			// 否则放在右边, 调用增加方法，依次调用addNode方法，找到自己的位置.
			if (this.right == null) {
				this.right = node;
			} else {
				this.right.addNode(node);
			}
		}

	}
	// searchByVal Method
	public Node searchByVal(int value) {
		if (this.value == value) {
			return this;
		}
		// 入参小于当前值从左边查，否则查右边.
		if (this.value < value) {
			if (this.left == null) {
				return null;
			}
			return this.left.searchByVal(value);
		} else {
			if (this.right == null) {
				return null;
			}
			return this.right.searchByVal(value);
		}
	}
}

