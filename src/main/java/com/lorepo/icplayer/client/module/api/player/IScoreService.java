package com.lorepo.icplayer.client.module.api.player;

import com.lorepo.icplayer.client.model.Content.ScoreType;
import java.util.HashMap;

public interface IScoreService {

	public int getScore(String moduleName);
	public PageScore getPageScore(String pageName);
	public PageScore getPageScoreById(String pageId);
	public PageScore getPageScoreByName(String pageName);
	public void	setScore(String moduleName, int score, int maxScore);
	int getTotalMaxScore();
	int getTotalScore();
	String getAsString();
	void loadFromString(String state);
	void setPageScore(IPage page, PageScore score);
	public ScoreType getScoreType();
	void lessonScoreReset(boolean resetChecks, boolean resetMistakes);
	public void setTextGroupID(String moduleID, String GroupID);
	public HashMap<String, String> getTextGroupID();
	public void	setGroupTexts(String moduleName, String text);
	public HashMap<String, HashMap<String, String>> getGroupTexts();
}
