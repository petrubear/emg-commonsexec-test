package emg.demo.cli;

import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class App {
	private static final boolean searchFiles = false;
	private static final Logger logger = LogManager.getLogger(App.class);

	public static void main(String[] args) {
		ShellExec exec = new ShellExec();
		try {
			BasicConfigurator.configure();
			String[] result = null;
			if (searchFiles) {
				result = exec.searchFile(".", "*.java");
			} else {
				result = exec.searchInsideFiles(".", "*.java", "LogManager");
			}
			if (result == null) {
				logger.error("result is null");
				return;
			}
			for (String item : result) {
				logger.info(item);
			}
		} catch (IOException e) {
			logger.error(e);
		}
	}
}
