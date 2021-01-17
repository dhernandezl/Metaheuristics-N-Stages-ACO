/**
 * We will use this class to store the best solutions from the previous steps
 **/
package Utils;

public class listaSE {
	private Node head = null;
	private int cant;
	private int count = 0;

	public listaSE(int cant) {
		super();
		this.head = new Node();
		this.cant = cant;
	}
	
    public void println(){
    	Node aux = head;
		Node aux1 = aux.getNext();
		while(aux1 != null){
			System.out.print(aux1.getWeight() + " ");
			aux = aux1;
			aux1 = aux1.getNext();
		}
		System.out.println();
    }
    
    public void insert(int []data, double weight){
    	    Node aux = head;
    		Node aux1 = aux.getNext();
    		while((aux1 != null)&& (aux1.getWeight() < weight)){
    			aux = aux1; 
    			aux1 = aux1.getNext();
    		}
    		if((aux1 != null)&&(aux1.getWeight() > weight)){
    			Node new_node = new Node(data, weight);
    			new_node.setNext(aux1);
    			aux.setNext(new_node);
    			count++;
    			if (count>this.cant)
    			   this.delete(new_node);
    		}
    		else
    			if ((aux1 == null)&&(this.cant > this.count)){
    				Node new_node = new Node(data, weight);
    		    	aux.setNext(new_node);
    		    	count++;
    	    	}
    	
    }
    public void delete(Node aux){
    	Node aux1 = aux.getNext();
    	while(aux1.getNext()!= null){
    		aux = aux1;
    		aux1 = aux1.getNext();
    	}
    	aux.setNext(null);
    	count--;
    }
    
    public int[]get(int pos){
    	Node aux = this.head.getNext();
    	int posAux = 0;
    	while(posAux < pos){
    		aux = aux.getNext();
    		posAux++;
    	}
    	return aux.getData();
    	
    }
    
    public void setData(int pos, int []data){
    	Node aux = this.head.getNext();
    	int posAux = 0;
    	while(posAux < pos){
    		aux = aux.getNext();
    		posAux++;
    	}
    	aux.setData(data);
    }
    
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
}
