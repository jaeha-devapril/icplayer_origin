package com.lorepo.icplayer.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import com.lorepo.icf.utils.ILoadListener;
import com.lorepo.icf.utils.JavaScriptUtils;
import com.lorepo.icplayer.client.metadata.ScoreWithMetadata;
import com.lorepo.icplayer.client.printable.PrintableContentParser;
import com.lorepo.icplayer.client.printable.PrintableParams;
import com.lorepo.icplayer.client.utils.Utils;

import java.util.List;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PlayerEntryPoint implements EntryPoint {

	private PlayerApp theApplication;
	private PlayerApp theApplicationJimun;
	private PlayerApp theApplicationAnswer;
	private PlayerApp theApplicationSolve;
	private JavaScriptObject pageLoadedListener;
	private JavaScriptObject externalEventListener;
	private JavaScriptObject pageLoadedListenerJimun;
	private JavaScriptObject pageLoadedListenerAnswer;
	private JavaScriptObject pageLoadedListenerSolve;
	private JavaScriptObject pageScrollToListener;
	private JavaScriptObject pageScrollToListenerJimun;
	private JavaScriptObject pageScrollToListenerAnswer;
	private JavaScriptObject pageScrollToListenerSolve;
	private JavaScriptObject statusChangedListener;
	private JavaScriptObject statusChangedListenerJimun;
	private JavaScriptObject statusChangedListenerAnswer;
	private JavaScriptObject statusChangedListenerSolve;
	private JavaScriptObject outstretchHeightListener;
	private JavaScriptObject outstretchHeightListenerJimun;
	private JavaScriptObject outstretchHeightListenerAnswer;
	private JavaScriptObject outstretchHeightListenerSolve;
	private JavaScriptObject contextMetadata;
	private JavaScriptObject externalVariables;

	public static String subject = "SOSC";
//	public static String subject = "MATH";
	public static String grade = "1";
	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {
		externalVariables = JavaScriptObject.createObject();
		initJavaScriptAPI(this);
	}

	private static native void initJavaScriptAPI(PlayerEntryPoint entryPoint) /*-{
		function createAPI(player) {
			player.load = function(url, index) {
				index = index || 0;
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::load(Ljava/lang/String;I)(url, index);
			};

			player.load2 = function(url, subject, grade, index) {
				index = index || 0;
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::load2(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)(url, subject, grade, index);
			};

			player.loadJimun = function(url, index) {
				index = index || 0;
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::loadJimun(Ljava/lang/String;I)(url, index);
			};

			player.loadAnswer = function(url, index) {
				index = index || 0;
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::loadAnswer(Ljava/lang/String;I)(url, index);
			};

			player.loadSolve = function(url, index) {
				index = index || 0;
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::loadSolve(Ljava/lang/String;I)(url, index);
			};

			player.loadLearnetic = function(url, index) {
				index = index || 0;
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::loadLearnetic(Ljava/lang/String;I)(url, index);
			};


			player.loadCommonPage = function(url, index) {
				index = index || 0;
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::loadCommonPage(Ljava/lang/String;I)(url, index);
			};

			player.loadCommonPageJimun = function(url, index) {
				index = index || 0;
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::loadCommonPageJimun(Ljava/lang/String;I)(url, index);
			};

			player.loadCommonPageAnswer = function(url, index) {
				index = index || 0;
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::loadCommonPageAnswer(Ljava/lang/String;I)(url, index);
			};

			player.loadCommonPageSolve = function(url, index) {
				index = index || 0;
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::loadCommonPageSolve(Ljava/lang/String;I)(url, index);
			};

			player.unload = function() {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::unload()();
			};

			player.unloadJimun = function() {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::unloadJimun()();
			};

			player.unloadAnswer = function() {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::unloadAnswer()();
			};

			player.unloadSolve = function() {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::unloadSolve()();
			};

			player.setConfig = function(config) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::setConfig(Lcom/google/gwt/core/client/JavaScriptObject;)(config);
			};

			player.setConfigJimun = function(config) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::setConfigJimun(Lcom/google/gwt/core/client/JavaScriptObject;)(config);
			};

			player.setConfigAnswer = function(config) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::setConfigAnswer(Lcom/google/gwt/core/client/JavaScriptObject;)(config);
			};

			player.setConfigSolve = function(config) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::setConfigSolve(Lcom/google/gwt/core/client/JavaScriptObject;)(config);
			};

			player.onStatusChanged = function(listener) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::statusChangedListener = listener;
			};

			player.onStatusChangedJimun = function(listener) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::statusChangedListenerJimun = listener;
			};

			player.onStatusChangedAnswer = function(listener) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::statusChangedListenerAnswer = listener;
			};

			player.onStatusChangedSolve = function(listener) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::statusChangedListenerSolve = listener;
			};

			player.setAnalytics = function(id) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::setAnalytics(Ljava/lang/String;)(id);
			};

			player.setAnalyticsJimun = function(id) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::setAnalyticsJimun(Ljava/lang/String;)(id);
			};

			player.setAnalyticsAnswer = function(id) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::setAnalyticsAnswer(Ljava/lang/String;)(id);
			};

			player.setAnalyticsSolve = function(id) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::setAnalyticsSolve(Ljava/lang/String;)(id);
			};

			player.getSemiResponsiveLayouts = function() {
				return entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::getSemiResponsiveLayouts()();
			}

			player.getSemiResponsiveLayoutsJimun = function() {
				return entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::getSemiResponsiveLayoutsJimun()();
			}

			player.getSemiResponsiveLayoutsAnswer = function() {
				return entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::getSemiResponsiveLayoutsAnswer()();
			}

			player.getSemiResponsiveLayoutsSolve = function() {
				return entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::getSemiResponsiveLayoutsSolve()();
			}

			player.getState = function() {
				return entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::getState()();
			};

			player.getStateJimun = function() {
				return entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::getStateJimun()();
			};

			player.getStateAnswer = function() {
				return entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::getStateAnswer()();
			};

			player.getStateSolve = function() {
				return entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::getStateSolve()();
			};

			player.changeLayout = function(layoutID) {
				return entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::changeLayout(Ljava/lang/String;)(layoutID);
			}

			player.changeLayoutJimun = function(layoutID) {
				return entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::changeLayoutJimun(Ljava/lang/String;)(layoutID);
			}

			player.changeLayoutAnswer = function(layoutID) {
				return entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::changeLayoutAnswer(Ljava/lang/String;)(layoutID);
			}

			player.changeLayoutSolve = function(layoutID) {
				return entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::changeLayoutSolve(Ljava/lang/String;)(layoutID);
			}


			player.sendLayoutChangedEvent = function (value) {
			    return entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::sendLayoutChangedEvent(Ljava/lang/String;)(value);
			}

			player.setState = function(state) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::setState(Ljava/lang/String;)(state);
			};


			player.setStateJimun = function(state) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::setStateJimun(Ljava/lang/String;)(state);
			};

			player.setStateAnswer = function(state) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::setStateAnswer(Ljava/lang/String;)(state);
			};

			player.setStateSolve = function(state) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::setStateSolve(Ljava/lang/String;)(state);
			};

			player.setPages = function(pages) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::setPages(Ljava/lang/String;)(pages);
			};

			player.setPagesJimun = function(pages) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::setPagesJimun(Ljava/lang/String;)(pages);
			};

			player.setPagesAnswer = function(pages) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::setPagesAnswer(Ljava/lang/String;)(pages);
			};

			player.setPagesSolve = function(pages) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::setPagesSolve(Ljava/lang/String;)(pages);
			};


			player.getPlayerServices = function() {
				return entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::getPlayerServices()();
			};

			player.getPlayerServicesJimun = function() {
				return entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::getPlayerServicesJimun()();
			};

			player.getPlayerServicesAnswer = function() {
				return entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::getPlayerServicesAnswer()();
			};

			player.getPlayerServicesSolve = function() {
				return entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::getPlayerServicesSolve()();
			};

			player.onPageLoaded = function(listener) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::pageLoadedListener = listener;
			};

			player.onPageLoadedJimun = function(listener) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::pageLoadedListenerJimun = listener;
			};

			player.onPageLoadedAnswer = function(listener) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::pageLoadedListenerAnswer = listener;
			};

			player.onPageLoadedSolve = function(listener) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::pageLoadedListenerSolve = listener;
			};

			player.onExternalEvent = function(listener) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::externalEventListener = listener;
			};

			player.onOutstretchHeight = function(listener) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::outstretchHeightListener = listener;
			};

			player.onOutstretchHeightJimun = function(listener) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::outstretchHeightListenerJimun = listener;
			};

			player.onOutstretchHeightAnswer = function(listener) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::outstretchHeightListenerAnswer = listener;
			};

			player.onOutstretchHeightSolve = function(listener) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::outstretchHeightListenerSolve = listener;
			};

			player.onPageScrollTo = function(listener) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::pageScrollToListener = listener;
			};

			player.onPageScrollToJimun = function(listener) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::pageScrollToListenerJimun = listener;
			};

			player.onPageScrollToAnswer = function(listener) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::pageScrollToListenerAnswer = listener;
			};

			player.onPageScrollToSolve = function(listener) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::pageScrollToListenerSolve = listener;
			};


			player.forceScoreUpdate = function(listener) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::forceScoreUpdate()();
			};

			player.forceScoreUpdateJimun = function(listener) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::forceScoreUpdateJimun()();
			};

			player.forceScoreUpdateAnswer = function(listener) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::forceScoreUpdateAnswer()();
			};

			player.forceScoreUpdateSolve = function(listener) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::forceScoreUpdateSolve()();
			};



			player.isAbleChangeLayout = function(){
				return entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::isAbleChangeLayout()();
			};

			player.setContextMetadata = function(contextData){
				return entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::contextMetadata = contextData;
			};

			player.setExternalVariables = function(contextData){
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::setExternalVariables(Lcom/google/gwt/core/client/JavaScriptObject;)(contextData);
			};

			player.getExternalVariables = function(){
				return entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::getExternalVariables()();
			};

			player.getPrintableHTML = function(callback, randomizePages, randomizeModules, showAnswers, dpi) {
				return entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::generatePrintableHTML(Lcom/google/gwt/core/client/JavaScriptObject;ZZZI)(callback, randomizePages, randomizeModules, showAnswers, dpi);
			};

			player.getPrintableHTMLWithSeed = function(callback, randomizePages, randomizeModules, showAnswers, dpi, seed) {
				return entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::generatePrintableHTML(Lcom/google/gwt/core/client/JavaScriptObject;ZZZII)(callback, randomizePages, randomizeModules, showAnswers, dpi, seed);
			};

			player.preloadAllPages = function(callback) {
				return entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::preloadAllPages(Lcom/google/gwt/core/client/JavaScriptObject;)(callback);
			};

			player.getCurrentStyles = function () {
				return entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::getCurrentStyles()();
			}

			player.getScoreWithMetadata = function () {
				return entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::getScoreWithMetadata()();
			}
			player.setScoreWithMetadata = function (state) {
				entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::setScoreWithMetadata(Ljava/lang/String;)(state);
			};
		}

		// CreatePlayer
		$wnd.icCreatePlayer = function(id) {
			var player = entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::createAppPlayer(Ljava/lang/String;)(id);

			createAPI(player);

			return player;
		}

		// CreatePlayer
		$wnd.icCreatePlayerJimun = function(id) {
			var player = entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::createAppPlayerJimun(Ljava/lang/String;)(id);

			createAPI(player);

			return player;
		}

		// CreatePlayer
		$wnd.icCreatePlayerAnswer = function(id) {
			var player = entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::createAppPlayerAnswer(Ljava/lang/String;)(id);

			createAPI(player);

			return player;
		}

		// CreatePlayer
		$wnd.icCreatePlayerSolve = function(id) {
			var player = entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::createAppPlayerSolve(Ljava/lang/String;)(id);

			createAPI(player);

			return player;
		}

		// Create book
		$wnd.icCreateBook = function(id, useCover) {
			var player = entryPoint.@com.lorepo.icplayer.client.PlayerEntryPoint::createBookPlayer(Ljava/lang/String;Z)(id, useCover);

			createAPI(player);

			return player;
		}

		// Call App loaded function
		if (typeof $wnd.icOnAppLoaded == 'function') {
			$wnd.icOnAppLoaded();
		} else if (typeof $wnd.qpOnAppLoaded == 'function') {
			$wnd.qpOnAppLoaded();
		}
	}-*/;

	/**
	 * createPlayer js interface
	 *
	 * @param node_id
	 *            wrap this node
	 */
	private JavaScriptObject createAppPlayer(String node_id) {
		this.theApplication = new PlayerApp(node_id, this, "question");

		return JavaScriptObject.createFunction();
	}

	/**
	 * 지문 플레이어 생성
	 * @param node_id
	 * @return
	 */
	private JavaScriptObject createAppPlayerJimun(String node_id) {
		this.theApplicationJimun = new PlayerApp(node_id, this, "jimun");

		return JavaScriptObject.createFunction();
	}

	/**
	 * 정답 플레이어 생성
	 * @param node_id
	 * @return
	 */
	private JavaScriptObject createAppPlayerAnswer(String node_id) {
		this.theApplicationAnswer = new PlayerApp(node_id, this, "answer");

		return JavaScriptObject.createFunction();
	}

	/**
	 * 풀이 플레이어 생성
	 * @param node_id
	 * @return
	 */
	private JavaScriptObject createAppPlayerSolve(String node_id) {
		this.theApplicationSolve = new PlayerApp(node_id, this, "solve");

		return JavaScriptObject.createFunction();
	}





	private JavaScriptObject createBookPlayer(String node_id, boolean useCover) {
		this.theApplication = new PlayerApp(node_id, this, "question");
		this.theApplication.setBookMode();
		this.theApplication.showCover(useCover);
		return JavaScriptObject.createFunction();
	}

	private boolean isAbleChangeLayout() {
		return this.theApplication.getPlayerServices().isAbleChangeLayout();
	}

	private void load(String url, int pageIndex) {
		if (pageIndex < 0) {
			pageIndex = 0;
		}
		clearBeforeReload();
		this.theApplication.load(url, pageIndex);
	}

	// 이석웅 추가
	private void load2(String url, String subject, String grade, int pageIndex) {
		if (pageIndex < 0) {
			pageIndex = 0;
		}
		clearBeforeReload();
		this.theApplication.load(url, pageIndex);
		this.subject = subject;
		this.grade = grade;
	}


	private void loadJimun(String url, int pageIndex) {
		if (pageIndex < 0) {
			pageIndex = 0;
		}
		this.theApplicationJimun.load(url, pageIndex);
	}

	private void loadAnswer(String url, int pageIndex) {
		if (pageIndex < 0) {
			pageIndex = 0;
		}
		this.theApplicationAnswer.load(url, pageIndex);
	}

	private void loadSolve(String url, int pageIndex) {
		if (pageIndex < 0) {
			pageIndex = 0;
		}
		this.theApplicationSolve.load(url, pageIndex);
	}

	//이석웅 추가
	private void loadLearnetic(String url, int pageIndex) {
		Utils.isQNote = false;
		if (pageIndex < 0) {
			pageIndex = 0;
		}
		clearBeforeReload();
		this.theApplication.loadLearnetic(url, pageIndex);
	}



	// 이석웅 추가
	private void unload() {
		this.theApplication.unload();
	}

	private void unloadJimun() {
		this.theApplicationJimun.unload();
	}

	private void unloadAnswer() {
		this.theApplicationAnswer.unload();
	}

	private void unloadSolve() {
		this.theApplicationSolve.unload();
	}

	private void loadCommonPage(String url, int pageIndex) {
		if (pageIndex < 0) {
			pageIndex = 0;
		}
		clearBeforeReload();
		this.theApplication.loadCommonPage(url, pageIndex);
	}

	private void loadCommonPageJimun(String url, int pageIndex) {
		if (pageIndex < 0) {
			pageIndex = 0;
		}
		this.theApplicationJimun.loadCommonPage(url, pageIndex);
	}

	private void loadCommonPageAnswer(String url, int pageIndex) {
		if (pageIndex < 0) {
			pageIndex = 0;
		}
		this.theApplicationAnswer.loadCommonPage(url, pageIndex);
	}

	private void loadCommonPageSolve(String url, int pageIndex) {
		if (pageIndex < 0) {
			pageIndex = 0;
		}
		this.theApplicationSolve.loadCommonPage(url, pageIndex);
	}


	private void setConfig(JavaScriptObject config) {
		this.theApplication.setConfig(config);
	}

	private void setConfigJimun(JavaScriptObject config) {
		this.theApplicationJimun.setConfig(config);
	}

	private void setConfigAnswer(JavaScriptObject config) {
		this.theApplicationAnswer.setConfig(config);
	}

	private void setConfigSolve(JavaScriptObject config) {
		this.theApplicationSolve.setConfig(config);
	}

	private void forceScoreUpdate() {
		this.theApplication.updateScore();
	}

	private void forceScoreUpdateJimun() {
		this.theApplicationJimun.updateScore();
	}

	private void forceScoreUpdateAnswer() {
		this.theApplicationAnswer.updateScore();
	}


	private void forceScoreUpdateSolve() {
		this.theApplicationSolve.updateScore();
	}


	private void setAnalytics(String id) {
		this.theApplication.setAnalytics(id);
	}

	private void setAnalyticsJimun(String id) {
		this.theApplicationJimun.setAnalytics(id);
	}

	private void setAnalyticsAnswer(String id) {
		this.theApplicationAnswer.setAnalytics(id);
	}

	private void setAnalyticsSolve(String id) {
		this.theApplicationSolve.setAnalytics(id);
	}

	private void setState(String state) {
		this.theApplication.setState(state);
	}

	private void setStateJimun(String state) {
		this.theApplicationJimun.setState(state);
	}

	private void setStateAnswer(String state) {
		this.theApplicationAnswer.setState(state);
	}

	private void setStateSolve(String state) {
		this.theApplicationSolve.setState(state);
	}

	// //정답 체크 이벤트 발송
	// private void setCheck(String check) {
	// this.theApplication.setState(check);
	// }
	//
	// //문항 enable설정
	// private void setQuestionEnable(String check) {
	//
	// }

	private void setPages(String pagesSub) {
		this.theApplication.setPages(pagesSub);
	}

	private void setPagesJimun(String pagesSub) {
		this.theApplicationJimun.setPages(pagesSub);
	}

	private void setPagesAnswer(String pagesSub) {
		this.theApplicationAnswer.setPages(pagesSub);
	}

	private void setPagesSolve(String pagesSub) {
		this.theApplicationSolve.setPages(pagesSub);
	}

	private String getState() {
		return this.theApplication.getState();
	}

	private String getStateJimun() {
		return this.theApplicationJimun.getState();
	}

	private String getStateAnswer() {
		return this.theApplicationAnswer.getState();
	}

	private String getStateSolve() {
		return this.theApplicationSolve.getState();
	}

	private JavaScriptObject getSemiResponsiveLayouts() {
		return this.theApplication.getSemiResponsiveLayouts();
	}

	private JavaScriptObject getSemiResponsiveLayoutsJimun() {
		return this.theApplicationJimun.getSemiResponsiveLayouts();
	}

	private JavaScriptObject getSemiResponsiveLayoutsAnswer() {
		return this.theApplicationAnswer.getSemiResponsiveLayouts();
	}

	private JavaScriptObject getSemiResponsiveLayoutsSolve() {
		return this.theApplicationSolve.getSemiResponsiveLayouts();
	}

	private JavaScriptObject getPlayerServices() {
		return this.theApplication.getPlayerServices().getAsJSObject();
	}

	private JavaScriptObject getPlayerServicesJimun() {
		return this.theApplicationJimun.getPlayerServices().getAsJSObject();
	}

	private JavaScriptObject getPlayerServicesAnswer() {
		return this.theApplicationAnswer.getPlayerServices().getAsJSObject();
	}

	private JavaScriptObject getPlayerServicesSolve() {
		return this.theApplicationSolve.getPlayerServices().getAsJSObject();
	}

	// //presenter 리턴
	// private JavaScriptObject getPresenter() {
	// return this.theApplication.getPlayerServices().
	// }

	// private JavaScriptObject getPlayerServices(int pagesIdx) {
	// return this.theApplication.getPlayerServices(pagesIdx).getAsJSObject();
	// }

	private boolean changeLayout(String layoutID) {
		return this.theApplication.changeLayout(layoutID);
	}

	private boolean changeLayoutJimun(String layoutID) {
		return this.theApplicationJimun.changeLayout(layoutID);
	}

	private boolean changeLayoutAnswer(String layoutID) {
		return this.theApplicationAnswer.changeLayout(layoutID);
	}

	private boolean changeLayoutSolve(String layoutID) {
		return this.theApplicationSolve.changeLayout(layoutID);
	}

	private void sendLayoutChangedEvent(String value) {
	    this.theApplication
			.getPlayerServices()
			.getEventBusService()
			.sendValueChangedEvent("", "ChangeLayout", "", value, "");
	}

	private static native void fireCallback(JavaScriptObject callback) /*-{
		if (callback != null) {
			callback();
		}
	}-*/;

	private static native void fireScrollTo(JavaScriptObject callback, int top) /*-{
		if (callback != null) {
			callback(top);
		}
	}-*/;

	private static native void fireStatusChanged(JavaScriptObject callback,
			String type, String source, String value) /*-{
		if (callback != null) {
			callback(type, source, value);
		}
	}-*/;

	public void onPageLoaded() {
		fireCallback(this.pageLoadedListener);
		final int currentPageIndex = this.theApplication.getPlayerServices()
				.getCurrentPageIndex();
		String source = Integer.toString(currentPageIndex + 1);
		fireStatusChanged(this.statusChangedListener, "PageLoaded", source, "");
	}

	public void onPageLoadedJimun() {
		fireCallback(this.pageLoadedListenerJimun);
		final int currentPageIndex = this.theApplicationJimun.getPlayerServices().getCurrentPageIndex();
		String source = Integer.toString(currentPageIndex + 1);
		fireStatusChanged(this.statusChangedListenerJimun, "PageLoaded", source, "");
	}

	public void onPageLoadedAnswer() {
		fireCallback(this.pageLoadedListenerAnswer);
		final int currentPageIndex = this.theApplicationAnswer.getPlayerServices().getCurrentPageIndex();
		String source = Integer.toString(currentPageIndex + 1);
		fireStatusChanged(this.statusChangedListenerAnswer, "PageLoaded", source, "");
	}

	public void onPageLoadedSolve() {
		fireCallback(this.pageLoadedListenerSolve);
		final int currentPageIndex = this.theApplicationSolve.getPlayerServices().getCurrentPageIndex();
		String source = Integer.toString(currentPageIndex + 1);
		fireStatusChanged(this.statusChangedListenerSolve, "PageLoaded", source, "");
	}

	public void onScrollTo(int top) {
		fireScrollTo(this.pageScrollToListener, top);
	}

	public void onScrollToJimun(int top) {
		fireScrollTo(this.pageScrollToListenerJimun, top);
	}

	public void onScrollToAnswer(int top) {
		fireScrollTo(this.pageScrollToListenerAnswer, top);
	}

	public void onScrollToSolve(int top) {
		fireScrollTo(this.pageScrollToListenerSolve, top);
	}

	private static native void fireExternalEvent(JavaScriptObject callback, String eventType, String data)/*-{
		if (callback != null) {
			callback(eventType, data);
		}
	}-*/;

	public void onExternalEvent(String eventType, String data) {
		fireExternalEvent(this.externalEventListener, eventType, data);
	}

	public JavaScriptObject getPageScrollToObject() {
		return this.pageScrollToListener;
	}

	public JavaScriptObject getPageScrollToObjectJimun() {
		return this.pageScrollToListenerJimun;
	}

	public JavaScriptObject getPageScrollToObjetAnswer() {
		return this.pageScrollToListenerJimun;
	}

	public JavaScriptObject getPageScrollToObjectSolve() {
		return this.pageScrollToListenerSolve;
	}

	public void fireOutstretchHeightEvent() {
		fireCallback(this.outstretchHeightListener);
	}

	public void fireOutstretchHeightEventJimun() {
		fireCallback(this.outstretchHeightListenerJimun);
	}

	public void fireOutstretchHeightEventAnswer() {
		fireCallback(this.outstretchHeightListenerAnswer);
	}

	public void fireOutstretchHeightEventSolve() {
		fireCallback(this.outstretchHeightListenerSolve);
	}

	public JavaScriptObject getContextMetadata() {
		return this.contextMetadata;
	}

	public void setExternalVariables(JavaScriptObject contextData) {
		if (JavaScriptUtils.isObject(contextData))
			this.externalVariables = contextData;
		else
			JavaScriptUtils.log(
					"The received value is not a dictionary (it is not instance of Object). " +
					"Received value: " + contextData
			);
	}

	public JavaScriptObject getExternalVariables() {
		return this.externalVariables;
	}

	private void generatePrintableHTML(final JavaScriptObject callback, boolean randomizePages, boolean randomizeModules, boolean showAnswers, int dpi) {
		generatePrintableHTML(callback, randomizePages, randomizeModules, showAnswers, dpi, -1);
	}

	private void generatePrintableHTML(final JavaScriptObject callback, boolean randomizePages, boolean randomizeModules, boolean showAnswers, int dpi, int seed) {
		PrintableContentParser.ParsedListener listener = new PrintableContentParser.ParsedListener() {
			@Override
			public void onParsed(String result) {
				fireParsedCallback(callback, result);
			}
		};
		PrintableParams params = new PrintableParams();
		params.listener = listener;
		params.randomizePages = randomizePages;
		params.randomizeModules = randomizeModules;
		params.showAnswers = showAnswers;
		params.dpi = dpi;
		params.seed = seed;
		theApplication.generatePrintableHTML(params);
	}

	private static native void fireParsedCallback(JavaScriptObject callback, String result)/*-{
		if (callback != null) {
			callback(result);
		}
	}-*/;

	private String getCurrentStyles () {
		return theApplication.getCurrentStyles();
	}

	private void preloadAllPages(final JavaScriptObject callback) {
		theApplication.preloadAllPages(new ILoadListener() {

			@Override
			public void onFinishedLoading(Object obj) {
				fireCallback(callback);

			}

			@Override
			public void onError(String error) {
				JavaScriptUtils.log("Loading pages error: " + error);
			}

		});
	}

	private JavaScriptObject getScoreWithMetadata() {
		JavaScriptObject jsScores = JavaScriptUtils.createEmptyJsArray();
		List<ScoreWithMetadata> scores = theApplication.getScoreWithMetadata();
		for (ScoreWithMetadata score: scores) {
			JavaScriptUtils.addElementToJSArray(jsScores, score.getJSObject());
		}
		return jsScores;
	}

	private void setScoreWithMetadata(String state) {
		this.theApplication.setScoreWithMetadata(state);
	}

	private void clearBeforeReload() {
		if (theApplication != null && theApplication.isContentModelLoaded()) {
			clearMetadata();
			theApplication.clearBeforeReload();
		}
	}

	private void clearMetadata() {
		pageLoadedListener = null;
		externalEventListener = null;
		pageScrollToListener = null;
		statusChangedListener = null;
		outstretchHeightListener = null;
		contextMetadata = null;
		externalVariables = null;
	}
}
