/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.framwork.dao.generator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nelson
 */
public class StringUtilsTest {
    
    public StringUtilsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of toFirstUpcase method, of class StringUtils.
     */
    @Test
    public void testToFirstUpcase() {
        System.out.println("toFirstUpcase");
        String str = "O";
        String expResult = "";
 
        str = "hellowowrd{helow}{helow}";
        System.out.println(str.replace("{helow}", "hellow"));
    }
}