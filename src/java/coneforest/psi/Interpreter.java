package coneforest.psi;
import coneforest.psi.core.*;

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
		try
		{
			ostack=new OperandStack();
			estack=new ExecutionStack();
			procstack=new ProcStack();
			dstack=new DictStack();
			pushStopLevel();
		}
		catch(PsiException e)
		{
			//e.printStackTrace();
			throw new AssertionError();
		}
	}

	public Interpreter(DictStack dstack)
	{
		ostack=new OperandStack();
		estack=new ExecutionStack();
		procstack=new ProcStack();
		this.dstack=(DictStack)dstack.clone();
		pushStopLevel();
	}

	/**
	 *	Returns operand stack.
	 *
	 *	@return an operand stack.
	 */
	public OperandStack operandStack()
	{
		return ostack;
	}

	/**
	 *	Returns dictionary stack.
	 *
	 *	@return a dictionary stack.
	 */
	public DictStack dictStack()
	{
		return dstack;
	}

	/**
	 *	Returns execution stack.
	 *
	 *	@return an execution stack.
	 */
	public ExecutionStack executionStack()
	{
		return estack;
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
	 *	Returns current dictionary.
	 *
	 *	@return a current dictionary.
	 */
	public PsiDictlike getCurrentDict()
	{
		return dstack.peek();
	}

	/**
	 *	Returns system dictionary.
	 *
	 *	@return a system dictionary.
	 */
	public PsiDictlike getSystemDict()
	{
		return dstack.get(0);
	}

	/**
	 *	Returns global dictionary.
	 *
	 *	@return a global dictionary.
	 */
	public PsiDictlike getGlobalDict()
	{
		return dstack.get(1);
	}

	/**
	 *	Returns user dictionary.
	 *
	 *	@return user dictionary.
	 */
	public PsiDictlike getUserDict()
	{
		return dstack.get(2);
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

	public void interpret(final java.io.Reader reader)
	{
		interpret(new PsiReader(reader));
	}

	public void interpret(final String string)
	{
		interpret(new PsiStringReader(string));
	}

	public void interpret(final PsiReader reader)
	{
		final Parser parser=new Parser(reader);
		try
		{
			while(running)
			{
				final Token token=parser.getNextToken();
				if(token.kind==ParserConstants.EOF)
					break;
				processToken(token);

				// If "stop" is invoked outside the stopping context
				if(getStopFlag())
				{
					PsiErrorDict.OP_HANDLEERROR.invoke(this);
					return;
				}
			}
			if(procstack.size()>0)
				throw new PsiSyntaxErrorException(reader);
		}
		catch(PsiException e)
		{
			e.setEmitter(reader); // IMPORTANT
			handleError(e);
			if(getStopFlag())
				PsiErrorDict.OP_HANDLEERROR.invoke(this);
		}
		catch(TokenMgrError e)
		{
			handleError(new PsiSyntaxErrorException(reader));
			if(getStopFlag())
				PsiErrorDict.OP_HANDLEERROR.invoke(this);
		}
	}

	public void interpretBraced(final PsiReader reader)
		throws PsiException
	{
		procstack.push(new PsiProc());
		interpret(reader);
		if(procstack.size()==0)
			handleError(new PsiSyntaxErrorException(reader));
		PsiProc proc=procstack.pop();
		if(procstack.size()>0)
			procstack.peek().psiAppend(proc);
		else
			ostack.push(proc);
	}

	private void processToken(final Token token)
		throws PsiException
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
					procstack.push(new PsiProc());
					break;
				case ParserConstants.CLOSE_BRACE:
					throw new PsiSyntaxErrorException();
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
					procstack.push(new PsiProc());
					break;
				case ParserConstants.CLOSE_BRACE:
					{
						final PsiProc proc=procstack.pop();
						if(procstack.size()>0)
							procstack.peek().psiAppend(proc);
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
					procstack.peek().psiAppend(parseToken(token));
					break;
				case ParserConstants.EOF:
					throw new PsiSyntaxErrorException();
			}
		}
	}

	private PsiObject parseToken(Token token)
		throws PsiException
	{
		return (token.kind==ParserConstants.IMMEDIATE)?
			dstack.load(token.image.substring(2)):
			TokensParser.parseToken(token);
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

	public void handleError(final PsiException oException)
	{
		final String errorName=oException.getName();
		final PsiDict errorObj=new PsiDict();
		errorObj.put("newerror", PsiBoolean.TRUE);
		errorObj.put("errorname", new PsiName(errorName));
		errorObj.put("emitter", oException.getEmitter());
		errorObj.put("ostack", new PsiArray((java.util.ArrayList<PsiObject>)ostack.clone()));
		errorObj.put("estack", new PsiArray((java.util.ArrayList<PsiObject>)estack.clone()));
		errorObj.put("dstack", new PsiArray((java.util.ArrayList<PsiObject>)dstack.clone()));
		getSystemDict().put("$error", errorObj);

		try
		{
			final PsiDict errorDict=getErrorDict();
			if(errorDict.known(errorName))
				errorDict.get(errorName).invoke(this);
			else
				psiStop();
		}
		catch(PsiException e)
		{
			throw new AssertionError();
		}
	}

	public void showStacks()
	{
		System.out.print("Operand stack:\n\t");
		for(PsiObject obj: ostack)
			System.out.print(" "+obj);
		System.out.println();

		System.out.print("Execution stack:\n\t");
		for(PsiObject obj: estack)
			System.out.print(" "+obj);
		System.out.println();

		/*
		System.out.println("Dictionary stack:");
		System.out.print("⊢\t");
		for(PsiObject obj: dstack)
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
		return estack.size();
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
		int level=estack.size();
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
		int level=estack.size();
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
		throws PsiException
	{
		PsiArray arguments=new PsiArray();
		for(String arg: args)
			arguments.psiAppend(new PsiName(arg));
		getSystemDict().put("arguments", arguments);
	}

	public void acceptEnvironment(final java.util.Map<String, String> env)
	{
		PsiDict environment=new PsiDict();
		for(java.util.Map.Entry<String, String> entry: env.entrySet())
			environment.put(entry.getKey(), new PsiName(entry.getValue()));
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
		estack.clear();
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
				final String line=cr.readLine();
				if(line==null)
				{
					cr.printNewline();
					cr.flushConsole();
					return;
				}
				// TODO: wrap StringReader into PsiReader and set PsiReader as
				// emitter
				//interpret(line);
				final Parser parser=new Parser(new java.io.StringReader(line));
				try
				{
					while(running)
					{
						final Token token=parser.getNextToken();
						if(token.kind==ParserConstants.EOF)
							break;
						processToken(token);
						// If "stop" invoked outside the stopping context
						if(getStopFlag())
						{
							PsiErrorDict.OP_HANDLEERROR.invoke(this);
							setStopFlag(false);
							break;
						}
					}
				}
				catch(PsiException e)
				{
					e.setEmitter(PsiNull.NULL);
					handleError(e);
					if(getStopFlag())
						PsiErrorDict.OP_HANDLEERROR.invoke(this);
				}
				catch(TokenMgrError e)
				{
					handleError(new PsiSyntaxErrorException(PsiNull.NULL));
					if(getStopFlag())
						PsiErrorDict.OP_HANDLEERROR.invoke(this);
				}
			}
		}
		catch(java.io.IOException e)
		{
			throw new PsiIOErrorException();
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
		if(ostack.size()>0)
			sb.append("<"+ostack.size());
		sb.append("> ");
		return sb.toString();
	}

	public static Interpreter currentInterpreter()
	{
		return (Interpreter)Thread.currentThread();
	}

	public void psiStop()
	{
		setStopFlag(true);
		if(currentStopLevel()!=-1)
			estack.setSize(currentStopLevel());
		else
			quit();
	}

	private final OperandStack ostack;
	private final DictStack dstack;
	private final ExecutionStack estack;
	private final ProcStack procstack;
	private final Stack<Integer>
		loopstack=new Stack<Integer>(),
		stopstack=new Stack<Integer>();
	private boolean exitFlag=false, stopFlag=false;
	private boolean running=true;
}
