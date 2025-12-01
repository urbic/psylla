package coneforest.psylla.runtime;

import coneforest.psylla.core.*;
import coneforest.psylla.runtime.parser.Parser;
import coneforest.psylla.runtime.parser.ParserConstants;
import coneforest.psylla.runtime.parser.Token;
import coneforest.psylla.runtime.parser.TokenMgrError;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringJoiner;
import jline.ConsoleReader;

/**
*	The Psylla language interpreter.
*/
public class Interpreter
	//extends Thread
	implements PsyContext
{
	protected DictStack dstack;
	private final OperandStack ostack;
	private final ExecutionStack estack;
	private final ProcStack procstack;
	private final HashMap<String, String> resourceRegistry=new HashMap<>();
	private final NamespacePool nspool=new NamespacePool();
	//private final Thread thread=Thread.ofVirtual().unstarted(this);
	private final Thread thread=Thread.ofPlatform().unstarted(this);
	/*private final Thread thread=new Thread()
		{
			@Override
			public void run()
			{
				Interpreter.this.run();
			}
		};*/

	private boolean stopped=false;
	private boolean running=true;

	private final ClassLoader classLoader=new DynamicClassLoader()
		{
			@Override
			protected Iterable<String> getClassPath()
				throws PsyUndefinedException
			{
				@SuppressWarnings("unchecked")
				final var parentIterator
					=((PsyFormalArray<PsyTextual>)systemDict().get("classpath")).iterator();
				return new Iterable<String>()
					{
						@Override
						public Iterator<String> iterator()
						{
							return new Iterator<String>()
								{
									@Override
									public boolean hasNext()
									{
										return parentIterator.hasNext();
									}

									@Override
									public String next()
									{
										return parentIterator.next().stringValue();
									}
								};
						}
					};
			}
		};

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
		}
		catch(final PsyErrorException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public void fork()
		throws PsyStackUnderflowException, PsyUnmatchedMarkException
	{
		final var ostack=operandStackBacked(1);
		final var o=ostack.getBacked(0);
		final var forkedDstack=dstack.clone();
		final var oForkedContext=new Interpreter()
			{
				{
					dstack=forkedDstack;
				}

				@Override
				public void run()
				{
					o.invoke(this);
					handleExecutionStack(0);
					if(getStopped())
						return;
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

	@Override
	public OperandStack operandStack()
	{
		return ostack;
	}

	@Override
	public OperandStack operandStackBacked(final int count)
		throws PsyStackUnderflowException
	{
		ostack.popOperands(count);
		return ostack;
	}

	@Override
	public DictStack dictStack()
	{
		return dstack;
	}

	@Override
	public ExecutionStack executionStack()
	{
		return estack;
	}

	/**
	*	{@return the interpreter’s class loader}
	*/
	public ClassLoader classLoader()
	{
		return classLoader;
	}

	@SuppressWarnings("unchecked")
	protected <T extends PsyObject> T load(final String name)
		throws PsyUndefinedException
	{
		final var prefixOffset=name.indexOf('@');
		if(prefixOffset==-1)
			return dstack.<T>load(name);
		return (T)nspool.get(name.substring(0, prefixOffset))
				.get(name.substring(prefixOffset+1));
	}

	@Override
	public <T extends PsyObject> T psyLoad(final PsyTextual oKey)
		throws PsyUndefinedException
	{
		return this.<T>load(oKey.stringValue());
	}

	@Override
	public void handleExecutionStack(final int level)
	{
		while(estack.size()>level)
			estack.pop().execute(this);
	}

	@Override
	public PsyFormalDict<PsyObject> /*<? extends PsyObject>*/ currentDict()
	{
		return dstack.peek();
	}

	@Override
	public PsyFormalDict<PsyObject> /*<? extends PsyObject>*/ systemDict()
	{
		return dstack.get(0);
	}

	@Override
	public PsyFormalDict<PsyObject> /*<? extends PsyObject>*/ userDict()
	{
		return dstack.get(1);
	}

	@Override
	public NamespacePool namespacePool()
	{
		return nspool;
	}

	/**
	*	{@return the current namespace}
	*/
	public PsyNamespace currentNamespace()
	{
		return dstack.currentNamespace();
	}

	/**
	*	Sets the interpreter’s standard reader.
	*
	*	@param reader the reader.
	*/
	public void setReader(final Reader reader)
	{
		systemDict().put("stdin", new PsyReader(reader));
	}

	/**
	*	Sets the interpreter’s standard writer.
	*
	*	@param writer the writer.
	*/
	public void setWriter(final Writer writer)
	{
		systemDict().put("stdout", new PsyWriter(writer));
	}

	/**
	*	Sets the interpreter’s standard error writer.
	*
	*	@param writer the error writer.
	*/
	public void setErrorWriter(final Writer writer)
	{
		systemDict().put("stderr", new PsyWriter(writer));
	}

	/**
	*	Reseeds the interpreter’s standard pseudorandom generator, using the given seed.
	*
	*	@param randomSeed the seed.
	*	@throws PsyUndefinedException when TODO
	*/
	public void setRandomSeed(final Long randomSeed)
		throws PsyUndefinedException
	{
		if(randomSeed!=null)
			((PsyRandom)systemDict().get("stdrandom"))
					.psySetSeed(PsyInteger.of(randomSeed));
	}

	public void setClassPath(final String[] classPath)
		throws
			PsyLimitCheckException,
			PsyRangeCheckException,
			PsyUndefinedException
	{
		@SuppressWarnings("unchecked")
		final var oClassPath
			=(PsyFormalArray<PsyTextual>)systemDict().get("classpath");
		final var envClassPath=System.getenv("PSYLLA_CLASSPATH");
		if(envClassPath!=null)
			for(final var pathItem: envClassPath.split(File.pathSeparator))
				oClassPath.psyAppend(new PsyString(pathItem));
		if(classPath!=null)
			for(final var pathItem: classPath)
				oClassPath.psyAppend(new PsyString(pathItem));
	}

	public void setLibraryPath(final String[] libraryPath)
		throws
			PsyLimitCheckException,
			PsyRangeCheckException,
			PsyUndefinedException
	{
		@SuppressWarnings("unchecked")
		final var oLibraryPath
			=(PsyFormalArray<PsyTextual>)systemDict().get("librarypath");
		final var envLibraryPath=System.getenv("PSYLLA_LIBRARYPATH");
		if(envLibraryPath!=null)
			for(final var pathItem: envLibraryPath.split(File.pathSeparator))
				oLibraryPath.psyAppend(new PsyString(pathItem));
		if(libraryPath!=null)
			for(final var pathItem: libraryPath)
				oLibraryPath.psyAppend(new PsyString(pathItem));
	}

	public void interpret(final Reader reader)
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
				if(getStopped())
					return;
			}
			// Incomplete procedure read
			if(procstack.size()>initProcLevel)
			{
				final var e=new PsySyntaxErrorException();
				e.setEmitter(oReader);
				throw e;
			}

			dstack.<PsyFlushable>load("stdout").psyFlush();
			dstack.<PsyFlushable>load("stderr").psyFlush();
		}
		catch(final PsyErrorException e)
		{
			e.setEmitter(oReader); // IMPORTANT
			e.setStacks(ostack, estack, dstack);
			e.invoke(this);
		}
		catch(final TokenMgrError ex)
		{
			//System.err.println(ex);
			final var e=new PsySyntaxErrorException();
			e.setEmitter(oReader);
			e.setStacks(ostack, estack, dstack);
			e.invoke(this);
		}
	}

	@Override
	public void interpretBraced(final PsyReader oReader)
		throws PsyLimitCheckException
	{
		procstack.push(new PsyProc());
		interpret(oReader);
		if(procstack.size()==0)
		{
			final var e=new PsySyntaxErrorException();
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
		throws
			PsyInvalidRegExpException,
			PsyInternalErrorException,
			PsyLimitCheckException,
			PsySyntaxErrorException,
			PsyUndefinedException,
			PsyUndefinedResultException
	{
		if(procstack.size()==0)
		{
			switch(token.kind)
			{
				case ParserConstants.NAME->
					{
						parseToken(token).execute(this);
						handleExecutionStack(0);
					}
				case ParserConstants.RATIONAL,
						ParserConstants.REAL,
						ParserConstants.STRING,
						ParserConstants.IMMEDIATE,
						ParserConstants.REGEXP,
						ParserConstants.LITERAL->
					ostack.push(parseToken(token));
				case ParserConstants.OPEN_BRACE->
					procstack.push(new PsyProc());
				case ParserConstants.CLOSE_BRACE->
					throw new PsySyntaxErrorException();
				case ParserConstants.EOF->{}
			}
		}
		else
		{
			switch(token.kind)
			{
				case ParserConstants.OPEN_BRACE->
					procstack.push(new PsyProc());
				case ParserConstants.CLOSE_BRACE->
					{
						final var proc=procstack.pop();
						if(procstack.size()>0)
							procstack.peek().psyAppend(proc);
						else
							ostack.push(proc);
					}
				case ParserConstants.RATIONAL,
						ParserConstants.REAL,
						ParserConstants.NAME,
						ParserConstants.STRING,
						ParserConstants.IMMEDIATE,
						ParserConstants.REGEXP,
						ParserConstants.LITERAL
					->procstack.peek().psyAppend(parseToken(token));
				case ParserConstants.EOF->
					throw new PsySyntaxErrorException();
			}
		}
	}

	private PsyObject parseToken(final Token token)
		throws
			PsyInvalidRegExpException,
			PsyInternalErrorException,
			PsySyntaxErrorException,
			PsyUndefinedException,
			PsyUndefinedResultException
	{
		final var image=token.image;
		return switch(token.kind)
			{
				case ParserConstants.IMMEDIATE->dstack.load(image.substring(2));
				case ParserConstants.STRING->PsyString.parseLiteral(image);
				case ParserConstants.RATIONAL->PsyRational.parseLiteral(image);
				case ParserConstants.REAL->PsyReal.parseLiteral(image);
				case ParserConstants.REGEXP->PsyRegExp.parseLiteral(image);
				case ParserConstants.NAME->new PsyName(image);
				case ParserConstants.LITERAL->parseLiteralImage(image);
				default->throw new PsyInternalErrorException();
			};
	}

	private PsyObject parseLiteralImage(final String image)
		throws
			PsyUndefinedException, // TODO
			PsySyntaxErrorException
	{
		final int i=image.indexOf('=');
		final var typeName=image.substring(0, i);
		final var typeClass=TypeResolver.resolve(typeName);
		try
		{
			final var mh=MethodHandles.lookup().findStatic(
					typeClass,
					"parseLiteral",
					MethodType.methodType(typeClass, String.class));
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

	@SuppressWarnings("unchecked")
	public PsyFormalDict<PsyObject> errorDict()
		throws PsyUndefinedException
	{
		return (PsyFormalDict<PsyObject>)systemDict().get("errordict");
		//return PsyNamespace.namespace("errordict");
	}

	/*
	@Override
	public void handleError(final PsyErrorException oException)
	{
		final var errorName=oException.getName();
		final var errorObj=new PsyDict();
		errorObj.put("newerror", PsyBoolean.TRUE);
		errorObj.put("errorname", new PsyString(errorName));
		errorObj.put("emitter", oException.getEmitter());
		errorObj.put("ostack", new PsyArray((ArrayList<PsyObject>)ostack.clone()));
		errorObj.put("estack", new PsyArray((ArrayList<PsyObject>)estack.clone()));
		errorObj.put("dstack", new PsyArray((ArrayList<PsyObject>)dstack.clone()));
		systemDict().put("$error", errorObj);

		try
		{
			final var errorDict=errorDict();
			if(errorDict.known(errorName))
				errorDict.get(errorName).invoke(this);
			else
				stop();
		}
		catch(final PsyErrorException e)
		{
			throw new AssertionError(e);
		}
	}
	*/

	@Override
	public void showStacks(final PrintWriter pw)
	{
		pw.print(Messages.getString("handleErrorMessageOStack"));
		{
			final var sj=new StringJoiner(" ", "\n\t", "");
			sj.setEmptyValue(" "+Messages.getString("handleErrorMessageEmpty"));
			ostack.forEach(o->sj.add(o.toSyntaxString()));
			pw.println(sj.toString());
		}

		pw.print(Messages.getString("handleErrorMessageEStack"));
		{
			final var sj=new StringJoiner(" ", "\n\t", "");
			sj.setEmptyValue(" "+Messages.getString("handleErrorMessageEmpty"));
			estack.forEach(o->sj.add(o.toSyntaxString()));
			pw.println(sj.toString());
		}
	}

	@Override
	public int execLevel()
	{
		return estack.size();
	}

	@Override
	public boolean getStopped()
	{
		return stopped;
	}

	@Override
	public void setStopped(final boolean stopped)
	{
		this.stopped=stopped;
	}

	public void setScriptName(final String scriptName)
	{
		systemDict().put("script", new PsyString(scriptName));
	}

	public void setShellArguments(final String[] args)
		throws PsyLimitCheckException, PsyUndefinedException
	{
		final var oArguments=(PsyArray)systemDict().get("arguments");
		for(final var arg: args)
			oArguments.psyAppend(new PsyString(arg));
	}

	public void setEnvironment(final Map<String, String> env)
	{
		systemDict().put("environment", new PsyEnvironment(env));
	}

	@Override
	public void quit()
	{
		running=stopped=false;
		estack.clear();
	}

	@Override
	public void repl()
		throws PsyIOErrorException
	{
		try
		{
			final var cr=new ConsoleReader();
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
				final var oReader=new PsyReader(new StringReader(line));
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
						if(getStopped())
						{
							setStopped(false);
							break;
						}
					}
				}
				catch(final PsyErrorException e)
				{
					e.setEmitter(oReader);
					e.setStacks(ostack, estack, dstack);
					e.invoke(this);
				}
				catch(final TokenMgrError ex)
				{
					final var e=new PsySyntaxErrorException();
					e.setEmitter(oReader);
					e.setStacks(ostack, estack, dstack);
					e.invoke(this);
				}
			}
		}
		catch(final IOException ex)
		{
			throw new PsyIOErrorException();
		}
	}

	/**
	*	{@return the Psylla banner}
	*/
	protected String banner()
	{
		return String.format(Messages.getString("banner"), Version.getVersion());
	}

	/**
	*	{@return the REPL prompt}
	*/
	public String prompt()
	{
		final var sb=new StringBuilder("PSYLLA");
		sb.append("{".repeat(procstack.size()));
		if(ostack.size()>0)
			sb.append("<"+ostack.size());
		sb.append("> ");
		return sb.toString();
	}

	public void start()
	{
		thread.start();
		//thread=Thread.startVirtualThread(this::run);
	}

	@Override
	public void run()
	{
	}

	@Override
	public void join()
		throws InterruptedException
	{
		thread.join();
	}

	//@Override
	public void join(final long millis)
		throws InterruptedException
	{
		thread.join(millis);
	}

	@Override
	public long getId()
	{
		return thread.threadId();
	}

	@Override
	public void stop()
	{
		setStopped(true);
		estack.exitStop();	// TODO quit()
	}

	public boolean loadLibraryResource(final String resourceName)
		throws
			PsyFileAccessDeniedException,
			PsyFileNotFoundException,
			PsyIOErrorException,
			PsySecurityErrorException,
			PsyUndefinedException,
			PsyErrorException	// TODO
	{
		final var oLibraryPath=dstack.<PsyFormalArray<PsyTextual>>load("librarypath");
		final var filePath=resourceName.replace('.', '/');
		for(final var oPathItem: oLibraryPath)
		{
			final var oFullResourceName
				=new PsyString(oPathItem.stringValue()+'/'+filePath+".psy");
			if(PsyFileSystem.psyFileExists(oFullResourceName).booleanValue()
					&& PsyFileSystem.psyIsFile(oFullResourceName).booleanValue())
			{
				final var resourceID
					="file:"+PsyFileSystem.psyFileAbsolutePath(oFullResourceName).stringValue();
				if(resourceRegistry.containsKey(resourceName))
					// TODO
					System.out.println("Already loaded: "+resourceID);
				else
				{
					resourceRegistry.put(resourceName, resourceID);
					new PsyFileReader(oFullResourceName).psyEval(this);
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
	{
		// TODO check NPE at readLine
		try
		{
			final var resourceStream=classLoader.getResourceAsStream(
					"META-INF/psylla/type/"+typeName);
			if(resourceStream==null)
				return false;
			final var className=(new BufferedReader(new InputStreamReader(
					resourceStream))).readLine();
			if(className==null)
				return false;
			final var clazz=Class.forName(
				className, true, classLoader).asSubclass(PsyObject.class);
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
					for(final var oOperator: operators)
					{
						//System.out.println(oOperator.getName());
						systemDict().put(oOperator.getName(), oOperator);
					}

				}
			}
			*/
			return true;
		}
		//catch(IOException|ClassNotFoundException|NullPointerException|IllegalAccessException e)
		catch(final IOException|ClassNotFoundException ex)
		{
			return false;
		}
	}

	@Override
	public void psyRequire(final PsyTextual oResourceName)
		throws
			PsyFileAccessDeniedException,
			PsyFileNotFoundException,
			PsyIOErrorException,
			PsySecurityErrorException,
			PsyUndefinedException,
			PsyErrorException	// TODO
	{
		final var resourceName=oResourceName.stringValue();

		if(loadType(resourceName))
			return;
		if(loadLibraryResource(resourceName))
			return;
		throw new PsyUndefinedException(); // TODO: more appropriate exception
	}

}
