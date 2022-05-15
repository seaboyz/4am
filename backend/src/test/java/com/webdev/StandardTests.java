package com.webdev;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StandardTests {

    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
    }

    @BeforeEach
    public void setUp() throws Exception {
    }

    @Test
    void successTest() {
        assert (true);
    }

    // @Test
    // public void test() {
    // fail("Not yet implemented");
    // }

    // @Test
    // @Disabled("for demonstration purposes")
    // void skippedTest() {
    // // not executed
    // }

    @AfterEach
    public void tearDown() throws Exception {
    }

    @AfterAll
    public static void tearDownAfterClass() throws Exception {
    }

}
