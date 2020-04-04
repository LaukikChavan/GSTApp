package windows;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import org.apache.poi.EncryptedDocumentException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class MainController {
	
	@FXML
	public TextField probName;
	public TextField comName;
	public TextField address;
	public TextField telNo;
	public TextField gstNo;
	public TextField emailAdd;
	public TextField panNo;
	
	@FXML
	public TextArea particularDes;
	public TextField serviceCode;
	public TextField amount;
	public ComboBox<String> particularCombo;
	
	@FXML
	public TextField otherComName;
	public TextField othergstNo;
	public TextField otherAdd;
	public ComboBox<String> comBox;
	
	@FXML 
	public ComboBox<String> mainBox;
	public ScrollPane mainList;
	public CheckBox[] checkBoxes;
	public Button mainOkBtn;
	
	@FXML
	public TextArea tnCField;
	public TextField gstPerText;
	public TextField refNoText;
	public Button settOkBtn;
	
	public String[] data;
	public String[] comData;
	
	public ArrayList<String> selectedBoxes;
	public int selectedIndex = 0;
	public boolean[] selectedB;
	public File selectedFile = null;
	public String imageFileName;
	
	public static DatabaseConnections db = new DatabaseConnections();
	
	public void getParForCheckBox() throws ClassNotFoundException, SQLException {
		Vector<String> data = (Vector<String>) db.getdataofParti();
		checkBoxes = new CheckBox[data.size()];
		selectedB = new boolean[data.size()];		
		int j = 0;
		if(data.size()> 0) {
			for(String i : data) {
				String p[] = i.split("#");
				System.out.println(p[0] + " " + p[1] + " " + p[2]);
				CheckBox temp = new CheckBox(p[0] + " :" + p[1] + " :" +  p[2]);
				checkBoxes[j] = temp;
				selectedB[j] = false;
				checkBoxes[j].setOnAction(event -> {
					int h = checkBoxes.length;
					for(int y=0; y < h; y++) {
						String[] df = checkBoxes[y].toString().split("'");
						String[] dr = event.toString().split("'");
						
						
						if(df[1].equals(dr[1])) {
							selectedB[y] = !selectedB[y];
						}
					}
				});
				j++;
			}
			VBox content = new VBox();
			mainList.setContent(content);
			content.getChildren().addAll(checkBoxes);
		}
	}
	
	public void mainPageLoader() throws ClassNotFoundException, SQLException {
		System.out.println("Main Page");
		Vector<String> data = (Vector<String>) db.getdataofCom();
		mainBox.getItems().clear();
		mainBox.setPromptText("Select Company Name");
		if(data.size()> 0) {
			for(String i : data) {
				String p[] = i.split("#");
				mainBox.getItems().add(p[0] + ": " + p[1] + ": " + p[2] );
			}
		}
		getParForCheckBox();
	}
	
	public boolean checkIsNumber(String s) {
		try {
			int i = Integer.parseInt(s);
			System.out.println("i : " + i);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
	public boolean checkParArray() {
		boolean f = true;
		
		if(checkBoxes.length <= 0) {
			f = false;
		}
		
		return f;
	}
	
	public boolean checkTheFields(int i) {
		Alert a1 = new Alert(Alert.AlertType.ERROR);
		a1.setTitle("Fill The Information");
		String checks;
		String errorText = "Please Fill Information Regarding ";
		boolean f = true;
		
		switch(i) {
			case 1:
				checks = probName.getText();
				if(checks.isBlank()) {
					f = f & false;
					errorText += ",Propritor's Name ";
				} 
		
				checks = comName.getText();
				if(checks.isBlank()) {
					f = f & false;
					errorText += ",Company's Name ";
				} 
				checks = address.getText();
				if(checks.isBlank()) {
					f = f & false;
					errorText += ",Address ";
				}
				checks = telNo.getText();
				if(checks.isBlank()) {
					f = f & false;
					errorText += ",Telephone Number ";
				}
		
				checks = gstNo.getText();
				if(checks.isBlank()) {
					f = f & false;
					errorText += ",GST Number ";
				} 
		
				checks = emailAdd.getText();
				if(checks.isBlank()) {
					f = f & false;
					errorText += ",Email Address ";
				} 
		
				checks = panNo.getText();
				if(checks.isBlank()) {
					f = f & false;
					errorText += ",Pan Number ";
				} 
				
			break;
			
			case 2:
				checks = amount.getText();
				if(!checkIsNumber(checks)) {
					f = f & false;
					errorText += ",Amount ";
				} 
				checks = particularDes.getText();
				if(checks.isBlank()) {
					f = f & false;
					errorText += ",Description ";
				} 
				
				checks = serviceCode.getText();
				if(!checkIsNumber(checks)) {
					f = f & false;
					errorText += ",Service Code ";
				}
			break;
			
			case 3:
				checks = otherAdd.getText();
				if(checks.isBlank()) {
					f = f & false;
					errorText += ",Address ";
				}
				checks = otherComName.getText();
				if(checks.isBlank()) {
					f = f & false;
					errorText += ",Company's Name ";
				} 
				checks = othergstNo.getText();
				if(checks.isBlank()) {
					f = f & false;
					errorText += ",GST Number ";
				} 
			break;
			
			case 4:
				checks = mainBox.getValue();
				if(checks == null) {
					f = f & false;
					errorText += ",Compnay Name ";
				}
				if(!checkParArray()) {
					f = f & false;

					errorText += ",Particulars ";
				}
			break;
			case 5:
				checks = tnCField.getText();
				if(checks.isBlank()) {
					f = f & false;
					errorText += ",Terms and Condition ";
				}
				checks = gstPerText.getText();
				if(checks.isBlank()) {
					f = f & false;
					errorText += ",Terms and Condition ";
				} 
				checks = refNoText.getText();
				if(checks.isBlank()) {
					f = f & false;
					errorText += ",Terms and Condition ";
				} 
			break;
		}
		
		if(!f) {
			a1.setContentText(errorText);
			a1.setHeaderText(null);
			a1.showAndWait();
		}
		
		return f;
	}
 	
	public void getDataofParticulars() throws ClassNotFoundException, SQLException {
		System.out.println("Data of Par");
		Vector<String> data = (Vector<String>) db.getdataofParti();
		particularCombo.getItems().clear();
		particularCombo.setPromptText("Please select one if you want to edit");
		if(data.size()> 0) {
			for(String i : data) {
				String p[] = i.split("#");
				particularCombo.getItems().add(p[0] + ": " + p[1] + ":" + p[2]);
			}
		}
	}
	
	public void getDataofMainTable() throws ClassNotFoundException, SQLException, IOException {
		data = db.getdataofMainTable();
		if(data.length > 0) {
			probName.setText(data[0]);
			comName.setText(data[1]);
			address.setText(data[2]);
			telNo.setText(data[3]);
			gstNo.setText(data[4]);
			panNo.setText(data[5]);
			emailAdd.setText(data[6]);
		}
	}
	
	public void getDataofSettings() throws ClassNotFoundException, SQLException, IOException {
		String[] data = db.getSettings();
		System.out.println("Geting the Setting");
		if(data.length > 0) {
			tnCField.setText(data[0]);
			gstPerText.setText(data[1]);
			refNoText.setText(data[2]);
		}
	}
	
	public void getDataofComTable() throws SQLException, ClassNotFoundException {
		System.out.println("Data Of Com");
		Vector<String> data = (Vector<String>) db.getdataofCom();
		comBox.getItems().clear();
		comBox.setPromptText("Please select one if you want to edit");
		if(data.size()> 0) {
			for(String i : data) {
				String p[] = i.split("#");
				comBox.getItems().add(p[0] + ": " + p[1]);
			}
		}
	}
	
	public void SavesInDatabase(ActionEvent e) throws FileNotFoundException {
		try {
			if(checkTheFields(1)) {
				String name, companyname, phonenumber, gstnumber, pannumber, emailaddress, addresstext;
				name = probName.getText();
				companyname = comName.getText();
				phonenumber = telNo.getText();
				gstnumber = gstNo.getText();
				pannumber = panNo.getText();
				emailaddress = emailAdd.getText();
				addresstext = address.getText();
				
				db.addToMainTable(name, companyname, addresstext, gstnumber, pannumber, phonenumber, emailaddress);
				System.out.println("Data is Entred");
				getDataofMainTable();
			}
						
		} catch (ClassNotFoundException | SQLException | IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void SavesInParDatabase(ActionEvent e) throws SQLException, ClassNotFoundException {
		if(checkTheFields(2)) {
			String par, code, am;
			par = particularDes.getText();
			code = serviceCode.getText();
			am = amount.getText();
			db.addToParTable(code, par, am);
			System.out.println("Par Is Saved");
			getDataofParticulars();
		}
		
		System.out.println("Par Data : " + particularCombo.getValue());
		particularDes.setText(null);
		serviceCode.setText(null);
		amount.setText(null);
	}
	
	public void updateParData(ActionEvent e) {
		if(particularCombo.getValue() != null) {
			String temp = particularCombo.getValue();
			String s[] = temp.split(":");
			System.out.println("S0 : " + s[0]);
			String[] p = db.getParParData(s[0]);				
			serviceCode.setText(p[0]);
			particularDes.setText(p[1]);
			amount.setText(p[2]);
			
		}
	}
	
	public void updateComData(ActionEvent e) {
		if(comBox.getValue() != null) {
			String temp = comBox.getValue();
			String s[] = temp.split(":");
			String[] p = db.getParComData(s[0]);			
			othergstNo.setText(p[0]);
			otherComName.setText(p[1]);
			otherAdd.setText(p[2]);
			
		}
	}
	
	public void SavesInComTable(ActionEvent e) throws SQLException, ClassNotFoundException {
		if(checkTheFields(3)) {
			String oadd, oname, ogstNo;
			oadd = otherAdd.getText();
			oname = otherComName.getText();
			ogstNo = othergstNo.getText();
			db.addToComTable(ogstNo, oname, oadd);
			System.out.println("Com Is Saved");
			getDataofComTable();
		}
		System.out.println("Com Data : " + comBox.getValue());
		otherAdd.setText(null);
		otherComName.setText(null);
		othergstNo.setText(null);
	}
	
	public void SavesInSettings(ActionEvent e) throws SQLException, ClassNotFoundException, IOException {
		if(checkTheFields(5)) {
			System.out.println("In Settings");
			String tnC,gstPer,refNo;
			tnC = tnCField.getText();
			gstPer = gstPerText.getText();
			refNo = refNoText.getText();
			db.addToSettings(tnC, gstPer, refNo);
			System.out.println("Settings Is Saved");
			getDataofSettings();
		}
	}

	public void createTheFile(ActionEvent e) throws IOException, ClassNotFoundException, SQLException {
		if(checkTheFields(4)) {			
			String CompanyName;
			String[] SelectedPar;
			String FileName;
			String[] settingData = db.getSettings();
			
			getDataofMainTable();			
			CompanyName = mainBox.getValue();
			
			String[] otherInfo = CompanyName.split(": ");
			
			int sizeP = 0;			
			for(boolean t : selectedB) {
				if(t) {
					sizeP++;
				}
			}
			
			SelectedPar = new String[sizeP];
			
			int n = selectedB.length;
			int j = 0;
			for(int q=0; q < n; q++) {
				if(selectedB[q]) {
					SelectedPar[j] = checkBoxes[q].getText();
					j++;
				}
			}
			
			MainFileCreation m = new MainFileCreation();			
			String header = data[1];
			String headAdd = data[2];
			String telNo = data[3];
			String email = data[6];
			String panNumber = data[5];
			String gstNumber = data[4];
			
			String oname = otherInfo[1];
			String oadd = otherInfo[2];
			String ogst = otherInfo[0];
			
			m.getTheTopHeader(header);
			m.getTheTopDescriptions(headAdd, telNo, email);
			m.getTheTopGstnPan(gstNumber, panNumber);
			String date = m.getTheDatenDes(settingData[2]);
			m.getToSet(oname, oadd, ogst);
			String bill = m.getParticularSet(SelectedPar,settingData[1]);
			m.getTheSingSet(header);
			m.getTheTermsSet(settingData[0]);
			String[] splitDate = date.split("/ ");
			FileName =  System.getProperty("user.home") + "/Desktop/" + splitDate[0] + "-" + splitDate[1] + "-" +splitDate[2] +"_" + oname + "_" + settingData[2] + ".docx";
			
			m.saveInFile(FileName);					
			db.incRef();
			
			Alert a1 = new Alert(Alert.AlertType.CONFIRMATION);
			a1.setTitle("File is Created");		
			a1.setContentText(FileName + " is Created at deskstop");	
			a1.setHeaderText(null);
			a1.showAndWait();		
			db.addToexcelFile(oname, ogst, settingData[2], date, bill);
		}		
		mainBox.setValue(null);
		
		for(int i = 0; i < checkBoxes.length; i++) {
			checkBoxes[i].setSelected(false);
		}
	}
	
	public void createExcelFromDatabase() {
		MainFileCreation m = new MainFileCreation();
		try {
			m.createExcelFile(db.getdataforexcelFile());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
