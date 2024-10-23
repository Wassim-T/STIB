package model.dto;

import javafx.util.Pair;

public class StopsDto extends Dto<Pair<Integer, Integer>> {

    private int idStation;


    public StopsDto(int line, int station, int idOrder) {
        super(new Pair<>(line, idOrder));
        this.idStation = station;
    }

    public int getLine() {
        return getKey().getKey();
    }

    public int getIdOrder() {
        return getKey().getValue();
    }

    public int getIdStation() {
        return idStation;
    }
}
