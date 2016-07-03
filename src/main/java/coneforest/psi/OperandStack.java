package coneforest.psi;
import coneforest.psi.core.*;

/**
*	An interpreter’s operand stack.
*/
public class OperandStack extends Stack<PsiObject>
{
	public <T extends PsiObject> T getBacked(final int index)
	{
		return (T)backup[index];
	}

	public void clearBackup()
	{
		backupSize=0;
	}

	public void ensureSize(final int size)
		throws PsiException
	{
		if(size()<size)
			throw new PsiStackUnderflowException();
	}

	public void popOperands(final int count)
		throws PsiException
	{
		final int offset=size()-count;
		if(offset<0)
		{
			backupSize=0;
			throw new PsiStackUnderflowException();
		}
		for(backupSize=0; backupSize<count; backupSize++)
			backup[backupSize]=get(offset+backupSize);
		setSize(offset);
	}

	public void restore()
	{
		for(int i=0; i<backupSize; i++)
			push(backup[i]);
		backupSize=0;
	}

	public int findMarkPosition()
		throws PsiUnmatchedMarkException
	{
		for(int i=size()-1; i>=0; i--)
			if(get(i)==PsiMark.MARK)
				return i;
		throw new PsiUnmatchedMarkException();
	}

	private PsiObject[] backup=new PsiObject[5];
	private int backupSize=0;
}
