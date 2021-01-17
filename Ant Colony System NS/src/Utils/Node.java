/**
 * This class is for making a linked list
 **/
package Utils;

public class Node {
	private int []data;
	private double weight;
	private Node next = null;

	public Node(int[] data, double weight) {
		super();
		this.data = data;
		this.weight = weight;
	}
	public Node() {
		super();
	}
	public double getWeight(){
		return this.weight;
	}
	public void setWeight(int weight){
		this.weight = weight;
	}
	
	public int[] getData(){
		return this.data;
	}
	public void setData(int []data){
		this.data = data;
	}
	public Node getNext(){
		return this.next;
	}
	public void setNext(Node next){
		this.next = next;
	}
}
