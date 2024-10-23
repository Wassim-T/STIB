package model.jdbc;

import model.dto.LinesDto;
import model.repository.Dao;
import model.exception.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LinesDao implements Dao<Integer, LinesDto> {


    private Connection connection;

    private static LinesDao INSTANCE;



    private LinesDao() throws RepositoryException{
        connection = DBManager.getInstance().getConnection();
    }

    @Override
    public Integer insert(LinesDto item) throws RepositoryException {
        if (item == null) {
            throw new RepositoryException("Aucune élément donné en paramètre");
        }
        String sql = "INSERT INTO LINES(id) values (?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, item.getKey());
            statement.executeUpdate();
        } catch (SQLException e){
            throw new RepositoryException(e);
        }

        return item.getKey();
    }

    @Override
    public void delete(Integer key) throws RepositoryException {
        if (key == null){
            throw new RuntimeException("No key given in params");
        }
        String sql = "DELETE FROM LINES WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, key);
            statement.executeUpdate();
        } catch (SQLException e){
            throw new RepositoryException(e);
        }
    }

    @Override
    public void update(LinesDto item) throws RepositoryException {
        // no update because only one id in the table
    }

    @Override
    public List<LinesDto> selectAll() throws RepositoryException {
        String sql = "SELECT * FROM LINES";
        List<LinesDto> dtos = new ArrayList<>();
        try(Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
                LinesDto dto = new LinesDto(rs.getInt(1));
                dtos.add(dto);
            }
        }catch (SQLException e){
            throw new RepositoryException(e);
        }
        return dtos;
    }

    @Override
    public LinesDto select(Integer key) throws RepositoryException {
        if (key == null){
            throw new RepositoryException("No key given in params");
        }
        String sql = "SELECT * FROM LINES WHERE id =?";
        LinesDto dto = null;
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,key);
            ResultSet rs = statement.executeQuery();

            int count = 0;
            while (rs.next()){
                dto = new LinesDto(rs.getInt(1));
                count++;
            }
            if (count > 1){
                throw new RepositoryException("Record is not unique " + key );
            }
        } catch (SQLException e){
            throw new RepositoryException(e);
        }
        return dto;
    }


    public static LinesDao getInstance() throws RepositoryException{
        if (INSTANCE == null){
            INSTANCE = new LinesDao();
        }
        return INSTANCE;
    }



}
