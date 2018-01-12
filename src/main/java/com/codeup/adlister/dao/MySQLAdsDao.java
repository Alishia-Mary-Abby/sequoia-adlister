package com.codeup.adlister.dao;

import com.codeup.adlister.models.Ad;
import com.codeup.adlister.models.Category;
import com.codeup.adlister.models.User;
import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLAdsDao implements Ads {
    private Connection connection = null;

    public MySQLAdsDao(Config config) {
        try {
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection(
                    config.getUrl(),
                    config.getUser(),
                    config.getPassword()
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error - connecting to the database!", e);
        }
    }

    @Override
    public List<Ad> all() {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("SELECT * FROM ads JOIN categories ON ads.category_id = categories.id ");
            ResultSet rs = stmt.executeQuery();
            return createAdsWithUsersCategories(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Error - retrieving all ads.", e);
        }
    }

    @Override
    public Long insert(Ad ad) {
        try {
            String insertQuery = "INSERT INTO ads(user_id, title, imgName, description, price) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, ad.getUserId());
            stmt.setString(2, ad.getTitle());
            stmt.setString(3, ad.getImgName());
            stmt.setString(4, ad.getDescription());
            stmt.setLong(5, ad.getPrice());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException("Error - creating a new ad.", e);
        }
    }
//          -----Method to View One Specific Ad----
    @Override
    public Ad ViewAd(long id) {
        String query = "SELECT * FROM ads WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
//            ----to move onto the next line in table---
            rs.next();
//            ---Returning extract ad method----
            return extractAd(rs);

        } catch (SQLException e) {
            throw new RuntimeException("Error, Ad does not exist", e);
        }
    }


//          ---------Method to Update Ad---------
    @Override
//    public void editAd(String title, String description, Long price, long id) {
    public void editAd(Ad ad) {
        String query = "UPDATE ads SET title = ?, description = ?, price = ? WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, ad.getTitle());
            stmt.setString(2, ad.getDescription());
            stmt.setLong(3, ad.getPrice());
            stmt.setLong(4, ad.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error, unable to edit your ad.", e);
        }
    }


    private Ad extractAd(ResultSet rs) throws SQLException {
        return new Ad(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getString("imgName"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getInt("price")
        );
    }

    private Ad extractAds(ResultSet rs) throws SQLException {
        return new Ad(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getString("imgName"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getLong("price"),
        new User(
                rs.getLong("id"),
                rs.getString("username"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("bio"),
                rs.getString("location")
        )
                );
    }

    private Ad extractAdsWithUserCategories(ResultSet rs) throws SQLException {
        return new Ad(
                rs.getLong("id"),
                new User(
                        rs.getLong("user_id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("bio"),
                        rs.getString("location")),

                rs.getString("imgName"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getLong("price")
        );
    }

    private List<Ad> createAdsFromResults(ResultSet rs) throws SQLException {
        List<Ad> ads = new ArrayList<>();
        while (rs.next()) {
            ads.add(extractAd(rs));
        }
        return ads;
    }

    private List<Ad> createAdsWithUsersFromResults(ResultSet rs) throws SQLException {
        List<Ad> ads = new ArrayList<>();
        while (rs.next()) {
            ads.add(extractAds(rs));
        }
        return ads;
    }

    private List<Ad> createAdsWithUsersCategories(ResultSet rs) throws SQLException {
        List<Ad> ads = new ArrayList<>();
        while (rs.next()) {
            ads.add(extractAdsWithUserCategories(rs));
        }
        return ads;
    }

    @Override
    public List<Ad> search(String searchTerm) {
        String query = "SELECT * FROM ads JOIN users ON ads.user_id = users.id WHERE ads.title LIKE ? OR ads.description LIKE ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, "%" + searchTerm + "%");
            stmt.setString(2, "%" + searchTerm + "%");

            ResultSet rs = stmt.executeQuery();
            return createAdsWithUsersFromResults(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Ad findById(Long id) {
        String query = "SELECT * FROM ads JOIN users ON ads.user_id = users.id JOIN categories ON ads.category_id = categories.id WHERE ads.id = ?";
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement(query);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return extractAdsWithUserCategories(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Ad> findAdsByCategory(Long id) {
        String query = "SELECT * FROM ads JOIN categories ON ads.category_id = categories.id JOIN users ON ads.user_id = users.id WHERE categories.id = ?";
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement(query);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return createAdsWithUsersCategories(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Ad> showUserAds(Long id) {
        String query = "SELECT * FROM ads JOIN categories ON ads.category_id = categories.id JOIN users ON ads.user_id = users.id WHERE users.id = ?";
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement(query);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            return createAdsWithUsersCategories(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Error - finding users' ads.", e);

        }
    }

//    @Override
//    public Boolean delete(Long id) {
//        String query = "DELETE FROM ads WHERE id = ?";
//        PreparedStatement stmt;
//        try {
//            stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
//            stmt.setLong(1, id);
//            stmt.executeUpdate();
//            return true;
//
//        } catch (SQLException e) {
////            throw new RuntimeException("Error deleting the ad.", e);
//            return false;
//        }
//    }

//    @Override
//    public void update(Ad ad) {
//        String query = "UPDATE ads SET imgname = ?, title = ?, description = ?, category_id = ? WHERE id = ?";
//        PreparedStatement stmt;
//        try {
//            stmt = connection.prepareStatement(query);
//            stmt.setString(1, ad.getImgName());
//            stmt.setString(1, ad.getTitle());
//            stmt.setString(2, ad.getDescription());
//            stmt.setLong(3, ad.getCategoryId());
//            stmt.setLong(4, ad.getId());
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException("Error updating the ad.", e);
//        }
//    }


    //          -----Show Users Ad-----
    @Override
    public List<Ad> showAds(long id) {
        String query = "SELECT * FROM ads WHERE ads.user_id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            return createAdsFromResults(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Error, unable to view user ad", e);
        }
    }
}