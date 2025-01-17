/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBContext;

import entity.Ship;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SAKURA
 */
public class ShipDAO {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    private String query;
    public ArrayList<Ship> getShip() {
        ArrayList<Ship> list = new ArrayList<>();
        try {
            query = "SELECT * from dbo.Ship";
            conn = DBcontext.open(); 
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Ship(rs.getInt("id"),rs.getString("CityName"),rs.getInt("ShipPrice")));
            }
        } catch (SQLException e) {
            Logger.getLogger(ShipDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        finally{
            DBcontext.close(conn, ps, rs);
        }
        return list;
    }
    public Ship getShip(int id) {
        Ship ship = null;
        try {
            query = "SELECT * from dbo.Ship where id=?";
            conn = DBcontext.open(); 
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                ship = new Ship(rs.getInt("id"),rs.getString("CityName"),rs.getInt("ShipPrice"));
            }
        } catch (SQLException e) {
            Logger.getLogger(ShipDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        finally{
            DBcontext.close(conn, ps, rs);
        }
        return ship;
    }
}
