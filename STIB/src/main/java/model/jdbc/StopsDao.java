package model.jdbc;

import javafx.util.Pair;
import model.dto.StopsDto;
import model.repository.Dao;
import model.exception.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StopsDao implements Dao<Pair<Integer,Integer>, StopsDto> {

    private Connection connection;


    private static StopsDao INSTANCE;

    private StopsDao() throws RepositoryException {
        connection = DBManager.getInstance().getConnection();
    }




    @Override
    public Pair<Integer,Integer> insert(StopsDto item) throws RepositoryException {
        if (item == null) {
            throw new RepositoryException("Aucune élément donné en paramètre");
        }
        String sql = "INSERT INTO STOPS(id_line,id_station,id_order) values (? , ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, item.getLine());
            statement.setInt(2,item.getIdStation());
            statement.setInt(3, item.getIdOrder());

            statement.executeUpdate();
        } catch (SQLException e){
            throw new RepositoryException(e);
        }

        return item.getKey();
    }

    @Override
    public void delete(Pair<Integer,Integer> key) throws RepositoryException {
            if (key == null){
                throw new RuntimeException("No key given in params");
            }
            String sql = "DELETE FROM STOPS WHERE id_order = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)){
                statement.setInt(1, key.getValue());
                statement.executeUpdate();
            } catch (SQLException e){
               throw new RepositoryException(e);
            }
    }

    @Override
    public void update(StopsDto item) throws RepositoryException {
        if (item == null){
            throw new RepositoryException("No item given in params");
        }
        String sql = "UPDATE STOPS SET id_line=? ,id_station=? where id_order=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,item.getLine());
            statement.setInt(2,item.getIdStation());
            statement.setInt(3, item.getIdOrder());
            statement.executeUpdate();
        } catch (SQLException e){
            throw new RepositoryException(e);
        }
    }

    @Override
    public List<StopsDto> selectAll() throws RepositoryException {
        String sql = "SELECT * FROM STOPS";
        List<StopsDto> dtos = new ArrayList<>();
        try(Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()){
                StopsDto dto = new StopsDto(rs.getInt(1), rs.getInt(2), rs.getInt(3));
                dtos.add(dto);
            }
        }catch (SQLException e){
            throw new RepositoryException(e);
        }
        return dtos;
    }

    @Override
    public StopsDto select(Pair<Integer,Integer> key) throws RepositoryException {
        if (key == null){
            throw new RepositoryException("No key given in params");
        }
        String sql = "SELECT * FROM STOPS WHERE id_order =?";
        StopsDto dto = null;
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,key.getValue());
            ResultSet rs = statement.executeQuery();

            int count = 0;
            while (rs.next()){
                dto = new StopsDto(rs.getInt(1),rs.getInt(2),rs.getInt(3));
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


   public static StopsDao getInstance() throws RepositoryException{
        if (INSTANCE == null){
            INSTANCE = new StopsDao();
        }
        return INSTANCE;
   }

//jai rajouté sa
    public List<StopsDto> selectAllStationsFromLine(Integer line) throws RepositoryException {
        String sql = "SELECT * FROM STOPS WHERE id_line = ? ORDER BY id_order";
        List<StopsDto> dtos = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, line);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                StopsDto dto = new StopsDto(rs.getInt(1), rs.getInt(2), rs.getInt(3));
                dtos.add(dto);
            }
        }catch (SQLException e){
            throw new RepositoryException(e);
        }
        return dtos;
    }
}
