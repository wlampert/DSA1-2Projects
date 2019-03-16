package priorityqueue;

import java.util.ArrayList;

public class MinHeap<T extends Comparable<T>> implements PriorityQueue<T> {

	/* The actual heap of data */
	private ArrayList<T> heap;
	
	public MinHeap() {
		heap = new ArrayList<>();
		//TODO: WRITE THIS METHOD
	}
	
	public MinHeap(ArrayList<T> data) {
		this.heap = (ArrayList<T>)data.clone();
		this.heapify();
		//TODO: WRITE THIS METHOD
	}
	
	private void heapify() {
		if(heap.size()>2) {
			for (int i = heap.size()-1; i >= 0; i--) {
				percolateDown(i);
			}
		}

		//TODO: WRITE THIS METHOD
	}
	
	private void percolateUp(int index) {
		int parent = Math.floorDiv(index - 1 , 2);
		if(index <= 0 || heap.get(index).compareTo(heap.get(parent)) >= 0) {
			return;
		}
		else {
			T parentTemp = heap.get(parent);
			heap.set(parent, heap.get(index));
			heap.set(index, parentTemp);
			percolateUp(parent);
		}
		//TODO: WRITE THIS METHOD
	}
	
	private void percolateDown(int index) {
		int leftChild = index*2+1;
		int rightChild = index*2+2;
		int returnIndex;


		if(heap.size() <= leftChild) {
			return;
		}

		else {
			//Scenario 1: There is only a left child
			if(heap.size() == leftChild+1) {
				if(heap.get(leftChild).compareTo(heap.get(index)) < 0) {
					T leftTemp = heap.get(leftChild);
					heap.set(leftChild, heap.get(index));
					heap.set(index, leftTemp);
					returnIndex = leftChild;
				}
				else {
					return;
				}
			}

			//Scenario 2: left child is less than right child
			else if(heap.get(leftChild).compareTo(heap.get(rightChild)) < 0) {
				if(heap.get(leftChild).compareTo(heap.get(index)) < 0) {
					T leftTemp = heap.get(leftChild);
					heap.set(leftChild, heap.get(index));
					heap.set(index, leftTemp);
					returnIndex = leftChild;
				}
				else {
					return;
				}
			}

			//Scenario 3: right child is less than left child
			else {
				if(heap.get(rightChild).compareTo(heap.get(index)) < 0) {
					T rightTemp = heap.get(rightChild);
					heap.set(rightChild, heap.get(index));
					heap.set(index, rightTemp);
					returnIndex = rightChild;
				}
				else {
					return;
				}
			}
			percolateDown(returnIndex);
		}
		return;
		//TODO: WRITE THIS METHOD
	}
	
	@Override
	public void push(T data) {
		int curSize = heap.size();
		heap.add(data);
		percolateUp(curSize);
		//TODO: WRITE THIS METHOD
	}

	@Override
	public T poll() {
		if(heap.size() == 0) {
			return null;
		}

		T root = heap.get(0);
		//Move last leaf node to root and remove it
		heap.set(0, heap.get(heap.size()-1));
		heap.remove(heap.size()-1);
		percolateDown(0);
		return root;
		//TODO: WRITE THIS METHOD
	}

	@Override
	public T peek() {
		if(heap.size() == 0) {
			return null;
		}
		return heap.get(0);
		//TODO: WRITE THIS METHOD
	}
	
	@Override
	public int size() {
		return heap.size();
		//TODO: WRITE THIS METHOD
	}


	//In Lab methods
	public void  updatePriority(T oldElement, T newElement) {
		heap.set(heap.indexOf(oldElement), newElement);
		this.heapify();
	}


}
