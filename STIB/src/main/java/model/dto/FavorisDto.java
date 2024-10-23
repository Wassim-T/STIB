package model.dto;


public class FavorisDto extends Dto<Integer> {

    private String name;

    private StationsDto source;

    private StationsDto destination;


    public FavorisDto(Integer id, String name, StationsDto source, StationsDto destination) {
        super(id);
        this.name = name;
        this.source = source;
        this.destination = destination;
    }


    public FavorisDto(String name, StationsDto source, StationsDto destination) {
        super();
        this.name = name;
        this.source = source;
        this.destination = destination;
    }


    public String getName() {
        return name;
    }

    public String getSourceName() {
        return source.getStationName();
    }

    public String getDestinationName() {
        return destination.getStationName();
    }

    public int getSourceId() {
        return source.getKey();
    }

    public void setKey(Integer key){
        super.setKey(key);
    }

    public int getDestinationId() {
        return destination.getKey();
    }

    @Override
    public String toString() {
        return name;
    }
}
