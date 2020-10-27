package coneforest.psylla;
import coneforest.psylla.core.*;

/**
*	An interpreter.
*/
public class Interpreter
	extends Thread
	implements PsyContext
{
	/**
	*	Creates a new Psylla language interpreter.
	*/
	public Interpreter()
	{
		try
		{
			ostack=new OperandStack();
			estack=new ExecutionStack();
			procstack=new ProcStack();
			dstack=new DictStack();
			pushStopLevel();
			PsySystemDict.register(this);
		}
		catch(final PsyException e)
		{
			throw new AssertionError();
		}
	}

	public Interpreter(final DictStack dstack)
	{
		ostack=new OperandStack();
		estack=new ExecutionStack();
		procstack=new ProcStack();
		this.dstack=(DictStack)dstack.clone();
		pushStopLevel();
	}

	/**
	*	Returns the operand stack.
	*
	*	@return the operand stack.
	*/
	public OperandStack operandStack()
	{
		return ostack;
	}

	public OperandStack operandStackBacked(final int count)
		throws PsyException
	{
		ostack.popOperands(count);
		return ostack;
	}

	/**
	*	Returns the dictionary stack.
	*
	*	@return the dictionary stack.
	*/
	public DictStack dictStack()
	{
		return dstack;
	}

	/**
	*	Returns the execution stack.
	*
	*	@return the execution stack.
	*/
	public ExecutionStack executionStack()
	{
		return estack;
	}

	/**
	*	Returns the namespace pool.
	*
	*	@return the namespace pool.
	*/
	public NamespacePool namespacePool()
	{
		return nspool;
	}

	public coneforest.psylla.ClassLoader classLoader()
	{
		return classLoader;
	}

	public PsyDictlike where(final String name)
	{
		return dstack.where(name);
	}

	public PsyDictlike psyWhere(final PsyStringy oKey)
	{
		return where(oKey.stringValue());
	}

	public <T extends PsyObject> T load(final String name)
		throws PsyException
	{
		final var prefixOffset=name.indexOf('@');
		if(prefixOffset==-1)
			return dstack.load(name);
		//final var prefix=name.substring(prefixOffset+1);
		//return (T)nspool.namespace(prefix).get(name.substring(0, prefixOffset));
		return (T)nspool.namespace(name.substring(0, prefixOffset))
				.get(name.substring(prefixOffset+1));
	}

	public <T extends PsyObject> T psyLoad(final PsyStringy oKey)
		throws PsyException
	{
		return this.<T>load(oKey.stringValue());
	}

	public void handleExecutionStack()
	{
		while(estack.size()>0)
			estack.pop().execute(this);
	}

	public void handleExecutionStack(final int level)
	{
		while(estack.size()>level)
			estack.pop().execute(this);
	}

	/**
	*	Returns the current dictionary.
	*
	*	@return the current dictionary.
	*/
	public PsyDictlike currentDict()
	{
		return dstack.peek();
	}

	/**
	*	Returns system dictionary.
	*
	*	@return the system dictionary.
	*/
	public PsyDictlike systemDict()
	{
		return dstack.get(0);
	}

	/**
	*	Returns the user dictionary.
	*
	*	@return the user dictionary.
	*/
	public PsyDictlike userDict()
	{
		return dstack.get(1);
	}

	/**
	*	Returns the current namespace.
	*
	*	@return the current namespace.
	*/
	public PsyNamespace currentNamespace()
	{
		return dstack.currentNamespace();
	}

	public void setReader(final java.io.Reader reader)
	{
		systemDict().put("stdin", new PsyReader(reader));
	}

	public void setWriter(final java.io.Writer writer)
	{
		systemDict().put("stdout", new PsyWriter(writer));
	}

	public void setErrorWriter(final java.io.Writer writer)
	{
		systemDict().put("stderr", new PsyWriter(writer));
	}

	public void setRandomSeed(final Long randomSeed)
		throws PsyException
	{
		if(randomSeed!=null)
			((PsyRandom)systemDict().get("stdrandom"))
				.psySetSeed(PsyInteger.valueOf(randomSeed));
	}

	public void setClassPath(final String[] classPath)
		throws PsyException
	{
		final var oClassPath
			=(PsyArraylike<PsyStringy>)systemDict().get("classpath");
		final var envClassPath=System.getenv("PSYLLA_CLASSPATH");
		if(envClassPath!=null)
			for(var pathItem: envClassPath.split(java.io.File.pathSeparator))
				oClassPath.psyAppend(new PsyName(pathItem));
		if(classPath!=null)
			for(var pathItem: classPath)
				oClassPath.psyAppend(new PsyName(pathItem));
	}

	public void setLibraryPath(final String[] libraryPath)
		throws PsyException
	{
		// Configure library path
		final var oLibraryPath
			=(PsyArraylike<PsyStringy>)systemDict().get("librarypath");
		final var envLibraryPath=System.getenv("PSYLLA_LIB");
		if(envLibraryPath!=null)
			for(var pathItem: envLibraryPath.split(java.io.File.pathSeparator))
				oLibraryPath.psyAppend(new PsyName(pathItem));
		if(libraryPath!=null)
			for(var pathItem: libraryPath)
				oLibraryPath.psyAppend(new PsyName(pathItem));
	}

	public void interpret(final java.io.Reader reader)
	{
		interpret(new PsyReader(reader));
	}

	public void interpret(final String string)
	{
		interpret(new PsyStringReader(string));
	}

	public void interpret(final PsyReader oReader)
	{
		final var initProcLevel=procstack.size();
		final var parser=new Parser(oReader);
		try
		{
			while(running)
			{
				final var token=parser.getNextToken();
				if(token.kind==ParserConstants.EOF)
					break;
				processToken(token);

				// If "stop" is invoked outside the stopping context
				if(getStopFlag())
				{
					PsyErrorDict.OP_HANDLEERROR.invoke(this);
					return;
				}
			}
			if(procstack.size()>initProcLevel)
				throw new PsySyntaxErrorException(oReader);

			dstack.<PsyWriter>load("stdout").psyFlush();
			dstack.<PsyWriter>load("stderr").psyFlush();
		}
		catch(final PsyException e)
		{
			e.setEmitter(oReader); // IMPORTANT
			handleError(e);
			if(getStopFlag())
				PsyErrorDict.OP_HANDLEERROR.invoke(this);
		}
		catch(final TokenMgrError e)
		{
			handleError(new PsySyntaxErrorException(oReader));
			if(getStopFlag())
				PsyErrorDict.OP_HANDLEERROR.invoke(this);
		}
	}

	public void interpretBraced(final PsyReader oReader)
		throws PsyException
	{
		procstack.push(new PsyProc());
		interpret(oReader);
		if(procstack.size()==0)
			handleError(new PsySyntaxErrorException(oReader));
		final var proc=procstack.pop();
		if(procstack.size()>0)
			procstack.peek().psyAppend(proc);
		else
			ostack.push(proc);
	}

	private void processToken(final Token token)
		throws PsyException
	{
		if(procstack.size()==0)
		{
			switch(token.kind)
			{
				case ParserConstants.COMMAND:
					parseToken(token).execute(this);
					handleExecutionStack();
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
					ostack.push(parseToken(token));
					break;
				case ParserConstants.OPEN_BRACE:
					procstack.push(new PsyProc());
					break;
				case ParserConstants.CLOSE_BRACE:
					throw new PsySyntaxErrorException();
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
					procstack.push(new PsyProc());
					break;
				case ParserConstants.CLOSE_BRACE:
					{
						final var proc=procstack.pop();
						if(procstack.size()>0)
							procstack.peek().psyAppend(proc);
						else
							ostack.push(proc);
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
					procstack.peek().psyAppend(parseToken(token));
					break;
				case ParserConstants.EOF:
					throw new PsySyntaxErrorException();
			}
		}
	}

	private PsyObject parseToken(final Token token)
		throws PsyException
	{
		// TODO: make TokensParser inner class of Interpreter
		switch(token.kind)
		{
			case ParserConstants.IMMEDIATE:
				return dstack.load(token.image.substring(2));
			default:
				return TokensParser.parseToken(token);
		}
	}

	public PsyDictlike errorDict()
		throws PsyException
	{
		return (PsyDictlike)systemDict().get("errordict");
	}

	public void handleError(final PsyException oException)
	{
		final var errorName=oException.getName();
		final var errorObj=new PsyDict();
		errorObj.put("newerror", PsyBoolean.TRUE);
		errorObj.put("errorname", new PsyName(errorName));
		errorObj.put("emitter", oException.getEmitter());
		errorObj.put("ostack", new PsyArray((java.util.ArrayList<PsyObject>)ostack.clone()));
		errorObj.put("estack", new PsyArray((java.util.ArrayList<PsyObject>)estack.clone()));
		errorObj.put("dstack", new PsyArray((java.util.ArrayList<PsyObject>)dstack.clone()));
		systemDict().put("$error", errorObj);

		try
		{
			final var errorDict=errorDict();
			if(errorDict.known(errorName))
				errorDict.get(errorName).invoke(this);
			else
				psyStop();
		}
		catch(final PsyException e)
		{
			throw new AssertionError();
		}
	}

	public void showStacks()
	{
		System.out.print("Operand stack:\n\t");
		for(var obj: ostack)
			System.out.print(" "+obj);
		System.out.println();

		System.out.print("Execution stack:\n\t");
		for(var obj: estack)
			System.out.print(" "+obj);
		System.out.println();

		/*
		System.out.println("Dictionary stack:");
		System.out.print("⊢\t");
		for(PsyObject obj: dstack)
			System.out.print(" "+obj);
		System.out.println();
		*/

		System.out.print("Loop level stack:\n\t");
		for(var item: loopstack)
			System.out.print(" "+item);
		System.out.println();
	}

	public int execLevel()
	{
		return estack.size();
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
		final var level=estack.size();
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
		final var level=estack.size();
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

	public void setScriptName(final String scriptName)
	{
		systemDict().put("script", new PsyName(scriptName));
	}

	public void setShellArguments(final String[] args)
		throws PsyException
	{
		final var oArguments
			=(PsyArray)systemDict().get("arguments");
		for(var arg: args)
			oArguments.psyAppend(new PsyName(arg));
	}

	public void setEnvironment(final java.util.Map<String, String> env)
	{
		final var environment=new PsyDict();
		for(java.util.Map.Entry<String, String> entry: env.entrySet())
			environment.put(entry.getKey(), new PsyName(entry.getValue()));
		systemDict().put("environment", environment);
	}

	public void quit()
	{
		running=false;
		//stopFlag=true;
		stopFlag=false;
		estack.clear();
	}

	public void repl()
		throws PsyException
	{
		try
		{
			final var cr=new jline.ConsoleReader();
			cr.printString(banner());
			while(running)
			{
				cr.setDefaultPrompt(prompt());
				final var line=cr.readLine();
				if(line==null)
				{
					cr.printNewline();
					cr.flushConsole();
					return;
				}
				// TODO: wrap StringReader into PsyReader and set PsyReader as
				// emitter
				//interpret(line);
				final var parser=new Parser(new java.io.StringReader(line));
				try
				{
					while(running)
					{
						final var token=parser.getNextToken();
						if(token.kind==ParserConstants.EOF)
							break;
						processToken(token);
						// If "stop" invoked outside the stopping context
						if(getStopFlag())
						{
							PsyErrorDict.OP_HANDLEERROR.invoke(this);
							setStopFlag(false);
							break;
						}
					}
				}
				catch(final PsyException e)
				{
					e.setEmitter(PsyNull.NULL);
					handleError(e);
					if(getStopFlag())
						PsyErrorDict.OP_HANDLEERROR.invoke(this);
				}
				catch(final TokenMgrError e)
				{
					handleError(new PsySyntaxErrorException(PsyNull.NULL));
					if(getStopFlag())
						PsyErrorDict.OP_HANDLEERROR.invoke(this);
				}
			}
		}
		catch(final java.io.IOException e)
		{
			throw new PsyIOErrorException();
		}
	}

	public String banner()
	{
		return String.format(Messages.getString("banner"), Version.getVersion());
	}

	public String prompt()
	{
		final var sb=new StringBuilder("PSYLLA");
		for(int i=procstack.size(); i>0; i--)
			sb.append('{');
		if(ostack.size()>0)
			sb.append("<"+ostack.size());
		sb.append("> ");
		return sb.toString();
	}

	public static Interpreter currentInterpreter()
	{
		return (Interpreter)Thread.currentThread();
	}

	public void psyStop()
	{
		setStopFlag(true);
		if(currentStopLevel()!=-1)
			estack.setSize(currentStopLevel());
		else
			quit();
	}

	/*public void registerType(final String typeName, final Class<? extends PsyObject> typeClass)
	{
		System.out.println("REGISTER: "+typeName+" "+typeClass);
		typeResolver.put(typeName, typeClass);
	}*/

	public void registerType(final Class<? extends PsyObject> typeClass)
	{
		final var typeName=typeClass.getAnnotation(Type.class).value();
		System.out.println("REGISTER: "+typeName+" "+typeClass);
		typeResolver.put(typeName, typeClass);
	}

	public Class<? extends PsyObject> resolveType(final String typeName)
	{
		return typeResolver.get(typeName);
	}

	public void loadLibraryResource(final PsyStringy oResourceName)
		throws PsyException
	{
		final PsyArraylike<PsyStringy> oLibraryPath
			=dstack.load("librarypath");
		final var resourceName=oResourceName.stringValue().replace('.', '/');
		for(var oPathItem: oLibraryPath)
		{
			final var oFullResourceName
				=new PsyName(oPathItem.stringValue()+'/'+resourceName+".psy");
			if(FileSystem.psyFileExists(oFullResourceName).booleanValue()
					&& FileSystem.psyIsFile(oFullResourceName).booleanValue())
			{
				//System.out.println(FileSystem.psyFileAbsolutePath(oFullResourceName).stringValue());
				final var resourceID="file:"+FileSystem.psyFileAbsolutePath(oFullResourceName).stringValue();
				if(resourceRegistry.containsKey(oResourceName.stringValue()))
					System.out.println("Already loaded: "+resourceID);
				else
				{
					resourceRegistry.put(oResourceName.stringValue(), resourceID);
					new PsyFileReader(oFullResourceName).eval(this);
				}
				return;
			}
		}
		throw new PsyUndefinedException(); // TODO: more appropriate exception
	}

	public void psyRequire(final PsyStringy oResourceName)
		throws PsyException
	{
		//classLoader.findResource("jline/History.class");
		/*try
		{
			//System.out.println(classLoader.loadClass("org.tukaani.xz.ArrayCache"));
			System.out.println(classLoader.loadClass("jline.History"));
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("CLASS NOT FOUND");
		}*/

		loadLibraryResource(oResourceName);
	}

	private final OperandStack ostack;
	private final DictStack dstack;
	private final ExecutionStack estack;
	private final ProcStack procstack;
	private final NamespacePool nspool=new NamespacePool();
	private final Stack<Integer>
		loopstack=new Stack<Integer>(),
		stopstack=new Stack<Integer>();
	private boolean stopFlag=false;
	private boolean running=true;
	private final java.util.HashMap<String, Class<? extends PsyObject>> typeResolver
		=new java.util.HashMap<String, Class<? extends PsyObject>>();
	private final java.util.HashMap<String, String> resourceRegistry
		=new java.util.HashMap<String, String>();

	private final coneforest.psylla.ClassLoader classLoader
		=new coneforest.psylla.ClassLoader()
			{
				@Override
				protected Iterable<String> getClassPath()
					throws PsyException
				{
					final var parentIterator
						=((PsyArraylike<PsyStringy>)systemDict().get("classpath")).iterator();
					return new Iterable<String>()
						{
							@Override
							public java.util.Iterator<String> iterator()
							{
								return new java.util.Iterator<String>()
									{
										@Override
										public String next()
										{
											return parentIterator.next().stringValue();
										}

										@Override
										public boolean hasNext()
										{
											return parentIterator.hasNext();
										}
									};
							}
						};
				}
			};
}
