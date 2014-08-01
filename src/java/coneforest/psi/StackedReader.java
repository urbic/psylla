package coneforest.psi;

public class StackedReader
	extends java.io.Reader
{
	@Override
	public void close()
		throws java.io.IOException
	{
		while(stack.size()>0)
			stack.pop().close();
	}

	@Override
	public int read(char[] cbuf, int off, int len)
		throws java.io.IOException
	{
		int result=0;
		while(stack.size()>0 && result<len)
		{
			int cc=stack.peek().read(cbuf, off+result, len-result);
			System.out.println("CC="+cc);
			if(cc==-1)
			{
				stack.pop().close();
				continue;
			}
			result+=cc;
			if(result==len)
				return result;
		}
		return -1;
	}

	public void push(java.io.Reader reader)
	{
		stack.push(reader);
	}

	public java.io.Reader pop()
	{
		return stack.pop();
	}

	public java.io.Reader peek()
	{
		return stack.peek();
	}

	private Stack<java.io.Reader> stack=new Stack<java.io.Reader>();
}
