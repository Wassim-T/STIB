package model.repository;

import model.dto.FavorisDto;
import model.exception.RepositoryException;
import model.jdbc.FavorisDao;

import java.util.List;

public class FavorisRepository implements Repository<Integer, FavorisDto> {

    private final FavorisDao dao;


    public FavorisRepository() throws RepositoryException{
        this.dao = FavorisDao.getInstance();
    }


    @Override
    public Integer add(FavorisDto item) throws RepositoryException {
        if (contains(item.getKey())){
            dao.update(item);
            return item.getKey();
        } else {
            return dao.insert(item);
        }
    }

    @Override
    public void remove(Integer key) throws RepositoryException {
        dao.delete(key);
    }

    @Override
    public List<FavorisDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public FavorisDto get(Integer key) throws RepositoryException {
        return dao.select(key);
    }

    @Override
    public boolean contains(Integer key) throws RepositoryException {
        return !(dao.select(key) == null);
    }
}
