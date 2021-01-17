package ACS_Algorithm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Ant {
	
	private int tourMemory[];
	private int beginState;
	
	public Ant(int beginState) {
		this.beginState = beginState;
		tourMemory = new int[1];
		this.tourMemory[0] = beginState;
	}
	
	public int oldState(){
		return this.tourMemory[tourMemory.length-2];
	}
	
	public int newState(){
		return this.tourMemory[tourMemory.length-1];
	}
	
	public int getBeginState(){
		return this.beginState;
	}

	public int[] getTourMemory() {
		return tourMemory;
	}

	public void setTourMemory(int[] tourMemory) {
		this.tourMemory = tourMemory;
	}

	public void setBeginState(int beginState) {
		this.beginState = beginState;
	}

	private void inTo_Memory(int pos)
	{
		int tempMemory[] = new int[tourMemory.length + 1];
		
		for(int i=0 ; i< tourMemory.length ;i++) {
		 tempMemory[i] = tourMemory[i];
		}
	 	tempMemory[tempMemory.length-1] = pos;
	 	tourMemory = tempMemory;
	}
	
	private boolean isHere(int pos){
		int i =0;
		while (i<= this.tourMemory.length-1){
			if (pos == this.tourMemory[i]){
				return true;
			}
			i++;
		}
		return false;
	}
	
	public int wayAcs(Environment environment, double alpha, double beta, double q0) {
		double arrayTempValue[] = new double[environment.getPr().getDimension()];
		double sumAj = 0.0;
		int thisState = this.newState();
		for (int i = 0; i < arrayTempValue.length; i++) {
			double b = 0.0;
			if (!isHere(i)) {
				double aux = (double) 1 / environment.getPr().getWeight_problem(thisState, i);
				b = (double) Math.pow(environment.getPheromone_In(thisState, i),alpha) * Math.pow(aux, beta);
				sumAj += b;
			}
			arrayTempValue[i] = b;
		}
		double q = Math.random();
		int state = 0;
		if(q < q0) {
			//indice del valor máximo de arrayTempValue
			state = maxIndex(arrayTempValue);
		}else {
 			for (int i = 0; i < arrayTempValue.length; i++) {
				arrayTempValue[i] /= sumAj;
			}
			state = ruleta(arrayTempValue);
		}
		this.inTo_Memory(state);
		return state;
	}
	
	
	private int ruleta(double arrayPj[]) {
		double minimo = 0;
		double maximo = 0;
		int numAux = 0;
		double q = (double) Math.random();
		for (int i = 0; i < arrayPj.length; i++) {
			maximo += arrayPj[i];
			if ((maximo >= q) && (q > minimo)) {
				numAux = i;
				break;
			}
			minimo = maximo;
		}
		return numAux;
	}
	
	private int maxIndex(double vector[]) {
	    int maxIndex = 0;
	    for (int i = 1; i < vector.length; i++) {

	        if (vector[i] >= vector[maxIndex]) {
	            maxIndex = i;
	        }
	    }
	    return maxIndex;
	}
	
	public int wayAcs_NS(Environment environment, double alpha, double beta, double q0) {
		HashMap<Integer, Double> arrayTempValue = possible_cities(environment);
		double sumAj = 0.0;
		int thisState = this.newState();
		for (HashMap.Entry<Integer, Double> entry : arrayTempValue.entrySet()) {
			double b = 0.0;
			double aux = (double) 1 / environment.getPr().getWeight_problem(thisState, entry.getKey());
			b = (double) Math.pow(environment.getPheromone_In(thisState, entry.getKey()),alpha) * Math.pow(aux, beta);
			sumAj += b;
		    entry.setValue(b);
		}
		double q = Math.random();
		int state = 0;
		if(q < q0) {
			//indice del valor máximo de arrayTempValue
			state = maxIndex_NStep(arrayTempValue);
		}else {
			for (HashMap.Entry<Integer, Double> entry : arrayTempValue.entrySet()) {
				double aux = entry.getValue();
				entry.setValue(aux / sumAj);
			}
			state = ruleta_NStep(arrayTempValue);
		}
		this.inTo_Memory(state);
		return state;
	}
	
	private HashMap<Integer, Double> possible_cities(Environment environment) {
		HashMap<Integer, Double> arrayTempValue = new HashMap<Integer, Double>();
		List<Integer> tourTemp = Arrays.stream(this.tourMemory).boxed().collect(Collectors.toList());
		for(int i = 0; i < environment.cant_cities(); i++) {
			if(!tourTemp.contains(i)) {
				arrayTempValue.put(i, 0.0);
			}
		}
		return arrayTempValue;
	}
	
	private int ruleta_NStep(HashMap<Integer, Double> tourTemp ) {
		double minimo = 0;
		double maximo = 0;
		int numAux = 0;
		for (Integer key : tourTemp.keySet()) {
	    	numAux = key;
	    	break;
	    }
		double q = (double) Math.random();
		for (HashMap.Entry<Integer, Double> entry : tourTemp.entrySet()) {
			maximo += entry.getValue();
			if((maximo >= q) && (q > minimo)) {
				numAux = entry.getKey();
				break;
			}
			minimo = maximo;
	    }
		return numAux;
	}
	
	private int maxIndex_NStep(HashMap<Integer, Double> tourTemp) {
	    int maxIndex = 0;
	    for (Integer key : tourTemp.keySet()) {
	    	maxIndex = key;
	    	break;
	    }
	    for (HashMap.Entry<Integer, Double> entry : tourTemp.entrySet()) {
	    	if(tourTemp.get(entry.getKey()) >= tourTemp.get(maxIndex)) {
	    		maxIndex = entry.getKey();
	    	}
	    	
	    }
	    return maxIndex;
	}
	

}
