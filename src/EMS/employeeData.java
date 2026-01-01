package EMS;

import java.sql.Date;

public class employeeData {
    
    private Integer employeeId;
    private String firstName;
    private String lastName;
    private String gender;
    private String phoneNumber;
    private String position;
    private String image;
    private Date date;
    private Double salary;
    
    public employeeData(Integer employeeId, String firstName, String lastName, String gender, String phoneNumber, String position,
            String image, Date date) {
        
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.image = image;
        this.date = date;
    }

    public employeeData (Integer employeeId, String firstName, String lastName, String position, Double salary) {
        
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.salary = salary;
    }
    
    public Integer getEmployeeId() {
        return employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPosition() {
        return position;
    }

    public String getImage() {
        return image;
    }

    public Date getDate() {
        return date;
    }
    
    public Double getSalary() {
        return salary;
    }
}
