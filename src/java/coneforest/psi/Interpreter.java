package coneforest.psi;

/**
 *	An interpreter class.
 */
public class Interpreter
	extends Thread
{
	/**
	 *	Creates new Ψ language interpreter.
	 */
	public Interpreter()
	{
		this(new DictStack());
	}

	public Interpreter(DictStack dictstack)
	{
		opstack=new OperandStack();
		execstack=new ExecutionStack();
		procstack=new ProcedureStack();
		this.dictstack=(DictStack)dictstack.clone();
		pushStopLevel();
	}

	/**
	 *	Returns operand stack.
	 *
	 *	@return an operand stack.
	 */
	public OperandStack getOperandStack()
	{
		return opstack;
	}

	/**
	 *	Returns dictionary stack.
	 *
	 *	@return a dictionary stack.
	 */
	public DictStack getDictStack()
	{
		return dictstack;
	}

	/**
	 *	Returns execution stack.
	 *
	 *	@return an execution stack.
	 */
	public ExecutionStack getExecutionStack()
	{
		return execstack;
	}

	public void handleExecutionStack(final int level)
	{
		while(execstack.size()>level)
			execstack.pop().execute(this);
	}

	/**
	 *	Returns current dictionary.
	 *
	 *	@return a current dictionary.
	 */
	public PsiDictlike getCurrentDict()
	{
		return dictstack.peek();
	}

	/**
	 *	Returns system dictionary.
	 *
	 *	@return a system dictionary.
	 */
	public PsiDictlike getSystemDict()
	{
		return dictstack.get(0);
	}

	/**
	 *	Returns global dictionary.
	 *
	 *	@return a global dictionary.
	 */
	public PsiDictlike getGlobalDict()
	{
		return dictstack.get(1);
	}

	/**
	 *	Returns user dictionary.
	 *
	 *	@return user dictionary.
	 */
	public PsiDictlike getUserDict()
	{
		return dictstack.get(2);
	}

	public void setReader(final java.io.Reader reader)
	{
		getSystemDict().put("stdin", new PsiReader(reader));
	}

	public void setWriter(final java.io.Writer writer)
	{
		getSystemDict().put("stdout", new PsiWriter(writer));
	}

	public void setErrorWriter(final java.io.Writer writer)
	{
		getSystemDict().put("stderr", new PsiWriter(writer));
	}

	public void interpret(final java.io.Reader readerValue)
	{
		interpret(new PsiReader(readerValue));
	}

	public void interpret(final String string)
	{
		interpret(new PsiStringReader(string));
	}

	public void interpret(final PsiReader reader)
	{
		Parser parser=new Parser(reader.getReader());
		try
		{
			while(running)
			{
				processToken(parser.getNextToken());
				if(getStopFlag())
					(new coneforest.psi.PsiErrorDict._handleerror()).invoke(this);
			}
		}
		catch(PsiException e)
		{
			handleError(e.kind(), reader);
			if(getStopFlag())
				(new coneforest.psi.PsiErrorDict._handleerror()).invoke(this);
		}
		catch(TokenMgrError e)
		{
			System.out.println("TOKENMGR ERROR");
			handleError("syntaxerror", reader);
			if(getStopFlag())
				(new coneforest.psi.PsiErrorDict._handleerror()).invoke(this);
		}
	}

	public void interpretBraced(final PsiReader reader)
	{
		procstack.push(new PsiProcedure());
		interpret(reader);
		if(procstack.size()==0)
			handleError("syntaxerror", reader);
		PsiProcedure proc=procstack.pop();
		if(procstack.size()>0)
			procstack.peek().psiAppend(proc);
		else
			opstack.push(proc);
	}

	public void processToken(final Token token)
		throws PsiException
	{
		if(procstack.size()==0)
		{
			switch(token.kind)
			{
				case ParserConstants.TOKEN_COMMAND:
					(newPsiObject(token)).execute(this);
					handleExecutionStack(0);
					break;
				case ParserConstants.TOKEN_INTEGER:
				case ParserConstants.TOKEN_INTEGER_HEXADECIMAL:
				case ParserConstants.TOKEN_INTEGER_BINARY:
				case ParserConstants.TOKEN_REAL:
				case ParserConstants.TOKEN_STRING:
				case ParserConstants.TOKEN_NAME:
				case ParserConstants.TOKEN_IMMEDIATE:
				case ParserConstants.TOKEN_REGEXP:
					opstack.push(newPsiObject(token));
					break;
				case ParserConstants.TOKEN_OPEN_BRACE:
					procstack.push(new PsiProcedure());
					break;
				case ParserConstants.TOKEN_CLOSE_BRACE:
					throw new PsiException("syntaxerror");
				case ParserConstants.EOF:
					quit();
					break;
			}
		}
		else
		{
			switch(token.kind)
			{
				case ParserConstants.TOKEN_OPEN_BRACE:
					procstack.push(new PsiProcedure());
					break;
				case ParserConstants.TOKEN_CLOSE_BRACE:
					{
						final PsiArray proc=procstack.pop();
						if(procstack.size()>0)
							procstack.peek().psiAppend(proc);
						else
							opstack.push(proc);
					}
					break;
				case ParserConstants.TOKEN_INTEGER:
				case ParserConstants.TOKEN_INTEGER_HEXADECIMAL:
				case ParserConstants.TOKEN_INTEGER_BINARY:
				case ParserConstants.TOKEN_REAL:
				case ParserConstants.TOKEN_STRING:
				case ParserConstants.TOKEN_NAME:
				case ParserConstants.TOKEN_COMMAND:
				case ParserConstants.TOKEN_IMMEDIATE:
				case ParserConstants.TOKEN_REGEXP:
					procstack.peek().psiAppend(newPsiObject(token));
					break;
				case ParserConstants.EOF:
					throw new PsiException("syntaxerror");
			}
		}
	}

	private PsiObject newPsiObject(final Token token)
		throws PsiException
	{
		switch(token.kind)
		{
			case ParserConstants.TOKEN_STRING:
				{
					StringBuilder buffer=new StringBuilder();
					for(int i=1; i<token.image.length()-1; i++)
					{
						final char c=token.image.charAt(i);
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
									case 'c':
										{
											final int ch=token.image.charAt(++i);
											buffer.append(Character.toChars(ch+(ch<64? 64: -64)));
										}
										break;
									case 'x':
										try
										{
											final int j=token.image.indexOf('}', i+2);
											buffer.append(Character.toChars(Integer.valueOf(token.image.substring(i+2, j), 16)));
											i=j;
										}
										catch(IllegalArgumentException e)
										{
											throw new PsiException("syntaxerror");
										}
										break;
								}
								break;
							default:
								buffer.append(c);
								break;
						}
					}
					return new PsiString(buffer);
				}
			case ParserConstants.TOKEN_REGEXP:
				{
					StringBuilder buffer=new StringBuilder();
					for(int i=1; i<token.image.length()-1; i++)
					{
						final char c=token.image.charAt(i);
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
									case '\n':
										break;
									case 'u':
										buffer.append(Character.toChars(Integer.valueOf(token.image.substring(i+1, i+5), 16)));
										i+=4;
										break;
									case 'c':
										{
											final int ch=token.image.charAt(++i);
											buffer.append(Character.toChars(ch+(ch<64? 64: -64)));
										}
										break;
									case 'x':
										try
										{
											// TODO: if not found
											final int j=token.image.indexOf('}', i+2);
											buffer.append(Character.toChars(Integer.valueOf(token.image.substring(i+2, j), 16)));
											i=j;
										}
										catch(IllegalArgumentException e)
										{
											throw new PsiException("syntaxerror");
										}
										break;
									/*
									case '\\':
									case 'd': case 'D':
									case 's': case 'S':
									case 'w': case 'W':
									case 'p':
									case 'b': case 'B':
									case 'A':
									case 'G':
									case 'z': case 'Z':
									case 'Q':
									case 'E':
									case '!':
									case '1': case '2': case '3':
									case '4': case '5': case '6':
									case '7': case '8': case '9':
									*/
									case '@':
										buffer.append('@');
										break;
									default:
										buffer.append("\\"+token.image.charAt(i));
										break;
								}
								break;
							default:
								buffer.append(c);
								break;
						}
					}
					return new PsiRegExp(buffer);
				}
			case ParserConstants.TOKEN_INTEGER:
				try
				{
					try
					{
						return PsiInteger.valueOf(Long.parseLong(token.image));
					}
					catch(NumberFormatException e)
					{
						return new PsiReal(Double.parseDouble(token.image));
					}
				}
				catch(NumberFormatException e)
				{
					throw new PsiException("syntaxerror");
				}
			case ParserConstants.TOKEN_INTEGER_HEXADECIMAL:
				if(token.image.startsWith("+")||token.image.startsWith("-"))
					return PsiInteger.valueOf(Long.parseLong(token.image.substring(0, 1)+token.image.substring(3), 16));
				else
					return PsiInteger.valueOf(Long.parseLong(token.image.substring(2), 16));
			case ParserConstants.TOKEN_INTEGER_BINARY:
				try
				{
					if(token.image.startsWith("+")||token.image.startsWith("-"))
						return PsiInteger.valueOf(Long.parseLong(token.image.substring(0, 1)+token.image.substring(3), 2));
					else
						return PsiInteger.valueOf(Long.parseLong(token.image.substring(2), 2));
				}
				catch(NumberFormatException e)
				{
					throw new PsiException("syntaxerror");
				}
			case ParserConstants.TOKEN_REAL:
				return new PsiReal(Double.parseDouble(token.image));
			case ParserConstants.TOKEN_NAME:
				return new PsiName(token.image.substring(1));
			case ParserConstants.TOKEN_COMMAND:
				return new PsiCommand(token.image);
			case ParserConstants.TOKEN_IMMEDIATE:
				return dictstack.load(token.image.substring(2));
			default:
				throw new AssertionError();
		}
		//throw new PsiException("unknownerror");
	}

	public PsiDict getErrorDict()
	{
		try
		{
			return (PsiDict)getSystemDict().get("errordict");
		}
		catch(PsiException e)
		{
			throw new AssertionError();
		}
	}

	public void handleError(final Exception e, final PsiObject obj)
	{
		if(e instanceof PsiException)
			handleError(((PsiException)e).kind(), obj);
		else if(e instanceof ClassCastException)
			handleError("typecheck", obj);
	}

	public void handleError(final String errorName, final PsiObject obj)
	{
		PsiDict errorObj=new PsiDict();
		errorObj.put("newerror", PsiBoolean.TRUE);
		errorObj.put("errorname", new PsiName(errorName));
		errorObj.put("command", obj);
		errorObj.put("ostack", new PsiArray((java.util.ArrayList<PsiObject>)opstack.clone()));
		errorObj.put("estack", new PsiArray((java.util.ArrayList<PsiObject>)execstack.clone()));
		errorObj.put("dstack", new PsiArray((java.util.ArrayList<PsiObject>)dictstack.clone()));
		getSystemDict().put("$error", errorObj);

		try
		{
			PsiDict errorDict=getErrorDict();
			(
				errorDict.known(errorName)?
					errorDict.get(errorName):
					new coneforest.psi.core._stop()
			).invoke(this);
		}
		catch(PsiException e)
		{
			throw new AssertionError();
		}
	}

	public void showStacks()
	{
		System.out.print("Operand stack:\n\t");
		for(PsiObject obj: opstack)
			System.out.print(" "+obj);
		System.out.println();

		System.out.print("Execution stack:\n\t");
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

		System.out.print("Loop level stack:\n\t");
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

	public void setExitFlag(final boolean exitFlag)
	{
		this.exitFlag=exitFlag;
	}

	public boolean getStopFlag()
	{
		return stopFlag;
	}

	public void setStopFlag(final boolean stopFlag)
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

	public void acceptScriptName(final String scriptName)
	{
		PsiString script=new PsiString(scriptName);
		getSystemDict().put("script", script);
	}

	public void acceptShellArguments(final String[] args)
	{
		PsiArray arguments=new PsiArray();
		for(String arg: args)
			arguments.psiAppend(new PsiString(arg));
		getSystemDict().put("arguments", arguments);
	}

	public void acceptEnvironment(final java.util.Map<String, String> env)
	{
		PsiDict environment=new PsiDict();
		for(java.util.Map.Entry<String, String> entry: env.entrySet())
			environment.put(entry.getKey(), new PsiString(entry.getValue()));
		getSystemDict().put("environment", environment);
	}

	public void acceptClassPath(final String[] classPath)
	{
		try
		{
			final PsiClassLoader classLoader=(PsiClassLoader)getSystemDict().get("classpath");
			for(String pathElement: classPath)
				classLoader.psiAppend(new PsiString(pathElement));
		}
		catch(PsiException e)
		{
			// NOP
		}
	}

	public void quit()
	{
		running=false;
		execstack.clear();
	}

	private final OperandStack opstack;
	private final DictStack dictstack;
	private final ExecutionStack execstack;
	private final ProcedureStack procstack;
	private final Stack<Integer>
		loopstack=new Stack<Integer>(),
		stopstack=new Stack<Integer>();
	private boolean exitFlag=false, stopFlag=false;
	private boolean running=true;
}
