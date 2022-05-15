package com.webdev;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class StandardTests {

  @BeforeAll
  public static void init() throws Exception {
  }

  @BeforeEach
  public void setUp() throws Exception {
  }

  @AfterEach
  public void tearDown() throws Exception {
  }

  @AfterAll
  void destory() {
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

}
