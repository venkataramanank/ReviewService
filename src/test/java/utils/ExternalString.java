package utils;

import org.apache.commons.io.IOUtils;
import org.junit.rules.ExternalResource;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * JUnit rule to read files into strings
 */
public final class ExternalString extends ExternalResource implements CharSequence {

    private final String filename;
    private Class<?> testClass;
    private String text;

    public ExternalString(String filename) {
        this.filename = filename;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        testClass = description.getTestClass();
        return super.apply(base, description);
    }

    @Override
    protected void before() throws Throwable {
        InputStream resourceAsStream = testClass.getResourceAsStream(filename);
        text = IOUtils.toString(resourceAsStream);
    }

    @Override
    public int length() {
        return text.length();
    }

    @Override
    public char charAt(int index) {
        return text.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return text.subSequence(start, end);
    }

    @Override
    public String toString() {
        return text;
    }

    public InputStream toInputStream() {
        return new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
    }
}
