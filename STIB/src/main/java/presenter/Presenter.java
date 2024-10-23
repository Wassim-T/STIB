package presenter;

import model.StationData;
import model.StationNetwork;
import model.dto.FavorisDto;
import model.exception.RepositoryException;
import model.repository.FavorisRepository;
import view.ViewFavoris;
import view.ViewMain;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import static model.StationNetwork.SEARCH;

public class Presenter implements PropertyChangeListener {

    private StationNetwork stationNetwork;
    private ViewMain view;
    private ViewFavoris viewFavoris;
    private FavorisRepository favorisRepository;


    public Presenter(StationNetwork stationNetwork, ViewMain view, ViewFavoris viewFavoris) throws RepositoryException {
        this.stationNetwork = stationNetwork;
        this.view = view;
        this.viewFavoris = viewFavoris;
        favorisRepository = new FavorisRepository();
        stationNetwork.addPropertyChangeListener(this);
    }

    public void initialize() throws RepositoryException {
        stationNetwork.initialize();
        view.initialize(stationNetwork.getAllStations(), favorisRepository.getAll());
        view.handleRechercher(this);
        viewFavoris.handler(this);
        view.handleAjouter(this);
        view.handleSupprimer(this);
    }

    public void search() throws RepositoryException {
        view.clearTable();
        stationNetwork.setSourceDestination(view.originStation(), view.destinationStation());
        stationNetwork.start();
        //stationNetwork.getShortestPath(view.originStation(), view.destinationStation());
    }


    public void SaveFavoris() throws RepositoryException {
        String name = viewFavoris.getName();
        FavorisDto favorisDto = new FavorisDto(name, view.originStation(), view.destinationStation());
        Integer key = favorisRepository.add(favorisDto);
        System.out.println(key);
        favorisDto.setKey(key);
        view.addFavoris(favorisDto);
        viewFavoris.close();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(SEARCH)) {
            for (StationData stationData : ((List<StationData>) evt.getNewValue())) {
                view.addData(stationData);
            }
        }
    }

    public void handleAjouter() {
        viewFavoris.show();
    }

    public void supprimer() throws RepositoryException {
        System.out.println("aaa");
        favorisRepository.remove(view.favorisDto().getKey());
        System.out.println("bbb");
        List<FavorisDto> dtos = favorisRepository.getAll();
        view.ajouterFavoris(dtos);

    }
}

