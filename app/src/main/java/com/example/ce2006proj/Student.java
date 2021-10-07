package com.example.ce2006proj;

public class Student
{
    private String name;
    private String age;
    private String citizenship;
    private String Location;

    public String getAge() {
        return age;
    }

    public String getCitizenship() {
        return citizenship;
    }


    public String getName() {
        return name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getLocation() {
        return Location;
    }
    public Student (String name, String location, String age, String citizenship)
    {
        this.name = name;
        this.Location = location;
        this.age = age;
        this.citizenship = citizenship;
    }
    public Student() {}
}
