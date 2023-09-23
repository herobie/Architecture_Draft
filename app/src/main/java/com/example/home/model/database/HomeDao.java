package com.example.home.model.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.home.model.bean.HomeResult;

import java.util.List;

@Dao
public interface HomeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(HomeResult.Data.Datas...datas);

    @Query("SELECT * FROM HomeDatas LIMIT 20")
    List<HomeResult.Data.Datas> getCacheData();
}
