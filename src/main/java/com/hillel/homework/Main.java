package com.hillel.homework;

import com.hillel.homework.dao.StudentDao;
import com.hillel.homework.entity.Homework;
import com.hillel.homework.entity.Student;
import com.hillel.homework.util.HibernateUtil;

import java.time.LocalDate;


public class Main {

    public static void main(String[] args) {
        StudentDao studentDao = new StudentDao();


        Student student = new Student("Іван", "Петренко", "ivan.petrenko@example.com");
        Homework homework = new Homework("Домашнє завдання №1", LocalDate.now().plusDays(7), 0);
        student.addHomework(homework);

        studentDao.save(student);
        System.out.println("Збережено: " + student);


        Student found = studentDao.findById(student.getId());
        System.out.println("Знайдено за id: " + found);

        Student foundByEmail = studentDao.findByEmail("ivan.petrenko@example.com");
        System.out.println("Знайдено за email: " + foundByEmail);

        System.out.println("Усі студенти: " + studentDao.findAll());


        found.setLastName("Коваленко");
        Student updated = studentDao.update(found);
        System.out.println("Оновлено: " + updated);


        boolean deleted = studentDao.deleteById(updated.getId());
        System.out.println("Видалено: " + deleted);

        HibernateUtil.close();
    }
}
