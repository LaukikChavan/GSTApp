package windows;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;

public class MainFileCreation {
    XWPFDocument document;
    Date date;
    
	public static void main(String args[]) throws IOException {
		MainFileCreation m = new MainFileCreation();
		
		String header = "Samarth Consultancy Services";
		String headAdd = "Flat No. L- 603 Ujwal Terraces, Raikar Nagar, Sinhagad Road, Dhayri, Pune - 411041 Maharashtra, India";
		String telNo = "9822552519";
		String email = "samarthconsultancyservices@rediffmail.com";
		String panNumber = "AFKPC1475Q";
		String gstNumber = "27AFKPC1475Q1ZZ";
		
		String oname = "XYZ Co. PVT. LDT";
		String oadd = "Somewhere, on somewhere, on somewhere, somewhere - 41";
		String ogst = "27AAECH0073R1Z7";
		
		String[] par = {
				"1234#hiiii#2000",
				"2345#Byeee#1000",
				"3456#sleepy#900"
		};
		
		String[] terms = {
				"plwase make all payment to ......",
				"Hiiii How are you........"
		};
		
		m.getTheTopHeader(header);
		m.getTheTopDescriptions(headAdd, telNo, email);
		m.getTheTopGstnPan(gstNumber, panNumber);
		m.getTheDatenDes();
		m.getToSet(oname, oadd, ogst);
		m.getParticularSet(par);
		m.getTheSingSet(header);
		m.getTheTermsSet(terms);
		m.saveInFile("Temp.docx");
	}
	
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
	
	public void getTheDatenDes() {
		SimpleDateFormat dnt = new SimpleDateFormat("DD/MM/YYYY");
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
        run.setText("Invoice No - " + y + "-" + (y+1) + "/139");
        run.addBreak();
        run.setText("Date :- " + dnt.format(date));
        
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
	    
	    run = par1.createRun();
	    run.setBold(false);
	    run.addTab();
	    run.setText("Place of Service : Maharashtra");
	    run.addTab();
	    run.setText("Code : 27");
	    run.addBreak();
	    run.addBreak();
	}
	
	public void getParticularSet(String[] par) {
		int i = par.length;
		i = i+4;
		XWPFTable table = document.createTable(i, 4);
		
		// write to first row, first column
		XWPFParagraph p1 = table.getRow(0).getCell(0).getParagraphs().get(0);
		p1.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun r1 = p1.createRun();
		r1.setBold(true);
		r1.setText("Sr.No");
		
		// write to first row, second column
		XWPFParagraph p2 = table.getRow(0).getCell(1).getParagraphs().get(0);
		p2.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun r2 = p2.createRun();
		r2.setBold(true);
		r2.setText("Particular");
		
		// write to first row, third column
		XWPFParagraph p3 = table.getRow(0).getCell(2).getParagraphs().get(0);
		p3.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun r3 = p3.createRun();
		r3.setBold(true);
		r3.setText("Service Code");
		
		// write to first row, fourth column
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
		gst = ((amount / 100) * 9);
		float total;

		table.getRow(k).getCell(2).setText("CGST 9%");
		table.getRow(k).getCell(3).setText(""+gst);
		
		k++;

		table.getRow(k).getCell(2).setText("SGST 9%");
		table.getRow(k).getCell(3).setText(""+gst);
		
		k++;
		
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
	
	public void getTheTermsSet(String[] s) {
		XWPFParagraph par = document.createParagraph();
		XWPFRun run = par.createRun();
		
		run.setBold(true);
		run.setFontSize(13);
		run.setText("Terms & Conditions : ");
		int n = s.length;
		run.addBreak();
		run = par.createRun();
		run.setFontSize(11);
		for(int i=0; i < n; i++) {
			run.addBreak();
			run.setText((i+1) + ") " + s[i]);
		}
		
	}
	
 	public void saveInFile(String s) throws IOException {
		FileOutputStream file = new FileOutputStream(s);
		document.write(file);
		document.close();
		System.out.println("File Is Creted");
	}
}



















