package Utils;

public class Problem {
	
	private String name;
	private String type;
	private int dimension;
	private String edge_weight_type;
	private String edge_weight_format;
	private int matrix_problem[][];
	
	public Problem() {
		
	}
	
	public double fo(int [] sol) {
		double sumCost = 0.0;
		for(int i= 1; i< sol.length; i++){
			sumCost += matrix_problem[sol[i-1]][sol[i]];
		}
		sumCost += matrix_problem[sol[0]][sol[sol.length - 1]];
		return sumCost;
	}
	
	public double cost_global(int tour[]) {
		double sumCost = 0.0;
		for (int i = 1; i < tour.length; i++) {
			sumCost += matrix_problem[tour[i - 1]][tour[i]];
		}
		sumCost += matrix_problem[tour[0]][tour[tour.length - 1]];
		return sumCost;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public int getDimension() {
		return dimension;
	}
	
	public void setDimension(int dimension) {
		this.dimension = dimension;
	}
	
	public String getEdge_weight_type() {
		return edge_weight_type;
	}
	
	public void setEdge_weight_type(String edge_weight_type) {
		this.edge_weight_type = edge_weight_type;
	}
	
	public String getEdge_weight_format() {
		return edge_weight_format;
	}
	
	public void setEdge_weight_format(String edge_weight_format) {
		this.edge_weight_format = edge_weight_format;
	}
	
	public int[][] getMatrix_problem() {
		return matrix_problem;
	}
	
	public void setMatrix_problem(int[][] matrix_problem) {
		this.matrix_problem = matrix_problem;
	}
	
	public double getWeight_problem(int i, int j) {
    	return this.matrix_problem[i][j];
	}
}
