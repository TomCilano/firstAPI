package com.ironyard.Services;

import com.ironyard.data.LineItems;
import com.ironyard.data.Total;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 * Created by Tom on 9/29/16.
 * CREATE TABLE lineitem
 (
 lin_description VARCHAR(155),
 lin_category VARCHAR(155),
 lin_budgetedamount NUMERIC,
 lin_actualamount NUMERIC,
 lin_id INTEGER PRIMARY KEY NOT NULL
 );
 */

public class LineService
{
    /**
     * Get the movies out of thw database and assigns them to the object models
     * in the CLass LineItems
     * @return
     * @throws SQLException
     */
    public List<LineItems> getAllLineItems() throws SQLException
    {
        LineItems found = null;
        List<LineItems> allOfThem = new ArrayList<LineItems>();
        DbService myDba = new DbService();
        Connection conn =  myDba.getConnection();
        PreparedStatement stmt = conn.prepareCall("SELECT * FROM budget.lineitem");
        ResultSet rs = stmt.executeQuery();
        while (rs.next())
        {
            found = new LineItems();
            found.setDescription(rs.getString("lin_description"));
            found.setCategory(rs.getString("lin_category"));
            found.setBudgetedAmount(rs.getDouble("lin_budgetedamount"));
            found.setTotalAmount(rs.getDouble("lin_actualamount"));
            found.setId(rs.getInt("lin_id"));
            allOfThem.add(found);
        }
        conn.close();
        return allOfThem;
    }

    /**
     *get the sum of the budgeted amount and total amounts in the DB and assigns them to the
     * data objects in the Class Total
     * @return
     * @throws SQLException
     */
    public List<Total> getTotals() throws SQLException
    {
        Total found = null;
        List<Total> allTotals = new ArrayList<Total>();
        DbService myDba = new DbService();
        Connection conn =  myDba.getConnection();
        PreparedStatement stmt = conn.prepareCall("SELECT lin_category, sum(lin_budgetedamount) AS totalBudget, sum(lin_budgetedamount) as actualtotal from budget.lineitem group by lin_category");
        ResultSet rs = stmt.executeQuery();
        while (rs.next())
        {
            found = new Total();
            found.setCategory(rs.getString("lin_category"));
            found.setBudgetTotal(rs.getDouble("totalBudget"));
            found.setActualTotal(rs.getDouble("actualtotal"));
            allTotals.add(found);
        }
        conn.close();
        return allTotals;
    }

    /**
     *
     * @param
     * @return
     * @throws SQLException
     */
    public List<LineItems> search (String search) throws SQLException
    {
        List<LineItems> found = new ArrayList<LineItems>();
        DbService myDb = new DbService();
        Connection con = null;

        try
        {
            con = myDb.getConnection();
            search = search + "%";
            PreparedStatement ps = con.prepareStatement("select * from budget.lineitem WHERE (lin_category ILIKE ?) OR (lin_description ILIKE ?);");
            ps.setString(1, search);
            ps.setString(2, search);
            ResultSet rs = ps.executeQuery();
            found = convertResultsToList(rs);
        } catch (Exception error)
        {
            error.printStackTrace();
            con.rollback();
        } finally {
            con.close();
        }
        return found;
    }

    /**
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private List<LineItems> convertResultsToList(ResultSet rs) throws SQLException
    {
        List<LineItems> found = new ArrayList<LineItems>();
        while (rs.next())
        {
            LineItems x = new LineItems();
            x.setDescription(rs.getString("lin_description"));
            x.setCategory(rs.getString("lin_category"));
            x.setBudgetedAmount(rs.getDouble("lin_budgetedamount"));
            x.setTotalAmount(rs.getDouble("lin_actualamount"));
            x.setId(rs.getInt("lin_id"));
            found.add(x);

        }
        return found;
    }

    /**
     * creates a budget object in the DB, VALUES (nextval('budget.lineitem_SEQ' creates a new ID for the entry
     * @param myLine
     * @throws SQLException
     */
    public void save (LineItems myLine) throws SQLException
    {
        DbService myDb = new DbService();
        Connection conn = null;
        try
        {
            conn = myDb.getConnection();
            PreparedStatement stmt = conn.prepareCall("INSERT INTO  budget.LINEITEM (lin_id, lin_description, lin_category, lin_budgetedamount, lin_actualamount)" +
                    " VALUES (nextval('budget.lineitem_SEQ'),?,?,?,?) ");
            stmt.setString(1, myLine.getDescription());
            stmt.setString(2, myLine.getCategory());
            stmt.setDouble(3, myLine.getBudgetedAmount());
            stmt.setDouble(4, myLine.getTotalAmount());
            stmt.executeUpdate();
        }
        catch (Exception e){

            e.printStackTrace();
            conn.rollback();
        }
        finally {
            conn.close();
        }

    }
    public void delete (int id) throws SQLException{
        DbService myDb = new DbService();
        Connection conn = null;

        try {
            conn = myDb.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE from budget.lineitem WHERE lin_id =?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
            conn.rollback();
        }
        finally {
            conn.close();
        }
    }

    public void update (LineItems aLineItem) throws SQLException {
        DbService myDb = new DbService();
        Connection conn = null;

        try {
            conn = myDb.getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE budget.lineitem SET lin_description = ?, lin_category =?, lin_budgetedamount=?, lin_actualamount=? WHERE lin_id=?; ");
            stmt.setString(1, aLineItem.getDescription());
            stmt.setString(2, aLineItem.getCategory());
            stmt.setDouble(3, aLineItem.getBudgetedAmount());
            stmt.setDouble(4, aLineItem.getTotalAmount());
            stmt.setInt(5, aLineItem.getId());
            stmt.executeUpdate();

        } catch (Exception e) {
            conn.rollback();
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }
    public LineItems getItemById(int idParse) throws SQLException
    {
        DbService myDB = new DbService();
        Connection conn = null;
        LineItems foundId = null;

        try {
            conn = myDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM budget.lineitem WHERE lin_id =?");
            stmt.setInt(1, idParse);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                foundId = new LineItems();
                foundId.setDescription(rs.getString("lin_description"));
                foundId.setCategory(rs.getString("lin_category"));
                foundId.setBudgetedAmount(rs.getDouble("lin_budgetedamount"));
                foundId.setTotalAmount(rs.getDouble("lin_actualamount"));
                foundId.setId(rs.getInt("lin_id"));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            conn.rollback();
            throw  e;
        }
        finally {
            conn.close();
        }
        return foundId;
    }


}


