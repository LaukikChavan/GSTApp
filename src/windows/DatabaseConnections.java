package windows;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.Date;

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
                + "    email text NOT NULL\n"
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
		
		sql = "CREATE TABLE IF NOT EXISTS Settings (\n"                
				+ "    id integer PRIMARY KEY,\n"
                + "    TermsNCon text NOT NULL,\n"
                + "    GstPer text NOT NULL,\n"
                + "    RefNo text NOT NULL,\n"
                + "    RefDate text NOT NULL\n"
                + ");";
		
		if(smt.execute(sql)) {
			System.out.println("Table par is Created");
		} else {
			System.out.println("Table par is already is there");
		}
		
		sql = "CREATE TABLE IF NOT EXISTS excelFile (\n"                
				+ "    id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "    CompanyName text NOT NULL,\n"
                + "    GstNumber text NOT NULL,\n"
                + "    RefNo text NOT NULL,\n"
                + "    Date text NOT NULL,\n"
                + "    totalBill text NOT NULL\n"
                + ");";
		
		if(smt.execute(sql)) {
			System.out.println("Table par is Created");
		} else {
			System.out.println("Table par is already is there");
		}
	}
	
	public void addToexcelFile(String comName, String gstNumber, String refNo, String date, String bill) throws SQLException {
		try {
			String sql = "INSERT INTO excelFile(CompanyName,GstNumber,RefNo,Date,totalBill) VALUES(?,?,?,?,?)";
	        PreparedStatement pstmt = c.prepareStatement(sql);
	            pstmt.setString(1, comName);
	            pstmt.setString(2, gstNumber);
	            pstmt.setString(3, refNo);
	            pstmt.setString(4, date);
	            pstmt.setString(5, bill);
	            pstmt.executeUpdate();
	            
	        System.out.println("Data is Added to excel file");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet getdataforexcelFile() throws SQLException {
		 String sql = "SELECT * FROM excelFile";
		 ResultSet rs;
	     Statement stmt  = c.createStatement();
	     rs = stmt.executeQuery(sql);       
	     System.out.println("In Get Data Act");        
	     return rs;
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
	
	
	public void addToMainTable(String name, String comName, String address, String gstNumber, String panNumber, String phoneNumber, String email) throws SQLException {
		try {
			String sql = "INSERT INTO MyCompany(id,name,comname,address,phonenumber,gstnumber,pannumber,email) VALUES(?,?,?,?,?,?,?,?)";
	        PreparedStatement pstmt = c.prepareStatement(sql);
	        	pstmt.setLong(1, 1);
	            pstmt.setString(2, name);
	            pstmt.setString(3, comName);
	            pstmt.setString(4, address);
	            pstmt.setString(5, phoneNumber);
	            pstmt.setString(6, gstNumber);
	            pstmt.setString(7, panNumber);
	            pstmt.setString(8, email);
	            pstmt.executeUpdate();
		} catch (Exception e) {
			String sql = "UPDATE MyCompany SET name = ?,comname = ?,address = ?,phonenumber = ?,gstnumber = ?,pannumber = ?,email = ? WHERE id = 1";
	        PreparedStatement pstmt = c.prepareStatement(sql);
	            pstmt.setString(1, name);
	            pstmt.setString(2, comName);
	            pstmt.setString(3, address);
	            pstmt.setString(4, phoneNumber);
	            pstmt.setString(5, gstNumber);
	            pstmt.setString(6, panNumber);
	            pstmt.setString(7, email);
	            pstmt.executeUpdate();
		}
	}
	
	public String[] getdataofMainTable() {
		 String sql = "SELECT * FROM MyCompany";
	        String[] data = new String[7];
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
	                  data[6] = rs.getString("email");
	            }
	            
	            if(data[0] == null) {
	            	data[0] = "Enter Name";
	            }
	            if(data[1] == null) {
	            	data[1] = "Enter Company Name";
	            }
	            if(data[2] == null) {
	            	data[2] = "Enter Address";
	            }
	            if(data[3] == null) {
	            	data[3] = "Enter Phone Number";
	            }
	            if(data[4] == null) {
	            	data[4] = "Enter Gst Number";
	            }
	            if(data[5] == null) {
	            	data[5] = "Enter Pan Number";
	            }
	            if(data[6] == null) {
	            	data[6] = "Enter Email Address";
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
        try (Statement stmt  = c.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
                String vec = new String();
                vec = rs.getString("code") + "#" +
                  rs.getString("description") + "#" +
                  rs.getString("amount");
                  data.add(vec);
            }
            
            if(data.size() <= 0) {
            	data.add("Enter Code#Enter Description#Enter Amount");
            }
            
        } catch(Exception e) {
        	e.printStackTrace();
        }
        return data;
	}

	public Vector<String> getdataofCom() {
		String sql = "SELECT * FROM companies";
        Vector<String> data = new Vector<String>();
        try (Statement stmt  = c.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
                String vec = new String();
                vec = rs.getString("gstnumber") + "#" +
                  rs.getString("name") + "#" +
                  rs.getString("address");
                  data.add(vec);
            }
            if(data.size() <= 0) {
            	data.add("Enter Gst Number#Enter Name#Enter Address");
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
	
	public void addToSettings(String terNCon, String gstPer, String refNo) throws SQLException {
		Date date = new Date();
		SimpleDateFormat dnt = new SimpleDateFormat("dd/ MMM/ yyyy");
		
		try {
			System.out.println("Entring 1st Data");
			String sql = "INSERT INTO Settings(id, TermsNCon, GstPer, RefNo, RefDate) VALUES(?,?,?,?,?)";
	        PreparedStatement pstmt = c.prepareStatement(sql);
	        	pstmt.setLong(1, 1);
	            pstmt.setString(2, terNCon);
	            pstmt.setString(3, gstPer);
	            pstmt.setString(4, refNo);
	            pstmt.setString(5, String.valueOf(dnt.format(date)));
	            pstmt.executeUpdate();
	           
		} catch (Exception e) {
			String da = "";
			 String sql = "SELECT RefDate FROM Settings";
			 try (Statement stmt  = c.createStatement();
		             ResultSet rs    = stmt.executeQuery(sql)){
		            
		        	System.out.println("In Get Data Act");
		        	
		            while (rs.next()) {
			        	da = rs.getString("RefDate");		            	
		            }		            
		        } catch(Exception ex) {
		        }
			 System.out.println(da.equalsIgnoreCase(dnt.format(date)));
			 if(da.equalsIgnoreCase(dnt.format(date))) {
				sql = "UPDATE Settings SET TermsNCon = ?,GstPer = ?,RefNo = ? WHERE id = 1";
		        PreparedStatement pstmt = c.prepareStatement(sql);
		            pstmt.setString(1, terNCon);
		            pstmt.setString(2, gstPer);
		            pstmt.setString(3, String.valueOf(Integer.parseInt(refNo) <= 0 ? Integer.parseInt(refNo)+1 :Integer.parseInt(refNo)));
		            pstmt.executeUpdate();				 
			 } else {
				sql = "UPDATE Settings SET TermsNCon = ?,GstPer = ?,RefNo = ?,RefDate = ? WHERE id = 1";
		        PreparedStatement pstmt = c.prepareStatement(sql);
		            pstmt.setString(1, terNCon);
		            pstmt.setString(2, gstPer);
		            pstmt.setString(3, "000");
		            pstmt.setString(4, dnt.format(date));
		            pstmt.executeUpdate();
			 }	            
		}
		System.out.println("Data is Saved");
	}
	
	public void incRef() throws SQLException { 
	String sql = "SELECT RefNo FROM Settings";
	String refNo = "000";
	 try (Statement stmt  = c.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
        	System.out.println("In Get Data Act");
        	
            while (rs.next()) {
	        	refNo = rs.getString("RefNo");		            	
            }		  
            
        } catch(Exception ex) {}
	 
		sql = "UPDATE Settings SET RefNo = ? WHERE id = 1";
        PreparedStatement pstmt = c.prepareStatement(sql);
        pstmt.setString(1, String.valueOf(Integer.parseInt(refNo)+1));
        pstmt.executeUpdate();	
	}
	
	public String[] getSettings() {
		 String sql = "SELECT * FROM Settings";
	        String[] data = new String[3];
	        try (Statement stmt  = c.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
	            
	        	System.out.println("In Get Data Act");
	        	
	            while (rs.next()) {
	                  data[0] = rs.getString("TermsNCon");
	                  data[1] = rs.getString("GstPer");
	                  data[2] = rs.getString("RefNo");
	            }
	          
	            if(data[0] == null) {
	                data[0] = "Enter Term And Condition";	            	
	            }
	            if(data[1] == null) {
	                data[1] = "Enter Gst Percentages";	            	
	            }
	            
	            if(data[2] == null) {
	                data[2] = "000";
	            }
	            
	        } catch(Exception e) {
	        	e.printStackTrace();
	        }
	        return data;
	}
}
