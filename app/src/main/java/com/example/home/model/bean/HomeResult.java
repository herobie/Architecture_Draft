package com.example.home.model.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeResult {
    @Expose
    @SerializedName("data")
    private Data data;
    @Expose
    private int curPage;
    public static class Data {
        @Expose
        private List<Datas> datas;

        @Entity(tableName = "HomeDatas")
        public static class Datas{
            @PrimaryKey(autoGenerate = true)
            private long id;

            @Expose
            @ColumnInfo
            private String title;

            @Expose
            @ColumnInfo
            private String envelopePic;

            @Expose
            @ColumnInfo
            private String projectLink;

            @Expose
            @ColumnInfo
            private String author;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public long getId() {
                return id;
            }

            public String getEnvelopePic() {
                return envelopePic;
            }

            public String getProjectLink() {
                return projectLink;
            }

            public String getAuthor() {
                return author;
            }

            public void setId(long id) {
                this.id = id;
            }

            public void setEnvelopePic(String envelopePic) {
                this.envelopePic = envelopePic;
            }

            public void setProjectLink(String projectLink) {
                this.projectLink = projectLink;
            }

            public void setAuthor(String author) {
                this.author = author;
            }
        }

        public List<Datas> getDatas() {
            return datas;
        }
    }

    public Data getProjectData() {
        return data;
    }

    public void setProjectData(Data data) {
        this.data = data;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }
}
