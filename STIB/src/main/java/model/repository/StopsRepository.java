package model.repository;

import javafx.util.Pair;
import model.dto.StopsDto;
import model.jdbc.StopsDao;
import model.exception.RepositoryException;

import java.util.List;

public class StopsRepository implements Repository<Pair<Integer, Integer>, StopsDto> {

    private final StopsDao dao;
   

    public StopsRepository() throws RepositoryException{
        this.dao = StopsDao.getInstance();
    }

    @Override
    public Pair<Integer,Integer> add(StopsDto item) throws RepositoryException {
        Pair<Integer,Integer> key = item.getKey();
        if (contains(key)){
            dao.update(item);
            return key;
        } else{
           return dao.insert(item);
        }
    }

    @Override
    public void remove(Pair<Integer,Integer> key) throws RepositoryException {
        dao.delete(key);
    }

    @Override
    public List<StopsDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public StopsDto get(Pair<Integer,Integer> key) throws RepositoryException {
        return dao.select(key);
    }

    @Override
    public boolean contains(Pair<Integer,Integer> key) throws RepositoryException {
        StopsDto dto = dao.select(key);
        return dto != null;
    }

    //jai raoujte sa
    public List<StopsDto> getAllStationsFromLine(Integer line) throws RepositoryException {
        return dao.selectAllStationsFromLine(line);
    }
}
