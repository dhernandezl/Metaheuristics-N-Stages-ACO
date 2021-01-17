package Decoder;

import java.io.BufferedReader;

import Utils.Problem;

public class LOWER_DIAG_ROW {

	public static void Decoder_LOWER_DIAG_ROW(BufferedReader reader, Problem problem) {
		try {
			String line = null;
			boolean read = false;
			int row = 0;
			int column = 0;
			int[][] matrix = new int[problem.getDimension()][problem.getDimension()];
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("EDGE_WEIGHT_SECTION")) {
					read = true;
				} else if (line.startsWith("EOF")) {
					break;
				} else if (read) {
					line = line.trim();
					String[] tokens = line.split("\\s");
					for (int i = 0; i < tokens.length; i++) {
						String v = tokens[i].trim();
						if (v.length() > 0) {
							int value = Integer.parseInt(tokens[i].trim());
							matrix[row][column] = value;
							column++;
							if (value == 0) {
								row++;
								column = 0;
							}
						}
					}
				}
			}
			reader.close();
			for (int i = 0; i < problem.getDimension(); i++) {
				for (int j = (i + 1); j <  problem.getDimension(); j++) {
					matrix[i][j] = matrix[j][i];
				}
			}
			problem.setMatrix_problem(matrix);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Error of reading");
		}
	}
}
