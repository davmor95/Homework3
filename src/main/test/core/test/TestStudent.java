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

public class TestStudent {

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
	public void student_added_and_drop_the_class() {
		this.admin.createClass("Astrology", 2017, "Sheryl", 24);
		this.student.registerForClass("Bob", "Astrology", 2017);
		this.student.dropClass("Bob", "Astrology", 2017);
		assertFalse(this.student.isRegisteredFor("Bob", "Astrology", 2017));
	}
	
	@Test
	public void student_should_not_submit_hw_after_drop() {
		this.admin.createClass("Astrology", 2017, "Sheryl", 24);
		this.student.registerForClass("Bob", "Astrology", 2017);
		this.instructor.addHomework("Sheryl", "Astrology", 2017, "Stars and Universe");
		this.student.dropClass("Bob", "Astrology", 2017);
		this.student.submitHomework("Bob", "Stars and Universe", "Big Bang", "Astrology", 2017);
		assertFalse(this.student.hasSubmitted("Bob", "Stars and Universe", "Astrology", 2017));
	}
	
	@Test
	public void student_register_when_class_full() {
		this.admin.createClass("Astrology", 2017, "Sheryl", 1);
		this.student.registerForClass("Bob", "Astrology", 2017);
		this.student.registerForClass("Jill", "Astrology", 2017);
		assertFalse(this.student.isRegisteredFor("Jill", "Astrology", 2017));
	}
	
	@Test
	public void student_register_and_forgot_to_but_their_name() {
		this.admin.createClass("Astrology", 2017, "Sheryl", 1);
		this.student.registerForClass("", "Astrology", 2017);
		assertFalse(this.student.isRegisteredFor("", "Astrology", 2017));
	}
	
	@Test
	public void student_register_for_a_class_from_a_different_year() {
		this.admin.createClass("Astrology", 2016, "Sheryl", 1);
		this.admin.createClass("Astrology", 2017, "Sheryl", 1);
		this.student.registerForClass("Bob", "Astrology", 2016);
		assertFalse(this.student.isRegisteredFor("Bob", "Astrology", 2016));
	}
	
	@Test
	public void student_submittshw_for_a_class_from_a_different_year() {
		this.admin.createClass("Astrology", 2016, "Sheryl", 1);
		this.admin.createClass("Astrology", 2017, "Sheryl", 1);
		this.student.registerForClass("Bob", "Astrology", 2017);
		this.instructor.addHomework("Sheryl", "Astrology", 2017, "Stars and Universe");
		this.student.submitHomework("Bob", "Stars and Universe", "Big Bang", "Astrology", 2016);
		assertFalse(this.student.hasSubmitted("Bob", "Stars and Universe", "Astrology", 2016));
	}
}
