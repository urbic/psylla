package coneforest.cli;

/**
 * Class representing a flag. Boolean option value, initially false, set to
 * true every time when option is processed.
 */
public class OptionFlag
	extends OptionWithoutArg<Boolean>
{
	public OptionFlag(String... names)
	{
		super(names);
	}

	/**
	 * Returns current option value.
	 *
	 * @return value
	 */
	@Override
	public Boolean getValue()
	{
		return flag;
	}

	/**
	 * Sets option value to true.
	 */
	public void handle()
	{
		flag=true;
	}

	private boolean flag=false;
}
