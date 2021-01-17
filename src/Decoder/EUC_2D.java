package Decoder;

import Utils.Problem;
import java.io.BufferedReader;

public class EUC_2D {
	
	public static void Decoder_EUC_2D(BufferedReader reader, Problem problem) {
		try {
			String line = null;
			boolean read = false;
			float x[] = new float[problem.getDimension()];
			float y[] = new float[problem.getDimension()];
			int i = 0;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("NODE_COORD_SECTION")) {
					read = true;
				} else if (line.startsWith("EOF")) {
					break;
				} else if (read) {
					line = line.trim();
					String[] cadArray = line.split("[ \t]+");
					Float k = Float.valueOf(cadArray[1]);
					x[i] = k.floatValue();
					k = Float.valueOf(cadArray[2]);
					y[i++] = k.floatValue();
				}	
			}
			reader.close();
			int matrix[][] = new int[problem.getDimension()][problem.getDimension()];
			for (i = 0; i < problem.getDimension(); i++) { 
				for (int j = 0; j < problem.getDimension(); j++) {
					float xd = x[i] - x[j];
					float yd = y[i] - y[j];
					matrix[i][j] = (int) Math.round(Math.sqrt(xd * xd + yd * yd));
				}
			}
			problem.setMatrix_problem(matrix);
		}catch(Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Error of reading");			
		}
	}
}
