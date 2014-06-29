package coneforest.psi;

public class PSIInterpreter
{
	public PSIInterpreter(java.io.InputStream is)
	{
		this.is=is;
		opstack=new OperandStack();
		dictstack=new DictionaryStack();
		execstack=new ExecutionStack();
		procstack=new ProcedureStack();

		// Load systemdict, globaldict, userdict
		dictstack.push(loadModule(coneforest.psi.systemdict.SystemDictionary.class));
		dictstack.push(new PSIDictionary());
		dictstack.push(new PSIDictionary());
	}

	public OperandStack getOperandStack()
	{
		return opstack;
	}

	public DictionaryStack getDictionaryStack()
	{
		return dictstack;
	}

	public ExecutionStack getExecutionStack()
	{
		return execstack;
	}

	public void handleExecutionStack(int level)
	{
		while(execstack.size()>level)
		{
			//System.out.println("<<<HANDLE ESTACK="+execstack+" OSTACK="+opstack);
			execstack.pop().execute(this);
			//System.out.println(">>>HANDLE ESTACK="+execstack+" OSTACK="+opstack);
		}
	}

	public void interpret()
	{
		PSIParser parser=new PSIParser(is);
		Token token;

		while(true)
		{
			token=parser.getNextToken();
			if(token.kind==PSIParserConstants.EOF) break;

			//System.out.println(token+"\t"+PSIParserConstants.tokenImage[token.kind]);


			if(procstack.size()==0)
			{
				switch(token.kind)
				{
					case PSIParserConstants.TOKEN_OPEN_BRACE:
						procstack.push(new PSIArray());
						procstack.peek().setAccess(PSIObject.ACCESS_EXECUTE);
						break;
					case PSIParserConstants.TOKEN_CLOSE_BRACE:
						error("syntaxerror");
						break;
					case PSIParserConstants.TOKEN_INTEGER:
						opstack.push(new PSIInteger(token));
						break;
					case PSIParserConstants.TOKEN_REAL:
						opstack.push(new PSIReal(token));
						break;
					case PSIParserConstants.TOKEN_STRING:
						opstack.push(new PSIString(token));
						break;
					case PSIParserConstants.TOKEN_NAME_LITERAL:
						opstack.push(new PSIName(token));
						break;
					case PSIParserConstants.TOKEN_NAME_EXECUTABLE:
						//dictstack.load(new PSIName(token)).execute(this);
						(new PSIName(token)).execute(this);
						break;
				}
				// TODO
				handleExecutionStack(0);
			}
			else
			{
				switch(token.kind)
				{
					case PSIParserConstants.TOKEN_OPEN_BRACE:
						procstack.push(new PSIArray());
						procstack.peek().setAccess(PSIObject.ACCESS_EXECUTE);
						break;
					case PSIParserConstants.TOKEN_CLOSE_BRACE:
						PSIArray proc=procstack.pop();
						if(procstack.size()>0)
							procstack.peek().add(proc);
						else
							opstack.push(proc);
						break;
					case PSIParserConstants.TOKEN_INTEGER:
						procstack.peek().add(new PSIInteger(token));
						break;
					case PSIParserConstants.TOKEN_REAL:
						procstack.peek().add(new PSIReal(token));
						break;
					case PSIParserConstants.TOKEN_STRING:
						procstack.peek().add(new PSIString(token));
						break;
					case PSIParserConstants.TOKEN_NAME_LITERAL:
					case PSIParserConstants.TOKEN_NAME_EXECUTABLE:
						procstack.peek().add(new PSIName(token));
						break;
				}
			}
		}
	}

	public void error(String errorName)
	{
		// TODO
		System.out.println("ERROR: "+errorName);
	}

	public PSIDictionary loadModule(Class<? extends PSIModule> moduleClass)
	{
		try
		{
			return moduleClass.newInstance();
		}
		catch(InstantiationException e)
		{
			System.out.println("INSTANTIATION EXCEPTION");
		}
		catch(IllegalAccessException e)
		{
			System.out.println("ILLEGAL ACCESS EXCEPTION");
		}
		return null;
	}
	public boolean getExitFlag()
	{
		return exitFlag;
	}

	public void setExitFlag(boolean exitFlag)
	{
		this.exitFlag=exitFlag;
	}
	
	public void pushLoopLevel()
	{
		loopstack.push(execstack.size());
	}

	public int popLoopLevel()
	{
		return loopstack.size()>0? loopstack.pop(): -1;
	}

	public int currentLoopLevel()
	{
		return loopstack.size()>0? loopstack.peek(): -1;
	}

	private java.io.InputStream is;
	private OperandStack opstack;
	private DictionaryStack dictstack;
	private ExecutionStack execstack;
	private ProcedureStack procstack;
	private java.util.Stack<Integer> loopstack=new java.util.Stack<Integer>();
	private boolean exitFlag=false;
}
