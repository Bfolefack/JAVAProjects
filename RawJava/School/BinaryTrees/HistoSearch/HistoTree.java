//(c) A+ Computer Science
//www.apluscompsci.com

//Name -

public class HistoTree {
	private TreeNode root;

	public HistoTree() {
		root = null;
	}

	public void addData(char val) {
		ThingCount hist = new ThingCount((Character)val, 1);
		if (root == null) {
			root = new TreeNode(hist);
			return;
		}
		root = add(hist, root);
	}

	public void addData(int val) {
		ThingCount hist = new ThingCount((Integer) val, 1);
		if (root == null) {
			root = new TreeNode(hist);
			return;
		}
		root = add(hist, root);
	}

	public void addData(Object val) {
		ThingCount hist = new ThingCount((Comparable) val, 1);
		if (root == null) {
			root = new TreeNode(hist);
			return;
		}
		root = add(hist, root);
	}

	private TreeNode add(Comparable val, TreeNode tree) {
		if (tree == null) {
			tree = new TreeNode(new ThingCount(val, 1));
			return tree;
		}

		Comparable treeValue = tree.getValue();
		int dirTest = treeValue.compareTo(val) * -1;

		if (dirTest < 0){
			tree.setLeft(add(val, tree.getLeft()));
			return tree;
		}else if (dirTest > 0){
			tree.setRight(add(val, tree.getRight()));
			return tree;
		}else {
			if (tree.getValue() instanceof ThingCount) {
				((ThingCount) tree.getValue()).increment();
			}
			return tree;
		}
	}

	public void inOrder() {
		inOrder(root);
		System.out.println("\n\n");
	}

	private void inOrder(TreeNode tree) {
		if (tree != null) {
			inOrder(tree.getLeft());
			System.out.print(tree.getValue() + " ");
			inOrder(tree.getRight());
		}
	}

	// add preOrder, postOrder, and revOrder

	public void preOrder() {
		preOrder(root);
		System.out.println("\n\n");
	}

	public void preOrder(TreeNode node) {
		if (node != null) {
			System.out.print(node.getValue() + " ");
			preOrder(node.getLeft());
			preOrder(node.getRight());
		}
	}

	public void postOrder() {
		postOrder(root);
		System.out.println("\n\n");
	}

	public void postOrder(TreeNode node) {
		if (node != null) {
			postOrder(node.getLeft());
			postOrder(node.getRight());
			System.out.print(node.getValue() + " ");
		}
		return;
	}

	public void revOrder() {
		revOrder(root);
		System.out.println("\n\n");
	}

	public void revOrder(TreeNode node) {
		if (node != null) {
			revOrder(node.getRight());
			System.out.print(node.getValue() + " ");
			revOrder(node.getLeft());
		}
		return;
	}

	public String toString() {
		String out = getString(root);
		return out;
	}

	public String getString(TreeNode node) {
		if (node.getLeft() != null && node.getRight() != null) {
			return getString(node.getLeft()) + " " + node.getValue() + " " + getString(node.getRight());
		} else if (node.getLeft() != null) {
			return getString(node.getLeft()) + " " + node.getValue();
		} else if (node.getRight() != null) {
			return node.getValue() + " " + getString(node.getRight());
		}
		return node.getValue().toString();
	}

	public int getHeight() {
		return getNumLevels() - 1;
	}

	public int getNumLeaves() {
		return getNumLeaves(root);
	}

	private int getNumLeaves(TreeNode node) {
		if (node.getLeft() != null && node.getRight() != null) {
			return getNumLeaves(node.getLeft()) + getNumLeaves(node.getRight());
		} else if (node.getLeft() != null) {
			return getNumLeaves(node.getLeft());
		} else if (node.getRight() != null) {
			return getNumLeaves(node.getRight());
		}
		return 1;
	}

	public int getNumNodes() {
		return getNumNodes(root);
	}

	private int getNumNodes(TreeNode node) {
		if (node.getLeft() != null && node.getRight() != null) {
			return getNumNodes(node.getLeft()) + getNumNodes(node.getRight()) + 1;
		} else if (node.getLeft() != null) {
			return getNumNodes(node.getLeft()) + 1;
		} else if (node.getRight() != null) {
			return getNumNodes(node.getRight()) + 1;
		}
		return 1;
	}

	public boolean isFull() {
		return isFull(root);
	}

	private boolean isFull(TreeNode node) {
		if (node.getLeft() != null && node.getRight() != null) {
			return isFull(node.getLeft()) && isFull(node.getRight());
		} else if (node.getLeft() == null && node.getRight() == null) {
			return true;
		}
		return false;
	}

	public int getNumLevels() {
		return getNumLevels(root);
	}

	private int getNumLevels(TreeNode tree) {
		if (tree == null)
			return 0;
		else if (getNumLevels(tree.getLeft()) > getNumLevels(tree.getRight()))
			return 1 + getNumLevels(tree.getLeft());
		else
			return 1 + getNumLevels(tree.getRight());
	}

	public class widthObj {
		int lLP;
		int rLP;
		int aLP;

		widthObj(int l, int r) {
			lLP = l;
			rLP = r;
			aLP = lLP + rLP;
		}
	}

	public int getWidth() {
		return getWidth(root);
	}

	private int getWidth(TreeNode node) {
		if (node.getLeft() != null && node.getRight() != null) {
			int leftLength = getLongest(node.getLeft());
			int rightLength = getLongest(node.getRight());
			int width = leftLength + rightLength;
			int leftWidth = getWidth(node.getLeft());
			int rightWidth = getWidth(node.getRight());
			if (leftWidth > width) {
				return leftWidth + 1;
			} else if (rightWidth > width) {
				return rightWidth + 1;
			} else {
				return width + 1;
			}
		} else if (node.getLeft() != null) {
			return getWidth(node.getLeft()) + 1;
		} else if (node.getRight() != null) {
			return getWidth(node.getRight()) + 1;
		} else {
			return 1;
		}
	}

	private int getLongest(TreeNode node) {
		if (node.getLeft() != null && node.getRight() != null) {
			int left = getLongest(node.getLeft());
			int right = getLongest(node.getRight());
			if (left > right) {
				return left + 1;
			} else {
				return right + 1;
			}
		} else if (node.getLeft() != null) {
			return getLongest(node.getLeft()) + 1;
		} else if (node.getRight() != null) {
			return getLongest(node.getRight()) + 1;
		}
		return 1;
	}
}