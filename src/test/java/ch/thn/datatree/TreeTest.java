package ch.thn.datatree;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ch.thn.datatree.core.CollectionTreeNodeInterface;
import ch.thn.datatree.core.ListTreeNodeInterface;
import ch.thn.datatree.core.MapTreeNodeInterface;
import ch.thn.datatree.printer.TreeNodePlainTextPrinter;
import ch.thn.datatree.printer.generic.PlainTextTreePrinter;

public class TreeTest {
	
	
	/**
	 * Compares all the trees (list, set, key list and key set) and checks that 
	 * all of them produce the same printer output
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAllEquality() throws Exception {
		
		System.out.println("\n ================= Tree building and printing =====================\n");
		
		ListTreeNode<String> listNode = new ListTreeNode<String>("Tree");
		SetTreeNode<String> setNode = new SetTreeNode<String>("Tree");
		KeyListTreeNode<String, String> keyListNode = new KeyListTreeNode<String, String>("", "Tree");
		KeySetTreeNode<String, String> keySetNode = new KeySetTreeNode<String, String>("", "Tree");
		
		buildValueTree(listNode);
		buildValueTree(setNode);
		buildKeyValueTree(keyListNode);
		buildKeyValueTree(keySetNode);
		
		
		//Raw types so that the printer works for any node implementation of this library
		TreeNodePlainTextPrinter printer = new TreeNodePlainTextPrinter();
		
		StringBuilder listNodeOut = printer.print(listNode);
		StringBuilder setNodeOut = printer.print(setNode);
		StringBuilder keyListNodeOut = printer.print(keyListNode);
		StringBuilder keySetNodeOut = printer.print(keySetNode);
		
		String reference = "Tree" + PlainTextTreePrinter.LINE_SEPARATOR 
				+ "├─ Child 1" + PlainTextTreePrinter.LINE_SEPARATOR 
				+ "│  ├─ Child 1.1" + PlainTextTreePrinter.LINE_SEPARATOR 
				+ "│  └─ Child 1.2" + PlainTextTreePrinter.LINE_SEPARATOR 
				+ "├─ Child 2" + PlainTextTreePrinter.LINE_SEPARATOR 
				+ "│  ├─ Child 2.1" + PlainTextTreePrinter.LINE_SEPARATOR 
				+ "│  ├─ Child 2.2" + PlainTextTreePrinter.LINE_SEPARATOR 
				+ "│  │  ├─ Child 2.2.1" + PlainTextTreePrinter.LINE_SEPARATOR 
				+ "│  │  └─ Child 2.2.2" + PlainTextTreePrinter.LINE_SEPARATOR 
				+ "│  │     └─ Child 2.2.2.1" + PlainTextTreePrinter.LINE_SEPARATOR 
				+ "│  ├─ Child 2.3" + PlainTextTreePrinter.LINE_SEPARATOR 
				+ "│  └─ Child 2.4" + PlainTextTreePrinter.LINE_SEPARATOR 
				+ "│     └─ Child 2.4.1" + PlainTextTreePrinter.LINE_SEPARATOR
				+ "├─ Child 3" + PlainTextTreePrinter.LINE_SEPARATOR 
				+ "│  ├─ Child 3.1" + PlainTextTreePrinter.LINE_SEPARATOR 
				+ "│  └─ Child 3.2" + PlainTextTreePrinter.LINE_SEPARATOR 
				+ "└─ Child 4" + PlainTextTreePrinter.LINE_SEPARATOR 
				+ "   └─ Child 4.1" + PlainTextTreePrinter.LINE_SEPARATOR 
				+ "      └─ Child 4.2" + PlainTextTreePrinter.LINE_SEPARATOR;
		
		System.out.println("-- Reference tree -------------------------------------");
		System.out.println(reference);
		System.out.println("-- List tree -------------------------------------");
		System.out.println(listNodeOut);
		System.out.println("--- Set tree ------------------------------------");
		System.out.println(setNodeOut);
		System.out.println("--- Key list tree ------------------------------------");
		System.out.println(keyListNodeOut);
		System.out.println("--- Key set tree ------------------------------------");
		System.out.println(keySetNodeOut);
		
		
		//Check that all the printed trees match the reference
		assertThat(listNodeOut.toString(), is(equalTo(reference)));
		assertThat(setNodeOut.toString(), is(equalTo(reference)));
		assertThat(keyListNodeOut.toString(), is(equalTo(reference)));
		assertThat(keySetNodeOut.toString(), is(equalTo(reference)));
		
	}
	
	@Test
	public void treeIterationForward() throws Exception {
		
		System.out.println("\n ================= Tree iteration forward =====================\n");
		
		ListTreeNode<String> listNode = new ListTreeNode<String>("Tree");
		SetTreeNode<String> setNode = new SetTreeNode<String>("Tree");
		KeyListTreeNode<String, String> keyListNode = new KeyListTreeNode<String, String>("tree", "Tree");
		KeySetTreeNode<String, String> keySetNode = new KeySetTreeNode<String, String>("tree", "Tree");
		
		buildValueTree(listNode);
		buildValueTree(setNode);
		buildKeyValueTree(keyListNode);
		buildKeyValueTree(keySetNode);
		
		List<String> reference = new ArrayList<String>();
		reference.add("Tree");
		reference.add("Child 1");
		reference.add("Child 1.1");
		reference.add("Child 1.2");
		reference.add("Child 2");
		reference.add("Child 2.1");
		reference.add("Child 2.2");
		reference.add("Child 2.2.1");
		reference.add("Child 2.2.2");
		reference.add("Child 2.2.2.1");
		reference.add("Child 2.3");
		reference.add("Child 2.4");
		reference.add("Child 2.4.1");
		reference.add("Child 3");
		reference.add("Child 3.1");
		reference.add("Child 3.2");
		reference.add("Child 4");
		reference.add("Child 4.1");
		reference.add("Child 4.2");
		
		
		
		List<String> listNodeList = buildForwardListIterationList(listNode);
		List<String> setNodeList = buildFowardIterationList(setNode);
		List<String> keyListNodeList = buildForwardListIterationList(keyListNode);
		List<String> keySetNodeList = buildFowardIterationList(keySetNode);
		
		System.out.println("-- Reference tree -------------------------------------");
		System.out.println(reference);
		System.out.println("-- List tree -------------------------------------");
		System.out.println(listNodeList);
		System.out.println("--- Set tree ------------------------------------");
		System.out.println(setNodeList);
		System.out.println("--- Key list tree ------------------------------------");
		System.out.println(keyListNodeList);
		System.out.println("--- Key set tree ------------------------------------");
		System.out.println(keySetNodeList);
		
		//Check that all the iterator lists match the reference
		assertThat(listNodeList, is(reference));
		assertThat(setNodeList, is(reference));
		assertThat(keyListNodeList, is(reference));
		assertThat(keySetNodeList, is(reference));
		
	}
	
	@Test
	public void treeIterationBackwards() throws Exception {
		
		System.out.println("\n ================= Tree iteration backwards =====================\n");
		
		ListTreeNode<String> listNode = new ListTreeNode<String>("Tree");
		SetTreeNode<String> setNode = new SetTreeNode<String>("Tree");
		KeyListTreeNode<String, String> keyListNode = new KeyListTreeNode<String, String>("tree", "Tree");
		KeySetTreeNode<String, String> keySetNode = new KeySetTreeNode<String, String>("tree", "Tree");
		
		buildValueTree(listNode);
		buildValueTree(setNode);
		buildKeyValueTree(keyListNode);
		buildKeyValueTree(keySetNode);
		
		//TreeNodePlainTextPrinter<ListTreeNode<String>> printer = new TreeNodePlainTextPrinter<ListTreeNode<String>>();
		//System.out.println(printer.print(listNode));
		
		List<String> reference = new ArrayList<String>();
		reference.add("Child 4.2");
		reference.add("Child 4.1");
		reference.add("Child 4");
		reference.add("Child 3.2");
		reference.add("Child 3.1");
		reference.add("Child 3");
		reference.add("Child 2.4.1");
		reference.add("Child 2.4");
		reference.add("Child 2.3");
		reference.add("Child 2.2.2.1");
		reference.add("Child 2.2.2");
		reference.add("Child 2.2.1");
		reference.add("Child 2.2");
		reference.add("Child 2.1");
		reference.add("Child 2");
		reference.add("Child 1.2");
		reference.add("Child 1.1");
		reference.add("Child 1");
		reference.add("Tree");

		
		List<String> listNodeListBackwards = buildBackwardListIterationList(listNode);
		List<String> keyListNodeListBackwards = buildBackwardListIterationList(keyListNode);
		
		
		System.out.println("-- Reference tree -------------------------------------");
		System.out.println(reference);
		System.out.println("-- List tree -------------------------------------");
		System.out.println(listNodeListBackwards);
		System.out.println("--- Key list tree ------------------------------------");
		System.out.println(keyListNodeListBackwards);
		
		//Check that all the iterator lists match the reference
		assertThat(listNodeListBackwards, is(reference));
		assertThat(keyListNodeListBackwards, is(reference));
				
	}
	
	@Test
	public void treeIterationAndNavigation() throws Exception {
		
		System.out.println("\n ================= Tree iteration/navigation =====================\n");
		
		ListTreeNode<String> listNode = new ListTreeNode<String>("Tree");
		
		buildValueTree(listNode);
		
		
		//TreeNodePlainTextPrinter<ListTreeNode<String>> printer = new TreeNodePlainTextPrinter<ListTreeNode<String>>();
		//System.out.println(printer.print(listNode));
		
		
		List<String> reference = new ArrayList<String>();
		//Forward iteration
		reference.add("Tree");
		reference.add("Child 1");
		reference.add("Child 1.1");
		reference.add("Child 1.2");
		reference.add("Child 2");
		
		//Backward iteration
		reference.add("Child 2");
		reference.add("Child 1.2");
		reference.add("Child 1.1");
		reference.add("Child 1");
		reference.add("Tree");
		
		//Jump to child
		reference.add("Child 2");
		
		//Last leaf node
		reference.add("Child 2.4.1");
		
		//Peek previous
		reference.add("Child 2.4.1");
		reference.add("Child 2.4");
		reference.add("Child 2.4");
		reference.add("Child 2.4");
		
		//Peek next
		reference.add("Child 2.4");
		reference.add("Child 2.4.1");
		reference.add("Child 2.4.1");
		reference.add("Child 2.4.1");
		
		List<String> iterationList = new ArrayList<String>();
		
		//Use advanced list iterator
		ListTreeIterator<ListTreeNode<String>> iterator = listNode.listIterator();
		
		try {
			//Forward iteration
			iterationList.add(iterator.next().getNodeValue());
			iterationList.add(iterator.next().getNodeValue());
			iterationList.add(iterator.next().getNodeValue());
			iterationList.add(iterator.next().getNodeValue());
			iterationList.add(iterator.next().getNodeValue());
						
			//Backward iteration
			iterationList.add(iterator.previous().getNodeValue());
			iterationList.add(iterator.previous().getNodeValue());
			iterationList.add(iterator.previous().getNodeValue());
			iterationList.add(iterator.previous().getNodeValue());
			iterationList.add(iterator.previous().getNodeValue());
			
			//Jump to child
			ListTreeNode<String> child2 = listNode.getChildNode(1);
			iterationList.add(child2.getNodeValue());
			
			//Last leaf node
			ListTreeNode<String> child241 = (ListTreeNode<String>) TreeUtil.getLastLeafNode(child2);
			iterationList.add(child241.getNodeValue());
			
			iterator = child241.listIterator();
			
			//Peek previous
			//First call to previous returns the current element
			iterationList.add(iterator.previous().getNodeValue());
			//Peek to previous now returns the previous element, but does not move the pointer
			iterationList.add(iterator.peekPrevious().getNodeValue());
			iterationList.add(iterator.peekPrevious().getNodeValue());
			iterationList.add(iterator.previous().getNodeValue());
			
			//Peek next
			//First call to next returns the current element
			iterationList.add(iterator.next().getNodeValue());
			//Peek to next now returns the next element, but does not move the pointer
			iterationList.add(iterator.peekNext().getNodeValue());
			iterationList.add(iterator.peekNext().getNodeValue());
			iterationList.add(iterator.next().getNodeValue());
			
			
		} catch (Exception e) {
			e.printStackTrace();
			//Just continue and print what we have
		}
		
		
		System.out.println("-- Reference iteration -------------------------------------");
		System.out.println(reference);
		System.out.println("-- List iteration -------------------------------------");
		System.out.println(iterationList);
		
		//Check that all the iterator lists match the reference
		assertThat(iterationList, is(reference));
	}
	
	/**
	 * 
	 * 
	 * @param headNode
	 * @return
	 */
	private List<String> buildFowardIterationList(CollectionTreeNodeInterface<String, ?> headNode) {
		
		List<String> list = new ArrayList<String>();
		
		//Use basic iterator
		TreeIterator<? extends CollectionTreeNodeInterface<String, ?>> iterator = headNode.iterator();
		
		while (iterator.hasNext()) {
			list.add(iterator.next().getNodeValue());
		}
		
		return list;
		
	}
	
	/**
	 * 
	 * 
	 * @param headNode
	 * @return
	 */
	private List<String> buildForwardListIterationList(ListTreeNodeInterface<String, ?> headNode) {
		
		List<String> list = new ArrayList<String>();
		
		//Use advanced list iterator
		ListTreeIterator<? extends ListTreeNodeInterface<String, ?>> iterator = headNode.listIterator();
		
		while (iterator.hasNext()) {
			list.add(iterator.next().getNodeValue());
		}
		
		return list;
		
	}
	
	/**
	 * 
	 * 
	 * @param headNode
	 * @return
	 */
	private List<String> buildBackwardListIterationList(ListTreeNodeInterface<String, ?> headNode) {
		
		List<String> list = new ArrayList<String>();
		
		ListTreeNodeInterface<String, ?> lastNode = TreeUtil.getLastLeafNode(headNode);
		
		//Use advanced list iterator
		ListTreeIterator<? extends ListTreeNodeInterface<String, ?>> iterator = lastNode.listIterator();
		
		while (iterator.hasPrevious()) {
			String s = iterator.previous().getNodeValue();
			list.add(s);
		}
		
		return list;
		
	}
	
	
	/**
	 * 
	 * 
	 * @param headNode
	 */
	private void buildValueTree(CollectionTreeNodeInterface<String, ?> headNode) {
		
		headNode.addChildNode("Child 1")
			.addChildNode("Child 1.1").getParentNode()
			.addChildNode("Child 1.2");
	
		headNode.addChildNode("Child 2")
			.addChildNode("Child 2.1").getParentNode()
			.addChildNode("Child 2.2")
				.addChildNode("Child 2.2.1").getParentNode()
				.addChildNode("Child 2.2.2")
					.addChildNode("Child 2.2.2.1").getParentNode().getParentNode().getParentNode()
			.addChildNode("Child 2.3").getParentNode()
			.addChildNode("Child 2.4")
				.addChildNode("Child 2.4.1");
	
		headNode.addChildNode("Child 3")
			.addChildNode("Child 3.1").getParentNode()
			.addChildNode("Child 3.2");
	
		headNode.addChildNode("Child 4")
			.addChildNode("Child 4.1")
			.addChildNode("Child 4.2");
		
		
	}

	/**
	 * 
	 * 
	 * @param headNode
	 */
	private void buildKeyValueTree(MapTreeNodeInterface<String, String, ?> headNode) {
		
		headNode.addChildNode("child_1", "Child 1")
			.addChildNode("child_11", "Child 1.1").getParentNode()
			.addChildNode("child_12", "Child 1.2");
	
		headNode.addChildNode("child_2", "Child 2")
			.addChildNode("child_21", "Child 2.1").getParentNode()
			.addChildNode("child_22", "Child 2.2")
				.addChildNode("child_221", "Child 2.2.1").getParentNode()
				.addChildNode("child_222", "Child 2.2.2")
					.addChildNode("child_2221", "Child 2.2.2.1").getParentNode().getParentNode().getParentNode()
			.addChildNode("child_23", "Child 2.3").getParentNode()
			.addChildNode("child_24", "Child 2.4")
				.addChildNode("child_241", "Child 2.4.1");
	
		headNode.addChildNode("child_3", "Child 3")
			.addChildNode("child_31", "Child 3.1").getParentNode()
			.addChildNode("child_32", "Child 3.2");
	
		headNode.addChildNode("child_4", "Child 4")
			.addChildNode("child_41", "Child 4.1")
			.addChildNode("child_42", "Child 4.2");
		
		
	}
	

}
