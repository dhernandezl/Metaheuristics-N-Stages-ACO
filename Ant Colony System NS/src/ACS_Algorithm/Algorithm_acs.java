package ACS_Algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import Utils.listaSE;
import Utils.Time;

public class Algorithm_acs {

	private int num_ants = 15;
	private int num_iteration = 10000;
	private Environment env;
	private double alpha = 1;
	private double beta = 2;
	private double p0 = 0.1;
	private double cons_evap = 0.1;
	private int[] best_solution;
	private String file = "input_file\\gr48.tsp";
	private double q0 = 0.1;
	private int step = 3;
	private double[] percentAnts = {25,25};
	private double[] percentCicle = {25,25};
	private double[] percentNode = {25,25};
	private int[] percentCant_Ants = new int[step - 1];
	private int[] percentCant_Cicle = new int[step - 1]; 
	private int[] percentCant_Node = new int[step - 1];
	private int percentNStep_Soluction = 5;	
	private listaSE[] bestSolution_NStep = new listaSE[step - 1];

	public Algorithm_acs() {
		for(int i = 0; i < this.step - 1; i++) {
			this.percentCant_Ants[i] = this.num_ants;
			this.percentCant_Cicle[i] = (int) Math.round(this.percentCicle[i] * this.num_iteration/100);
			this.bestSolution_NStep[i] = new listaSE(this.percentNStep_Soluction);
		}
	}
	
	public Algorithm_acs(String file, double alpha, double beta, double p0, double cons_evap, int num_ants, int num_iteration, double q0, int step, double[] percentAnts, double[] percentCicle, double[] percentNode, int percentNStepSolution) {
		this.num_ants = num_ants;
		this.num_iteration = num_iteration;
		this.alpha = alpha;
		this.beta = beta;
		this.p0 = p0;
		this.cons_evap = cons_evap;
		this.file = file; 
		this.q0 = q0;
		this.step = step;
		this.percentNStep_Soluction = percentNStepSolution;
		this.percentAnts = percentAnts;
		this.percentCicle = percentCicle;
		this.percentNode = percentNode;
		this.bestSolution_NStep = new listaSE[this.step - 1];
		this.percentCant_Ants = new int[step - 1];
		this.percentCant_Cicle = new int[step - 1]; 
		this.percentCant_Node = new int[step - 1];
		for(int i = 0; i < this.step - 1; i++) {
			this.percentCant_Ants[i] = this.num_ants;
			this.percentCant_Cicle[i] = (int) Math.round(this.percentCicle[i] * this.num_iteration/100);
			this.bestSolution_NStep[i] = new listaSE(this.percentNStep_Soluction);
		}
	}
	
	public void run_ACS_NS() {
		boolean flag = true;
		this.env = new Environment(file, p0, cons_evap);
		for (int i = 0; i < this.step - 1; i++) {
			this.percentCant_Node[i] = (int) Math.round(this.percentNode[i] * (this.env.cant_cities()) / 100);
		}
		print_parameters();
		
		//Initialization to calculate the Execution time
		Time time = new Time();
		time.initTime();
		
		//Values for the first stage
		ArrayList<Ant> ants = new ArrayList<Ant>(this.num_ants);
		for (int j = 0; j < this.percentCant_Ants[0]; j++) {
			ants.add(new Ant(new Random().nextInt(this.env.cant_cities())));
		}
		
		int control = 0;

		//N Stages
		for (int i = 0; i < this.step - 1; i++) { //Iterations per stage - Start of stage
			for(int j = 0; j < this.percentCant_Cicle[i]; j++) { //Iterations for cycle
				if(flag) {
					for (int k = 1; k < this.percentCant_Node[i]; k++) {
						for (Ant ant : ants) {
							ant.wayAcs_NS(env, alpha, beta, q0);
							int a = ant.oldState();
							int node = ant.newState();
							this.env.updatePheromone_Step_by_Step(a, node);
						}
					}
					
					for (Ant ant : ants) {
						int[] solution = ant.getTourMemory(); //localSerch2_opt(ant.getTourMemory());
						this.bestSolution_NStep[i].insert(solution, this.env.fo(solution));
						Ant a = new Ant((int) Math.round(Math.random() * (this.env.cant_cities() - 1)));
						ant.setTourMemory(a.getTourMemory());
						ant.setBeginState(a.getBeginState());
					}
					
				}else {
					for (int k = 0; k < this.percentCant_Node[i]; k++) {
						for (Ant ant : ants) {
							ant.wayAcs_NS(env, alpha, beta, q0);
							int a = ant.oldState();
							int node = ant.newState();
							this.env.updatePheromone_Step_by_Step(a, node);
						}
					}
					
					for (Ant ant : ants) {
						this.env.updatePheromone_Step_by_Step(ant.newState(), ant.getBeginState());
						int[] solution = ant.getTourMemory(); //localSerch2_opt(ant.getTourMemory());
						this.bestSolution_NStep[i].insert(solution, this.env.fo(solution));
						int positionSolution = (int) Math.round(Math.random() * (this.percentNStep_Soluction - 1));
						int[] nodes = this.bestSolution_NStep[i - 1].get(positionSolution);
						ant.setBeginState(nodes[0]);
						ant.setTourMemory(nodes);
					}
				}
				control++;
				this.env.evaporation();
				this.env.pheromone_Update(this.bestSolution_NStep[i].get(0));
			}//End of a stage cycle
			
			if((i + 1) < step - 1) {
				ants = new ArrayList<Ant>(this.percentCant_Ants[i + 1]);
				for (int j = 0; j < this.percentCant_Ants[i + 1]; j++) { 
					int positionSolution = (int) Math.round(Math.random() * (this.percentNStep_Soluction-1));
					int[] nodes = this.bestSolution_NStep[i].get(positionSolution);
					Ant ant = new Ant(nodes[0]);
					ant.setTourMemory(nodes);
					ants.add(ant);
				}
				flag = false;
			}
			//End of stage
		}
		
		//Calculation of values for the final stage
		int end_step_ants = 0;
		int end_step_iter = 0;
		int end_step_node = 0;
		for(int i = 0; i < this.step - 1; i++) {
			end_step_ants += percentCant_Ants[i];
			end_step_iter += percentCant_Cicle[i];
			end_step_node += percentCant_Node[i];
		}
		
		//End Stage
		ants = new ArrayList<Ant>(this.num_ants);
		for (int j = 0; j < this.num_ants; j++) {
			int positionSolution = (int) Math.round(Math.random() * (this.percentNStep_Soluction-1));
			int[] nodes = this.bestSolution_NStep[this.step - 2].get(positionSolution);
			Ant ant = new Ant(nodes[0]);
			ant.setTourMemory(nodes);
			ants.add(ant);
		}
		
		for (int i = 0; i < this.num_iteration - end_step_iter; i++) {
			for(int j = end_step_node; j < this.env.cant_cities(); j++) {
				for (Ant ant : ants) {
 					ant.wayAcs_NS(env, alpha, beta, q0);
					int a = ant.oldState();
					int node = ant.newState();
					this.env.updatePheromone_Step_by_Step(a, node);
				}
			}
			for (Ant ant : ants) {
				this.env.updatePheromone_Step_by_Step(ant.newState(), ant.getBeginState());
				int[] localSolution = ant.getTourMemory(); //localSerch2_opt(ant.getTourMemory());
				int positionSolution = (int) Math.round(Math.random() * (this.percentNStep_Soluction-1));
				int[] nodes = this.bestSolution_NStep[this.step - 2].get(positionSolution);
				ant.setBeginState(nodes[0]);
				ant.setTourMemory(nodes);
				if (this.CompareGlobalSolution(localSolution)) {
					this.best_solution = (int[]) localSolution.clone();
				}
				
			}
			if (control % 100 == 0) {
				System.out.println("\nIteration: " + control);
				System.out.println("\nCost: " + this.env.cost_global(best_solution));
				
				for (int m = 0; m < best_solution.length; m++) {
					System.out.print(best_solution[m] + 1);
					System.out.print(" ");
				}
				System.out.println("\n----------------------------------------------\n");
			}
			control++;
			this.env.evaporation();
			this.env.pheromone_Update(this.best_solution);
		}
		
		time.endTime();
		
		System.out.println("Solution Opt: \n");
		for (int i = 0; i < best_solution.length; i++) {
			System.out.print(best_solution[i] + 1);
			System.out.print(" ");
		}
		System.out.println("\n");
		System.out.println("\nCost: " + this.env.cost_global(best_solution));
		System.out.println("\nTime: " + time.getTimeFormat());
	}

	private int[] localSerch2_opt(int aux[]) {
		boolean band = true;
		while (band) {
			band = false;
			for (int i = 0; i < aux.length; i++) {
				int j = i + 2;
				while (j != i) {
					int[] aux1 = new int[aux.length];
					for (int h = 0; h < aux.length; h++) {
						aux1[h] = aux[h];
					}
					int aux2 = aux1[(i + 1) % aux.length];
					aux1[(i + 1) % aux.length] = aux1[j % aux.length];
					aux1[j % aux.length] = aux2;
					if (this.env.cost_global(aux) > this.env.cost_global(aux1)) {
						aux = aux1;
						band = true;
					}
					j = (j + 1) % aux.length;
				}
			}
		}
		return aux;
	}

	private boolean CompareGlobalSolution(int bestCycle_Solution[]) {
		double sumSolutioAux = 0;
		double sumGlobalSolutio = 0;
		if (this.best_solution != null) {
			sumSolutioAux = this.env.cost_global(bestCycle_Solution);
			sumGlobalSolutio = this.env.cost_global(this.best_solution);
			if (sumGlobalSolutio > sumSolutioAux) {
				return true;
			}

			return false;
		} else
			return true;

	}

	public void print_parameters() {
		System.out.println("Beta: " + this.beta);
		System.out.println("Alpha: " + this.alpha);
		System.out.println("Pheromone0: " + this.p0);
		System.out.println("q0: " + this.q0);
		System.out.println("Const_Evap: " + this.cons_evap);
		System.out.println("# Ants: " + this.num_ants);
		System.out.println("# Iteration: " + this.num_iteration);
		
		System.out.println("# Step: " + this.step);
		
		System.out.print("\n# PercentAnt: [");
		for(int i = 0; i < this.step - 1; i++) {
			System.out.print(" " + percentAnts[i]);
		}
		System.out.print("]");
		
		System.out.print("\n# PercentCicle: [");
		for(int i = 0; i < this.step - 1; i++) {
			System.out.print(" " + percentCicle[i]);
		}
		System.out.print("]");
		
		System.out.print("\n# PercentNode: [");
		for(int i = 0; i < this.step- 1; i++) {
			System.out.print(" " + percentNode[i]);
		}
		System.out.print("]");
		
		System.out.print("\n# percentCant_Ants: [");
		for(int i = 0; i < this.step- 1; i++) {
			System.out.print(" " + percentCant_Ants[i]);
		}
		System.out.print("]");
		
		System.out.print("\n# percentCant_Cicle: [");
		for(int i = 0; i < this.step- 1; i++) {
			System.out.print(" " + percentCant_Cicle[i]);
		}
		System.out.print("]");
		
		System.out.print("\n# percentCant_Node: [");
		for(int i = 0; i < this.step- 1; i++) {
			System.out.print(" " + percentCant_Node[i]);
		}
		System.out.print("]");
		
		System.out.println("");
		System.out.println("");
	}
	
	public static void main(String[] args) {
		Algorithm_acs ns;
		if(args.length == 0) {
			ns = new Algorithm_acs();
			ns.run_ACS_NS();
		}else {
			try {				
				double[] v1 = Arrays.stream(args[9].split("-")).mapToDouble(Double::parseDouble).toArray();
				double[] v2 = Arrays.stream(args[10].split("-")).mapToDouble(Double::parseDouble).toArray();
				double[] v3 = Arrays.stream(args[11].split("-")).mapToDouble(Double::parseDouble).toArray();

				ns = new Algorithm_acs(args[0], Double.parseDouble(args[1]), Double.parseDouble(args[2]),
						Double.parseDouble(args[3]), Double.parseDouble(args[4]), Integer.parseInt(args[5]),
						Integer.parseInt(args[6]), Double.parseDouble(args[7]), Integer.parseInt(args[8]), v1, v2, v3,
						Integer.parseInt(args[12]));

				ns.run_ACS_NS();
			}catch(Exception e){
				System.out.println(e.getMessage());
				System.out.println("Error of reading");			
			}
		}
	}
}
