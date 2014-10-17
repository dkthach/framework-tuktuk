package com.mg.studio.tuktuk.actions;

import java.util.ArrayList;

import android.util.Log;

import com.mg.studio.tuktuk.actions.bas.MGAction;
import com.mg.studio.tuktuk.director.MGNode;
import com.mg.studio.tuktuk.util.collections.ConcurrentArrayHashMap;
import com.mg.studio.tuktuk.util.pool.ConcOneClassPool;

/**
 * MGActionManager is a singleton that manages all the actions. Normally you
 * won't need to use this singleton directly. 99% of the cases you will use the
 * MGNode interface, which uses this singleton. But there are some cases where
 * you might need to use this singleton. Examples: - When you want to run an
 * action where the target is different from a MGNode. - When you want to pause
 * / resume the actions
 */
public class MGActionManager implements UpdateCallback {
	private static final String LOG_TAG = MGActionManager.class
			.getSimpleName();

	private static class HashElement {
		final ArrayList<MGAction> actions;
		MGNode target;
		int actionIndex = -1;
		// MGAction currentAction;
		// boolean currentActionSalvaged;
		boolean paused;

		public HashElement() {
			actions = new ArrayList<MGAction>(4);
		}
	}

	private ConcOneClassPool<HashElement> pool = new ConcOneClassPool<MGActionManager.HashElement>() {
		@Override
		protected HashElement allocate() {
			return new HashElement();
		}
	};

	/**
	 * ActionManager is a singleton that manages all the actions. Normally you
	 * won't need to use this singleton directly. 99% of the cases you will use
	 * the CocosNode interface, which uses this singleton. But there are some
	 * cases where you might need to use this singleton. Examples: - When you
	 * want to run an action where the target is different from a CocosNode. -
	 * When you want to pause / resume the actions
	 * 
	 */

	private final ConcurrentArrayHashMap<MGNode, HashElement> targets;
	// private HashElement currentTarget;
	// private boolean currentTargetSalvaged;

	/**
	 * returns a shared instance of the ActionManager
	 */
	private static MGActionManager _sharedManager = new MGActionManager();

	/** returns a shared instance of the MGActionManager */
	public static MGActionManager sharedManager() {
		return _sharedManager;
	}

	private MGActionManager() {
		MGScheduler.sharedScheduler().scheduleUpdate(this, 0, false);
		targets = new ConcurrentArrayHashMap<MGNode, HashElement>();
	}

	private void deleteHashElement(HashElement element) {
		synchronized (element.actions) {
			element.actions.clear();
		}
		element.actionIndex = -1;

		HashElement removedEl = targets.remove(element.target);// put(element.target,
													// null);

		if (removedEl != null) {
			pool.free(removedEl);
		}
	}

	private void removeAction(int index, HashElement element) {
		synchronized (element.actions) {
			element.actions.remove(index);

			if (element.actionIndex >= index)
				element.actionIndex--;

			if (element.actions.isEmpty()) {
				deleteHashElement(element);
			}

		}
	}

	// actions

	// TODO figure out why the target not found
	/**
	 * Pauses all actions for a certain target. When the actions are paused,
	 * they won't be "ticked".
	 */
	@Deprecated
	public void pauseAllActions(MGNode target) {
		this.pause(target);
	}

	/**
	 * Resumes all actions for a certain target. Once the actions are resumed,
	 * they will be "ticked" in every frame.
	 */
	@Deprecated
	public void resumeAllActions(MGNode target) {
		this.resume(target);
	}

	/**
	 * Adds an action with a target. If the target is already present, then the
	 * action will be added to the existing target. If the target is not
	 * present, a new instance of this target will be created either paused or
	 * paused, and the action will be added to the newly created target. When
	 * the target is paused, the queued actions won't be 'ticked'.
	 */
	public void addAction(MGAction action, MGNode target, boolean paused) {
		assert action != null : "Argument action must be non-null";
		assert target != null : "Argument target must be non-null";

		HashElement element = targets.get(target);
		if (element == null) {
			element = pool.get();

			element.target = target;
			element.paused = paused;

			targets.put(target, element);
		}

		synchronized (element.actions) {

			assert !element.actions.contains(action) : "runAction: Action already running";

			element.actions.add(action);

		}

		action.start(target);
	}

	/**
	 * Removes all actions from all the targers.
	 */
	public void removeAllActions() {

		for (ConcurrentArrayHashMap<MGNode, HashElement>.Entry e = targets
				.firstValue(); e != null; e = targets.nextValue(e)) {
			HashElement element = e.getValue();

			if (element != null)
				removeAllActions(element.target);
		}
	}

	/**
	 * Removes all actions from a certain target. All the actions that belongs
	 * to the target will be removed.
	 */
	public void removeAllActions(MGNode target) {
		// explicit null handling
		if (target == null)
			return;

		HashElement element = targets.get(target);
		if (element != null) {
			// if( element.actions.contains(element.currentAction) &&
			// !element.currentActionSalvaged ) {
			// element.currentActionSalvaged = true;
			// }

			// element.actions.clear();
			// if( currentTarget == element )
			// currentTargetSalvaged = true;
			// else
			deleteHashElement(element);

		} else {
			// Log.w(LOG_TAG, "removeAllActions: target not found");
		}
	}

	/**
	 * Removes an action given an action reference.
	 */
	public void removeAction(MGAction action) {
		if (action == null)
			return;
		HashElement element = targets.get(action.getOriginalTarget());
		if (element != null) {
			int i;
			synchronized (element.actions) {
				i = element.actions.indexOf(action);

				if (i != -1) {
					removeAction(i, element);
				}
			}
		} else {
			Log.w(LOG_TAG, "removeAction: target not found");
		}
	}

	/**
	 * Removes an action given its tag and the target
	 */
	public void removeAction(int tag, MGNode target) {
		assert tag != MGAction.K_MG_ACTION_TAG_INVALIA : "Invalid tag";

		HashElement element = targets.get(target);
		if (element != null) {
			synchronized (element.actions) {
				int limit = element.actions.size();
				for (int i = 0; i < limit; i++) {

					MGAction a = element.actions.get(i);

					if (a.getTag() == tag
							&& a.getOriginalTarget() == target)
						removeAction(i, element);

				}
			}
		} else {
			// Log.w(LOG_TAG, "removeAction: target not found");
		}
	}

	/**
	 * Gets an action given its tag and a target
	 * 
	 * @return the Action with the given tag
	 */
	public MGAction getAction(int tag, MGNode target) {
		assert tag != MGAction.K_MG_ACTION_TAG_INVALIA : "Invalid tag";

		HashElement element = targets.get(target);
		if (element != null) {
			synchronized (element.actions) {
				int limit = element.actions.size();
				for (int i = 0; i < limit; i++) {
					MGAction a = element.actions.get(i);
					if (a.getTag() == tag)
						return a;
				}
			}
		} else {
			// Log.w(LOG_TAG, "getAction: target not found");
		}

		return null;
	}

	/**
	 * Returns the numbers of actions that are running in a certain target
	 * Composable actions are counted as 1 action. Example: If you are running
	 * 1 Sequence of 7 actions, it will return 1. If you are running 7
	 * Sequences of 2 actions, it will return 7.
	 */
	public int numberOfRunningActions(MGNode target) {
		HashElement element = targets.get(target);
		if (element != null) {
			synchronized (element.actions) {
				return element.actions.size();
			}
		}
		return 0;
	}

	public void update(float dt) {

		for (ConcurrentArrayHashMap<MGNode, HashElement>.Entry e = targets
				.firstValue(); e != null; e = targets.nextValue(e)) {
			HashElement currentTarget = e.getValue();
			if (currentTarget == null)
				continue;

			if (!currentTarget.paused) {
				synchronized (currentTarget.actions) {
					// The 'actions' may change while inside this loop.
					for (currentTarget.actionIndex = 0; currentTarget.actionIndex < currentTarget.actions
							.size(); currentTarget.actionIndex++) {

						MGAction currentAction = currentTarget.actions
								.get(currentTarget.actionIndex);

						currentAction.step(dt);
						if (currentAction.isDone()) {
							currentAction.stop();

							// removeAction(currentAction);
							HashElement element = targets
									.get(currentTarget.target);
							if (element != null
									&& currentTarget.actionIndex >= 0) {
								removeAction(currentTarget.actionIndex,
										currentTarget);
							}
						}

						// currentTarget.currentAction = null;
					}
					currentTarget.actionIndex = -1;
				}
			}

			if (currentTarget.actions.isEmpty())
				deleteHashElement(currentTarget);
		}
	}

	public void resume(MGNode target) {
		HashElement element = targets.get(target);
		if (element != null)
			element.paused = false;
	}

	public void pause(MGNode target) {
		HashElement element = targets.get(target);
		if (element != null)
			element.paused = true;
	}

	/**
	 * purges the shared action manager. It releases the retained instance.
	 */
	public static void purgeSharedManager() {
		if (_sharedManager != null) {
			MGScheduler.sharedScheduler().unscheduleUpdate(_sharedManager);
			_sharedManager = null;
		}
	}
}
