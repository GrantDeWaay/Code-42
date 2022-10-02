package coms309.controller.login;

public class UserLogin {
    /*
        Names of variables copied directly from database
        However confused on how spaces are handled
     */
    long ID;
    private String Account_Type;
    private String Created; // Time account was created
    private String Email;
    private String First_Name;
    private String Last_Name;
    private String Password;
    private String Username;

    public UserLogin(long ID, String Username, String First_Name, String Last_Name, String Password, String Email, String Account_Type, String Created) {
        this.ID = ID;
        this.Username = Username;
        this.First_Name = First_Name;
        this.Last_Name = Last_Name;
        this.Password = Password;
        this.Email = Email;
        this.Account_Type = Account_Type;
        this.Created = Created;
    }

    public UserLogin() {
        // Default constructor for testing >:D
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getAccount_Type() {
        return Account_Type;
    }

    public void setAccount_Type(String account_Type) {
        Account_Type = account_Type;
    }

    public String getCreated() {
        return Created;
    }

    public void setCreated(String created) {
        Created = created;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public void setFirst_Name(String first_Name) {
        First_Name = first_Name;
    }

    public String getLast_Name() {
        return Last_Name;
    }

    public void setLast_Name(String last_Name) {
        Last_Name = last_Name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

}
