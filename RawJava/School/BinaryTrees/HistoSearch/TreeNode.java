//(c) A+ Computer Science
//www.apluscompsci.com

//Name -

public class TreeNode implements Treeable
{
	private ThingCount treeNodeValue;
	private TreeNode leftTreeNode;
	private TreeNode rightTreeNode;

	public TreeNode( )
	{
		treeNodeValue = null;
		leftTreeNode = null;
		rightTreeNode = null;
	}

	public TreeNode(ThingCount value)
	{
		treeNodeValue = value;
		leftTreeNode = null;
		rightTreeNode = null;
	}

	public TreeNode(ThingCount value, TreeNode left, TreeNode right)
	{
		treeNodeValue = value;
		leftTreeNode = left;
		rightTreeNode = right;
	}

	public Comparable getValue()
	{
		return treeNodeValue;
	}

	public TreeNode getLeft()
	{
		return leftTreeNode;
	}

	public TreeNode getRight()
	{
		return rightTreeNode;
	}

	public void setValue(ThingCount value)
	{
		treeNodeValue = value;
	}

	public void setLeft(Treeable left)
	{
		leftTreeNode = (TreeNode)left;
	}

	public void setRight(Treeable right)
	{
		rightTreeNode = (TreeNode)right;
	}

	public String toString(){
		return treeNodeValue.toString();
	}

	@Override
	public void setValue(Comparable value) {
		setValue((ThingCount)value);
		
	}
}