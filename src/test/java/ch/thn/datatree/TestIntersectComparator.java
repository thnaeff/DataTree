package ch.thn.datatree;

import ch.thn.datatree.DataTreeUtil.TreeIntersectComparator;
import ch.thn.datatree.core.CollectionTreeNodeInterface;

public class TestIntersectComparator<N extends CollectionTreeNodeInterface<?, N>> implements TreeIntersectComparator<N> {

	@Override
	public int compare(N masterNode, N slaveNode) {
		return masterNode.getNodeValue().toString().compareTo(slaveNode.getNodeValue().toString());
	}

}
