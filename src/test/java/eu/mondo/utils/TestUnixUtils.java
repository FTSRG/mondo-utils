package eu.mondo.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.environment.EnvironmentUtils;
import org.junit.Test;

public class TestUnixUtils {

	@Test
	public void test() throws FileNotFoundException, IOException {
		UnixUtils.exec("ls /", Collections.<String, String> emptyMap(), true, System.out);
	}

	@Test(expected = ExecuteException.class)
	public void testFail() throws FileNotFoundException, IOException {
		UnixUtils.exec("ls :", Collections.<String, String> emptyMap(), true, System.out);
	}

	@Test(expected = ExecuteException.class)
	public void testNoSuchScript() throws FileNotFoundException, IOException {
		UnixUtils.exec("./foo", Collections.<String, String> emptyMap(), true, System.out);
	}

	@Test
	public void testEnvironmentVariables() throws IOException {
		final Map<?, ?> executionEnvironment = EnvironmentUtils.getProcEnvironment();
		EnvironmentUtils.addVariableToEnvironment(executionEnvironment, "HELLO=world");

		final CommandLine commandLine = new CommandLine("/bin/bash");
		commandLine.addArguments(new String[] { "-c", "echo $HELLO" }, false);
		new DefaultExecutor().execute(commandLine);
	}

}
