This program is a proof of concept demonstration of Java serialization. It consists of three classes:

1. FileMgr, which handles user input and contains the program's main() function
2. NodeCollection, which contains an ArrayList of Nodes as well as some methods to handle that ArrayList
2. Node, which has two fields: an ID number and a name. 

The program has a shell-like interface which creates an interactive prompt and waits for user commands until the user exits from the prompt.

Commands and their usage:

- "init FILE" opens a handle to a file which will store the serialized nodes. If the file passed to the program already contains serialized nodes, they will be loaded into memory. 
- "add ID NAME" adds a node to the NodeCollection.
- "delete ID" removes a node from the NodeCollection.
- "list" displays the nodes contained in the NodeCollection as well as their IDs and names.
- "searchs ID" searches for a node by ID using a linear search and reports performance.
- "searchf ID" searches for a node by ID using a binary search and reports performance. This command sorts the NodeCollection before searching.
- "sorts" sorts the NodeCollection in place using a bubble sort and reports performance.
- "sortf" sorts the NodeCollection using a merge sort and reports performance.
- "flush" serializes the ArrayList contained within NodeCollection and writes it to the file indicated with the "init" command. 
- "exit" exits the program.

The NodeCollection is kept in memory unless explicitly flushed to disk. If the user exits without flushing, any nodes added will be lost.

Performance data can be found in the data/ directory.

To compile:

$ javac *.java

Here is an example session with the program:

$ java FileMgr
filemgr v0.1
$> init abc
New NodeCollection@4a574795 using file "abc"
$> list
NodeCollection@4a574795
{
}
$> add 1 john
Added Node@23fc625e
{
	"id": 1
	"name": "john"
}
$> add 2 tim
Added Node@3f99bd52
{
	"id": 2
	"name": "tim"
}
$> add 7 daniel
Added Node@4f023edb
{
	"id": 7
	"name": "daniel"
}
$> list
NodeCollection@4a574795
{
	{
		"id": 1
		"name": "john"
	}
	{
		"id": 2
		"name": "tim"
	}
	{
		"id": 7
		"name": "daniel"
	}
}
$> flush
NodeCollection@4a574795 flushed to abc
$> exit
$ java FileMgr
filemgr v0.1
$> init abc
New NodeCollection@1e80bfe8 using file "abc"
$> list
NodeCollection@1e80bfe8
{
	{
		"id": 1
		"name": "john"
	}
	{
		"id": 2
		"name": "tim"
	}
	{
		"id": 7
		"name": "daniel"
	}
}
$> delete 2
Deleted Node@66a29884
$> list
NodeCollection@1e80bfe8
{
	{
		"id": 1
		"name": "john"
	}
	{
		"id": 7
		"name": "daniel"
	}
}
$> flush
NodeCollection@1e80bfe8 flushed to abc
$> exit
$ java FileMgr
filemgr v0.1
$> init abc
New NodeCollection@1e80bfe8 using file "abc"
$> list
NodeCollection@1e80bfe8
{
	{
		"id": 1
		"name": "john"
	}
	{
		"id": 7
		"name": "daniel"
	}
}
$> exit
