package com.example.security.security;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class HeaderTokenExtractorTest {
    private HeaderTokenExtractor extractor = new HeaderTokenExtractor();
    private String header;

    @Before
    public void setUp() {
        this.header = "Bearer ABCD.EFGB.ASDF";
    }

    @Test
    public void TEST_JWT_EXTRACT() {
        assertThat(extractor.extract(this.header), is("ABCD.EFGB.ASDF"));
    }
}