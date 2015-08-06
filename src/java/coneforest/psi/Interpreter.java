package coneforest.psi;

/**
 *	An interpreter class.
 */
public class Interpreter
	extends Thread
	implements PsiContext
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

		//typeRegistry.load(Interpreter.class.getResourceAsStream("TypeRegistry.properties"));
		//TypeRegistry("object");
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
		{
			PsiObject obj=execstack.pop();
			if(obj instanceof PsiCommand)
			{
				try
				{
					dictstack.load((PsiCommand)obj).invoke(this);
				}
				catch(PsiException e)
				{
					handleError("undefined", obj);
				}
			}
			else
			{
			//System.out.print(execstack.size()+" ");
			//System.out.println(obj.toSyntaxString());
				obj.execute(this);
			}
		}
		//while(execstack.size()>level)
		//{
		//}
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
		final Parser parser=new Parser(reader.getReader());
		try
		{
			while(running)
			{
				final Token token=parser.getNextToken();
				if(token.kind==ParserConstants.EOF)
					break;

				final int execLevel=getExecLevel();
				processToken(token);
				handleExecutionStack(execLevel);

				// If "stop" invoked outside the stopping context
				if(getStopFlag())
				{
					(new coneforest.psi.PsiErrorDict._handleerror()).invoke(this);
					return;
				}
			}
			if(procstack.size()>0)
				throw new PsiException("syntaxerror");
		}
		catch(PsiException e)
		{
			handleError(e.kind(), reader);
			if(getStopFlag())
				(new coneforest.psi.PsiErrorDict._handleerror()).invoke(this);
		}
		catch(TokenMgrError e)
		{
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
				case ParserConstants.COMMAND:
					(newPsiObject(token)).execute(this);
					handleExecutionStack(0);
					break;
				case ParserConstants.INTEGER:
				case ParserConstants.INTEGER_HEXADECIMAL:
				case ParserConstants.INTEGER_BINARY:
				case ParserConstants.REAL:
				case ParserConstants.STRING:
				case ParserConstants.NAME_SLASHED:
				case ParserConstants.NAME_QUOTED:
				case ParserConstants.IMMEDIATE:
				case ParserConstants.REGEXP:
				case ParserConstants.CHAR:
					opstack.push(newPsiObject(token));
					break;
				case ParserConstants.OPEN_BRACE:
					procstack.push(new PsiProcedure());
					break;
				case ParserConstants.CLOSE_BRACE:
					throw new PsiException("syntaxerror");
				case ParserConstants.EOF:
				//	quit();
					break;
			}
		}
		else
		{
			switch(token.kind)
			{
				case ParserConstants.OPEN_BRACE:
					procstack.push(new PsiProcedure());
					break;
				case ParserConstants.CLOSE_BRACE:
					{
						final PsiArray proc=procstack.pop();
						if(procstack.size()>0)
							procstack.peek().psiAppend(proc);
						else
							opstack.push(proc);
					}
					break;
				case ParserConstants.INTEGER:
				case ParserConstants.INTEGER_HEXADECIMAL:
				case ParserConstants.INTEGER_BINARY:
				case ParserConstants.REAL:
				case ParserConstants.STRING:
				case ParserConstants.NAME_SLASHED:
				case ParserConstants.NAME_QUOTED:
				case ParserConstants.COMMAND:
				case ParserConstants.IMMEDIATE:
				case ParserConstants.REGEXP:
				case ParserConstants.CHAR:
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
			case ParserConstants.STRING:
				{
					final StringBuilder buffer=new StringBuilder();
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
			case ParserConstants.NAME_QUOTED:
				{
					final StringBuilder buffer=new StringBuilder();
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
									case '\'':
										buffer.append('\'');
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
					return new PsiName(buffer.toString().intern());
				}
			case ParserConstants.REGEXP:
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
			case ParserConstants.INTEGER:
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
			case ParserConstants.INTEGER_HEXADECIMAL:
				try
				{
					if(token.image.startsWith("+") || token.image.startsWith("-"))
						return PsiInteger.valueOf(Long.parseLong(token.image.substring(0, 1)
								+token.image.substring(3), 16));
					else
						return PsiInteger.valueOf(Long.parseLong(token.image.substring(2), 16));
				}
				catch(NumberFormatException e)
				{
					throw new PsiException("syntaxerror");
				}
			case ParserConstants.INTEGER_BINARY:
				try
				{
					if(token.image.startsWith("+") || token.image.startsWith("-"))
						return PsiInteger.valueOf(Long.parseLong(token.image.substring(0, 1)
								+token.image.substring(3), 2));
					else
						return PsiInteger.valueOf(Long.parseLong(token.image.substring(2), 2));
				}
				catch(NumberFormatException e)
				{
					throw new PsiException("syntaxerror");
				}
			case ParserConstants.CHAR:
				switch(token.image.charAt(1))
				{
					case '\\':
						switch(token.image.charAt(2))
						{
							case '0':
								return PsiInteger.valueOf('\u0000');
							case 'a':
								return PsiInteger.valueOf('\u0007');
							case 'n':
								return PsiInteger.valueOf('\n');
							case 'r':
								return PsiInteger.valueOf('\r');
							case 't':
								return PsiInteger.valueOf('\t');
							case 'f':
								return PsiInteger.valueOf('\f');
							case 'e':
								return PsiInteger.valueOf('\u001B');
							case '\\':
								return PsiInteger.valueOf('\\');
							case 'u':
								return PsiInteger.valueOf(Integer.valueOf(token.image.substring(3, 7), 16));
							case 'c':
								{
									final int ch=token.image.charAt(3);
									return PsiInteger.valueOf(ch+(ch<64? 64: -64));
								}
							case 'x':
								try
								{
									return PsiInteger.valueOf(Integer.valueOf(
											token.image.substring(4, token.image.length()-1), 16));
								}
								catch(IllegalArgumentException e)
								{
									throw new PsiException("syntaxerror");
								}
						}
					default:
						return PsiInteger.valueOf(token.image.charAt(1));
				}
			case ParserConstants.REAL:
				return new PsiReal(Double.parseDouble(token.image));
			case ParserConstants.NAME_SLASHED:
				return new PsiName(token.image.substring(1).intern());
			case ParserConstants.COMMAND:
				return new PsiCommand(token.image);
			case ParserConstants.IMMEDIATE:
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
			System.out.print(" "+obj.toSyntaxString());
		System.out.println();

		System.out.print("Execution stack:\n\t");
		for(PsiObject obj: execstack)
			System.out.print(" "+obj.toSyntaxString());
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
		stopFlag=true;
		execstack.clear();
	}

	public void repl()
		throws PsiException
	{
		try
		{
			final jline.ConsoleReader cr=new jline.ConsoleReader();
			cr.printString(banner());
			while(running)
			{
				cr.setDefaultPrompt(prompt());
				String line=cr.readLine();
				if(line==null)
				{
					cr.printNewline();
					cr.flushConsole();
					return;
				}
				final Parser parser=new Parser(new java.io.StringReader(line));
				try
				{
					while(running)
					{
						final Token token=parser.getNextToken();
						if(token.kind==ParserConstants.EOF)
							break;
						processToken(token);
						handleExecutionStack(0);
						// If "stop" invoked outside the stopping context
						if(getStopFlag())
						{
							(new coneforest.psi.PsiErrorDict._handleerror()).invoke(this);
							setStopFlag(false);
							break;
						}
					}
				}
				catch(PsiException e)
				{
					handleError(e.kind(), PsiNull.NULL);
					if(getStopFlag())
						(new coneforest.psi.PsiErrorDict._handleerror()).invoke(this);
				}
				catch(TokenMgrError e)
				{
					handleError("syntaxerror", PsiNull.NULL);
					if(getStopFlag())
						(new coneforest.psi.PsiErrorDict._handleerror()).invoke(this);
				}
			}
		}
		catch(java.io.IOException e)
		{
			throw new PsiException("ioerror");
		}
	}

	public String banner()
	{
		return String.format(Messages.getString("bannerText"), Version.getVersion());
	}

	public String prompt()
	{
		StringBuilder sb=new StringBuilder("PSYLLA");
		for(int i=procstack.size(); i>0; i--)
			sb.append('{');
		if(opstack.size()>0)
			sb.append("<"+opstack.size());
		sb.append("> ");
		return sb.toString();
	}

	public static Interpreter currentInterpreter()
	{
		return (Interpreter)Thread.currentThread();
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
	//private TypeRegistry typeRegistry=new TypeRegistry();
}
