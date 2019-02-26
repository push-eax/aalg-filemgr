import java.io.*;
import java.util.*;

class NodeCollection {
    // nodes stored here
    private ArrayList<Node> nodes;
    // handle to the file which stores the serialized ArrayList
    public File file;
    // stores the runtime of the last run sort or search function
    public double runtime;

    // constructor
    public NodeCollection(String file) { 
        this.nodes = new ArrayList<Node>();
        this.file = new File(file);
        this.runtime = 0;
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
        Node localnode = this.searchSlow(id);
 
        this.nodes.remove(localnode);

        return localnode;
    }

    // sorting and searching methods
    // these return performance in seconds, to nanosecond precision
    // there is no greater precision available in Java

    // linear search
    public Node searchSlow(int id) {
        // start the timer
        long startTime = System.nanoTime();
        
        for (int i = 0; i < this.getSize(); i++) {
            if (this.nodes.get(i).id == id) {
                // stop the timer
                long endTime = System.nanoTime();
                // set runtime to elapsed
                this.runtime = (double)(endTime - startTime) / 1e9;
                return this.nodes.get(i);
            }
        }

        long endTime = System.nanoTime();
        this.runtime = (double)(endTime - startTime) / 1e9;
        return null;
    }

    // fast search
    public Node searchFast(int id) {
        long startTime = System.nanoTime();
        
        // sort before we search
        this.sortFast();
        Node[] nodeArray = this.nodes.toArray(new Node[this.getSize()]);

        int index = this.binarySearch(nodeArray, 0, this.getSize() - 1, id);

        long endTime = System.nanoTime();
        this.runtime = (double)(endTime - startTime) / 1e9; 
       
        Node retNode;

        try {
            retNode = this.nodes.get(index);
        } catch (Exception e) {
            return null;
        }
        return retNode;
    }

    private int binarySearch(Node arr[], int l, int r, int x) {
        if (r >= l) {
            int mid = l + (r - l) / 2;

            if (arr[mid].id == x) {
                return mid;
            }

            if (arr[mid].id > x) {
                return this.binarySearch(arr, l, mid - 1, x);
            }

            return this.binarySearch(arr, mid + 1, r, x);
        }

        return -1;
    }


    // bubble sort
    public void sortSlow() {
        long startTime = System.nanoTime();

        int n = this.getSize();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                    if (nodes.get(j).id > nodes.get(j + 1).id) {
                        Collections.swap(nodes, j, j + 1);
                    }
            }
        }

        long endTime = System.nanoTime();
        this.runtime = (double)(endTime - startTime) / 1e9;
    }

    // mergesort
    public void sortFast() {
        long startTime = System.nanoTime();
        
        this.divide(0, this.getSize() - 1);

        long endTime = System.nanoTime();
        this.runtime = (double)(endTime - startTime) / 1e9;
    }

    // private method for mergesort
    private void divide(int startIndex, int endIndex) {

        if (startIndex < endIndex && (endIndex - startIndex) >= 1) {
            int mid = (endIndex + startIndex) / 2;
            this.divide(startIndex, mid);
            this.divide(mid + 1, endIndex);

            this.merger(startIndex, mid, endIndex);
        }
    }
    
    // private method for mergesort
    private void merger(int startIndex, int midIndex, int endIndex) {
        ArrayList<Node> mergedSortedArray = new ArrayList<Node>();

        int leftIndex = startIndex;
        int rightIndex = midIndex + 1;

        while (leftIndex <= midIndex && rightIndex <= endIndex) {
            if (this.nodes.get(leftIndex).id <= this.nodes.get(rightIndex).id) {
                mergedSortedArray.add(this.nodes.get(leftIndex));
                leftIndex++;
            } else {
                mergedSortedArray.add(this.nodes.get(rightIndex));
                rightIndex++;
            }
        }

        while (leftIndex <= midIndex) {
            mergedSortedArray.add(this.nodes.get(leftIndex));
            leftIndex++;
        }

        while (rightIndex <= endIndex) {
            mergedSortedArray.add(this.nodes.get(rightIndex));
            rightIndex++;
        }

        int i = 0;
        int j = startIndex;
        while(i < mergedSortedArray.size()){
            this.nodes.set(j, mergedSortedArray.get(i++));
            j++;
        }
    }

    // get the size of the ArrayList. the ArrayList is private, so we need to use a getter
    public int getSize() {
        return nodes.size();
    }
}


