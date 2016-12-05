package org.fundacionjala.sonarqube.parser;

import com.sonar.sslr.api.typed.ActionParser;
import org.fundacionjala.sonarqube.apex.ApexAstScanner;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.Charset;
import java.util.Calendar;

import static org.junit.Assert.*;

public class ApexParserTest {

    @Before
    public void setUp() {

    }

    @Test
    public void testCreateParserNotNull() {
        ActionParser parser = ApexParser.createParser(Charset.defaultCharset());
        assertNotNull(parser);
    }
}