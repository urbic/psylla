package coneforest.psi;

public class PsiReader
	extends PsiObject
	implements PsiAtomic, PsiReadable, PsiCloseable
{
	public PsiReader()
	{
	}

	public PsiReader(java.io.Reader reader)
	{
		setReader(reader);
	}

	public PsiReader(java.io.InputStream is)
	{
		this(new java.io.InputStreamReader(is));
	}

	/*
	// TODO
	@Override
	public void execute(Interpreter interpreter)
	{
		if(isExecutable())
		{
			//interpreter.interpret(this);
			ExecutionStack execstack=interpreter.getExecutionStack();
			int execlevel=execstack.size();
			execstack.push(this);
			interpreter.handleExecutionStack(execlevel);
			//interpreter.pushSourceReader(getReader());
		}
		else
			super.execute(interpreter);
	}
	*/

	@Override
	public void invoke(Interpreter interpreter)
	{
		OperandStack opstack=interpreter.getOperandStack();
		if(isExecutable())
		{
			interpreter.interpretBraced(this);
			opstack.pop().invoke(interpreter);
			/*
			ExecutionStack execstack=interpreter.getExecutionStack();
			int execlevel=execstack.size();
			execstack.push(this);
			interpreter.handleExecutionStack(execlevel);
			//interpreter.pushSourceReader(getReader());
			*/
		}
		else
			super.execute(interpreter);
	}

	@Override
	public String getTypeName()
	{
		return "reader";
	}

	public void setReader(java.io.Reader reader)
	{
		this.reader=reader;
	}

	public java.io.Reader getReader()
	{
		return reader;
	}

	@Override
	public PsiInteger psiRead()
		throws PsiException
	{
		try
		{
			return new PsiInteger(reader.read());
		}
		catch(java.io.IOException e)
		{
			throw new PsiException("ioerror");
		}
	}

	@Override
	public PsiString psiReadString(PsiInteger count)
		throws PsiException
	{
		int countValue=count.getValue().intValue();
		if(countValue<=0)
			throw new PsiException("rangecheck");
		java.nio.CharBuffer buffer=java.nio.CharBuffer.allocate(countValue);
		try
		{
			reader.read(buffer);
		}
		catch(java.io.IOException e)
		{
			throw new PsiException("ioerror");
		}
		buffer.flip();
		//System.out.println(">>>"+buffer);
		return new PsiString(buffer.toString());
	}

	@Override
	public PsiString psiReadLine()
		throws PsiException
	{
		// TODO
		PsiInteger eol=new PsiInteger('\n');
		PsiString string=new PsiString();
		do
		{
			PsiInteger character=psiRead();
			string.psiAppend(character);
			if(character.psiEq(eol).getValue())
				break;
		}
		while(true);
		return string;
	}


	@Override
	public void psiClose()
		throws PsiException
	{
		try
		{
			reader.close();
		}
		catch(java.io.IOException e)
		{
			throw new PsiException("ioerror");
		}
	}

	private java.io.Reader reader;
}
