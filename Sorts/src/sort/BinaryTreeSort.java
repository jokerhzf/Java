package sort;

import static sort.SortUtils.less;
import static sort.SortUtils.print;

/**
 *
 * @author Podshivalov Nikita (https://github.com/nikitap492)
 *
 * @see SortAlgorithm
 *
 */
public class BinaryTreeSort implements SortAlgorithm {

	//定义接口
	interface TreeVisitor<T extends Comparable<T>>  {
		void visit(Node<T> node);
	}

	//实现接口
	private static class SortVisitor<T extends Comparable<T>> implements TreeVisitor<T> {

		private final T[] array;
		private int counter;


		SortVisitor(T[] array) {
			this.array = array;
		}

		@Override
		public void visit(Node<T> node) {
			array[counter++] = node.value;
		}
	}

	//树节点
	private static class Node<T extends Comparable<T>>{
		private T value;
		private Node<T> left;
		private Node<T> right;

		Node(T value) {
			this.value = value;
		}

		void insert(Node<T> node) {
			//插入的节点小于当前节点，形成左小右大的二叉树
			if (less(node.value, value)){
				//若当前节点已有左子树，则从左子树开始插入节点
				if (left != null) left.insert(node);
				//若无则插入左节点
				else left = node;
			}
			else {
				//若当前节点已有右子树，则从右子树开始插入节点
				if (right != null) right.insert(node);
				//若无则插入右节点
				else right = node;
			}
		}

		void traverse(TreeVisitor<T> visitor) {//先序遍历
			//先左子树
			if ( left != null)
				left.traverse(visitor);
			//在自身
			visitor.visit(this);
			//后右子树
			if ( right != null )
				right.traverse(visitor );
		}

	}


	/**
	 * 排序算法
	 * @param array
	 * @param <T>
	 * @return
	 */
	@Override
	public  <T extends Comparable<T>> T[] sort(T[] array) {
		//将数组[0]转化成root节点
		Node<T> root = new Node<>(array[0]);
		for (int i = 1; i < array.length; i++) {
			//先将数组转换成节点，往树里插入节点元素
			root.insert(new Node<>(array[i]));
		}


		root.traverse(new SortVisitor<>(array));

		return array;
	}


	/**
	 * 测试的入口
	 * @param args
	 */
	public static void main(String args[]) {

		Integer[] intArray = {12, 40, 9, 3, 19, 74, 7, 31, 23, 54, 26, 81, 12};
		BinaryTreeSort treeSort = new BinaryTreeSort();
		Integer[] sorted = treeSort.sort(intArray);
		print(sorted);

		Double[] decimalArray = {8.2, 1.5, 3.14159265, 9.3, 5.1, 4.8, 2.6};
		print(treeSort.sort(decimalArray));

		String[] stringArray = {"c", "a", "e", "b","d", "dd","da","zz", "AA", "aa","aB","Hb", "Z"};
		print(treeSort.sort(stringArray));
	}

}

