import java.io.Serializable;

class Node implements Serializable {
    public int id;
    public String name;

    public Node(int id, String name){
        this.id = id;
        this.name = name;
    }
}

