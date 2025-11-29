package com.todo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public int addUser(String email) {
        try (Connection con = DBConnection.getConnection()) {
            // check if user exists
            String checkQuery = "SELECT id FROM users WHERE email=?";
            PreparedStatement psCheck = con.prepareStatement(checkQuery);
            psCheck.setString(1, email);
            ResultSet rs = psCheck.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }

            // insert new user
            String query = "INSERT INTO users(email) VALUES(?)";
            PreparedStatement ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, email);
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // error
    }
}
