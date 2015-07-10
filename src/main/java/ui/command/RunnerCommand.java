package ui.command;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import exceptions.RunnerCommandException;
import businessModel.MicroController;

public class RunnerCommand {

	private static MicroController micro = new MicroController();

	public static void main(String[] args) {
		try {
			checkArguments(args);
			String fileProgram = new String(Files.readAllBytes(Paths
					.get(args[0])), StandardCharsets.UTF_8);
			micro.load(new ProgramParser().parse(fileProgram));
			micro.start();
			micro.execute();
			System.out.println("Register A: "
					+ micro.getRegister("A").getValue().toString());
			System.out.println("Register B: "
					+ micro.getRegister("B").getValue().toString());
			System.out.println("Program Finished Correctly");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void checkArguments(String[] args) {
		Integer amount = args.length;
		if(amount == 0)
			throw new RunnerCommandException("Feed me with arguments!");
		checkFlags(args);
		checkFileExistence(args[args.length - 1]);

	}

	private static void checkFlags(String[] args) {
		
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
