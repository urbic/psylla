package coneforest.psi;

public class Interpreter
{
	public Interpreter()
	{
		//this.is=is;
		opstack=new OperandStack();
		dictstack=new DictionaryStack();
		execstack=new ExecutionStack();
		procstack=new ProcedureStack();

		// Load systemdict, globaldict, userdict
		dictstack.push(loadModule(coneforest.psi.systemdict.SystemDictionary.class));
		dictstack.push(new PsiDictionary());
		dictstack.push(new PsiDictionary());

		setReader(new java.io.InputStreamReader(System.in));
		setWriter(new java.io.OutputStreamWriter(System.out));
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

	public PsiDictionary getCurrentDictionary()
	{
		return dictstack.peek();
	}

	public PsiDictionary getSystemDictionary()
	{
		return dictstack.get(0);
	}

	public void setReader(java.io.Reader reader)
	{
		getSystemDictionary().put("stdin", new PsiReader(reader));
	}

	public void setWriter(java.io.Writer writer)
	{
		getSystemDictionary().put("stdout", new PsiWriter(writer));
	}

	public void interpret(java.io.Reader reader)
	{
		Parser parser=new Parser(reader);
		while(true)
		{
			Token token=parser.getNextToken();
			if(token.kind==ParserConstants.EOF)
				if(procstack.size()==0)
					return;
				else
				{
					error("syntaxerror");
					return;
				}
			processToken(token);
		}
	}

	public void interpret(PsiReader reader)
	{
		interpret(reader.getReader());
	}

	public void processToken(Token token)
	{
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
				case ParserConstants.TOKEN_REAL:
				case ParserConstants.TOKEN_STRING:
				case ParserConstants.TOKEN_NAME_LITERAL:
				case ParserConstants.TOKEN_NAME_EXECUTABLE:
					(newPsiObject(token)).execute(this);
					handleExecutionStack(0);
					break;
			}
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
				case ParserConstants.TOKEN_REAL:
				case ParserConstants.TOKEN_STRING:
				case ParserConstants.TOKEN_NAME_LITERAL:
				case ParserConstants.TOKEN_NAME_EXECUTABLE:
					procstack.peek().add(newPsiObject(token));
					break;
			}
		}
	}

	public PsiObject getPsiObject(PsiReader reader)
		throws PsiException
	{
		Parser parser=new Parser(reader.getReader());
		for(int i=0; ; i++)
		{
			Token token=parser.getToken(i);
						System.out.println("BEGIN=====");
						//while(true)
						{
							int c=reader.read();
							//if(c==-1)
							//	break;
							System.out.println(c);
						}
						System.out.println("END=====");

			if(procstack.size()==0)
			{
				switch(token.kind)
				{
					case ParserConstants.TOKEN_OPEN_BRACE:
						procstack.push(new PsiArray());
						procstack.peek().setAccess(PsiObject.ACCESS_EXECUTE);
						break;
					case ParserConstants.TOKEN_CLOSE_BRACE:
						throw new PsiException("syntaxerror");
					case ParserConstants.TOKEN_INTEGER:
					case ParserConstants.TOKEN_REAL:
					case ParserConstants.TOKEN_STRING:
					case ParserConstants.TOKEN_NAME_LITERAL:
					case ParserConstants.TOKEN_NAME_EXECUTABLE:
						/*System.out.println("BEGIN=====");
						while(true)
						{
							int c=reader.read();
							if(c==-1)
								break;
							System.out.println(c);
						}
						System.out.println("END=====");*/
						return newPsiObject(token);
					case ParserConstants.EOF:
						return null;
				}
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
							return proc;
						break;
					case ParserConstants.TOKEN_INTEGER:
					case ParserConstants.TOKEN_REAL:
					case ParserConstants.TOKEN_STRING:
					case ParserConstants.TOKEN_NAME_LITERAL:
					case ParserConstants.TOKEN_NAME_EXECUTABLE:
						procstack.peek().add(newPsiObject(token));
						break;
					case ParserConstants.EOF:
						throw new PsiException("syntaxerror");
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
				System.out.println(token);
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
		getSystemDictionary().put("arguments", arguments);
	}

	//private java.io.InputStream is;
	private OperandStack opstack;
	private DictionaryStack dictstack;
	private ExecutionStack execstack;
	private ProcedureStack procstack;
	private Stack<Integer>
		loopstack=new Stack<Integer>(),
		stopstack=new Stack<Integer>();
	private boolean exitFlag=false, stopFlag=false;

}
