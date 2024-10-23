package model;

import java.util.List;

public class StationData {

    private String stationName;

    private List<Integer> stationLines;

    public StationData(String stationName, List<Integer> stationLines) {
        this.stationName = stationName;
        this.stationLines = stationLines;
    }

    public String getStationName() {
        return stationName;
    }

    public List<Integer> getStationLines() {
        return stationLines;
    }
}
