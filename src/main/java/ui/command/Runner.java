package ui.command;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import businessModel.MicroController;

public class Runner {

	public static void main(String[] args) {
		try {
			String fileProgram = new String(Files.readAllBytes(Paths
					.get(args[0])), StandardCharsets.UTF_8);
			MicroController micro = new MicroController();
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

}
