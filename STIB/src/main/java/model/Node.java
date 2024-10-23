package model;

import model.dto.StationsDto;

import java.util.*;

public class Node {

    private static final Integer STATION_DISTANCE = 1;
    private String name;
    private final int id;
    private List<Node> shortestPath;
    private Integer distance;
    private Map<Node, Integer> adjacentNodes;

    private List<Integer> linesAvailable;

    public Node(Node original) {
        this.name = original.name;
        this.id = original.id;
        this.shortestPath = new LinkedList<>(original.shortestPath);
        this.distance = original.distance;
        this.adjacentNodes = new HashMap<>(original.adjacentNodes);
        this.linesAvailable = new ArrayList<>(original.linesAvailable);
    }

    public Node(StationsDto dto) {
        this.name = dto.getStationName();
        this.id = dto.getKey();
        shortestPath = new LinkedList<>();
        distance = Integer.MAX_VALUE;
        adjacentNodes = new HashMap<>();
        linesAvailable = new ArrayList<>();
    }

    public void addDestination(Node destination, int distance) {
        adjacentNodes.put(destination, distance);
    }

    public String getName() {
        return name;
    }

    public Integer getDistance() {
        return distance;
    }

    public List<Node> getShortestPath() {
        return shortestPath;
    }

    public Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShortestPath(List<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public void addAdjacentNode(Node adjacentNode){
        adjacentNodes.put(adjacentNode,STATION_DISTANCE);
    }

    public void setAdjacentNodes(Map<Node, Integer> adjacentNodes) {
        this.adjacentNodes = adjacentNodes;
    }

    public List<Integer> getLinesAvailable() {
        return linesAvailable;
    }

    public void addLine(Integer line){
        if (!linesAvailable.contains(line)){
            linesAvailable.add(line);
        }
    }

    public int getId() {
        return id;
    }
}

