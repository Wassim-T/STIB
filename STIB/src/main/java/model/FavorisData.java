package model;

public class FavorisData {

    private String name;
    private String source;
    private String destination;

    public FavorisData(String name, String source, String destination) {
        this.name = name;
        this.source = source;
        this.destination = destination;
    }

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }
}
