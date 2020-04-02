package windows;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Vector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class MainController {
	@FXML 
	public Button FileSelectBtn;
	
	@FXML
	public Label filePathLabel;
	
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
	public ImageView signImage;
	
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
	public TextField fileName;
	
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
							System.out.println(df[1]);
							System.out.println(dr[1]);
							System.out.println("is Equal");
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
				System.out.println(p[0] + " " + p[1] + " " + p[2]);
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
					probName.setStyle("-fx-border-color: red;");
					f = f & false;
					errorText += ",Propritor's Name ";
				} else {
					probName.setStyle("-fx-border-color: black;");
				}
		
				checks = comName.getText();
				if(checks.isBlank()) {
					comName.setStyle("-fx-border-color: red;");
					f = f & false;
					errorText += ",Company's Name ";
				} else {
					comName.setStyle("-fx-border-color: black;");
				}
				checks = address.getText();
				if(checks.isBlank()) {
					address.setStyle("-fx-border-color: red;");
					f = f & false;
					errorText += ",Address ";
				} else {
					address.setStyle("-fx-border-color: black;");
				}
				checks = telNo.getText();
				if(checks.isBlank()) {
					telNo.setStyle("-fx-border-color: red;");
					f = f & false;
					errorText += ",Telephone Number ";
				} else {
					telNo.setStyle("-fx-border-color: black;");
				}
		
				checks = gstNo.getText();
				if(checks.isBlank()) {
					gstNo.setStyle("-fx-border-color: red;");
					f = f & false;
					errorText += ",GST Number ";
				} else {
					gstNo.setStyle("-fx-border-color: black;");
				}
		
				checks = emailAdd.getText();
				if(checks.isBlank()) {
					emailAdd.setStyle("-fx-border-color: red;");
					f = f & false;
					errorText += ",Email Address ";
				} else {
					emailAdd.setStyle("-fx-border-color: black;");
				}
		
				checks = panNo.getText();
				if(checks.isBlank()) {
					panNo.setStyle("-fx-border-color: red;");
					f = f & false;
					errorText += ",Pan Number ";
				} else {
					panNo.setStyle("-fx-border-color: black;");
				}
		
				checks = filePathLabel.getText();
				if(checks.isBlank()) {
					FileSelectBtn.setStyle("-fx-border-color: red;");
					f = f & false;
					errorText += ",FilePath ";
				}
			break;
			
			case 2:
				checks = amount.getText();
				if(!checkIsNumber(checks)) {
					amount.setStyle("-fx-border-color: red;");
					f = f & false;
					errorText += ",Amount ";
				} else {
					System.out.println(checkIsNumber(checks));
					amount.setStyle("-fx-border-color: black;");
				}
				checks = particularDes.getText();
				if(checks.isBlank()) {
					particularDes.setStyle("-fx-border-color: red;");
					f = f & false;
					errorText += ",Description ";
				} else {
					particularDes.setStyle("-fx-border-color: black;");
				}
				
				checks = serviceCode.getText();
				if(!checkIsNumber(checks)) {
					serviceCode.setStyle("-fx-border-color: red;");
					f = f & false;
					errorText += ",Service Code ";
				} else {
					serviceCode.setStyle("-fx-border-color: black;");
				}
			break;
			
			case 3:
				checks = otherAdd.getText();
				if(checks.isBlank()) {
					otherAdd.setStyle("-fx-border-color: red;");
					f = f & false;
					errorText += ",Address ";
				} else {
					otherAdd.setStyle("-fx-border-color: black;");
				}
				checks = otherComName.getText();
				if(checks.isBlank()) {
					otherComName.setStyle("-fx-border-color: red;");
					f = f & false;
					errorText += ",Company's Name ";
				} else {
					otherComName.setStyle("-fx-border-color: black;");
				}
				checks = othergstNo.getText();
				if(checks.isBlank()) {
					othergstNo.setStyle("-fx-border-color: red;");
					f = f & false;
					errorText += ",GST Number ";
				} else {
					othergstNo.setStyle("-fx-border-color: black;");
				}
			break;
			
			case 4:
				checks = mainBox.getValue();
				if(checks == null) {
					f = f & false;
					errorText += ",Compnay Name ";
				}
				checks = fileName.getText();
				if(checks.isBlank()) {
					fileName.setStyle("-fx-border-color: red;");
					f = f & false;
					errorText += ",File Name ";
				} else {
					fileName.setStyle("-fx-border-color: black;");
				}
				if(!checkParArray()) {
					f = f & false;

					errorText += ",Particulars ";
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
	
	public static void decodeToImage() throws IOException {
		 String[] dbdata = db.getdataofMainTable();
		 byte[] data = Base64.getDecoder().decode(dbdata[6]);
		 FileOutputStream fileoutput = new FileOutputStream(dbdata[8]);
		 fileoutput.write(data);
		 fileoutput.close();
    }
 	
	public void getDataofParticulars() throws ClassNotFoundException, SQLException {
		System.out.println("Data of Par");
		Vector<String> data = (Vector<String>) db.getdataofParti();
		particularCombo.getItems().clear();
		particularCombo.setPromptText("Please select one if you want to edit");
		if(data.size()> 0) {
			for(String i : data) {
				String p[] = i.split("#");
				System.out.println(p[0] + " " + p[1] + " " + p[2]);
				particularCombo.getItems().add(p[0] + ": " + p[1] + ":" + p[2]);
			}
		}
	}
	
	public void getDataofMainTable() throws ClassNotFoundException, SQLException, IOException {
		data = db.getdataofMainTable();
		if(data.length > 0) {
			String imgName;
			probName.setText(data[0]);
			comName.setText(data[1]);
			address.setText(data[2]);
			telNo.setText(data[3]);
			gstNo.setText(data[4]);
			panNo.setText(data[5]);
			emailAdd.setText(data[7]);
			filePathLabel.setText(data[8]);
			decodeToImage();
			imgName = data[8];
			File file = new File(imgName);
			FileInputStream fi = new FileInputStream(file);
			Image image = new Image(fi);
			signImage.setImage(image);
			if(file.delete()) {
				System.out.println("File is Deleted");
			} else {
				System.out.println("File is There clear your self");
			}
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
				System.out.println(p[0] + " " + p[1] + " " + p[2]);
				comBox.getItems().add(p[0] + ": " + p[1]);
			}
		}
	}
	
	public void FileChoose(ActionEvent e) {
		FileChooser fc = new FileChooser();	
		selectedFile = fc.showOpenDialog(null);
		
		if(selectedFile != null) {
			filePathLabel.setText("Selected File :- "+ selectedFile.getPath() + "			");
		}
		else {
			System.out.println("File Is InVaild");
		}
	}
	
	public void SavesInDatabase(ActionEvent e) throws FileNotFoundException {
		try {
			if(checkTheFields(1)) {
				String name, companyname, phonenumber, gstnumber, pannumber, emailaddress, signimage, addresstext, imgname;
				name = probName.getText();
				companyname = comName.getText();
				phonenumber = telNo.getText();
				gstnumber = gstNo.getText();
				pannumber = panNo.getText();
				emailaddress = emailAdd.getText();
				addresstext = address.getText();
				
				if(selectedFile != null) {
					FileInputStream fileInput = new FileInputStream(selectedFile);
					byte[] src = fileInput.readAllBytes();
					signimage = Base64.getEncoder().encodeToString(src);
					imgname = selectedFile.getName();
				} else {
					signimage = data[6];
					imgname = data[8];
				}
				
				db.addToMainTable(name, companyname, addresstext, gstnumber, pannumber, phonenumber, emailaddress, signimage, imgname);
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
			System.out.println("S0 :" + s[0]);
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
	}

	public void createTheFile(ActionEvent e) throws IOException, ClassNotFoundException, SQLException {
		if(checkTheFields(4)) {
			
			String CompanyName;
			String[] SelectedPar;
			String FileName;
			
			String[] terms = {
					"plwase make all payment to ......",
					"Hiiii How are you........"
			};
			
			getDataofMainTable();
			
			FileName = fileName.getText() + ".docx";
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
					System.out.println(SelectedPar[j]);
					j++;
					System.out.println("ADD");
				}
			}
			
			MainFileCreation m = new MainFileCreation();
			
			System.out.println("Data :- " + data[0] + " " + data[1] + " " + data[2] + " ");
			
			String header = data[1];
			String headAdd = data[2];
			String telNo = data[3];
			String email = data[7];
			String panNumber = data[5];
			String gstNumber = data[4];
			
			String oname = otherInfo[1];
			String oadd = otherInfo[2];
			String ogst = otherInfo[0];
			
			m.getTheTopHeader(header);
			m.getTheTopDescriptions(headAdd, telNo, email);
			m.getTheTopGstnPan(gstNumber, panNumber);
			m.getTheDatenDes();
			m.getToSet(oname, oadd, ogst);
			m.getParticularSet(SelectedPar);
			m.getTheSingSet(header);
			m.getTheTermsSet(terms);
			m.saveInFile(FileName);
						
			System.out.println("In Action Brox");
		}
	}
}
