package emg.demo.cli;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ShellExec {

	private final Logger logger = LogManager.getLogger(ShellExec.class);

	public String[] searchFile(String path, String fileExpression) throws IOException {
		// find . -type f -iname \'*.java\'
		CommandLine commandLine = new CommandLine("find");
		commandLine.addArgument(path);
		commandLine.addArgument("-type");
		commandLine.addArgument("f");
		commandLine.addArgument("-iname");
		commandLine.addArgument(fileExpression);

		return execute(commandLine);
	}

	public String[] searchInsideFiles(String path, String fileExpression, String fileContents) throws IOException {
		CommandLine commandLine = new CommandLine("grep");
		if (fileExpression != null) {
			commandLine.addArgument("--include");
			commandLine.addArgument(fileExpression);
		}
		commandLine.addArgument("-rnw");
		commandLine.addArgument(path);
		commandLine.addArgument("-e");
		commandLine.addArgument(fileContents);

		System.out.println(commandLine.toString());
		return execute(commandLine);
	}

	private String[] execute(CommandLine commandLine) {
		int iExitValue = 0;
		DefaultExecutor defaultExecutor = new DefaultExecutor();
		defaultExecutor.setExitValue(0);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PumpStreamHandler streamHandler = new PumpStreamHandler(baos);
		defaultExecutor.setStreamHandler(streamHandler);

		try {
			iExitValue = defaultExecutor.execute(commandLine);
			String result = baos.toString();
			baos.close();

			String[] items = result.split("\n");
			return items;
		} catch (ExecuteException e) {
			logger.error("Execution failed: " + e.getMessage());
		} catch (IOException e) {
			logger.error(e);
		}
		return null;
	}
}
