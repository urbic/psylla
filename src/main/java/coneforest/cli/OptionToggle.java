package coneforest.cli;

/**
*	A toggle option. Boolean option value, initially false, toggles every time
*	when option is processed.
*/
public class OptionToggle
	extends OptionWithoutArg
{
	/**
	*	Creates new option with given names.
	*
	*	@param names space-delimited names.
	*/
	public OptionToggle(final String names)
	{
		super(names);
	}

	/**
	*	Returns the current option value.
	*
	*	@return a value.
	*/
	@Override
	public Boolean getValue()
	{
		return toggle;
	}

	/**
	*	Toggles option value.
	*/
	@Override
	public void handle()
	{
		toggle=!toggle;
	}

	private boolean toggle=false;
}
