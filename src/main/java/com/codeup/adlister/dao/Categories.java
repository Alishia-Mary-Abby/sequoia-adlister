package com.codeup.adlister.dao;

import com.codeup.adlister.models.Category;

import java.util.List;

public interface Categories {
    // get a list of all the categories from db
    List<Category> getAllCategories();

    // getCategoryForAd( ) by the ad id or ad object
    List<Category> getCategoriesForAd(long adId);

    // FINISHING CATEGORY WORK //
    //
    //
    // inserting category attached to new ad should go through ads DAO

    // crud operation on ads
    // category / ad

    //    List<String> useAdCategories(int findCategoryId, List<String> categoryIds);
    //    Category getCategoryId(int id);

}
