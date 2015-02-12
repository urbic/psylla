package coneforest.psi;

public class SignalHandler
	implements sun.misc.SignalHandler
{
	public void handle(sun.misc.Signal signal)
	{
		System.out.println("SIGNAL CAUGHT!");
		System.exit(1);
	}
}
