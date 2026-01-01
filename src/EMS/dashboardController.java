package EMS;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class dashboardController implements Initializable{
    
    @FXML
    private AnchorPane mainForm;
    
    @FXML
    private Button homeBtn;

    @FXML
    private Button addEmployeeAddBtn;

    @FXML
    private Button addEmployeeBtn;

    @FXML
    private Button addEmployeeClearBtn;

    @FXML
    private TableView<employeeData> addEmployeeTableView;
    
    @FXML
    private TableColumn<employeeData, String> addEmployeeColEmployeeId;

    @FXML
    private TableColumn<employeeData, String> addEmployeeColFirstName;

    @FXML
    private TableColumn<employeeData, String> addEmployeeColGender;

    @FXML
    private TableColumn<employeeData, String> addEmployeeColLastName;

    @FXML
    private TableColumn<employeeData, String> addEmployeeColPhoneNumber;

    @FXML
    private TableColumn<employeeData, String> addEmployeeColPosition;
    
    @FXML
    private TableColumn<employeeData, String> addEmployeeColDateMember;

    @FXML
    private Button addEmployeeDeleteBtn;

    @FXML
    private TextField addEmployeeEmployeeId;

    @FXML
    private TextField addEmployeeFirstName;

    @FXML
    private AnchorPane addEmployeeForm;

    @FXML
    private ComboBox<?> addEmployeeGender;

    @FXML
    private Button addEmployeeImportBtn;

    @FXML
    private TextField addEmployeeLastName;

    @FXML
    private TextField addEmployeePhoneNumber;

    @FXML
    private ImageView addEmployeePhoto;

    @FXML
    private ComboBox<?> addEmployeePosition;

    @FXML
    private TextField addEmployeeSearchTextField;

    @FXML
    private Button addEmployeeUpdateBtn;

    @FXML
    private Button close;

    @FXML
    private Button employeeSalaryBtn;

    @FXML
    private AnchorPane homeForm;

    @FXML
    private Label homeTotalEmployeesNumber;

    @FXML
    private Label homeTotalInactivesNumber;

    @FXML
    private Label homeTotalPresentsNumber;

    @FXML
    private Button logout;

    @FXML
    private Button minimize;

    @FXML
    private Button salaryClearBtn;

    @FXML
    private TableView<employeeData> salaryTableView;
    
    @FXML
    private TableColumn<employeeData, String> salaryColEmployeeID;

    @FXML
    private TableColumn<employeeData, String> salaryColFirstName;

    @FXML
    private TableColumn<employeeData, String> salaryColLastName;

    @FXML
    private TableColumn<employeeData, String> salaryColPosition;

    @FXML
    private TableColumn<employeeData, String> salaryColSalary;

    @FXML
    private TextField salaryEmployeeId;

    @FXML
    private Label salaryFirstName;

    @FXML
    private AnchorPane salaryForm;

    @FXML
    private Label salaryLastName;

    @FXML
    private Label salaryPosition;

    @FXML
    private TextField salarySalary;
    
    @FXML
    private Button salaryUpdateBtn;

    @FXML
    private Label username;
    
    @FXML
    private AnchorPane homeChartPane;

    @FXML
    private BarChart<String, Number> homeEmployeesDataChartTable;
    
    // Database Tools
    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    
    private double x = 0;
    private double y = 0;
    
    private Image image;
    
    public void homeTotalEmployees() {
        
        String sql = "SELECT COUNT(id) FROM employee";
        
        connect = database.connectDb();
        int countData = 0;
        
        try {
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()) {
                countData = result.getInt("COUNT(id)");
            }
            
            homeTotalEmployeesNumber.setText(String.valueOf(countData));
            
        }catch(Exception e) {e.printStackTrace();}
    }
            
    public void homeTotalPresents() {
        
        String sql = "SELECT COUNT(id) FROM employee_info";
        
        connect = database.connectDb();
        int countData = 0;
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()) {
                countData = result.getInt("COUNT(Id)");
            }
            
            homeTotalPresentsNumber.setText(String.valueOf(countData));
            
        }catch(Exception e) {e.printStackTrace();}
    }
    
    public void homeTotalInactives() {
        
        String sql = "SELECT COUNT(id) FROM employee_info WHERE salary = '0.0'";
        
        connect = database.connectDb();
        int countData = 0;
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()) {
                countData = result.getInt("COUNT(Id)");
            }
            
            homeTotalInactivesNumber.setText(String.valueOf(countData));
            
        }catch(Exception e) {e.printStackTrace();}
    }
    
    public void homeChart() {
        homeEmployeesDataChartTable.getData().clear();

        String sql = "SELECT date, COUNT(id) FROM employee GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 7";

        connect = database.connectDb();

        try {
            XYChart.Series<String, Number> chart = new XYChart.Series<>();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()) {
                chart.getData().add(new XYChart.Data<>(result.getString(1), result.getInt(2)));
            }

            homeEmployeesDataChartTable.getData().add(chart);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    private String[] positionList = {"Marketer Coordinator"
            , "Web Developer (Full Stack)"
            , "Web Developer (Back End)"
            , "Web Developer (Front End)"
            , "App Developer"
            , "Data Analyst"};
    
    public void addEmployeePositionList() {
        List<String> listP = new ArrayList<>();
        
        for(String position : positionList) {
            listP.add(position);
        }
        
        ObservableList listData = FXCollections.observableArrayList(listP);
        addEmployeePosition.setItems(listData);
    }
    
    private String[] genderList = {"Male", "Female"};
    
    public void addEmployeeGenderList() {
        List<String> listG = new ArrayList<>();
        
        for(String gender : genderList) {
            listG.add(gender);
        }
        
        ObservableList listData = FXCollections.observableArrayList(listG);
        addEmployeeGender.setItems(listData);
    }
    
    public void addEmployeeAdd() {
        
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        
        String sql = "INSERT INTO employee "
                + "(employeeId,firstName,lastName,gender,phoneNumber,position,image,date) "
                + "VALUES(?,?,?,?,?,?,?,?)";
        
        connect = database.connectDb();
        
        try {
            
            Alert alert;
            if(addEmployeeEmployeeId.getText().isEmpty()
                || addEmployeeEmployeeId.getText().isEmpty()
                || addEmployeeFirstName.getText().isEmpty()
                || addEmployeeLastName.getText().isEmpty()
                || addEmployeeGender.getSelectionModel().getSelectedItem() == null
                || addEmployeePhoneNumber.getText().isEmpty()
                || addEmployeePosition.getSelectionModel().getSelectedItem() == null
                || getData.path == null || getData.path == "") {
                
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Massage");
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill all blank fields");
                    alert.showAndWait();
            } else {
                
                String check = "SELECT employeeId FROM employee WHERE employeeId = '" 
                        +addEmployeeEmployeeId.getText()+"'";
                
                statement = connect.createStatement();
                result = statement.executeQuery(check);
                
                if(result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Massage");
                    alert.setHeaderText(null);
                    alert.setContentText("Employee ID: " + addEmployeeEmployeeId.getText() + "was already exist!");
                    alert.showAndWait();
                }
                
                else {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, addEmployeeEmployeeId.getText());
                    prepare.setString(2, addEmployeeFirstName.getText());
                    prepare.setString(3, addEmployeeLastName.getText());
                    prepare.setString(4, (String)addEmployeeGender.getSelectionModel().getSelectedItem());
                    prepare.setString(5, addEmployeePhoneNumber.getText());
                    prepare.setString(6, (String)addEmployeePosition.getSelectionModel().getSelectedItem());

                    String uri = getData.path;
                    uri = uri.replace("\\", "\\\\");

                    prepare.setString(7, uri);
                    prepare.setString(8, String.valueOf(sqlDate));
                    prepare.executeUpdate();
                    
                    String insertInfo = "INSERT INTO employee_info " 
                            + "(employeeId,firstName,lastName,position,salary,date)"
                            + "VALUES(?,?,?,?,?,?)";
                    
                    prepare = connect.prepareStatement(insertInfo);
                    prepare.setString(1, addEmployeeEmployeeId.getText());
                    prepare.setString(2, addEmployeeFirstName.getText());
                    prepare.setString(3, addEmployeeLastName.getText());
                    prepare.setString(4, (String)addEmployeePosition.getSelectionModel().getSelectedItem());
                    prepare.setDouble(5, 0.0);
                    prepare.setString(6, String.valueOf(sqlDate));
                    prepare.executeUpdate();
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Massage");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();
                    
                    addEmployeeShowListData();
                    addEmployeeReset();
                }
            }
            
        }catch(Exception e) {e.printStackTrace();}
    }
    
    public void addEmployeeDelete() {
        
        String sql = "DELETE FROM employee WHERE employeeId = '" + addEmployeeEmployeeId.getText() + "'";
        
        connect = database.connectDb();
        
        try {
            
            Alert alert;
            if(addEmployeeEmployeeId.getText().isEmpty()
                || addEmployeeEmployeeId.getText().isEmpty()
                || addEmployeeFirstName.getText().isEmpty()
                || addEmployeeLastName.getText().isEmpty()
                || addEmployeePhoneNumber.getText().isEmpty()
                || getData.path == null || getData.path == "") {
                
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Massage");
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill all blank fields");
                    alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Massage");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want DELETE Employee id: " + addEmployeeEmployeeId.getText());
                alert.showAndWait();
                Optional<ButtonType> option = alert.showAndWait();
                
                if(option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);
                    
                    String deleteInfo = "DELETE FROM employee_info WHERE employeeId = '"
                           + addEmployeeEmployeeId.getText() + "'";
                    
                    prepare = connect.prepareStatement(deleteInfo);
                    prepare.executeUpdate();
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Massage");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfuly Deleted!");
                    alert.showAndWait();

                    addEmployeeShowListData();
                    addEmployeeReset();
                }
            }
            
        }catch(Exception e) {e.printStackTrace();}
    }
    
    public void addEmployeeReset() {
        addEmployeeEmployeeId.setText("");
        addEmployeeFirstName.setText("");
        addEmployeeLastName.setText("");
        addEmployeeGender.getSelectionModel().clearSelection();
        addEmployeePhoneNumber.setText("");
        addEmployeePosition.getSelectionModel().clearSelection();
        addEmployeePhoto.setImage(null);
        getData.path = "";
    }
    
    public void addEmployeeUpdate() {
        
        String uri = getData.path;
        uri = uri.replace("\\", "\\\\");
        
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        
        String sql = "UPDATE employee SET firstName = '"
                + addEmployeeFirstName.getText() + "', lastName = '"
                + addEmployeeLastName.getText() + "', gender = '"
                + addEmployeeGender.getSelectionModel().getSelectedItem() + "', position = '"
                + addEmployeePosition.getSelectionModel().getSelectedItem() + "', phoneNumber = '"
                + addEmployeePhoneNumber.getText() + "', image = '" 
                + uri + "', date = '" + sqlDate + "' WHERE employeeId = '" 
                + addEmployeeEmployeeId.getText() + "'";
        
        connect = database.connectDb();
        
        try {
            
            Alert alert;
            if(addEmployeeEmployeeId.getText().isEmpty()
                || addEmployeeEmployeeId.getText().isEmpty()
                || addEmployeeFirstName.getText().isEmpty()
                || addEmployeeLastName.getText().isEmpty()
                || addEmployeeGender.getSelectionModel().getSelectedItem() == null
                || addEmployeePhoneNumber.getText().isEmpty()
                || addEmployeePosition.getSelectionModel().getSelectedItem() == null
                || getData.path == null || getData.path == "") {
                
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Massage");
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill all blank fields");
                    alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Massage");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want UPDATE Employee id: " + addEmployeeEmployeeId.getText());
                alert.showAndWait();
                Optional<ButtonType> option = alert.showAndWait();
                    if(option.get().equals(ButtonType.OK)) {
                        statement = connect.createStatement();
                        statement.executeUpdate(sql);
                        
                        String updateInfo = "UPDATE employee_info SET firstName = '"
                                + salaryFirstName.getText() + "', lastName = '"
                                + salaryLastName.getText() + "', position = '"
                                + addEmployeePosition.getSelectionModel().getSelectedItem()
                                + "' WHERE employeeId = '"
                                + addEmployeeEmployeeId.getText() + "'";

                                
                        prepare = connect.prepareStatement(updateInfo);
                        prepare.executeUpdate();
                        
                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Information Massage");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfuly Updated!");
                        alert.showAndWait();
                        
                        addEmployeeShowListData();
                        addEmployeeReset();
                    }
            }
            
        }catch(Exception e) {e.printStackTrace();}
    }
    
    public void addEmployeeInsertImage() {
        FileChooser open = new FileChooser();
        File file = open.showOpenDialog(mainForm.getScene().getWindow());
        
        if(file != null) {
            getData.path = file.getAbsolutePath();
            
            image = new Image(file.toURI().toString(), 125, 117, false, true);
            addEmployeePhoto.setImage(image);
        }
        
    }
    
    public ObservableList<employeeData> addEmployeeListData() {
        
        ObservableList<employeeData> listData = FXCollections.observableArrayList();
        
        String sql = "SELECT * FROM employee";
        
        connect = database.connectDb();
        
        try {
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            employeeData employeeD;
            
            while(result.next()) {
                employeeD = new employeeData(result.getInt("employeeId")
                        , result.getString("firstName")
                        , result.getString("lastName")
                        , result.getString("gender")
                        , result.getString("phoneNumber")
                        , result.getString("position")
                        , result.getString("image")
                        , result.getDate("date"));
                
                listData.add(employeeD);
            }
        }catch(Exception e) {e.printStackTrace();}
        
        return listData;
    }
    
    public void addEmployeeSearch() {
        
        FilteredList<employeeData> filter = new FilteredList<>(addEmployee, e-> true);   
        
        addEmployeeSearchTextField.textProperty().addListener((Observable, oldValue, newValue) -> {
            
            filter.setPredicate(predicateEmployeeData ->{
                
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                
                String searchKey = newValue.toLowerCase();
                
                if(predicateEmployeeData.getEmployeeId().toString().contains(searchKey)) {
                    return true;
                }else if(predicateEmployeeData.getFirstName().toLowerCase().contains(searchKey)) {
                    return true;
                }else if(predicateEmployeeData.getLastName().toLowerCase().contains(searchKey)) {
                    return true;
                }else if(predicateEmployeeData.getGender().toLowerCase().contains(searchKey)) {
                    return true;
                }else if(predicateEmployeeData.getPhoneNumber().toLowerCase().contains(searchKey)) {
                    return true;
                }else if(predicateEmployeeData.getPosition().toLowerCase().contains(searchKey)) {
                    return true;
                }else if(predicateEmployeeData.getDate().toString().contains(searchKey)) {
                    return true;
                }else return false;
            });
        });
        
        SortedList<employeeData> sortList = new SortedList<>(filter);
        
        sortList.comparatorProperty().bind(addEmployeeTableView.comparatorProperty());
        addEmployeeTableView.setItems(sortList);
    }
    
    private ObservableList<employeeData> addEmployee;
    
    public void addEmployeeShowListData() {
        addEmployee = addEmployeeListData();
        
        addEmployeeColEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        addEmployeeColFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        addEmployeeColLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        addEmployeeColGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        addEmployeeColPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        addEmployeeColPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        addEmployeeColDateMember.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        addEmployeeTableView.setItems(addEmployee);
    }
    
    public void addEmployeeSelect() {
        employeeData employeeD = addEmployeeTableView.getSelectionModel().getSelectedItem();
        int num = addEmployeeTableView.getSelectionModel().getSelectedIndex();
        
        if((num -1) < -1) {return;}
        
        addEmployeeEmployeeId.setText(String.valueOf(employeeD.getEmployeeId()));
        addEmployeeFirstName.setText(String.valueOf(employeeD.getFirstName()));
        addEmployeeLastName.setText(String.valueOf(employeeD.getLastName()));
        addEmployeePhoneNumber.setText(String.valueOf(employeeD.getPhoneNumber()));
        
        getData.path = employeeD.getImage();
        
        String uri = "file:" + employeeD.getImage();
        
        image = new Image(uri, 125, 117, false, true);
        addEmployeePhoto.setImage(image);
    }
    
    public void salaryUpdate() {
        
        String sql = "UPDATE employee_info SET salary = '" + salarySalary.getText()
                + "' WHERE employeeId = '" + salaryEmployeeId.getText() + "'";
        
        connect = database.connectDb();
        
        try {
            Alert alert;
            
            if(salaryEmployeeId.getText().isEmpty()
                    || salaryFirstName.getText().isEmpty()
                    || salaryLastName.getText().isEmpty()
                    || salaryPosition.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Massage");
                alert.setHeaderText(null);
                alert.setContentText("Please select item first");
                alert.showAndWait();
            } else {
                statement = connect.createStatement();
                statement.executeUpdate(sql);
                
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Massage");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Updated!");
                alert.showAndWait();
                
                salaryShowListData();
            }

        }catch(Exception e) {e.printStackTrace();}
    }
    
    public void salaryReset() {
        salaryEmployeeId.setText("");
        salaryFirstName.setText("");
        salaryLastName.setText("");
        salaryPosition.setText("");
        salarySalary.setText("");
    }
    
    public ObservableList<employeeData> salaryListData() {
        
        ObservableList<employeeData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM employee_info";
        
        connect = database.connectDb();
        
        try {
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            employeeData employeeD;
            
            while(result.next()) {
                employeeD = new employeeData(result.getInt("employeeId")
                        , result.getString("firstName")
                        , result.getString("lastName")
                        , result.getString("position")
                        , result.getDouble("salary"));
                
                listData.add(employeeD);
            }
        }catch(Exception e) {e.printStackTrace();}
        
        return listData;
    }
    
    public ObservableList<employeeData> salaryList;
    public void salaryShowListData() {
        salaryList = salaryListData();
        
        salaryColEmployeeID.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        salaryColFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        salaryColLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        salaryColPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        salaryColSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        
        salaryTableView.setItems(salaryList);
    }
    
    public void salarySelect() {
        
        employeeData employeeD = salaryTableView.getSelectionModel().getSelectedItem();
        int num = salaryTableView.getSelectionModel().getSelectedIndex();
        
        if(num-1 < -1) {
            return;
        }
        
        salaryEmployeeId.setText(String.valueOf(employeeD.getEmployeeId()));
        salaryFirstName.setText(employeeD.getFirstName());
        salaryLastName.setText(employeeD.getLastName());
        salaryPosition.setText(employeeD.getPosition());
        salarySalary.setText(String.valueOf(employeeD.getSalary()));
    }
    
    public void displayUsername() {
        username.setText(getData.username);
    }
    
    public void switchForm(ActionEvent event) {
        
        if(event.getSource() == homeBtn) {
            homeForm.setVisible(true);
            addEmployeeForm.setVisible(false);
            salaryForm.setVisible(false);
            
            homeBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #3b3d40, #788396)");
            addEmployeeBtn.setStyle("-fx-background-color: transparent");
            employeeSalaryBtn.setStyle("-fx-background-color: transparent");
            
            homeTotalEmployees();
            homeTotalPresents();
            homeTotalInactives();
            homeChart();
            
        } else if(event.getSource() == addEmployeeBtn) {
            homeForm.setVisible(false);
            addEmployeeForm.setVisible(true);
            salaryForm.setVisible(false);
            
            homeBtn.setStyle("-fx-background-color: transparent");
            addEmployeeBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #3b3d40, #788396)");
            employeeSalaryBtn.setStyle("-fx-background-color: transparent");
            
            addEmployeeGenderList();
            addEmployeePositionList();
            addEmployeeSearch();
            
        } else if(event.getSource() == employeeSalaryBtn) {
            homeForm.setVisible(false);
            addEmployeeForm.setVisible(false);
            salaryForm.setVisible(true);
            
            homeBtn.setStyle("-fx-background-color: transparent");
            addEmployeeBtn.setStyle("-fx-background-color: transparent");
            employeeSalaryBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #3b3d40, #788396)");
            
            salaryShowListData();
        }
    }
    
    public void logout() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Massage!");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");
        Optional<ButtonType> option = alert.showAndWait();
        
        try {
            if(option.get().equals(ButtonType.OK)) {
                logout.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("LoginFXML.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                
                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });
                
                stage.setScene(scene);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.show();
            }
        }catch(Exception e) {e.printStackTrace();}
    }
    
    public void close() {
        System.exit(0);
    }
    
    public void minimize() {
        Stage stage = (Stage)mainForm.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Date");
        yAxis.setLabel("Employees");

        homeEmployeesDataChartTable = new BarChart<>(xAxis, yAxis);
        homeEmployeesDataChartTable.setPrefSize(400, 300);

        AnchorPane.setTopAnchor(homeEmployeesDataChartTable, 0.0);
        AnchorPane.setBottomAnchor(homeEmployeesDataChartTable, 0.0);
        AnchorPane.setLeftAnchor(homeEmployeesDataChartTable, 0.0);
        AnchorPane.setRightAnchor(homeEmployeesDataChartTable, 0.0);

        homeEmployeesDataChartTable.prefWidthProperty()
                .bind(homeChartPane.widthProperty());
        homeEmployeesDataChartTable.prefHeightProperty()
                .bind(homeChartPane.heightProperty());

        homeChartPane.getChildren().add(homeEmployeesDataChartTable);

        homeTotalEmployees();
        homeTotalPresents();
        homeTotalInactives();
        homeChart();

        addEmployeeShowListData();
        addEmployeePositionList();
        addEmployeeGenderList();
        salaryShowListData();
    }
}