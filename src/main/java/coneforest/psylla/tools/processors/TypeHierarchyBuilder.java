package coneforest.psylla.tools.processors;

import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@SupportedAnnotationTypes({"coneforest.psylla.Type"})
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class TypeHierarchyBuilder
	extends AbstractProcessor
{
	@Override
	public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv)
	{
		final var hierarchy=new java.util.HashMap<String, java.util.ArrayList<String>>();

		for(final var element: roundEnv.getElementsAnnotatedWith(coneforest.psylla.Type.class))
		{
			final var typeName=element.getAnnotation(coneforest.psylla.Type.class).value();
			final var parentNames=new java.util.ArrayList<String>();
			for(final var iface: ((TypeElement)element).getInterfaces())
			{
				final var annot=(tu.asElement(iface)).getAnnotation(coneforest.psylla.Type.class);
				if(annot!=null)
					parentNames.add(annot.value());
			}
			//final var superclass=tu.asElement(((TypeElement)element).getSuperclass());
			final var superclass=tu.asElement(((TypeElement)element).getSuperclass());
			//System.out.println(">>>"+superclass+">>>"+tu.asElement(superclass));
			if(superclass!=null)
			{
				final var annot=superclass.getAnnotation(coneforest.psylla.Type.class);
				if(annot!=null)
					parentNames.add(annot.value());
			}

			hierarchy.put(typeName, parentNames);
		}

		try
		{
			generateGraphs(hierarchy);
		}
		catch(final java.io.IOException e)
		{
			messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
		}

		return false;
	}

	private void generateGraphs(java.util.Map<String, ? extends java.util.List<String>> hierarchy)
		throws java.io.IOException
	{
		final var outputDir=options.get("coneforest.psylla.tools.processors.TypeHierarchyBuilder.outputDir");

		//java.nio.file.Files.createDirectory(java.nio.file.Path.of(outputDir));

		for(final var head: hierarchy.keySet())
		{
			final var agenda=new java.util.ArrayList<String>();
			agenda.add(head);
			final var closure=new java.util.HashSet<String>();
			//final var dot=new ProcessBuilder("dot", "-Tsvg", "-o"+outputDir+"/PsyllaTypesHierarchy_"+head+".svg").start();
			//final var dotWriter=new java.io.PrintStream(dot.getOutputStream());
			final var dotWriter=new java.io.PrintStream(
					java.nio.file.Files.newOutputStream(java.nio.file.Path.of(outputDir, "PsyllaTypesHierarchy_"+head+".dot")));
			dotWriter.print("digraph "+head+"\n");
			dotWriter.print("{\n"
				+"\tgraph\n"
				+"\t[\n"
				+"\t\tbgcolor=\"none\",\n"
				+"\t]\n"
				+"\tnode\n"
				+"\t[\n"
				+"\t\tshape=\"note\",\n"
				+"\t\tstyle=\"filled\",\n"
				+"\t\tfillcolor=\"linen\",\n"
				+"\t\tfontname=\"PT Mono, monospace\",\n"
				+"\t\tfontsize=\"10px\",\n"
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
		return Set.of("coneforest.psylla.tools.processors.TypeHierarchyBuilder.outputDir");
	}

	@Override
	public void init(final ProcessingEnvironment penv)
	{
		super.init(penv);

		messager=penv.getMessager();
		options=penv.getOptions();
		tu=penv.getTypeUtils();
	}

	private java.util.Map<String, String> options;
	private Messager messager;
	private javax.lang.model.util.Types tu;
}
