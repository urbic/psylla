package coneforest.cli;

/**
 * Class representing a toggle. Boolean option value, initially false, toggles
 * every time when option is processed.
 */
public class OptionToggle
	extends OptionWithoutArg
{
	public OptionToggle(final String... names)
	{
		super(names);
	}

	/**
	 * Returns current option value.
	 *
	 * @return a value.
	 */
	@Override
	public Boolean getValue()
	{
		return toggle;
	}

	/**
	 * Toggles option value.
	 */
	@Override
	public void handle()
	{
		toggle=!toggle;
	}

	private boolean toggle=false;
}
