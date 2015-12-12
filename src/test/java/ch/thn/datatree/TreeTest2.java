package ch.thn.datatree;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import ch.thn.datatree.printer.TreeNodePlainTextPrinter;
import ch.thn.datatree.printer.generic.PlainTextTreePrinter;

public class TreeTest2 {

	@Test
	public void testMultiKey() throws Exception {
		
		KeyListTreeNode<String, String> keyListNode = new KeyListTreeNode<String, String>("", "Tree");
		KeySetTreeNode<String, String> keySetNode = new KeySetTreeNode<String, String>("", "Tree");
		
		//Same key multiple times
		
		keyListNode.addChildNode("key1", "Value1");
		keyListNode.addChildNode("key1", "Value2");
		
		keySetNode.addChildNode("key1", "Value1");
		keySetNode.addChildNode("key1", "Value2");
		//Same key-value combination. Because this tree is backed by a set, 
		//the default comparator will prevent it from being added
		keySetNode.addChildNode("key1", "Value2");
		
		
		
		TreeNodePlainTextPrinter printer = new TreeNodePlainTextPrinter();
		
		StringBuilder keyListNodeOut = printer.print(keyListNode);
		StringBuilder keySetNodeOut = printer.print(keySetNode);
		
		
		String reference = "Tree" + PlainTextTreePrinter.LINE_SEPARATOR 
				+ "├─ Value1" + PlainTextTreePrinter.LINE_SEPARATOR 
				+ "└─ Value2" + PlainTextTreePrinter.LINE_SEPARATOR;
		
		
		System.out.println("-- Reference tree -------------------------------------");
		System.out.println(reference);
		System.out.println("--- Key list tree ------------------------------------");
		System.out.println(keyListNodeOut);
		System.out.println("--- Key set tree ------------------------------------");
		System.out.println(keySetNodeOut);
		
		
		//Check that all the printed trees match the reference
		assertThat(keyListNodeOut.toString(), is(equalTo(reference)));
		assertThat(keySetNodeOut.toString(), is(equalTo(reference)));
		
	}
	
	
	
	
}
