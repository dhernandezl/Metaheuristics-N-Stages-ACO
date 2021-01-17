package Decoder;

import java.io.BufferedReader;

import Utils.Problem;

public class GEO {

	public static void Decoder_GEO(BufferedReader reader, Problem problem) {
		try {
			String line = null;
			boolean read = false;
			double x[] = new double[problem.getDimension()];
			double y[] = new double[problem.getDimension()];
			double[] latitude = new double[problem.getDimension()];
			double[] longitude = new double[problem.getDimension()];
			int i = 0;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("NODE_COORD_SECTION")) {
					read = true;
				} else if (line.startsWith("EOF")) {
					break;
				} else if (read) {
					line = line.trim();
					String[] cadArray = line.split("[ \t]+");
					x[i] = Double.valueOf(cadArray[1]);
					y[i++] = Double.valueOf(cadArray[2]);
				}	
			}
			reader.close();
			
			int deg = 0;
			double min = 0;
			for(int j = 0; j < problem.getDimension(); j++) {
				deg = (int) Math.round(x[j]);
				min = x[j] - deg;
				latitude[j] = Math.PI * (deg + 5.0 * min / 3.0)/180.0;
				
				deg = (int) Math.round(y[j]);
				min = y[j] - deg;
				longitude[j] = Math.PI * (deg + 5.0 * min / 3.0)/180.0;
			}
			
			int[][] matrix = new int[problem.getDimension()][problem.getDimension()];
			double RRR = 6378.388;
			
			for(int k = 0; k < problem.getDimension(); k++) {
				for(int j = 0; j < problem.getDimension(); j++) {
					double q1 = Math.cos( longitude[k] - longitude[j] );
					double q2 = Math.cos( latitude[k] - latitude[j] );
					double q3 = Math.cos( latitude[k] + latitude[j] );
					int dij = (int) ( RRR * Math.acos( 0.5*((1.0+q1)*q2 - (1.0-q1)*q3) ) + 1.0);
					matrix[k][j] = (dij != 1)? dij: 0;  
				}
			}
			
			problem.setMatrix_problem(matrix);
		}catch(Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Error of reading");			
		}
	}
	
}
