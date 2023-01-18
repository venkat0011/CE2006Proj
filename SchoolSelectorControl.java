package com.example.ce2006proj;

public class SchoolSelectorControl
{
    public static Student getStudent()
    {
        return StudentDatabase.student;
    }
    public static void setStudent(Student student )
    {
        StudentDatabase.student = student;
    }

}
