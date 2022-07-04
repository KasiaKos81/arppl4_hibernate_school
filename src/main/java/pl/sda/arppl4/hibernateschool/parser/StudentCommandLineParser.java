package pl.sda.arppl4.hibernateschool.parser;

import pl.sda.arppl4.hibernateschool.dao.GenericDao;
import pl.sda.arppl4.hibernateschool.model.Mark;
import pl.sda.arppl4.hibernateschool.model.Student;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class StudentCommandLineParser {

    private final Scanner scanner;
    private GenericDao<Student> studentDao;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public StudentCommandLineParser(Scanner scanner, GenericDao<Student> studentDao) {
        this.scanner = scanner;
        this.studentDao = studentDao;
    }

    public void hellYeah() {
        String command;

        do {

            System.out.println("Please giva a commend: add/delete/update/getAll or type: 'end' to finish");
            command = scanner.next();

            switch (command) {
                case "add":
                    handleAddCommand();
                    break;
                case "getAll":
                    handleGetAllCommand();
                    break;
                case "update":
                    handleUpdateCommand();
                    break;
                case "delete":
                    handleDeleteCommand();
                    break;

            }

        } while (!command.equalsIgnoreCase("end"));
    }

    private void handleDeleteCommand() {
        System.out.println("Provide the id of the student you want to delete");
        Long id = scanner.nextLong();

        Optional<Student> studentOptional = studentDao.getOneStudent(id, Student.class);
        if (studentOptional.isPresent()) {
            Student studentToDelete = studentOptional.get();
            Set<Mark> setOfMarks = studentOptional.get().getMarks();
            if (setOfMarks.isEmpty()) {
                System.out.println("Student cannot be deleted");
            } else
                studentDao.deleteStudent(studentToDelete);
            System.out.println("Student is deleted from database");

        } else
            System.out.println("404 student with given id not found");
    }

        private void handleUpdateCommand () {
            System.out.println("Provide the id of the student you want to update");
            Long id = scanner.nextLong();

            Optional<Student> studentOptional = studentDao.getOneStudent(id, Student.class);
            if (studentOptional.isPresent()) {
                Student studencik = studentOptional.get();

                System.out.println("Tell what you want to change: name or surname");
                String change = scanner.next();

                switch (change) {
                    case "name":
                        System.out.println("provide name");
                        String name = scanner.next();
                        studencik.setName(name);
                        break;
                    case "surname":
                        System.out.println("provide surname");
                        String surname = scanner.next();
                        studencik.setSurname(surname);
                        break;
                    default:
                        System.out.println("this field cannot be updated");
                }
                studentDao.updateStudent(studencik);
                System.out.println("Object has been updated");
            } else {
                System.out.println("404 object not found");
            }
        }

        private void handleGetAllCommand () {
            List<Student> students = studentDao.getAllStudents(Student.class);
            for (Student studenciki : students) {
                System.out.println("Lista studentów " + studenciki);
            }

        }

        private void handleAddCommand () {
            System.out.println("Give name:");
            String name = scanner.next();

            System.out.println("Give surname:");
            String surname = scanner.next();

            System.out.println("Give index number");
            String indexNo = scanner.next();

            LocalDate birthDate = loadBirthDateFromUser();

            Student student = new Student(name, surname, indexNo, birthDate);
            studentDao.addStudent(student);

        }

        private LocalDate loadBirthDateFromUser () {
            LocalDate birthDate = null;
            do {
                try {
                    System.out.println("Please, provide your birth date:");
                    String birthDateString = scanner.next();
                    //       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    birthDate = LocalDate.parse(birthDateString, FORMATTER);

                    LocalDate today = LocalDate.now();
                    if (birthDate.isAfter(today)) {

                        throw new IllegalArgumentException("Date is after today");
                    }

                } catch (DateTimeException iae) {
                    birthDate = null; // żeby wyczyściło jak ktoś wpisał złą date
                    System.err.println("Wrong date formatt, should be yyyy-MM-dd");
                } catch (IllegalArgumentException iae) {
                    birthDate = null;
                    System.err.println("Date is after today");
                }
            } while (birthDate == null);

            return birthDate;
        }
    }
