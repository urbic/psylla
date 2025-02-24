package coneforest.psylla.runtime;

import java.util.ArrayList;

/**
*	Base class for interpreter’s stacks.
*
*	@param <E> a type of an element.
*/
@SuppressWarnings("serial")
public class Stack<E>
	extends ArrayList<E>
{
	/**
	*	Constructs an empty stack with an initial capacity of ten.
	*
	*	@param <E> a type of an element.
	*/
	public <E> Stack()
	{
	}

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
	*	{@return the topmost of this stack without popping it}
	*/
	public E peek()
	{
		return get(size()-1);
	}

	/**
	*	Pushes an element to this stack.
	*
	*	@param element an element.
	*/
	public void push(final E element)
	{
		add(element);
	}

	/**
	*	Truncate this stack to the specified size.
	*
	*	@param size the size.
	*/
	public void setSize(final int size)
	{
		removeRange(size, size());
	}
}
