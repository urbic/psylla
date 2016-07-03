package coneforest.psi;

/**
*	Base class for interpreter’s stacks.
*
*	@param <E> a type of an element.
*/
public class Stack<E> extends java.util.ArrayList<E>
{
	/**
	*	Pops and returns the last element of the stack.
	*
	*	@return an element popped.
	*/
	public E pop()
	{
		return remove(size()-1);
	}

	/**
	*	Returns the last element of the stack without popping it.
	*
	*	@return an element.
	*/
	public E peek()
	{
		return get(size()-1);
	}

	/**
	*	Pushes an element to the stack.
	*
	*	@param element an element.
	*/
	public void push(final E element)
	{
		add(element);
	}

	public void setSize(final int size)
	{
		removeRange(size, size());
	}
}
