package com.example.uniplanner.persistence;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface Access {

    @Query("SELECT COUNT(*) FROM selections WHERE id = :id")
    int count(long id);

    @Query("SELECT COUNT(*) FROM selections WHERE id = :id")
    LiveData<Integer> countAsync(long id);

    @Query("SELECT COUNT(*) FROM selections WHERE id = :id AND type = :type")
    int countByType(long id, String type);

    @Query("SELECT COUNT(*) FROM SELECTIONS WHERE id = :id AND type = :type")
    LiveData<Integer> countByTypeAsync(long id, String type);

    @Delete
    void delete(CourseSelection... selections);

    @Query("SELECT * FROM selections WHERE id = :id AND year = :year AND term = :term AND choice = :choice")
    CourseSelection getSelection(long id, int year, int term, int choice);

    @Query("SELECT * FROM selections WHERE id = :id AND year = :year AND term = :term AND choice = :choice")
    LiveData<CourseSelection> getSelectionAsync(long id, int year, int term, int choice);

    @Query("SELECT * FROM selections WHERE id = :id")
    List<CourseSelection> getSelections(long id);

    @Query("SELECT * FROM selections WHERE id = :id")
    LiveData<List<CourseSelection>> getSelectionsAsync(long id);

    @Query("SELECT EXISTS(SELECT * FROM selections WHERE id = :id AND year = :year AND term = :term AND choice = :choice)")
    boolean hasSelected(long id, int year, int term, int choice);

    @Query("SELECT EXISTS(SELECT * FROM selections WHERE id = :id AND year = :year AND term = :term AND choice = :choice)")
    LiveData<Boolean> hasSelectedAsync(long id, int year, int term, int choice);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CourseSelection... selections);

    @Query("SELECT EXISTS(SELECT * FROM selections WHERE id = :id AND course = :course)")
    boolean isTaking(long id, String course);

    @Query("SELECT EXISTS(SELECT * FROM selections WHERE id = :id AND course = :course)")
    LiveData<Boolean> isTakingAsync(long id, String course);
}