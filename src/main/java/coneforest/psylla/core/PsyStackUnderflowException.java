package coneforest.psylla.core;

public class PsyStackUnderflowException
	extends PsyException
{
	@Override
	public String getName()
	{
		return "stackunderflow";
	}
}
