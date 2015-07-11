package ui.command;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import exceptions.RunnerCommandException;
import businessModel.MicroController;

public class RunnerCommand {

	private static MicroController micro = new MicroController();

	public static void main(String[] args) {
		checkArguments(args);
		String textProgram = getFileContent(args);
		micro.load(new ProgramParser().parse(textProgram));
		micro.start();
		micro.execute();
		System.out.println("Register A: "
				+ micro.getRegister("A").getValue().toString());
		System.out.println("Register B: "
				+ micro.getRegister("B").getValue().toString());
		System.out.println("Program Finished Correctly");
	}

	private static String getFileContent(String[] args) {
		byte[] readInfo = null;
		Integer progArg = getProgramArgumentIndex(args);
		try {
			readInfo = Files.readAllBytes(Paths.get(args[progArg]));
		} catch (IOException e) {
			throw new RunnerCommandException("Error Reading Program: "
					+ e.getMessage());
		}
		return new String(readInfo, StandardCharsets.UTF_8);

	}

	private static Integer getProgramArgumentIndex(String[] args) {
		if (args.length == 0)
			throw new RunnerCommandException("Feed me with arguments!");
		return args.length - 1;
	}

	public static void checkArguments(String[] args) {
		checkFlags(args);
		checkFileExistence(args[getProgramArgumentIndex(args)]);
	}

	private static void checkFlags(String[] args) {
		List<String> arguments = Arrays.asList(args);
		// TODO: Replace with Command
	}

	private static void checkFileExistence(String path) {
		File file = new File(path);
		if (!file.exists()) {
			throw new RunnerCommandException("File does not exist.");
		}
	}

	public static MicroController getMicro() {
		return micro;
	}

}
