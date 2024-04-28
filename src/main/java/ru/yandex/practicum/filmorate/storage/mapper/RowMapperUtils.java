package ru.yandex.practicum.filmorate.storage.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RowMapperUtils {
    public static boolean isColumnExist(ResultSet rs, String columnName) throws SQLException {
        for (int i = 1; i < rs.getMetaData().getColumnCount(); i++) {
            if (columnName.equals(rs.getMetaData().getColumnName(i))) {
                return true;
            }
        }
        return false;
    }
}
