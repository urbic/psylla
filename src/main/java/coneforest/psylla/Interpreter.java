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
		}
		catch(final PsyErrorException e)
		{
			throw new AssertionError(e);
		}
	}

	@Override
	public void fork()
		throws PsyErrorException
	{
		final var ostack=operandStackBacked(1);
		final var o=ostack.getBacked(0);
		final var forkedDstack=(DictStack)dstack.clone();
		final var oForkedContext=new Interpreter()
			{
				{
					dstack=forkedDstack;
				}

				@Override
				public void run()
				{
					o.invoke(this);
					handleExecutionStack();
					if(getStopFlag())
					{
						//PsyErrorDict.OP_HANDLEERROR.invoke(this);
						return;
					}
				}
			};
		final int i=ostack.findMarkPosition();
		final int ostackSize=ostack.size();
		final var forkedOstack=oForkedContext.operandStack();
		for(int j=i+1; j<ostackSize; j++)
			forkedOstack.push(ostack.get(j));
		ostack.setSize(i);
		ostack.push(oForkedContext);
		oForkedContext.start();
	}

	/*public void importType(final String typeName)
		throws PsyErrorException
	{
		PsyNamespace.namespace("system").psyImport(PsyNamespace.namespace(TypeResolver.resolve(typeName)));
	}*/

	/**
	*	Returns the operand stack.
	*
	*	@return the operand stack.
	*/
	@Override
	public OperandStack operandStack()
	{
		return ostack;
	}

	@Override
	public OperandStack operandStackBacked(final int count)
		throws PsyErrorException
	{
		ostack.popOperands(count);
		return ostack;
	}

	/**
	*	Returns the dictionary stack.
	*
	*	@return the dictionary stack.
	*/
	@Override
	public DictStack dictStack()
	{
		return dstack;
	}

	/**
	*	Returns the execution stack.
	*
	*	@return the execution stack.
	*/
	@Override
	public ExecutionStack executionStack()
	{
		return estack;
	}

	public java.lang.ClassLoader classLoader()
	{
		return classLoader;
	}

	public PsyFormalDict where(final String name)
	{
		return dstack.where(name);
	}

	@Override
	public PsyFormalDict psyWhere(final PsyTextual oKey)
	{
		return where(oKey.stringValue());
	}

	public <T extends PsyObject> T load(final String name)
		throws PsyErrorException
	{
		final var prefixOffset=name.indexOf('@');
		if(prefixOffset==-1)
			return (T)dstack.load(name);
		return (T)nspool.get(name.substring(0, prefixOffset))
				.get(name.substring(prefixOffset+1));
	}

	@Override
	public <T extends PsyObject> T psyLoad(final PsyTextual oKey)
		throws PsyErrorException
	{
		return this.<T>load(oKey.stringValue());
	}

	@Override
	public void handleExecutionStack()
	{
		while(estack.size()>0)
			estack.pop().execute(this);
	}

	@Override
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
	@Override
	public PsyFormalDict currentDict()
	{
		return dstack.peek();
	}

	/**
	*	Returns system dictionary.
	*
	*	@return the system dictionary.
	*/
	@Override
	public PsyFormalDict systemDict()
	{
		return dstack.get(0);
	}

	/**
	*	Returns the user dictionary.
	*
	*	@return the user dictionary.
	*/
	public PsyFormalDict userDict()
	{
		return dstack.get(1);
	}

	public NamespacePool namespacePool()
	{
		return nspool;
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
		throws PsyErrorException
	{
		if(randomSeed!=null)
			((PsyRandom)systemDict().get("stdrandom"))
				.psySetSeed(PsyInteger.of(randomSeed));
	}

	public void setClassPath(final String[] classPath)
		throws PsyErrorException
	{
		final var oClassPath
			=(PsyFormalArray<PsyTextual>)systemDict().get("classpath");
		final var envClassPath=System.getenv("PSYLLA_CLASSPATH");
		if(envClassPath!=null)
			for(final var pathItem: envClassPath.split(java.io.File.pathSeparator))
				oClassPath.psyAppend(new PsyName(pathItem));
		if(classPath!=null)
			for(final var pathItem: classPath)
				oClassPath.psyAppend(new PsyName(pathItem));
	}

	public void setLibraryPath(final String[] libraryPath)
		throws PsyErrorException
	{
		final var oLibraryPath
			=(PsyFormalArray<PsyTextual>)systemDict().get("librarypath");
		final var envLibraryPath=System.getenv("PSYLLA_LIBRARYPATH");
		if(envLibraryPath!=null)
			for(final var pathItem: envLibraryPath.split(java.io.File.pathSeparator))
				oLibraryPath.psyAppend(new PsyName(pathItem));
		if(libraryPath!=null)
			for(final var pathItem: libraryPath)
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

	@Override
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

				// If "stop" is invoked outside an explicit stopping context
				if(getStopFlag())
					return;
			}
			if(procstack.size()>initProcLevel)
			{
				var e=new PsySyntaxErrorException();
				e.setEmitter(oReader);
				throw e;
			}

			dstack.<PsyFlushable>load("stdout").psyFlush();
			dstack.<PsyFlushable>load("stderr").psyFlush();
		}
		catch(final PsyErrorException e)
		{
			e.setEmitter(oReader); // IMPORTANT
			//handleError(e);
			e.setStacks(ostack, estack, dstack);
			e.invoke(this);
			// TODO
			if(getStopFlag())
				PsyErrorDict.OP_HANDLEERROR.invoke(this);
		}
		catch(final TokenMgrError ex)
		{
			var e=new PsySyntaxErrorException();
			e.setEmitter(oReader);
			e.setStacks(ostack, estack, dstack);
			e.invoke(this);
			// TODO
			if(getStopFlag())
				PsyErrorDict.OP_HANDLEERROR.invoke(this);
		}
	}

	@Override
	public void interpretBraced(final PsyReader oReader)
		throws PsyErrorException
	{
		procstack.push(new PsyProc());
		interpret(oReader);
		if(procstack.size()==0)
		{
			var e=new PsySyntaxErrorException();
			e.setEmitter(oReader);
			e.setStacks(ostack, estack, dstack);
			e.invoke(this);
		}
		final var proc=procstack.pop();
		if(procstack.size()>0)
			procstack.peek().psyAppend(proc);
		else
			ostack.push(proc);
	}

	private void processToken(final Token token)
		throws PsyErrorException
	{
		if(procstack.size()==0)
		{
			switch(token.kind)
			{
				case ParserConstants.COMMAND:
					parseToken(token).execute(this);
					handleExecutionStack();
					break;
				case ParserConstants.INTEGRAL:
				case ParserConstants.REAL:
				case ParserConstants.STRING:
				case ParserConstants.NAME:
				case ParserConstants.IMMEDIATE:
				case ParserConstants.REGEXP:
				case ParserConstants.LITERAL:
					ostack.push(parseToken(token));
					break;
				case ParserConstants.OPEN_BRACE:
					procstack.push(new PsyProc());
					break;
				case ParserConstants.CLOSE_BRACE:
					throw new PsySyntaxErrorException();
				case ParserConstants.EOF:
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
				case ParserConstants.INTEGRAL:
				case ParserConstants.REAL:
				case ParserConstants.STRING:
				case ParserConstants.NAME:
				case ParserConstants.COMMAND:
				case ParserConstants.IMMEDIATE:
				case ParserConstants.REGEXP:
				case ParserConstants.LITERAL:
					procstack.peek().psyAppend(parseToken(token));
					break;
				case ParserConstants.EOF:
					throw new PsySyntaxErrorException();
			}
		}
	}

	private PsyObject parseToken(final Token token)
		throws PsyErrorException
	{
		// TODO: make TokensParser inner class of Interpreter
		var image=token.image;
		switch(token.kind)
		{
			case ParserConstants.IMMEDIATE:
				return dstack.load(image.substring(2));
			case ParserConstants.NAME:
				return PsyName.parseLiteral(image);
			case ParserConstants.INTEGRAL:
				return PsyIntegral.parseLiteral(image);
			case ParserConstants.REAL:
				return PsyReal.parseLiteral(image);
			case ParserConstants.STRING:
				return PsyString.parseLiteral(image);
			case ParserConstants.REGEXP:
				return PsyRegExp.parseLiteral(image);
			case ParserConstants.COMMAND:
				return new PsyCommand(image);
			case ParserConstants.LITERAL:
				return parseLiteralImage(image);
			default:
				throw new AssertionError();
		}
	}

	private PsyObject parseLiteralImage(final String image)
		throws
			PsyUndefinedException, // TODO
			PsySyntaxErrorException
	{
		final int i=image.indexOf('=');
		final String typeName=image.substring(0, i);
		final var typeClass=TypeResolver.resolve(typeName);
		try
		{
			final var mh=java.lang.invoke.MethodHandles.lookup().findStatic(
					typeClass,
					"parseLiteral",
					java.lang.invoke.MethodType.methodType(typeClass, String.class));
			return typeClass.cast(mh.invoke(image.substring(i+2, image.length()-1)));
		}
		catch(final NoSuchMethodException|IllegalAccessException ex)
		{
			throw new PsyUndefinedException();	// TODO more appropriate exception
		}
		catch(final Throwable ex)
		{
			throw new PsySyntaxErrorException();
		}
	}

	public PsyFormalDict errorDict()
		throws PsyErrorException
	{
		return (PsyFormalDict)systemDict().get("errordict");
		//return PsyNamespace.namespace("errordict");
	}

	/*
	@Override
	public void handleError(final PsyErrorException oException)
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
				stop_();
		}
		catch(final PsyErrorException e)
		{
			throw new AssertionError(e);
		}
	}
	*/

	// TODO
	public void showStacks()
	{
		System.out.print("Operand stack:\n\t");
		for(final var o: ostack)
			System.out.print(" "+o);
		System.out.println();

		System.out.print("Execution stack:\n\t");
		for(final var o: estack)
			System.out.print(" "+o);
		System.out.println();

		/*
		System.out.println("Dictionary stack:");
		System.out.print("⊢\t");
		for(final PsyObject obj: dstack)
			System.out.print(" "+obj);
		System.out.println();
		*/

		System.out.print("Loop level stack:\n\t");
		for(final var o: loopstack)
			System.out.print(" "+o);
		//loopstack.forEach(l->System.out.print(l));
		System.out.println();
	}

	@Override
	public int execLevel()
	{
		return estack.size();
	}

	@Override
	public boolean getStopFlag()
	{
		return stopFlag;
	}

	@Override
	public void setStopFlag(final boolean stopFlag)
	{
		this.stopFlag=stopFlag;
	}

	@Override
	public int pushLoopLevel()
	{
		final var level=estack.size();
		loopstack.push(level);
		return level;
	}

	@Override
	public int popLoopLevel()
	{
		return loopstack.size()>0? loopstack.pop(): -1;
	}

	@Override
	public int currentLoopLevel()
	{
		return loopstack.size()>0? loopstack.peek(): -1;
	}

	@Override
	public int pushStopLevel()
	{
		final var level=estack.size();
		stopstack.push(level);
		return level;
	}

	@Override
	public int popStopLevel()
	{
		return stopstack.size()>0? stopstack.pop(): -1;
	}

	@Override
	public int currentStopLevel()
	{
		return stopstack.size()>0? stopstack.peek(): -1;
	}

	public void setScriptName(final String scriptName)
	{
		systemDict().put("script", new PsyName(scriptName));
	}

	public void setShellArguments(final String[] args)
		throws PsyErrorException
	{
		final var oArguments
			=(PsyArray)systemDict().get("arguments");
		for(final var arg: args)
			oArguments.psyAppend(new PsyName(arg));
	}

	public void setEnvironment(final java.util.Map<String, String> env)
	{
		final var environment=new PsyDict();
		for(final var entry: env.entrySet())
			environment.put(entry.getKey(), new PsyName(entry.getValue()));
		systemDict().put("environment", environment);
	}

	@Override
	public void quit()
	{
		running=false;
		stopFlag=false;
		estack.clear();
	}

	@Override
	public void repl()
		throws PsyErrorException
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
				final var oReader=new PsyReader(new java.io.StringReader(line));
				final var parser=new Parser(oReader);
				try
				{
					while(running)
					{
						final var token=parser.getNextToken();
						if(token.kind==ParserConstants.EOF)
							break;
						processToken(token);
						// If "stop" invoked outside an explicit stopping context
						if(getStopFlag())
						{
							setStopFlag(false);
							break;
						}
					}
				}
				catch(final PsyErrorException e)
				{
					e.setEmitter(oReader);
					e.setStacks(ostack, estack, dstack);
					e.invoke(this);
					// TODO
					if(getStopFlag())
						PsyErrorDict.OP_HANDLEERROR.invoke(this);
				}
				catch(final TokenMgrError ex)
				{
					var e=new PsySyntaxErrorException();
					e.setEmitter(oReader);
					e.setStacks(ostack, estack, dstack);
					e.invoke(this);
					// TODO
					if(getStopFlag())
						PsyErrorDict.OP_HANDLEERROR.invoke(this);
				}
			}
		}
		catch(final java.io.IOException ex)
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
		sb.append("{".repeat(procstack.size()));
		if(ostack.size()>0)
			sb.append("<"+ostack.size());
		sb.append("> ");
		return sb.toString();
	}

	@Override
	public void stop_()
	{
		setStopFlag(true);
		if(currentStopLevel()>-1)
			estack.setSize(currentStopLevel());
		else
			quit();
	}

	public boolean loadLibraryResource(final String resourceName)
		throws PsyErrorException
	{
		final var oLibraryPath=(PsyFormalArray<PsyTextual>)dstack.load("librarypath");
		final var filePath=resourceName.replace('.', '/');
		for(final var oPathItem: oLibraryPath)
		{
			final var oFullResourceName
				=new PsyName(oPathItem.stringValue()+'/'+filePath+".psy");
			if(PsyFileSystem.psyFileExists(oFullResourceName).booleanValue()
					&& PsyFileSystem.psyIsFile(oFullResourceName).booleanValue())
			{
				final var resourceID="file:"+PsyFileSystem.psyFileAbsolutePath(oFullResourceName).stringValue();
				if(resourceRegistry.containsKey(resourceName))
				{
					System.out.println("Already loaded: "+resourceID);
					return true;
				}
				else
				{
					resourceRegistry.put(resourceName, resourceID);
					new PsyFileReader(oFullResourceName).psyEval();
				}
				return true;
			}
		}
		return false;
	}

	/*
	public void loadAnnotatedOperators(final Class<PsyObject> clazz)
	{
		for(final var method: clazz.getDeclaredMethods())
			if(method.isAnnotationPresent(Operator.class))
			{
				final var oOperator=new PsyOperator.Method(method);
				nspool.get(oOperator.getPrefix()).put(oOperator.getSimpleName(), oOperator);
				//oNamespace.put(method.getDeclaredAnnotation(Operator.class).value(),
				//		PsyOperator.of(method));
			}
		//
		for(final var constructor: clazz.getDeclaredConstructors())
			if(constructor.isAnnotationPresent(Operator.class))
				oNamespace.put(constructor.getDeclaredAnnotation(Operator.class).value(),
						PsyOperator.of(constructor));
		//
	}
	*/

	public boolean loadType(final String typeName)
		throws PsyErrorException
	{
		try
		{
			final var clazz=
				(Class<? extends PsyObject>)Class.forName(
						(new java.io.BufferedReader(new java.io.InputStreamReader(
								classLoader.getResourceAsStream(
										"META-INF/psylla/type/"+typeName)))).readLine(),
						true,
						classLoader);
			final var resourceID="class:"+clazz.getName();
			if(resourceRegistry.containsKey(typeName))
			{
				System.out.println("Already loaded: "+resourceID);
				return true;
			}
			else
				resourceRegistry.put(typeName, resourceID);
			//final var oNamespace=PsyNamespace.namespace(clazz.getAnnotation(Type.class).value());
			/*
			for(final var method: clazz.getDeclaredMethods())
			{
				if(method.isAnnotationPresent(Operator.class))
				{
					final var operatorName=method.getDeclaredAnnotation(Operator.class).value();
					oNamespace.put(operatorName, PsyOperator.of(method));
				}
			}
			for(final var constructor: clazz.getDeclaredConstructors())
			{
				if(constructor.isAnnotationPresent(Operator.class))
				{
					final var operatorName=constructor.getDeclaredAnnotation(Operator.class).value();
					oNamespace.put(operatorName, PsyOperator.of(constructor));
				}
			}
			for(final var field: clazz.getDeclaredFields())
			{
				if(field.isAnnotationPresent(Operator.class))
				{
					System.out.println("FIELD");
					final var operatorName=field.getDeclaredAnnotation(Operator.class).value();
					oNamespace.put(operatorName, (PsyOperator)field.get(null));
				}
			}
			*/
			/*
			for(final var field: clazz.getDeclaredFields())
			{
				if(field.isAnnotationPresent(Export.class))
				{
					//System.out.println("FIELD");
					//final var operatorName=field.getDeclaredAnnotation(Operator.class).value();
					//oNamespace.put(operatorName, (PsyOperator)field.get(null));
					final var operators=(PsyOperator[])field.get(null);
					for(var oOperator: operators)
					{
						//System.out.println(oOperator.getName());
						systemDict().put(oOperator.getName(), oOperator);
					}

				}
			}
			*/
			return true;
		}
		//catch(java.io.IOException|ClassNotFoundException|NullPointerException|IllegalAccessException e)
		catch(final java.io.IOException|ClassNotFoundException|NullPointerException ex)
		{
			return false;
		}
	}

	@Override
	public void psyRequire(final PsyTextual oResourceName)
		throws PsyErrorException
	{
		final var resourceName=oResourceName.stringValue();
		//classLoader.findResource("jline/History.class");
		/*try
		{
			//System.out.println(classLoader.loadClass("org.tukaani.xz.ArrayCache"));
			System.out.println(classLoader.loadClass("jline.History"));
		}
		catch(ClassNotFoundException ex)
		{
			System.out.println("CLASS NOT FOUND");
		}*/

		if(loadType(resourceName))
			return;
		if(loadLibraryResource(resourceName))
			return;
		throw new PsyUndefinedException(); // TODO: more appropriate exception
	}

	private final OperandStack ostack;
	protected DictStack dstack;
	private final ExecutionStack estack;
	private final ProcStack procstack;
	private final Stack<Integer>
		loopstack=new Stack<Integer>(),
		stopstack=new Stack<Integer>();

	//private IntStack
	//	loopstack=new IntStack(),
	//	stopstack=new IntStack();
	private final java.util.HashMap<String, String> resourceRegistry
		=new java.util.HashMap<String, String>();
	private final NamespacePool nspool=new NamespacePool();

	private boolean stopFlag=false;
	private boolean running=true;

	private final java.lang.ClassLoader classLoader
		=new coneforest.psylla.ClassLoader()
			{
				@Override
				protected Iterable<String> getClassPath()
					throws PsyErrorException
				{
					final var parentIterator
						=((PsyFormalArray<PsyTextual>)systemDict().get("classpath")).iterator();
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
