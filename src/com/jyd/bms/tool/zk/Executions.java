package com.jyd.bms.tool.zk;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Window;

public class Executions {
	private static final Logger log;

	static {
		log = LoggerFactory.getLogger((Class) Executions.class);
	}

	public static Component createComponents(final String uri, final Component parent, final Map arg) {
		return createComponents(uri, parent, arg, false);
	}

	public static Execution getCurrent() {
		return org.zkoss.zk.ui.Executions.getCurrent();
	}

	public static Component createComponents(final String uri, final Component parent, final Map arg,
			final boolean showProcessing) {
		final Component com = org.zkoss.zk.ui.Executions.createComponents(uri, parent, arg);
		if (showProcessing) {
			Clients.showBusy();
		}
		return com;
	}

	public static Window doModal(final String uri, final Component parent, final Map arg)
			throws SuspendNotAllowedException, InterruptedException {
		return doModal(uri, parent, arg, false, null);
	}

	public static Window doModal(final String uri, final Component parent, final Map arg, final String echoEventName)
			throws SuspendNotAllowedException, InterruptedException {
		return doModal(uri, parent, arg, true, echoEventName, null);
	}

	public static Window doModal(final String uri, final Component parent, final Map arg, final boolean showProcessing)
			throws SuspendNotAllowedException, InterruptedException {
		return doModal(uri, parent, arg, showProcessing, null, null);
	}

	public static Window doModal(final String uri, final Component parent, final Map arg, final String echoEventName,
			final ContinueProcessAfterComponentCreated process)
			throws SuspendNotAllowedException, InterruptedException {
		return doModal(uri, parent, arg, true, echoEventName, process);
	}

	public static Window doModal(final String uri, final Component parent, final Map arg, final boolean showProcessing,
			final ContinueProcessAfterComponentCreated process)
			throws SuspendNotAllowedException, InterruptedException {
		return doModal(uri, parent, arg, showProcessing, null, process);
	}

	public static Window doModal(final String uri, final Component parent, final Map arg, final boolean showProcessing,
			final String echoEventName, final ContinueProcessAfterComponentCreated process)
			throws SuspendNotAllowedException, InterruptedException {
		final Window win = (Window) createComponents(uri, parent, arg, showProcessing);
		if (process != null) {
			process.processAfterComponentCreated((Component) win);
		}
		longOperation((Component) win, showProcessing, echoEventName);
		win.doModal();
		return win;
	}

	public static Window doHighlighted(final String uri, final Component parent, final Map arg)
			throws SuspendNotAllowedException, InterruptedException {
		return doHighlighted(uri, parent, arg, false, null);
	}

	public static Window doHighlighted(final String uri, final Component parent, final Map arg,
			final String echoEventName) throws SuspendNotAllowedException, InterruptedException {
		return doHighlighted(uri, parent, arg, true, echoEventName, null);
	}

	public static Window doHighlighted(final String uri, final Component parent, final Map arg,
			final boolean showProcessing) throws SuspendNotAllowedException, InterruptedException {
		return doHighlighted(uri, parent, arg, showProcessing, null, null);
	}

	public static Window doHighlighted(final String uri, final Component parent, final Map arg,
			final String echoEventName, final ContinueProcessAfterComponentCreated process)
			throws SuspendNotAllowedException, InterruptedException {
		return doHighlighted(uri, parent, arg, true, echoEventName, process);
	}

	public static Window doHighlighted(final String uri, final Component parent, final Map arg,
			final boolean showProcessing, final ContinueProcessAfterComponentCreated process)
			throws SuspendNotAllowedException, InterruptedException {
		return doHighlighted(uri, parent, arg, showProcessing, null, process);
	}

	public static Window doHighlighted(final String uri, final Component parent, final Map arg,
			final boolean showProcessing, final String echoEventName,
			final ContinueProcessAfterComponentCreated process)
			throws SuspendNotAllowedException, InterruptedException {
		final Window win = (Window) createComponents(uri, parent, arg, showProcessing);
		if (process != null) {
			process.processAfterComponentCreated((Component) win);
		}
		longOperation((Component) win, showProcessing, echoEventName);
		win.doHighlighted();
		return win;
	}

	public static Window doOverlapped(final String uri, final Component parent, final Map arg)
			throws SuspendNotAllowedException, InterruptedException {
		return doOverlapped(uri, parent, arg, false, null);
	}

	public static Window doOverlapped(final String uri, final Component parent, final Map arg,
			final String echoEventName) throws SuspendNotAllowedException, InterruptedException {
		return doOverlapped(uri, parent, arg, true, echoEventName, null);
	}

	public static Window doOverlapped(final String uri, final Component parent, final Map arg,
			final boolean showProcessing) throws SuspendNotAllowedException, InterruptedException {
		return doOverlapped(uri, parent, arg, showProcessing, null, null);
	}

	public static Window doOverlapped(final String uri, final Component parent, final Map arg,
			final String echoEventName, final ContinueProcessAfterComponentCreated process)
			throws SuspendNotAllowedException, InterruptedException {
		return doOverlapped(uri, parent, arg, true, echoEventName, process);
	}

	public static Window doOverlapped(final String uri, final Component parent, final Map arg,
			final boolean showProcessing, final ContinueProcessAfterComponentCreated process)
			throws SuspendNotAllowedException, InterruptedException {
		return doOverlapped(uri, parent, arg, showProcessing, null, process);
	}

	public static Window doOverlapped(final String uri, final Component parent, final Map arg,
			final boolean showProcessing, final String echoEventName,
			final ContinueProcessAfterComponentCreated process)
			throws SuspendNotAllowedException, InterruptedException {
		final Window win = (Window) createComponents(uri, parent, arg, showProcessing);
		if (process != null) {
			process.processAfterComponentCreated((Component) win);
		}
		longOperation((Component) win, showProcessing, echoEventName);
		win.doOverlapped();
		return win;
	}

	public static Window doEmbedded(final String uri, final Component parent, final Map arg)
			throws SuspendNotAllowedException, InterruptedException {
		return doEmbedded(uri, parent, arg, false, null);
	}

	public static Window doEmbedded(final String uri, final Component parent, final Map arg, final String echoEventName)
			throws SuspendNotAllowedException, InterruptedException {
		return doEmbedded(uri, parent, arg, true, echoEventName, null);
	}

	public static Window doEmbedded(final String uri, final Component parent, final Map arg,
			final boolean showProcessing) throws SuspendNotAllowedException, InterruptedException {
		return doEmbedded(uri, parent, arg, showProcessing, null, null);
	}

	public static Window doEmbedded(final String uri, final Component parent, final Map arg, final String echoEventName,
			final ContinueProcessAfterComponentCreated process)
			throws SuspendNotAllowedException, InterruptedException {
		return doEmbedded(uri, parent, arg, true, echoEventName, process);
	}

	public static Window doEmbedded(final String uri, final Component parent, final Map arg,
			final boolean showProcessing, final ContinueProcessAfterComponentCreated process)
			throws SuspendNotAllowedException, InterruptedException {
		return doEmbedded(uri, parent, arg, showProcessing, null, process);
	}

	public static Window doEmbedded(final String uri, final Component parent, final Map arg,
			final boolean showProcessing, final String echoEventName,
			final ContinueProcessAfterComponentCreated process)
			throws SuspendNotAllowedException, InterruptedException {
		final Window win = (Window) createComponents(uri, parent, arg, showProcessing);
		if (process != null) {
			process.processAfterComponentCreated((Component) win);
		}
		longOperation((Component) win, showProcessing, echoEventName);
		win.doEmbedded();
		return win;
	}

	private static void longOperation(final Component com, final boolean showProcessing, final String echoEventName) {
		if (showProcessing) {
			if (echoEventName != null) {
				longOperationComplete(com, echoEventName);
			} else {
				longOperationComplete(com);
			}
		}
	}

	public static void longOperationComplete(final Component com, final String echoEventName) {
		Events.echoEvent(echoEventName, com, (String) null);
	}

	public static void longOperationComplete(final Component com) {
		Events.echoEvent("longOperationComplete", com, (String) null);
	}

	public static void longOperationComplete(final Component com, final String echoEventName, final String data) {
		Events.echoEvent(echoEventName, com, data);
	}

	public static void longOperationStart() {
		Clients.showBusy();
	}

	public static void sendRedirect(final String uri) {
		org.zkoss.zk.ui.Executions.sendRedirect(uri);
	}
}
