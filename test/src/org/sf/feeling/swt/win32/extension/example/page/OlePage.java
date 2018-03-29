/*******************************************************************************
 * Copyright (c) 2007 cnfree.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  cnfree  - initial API and implementation
 *******************************************************************************/
package org.sf.feeling.swt.win32.extension.example.page;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.extension.example.util.WidgetUtil;
import org.sf.feeling.swt.win32.extension.hook.data.struct.Msg;
import org.sf.feeling.swt.win32.extension.io.FileSystem;
import org.sf.feeling.swt.win32.extension.ole.OleHookInterceptor;
import org.sf.feeling.swt.win32.extension.ole.flash.Flash;
import org.sf.feeling.swt.win32.extension.ole.flash.listener.FlashEventAdapter;
import org.sf.feeling.swt.win32.extension.ole.flash.listener.FlashEventListener;

public class OlePage extends TabPage {

	private Composite container;

	private Text fileText;

	private Flash flash;

	public void buildUI(Composite parent) {

		container = WidgetUtil.getToolkit().createComposite(parent);
		GridLayout layout = new GridLayout();
		layout.marginLeft = 10;
		layout.marginRight = 10;
		layout.marginTop = 15;
		layout.verticalSpacing = 20;
		container.setLayout(layout);

		createTitle();
		createFileSelectedArea();
		createFlashArea();
	}

	private void createFileSelectedArea() {
		Composite fileSelectedArea = WidgetUtil.getToolkit().createComposite(
				container);
		GridLayout layout = new GridLayout();
		fileSelectedArea.setLayout(layout);
		layout.numColumns = 3;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		WidgetUtil.createLabel(fileSelectedArea).setText("Select File:");
		fileText = WidgetUtil.getToolkit().createText(fileSelectedArea, "");
		fileText.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == Win32.VK_RETURN) {
					if (fileText.getText().trim().length() == 0)
						playFlash();
					else
						flash.loadMovie(0, fileText.getText());
				}
			}
		});
		GridData gd = new GridData();
		gd.widthHint = 250;
		fileText.setLayoutData(gd);
		Button button = WidgetUtil.getToolkit().createButton(fileSelectedArea,
				SWT.PUSH, true);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				playFlash();
			}
		});

	}

	private void playFlash() {
		String ext[] = new String[] { "*.swf" };
		FileDialog fd = new FileDialog(container.getShell(), SWT.OPEN);
		fd.setFilterExtensions(ext);
		String file = fd.open();
		if (file != null) {
			fileText.setText(file);
			flash.loadMovie(0, file);
		}
	}

	private void createFlashArea() {
		FlashEventListener listener = new FlashEventAdapter() {

			public void onFSCommand(String command, String args) {
				MessageBox message = new MessageBox(container.getShell());
				message.setText("Receive Event");
				message.setMessage("Receive a FSCommand Event: command = "
						+ command + ", value = " + args);
				message.open();
			}
		};
		flash = new Flash(container, SWT.NONE, listener);

		flash.addHookInterceptor(new OleHookInterceptor() {

			public boolean intercept(Msg message, int code, int wParam,
					int lParam) {
				if (message.getMessage() == Win32.WM_RBUTTONDOWN) {
					Point cursor = flash.getParent().toControl(
							Display.getCurrent().getCursorLocation());
					if (flash.getBounds().contains(cursor) && flash.isVisible()) {
						MessageBox messageBox = new MessageBox(container
								.getShell());
						messageBox.setText("Intercept Flash Menu");
						messageBox
								.setMessage("Use hook to intercept flash system menu.");
						messageBox.open();
						return true;
					}
				}
				return false;
			}

		});

		GridData td = new GridData(GridData.FILL_VERTICAL);
		flash.setLayoutData(td);
		resize();
		flash.getParent().addControlListener(new ControlAdapter() {

			public void controlResized(ControlEvent e) {
				resize();
			}

		});
		flash.getParent().addPaintListener(new PaintListener() {

			public void paintControl(PaintEvent e) {
				resize();
			}
		});

		try {
			InputStream is = this.getClass().getResourceAsStream(
					"/flash.swf");
			File file = new File(FileSystem.getTempPath() + "flash.swf");
			if (!file.exists()) {
				FileOutputStream fos = new FileOutputStream(file);
				byte[] b = new byte[1024];
				int len;
				while ((len = is.read(b)) > 0) {
					fos.write(b, 0, len);
				}
				is.close();
				fos.close();
			}
			flash.loadMovie(0, file.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createTitle() {
		Label label = WidgetUtil
				.getToolkit()
				.createLabel(
						container,
						"This page demonstrates that how play flash in SWT control and observe ole event.",
						SWT.WRAP);
		label.setLayoutData(new GridData());
	}

	public Composite getControl() {
		return container;
	}

	public String getDisplayName() {
		return "Ole Control Example";
	}

	private void resize() {
		GridData gd = (GridData) flash.getLayoutData();
		int width = (int) (flash.getSize().y * ((float) 16 / 9));
		width = width < flash.getParent().getSize().x - 30 ? width : flash.getParent().getSize().x - 30;
		gd.widthHint = width;
		flash.setLayoutData(gd);
		flash.getParent().layout();
	}

}
