package model.dto;

public class StationsDto extends Dto<Integer> {


    private String stationName;


    public StationsDto(int id,String stationName) {
        super(id);
        this.stationName = stationName;

    }


    public String getStationName(){
        return stationName;
    }

    @Override
    public String toString() {
        return stationName;
    }
}
