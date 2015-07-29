package com.brandon3055.potionidhelper.asm;

import com.brandon3055.potionidhelper.PotionIDHelper;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

/**
 * Created by Brandon on 27/5/2015.
 */
@IFMLLoadingPlugin.Name(value = PotionIDHelper.MODNAME)
@IFMLLoadingPlugin.MCVersion(value = "1.7.10")
@IFMLLoadingPlugin.TransformerExclusions(value = "com.brandon3055.potionidsorter.asm.")
@IFMLLoadingPlugin.SortingIndex(value = 1001)
public class LoadingPlugin implements IFMLLoadingPlugin {

	@Override
	public String[] getASMTransformerClass() {
		return new String[] {ClassTransformer.class.getName()};
	}

	@Override
	public String getModContainerClass() {
		return null;
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}
}
