package com.example.ce2006proj;

import java.util.ArrayList;
import java.util.Random;

public class User
{
    private String Username;
    private String Password;
    // when storing password please store the password as an encrypted mode
    private ArrayList<Student> Children_List = new ArrayList<>();

    public ArrayList<Student> getChildren_List() {
        return Children_List;
    }

    public void setChildren_List(ArrayList<Student> children_List) {
        Children_List = children_List;
    }

    public String getPassword() {
        return Password;
    }

    public String getUsername() {
        return Username;
    }

    public void setPassword(String password)
    {
        // use the algo to mask the password
        Password = password;
    }

    public void setUsername(String username) {
        Username = username;
    }



    public User(String Username, String Password)
    {
        this.Username = Username;
        this.setPassword(Password);

    };
    public User (String username,String password,ArrayList<Student> students)
    {
        this.Username = username;
        this.Password = password;
        this.Children_List = students;
    }
    public User() {};



}
