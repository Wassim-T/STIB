package model;

import java.util.HashSet;
import java.util.Set;

public class Graph {

    private Set<Node> nodes;


    public Graph(){
        this.nodes = new HashSet<>();
    }

    public Graph(Graph original) {
        this.nodes = new HashSet<>();

        // Copy each node
        for (Node node : original.nodes) {
            Node copiedNode = new Node(node);
            this.nodes.add(copiedNode);
        }
    }

    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public void setNodes(Set<Node> nodes) {

        this.nodes = nodes;
    }

    public Node getNode(int idStation){
        for (Node node : nodes){
            if (node.getId() == idStation){
                return node;
            }
        }
        return null;
    }

    public Node getNode(String stationName){
        for (Node node : nodes){
            if (node.getName().equals(stationName)){
                return node;
            }
        }
        return null;
    }
}
