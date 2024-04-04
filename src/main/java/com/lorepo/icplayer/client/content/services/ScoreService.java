package com.lorepo.icplayer.client.content.services;

import java.util.HashMap;

import com.lorepo.icf.utils.JSONUtils;
import com.lorepo.icplayer.client.model.Content.ScoreType;
import com.lorepo.icplayer.client.module.api.player.IPage;
import com.lorepo.icplayer.client.module.api.player.IPlayerServices;
import com.lorepo.icplayer.client.module.api.player.IScoreService;
import com.lorepo.icplayer.client.module.api.player.PageScore;
import com.lorepo.icplayer.client.utils.Utils;

public class ScoreService implements IScoreService {

	private final HashMap<String, Integer>	scores;
	private final HashMap<String, String>	groupids;
	private final HashMap<String, HashMap<String, String>>	groupTexts;
	// Helper field for mapping page name to its ID (backwards compatibility)
	private final HashMap<String, String>	pagesNamesToIds;
	private final HashMap<String, PageScore>	pageScores;
	private final boolean useLast;
	private final ScoreType scoreType;
	private IPlayerServices playerServices;

	public ScoreService(ScoreType scoreType){
		this.scoreType = scoreType;
		this.useLast = scoreType == ScoreType.last;
		scores = new HashMap<String, Integer>();
		groupids = new HashMap<String, String>();
		groupTexts = new HashMap<String, HashMap<String, String>>();
		pagesNamesToIds = new HashMap<String, String>();
		pageScores = new HashMap<String, PageScore>();
	}

	//이석웅 추가
	//text 의 비순서 정답 체크를 위한 Group ID 설정
	@Override
	public void setTextGroupID(String module, String groupID) {
		groupids.remove(module);
		
		if( groupID.length() > 0 ) {
			Utils.consoleLog("setTextGroupID module : " + module + ", groupID : " + groupID);
			groupids.put(module, groupID);
		}
	}
	
	@Override
	public HashMap<String, String> getTextGroupID() {
		return groupids;
	}
	
	@Override
	public void setGroupTexts(String module, String text) {
//		groupTexts.remove(module);
		//TODO 페이지 이동시 groupTexts reset
		String groupID = groupids.get(module);
//		Utils.consoleLog("setGroupTexts groupID : " + groupID);
		if( text.length() > 0 ) {
			HashMap<String, String> group = groupTexts.get(groupID);
			if( group == null ) {
				group = new HashMap<String, String>();
			}
//			Utils.consoleLog("setGroupTexts module : " + module);
//			Utils.consoleLog("setGroupTexts text : " + text);
			group.put(module, text);
//			Utils.consoleLog("setModuleText module : " + module + ", text : " + text);
			groupTexts.put(groupID, group);
		}
	}
	
	@Override
	public HashMap<String, HashMap<String, String>> getGroupTexts() {
		return groupTexts;
	}
	
	
	
	

	@Override
	public int getScore(String moduleName) {

		Integer scoreObj = scores.get(moduleName);
		if(scoreObj != null){
			return scoreObj.intValue();
		}
		return 0;
	}

	@Override
	public int getTotalMaxScore() {

		int max = 0;
		for (PageScore scoreObj : pageScores.values()){
			max += scoreObj.getMaxScore() * scoreObj.getWeight();
		}

		return max;
	}

	@Override
	public int getTotalScore() {
		ScoreType scoreType = getScoreType();

		if (scoreType.equals(ScoreType.last)) {
			playerServices.getCommands().updateCurrentPageScore(false);
		}

		int total = 0;
		for (PageScore scoreObj : pageScores.values()) {
			total += scoreObj.getScore() * scoreObj.getWeight();
		}

		return total;
	}

	@Override
	public void setScore(String moduleName, int score, int maxScore) {
		Integer scoreObj = new Integer(score);
		scores.put(moduleName, scoreObj);
	}

	@Override
	public PageScore getPageScore(String pageId) {
		PageScore score = pageScores.get(pageId);
			
		if(score == null){
			score = new PageScore();
		}

		assert(score != null);
		return score;
	}

	@Override
	public String getAsString(){
		HashMap<String, String> data = new HashMap<String, String>();
		for(String name : pageScores.keySet()){
			PageScore score = pageScores.get(name);
			data.put(name, score.getAsString());
		}
		return JSONUtils.toJSONString(data);
	}

	@Override
	public void loadFromString(String state){
		HashMap<String, String> data = JSONUtils.decodeHashMap(state);
		for(String pageName : data.keySet()){
			PageScore pageScore = pageScores.get(pageName);
			pageScore = PageScore.loadFromString(pageName, data.get(pageName));
			pageScores.put(pageName, pageScore);
		}
	}

	@Override
	public void setPageScore(IPage page, PageScore score) {
		score.setWeight(page.getPageWeight());
		PageScore pageScore = pageScores.get(page.getId());

		if (getScoreType().equals(ScoreType.last) || pageScore == null) {

			pageScores.put(page.getId(), score);
			pagesNamesToIds.put(page.getName(), page.getId());
		}
	}

	public boolean useLast() {
		return useLast;
	}

	@Override
	public ScoreType getScoreType() {
		return scoreType;
	}

	@Override
	public PageScore getPageScoreByName(String pageName) {
		if (scoreType.equals(ScoreType.last)) {
			playerServices.getCommands().updateCurrentPageScore(false);
		}

		return getPageScoreById(pagesNamesToIds.get(pageName));
	}

	@Override
	public PageScore getPageScoreById(String pageId) {
		if (scoreType.equals(ScoreType.last)) {
			playerServices.getCommands().updateCurrentPageScore(false);
		}

		PageScore score = pageScores.get(pageId);

		if (score == null) {
			score = new PageScore();
		}

		return score;
	}

	public void setPlayerService(IPlayerServices playerServices) {
		this.playerServices = playerServices;
	}

	@Override
	public void lessonScoreReset(boolean resetChecks, boolean resetMistakes) {
		for (int i=0; i<playerServices.getModel().getPageCount(); i++) {
			IPage currentPage = playerServices.getModel().getPage(i);
			if (currentPage.isReportable()) {
				PageScore pageScore = playerServices.getScoreService().getPageScore(currentPage.getId());
				if (pageScore.hasScore()) {
					PageScore score;

					if (resetChecks && !resetMistakes) {
						score = pageScore.resetScoreAndChecks();
					}else if (!resetChecks && resetMistakes) {
						score = pageScore.resetScoreAndMistakes();
					}else if (resetChecks && resetMistakes){
						score = pageScore.resetAllScores();
					}else {
						score = pageScore.reset();
					}

					playerServices.getScoreService().setPageScore(currentPage, score);
				}
			}
		}
	}

}
