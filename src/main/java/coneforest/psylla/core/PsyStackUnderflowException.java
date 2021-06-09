package coneforest.psylla.core;
import coneforest.psylla.*;

@ExceptionType("stackunderflow")
public class PsyStackUnderflowException
	extends PsyException
{
	@Override
	public String getName()
	{
		return "stackunderflow";
	}
}
