package windows;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import javafx.scene.control.Alert;

public class MainFileCreation {
    XWPFDocument document;
    Date date;
    String code;
    
	public MainFileCreation() {
		document = new XWPFDocument();      
		date  = new Date();
	}
		
	public void createWord(String lines) throws IOException {
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText("VK Number (Parameter):  here you type your text...\n");
    }
	
	public void getTheTopHeader(String s) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = paragraph.createRun();
        run.setText(s);
        run.setBold(true);
        run.setFontSize(20);
        run.setCapitalized(true);
	}
	
	public void getTheTopDescriptions(String add, String tel, String mail) {
		XWPFParagraph paragraph = document.createParagraph();
	    paragraph.setAlignment(ParagraphAlignment.CENTER);
	    XWPFRun run = paragraph.createRun();
	    run.setFontSize(11);
	    run.setText("Office :- " + add);
	    run.addBreak();
	    run.setText("Tel/Mobile :- " + tel);
	    run.addBreak();
	    run.setText("eamil :- " + mail);
	}
	
	public void getTheTopGstnPan(String gst, String pan) {
		XWPFParagraph paragraph = document.createParagraph();
	    paragraph.setAlignment(ParagraphAlignment.CENTER);
	    XWPFRun run = paragraph.createRun();
	    run.setFontSize(14);
	    run.setText("PAN :- " + pan);
	    run.addBreak();
	    run.setText("GSTIN:- " + gst + "(Management Consultant)");
	    run.addBreak();
	}
	
	public String getTheDatenDes(String refNo) {
		SimpleDateFormat dnt = new SimpleDateFormat("dd/ MMM/ yyyy");
		XWPFParagraph paragraphI = document.createParagraph();
        XWPFRun run = paragraphI.createRun();
        paragraphI.setAlignment(ParagraphAlignment.CENTER);
        run.setFontSize(18);
        run.setBold(true);
        run.setText("Invoice");
        int y = Calendar.getInstance().get(Calendar.YEAR);
        XWPFParagraph par = document.createParagraph();
        run = par.createRun();
        par.setAlignment(ParagraphAlignment.RIGHT);
        y = y-2000;
        run.setText("Invoice No - " + y + "-" + (y+1) + "/" + refNo);
        run.addBreak();
        run.setText("Date :- " + dnt.format(date));
        return String.valueOf(dnt.format(date));
	}
	
	public void getToSet(String name, String add, String gst) {
		XWPFParagraph paragraph = document.createParagraph();
		XWPFParagraph par = document.createParagraph();
		XWPFParagraph par1 = document.createParagraph();
		
	    XWPFRun run = paragraph.createRun();
	    
	    run.setFontSize(11);
	    run.setText("To : ");
	    
	    run = par.createRun();
	    run.addTab();
	    
	    run.setFontSize(14);
	    run.setBold(true);
	    run.setText(name);
	    
	    run = par.createRun();
	    run.addBreak();
	    run.addTab();
	    
	    run.setFontSize(11);
	    run.setBold(false);
	    run.setText(add);
	    
	    run = par1.createRun();
	    run.addBreak();
	    run.setFontSize(12);
	    run.setText("GSTIN :- ");
	    run = par1.createRun();
	    run.setBold(true);
	    run.setText(gst);
	    
	    code = gst.substring(0,2);
	    
	    run = par1.createRun();
	    run.setBold(false);
	    run.addTab();
	    run.setText("Place of Service : Maharashtra");
	    run.addTab();
	    run.setText("Code : " + code);
	    run.addBreak();
	    run.addBreak();
	}
	
	public String getParticularSet(String[] par, String gstText) {
		int i = par.length;
		i = i+4;
		XWPFTable table = document.createTable(i, 4);
		
		XWPFParagraph p1 = table.getRow(0).getCell(0).getParagraphs().get(0);
		p1.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun r1 = p1.createRun();
		r1.setBold(true);
		r1.setText("Sr.No");
		
		XWPFParagraph p2 = table.getRow(0).getCell(1).getParagraphs().get(0);
		p2.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun r2 = p2.createRun();
		r2.setBold(true);
		r2.setText("Particular");
		
		XWPFParagraph p3 = table.getRow(0).getCell(2).getParagraphs().get(0);
		p3.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun r3 = p3.createRun();
		r3.setBold(true);
		r3.setText("Service Code");
		
		XWPFParagraph p4 = table.getRow(0).getCell(3).getParagraphs().get(0);
		p4.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun r4 = p4.createRun();
		r4.setBold(true);
		r4.setText("Amount");
		
		int k = 1;
		int amount = 0;
		for(int j = 0; j < par.length; j++) {
			String ser[] = par[j].split(":");
			table.getRow(k).getCell(0).setText(""+(j+1));
			table.getRow(k).getCell(1).setText(ser[1]);
			table.getRow(k).getCell(2).setText(ser[0]);
			table.getRow(k).getCell(3).setText(ser[2]);
			
			amount = amount + Integer.parseInt(ser[2]);
			
			k++;
		}
		float gst;
		gst = ((amount / 100) * Integer.parseInt(gstText));
		float total;
		
		if(Integer.parseInt(code) == 27) {
			
			table.getRow(k).getCell(2).setText("CGST :"+gstText+"%");
			table.getRow(k).getCell(3).setText(""+gst);
			
			k++;
	
			table.getRow(k).getCell(2).setText("SGST :"+gstText+"%");
			table.getRow(k).getCell(3).setText(""+gst);
			
			k++;
		} else {
			
			table.getRow(k).getCell(2).setText("IGST :" + (Integer.parseInt(gstText) + Integer.parseInt(gstText)) + "%");
			table.getRow(k).getCell(3).setText(""+(gst*2));
			
			k++;
		}
		total = (amount + gst + gst);
		
		XWPFParagraph t = table.getRow(k).getCell(2).getParagraphs().get(0);
		t.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun r = t.createRun();
		r.setBold(true);
		r.setText("Total");
		
		XWPFParagraph x = table.getRow(k).getCell(3).getParagraphs().get(0);
		x.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun xr = x.createRun();
		xr.setBold(true);
		xr.setText(""+Math.round(total) + "=00");
		String numInWords = NumberToWordConverter.numberToWord(Math.round(total));
		
		XWPFParagraph par2 = document.createParagraph();
		XWPFRun y1 = par2.createRun();
		y1.addBreak();
		y1.setBold(true);
		y1.setFontSize(11);
		y1.setCapitalized(true);
		y1.setText("Amount is Words :- " + numInWords + " RS.");
		
		return String.valueOf(total);
	}
	
	public void getTheSingSet(String s) {
		XWPFParagraph par = document.createParagraph();
		XWPFRun run = par.createRun();
		par.setAlignment(ParagraphAlignment.RIGHT);
		run.setBold(true);
		run.setFontSize(12);
		run.addBreak();
		run.addBreak();
		run.setText("For " + s);
		run.addBreak();
		run.addBreak();
		run.addBreak();
		run.addBreak();
		run.addBreak();
		run.addBreak();
		run.setText("Authorised Signatory");
	}
	
	public void getTheTermsSet(String s) throws SQLException {
		XWPFParagraph par = document.createParagraph();
		XWPFRun run = par.createRun();
		run.setBold(true);
		run.setFontSize(13);
		run.setText("Terms & Conditions : ");
		run.addBreak();
		run = par.createRun();
		run.setFontSize(10);
		run.addBreak();
		run.setText(s);
	}
	
 	public void saveInFile(String s) throws IOException {
		FileOutputStream file = new FileOutputStream(s);
		document.write(file);
		document.close();
		System.out.println("File Is Creted");
	}
 	
 	public void createExcelFile(ResultSet rs) throws SQLException, EncryptedDocumentException, FileNotFoundException, IOException {   
        Workbook writeWorkbook = new HSSFWorkbook();
        Sheet desSheet = writeWorkbook.createSheet("new sheet");
        String FileName = "";
        try{
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            String[] headings = {"ID","Company Name", "GST Number", "Refrence No", "Date", "Total Bill"};
            
            Row desRow1 = desSheet.createRow(0);
            for(int col=0 ;col < columnsNumber;col++) {
                Cell newpath = desRow1.createCell(col);
                newpath.setCellValue(headings[col]);
            }
            while(rs.next()) {
                System.out.println("Row number" + rs.getRow() );
                Row desRow = desSheet.createRow(rs.getRow());
                for(int col=0 ;col < columnsNumber;col++) {
                    Cell newpath = desRow.createCell(col);
                    newpath.setCellValue(rs.getString(col+1));  
                }
                
                SimpleDateFormat dn = new SimpleDateFormat("MMMMMMMM_yyyy");
                SimpleDateFormat df = new SimpleDateFormat("yyyy");
                String Fname = String.valueOf(dn.format(date)) + "-" + String.valueOf(Integer.parseInt(String.valueOf(df.format(date)).substring(2))+1);
                FileName = System.getProperty("user.home") + "/Desktop/" + Fname + ".xlsx";
                FileOutputStream fileOut = new FileOutputStream(FileName);
                writeWorkbook.write(fileOut);
                fileOut.close();
            }

			Alert a1 = new Alert(Alert.AlertType.CONFIRMATION);
			a1.setTitle("File is Created");		
			a1.setContentText(FileName + " is Created at deskstop");	
			a1.setHeaderText(null);
			a1.showAndWait();		
        }
        catch (SQLException | IOException e) {
            System.out.println("Failed to get data from database");
        }
    }
}



















