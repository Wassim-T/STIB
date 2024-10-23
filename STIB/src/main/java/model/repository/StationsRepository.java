package model.repository;

import model.dto.StationsDto;
import model.jdbc.StationsDao;
import model.exception.RepositoryException;

import java.util.List;

public class StationsRepository implements Repository<Integer, StationsDto> {


    private final StationsDao dao;


     public StationsRepository() throws RepositoryException{
         this.dao = StationsDao.getInstance();
     }

    @Override
    public Integer add(StationsDto item) throws RepositoryException {
        Integer key = item.getKey();
        if (contains(key)){
            dao.update(item);
            return key;
        } else {
            return dao.insert(item);
        }
    }

    @Override
    public void remove(Integer key) throws RepositoryException {
        dao.delete(key);
    }

    @Override
    public List<StationsDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }
    @Override
    public StationsDto get(Integer key) throws RepositoryException {
        return dao.select(key);
    }

    @Override
    public boolean contains(Integer key) throws RepositoryException {
        StationsDto dto = dao.select(key);
        return dto != null;
    }
}
