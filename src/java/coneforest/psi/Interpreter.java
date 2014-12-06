package coneforest.psi;

/**
 *	An interpreter.
 */
public class Interpreter
{
	public Interpreter()
	{
		opstack=new OperandStack();
		dictstack=new DictionaryStack();
		execstack=new ExecutionStack();
		procstack=new ProcedureStack();

		dictstack.push(new PsiSystemDictionary());
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
			execstack.pop().execute(this);
	}

	public PsiDictionarylike getCurrentDictionary()
	{
		return dictstack.peek();
	}

	/**
	 *	Returns system dictionary.
	 *
	 *	@return system dictionary.
	 */
	public PsiDictionarylike getSystemDictionary()
	{
		return dictstack.get(0);
	}

	/**
	 *	Returns global dictionary.
	 *
	 *	@return global dictionary.
	 */
	public PsiDictionarylike getGlobalDictionary()
	{
		return dictstack.get(1);
	}

	/**
	 *	Returns user dictionary.
	 *
	 *	@return user dictionary.
	 */
	public PsiDictionarylike getUserDictionary()
	{
		return dictstack.get(2);
	}

	public void setReader(java.io.Reader reader)
	{
		getSystemDictionary().psiPut("stdin", new PsiReader(reader));
	}

	public void setWriter(java.io.Writer writer)
	{
		getSystemDictionary().psiPut("stdout", new PsiWriter(writer));
	}

	public void setErrorWriter(java.io.Writer writer)
	{
		getSystemDictionary().psiPut("stderr", new PsiWriter(writer));
	}

	public void eval(PsiReader reader)
	{
		try
		{
			interpret(reader);
		}
		catch(PsiException e)
		{
			error(e.kind(), reader);
		}
	}

	public void eval(java.io.Reader readerValue)
	{
		eval(new PsiReader(readerValue));
	}

	public void eval(String string)
	{
		eval(new PsiStringReader(string));
	}

	public void interpret(PsiReader reader)
		throws PsiException
	{
		int procLevel=procstack.size();
		Parser parser=new Parser(reader.getReader());
		try
		{
			while(true)
			{
				Token token=parser.getNextToken();
				if(token.kind==ParserConstants.EOF)
					if(procstack.size()==procLevel)
						return;
					else
						throw new PsiException("syntaxerror");
				processToken(token);
			}
		}
		catch(TokenMgrError e)
		{
			//error("syntaxerror", reader);
			throw new PsiException("syntaxerror");
		}
		/*
		catch(PsiException e)
		{
			error(e.kind(), reader);
		}
		*/
		catch(StackOverflowError e)
		{
			//error("limitcheck", reader);
			throw new PsiException("limitcheck");
		}
	}

	public void interpretBraced(PsiReader reader)
		throws PsiException
	{
		procstack.push(new PsiArray());
		procstack.peek().setExecutable();
		interpret(reader);
		if(procstack.size()==0)
			error("syntaxerror", reader);
		else
		{
			PsiArray proc=procstack.pop();
			if(procstack.size()>0)
				procstack.peek().psiAppend(proc);
			else
				opstack.push(proc);
		}
	}

	public void processToken(Token token)
		throws PsiException
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
				case ParserConstants.TOKEN_INTEGER_HEXADECIMAL:
				case ParserConstants.TOKEN_INTEGER_BINARY:
				case ParserConstants.TOKEN_REAL:
				//case ParserConstants.TOKEN_COMPLEX:
				case ParserConstants.TOKEN_STRING:
				case ParserConstants.TOKEN_NAME_LITERAL:
				case ParserConstants.TOKEN_NAME_IMMEDIATE:
				case ParserConstants.TOKEN_REGEXP:
					opstack.push(newPsiObject(token));
					break;
				case ParserConstants.TOKEN_OPEN_BRACE:
					procstack.push(new PsiArray());
					procstack.peek().setExecutable();
					break;
				case ParserConstants.TOKEN_CLOSE_BRACE:
					throw new PsiException("syntaxerror");
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
				case ParserConstants.TOKEN_INTEGER_HEXADECIMAL:
				case ParserConstants.TOKEN_INTEGER_BINARY:
				case ParserConstants.TOKEN_REAL:
				//case ParserConstants.TOKEN_COMPLEX:
				case ParserConstants.TOKEN_STRING:
				case ParserConstants.TOKEN_NAME_LITERAL:
				case ParserConstants.TOKEN_NAME_EXECUTABLE:
				case ParserConstants.TOKEN_NAME_IMMEDIATE:
				case ParserConstants.TOKEN_REGEXP:
					procstack.peek().psiAppend(newPsiObject(token));
					break;
			}
		}
	}

	/*
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
						procstack.peek().setExecutable();
						break;
					case ParserConstants.TOKEN_CLOSE_BRACE:
						throw new PsiException("syntaxerror");
					case ParserConstants.TOKEN_INTEGER:
					case ParserConstants.TOKEN_INTEGER_HEXADECIMAL:
					case ParserConstants.TOKEN_INTEGER_BINARY:
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
						System.out.println("END=====");* /
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
						procstack.peek().setExecutable();
						break;
					case ParserConstants.TOKEN_CLOSE_BRACE:
						PsiArray proc=procstack.pop();
						if(procstack.size()>0)
							procstack.peek().psiAppend(proc);
						else
							return proc;
						break;
					case ParserConstants.TOKEN_INTEGER:
					case ParserConstants.TOKEN_INTEGER_HEXADECIMAL:
					case ParserConstants.TOKEN_INTEGER_BINARY:
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
	*/

	private PsiObject newPsiObject(Token token)
		throws PsiException
	{
		switch(token.kind)
		{
			case ParserConstants.TOKEN_STRING:
				{
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
									case 'c':
										int ch=token.image.charAt(++i);
										ch=ch+(ch<64? 64: -64);
										buffer.append(Character.toChars(ch));
										break;
									case 'x':
										try
										{
											int j=token.image.indexOf('}', i+2);
											buffer.append(Character.toChars(Integer.valueOf(token.image.substring(i+2, j), 16)));
											i=j;
										}
										catch(IllegalArgumentException e)
										{
											throw new PsiException("syntax");
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
									case '!':
										buffer.append('!');
										break;
									//case '\\':
									//	buffer.append('\\');
									//	break;
									case '\n':
										break;
									case 'u':
										buffer.append(Character.toChars(Integer.valueOf(token.image.substring(i+1, i+5), 16)));
										i+=4;
										break;
									case 'c':
										int ch=token.image.charAt(++i);
										ch=ch+(ch<64? 64: -64);
										buffer.append(Character.toChars(ch));
										break;
									case 'x':
										try
										{
											int j=token.image.indexOf('}', i+2);
											buffer.append(Character.toChars(Integer.valueOf(token.image.substring(i+2, j), 16)));
											i=j;
										}
										catch(IllegalArgumentException e)
										{
											throw new PsiException("syntax");
										}
										break;
									case '\\':
									case 'd':
									case 'D':
									case 's':
									case 'S':
									case 'w':
									case 'W':
									case 'p':
									case 'b':
									case 'B':
									case 'A':
									case 'G':
									case 'z':
									case 'Z':
									case 'Q':
									case 'E':
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
				return new PsiInteger(Long.parseLong(token.image));
			case ParserConstants.TOKEN_INTEGER_HEXADECIMAL:
				if(token.image.startsWith("+")||token.image.startsWith("-"))
					return new PsiInteger(Long.parseLong(token.image.substring(0, 1)+token.image.substring(3), 16));
				else
					return new PsiInteger(Long.parseLong(token.image.substring(2), 16));
			case ParserConstants.TOKEN_INTEGER_BINARY:
				if(token.image.startsWith("+")||token.image.startsWith("-"))
					return new PsiInteger(Long.parseLong(token.image.substring(0, 1)+token.image.substring(3), 2));
				else
					return new PsiInteger(Long.parseLong(token.image.substring(2), 2));
			case ParserConstants.TOKEN_REAL:
				return new PsiReal(Double.parseDouble(token.image));
			/*case ParserConstants.TOKEN_COMPLEX:
				{
					String[] images=token.image.substring(3).split(":");
					return new PsiComplex(Double.parseDouble(images[0]), Double.parseDouble(images[1]));
				}
			*/
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
				return dictstack.load(token.image.substring(2));
			/*
			default:
				System.out.println(token);
				return null;
			*/
		}
		throw new PsiException("unknownerror");
	}

	public void error(Exception e, PsiObject obj)
	{
		String errorName;
		if(e instanceof ClassCastException)
			errorName="typecheck";
		else if(e instanceof PsiException)
			errorName=((PsiException)e).kind();
		else
			errorName="unknownerror";
		error(errorName, obj);
	}

	public void error(String errorName, PsiObject obj)
	{
		// TODO
		opstack.push(obj);
		System.out.println("Error: /"+errorName+" in "+obj);
		showStacks();
		System.exit(1);
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

	public void acceptScriptName(final String scriptName)
	{
		PsiString script=new PsiString(scriptName);
		getSystemDictionary().psiPut("script", script);
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
			environment.psiPut(entry.getKey(), new PsiString(entry.getValue()));
		getSystemDictionary().psiPut("environment", environment);
	}

	public void acceptClassPath(String[] classPath)
	{
		try
		{
			for(String pathElement: classPath)
				((PsiClassLoader)getSystemDictionary().psiGet("classpath"))
						.psiAppend(new PsiString(pathElement));
		}
		catch(PsiException e)
		{
			// NOP
		}
	}

	private OperandStack opstack;
	private DictionaryStack dictstack;
	private ExecutionStack execstack;
	private ProcedureStack procstack;
	private Stack<Integer>
		loopstack=new Stack<Integer>(),
		stopstack=new Stack<Integer>();
	private boolean exitFlag=false, stopFlag=false;
}
