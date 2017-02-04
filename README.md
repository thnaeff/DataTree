# DataTree
**A fully functional tree library which includes list, set and map trees, printers to print a tree as Text, HTML or CSV, iterators and much more**

---


[![License](http://img.shields.io/badge/License-Apache v2.0-802879.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Java Version](http://img.shields.io/badge/Java-1.6%2B-2E6CB8.svg)](https://java.com)
[![Apache Maven ready](http://img.shields.io/badge/Apache Maven ready-3.3.9%2B-FF6804.svg)](https://maven.apache.org/)


---


The project is separated in four packages:
- The base package *datatree*: These tree implementations are the main implementations to use. See the various tree implementations below.
- The sub-package *datatree.core*: The core package contains the basic structures on which the main implementations are built up on. The most important class here is `CollectionTreeNodeInterface`, because this is the base of every tree node implementation. Each tree implementation consists of an interface and an abstract implementation.
- The sub-package *datatree.printer*: The printer package offers a set of printers which can be used to print the tree structures of this project in various formats (currently HTML, CSV, text), but it also allows any tree-like structure to be printed.
- The sub-package *datatree.onoff*: Special implementations of the tree which support ignoring (no printing) and hiding (collapsing) of tree nodes.


## Tree implementations:
- `KeyListTreeNode`: A tree in which the nodes can be accessed though a key or though their index. The nodes are stored in an ordered list.
- `KeySetTreeNode`: A tree in which the nodes can be accessed though a key. The nodes are unordered in a set.
- `ListTreeNode`: A tree in which the nodes are stored in an ordered list. The nodes can only be accessed through their index.
- `SetTreeNode`: A tree in which the nodes are unordered in a set. The nodes can only be accessed by using an iterator.

All tree implementations implement the `Iterator` interface. A call to `iterator()` returns an `TreeIterator`. All list tree implementations (list and keylist) also implement the `ListIterator` interface in addition to the `Iterator` interface. The method `listIterator()` returns a `ListTreeIterator` which supports more advanced iteration (forward, backwards, peek).

### Tree examples




## Tree printer implementations
- `TreeNodePlainTextPrinter`: Prints a tree in plain text (see example below)
- `TreeNodeDebutPrinter`: Prints a tree in plain text, formatted with additional node information to analyze and debug a tree (e.g. line number, child index, node depth, node key and value, ...)
- `TreeNodeCSVPrinter`: Prints a tree in the CSV format to be used in any spreadsheet program (e.g. Excel, LibreOffice/OpenOffice Calc, ...). The tree structure and the values are aligned in columns for spreadsheet usability.
- `TreeNodeHTMLPrinter`: Prints the tree as HTML table to be displayed in a web browser. Various formatting (color, size, ...) options are built in.


Here is an example of a tree (as used in the JUnit test cases). This tree is printed using the built in plain text tree printer.

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
│  │     └─ Child 2.2.2.1
│  ├─ Child 2.3
│  └─ Child 2.4
│     └─ Child 2.4.1
├─ Child 3
│  ├─ Child 3.1
│  └─ Child 3.2
└─ Child 4
   └─ Child 4.1
      └─ Child 4.2
```


### Tree printer examples



----



# OnOff tree

This is a special implementation of the tree, built on top of the regular data tree. It offers additional functionality to control the appearance of nodes in the tree. Features are:

- Hiding a node: A hidden node does not show up in the tree, which also means that all its child nodes are not visible in the tree
- Ignoring a node: An ignored node is skipped when traveling through the tree. This means that all its child nodes will appear as child nodes of its parent node.
- Hiding child nodes: Only the child nodes of a node do not appear in the tree. The node itself stays visible.
- Force visibility: Overwrites/ignores any state set with the previous three functions.


### OnOff tree examples




---


<img src="http://maven.apache.org/images/maven-logo-black-on-white.png" alt="Built with Maven" width="150">

This project can be built with Maven

Maven command:
```
$ mvn clean install
```

pom.xml entry in your project:
```
<dependency>
	<groupId>ch.thn</groupId>
	<artifactId>datatree</artifactId>
	<version>0.0.1-SNAPSHOT</version>
</dependency>
```

---

