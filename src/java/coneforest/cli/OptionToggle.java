package coneforest.cli;

/**
 * Class representing a toggle. Boolean option value toggles every time when
 * option is processed.
 */
public class OptionToggle extends OptionWithoutArg
{
	public OptionToggle(String names)
	{
		super(names);
	}

	/**
	 * Returns current option value.
	 *
	 * @return value
	 */
	public Boolean getValue()
	{
		return toggle;
	}

	/**
	 * Toggles option value.
	 */
	public void handle()
	{
		toggle=!toggle;
	}

	private boolean toggle=false;
}
