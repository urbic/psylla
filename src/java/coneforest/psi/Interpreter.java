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
					default:
						(newPsiObject(token)).execute(this);
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
					default:
						procstack.peek().add(newPsiObject(token));
						break;
				}
			}
		}
	}

	private PsiObject newPsiObject(Token token)
	{
		switch(token.kind)
		{
			case ParserConstants.TOKEN_STRING:
				StringBuffer buffer=new StringBuffer();
				for(int i=1; i<token.image.length()-1; i++)
				{
					char c=token.image.charAt(i);
					switch(c)
					{
						case '\\':
							i++;
							switch(token.image.charAt(i))
							{
								case '0':
									buffer.append('\u0000');
									break;
								case 'a':
									buffer.append('\u0007');
									break;
								case 'n':
									buffer.append('\n');
									break;
								case 'r':
									buffer.append('\r');
									break;
								case 't':
									buffer.append('\t');
									break;
								case 'f':
									buffer.append('\f');
									break;
								case 'e':
									buffer.append('\u001B');
									break;
								case '"':
									buffer.append('"');
									break;
								case '\\':
									buffer.append('\\');
									break;
								case 'u':
									buffer.append(Character.toChars(Integer.valueOf(token.image.substring(i+1, i+5), 16)));
									i+=4;
									break;
							}
							break;
						default:
							buffer.append(c);
							break;
					}
				}
				return new PsiString(buffer);
			case ParserConstants.TOKEN_REAL:
				return new PsiReal(Double.parseDouble(token.image));
			case ParserConstants.TOKEN_INTEGER:
				return new PsiInteger(Long.parseLong(token.image));
			case ParserConstants.TOKEN_NAME_LITERAL:
				{
					PsiName name=new PsiName(token.image.substring(1));
					name.setLiteral();
					return name;
				}
			case ParserConstants.TOKEN_NAME_EXECUTABLE:
				{
					PsiName name=new PsiName(token.image);
					name.setExecutable();
					return name;
				}
			default:
				return null;
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
