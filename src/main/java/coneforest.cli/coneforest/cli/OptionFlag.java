package coneforest.cli;

/**
*	A flag option. Boolean option value, initially false, set to true every
*	time when option is processed.
*/
public class OptionFlag
	extends OptionWithoutArg<Boolean>
{
	/**
	*	Creates new option with given names.
	*
	*	@param names space-delimited names.
	*/
	public OptionFlag(final String names)
	{
		super(names);
	}

	/**
	*	Returns current option value.
	*
	*	@return a value.
	*/
	@Override
	public Boolean getValue()
	{
		return flag;
	}

	/**
	*	Sets option value to true.
	*/
	@Override
	public void handle()
	{
		flag=true;
	}

	private boolean flag=false;
}
