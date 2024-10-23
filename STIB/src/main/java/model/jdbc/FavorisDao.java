package model.jdbc;

import model.dto.StationsDto;
import model.repository.Dao;
import model.dto.FavorisDto;

import model.exception.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FavorisDao implements Dao<Integer, FavorisDto> {


    private Connection connection;


    private static FavorisDao INSTANCE;

    private FavorisDao() throws RepositoryException {
        connection = DBManager.getInstance().getConnection();
    }

    public static FavorisDao getInstance() throws RepositoryException {
        if (INSTANCE == null) {
            INSTANCE = new FavorisDao();
        }
        return INSTANCE;
    }


    @Override
    public Integer insert(FavorisDto item) throws RepositoryException {
        if (item == null) {
            throw new RepositoryException("Aucune élément donné en paramètre");
        }
        String sql = "INSERT INTO FAVORIS(name,id_source,id_destination) values (?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, item.getName());
            statement.setInt(2, item.getSourceId());
            statement.setInt(3, item.getDestinationId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
                return rs.getInt(1);

        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
    }

    @Override
    public void delete(Integer key) throws RepositoryException {
        if (key == null) {
            throw new RuntimeException("No key given in params");
        }

        String sql = "DELETE FROM FAVORIS WHERE id_favoris = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, key);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }

    }


    @Override
    public void update(FavorisDto item) throws RepositoryException {
        if (item == null) {
            throw new RepositoryException("No item given in params");
        }
        String sql = "UPDATE FAVORIS SET name=?, id_source=?, id_destination=? where id_favoris=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, item.getName());
            statement.setInt(2, item.getSourceId());
            statement.setInt(3, item.getDestinationId());
            statement.setInt(4, item.getKey());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }

    }

    @Override
    public List<FavorisDto> selectAll() throws RepositoryException {
        String sql = " SELECT id_favoris, Favoris.name, id_source, s1.name ,id_destination,s2.name FROM FAVORIS LEFT JOIN STATIONS s1 ON FAVORIS.id_source = s1.id" +
                " JOIN STATIONS s2 ON FAVORIS.id_destination = s2.id ";

        List<FavorisDto> dtos = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                StationsDto source = new StationsDto(rs.getInt(3), rs.getString(4));
                StationsDto destination = new StationsDto(rs.getInt(5), rs.getString(6));
                FavorisDto dto = new FavorisDto(rs.getInt(1), rs.getString(2), source, destination);
                dtos.add(dto);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return dtos;
    }

    @Override
    public FavorisDto select(Integer key) throws RepositoryException {
        if (key == null) {
            return null;
        }
        String sql = " SELECT id_favoris, Favoris.name, id_source, s1.name ,id_destination,s2.name FROM FAVORIS LEFT JOIN STATIONS s1 ON FAVORIS.id_source = s1.id" +
                " JOIN STATIONS s2 ON FAVORIS.id_destination = s2.id WHERE id_favoris = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, key);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                StationsDto source = new StationsDto(rs.getInt(3), rs.getString(4));
                StationsDto destination = new StationsDto(rs.getInt(5), rs.getString(6));
                FavorisDto dto = new FavorisDto(rs.getInt(1), rs.getString(2), source, destination);
                return dto;
            }
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return null;
    }

    public static void main(String[] args) throws RepositoryException {
        FavorisDao test = FavorisDao.getInstance();
        StationsDao dao = StationsDao.getInstance();
        StationsDto source = dao.selectAll().get(0);
        StationsDto destination = dao.selectAll().get(1);
        test.insert(new FavorisDto(0, "test2", source, destination));
        var list = test.selectAll();
        for (FavorisDto item : list) {
            System.out.println(item.getKey() + " " + item.getName() + " " + item.getSourceName() + " " + item.getDestinationName());
        }
    }
}
