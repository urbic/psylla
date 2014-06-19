package coneforest.psi;

public class PSIInterpreter
{
	public PSIInterpreter(java.io.InputStream is)
	{
		this.is=is;
		opstack=new OperandStack();
		dictstack=new DictionaryStack();
		execstack=new ExecutionStack();
		procstack=new ProcedureStack();

		// Load systemdict, globaldict, userdict
		dictstack.push(loadModule(coneforest.psi.systemdict.Systemdict.class));
		dictstack.push(new PSIDictionary());
		dictstack.push(new PSIDictionary());
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

	public void handleExecutionStack()
	{
		//int currentExecutionLevel=execstack.size();
		while(execstack.size()>0)
		{
			System.out.println("START HANDLE EXECUTION STACK="+execstack);
			System.out.println("START HANDLE OPSTACK STACK="+opstack);
			PSIObject exectop=execstack.peek();
			if(exectop instanceof PSIArray && exectop.isExecutable())
			{
				PSIArray proc=(PSIArray)exectop;
				boolean execLevelChanged=false;
				for(int i=0; i<proc.size()-1; i++)
				{
					//System.out.println("FOUND ELEMENT "+((PSIArray)exectop).get(i));
					int execlevel=execstack.size();
					proc.get(i).execute(this);
					if(execlevel<execstack.size())
					{
						System.out.println("EXEC LEVEL CHANGED FROM "+execlevel+" TO "+execstack.size());
						//System.exit(55);

						execLevelChanged=true;
						PSIArray newproc=new PSIArray();
						newproc.setExecutable();
						for(int j=i+1; j<proc.size(); j++)
							newproc.add(proc.get(j));
						execstack.setElementAt(newproc, execlevel-1);
						break;
					}
				}
				if(execLevelChanged)
				{
					continue;
				}
				execstack.pop();
				if(proc.size()>0)
					//execstack.push(((PSIArray)exectop).get(((PSIArray)exectop).size()-1));
					proc.get(proc.size()-1).execute(this);
			}
			else
				execstack.pop().execute(this);
			System.out.println("STOP HANDLE EXECUTION STACK="+execstack);
			System.out.println("STOP HANDLE OPSTACK STACK="+opstack);
		}
	}

	public void interpret()
	{
		PSIParser parser=new PSIParser(is);
		Token token;

		while(true)
		{
			token=parser.getNextToken();
			if(token.kind==PSIParserConstants.EOF) break;

			//System.out.println(token+"\t"+PSIParserConstants.tokenImage[token.kind]);


			if(procstack.size()==0)
			{
				switch(token.kind)
				{
					case PSIParserConstants.TOKEN_OPEN_BRACE:
						procstack.push(new PSIArray());
						procstack.peek().setAccess(PSIObject.ACCESS_EXECUTE);
						break;
					case PSIParserConstants.TOKEN_CLOSE_BRACE:
						error("syntaxerror");
						break;
					case PSIParserConstants.TOKEN_INTEGER:
						opstack.push(new PSIInteger(token));
						break;
					case PSIParserConstants.TOKEN_REAL:
						opstack.push(new PSIReal(token));
						break;
					case PSIParserConstants.TOKEN_STRING:
						opstack.push(new PSIString(token));
						break;
					case PSIParserConstants.TOKEN_NAME_LITERAL:
						opstack.push(new PSIName(token));
						break;
					case PSIParserConstants.TOKEN_NAME_EXECUTABLE:
						//dictstack.load(new PSIName(token)).execute(this);
						(new PSIName(token)).execute(this);
						break;
				}
				// TODO
				handleExecutionStack();
			}
			else
			{
				switch(token.kind)
				{
					case PSIParserConstants.TOKEN_OPEN_BRACE:
						procstack.push(new PSIArray());
						procstack.peek().setAccess(PSIObject.ACCESS_EXECUTE);
						break;
					case PSIParserConstants.TOKEN_CLOSE_BRACE:
						PSIArray proc=procstack.pop();
						if(procstack.size()>0)
							procstack.peek().add(proc);
						else
							opstack.push(proc);
						break;
					case PSIParserConstants.TOKEN_INTEGER:
						procstack.peek().add(new PSIInteger(token));
						break;
					case PSIParserConstants.TOKEN_REAL:
						procstack.peek().add(new PSIReal(token));
						break;
					case PSIParserConstants.TOKEN_STRING:
						procstack.peek().add(new PSIString(token));
						break;
					case PSIParserConstants.TOKEN_NAME_LITERAL:
					case PSIParserConstants.TOKEN_NAME_EXECUTABLE:
						procstack.peek().add(new PSIName(token));
						break;
				}
			}
		}
	}

	public void error(String errorName)
	{
		// TODO
		System.out.println("ERROR: "+errorName);
	}

	public PSIDictionary loadModule(Class<? extends PSIModule> moduleClass)
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

	public int getLoopLevel()
	{
		return loopLevel;
	}

	public void setLoopLevel(int loopLevel)
	{
		this.loopLevel=loopLevel;
	}

	private java.io.InputStream is;
	private OperandStack opstack;
	private DictionaryStack dictstack;
	private ExecutionStack execstack;
	private ProcedureStack procstack;
	private int loopLevel=0;
}
