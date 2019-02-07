import java.io.*;
import java.util.*;

class NodeCollection {
    // nodes stored here
    private ArrayList<Node> nodes;
    // handle to the file which stores the serialized ArrayList
    public File file;

    // constructor
    public NodeCollection(String file) { 
        this.nodes = new ArrayList<Node>();
        this.file = new File(file);
        try {
            this.file.createNewFile();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    // read ArrayList from file - "deserialize"
    public void readNodes() {
        // don't bother deserializing an empty file
        if (this.file.length() == 0) {
            return;
        }
    
        try {
            FileInputStream fin = new FileInputStream(this.file);
            ObjectInputStream in = new ObjectInputStream(fin);
            this.nodes = (ArrayList<Node>) in.readObject();
            in.close();
            fin.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println(this.file.getPath() + " does not contain NodeCollection");
            c.printStackTrace();
        }

        return;
    }

    // write ArrayList to file - "serialize"
    public void writeNodes() {
        try {
            new PrintWriter(this.file.getPath()).close(); // empty the file
            FileOutputStream fout = new FileOutputStream(this.file);
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(this.nodes);
            out.close();
            fout.close();
        } catch (IOException i) {
            i.printStackTrace();
        }

        return;
    }

    // list nodes in collection
    // easier to traverse an array than an ArrayList so we convert using toArray
    public <Node> Node[] listNodes(Node a[]) {
        return this.nodes.toArray(a);
    }

    // add a node to the collection
    public Node addNode(int id, String name) {
        Node localnode = new Node(id, name);
        this.nodes.add(localnode);
        return localnode;
    }

    // delete a node from the collection
    public Node delNode(int id) {
        // find node by id
        // remove it from this.nodes
        // TODO: Use a better search algorithm (this.searchFast) for competency 0.0f

        Node localnode = null;

        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).id == id) {
                localnode = nodes.get(i);
                nodes.remove(i);
            }
        }

        return localnode;
    }
    
    // get the size of the ArrayList. the ArrayList is private, so we need to use a getter
    public int getSize() {
        return nodes.size();
    }
}


