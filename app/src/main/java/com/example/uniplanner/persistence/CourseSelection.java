package com.example.uniplanner.persistence;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "selections", primaryKeys = { "id", "position" })
public class CourseSelection {

    @ColumnInfo(typeAffinity = ColumnInfo.INTEGER)
    public long id;

    @ColumnInfo(typeAffinity = ColumnInfo.INTEGER)
    public int position;

    @ColumnInfo(typeAffinity = ColumnInfo.INTEGER)
    public int year;

    @ColumnInfo(typeAffinity = ColumnInfo.INTEGER)
    public int term;

    @ColumnInfo(typeAffinity = ColumnInfo.INTEGER)
    public int choice;

    @ColumnInfo(typeAffinity = ColumnInfo.TEXT)
    public String course;

    @ColumnInfo(typeAffinity = ColumnInfo.TEXT)
    public String type;

    public CourseSelection(long id, int year, int term, int choice, String course, String type) {
        this.id = id;
        this.position = toAbsolutePosition(year, term, choice);
        this.year = year;
        this.term = term;
        this.choice = choice;
        this.course = course;
        this.type = type;
    }

    public static int toAbsolutePosition(int year, int term, int choice) {
        return (int) ((year - 1) * Math.pow(3, 2) + (term - 1) * Math.pow(3, 1) + (choice - 1) * Math.pow(3, 0));
    }
}