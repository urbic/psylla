/**
 * <acronym>GNU</acronym>-compatible command-line options processor.
 *
 * <h3>Features</h3>
 * <p>
 * <ul>
 * <li>Support for either short (<code>-V</code>) or long
 * (<code>--version</code>) option forms.</li>
 * <li>Support for options with arguments (<code>-o
 * file.txt</code>, <code>-ofile.txt</code>, <code>--output-file
 * file.txt</code>, <code>--output-file=file.txt</code>).</li>
 * <li>Short options can be bundled (<code>-xvf</code>).</li>
 * <li>Toggle options processing with <code>--</code>.</li>
 * </ul>
 * </p>
 *
 * @author Anton Shvetz
 */
package coneforest.cli;
