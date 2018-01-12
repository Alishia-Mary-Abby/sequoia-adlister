//package com.codeup.adlister.dao;
//
//import com.codeup.adlister.models.Ad;
//import com.codeup.adlister.models.Category;
//import com.mysql.cj.api.mysqla.result.Resultset;
//import com.mysql.cj.jdbc.Driver;
//
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class MySQLCategoriesDao implements Categories {
//    private Connection connection;
//
//    public MySQLCategoriesDao(Config config) {
//        try {
//            DriverManager.registerDriver(new Driver());
//            connection = DriverManager.getConnection(
//                    config.getUrl(),
//                    config.getUser(),
//                    config.getPassword()
//            );
//        } catch (SQLException e) {
//            throw new RuntimeException("Error connecting to the database!", e);
//        }
//    }
//
//    @Override
//    public List<Category> getCategories() {
//        PreparedStatement stmt = null;
//        try {
//            stmt = connection.prepareStatement("SELECT * FROM categories");
//            ResultSet rs = stmt.executeQuery();
//            return createCategoriesFromResults(rs);
//        } catch (SQLException e) {
//            throw new RuntimeException("Error - unable to retrieve categories.", e);
//        }
//
//
//        public List<Category> getCategoriesForAd(long adId) {
//            String query = "SELECT * FROM categories JOIN ad_categories ON ad_categories.ads_category_id = categories.id JOIN ads ON ads.id = ads_categories.ads_id WHERE ad_categories.ads_id = ?";
//            try {
//                PreparedStatement stmt = connection.prepareStatement(query);
//                stmt.setLong(1, adId);
//                ResultSet rs = stmt.executeQuery();
//                return getCategoriesFromResults(rs);
//            } catch (SQLException e) {
//                throw new RuntimeException("Error - unable to get categories for ad", e);
//        }
//
//        private List<Category> createCategoriesFromResults(ResultSet rs) throws SQLException {
//        List<Category> categories = new ArrayList<>();
//        while (rs.next()) {
//            categories.add(extractCategory(rs));
//        }
//        return categories;
//        }
//    }
//        private List<Category> extractCategory(ResultSet rs) throws SQLException {
//        return new Category(
//                rs.getLong("id");
//                rs.getString("category");
//        );
//        }
//    }
//
//}

package com.codeup.adlister.dao;

import com.codeup.adlister.models.Category;
import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLCategoriesDao implements Categories {
    private Connection connection = null;

    public MySQLCategoriesDao(Config config) {
        try {
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection(
                    config.getUrl(),
                    config.getUser(),
                    config.getPassword()
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error - connecting to the database.", e);
        }
    }

    @Override
    public List<Category> getAllCategories() {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement("SELECT * FROM categories");
            ResultSet rs = stmt.executeQuery();
            return createCategoriesFromResults(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Error - retrieving categories.", e);
        }
    }

    public List<Category> getCategoriesForAd(long adId) {
        String query = "SELECT * FROM categories JOIN ad_categories ON ad_categories.ads_category_id = categories.id JOIN ads ON ads.id = ads_categories.ads_id WHERE ad_categories.ads_id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setLong(1, adId);
            ResultSet rs = stmt.executeQuery();
            return createCategoriesFromResults(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Error - retrieving categories for ad.", e);
        }
    }

    private List<Category> createCategoriesFromResults(ResultSet rs) throws SQLException {
        List<Category> categories = new ArrayList<>();
        while (rs.next()) {
            categories.add(extractCategory(rs));
        }
        return categories;
    }

    private Category extractCategory(ResultSet rs) throws SQLException {
        return new Category(
                rs.getLong("id"),
                rs.getString("category")
        );
    }
}