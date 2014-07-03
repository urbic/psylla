package coneforest.psi;

public class Interpreter
{
	public Interpreter(java.io.InputStream is)
	{
		this.is=is;
		opstack=new OperandStack();
		dictstack=new DictionaryStack();
		execstack=new ExecutionStack();
		procstack=new ProcedureStack();

		// Load systemdict, globaldict, userdict
		dictstack.push(loadModule(coneforest.psi.systemdict.SystemDictionary.class));
		dictstack.push(new PsiDictionary());
		dictstack.push(new PsiDictionary());
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
		Parser parser=new Parser(is);
		Token token;

		while(true)
		{
			token=parser.getNextToken();
			if(token.kind==ParserConstants.EOF) break;

			//System.out.println(token+"\t"+PsiParserConstants.tokenImage[token.kind]);


			if(procstack.size()==0)
			{
				switch(token.kind)
				{
					case ParserConstants.TOKEN_OPEN_BRACE:
						procstack.push(new PsiArray());
						procstack.peek().setAccess(PsiObject.ACCESS_EXECUTE);
						break;
					case ParserConstants.TOKEN_CLOSE_BRACE:
						error("syntaxerror");
						break;
					case ParserConstants.TOKEN_INTEGER:
						opstack.push(new PsiInteger(token));
						break;
					case ParserConstants.TOKEN_REAL:
						opstack.push(new PsiReal(token));
						break;
					case ParserConstants.TOKEN_STRING:
						opstack.push(new PsiString(token));
						break;
					case ParserConstants.TOKEN_NAME_LITERAL:
						opstack.push(new PsiName(token));
						break;
					case ParserConstants.TOKEN_NAME_EXECUTABLE:
						//dictstack.load(new PsiName(token)).execute(this);
						(new PsiName(token)).execute(this);
						break;
				}
				// TODO
				handleExecutionStack(0);
			}
			else
			{
				switch(token.kind)
				{
					case ParserConstants.TOKEN_OPEN_BRACE:
						procstack.push(new PsiArray());
						procstack.peek().setAccess(PsiObject.ACCESS_EXECUTE);
						break;
					case ParserConstants.TOKEN_CLOSE_BRACE:
						PsiArray proc=procstack.pop();
						if(procstack.size()>0)
							procstack.peek().add(proc);
						else
							opstack.push(proc);
						break;
					case ParserConstants.TOKEN_INTEGER:
						procstack.peek().add(new PsiInteger(token));
						break;
					case ParserConstants.TOKEN_REAL:
						procstack.peek().add(new PsiReal(token));
						break;
					case ParserConstants.TOKEN_STRING:
						procstack.peek().add(new PsiString(token));
						break;
					case ParserConstants.TOKEN_NAME_LITERAL:
					case ParserConstants.TOKEN_NAME_EXECUTABLE:
						procstack.peek().add(new PsiName(token));
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

	public PsiDictionary loadModule(Class<? extends PsiModule> moduleClass)
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

	public int getExecLevel()
	{
		return execstack.size();
	}

	public boolean getExitFlag()
	{
		return exitFlag;
	}

	public void setExitFlag(boolean exitFlag)
	{
		this.exitFlag=exitFlag;
	}
	
	public boolean getStopFlag()
	{
		return stopFlag;
	}

	public void setStopFlag(boolean stopFlag)
	{
		this.stopFlag=stopFlag;
	}
	
	public int pushLoopLevel()
	{
		int level=execstack.size();
		loopstack.push(level);
		return level;
	}

	public int popLoopLevel()
	{
		return loopstack.size()>0? loopstack.pop(): -1;
	}

	public int currentLoopLevel()
	{
		return loopstack.size()>0? loopstack.peek(): -1;
	}

	public int pushStopLevel()
	{
		int level=execstack.size();
		stopstack.push(level);
		return level;
	}

	public int popStopLevel()
	{
		return stopstack.size()>0? stopstack.pop(): -1;
	}
	
	public int currentStopLevel()
	{
		return stopstack.size()>0? stopstack.peek(): -1;
	}

	public void acceptShellArguments(final String[] args)
	{
		PsiArray arguments=new PsiArray();
		for(String arg: args)
			arguments.add(new PsiString(arg));
		dictstack.getSystemDictionary().put("arguments", arguments);
	}

	private java.io.InputStream is;
	private OperandStack opstack;
	private DictionaryStack dictstack;
	private ExecutionStack execstack;
	private ProcedureStack procstack;
	private java.util.Stack<Integer>
		loopstack=new java.util.Stack<Integer>(),
		stopstack=new java.util.Stack<Integer>();
	private boolean exitFlag=false, stopFlag=false;
}
