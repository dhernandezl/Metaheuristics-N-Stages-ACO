package ACS_Algorithm;

import Utils.Decoding;
import Utils.Problem;

public class Environment {

	private double matrixPheromone[][];
	private double pheromoneInicial;
	private double constEvaporation;
	private Problem pr;

	public Environment(String dir, double pheromoneInicial, double constEvaporation) {
		this.pr = Decoding.Decoding_File(dir);
		this.matrixPheromone = new double[pr.getDimension()][pr.getDimension()];
		this.pheromoneInicial = pheromoneInicial;
		this.constEvaporation = constEvaporation;
		this.initMatrixPheromone();
	}

	public void initMatrixPheromone() {
		for (int i = 0; i < this.matrixPheromone.length; i++) {
			for (int j = i + 1; j < this.matrixPheromone.length; j++) {
				this.matrixPheromone[j][i] = this.matrixPheromone[i][j] = this.pheromoneInicial;
			}
		}
	}
	
	public void pheromone_Update(int[] tour) {
		double fo = pr.fo(tour);
		for (int i = 1; i < tour.length; i++) {
			int j = tour[i - 1];
			int k = tour[i];
			this.matrixPheromone[k][j] = this.matrixPheromone[j][k] += this.constEvaporation * (1 / fo);
		}
		this.matrixPheromone[tour[0]][tour[tour.length
				- 1]] = this.matrixPheromone[tour[tour.length - 1]][tour[0]] += this.constEvaporation * (1 / fo);

	}

	public void evaporation() {
		for (int i = 0; i < this.matrixPheromone.length; i++) {
			for (int j = i + 1; j < this.matrixPheromone.length; j++) {
				this.matrixPheromone[j][i] = this.matrixPheromone[i][j] = (1 - this.constEvaporation)
						* this.matrixPheromone[i][j];
			}
		}

	}
	
	public void updatePheromone_Step_by_Step(int i, int j){
    	this.matrixPheromone[j][i] = this.matrixPheromone[i][j]= (1 - this.constEvaporation)* this.matrixPheromone[i][j]+ this.constEvaporation* this.pheromoneInicial;
    }
	
	public void updatePheromone_ForStep(int tour[]){
    	double coste = this.fo(tour);
        for (int i = 1; i < tour.length; i++) {
     		int j = tour[i-1];
   	    	int k = tour[i];
   		    this.matrixPheromone[k][j] =this.matrixPheromone[j][k]+= this.constEvaporation * (1 / coste);
   	    }
        
    }

   public double fo(int[] tour) {
	   return this.pr.fo(tour);
   }
   
   public double cost_global(int[] tour) {
	   return this.pr.cost_global(tour);
   }
   
   public int cant_cities() {
	   return this.pr.getDimension();
   }

	public double getPheromone_In(int i, int j) {
		return this.matrixPheromone[i][j];
	}

	public Problem getPr() {
		return pr;
	}

	public void setPr(Problem pr) {
		this.pr = pr;
	}
}
