/**
 * 
 * Copyright (c) 2013-2014, Openflexo
 * Copyright (c) 2012-2012, AgileBirds
 * 
 * This file is part of Flexo-exec, a component of the software infrastructure 
 * developed at Openflexo.
 * 
 * 
 * Openflexo is dual-licensed under the European Union Public License (EUPL, either 
 * version 1.1 of the License, or any later version ), which is available at 
 * https://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 * and the GNU General Public License (GPL, either version 3 of the License, or any 
 * later version), which is available at http://www.gnu.org/licenses/gpl.html .
 * 
 * You can redistribute it and/or modify under the terms of either of these licenses
 * 
 * If you choose to redistribute it and/or modify under the terms of the GNU GPL, you
 * must include the following additional permission.
 *
 *          Additional permission under GNU GPL version 3 section 7
 *
 *          If you modify this Program, or any covered work, by linking or 
 *          combining it with software containing parts covered by the terms 
 *          of EPL 1.0, the licensors of this Program grant you additional permission
 *          to convey the resulting work. * 
 * 
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE. 
 *
 * See http://www.openflexo.org/license.html for details.
 * 
 * 
 * Please contact Openflexo (openflexo-contacts@openflexo.org)
 * or visit www.openflexo.org if you need additional information.
 * 
 */

package org.openflexo.script;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.openflexo.fib.FIBLibrary;
import org.openflexo.fib.model.FIBComponent;
import org.openflexo.fib.model.FIBContainer;
import org.openflexo.fib.model.FIBParameter;
import org.openflexo.toolbox.ResourceLocator;

public class VisibleForScript {

	public static final boolean DRY_RUN = false;

	private static final String[] FIB_EXTENSIONS = { "fib", "inspector" };

	private void convert() {
		Collection<File> fibs = FileUtils.listFiles(
				ResourceLocator.findProjectDirectoryWithName(new File(System.getProperty("user.dir")), "openflexo"), FIB_EXTENSIONS, true);
		for (File fib : fibs) {
			convertFib(fib);
		}
	}

	private void convertFib(File fib) {
		if (fib.getName().endsWith("Prefs.inspector")) {
			return;
		}
		LineIterator lineIterator;
		try {
			lineIterator = FileUtils.lineIterator(fib, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		if (!lineIterator.hasNext() || !lineIterator.nextLine().startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")) {
			System.err.println(fib.getAbsolutePath() + " is not a fib");
			return;
		}
		FIBComponent fibComponent = FIBLibrary.instance().retrieveFIBComponent(fib);
		if (fibComponent == null) {
			System.err.println(fib.getAbsolutePath() + " could not be loaded");
			return;
		}
		boolean changed = lookupAndFixVisibleFor(fibComponent, fibComponent);
		if (changed) {
			System.err.println(fib.getAbsolutePath() + " has changed");
			if (DRY_RUN) {
				FIBLibrary.saveComponentToStream(fibComponent, fib, new OutputStream() {

					@Override
					public void write(int b) throws IOException {
						System.err.print((char) b);
					}
				});
			} else {
				FIBLibrary.save(fibComponent, fib);
			}
		}
	}

	private boolean lookupAndFixVisibleFor(FIBComponent root, FIBComponent fib) {
		boolean changed = false;
		String parameter = fib.getParameter("visibleFor");
		if (parameter != null) {
			FIBComponent label = root.getComponentNamed(fib.getName() + "Label");
			if (label != null && label.getParameter("visibleFor") == null) {
				label.addToParameters(new FIBParameter("visibleFor", parameter));
				changed = true;
			}
		}
		if (fib instanceof FIBContainer) {
			for (FIBComponent child : ((FIBContainer) fib).getSubComponents()) {
				changed |= lookupAndFixVisibleFor(root, child);
			}
		}
		return changed;
	}

	public static void main(String[] args) {
		new VisibleForScript().convert();
		System.exit(0);
	}

}
