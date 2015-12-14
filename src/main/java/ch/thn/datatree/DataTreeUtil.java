/**
 *    Copyright 2015 Thomas Naeff (github.com/thnaeff)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package ch.thn.datatree;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

import ch.thn.datatree.core.CollectionTreeNodeInterface;
import ch.thn.datatree.core.GenericKeySetTreeNode;
import ch.thn.datatree.core.ListTreeNodeInterface;

/**
 *
 * @author Thomas Naeff (github.com/thnaeff)
 *
 */
public class DataTreeUtil {
	
	/**
	 * Creates a comparator which compares the toString output of parameters of type K 
	 * 
	 * @return
	 */
	public static <K> Comparator<K> getDefaultKeyComparator() {
		return new Comparator<K>() {

			@Override
			public int compare(K o1, K o2) {
				if (o1 == null && o2 != null) { return 1; }
				if (o1 != null && o2 == null) { return -1; }
				if (o1 == o2) { return 0; }
				
				return o1.toString().compareTo(o2.toString());
			}
		};
	}
	
	/**
	 * Creates a comparator which compares the toString output of the tree node 
	 * value of type N 
	 * 
	 * @return
	 */
	public static <N extends GenericKeySetTreeNode<?, ?, N>> Comparator<N> getDefaultValueComparator() {
		return new Comparator<N>() {

			@Override
			public int compare(N o1, N o2) {
				if (o1 == null && o2 != null) { return 1; }
				if (o1 != null && o2 == null) { return -1; }
				if (o1 == o2) { return 0; }
				
				if (o1.getNodeValue() == null && o2.getNodeValue() != null) { return 1; }
				if (o1.getNodeValue() != null && o2.getNodeValue() == null) { return -1; }
				if (o1.getNodeValue() == o2.getNodeValue()) { return 0; }
				
				return o1.getNodeValue().toString().compareTo(o2.getNodeValue().toString());
			}
		};
	}
	
	
	/**
	 * Makes a copy of the whole tree, starting at the given node
	 * 
	 * @param node
	 * @return
	 */
	public static <N extends CollectionTreeNodeInterface<?, N>> N copyTree(N node) {	
		return copyTree(node, null);
	}
	
	/**
	 * Makes a copy of the whole tree, starting at the given node.
	 * If the creation of node instances has to be controlled, a node factory can be provided for that purpose.
	 * 
	 * @param node
	 * @param nodeFactory
	 * @return
	 */
	public static <N extends CollectionTreeNodeInterface<?, N>> N copyTree(N node, 
			DataTreeCopyNodeFactory<N> nodeFactory) {	
		N newNode = node.nodeFactory(node);
		DataTreeUtil.copyTreeChildNodes(newNode, node, nodeFactory);
		return newNode;
	}
	
	/**
	 * 
	 * 
	 * @param targetNode
	 * @param sourceNode
	 */
	private static <N extends CollectionTreeNodeInterface<?, N>> void copyTreeChildNodes(N targetNode, 
			N sourceNode, DataTreeCopyNodeFactory<N> nodeFactory) {
				
		if (sourceNode.isLeafNode()) {
			//Skip if there are no child nodes
			return;
		}
				
		for (N sourceChildNode : sourceNode.getChildNodes()) {
			//Add a new child node with the data of the source child node
			N newTargetChildNode = null;
			if (nodeFactory == null) {
				newTargetChildNode = targetNode.addChildNodeCopy(sourceChildNode);
			} else {
				newTargetChildNode = nodeFactory.newInstanceCopy(sourceChildNode);
				targetNode.addChildNode(newTargetChildNode);
			}
			
			//Add all children of the source child node to the new child node
			DataTreeUtil.copyTreeChildNodes(newTargetChildNode, sourceChildNode, nodeFactory);			
			
		}
		
	}
	
	/**
	 * Builds the intersect of two trees. 
	 * The given {@link TreeIntersectComparator} does the comparison between two nodes and decides if they are equal 
	 * or not. 
	 * The resulting tree is a copy of the master tree (using the node factory methods of the master tree)
	 * 
	 * @param masterNode
	 * @param slaveNode
	 * @param walker
	 * @return
	 */
	public static <N extends CollectionTreeNodeInterface<?, N>> N intersect(N masterNode, N slaveNode, 
			TreeIntersectComparator<N> walker) {
		
		if (walker.compare(masterNode, slaveNode) != 0) {
			//The head nodes are already not equal.
			return null;
		}
		
		N intersectTree = masterNode.nodeFactory(masterNode);
		
		intersectChildren(masterNode, slaveNode, walker, intersectTree);
		
		return intersectTree;
	}
	
	/**
	 * 
	 * 
	 * @param masterNode
	 * @param slaveNode
	 * @param walker
	 * @param intersectTree
	 */
	private static <K, N extends CollectionTreeNodeInterface<?, N>> void intersectChildren(N masterNode, N slaveNode, 
			TreeIntersectComparator<N> walker, N intersectTree) {
		
		Collection<N> masterChildNodes = masterNode.getChildNodes();
		Collection<N> slaveChildNodes = slaveNode.getChildNodes();
		
		Collection<N> intersectChildNodes = intersectTree.getChildNodes();
		
		//Compare each master node to each child node
		for (N masterChildNode : masterChildNodes) {
			for (N slaveChildNode : slaveChildNodes) {
				if (walker.compare(masterChildNode, slaveChildNode) == 0) {
					boolean alreadyAdded = false;
					//Only add the matching child node if not added yet
					for (N intersectChildNode : intersectChildNodes) {
						if (walker.compare(masterChildNode, intersectChildNode) == 0) {
							alreadyAdded = true;
							break;
						}
					}
					
					if (! alreadyAdded) {
						N intersectChildTree = intersectTree.addChildNode(masterChildNode.nodeFactory(masterChildNode));
						intersectChildren(masterChildNode, slaveChildNode, walker, intersectChildTree);
					}
				}
			}
		}
		
	}
	
	/**
	 * Goes through the whole tree and looks for the highest node level
	 * 
	 * @param node
	 * @return
	 */
	public static <N extends CollectionTreeNodeInterface<?, N>> int highestNodeLevel(N node) {
		
		if (node.isLeafNode()) {
			return node.getNodeDepth();
		}
		
		//It is probably faster to iterate through the tree like this than 
		//using node.iterator() which goes step by step and has to search for the next node
		Iterator<N> iterator = node.getChildNodes().iterator();
		
		int highest = 0;
		int current = 0;
		
		while (iterator.hasNext()) {
			N childNode = iterator.next();
			
			current = highestNodeLevel(childNode);
			if (current > highest) {
				highest = current;
			}
			
		}
		
		return highest;
	}
	
	/**
	 * Jumps to the very last leaf node of the branch of the given node.
	 * 
	 * @param node
	 * @return
	 */
	public static <	V extends Object, 
					N extends ListTreeNodeInterface<V, N>> 
					ListTreeNodeInterface<V, N> getLastLeafNode(ListTreeNodeInterface<V, N> node) {
		
		if (node.isLeafNode()) {
			return node;
		}
		
		//Improved functionality with a list tree node (compared to the general getLastLeafNode() method)
		N lastChildNode = node.getLastChildNode();
		
		while (! lastChildNode.isLeafNode()) {
			lastChildNode = lastChildNode.getLastChildNode();
		}
		
		return lastChildNode;
	}
	
	/**
	 * Jumps to the very last leaf node of the branch of the given node.
	 * 
	 * 
	 * @param node
	 * @return
	 */
	public static <	V extends Object, 
					N extends CollectionTreeNodeInterface<V, N>> 
					CollectionTreeNodeInterface<V, N> getLastLeafNode(CollectionTreeNodeInterface<V, N> node) {
		
		if (node.isLeafNode()) {
			return node;
		}
		
		N lastChildNode = getLastSibling(node.iterator());
		
		while (! lastChildNode.isLeafNode()) {
			lastChildNode = getLastSibling(lastChildNode.iterator());
		}
		
		return lastChildNode;
	}
	
	/**
	 * Iterates over the given iterator until the last element is reached
	 * 
	 * @param iterator
	 * @return
	 */
	private static <N extends CollectionTreeNodeInterface<?, N>> N getLastSibling(Iterator<N> iterator) {
		N lastSibling = null;
		while (iterator.hasNext()) {
			lastSibling = iterator.next();
		}
		
		return lastSibling;
	}
	
	
	
	/**********************************************************************************************************
	 * 
	 * 
	 * @author Thomas Naeff (github.com/thnaeff)
	 *
	 * @param <N>
	 */
	public interface DataTreeCopyNodeFactory<N> {
		
		/**
		 * Creates a new instance of a tree node and copies needed data
		 * 
		 * @param node
		 * @return
		 */
		public N newInstanceCopy(N node);

	}
	
	/***********************************************************************************************************
	 * 
	 * 
	 * 
	 * @author Thomas Naeff (github.com/thnaeff)
	 *
	 * @param <N>
	 */
	public interface TreeIntersectComparator<N extends CollectionTreeNodeInterface<?, N>> extends Comparator<N> {
		
		/**
		 * Compares the master node to a slave node. If they are equal (return code 0), 
		 * the tree walking continues with this node. If they are not equal (return code != 1), 
		 * the tree walking stops at this node and continues with its parent/sibling node.
		 */
		@Override
		int compare(N masterNode, N slaveNode);
		
	}

}
