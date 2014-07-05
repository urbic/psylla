package coneforest.psi;

public class Stack<E> extends java.util.ArrayList<E>
{
	public E pop()
	{
		return remove(size()-1);
	}

	public E peek()
	{
		return get(size()-1);
	}

	public void push(E element)
	{
		add(element);
	}

	public void setSize(int size)
	{
		removeRange(size, size());
	}
}
