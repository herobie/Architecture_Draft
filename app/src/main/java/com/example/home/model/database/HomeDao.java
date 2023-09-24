package com.example.home.model.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.home.model.bean.HomeResult;

import java.util.List;

@Dao
public interface HomeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(HomeResult.Data.Datas...datas);

    @Query("DELETE FROM HomeDatas")
    void clearCache();//直接清空缓存数据，配合上面的添加达成重写效果

    @Query("SELECT * FROM HomeDatas LIMIT 15")
    List<HomeResult.Data.Datas> getCacheData();
}
