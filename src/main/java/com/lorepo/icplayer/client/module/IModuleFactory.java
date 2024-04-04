package com.lorepo.icplayer.client.module;

import com.lorepo.icplayer.client.model.page.group.Group;
import com.lorepo.icplayer.client.model.page.group.GroupPresenter;
import com.lorepo.icplayer.client.model.page.group.GroupView;
import com.lorepo.icplayer.client.module.api.IModuleModel;
import com.lorepo.icplayer.client.module.api.IModuleView;
import com.lorepo.icplayer.client.module.api.IPresenter;

public interface IModuleFactory {

	public IModuleModel createModel(String xmlNodeName);
	public IModuleView createView(IModuleModel module);
	public GroupView createView(Group group);
	//이석웅 수정
//	public IPresenter createPresenter(IModuleModel model);
	public IPresenter createPresenter(IModuleModel model, String pageURL);

	public GroupPresenter createPresenter(Group group); 
}
