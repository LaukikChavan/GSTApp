package windows;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

public class DatabaseConnections {
	
	static Connection c = null;
	static Statement smt = null;
	
	public DatabaseConnections() {
		try {
			connectToDatabase();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void connectToDatabase() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		c = DriverManager.getConnection("jdbc:sqlite:Database.db");
		smt = c.createStatement();
		System.out.println("Database is Connected");
		createTable();
	}
	
	public static void createTable() throws SQLException {
		String sql = "CREATE TABLE IF NOT EXISTS MyCompany (\n"                
				+ "    id integer PRIMARY KEY,\n"
                + "    name text NOT NULL,\n"
                + "    comname text NOT NULL,\n"
                + "    address text NOT NULL,\n"
                + "    phonenumber text NOT NULL,\n"
                + "    gstnumber text NOT NULL,\n"
                + "    pannumber text NOT NULL,\n"
                + "    email text NOT NULL,\n"
                + "    signimg text NOT NULL,\n"
                + "    imgname text NOT NULL\n"
                + ");";
		
		if(smt.execute(sql)) {
			System.out.println("Table main is Created");
		} else {
			System.out.println("Table main is already is there");
		}
		
		sql = "CREATE TABLE IF NOT EXISTS Particulars (\n"                
				+ "    code integer PRIMARY KEY,\n"
                + "    description text NOT NULL,\n"
                + "    amount integer NOT NULL\n"
                + ");";
		
		if(smt.execute(sql)) {
			System.out.println("Table par is Created");
		} else {
			System.out.println("Table par is already is there");
		}
		
		sql = "CREATE TABLE IF NOT EXISTS companies (\n"                
				+ "    gstnumber text PRIMARY KEY,\n"
                + "    name text NOT NULL,\n"
                + "    address integer NOT NULL\n"
                + ");";
		
		if(smt.execute(sql)) {
			System.out.println("Table com is Created");
		} else {
			System.out.println("Table com is already is there");
		}
	}
	
	public void addToComTable(String gst, String name, String add) throws SQLException {
		try {
			String sql = "INSERT INTO companies(gstnumber,name,address) VALUES(?,?,?)";
	        PreparedStatement pstmt = c.prepareStatement(sql);
	        	pstmt.setString(1, gst);
	            pstmt.setString(2, name);
	            pstmt.setString(3, add);
	            pstmt.executeUpdate();
		} catch (Exception e) {
			String sql = "UPDATE companies SET name = ?,address = ? WHERE gstnumber = ?";
	        PreparedStatement pstmt = c.prepareStatement(sql);
	            pstmt.setString(1, name);
	            pstmt.setString(2, add);
	            pstmt.setString(3, gst);
	            pstmt.executeUpdate();
		}
	}
		
	public void addToMainTable(String name, String comName, String address, String gstNumber, String panNumber, String phoneNumber, String email, String signImage, String imgName) throws SQLException {
		try {
			String sql = "INSERT INTO MyCompany(id,name,comname,address,phonenumber,gstnumber,pannumber,email,signimg,imgname) VALUES(?,?,?,?,?,?,?,?,?,?)";
	        PreparedStatement pstmt = c.prepareStatement(sql);
	        	pstmt.setLong(1, 1);
	            pstmt.setString(2, name);
	            pstmt.setString(3, comName);
	            pstmt.setString(4, address);
	            pstmt.setString(5, phoneNumber);
	            pstmt.setString(6, gstNumber);
	            pstmt.setString(7, panNumber);
	            pstmt.setString(8, email);
	            pstmt.setString(9, signImage);
	            pstmt.setString(10, imgName);
	            pstmt.executeUpdate();
		} catch (Exception e) {
			String sql = "UPDATE MyCompany SET name = ?,comname = ?,address = ?,phonenumber = ?,gstnumber = ?,pannumber = ?,email = ?,signimg = ?,imgname = ? WHERE id = 1";
	        PreparedStatement pstmt = c.prepareStatement(sql);
	            pstmt.setString(1, name);
	            pstmt.setString(2, comName);
	            pstmt.setString(3, address);
	            pstmt.setString(4, phoneNumber);
	            pstmt.setString(5, gstNumber);
	            pstmt.setString(6, panNumber);
	            pstmt.setString(7, email);
	            pstmt.setString(8, signImage);
	            pstmt.setString(9, imgName);
	            pstmt.executeUpdate();
		}
	}
	
	public String[] getdataofMainTable() {
		 String sql = "SELECT * FROM MyCompany";
	        String[] data = new String[9];
	        try (Statement stmt  = c.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
	            
	        	System.out.println("In Get Data Act");
	        	
	            while (rs.next()) {
	                  data[0] = rs.getString("name");
	                  data[1] = rs.getString("comname");
	                  data[2] = rs.getString("address");
	                  data[3] = rs.getString("phonenumber");
	                  data[4] = rs.getString("gstnumber");
	                  data[5] = rs.getString("pannumber") ;
	                  data[6] = rs.getString("signimg");
	                  data[7] = rs.getString("email");
	                  data[8] = rs.getString("imgname");
	            }
	        } catch(Exception e) {
	        	e.printStackTrace();
	        }
	        
	        return data;
	}

	public void addToParTable(String code, String par, String amo) throws SQLException {
		try {
			String sql = "INSERT INTO Particulars(code,description,amount) VALUES(?,?,?)";
	        PreparedStatement pstmt = c.prepareStatement(sql);
	        	pstmt.setString(1, code);
	            pstmt.setString(2, par);
	            pstmt.setString(3, amo);
	            pstmt.executeUpdate();
		} catch (Exception e) {
			String sql = "UPDATE Particulars SET description = ?,amount = ? WHERE code = ?";
	        PreparedStatement pstmt = c.prepareStatement(sql);
	            pstmt.setString(1, par);
	            pstmt.setString(2, amo);
	            pstmt.setString(3, code);
	            pstmt.executeUpdate();
		}
	}
	
	public Vector<String> getdataofParti() {
		String sql = "SELECT * FROM Particulars";
        Vector<String> data = new Vector<String>();
        int len = 0;
        try (Statement stmt  = c.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
                String vec = new String();
                vec = rs.getString("code") + "#" +
                  rs.getString("description") + "#" +
                  rs.getString("amount");
                  data.add(vec);
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }
        return data;
	}

	public Vector<String> getdataofCom() {
		String sql = "SELECT * FROM companies";
        Vector<String> data = new Vector<String>();
        int len = 0;
        try (Statement stmt  = c.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
                String vec = new String();
                vec = rs.getString("gstnumber") + "#" +
                  rs.getString("name") + "#" +
                  rs.getString("address");
                  data.add(vec);
            }
        } catch(Exception e) {
        	e.printStackTrace();
        }
        return data;
	}
	
	public String[] getParComData(String co) {
		String[] p = new String[3];
		String sql = "SELECT * FROM companies Where gstnumber = '" + co + "';";
		try (	Statement stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery(sql) ) {
				p[0] = rs.getString(1);
				p[1] = rs.getString(2);
				p[2] = rs.getString(3);
		} catch (Exception e){
			e.printStackTrace();
		}
		return p;
	}
	
	public String[] getParParData(String co) {
		String[] p = new String[3];
		String sql = "SELECT * FROM Particulars Where code = " + co + ";";
		try (	Statement stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery(sql) ) {
				p[0] = rs.getString(1);
				p[1] = rs.getString(2);
				p[2] = rs.getString(3);
		} catch (Exception e){
			e.printStackTrace();
		}
		return p;
	}
}
