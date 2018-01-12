package com.codeup.adlister.dao;

import com.codeup.adlister.models.Ad;
import com.codeup.adlister.models.Category;

import java.util.List;

public interface Ads {
//  ---------display list of all the ads---------
    List<Ad> all();
//  ---------insert a new ad---------
    Long insert(Ad ad);

//  ---------View One Specific Ad---------
    Ad ViewAd (long id);

//  ---------Method to Update Ad---------
    void editAd(Ad ad);
    List<Ad> search(String searchAd);

    List<Ad> showAds(long id);

//    Object findAdsByCategory(Long categoryId);
    List<Ad> findAdsByCategory(Long id);







}
