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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.extension.example.util.WidgetUtil;
import org.sf.feeling.swt.win32.extension.shell.ApplicationBar;
import org.sf.feeling.swt.win32.extension.shell.ShellSystem;
import org.sf.feeling.swt.win32.extension.shell.Windows;

public class DesktopPage extends SimpleTabPage
{

	private Button onTopBtn;

	private Button autoHideBtn;

	public void buildUI(Composite parent)
	{
		super.buildUI(parent);
		TableWrapLayout layout = new TableWrapLayout();
		layout.leftMargin = 10;
		layout.rightMargin = 10;
		layout.topMargin = 15;
		layout.verticalSpacing = 20;
		layout.numColumns = 2;
		container.getBody().setLayout(layout);

		createTitle();
		createWallPaperArea();
		createTaskBarArea();
	}

	private void createTaskBarArea()
	{
		FormText infoLabel = WidgetUtil
				.createFormText(container.getBody(),
						"Also SWT Extension provides the ability to manage taskbar state using ApplicationBar class.");

		TableWrapData td = new TableWrapData(TableWrapData.FILL);
		td.colspan = 2;
		infoLabel.setLayoutData(td);

		WidgetUtil.getToolkit().createLabel(container.getBody(), "Current TaskBar State:");
		final FormText statusLabel = WidgetUtil.createFormText(container.getBody());
		statusLabel.setText("<form><p><b>" + getTaskbarState() + "</b></p></form>", true,
				false);

		Composite taskBarArea = WidgetUtil.getToolkit().createComposite(container.getBody());
		td = new TableWrapData(TableWrapData.FILL);
		td.colspan = 2;
		taskBarArea.setLayoutData(td);

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		taskBarArea.setLayout(layout);

		if (Win32.getWin32Version() <= Win32.VERSION(5, 0))
		{
			WidgetUtil.getToolkit().createLabel(taskBarArea, "Check TaskBar State:");
			autoHideBtn = WidgetUtil.getToolkit().createButton(taskBarArea, SWT.CHECK, true);
			autoHideBtn.setEnabled(false);
			autoHideBtn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			autoHideBtn.setText("Auto-hide the taskbar");
			WidgetUtil.getToolkit().createLabel(taskBarArea, true);
			onTopBtn = WidgetUtil.getToolkit().createButton(taskBarArea, SWT.CHECK, true);
			onTopBtn.setEnabled(false);
			onTopBtn.setText("Keep the taskbar on top of other windows");
			WidgetUtil.getToolkit().createLabel(taskBarArea, true);
			Button applyBtn = WidgetUtil.getToolkit()
					.createButton(taskBarArea, SWT.PUSH, true);
			applyBtn.setText("Refresh");
			GridData gd = new GridData();
			gd.widthHint = 60;
			applyBtn.setLayoutData(gd);
			applyBtn.addSelectionListener(new SelectionAdapter()
			{

				public void widgetSelected(SelectionEvent e)
				{
					activate();
					statusLabel.setText(
							"<form><p><b>" + getTaskbarState() + "</b></p></form>", true,
							false);
					container.reflow(true);
				}

			});
		}
		else
		{
			WidgetUtil.getToolkit().createLabel(taskBarArea, "Setup TaskBar State:");
			autoHideBtn = WidgetUtil.getToolkit().createButton(taskBarArea, SWT.CHECK, true);
			autoHideBtn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			autoHideBtn.setText("Auto-hide the taskbar");
			WidgetUtil.getToolkit().createLabel(taskBarArea, true);
			onTopBtn = WidgetUtil.getToolkit().createButton(taskBarArea, SWT.CHECK, true);
			onTopBtn.setText("Keep the taskbar on top of other windows");

			WidgetUtil.getToolkit().createLabel(taskBarArea, true);
			Button applyBtn = WidgetUtil.getToolkit()
					.createButton(taskBarArea, SWT.PUSH, true);
			applyBtn.setText("Apply");
			GridData gd = new GridData();
			gd.widthHint = 60;
			applyBtn.setLayoutData(gd);
			applyBtn.addSelectionListener(new SelectionAdapter()
			{

				public void widgetSelected(SelectionEvent e)
				{
					if (onTopBtn.getSelection() && autoHideBtn.getSelection())
					{
						ApplicationBar.setAppBarState(container.handle,
								Win32.STATE_AUTOHIDE_ALWAYSONTOP);
					}
					else if (onTopBtn.getSelection())
					{
						ApplicationBar.setAppBarState(container.handle,
								Win32.STATE_ALWAYSONTOP);
					}
					else if (autoHideBtn.getSelection())
					{
						ApplicationBar.setAppBarState(container.handle, Win32.STATE_AUTOHIDE);
					}
					else
					{
						ApplicationBar.setAppBarState(container.handle, Win32.STATE_NONE);
					}
					statusLabel.setText(
							"<form><p><b>" + getTaskbarState() + "</b></p></form>", true,
							false);
					container.reflow(true);
				}

			});
		}

	}

	private void createWallPaperArea()
	{
		TableWrapData td;
		Section desktopSection = WidgetUtil.getToolkit().createSection(container.getBody(),
				Section.EXPANDED);
		td = new TableWrapData(TableWrapData.FILL);
		td.colspan = 2;
		desktopSection.setLayoutData(td);
		desktopSection.setText("Active Desktop Functions:");
		WidgetUtil.getToolkit().createCompositeSeparator(desktopSection);
		Composite desktopClient = WidgetUtil.getToolkit().createComposite(desktopSection);
		GridLayout layout = new GridLayout();
		layout.numColumns = 4;
		desktopClient.setLayout(layout);

		WidgetUtil.getToolkit().createLabel(desktopClient, "Select File:");
		final Text fileText = WidgetUtil.getToolkit().createText(desktopClient, "");
		GridData gd = new GridData();
		gd.widthHint = 200;
		fileText.setLayoutData(gd);
		Button button = WidgetUtil.getToolkit().createButton(desktopClient, SWT.PUSH, true);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				FileDialog dialog = new FileDialog(container.getShell(), SWT.OPEN);
				dialog.setFilterExtensions(new String[] {
						"*.bmp;*.gif;*.jpg;*.jpeg;*.dib;*.png;*.htm;*.html",
						"*.bmp;*.gif;*.jpg;*.jpeg;*.dib;*.png", "*.htm;*.html" });
				dialog.setFilterNames(new String[] { "Backgroud Files",
						"All Image Files (*.bmp;*.gif;*.jpg;*.jpeg;*.dib;*.png)",
						"HTML Documents (.htm;*.html)" });
				String file = dialog.open();
				if (file != null)
				{
					fileText.setText(file);
				}
			}
		});

		final Button setButton = WidgetUtil.getToolkit().createButton(desktopClient, SWT.PUSH,
				true);
		setButton.setText("Set as WallPaper");
		setButton.setEnabled(false);
		setButton.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				ShellSystem.setWallPaper(fileText.getText().trim(),
						ShellSystem.WALLPAPER_STRETCH);
			}
		});

		fileText.addModifyListener(new ModifyListener()
		{

			public void modifyText(ModifyEvent e)
			{
				String file = fileText.getText().trim();
				if (file.length() > 0)
				{
					setButton.setEnabled(true);
				}
				else
					setButton.setEnabled(false);
			}

		});

		desktopSection.setClient(desktopClient);
	}

	public String getDisplayName()
	{
		return "Active Desktop Example";
	}

	private void createTitle()
	{
		FormText title = WidgetUtil.createFormText(container.getBody(),
				"This page demonstrates SWT Extension ability to manage desktop using "
						+ "ActiveDesktop interface.");
		TableWrapData td = new TableWrapData(TableWrapData.FILL);
		td.colspan = 2;
		title.setLayoutData(td);
	}

	private String getTaskbarState()
	{
		int barState = ApplicationBar.getAppBarState(Windows.getSystemTray());
		switch (barState)
		{
		case Win32.STATE_ALWAYSONTOP:
			return "Taskbar is always on top";
		case Win32.STATE_AUTOHIDE:
			return "Taskbar auto-hides when inactive";
		case Win32.STATE_AUTOHIDE_ALWAYSONTOP:
			return "Taskbar is always on top and auto-hides when inactive";
		default:
			return "Taskbar is not always on top";
		}
	}

	public void activate()
	{
		onTopBtn
				.setSelection(ApplicationBar.getAppBarState(container.handle) == Win32.STATE_ALWAYSONTOP
						|| ApplicationBar.getAppBarState(container.handle) == Win32.STATE_AUTOHIDE_ALWAYSONTOP);
		autoHideBtn
				.setSelection(ApplicationBar.getAppBarState(container.handle) == Win32.STATE_AUTOHIDE
						|| ApplicationBar.getAppBarState(container.handle) == Win32.STATE_AUTOHIDE_ALWAYSONTOP);
	}

}
