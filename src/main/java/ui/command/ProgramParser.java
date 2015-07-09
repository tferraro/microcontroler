package ui.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import exceptions.ProgramParserException;
import businessModel.Program;
import businessModel.ProgramBuilder;

public class ProgramParser {

	private Hashtable<String, Method> methodTable = new Hashtable<String, Method>();
	private Class<?> builderClass;

	public ProgramParser() {
		try {
			builderClass = Class.forName("businessModel.ProgramBuilder");
			methodTable.put("ADD", builderClass.getDeclaredMethod("add"));
			methodTable.put("DIV", builderClass.getDeclaredMethod("div"));
			methodTable.put("HALT", builderClass.getDeclaredMethod("halt"));
			methodTable.put("NOP", builderClass.getDeclaredMethod("nop"));
			methodTable.put("PRNT", builderClass.getDeclaredMethod("prnt"));
			methodTable.put("SUB", builderClass.getDeclaredMethod("sub"));
			methodTable.put("SWAP", builderClass.getDeclaredMethod("swap"));
			methodTable.put("IFNZ",
					builderClass.getDeclaredMethod("ifnz", Integer.class));
			methodTable.put("LOD",
					builderClass.getDeclaredMethod("lod", Integer.class));
			methodTable.put("LODV",
					builderClass.getDeclaredMethod("lodv", Integer.class));
			methodTable.put("STR",
					builderClass.getDeclaredMethod("str", Integer.class));
			methodTable.put("WHNZ",
					builderClass.getDeclaredMethod("whnz", Integer.class));
		} catch (ClassNotFoundException | NoSuchMethodException
				| SecurityException e) {
			throw new ProgramParserException(
					"Error during method Table Creation: " + e.getMessage());
		}

	}

	public Program parse(String program) {
		ProgramBuilder builder = new ProgramBuilder();
		mapStrings(program).forEach(instr -> transformMethods(instr, builder));
		return builder.build();
	}

	private void transformMethods(List<String> instr, ProgramBuilder builder) {
		Method method = methodTable.get(instr.get(0).toUpperCase());
		if (method == null)
			throw new ProgramParserException("Wrong sintax on Instruction: "
					+ instr.get(0));
		try {
			if (instr.isEmpty())
				method.invoke(builder);
			else
				method.invoke(builder, convertToCorrectType(instr));
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new ProgramParserException("Semantic Error: " + e.getMessage());
		}

	}

	private Object[] convertToCorrectType(List<String> instr) {
		List<Integer> intParameters = new LinkedList<Integer>();
		instr.remove(0);
		instr.forEach(instrString -> intParameters.add(Integer
				.parseInt(instrString)));
		return intParameters.toArray();
	}

	private List<List<String>> mapStrings(String program) {
		return Arrays
				.asList(program.split("\n"))
				.stream()
				.map(inst -> new LinkedList<String>(Arrays.asList(inst
						.split(" ")))).collect(Collectors.toList());
	}

}
