package model;

import model.dto.LinesDto;
import model.dto.StationsDto;
import model.repository.LinesRepository;
import model.repository.StationsRepository;
import model.repository.StopsRepository;
import model.exception.RepositoryException;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

public class StationNetwork extends Thread{

    private Graph graph;
    private final StationsRepository stationsRepository;
    private final StopsRepository stopsRepository;
    private final LinesRepository linesRepository;
    private PropertyChangeSupport pcs;
    public static final String SEARCH = "search";
    private StationsDto source, destination;


    public StationNetwork() throws RepositoryException {
        this.stationsRepository = new StationsRepository();
        this.stopsRepository = new StopsRepository();
        this.linesRepository = new LinesRepository();
        graph = new Graph();
        pcs = new PropertyChangeSupport(this);
    }

    public void setSourceDestination(StationsDto source, StationsDto destination) {
        this.source = source;
        this.destination = destination;
    }

    @Override
    public void run() {
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }  ---> pour illustrer le freeze
        try {
            getShortestPath(source, destination);
        }catch (RepositoryException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    private void populateGraph() throws RepositoryException {
        for (StationsDto station : stationsRepository.getAll()) {
            Node node = new Node(station);
            graph.addNode(node);
        }
    }

    public List<StationsDto> getAllStations() throws RepositoryException {
        return stationsRepository.getAll();
    }

    private void addAdjacentsStations() throws RepositoryException {
        for (LinesDto line : linesRepository.getAll()) {
            var stops = stopsRepository.getAllStationsFromLine(line.getKey());

            for (int i = 0; i < stops.size(); i++) {
                Node current = graph.getNode(stops.get(i).getIdStation());
                current.addLine(line.getKey());
                Node before = null;
                Node after = null;
                if (i == 0) {
                    after = graph.getNode(stops.get(i + 1).getIdStation());

                } else if (i == stops.size() - 1) {
                    before = graph.getNode(stops.get(i - 1).getIdStation());

                } else {
                    before = graph.getNode(stops.get(i - 1).getIdStation());
                    after = graph.getNode(stops.get(i + 1).getIdStation());
                }

                if (after == null && before != null) {
                    current.addAdjacentNode(before);

                } else if (after != null && before == null) {
                    current.addAdjacentNode(after);

                } else {
                    current.addAdjacentNode(before);
                    current.addAdjacentNode(after);
                }
            }
        }
    }

    public void initialize() throws RepositoryException {
        populateGraph();
        addAdjacentsStations();
    }

    private Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node : unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private void CalculateMinimumDistance(Node evaluationNode, Integer edgeWeigh, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    public void calculateShortestPathFromSource(Node source) {
        source.setDistance(0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (!unsettledNodes.isEmpty()) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<Node, Integer> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    CalculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
    }

    public Node getNodeFromGraph(String stationName) {
        return graph.getNode(stationName);
    }



    public void getShortestPath(StationsDto source, StationsDto destination) throws RepositoryException {
        graph = new Graph();
        initialize();
        Node begin = getNodeFromGraph(source.getStationName());
        calculateShortestPathFromSource(begin);

        List<StationData> path = new ArrayList<>();
        List<Node> tmp = getNodeFromGraph(destination.getStationName()).getShortestPath();
        for (Node node : tmp) {
            path.add(new StationData(node.getName(), node.getLinesAvailable()));

        }
        Node finalNode = getNodeFromGraph(destination.getStationName());
        path.add(new StationData(finalNode.getName(), finalNode.getLinesAvailable()));
        pcs.firePropertyChange(SEARCH, null, path);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

}
