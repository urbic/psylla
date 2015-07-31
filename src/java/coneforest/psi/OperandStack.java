package coneforest.psi;

/**
 *	An interpreter’s operand stack.
 */
public class OperandStack extends Stack<PsiObject>
{
	public void clearBackup()
	{
		backupSize=0;
	}

	public void ensureSize(final int size)
		throws PsiException
	{
		if(size()<size)
			throw new PsiException("stackunderflow");
	}

	public PsiObject[] popOperands(final int count)
		throws PsiException
	{
		final int offset=size()-count;
		if(offset<0)
		{
			backupSize=0;
			throw new PsiException("stackunderflow");
		}
		for(backupSize=0; backupSize<count; backupSize++)
			backup[backupSize]=get(offset+backupSize);
		setSize(offset);
		return backup;
	}

	public void restore()
	{
		for(int i=0; i<backupSize; i++)
			push(backup[i]);
		backupSize=0;
	}

	private PsiObject[] backup=new PsiObject[5];
	private int backupSize=0;
}
