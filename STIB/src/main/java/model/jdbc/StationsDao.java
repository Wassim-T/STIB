package model.jdbc;

import model.dto.StationsDto;
import model.repository.Dao;
import model.exception.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StationsDao implements Dao<Integer, StationsDto> {

    private Connection connection;
    private static StationsDao INSTANCE;


    private StationsDao() throws RepositoryException{
        connection = DBManager.getInstance().getConnection();
    }

    @Override
    public Integer insert(StationsDto item) throws RepositoryException {
        if (item == null) {
            throw new RepositoryException("Aucune élément donné en paramètre");
        }
        String sql = "INSERT INTO STATIONS(id,name) values (? ,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, item.getKey());
            statement.setString(2,item.getStationName());

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
        String sql = "DELETE FROM STATIONS WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, key);
            statement.executeUpdate();
        } catch (SQLException e){
            throw new RepositoryException(e);
        }
    }

    @Override
    public void update(StationsDto item) throws RepositoryException {
        if (item == null){
            throw new RepositoryException("No item given in params");
        }
        String sql = "UPDATE STATIONS SET name=? where id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,item.getStationName());
            statement.setInt(2,item.getKey());
            statement.setInt(3, item.getKey());
            statement.executeUpdate();
        } catch (SQLException e){
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<StationsDto> selectAll() throws RepositoryException {
        String sql = "SELECT id, name FROM STATIONS";
        List<StationsDto> dtos = new ArrayList<>();
        try(Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
                StationsDto dto = new StationsDto(rs.getInt(1), rs.getString(2));
                dtos.add(dto);
            }
        }catch (SQLException e){
            throw new RepositoryException(e);
        }
        return dtos;
    }

    @Override
    public StationsDto select(Integer key) throws RepositoryException {
        if (key == null){
            throw new RepositoryException("No key given in params");
        }
        String sql = "SELECT * FROM STOPS WHERE id =?";
        StationsDto dto = null;
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,key);
            ResultSet rs = statement.executeQuery();

            int count = 0;
            while (rs.next()){
                dto = new StationsDto(rs.getInt(1),rs.getString(2));
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

    public static StationsDao getInstance() throws RepositoryException{
        if (INSTANCE == null){
            INSTANCE = new StationsDao();
        }
        return INSTANCE;
    }

}
