package model.repository;

import model.dto.LinesDto;
import model.jdbc.LinesDao;
import model.exception.RepositoryException;

import java.util.List;

public class LinesRepository implements Repository<Integer, LinesDto> {

    private final LinesDao dao;

    public LinesRepository() throws RepositoryException{
        this.dao = LinesDao.getInstance();
    }


    @Override
    public Integer add(LinesDto item) throws RepositoryException {
        Integer key = item.getKey();
        if (contains(key)){
            dao.update(item);
            return key;
        }
        else {
            return dao.insert(item);
        }
    }

    @Override
    public void remove(Integer key) throws RepositoryException {
        if (contains(key)){
            dao.delete(key);
        }
        else {
            throw new RepositoryException("Nothing to delete, the key doesn't exist in the table");
        }
    }

    @Override
    public List<LinesDto> getAll() throws RepositoryException {
        return dao.selectAll();
    }

    @Override
    public LinesDto get(Integer key) throws RepositoryException {
        return dao.select(key);
    }

    @Override
    public boolean contains(Integer key) throws RepositoryException {
        LinesDto dto = dao.select(key);
        return dto != null;
    }
}
