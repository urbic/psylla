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
public class TypeAnnotationProcessor extends AbstractProcessor
{
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
	{
		final var annotatedElements=roundEnv.getElementsAnnotatedWith(coneforest.psylla.Type.class);

		final var td=options.get("typemap.dir");

		for(var element: annotatedElements)
		{
			try
			{
				final var typeName=element.getAnnotation(coneforest.psylla.Type.class).value();
				final var className=((TypeElement)element).getQualifiedName().toString();
				final var ps=new java.io.PrintStream(new java.io.File(td, typeName));
				ps.println(className);
				ps.close();
			}
			catch(final java.io.FileNotFoundException e)
			{
				messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage(), element);
			}
		}

		return false;
	}

	@Override
	public Set<String> getSupportedOptions()
	{
		return Set.of("typemap.dir");
	}

	@Override
	public void init(final ProcessingEnvironment penv)
	{
		super.init(penv);

		messager=penv.getMessager();
		options=penv.getOptions();
	}

	private java.util.Map<String, String> options;
	private Messager messager;
}
