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
		try
		{
			dictstack.push(module(PsiSystemDictionary.class));
		}
		catch(PsiException e)
		{
			// TODO
		}
		PsiDictionary globaldict=new PsiDictionary();
		getSystemDictionary().psiPut("globaldict", globaldict);
		dictstack.push(globaldict);
		PsiDictionary userdict=new PsiDictionary();
		getSystemDictionary().psiPut("userdict", userdict);
		dictstack.push(userdict);

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
		getSystemDictionary().psiPut("stdin", new PsiReader(reader));
	}

	public void setWriter(java.io.Writer writer)
	{
		getSystemDictionary().psiPut("stdout", new PsiWriter(writer));
	}

	/*
	public void pushSourceReader(java.io.Reader reader)
	{
		System.out.println("PUSH SR");
		stackedReader.push(reader);
	}

	public void interpret()
	{
		interpret(stackedReader);
	}
	*/

	public void interpret(java.io.Reader reader)
	{
		interpret(new PsiReader(reader));
	}

	public void interpret(PsiReader reader)
	{
		//interpret(reader.getReader());
		try
		{
			Parser parser=new Parser(reader.getReader());
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
		catch(StackOverflowError e)
		{
			//System.out.println("STACK OVERFLOW");
			//System.exit(1);
			error("limitcheck", reader);
		}
	}

	public void processToken(Token token)
	{
		if(procstack.size()==0)
		{
			switch(token.kind)
			{
				case ParserConstants.TOKEN_NAME_EXECUTABLE:
					(newPsiObject(token)).execute(this);
					assert execstack.size()==0: "Execution stack not empty";
					handleExecutionStack(0);
					break;
				case ParserConstants.TOKEN_INTEGER:
				case ParserConstants.TOKEN_HEXINTEGER:
				case ParserConstants.TOKEN_BININTEGER:
				case ParserConstants.TOKEN_REAL:
				case ParserConstants.TOKEN_STRING:
				case ParserConstants.TOKEN_NAME_LITERAL:
				case ParserConstants.TOKEN_NAME_IMMEDIATE:
					opstack.push(newPsiObject(token));
					break;
				case ParserConstants.TOKEN_OPEN_BRACE:
					procstack.push(new PsiArray());
					procstack.peek().setExecutable();
					break;
				case ParserConstants.TOKEN_CLOSE_BRACE:
					error("syntaxerror");
					break;
			}
		}
		else
		{
			switch(token.kind)
			{
				case ParserConstants.TOKEN_OPEN_BRACE:
					procstack.push(new PsiArray());
					procstack.peek().setExecutable();
					break;
				case ParserConstants.TOKEN_CLOSE_BRACE:
					PsiArray proc=procstack.pop();
					if(procstack.size()>0)
						procstack.peek().psiAppend(proc);
					else
						opstack.push(proc);
					break;
				case ParserConstants.TOKEN_INTEGER:
				case ParserConstants.TOKEN_HEXINTEGER:
				case ParserConstants.TOKEN_BININTEGER:
				case ParserConstants.TOKEN_REAL:
				case ParserConstants.TOKEN_STRING:
				case ParserConstants.TOKEN_NAME_LITERAL:
				case ParserConstants.TOKEN_NAME_EXECUTABLE:
				case ParserConstants.TOKEN_NAME_IMMEDIATE:
					procstack.peek().psiAppend(newPsiObject(token));
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
							PsiInteger c=reader.psiRead();
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
					case ParserConstants.TOKEN_HEXINTEGER:
					case ParserConstants.TOKEN_BININTEGER:
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
							procstack.peek().psiAppend(proc);
						else
							return proc;
						break;
					case ParserConstants.TOKEN_INTEGER:
					case ParserConstants.TOKEN_HEXINTEGER:
					case ParserConstants.TOKEN_BININTEGER:
					case ParserConstants.TOKEN_REAL:
					case ParserConstants.TOKEN_STRING:
					case ParserConstants.TOKEN_NAME_LITERAL:
					case ParserConstants.TOKEN_NAME_EXECUTABLE:
						procstack.peek().psiAppend(newPsiObject(token));
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
				StringBuilder buffer=new StringBuilder();
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
								case '\n':
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
			case ParserConstants.TOKEN_INTEGER:
				return new PsiInteger(Long.parseLong(token.image));
			case ParserConstants.TOKEN_HEXINTEGER:
				if(token.image.startsWith("+")||token.image.startsWith("-"))
					return new PsiInteger(Long.parseLong(token.image.substring(0, 1)+token.image.substring(3), 16));
				else
					return new PsiInteger(Long.parseLong(token.image.substring(2), 16));
			case ParserConstants.TOKEN_BININTEGER:
				if(token.image.startsWith("+")||token.image.startsWith("-"))
					return new PsiInteger(Long.parseLong(token.image.substring(0, 1)+token.image.substring(3), 2));
				else
					return new PsiInteger(Long.parseLong(token.image.substring(2), 2));
			case ParserConstants.TOKEN_REAL:
				return new PsiReal(Double.parseDouble(token.image));
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
			case ParserConstants.TOKEN_NAME_IMMEDIATE:
				try
				{
					return dictstack.load(token.image.substring(2));
				}
				catch(PsiException e)
				{
					error(e.kind());
					return null;
				}
			default:
				System.out.println(token);
				return null;
		}
	}

	public void error(String errorName, PsiObject obj)
	{
		// TODO
		opstack.push(obj);
		System.out.println("Error /"+errorName+" in "+obj);
		showStacks();
		System.exit(1);
	}

	public void error(String errorName)
	{
		// TODO
		System.out.println("ERROR "+errorName);
		System.exit(1);
	}

	public PsiModule module(Class<? extends PsiModule> moduleClass)
		throws PsiException
	{
		try
		{
			return moduleClass.newInstance();
		}
		catch(InstantiationException e)
		{
			throw new PsiException("undefinedmodule");
		}
		catch(IllegalAccessException e)
		{
			throw new PsiException("undefinedmodule");
		}
	}

	public PsiModule module(String moduleClassName)
		throws PsiException
	{
		try
		{
			return module((Class<? extends PsiModule>)Class.forName(moduleClassName));
		}
		catch(ClassNotFoundException e)
		{
			throw new PsiException("undefinedmodule");
		}
	}

	public void showStacks()
	{
		System.out.println("Operand stack:");
		System.out.print("⊢\t");
		for(PsiObject obj: opstack)
			System.out.print(" "+obj);
		System.out.println();
		
		System.out.println("Execution stack:");
		System.out.print("⊢\t");
		for(PsiObject obj: execstack)
			System.out.print(" "+obj);
		System.out.println();

		/*
		System.out.println("Dictionary stack:");
		System.out.print("⊢\t");
		for(PsiObject obj: dictstack)
			System.out.print(" "+obj);
		System.out.println();
		*/

		System.out.println("Loop level stack:");
		System.out.print("⊢\t");
		for(int item: loopstack)
			System.out.print(" "+item);
		System.out.println();
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
		//show("PUSH LOOP LEVEL "+level);
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
			arguments.psiAppend(new PsiString(arg));
		getSystemDictionary().psiPut("arguments", arguments);
	}

	public void acceptEnvironment(final java.util.Map<String, String> env)
	{
		PsiDictionary environment=new PsiDictionary();
		for(java.util.Map.Entry<String, String> entry: env.entrySet())
			environment.psiPut(new PsiName(entry.getKey()), new PsiString(entry.getValue()));
		getSystemDictionary().psiPut("environment", environment);
	}

	private OperandStack opstack;
	private DictionaryStack dictstack;
	private ExecutionStack execstack;
	private ProcedureStack procstack;
	private Stack<Integer>
		loopstack=new Stack<Integer>(),
		stopstack=new Stack<Integer>();
	private boolean exitFlag=false, stopFlag=false;
	//private StackedReader stackedReader=new StackedReader();
}
