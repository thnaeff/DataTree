# DataTree
**A fully functional tree library which includes list/set/map trees, printers to print Text/HTML/CSV, iterators and much more**


The project is separated in four packages:
- The base package *datatree*: These tree implementations are the main implementations to use. There is an implementation 
for a tree backed by a key list, a key set, a list or a set.
- The sub-package *datatree.core*: The core package contains the basic structures on which the main implementations are built up on. 
The most important class here is `CollectionTreeNodeInterface`, because this is the base of every tree node implementation.
- The sub-package *datatree.onoff*: Special implementations of the tree which support ignoring and hiding of tree nodes.
- The sub-package *datatree.printer*: The printer package offers a set of printers which can be used to print the tree 
structures of this project in various formats (currently HTML, CSV, text), but it also allows the implementation of any tree-like 
structure to be printed.

Various tree implementations:
- `KeyListTreeNode`: A tree in which the nodes can be accessed though a key or though their index. The nodes are ordered in a list.
- `KeySetTreeNode`: A tree in which the nodes can be accessed though a key. The nodes are unordered in a set.
- `ListTreeNode`: A tree in which the child nodes are ordered in a list. The nodes can only be accessed through their index.
- `SetTreeNode`: A tree in which the child nodes are unordered in a set. The nodes can only be accessed by using an iterator.

All tree implementations implement the `Iterator` interface. A call to `iterator()` returns an `TreeIterator`. All list tree 
implementations (list and keylist) also implement the `ListIterator` interface in addition to the `Iterator` interface.  
The method `listIterator()` returns a `ListTreeIterator` which supports the iteration through the whole tree, forward and backwards.


```
Tree
├─ Child 1
│  ├─ Child 1.1
│  └─ Child 1.2
├─ Child 2
│  ├─ Child 2.1
│  ├─ Child 2.2
│  │  ├─ Child 2.2.1
│  │  └─ Child 2.2.2
│  ├─ Child 2.3
│  └─ Child 2.4
├─ Child 3
│  ├─ Child 3.1
│  └─ Child 3.2
└─ Child 4
   └─ Child 4.1
      └─ Child 4.2
```
