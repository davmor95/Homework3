package core.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import core.api.IAdmin;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Student;


public class TestAdmin {

	 private IAdmin admin;
	 private IStudent student;

	    @Before
	    public void setup() {
	        this.admin = new Admin();
	        this.student = new Student();
	    }
	    
	    @Test
	    public void testMakeClass() {
	    	  this.admin.createClass("ECS 161", 2017, "Devanbu", 15);
	      assertTrue(this.admin.classExists("ECS 161", 2017));
	    }
	    
	    @Test
	    public void No_more_than_two() {
	        this.admin.createClass("Astrology", 2017, "Sheryl", 10);
	        this.admin.createClass("Astronomy", 2017, "Sheryl", 25);
	        this.admin.createClass("Physics 9a", 2017, "Sheryl", 45);
	        assertFalse(this.admin.classExists("Physics 9a", 2017));
	    }
	    
	    @Test
	    public void Professor_teaching_two() {
	        this.admin.createClass("Astrology", 2017, "Sheryl", 10);
	        this.admin.createClass("Astronomy", 2017, "Sheryl", 25);
	        assertTrue(this.admin.classExists("Astronomy", 2017));
	    }
	    
	    	    
	    @Test
	    public void check_change_capacity() {
	        this.admin.createClass("Astrology", 2017, "Sheryl", 12);
	        this.admin.changeCapacity("Astrology", 2017, 24);
	        assertEquals(this.admin.getClassCapacity("Astrology", 2017), 24);
	    
	    }
	    @Test
	    public void check_Year_for_change_capacity() {
	    	    this.admin.createClass("Astrology", 2016, "Sheryl", 12);
	        this.admin.createClass("Astrology", 2017, "Sheryl", 12);
	        this.admin.changeCapacity("Astrology", 2017, 24);
	        assertEquals(this.admin.getClassCapacity("Astrology", 2016), 24);
	    
	    }
	    @Test
	    public void check_Year_for_change_capacity2() {
	    	    this.admin.createClass("Astrology", 2016, "Sheryl", 12);
	        this.admin.createClass("Astrology", 2017, "Sheryl", 12);
	        this.admin.changeCapacity("Astrology", 2017, 24);
	        assertEquals(this.admin.getClassCapacity("Astrology", 2017), 24);
	    
	    }

	    
	    @Test
	    public void capacity_shouldnt_be_negative() {
	        this.admin.createClass("Astrology", 2017, "Sheryl", 12);
	        this.admin.changeCapacity("Astrology", 2017, -1);
	        assertTrue(this.admin.getClassCapacity("Astrology", 2017) >= 0);
	    
	    }
	    
	    @Test
	    public void class_with_no_instructor() {
	        this.admin.createClass("Class1", 2017, "", 12);
	        assertEquals(this.admin.getClassInstructor("Class1", 2017), "");
	    
	    }
	    
	    @Test
	    public void class_with_no_coursename() {
	        this.admin.createClass("", 2017, "Sheryl", 12);
	        assertFalse(this.admin.classExists("", 2017));
	    
	    }
	    
	    @Test
	    public void change_capacity_of_class_than_number_of_students_enrolled() {
	        this.admin.createClass("Astrology", 2017, "Sheryl", 2);
	        this.student.registerForClass("Bob", "Astrology", 2017);
	        this.student.registerForClass("Bill", "Astrology", 2017);
	        this.admin.changeCapacity("Astrology", 2017, 1);
	        assertFalse(this.admin.getClassCapacity("Astrology", 2017) <= 2);
	    
	    }
	    
	    @Test
	    public void course_in_past() {
	    		this.admin.createClass("ECS122B", 2017, "Rob Gysel", 24);
	        this.admin.createClass("Astrology", 1985, "Sheryl", 24);
	        assertFalse(this.admin.classExists("Astrology", 1985));
	    }
	    
	    @Test
	    public void identical_class_different_instructor() {
	    		this.admin.createClass("ECS122B", 2017, "Rob Gysel", 24);
	        this.admin.createClass("ECS122B", 2017, "Sheryl", 24);
	        assertFalse(!(this.admin.classExists("ECS122B", 2017)));
	    }
	    
}
