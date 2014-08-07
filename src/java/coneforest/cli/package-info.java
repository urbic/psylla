/**
 * <acronym>GNU</acronym>-compatible command-line options processor.
 *
 * <h3>Features</h3>
 * <p>
 * <ul>
 * <li>Support for either short (<code>-V</code>) or long
 * (<code>--version</code>) option forms.</li>
 * <li>Support fot options with arguments (<code>-o
 * file.txt</code>, <code>-ofile.txt</code>, <code>--output-file
 * file.txt</code>, <code>--output-file=file.txt</code>).</li>
 * <li>Short options can be bundled (<code>-xvf</code>).</li>
 * <li>Toggle options processing with <code>--</code>.</li>
 * <li>Automatical generation of help and usage messages.</li>
 * <li><acronym>XML</acronym>-based processor configuration.</li>
 * <li>Validation of option configuration document against
 * <acronym>XML</acronym> schema.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Options processor configuration file is <acronym>XML</acronym>-based. Its
 * <acronym>URL</acronym> is passed to {@linkplain
 * coneforest.cli.OptionsProcessor#OptionsProcessor(java.net.URL) processor
 * constructor}.
 * </p>
 *
 * <h3>Options processor configuration</h3>
 * <p>
 * Configuration file has following structure:
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;
 * &lt;options xmlns="urn:coneforest:cli:ns"&gt;
 * 	&lt;option names="o output-file" class="coneforest.cli.OptionString"&gt;
 * 		&lt;usage description="program output file"/&gt;
 * 		&lt;usage lang="ru" description="флаг"/&gt;
 * 	&lt;/option&gt;
 * 	&lt;option … /&gt;
 * 	…
 * 	&lt;option … /&gt;
 * &lt;/options&gt;
 * </pre>
 * </p>
 *
 *
 * @author Anton Shvetz
 */
package coneforest.cli;
