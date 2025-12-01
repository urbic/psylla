package coneforest.psylla.processors;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
*	Annotation processor aimed to create Graphviz diagrams reflecting Psylla type hierarchy.
*/
@SupportedAnnotationTypes({"coneforest.psylla.runtime.Type", "coneforest.psylla.runtime.ErrorType"})
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class TypeHierarchyBuilder
	extends AbstractProcessor
{
	private Map<String, String> options;
	private Messager messager;
	private Types tu;

	/**
	*	Constructs a new processor.
	*/
	public TypeHierarchyBuilder()
	{
		// NOP
	}

	@Override
	public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv)
	{
		final var hierarchy=new HashMap<String, ArrayList<String>>();

		for(final var element: roundEnv.getElementsAnnotatedWith(coneforest.psylla.runtime.Type.class))
		{
			final var typeName=element.getAnnotation(coneforest.psylla.runtime.Type.class).value();
			final var parentNames=new ArrayList<String>();
			for(final var iface: ((TypeElement)element).getInterfaces())
			{
				final var annot=(tu.asElement(iface)).getAnnotation(coneforest.psylla.runtime.Type.class);
				if(annot!=null)
					parentNames.add(annot.value());
			}
			final var superclass=tu.asElement(((TypeElement)element).getSuperclass());
			if(superclass!=null)
			{
				final var annot=superclass.getAnnotation(coneforest.psylla.runtime.Type.class);
				if(annot!=null)
					parentNames.add(annot.value());
			}
			hierarchy.put(typeName, parentNames);
		}

		try
		{
			generateGraphs(hierarchy);
		}
		catch(final IOException e)
		{
			messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
		}

		return false;
	}

	private void generateGraphs(final Map<String, ? extends List<String>> hierarchy)
		throws IOException
	{
		final var outputDir=options.get(getClass().getName()+".outputDir");

		//java.nio.file.Files.createDirectory(java.nio.file.Path.of(outputDir));

		for(final var head: hierarchy.keySet())
		{
			final var agenda=new ArrayList<String>();
			agenda.add(head);
			final var closure=new HashSet<String>();
			final var dotWriter=new PrintStream(
					new File(outputDir, "PsyllaTypesHierarchy_"+head+".dot"),
					StandardCharsets.UTF_8);
			dotWriter.print("digraph "+head+"\n");
			dotWriter.print("{\n"
				+"\tnode\n"
				+"\t[\n"
				+"\t\thref=\"PsyllaReference_Types.xhtml#PsyllaReference_Types_Details_\\N\",\n"
				+"\t\ttarget=\"_parent\"\n"
				+"\t]\n"
				);
			while(!agenda.isEmpty())
			{
				final var rhs=agenda.remove(0);
				if(closure.contains(rhs))
					continue;
				closure.add(rhs);
				if(!hierarchy.containsKey(rhs))
					continue;
				agenda.addAll(hierarchy.get(rhs));
				final var lhs=hierarchy.get(rhs);
				if(!lhs.isEmpty())
					dotWriter.print("\t"+String.join(", ", lhs)+" -> "+rhs+";\n");
			}
			dotWriter.print("\t"+head+" [fillcolor=\"wheat\"]\n}\n\n");
			dotWriter.close();
		}
	}

	@Override
	public Set<String> getSupportedOptions()
	{
		return Set.of(getClass().getName()+".TypeHierarchyBuilder.outputDir");
	}

	@Override
	public void init(final ProcessingEnvironment penv)
	{
		super.init(penv);
		messager=penv.getMessager();
		options=penv.getOptions();
		tu=penv.getTypeUtils();
	}
}
