package pl.sda.arppl4.hibernateschool;

import pl.sda.arppl4.hibernateschool.dao.GenericDao;
import pl.sda.arppl4.hibernateschool.model.Student;
import pl.sda.arppl4.hibernateschool.parser.StudentCommandLineParser;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        GenericDao<Student> genericStudentDao = new GenericDao<>();


        StudentCommandLineParser parser = new StudentCommandLineParser(scanner, genericStudentDao);
        parser.hellYeah();

    }
}
