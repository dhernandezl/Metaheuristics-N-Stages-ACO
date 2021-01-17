package Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Decoding {

	public static Problem Decoding_File(String file_S) {
		Problem problem = new Problem();
		BufferedReader reader;
		try {
			File file = new File(file_S);
			if(file.exists()){
				reader = new BufferedReader(new FileReader(file_S));
				String line = null;
				while ((line = reader.readLine()) != null) {
					if (line.startsWith("NAME")) {
						String[] tokens = line.split(":");
						problem.setName(tokens[1].trim());
					}
					else if (line.startsWith("TYPE")) {
						String[] tokens = line.split(":");
						problem.setType(tokens[1].trim());
					}
					else if (line.startsWith("DIMENSION")) {
						String[] tokens = line.split(":");
						problem.setDimension(Integer.parseInt(tokens[1].trim()));
					}
					else if (line.startsWith("EDGE_WEIGHT_TYPE")) {
						String[] tokens = line.split(":");
						problem.setEdge_weight_type(tokens[1].trim());
						if(problem.getEdge_weight_type().equals("EUC_2D")) {
							Decoder.EUC_2D.Decoder_EUC_2D(reader, problem);
							break;
						}else if(problem.getEdge_weight_type().equals("GEO")) {
							Decoder.GEO.Decoder_GEO(reader, problem);
							break;
						}
					} else if (line.startsWith("EDGE_WEIGHT_FORMAT")) {
						String[] tokens = line.split(":");
						problem.setEdge_weight_format(tokens[1].trim());
						if(problem.getEdge_weight_format().equals("LOWER_DIAG_ROW")) {
							Decoder.LOWER_DIAG_ROW.Decoder_LOWER_DIAG_ROW(reader, problem);
							break;
						}
					}
				}
			}else {
				System.out.println("File not found");
			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Error of reading");		
		}
		return problem;
	}
}
