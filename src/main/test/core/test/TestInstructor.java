package core.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import core.api.IAdmin;
import core.api.IInstructor;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Instructor;
import core.api.impl.Student;


public class TestInstructor {

	private IAdmin admin;
	private IInstructor instructor;
	private IStudent student;
    @Before
    public void setup() {
        this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();
    }
	
	@Test
	public void assert_false_for_anyone_else_adding_hw() {
		this.admin.createClass("Astrology", 2017, "Sheryl", 24);
		this.instructor.addHomework("Bob", "Astrology", 2017, "Stars and Universe");
		assertFalse(this.instructor.homeworkExists("Astrology", 2017, "Stars and Universe"));
	}
	
	@Test
	public void calling_addhw_without_assigning_hw() {
		this.admin.createClass("Astrology", 2017, "Sheryl", 24);
		this.instructor.addHomework("Sheryl", "Astrology", 2017, "");
		assertFalse(this.instructor.homeworkExists("Astrology", 2017, ""));
	}

	@Test
	public void adding_hw_with_no_instructor_name() {
		this.admin.createClass("Astrology", 2017, "Sheryl", 24);
		this.instructor.addHomework("", "Astrology", 2017, "Stars and Universe");
		assertFalse(this.instructor.homeworkExists("Astrology", 2017, "Stars and Universe"));
	}
	
	
	@Test
	public void cant_assign_negative_grade() {
		this.admin.createClass("Astrology", 2017, "Sheryl", 24);
		this.student.registerForClass("Bob", "Astrology", 2017);
		this.instructor.addHomework("Sheryl", "Astrology", 2017, "Stars and Universe");
		this.instructor.assignGrade("Sheryl", "Astrology", 2017, "Stars and Universe", "Bob", -1);
		assertTrue(this.instructor.getGrade("Astrology", 2017, "Stars and Universe", "Bob") >= 0);
	}
	@Test
	public void assign_a_grade_to_a_student_when_not_registered() {
		this.admin.createClass("Astrology", 2017, "Sheryl", 2);
		this.student.registerForClass("Jack", "Astrology", 2017);
		this.student.registerForClass("Jill", "Astrology", 2017);
		this.instructor.addHomework("Sheryl", "Astrology", 2017, "Stars and Universe");
		this.student.hasSubmitted("Jack", "Stars and Universe", "Astrology", 2017);
		this.student.hasSubmitted("Jill", "Stars and Universe", "Astrology", 2017);
		this.instructor.assignGrade("Sheryl", "Astrology", 2017, "Stars and Universe", "Bob", 99);
		assertNull(this.instructor.getGrade("Astrology", 2017, "Stars and Universe", "Bob"));
	}
	
	@Test
	public void assign_a_grade_to_a_student_who_is_in_a_class() {
		this.admin.createClass("Astrology", 2017, "Sheryl", 2);
		this.student.registerForClass("Jack", "Astrology", 2017);
		this.instructor.addHomework("Sheryl", "Astrology", 2017, "Stars and Universe");
		this.student.hasSubmitted("Jack", "Stars and Universe", "Astrology", 2017);
		this.instructor.assignGrade("Sheryl", "Astrology", 2017, "Stars and Universe", "Jack", 99);
		assertTrue(this.instructor.getGrade("Astrology", 2017, "Stars and Universe", "Jack") >= 0);
	}
	@Test
	public void different_teacher_assign_a_grade_to_a_student_who_is_in_a_class() {
		this.admin.createClass("Astrology", 2017, "Sheryl", 2);
		this.student.registerForClass("Jack", "Astrology", 2017);
		this.instructor.addHomework("Sheryl", "Astrology", 2017, "Stars and Universe");
		this.student.hasSubmitted("Jack", "Stars and Universe", "Astrology", 2017);
		this.instructor.assignGrade("Sean", "Astrology", 2017, "Stars and Universe", "Jack", 99);
		assertFalse(this.instructor.getGrade("Astrology", 2017, "Stars and Universe", "Jack") >= 0);
	}
	
	@Test
	public void test_for_extra_credit() {
		this.admin.createClass("Astrology", 2017, "Sheryl", 2);
		this.student.registerForClass("Jack", "Astrology", 2017);
		this.instructor.addHomework("Sheryl", "Astrology", 2017, "Stars and Universe");
		this.student.hasSubmitted("Jack", "Stars and Universe", "Astrology", 2017);
		this.instructor.assignGrade("Sean", "Astrology", 2017, "Stars and Universe", "Jack", 103);
		assertTrue(this.instructor.getGrade("Astrology", 2017, "Stars and Universe", "Jack") >= 100);
	}
	
	@Test
	public void test_for_grade_between_0_and_100() {
		this.admin.createClass("Astrology", 2017, "Sheryl", 2);
		this.student.registerForClass("Jack", "Astrology", 2017);
		this.instructor.addHomework("Sheryl", "Astrology", 2017, "Stars and Universe");
		this.student.hasSubmitted("Jack", "Stars and Universe", "Astrology", 2017);
		this.instructor.assignGrade("Sean", "Astrology", 2017, "Stars and Universe", "Jack", 100);
		int grade = this.instructor.getGrade("Astrology", 2017, "Stars and Universe", "Jack");
		assertTrue(grade <= 100 & grade >= 0);
	}
}
