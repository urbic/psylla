package coneforest.psylla.runtime;

import java.util.ArrayList;

/**
*	Base class for interpreter’s stacks.
*
*	@param <E> a type of an element.
*/
public class Stack<E>
	extends ArrayList<E>
{
	/**
	*	Pops and returns the topmost element of this stack.
	*
	*	@return an element popped.
	*/
	public E pop()
	{
		return remove(size()-1);
	}

	/**
	*	Returns the topmost of this stack without popping it.
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
