package com.brandon3055.potionidhelper;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.potion.Potion;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

// -Dfml.coreMods.load=com.brandon3055.potionidhelper.asm.LoadingPlugin
@Mod(modid = PotionIDHelper.MODID, version = PotionIDHelper.VERSION, name = PotionIDHelper.MODNAME, dependencies = "required-before:BrandonsCore@[1.0.0.4,);")
public class PotionIDHelper
{
	public static final String MODNAME = "Potion ID Helper";
    public static final String MODID = "PotionIDHelper";
    public static final String VERSION = "1.0.4";

//	public static int arraySize;

	public PotionIDHelper(){
		LogHelper.info("Hello Minecraft!!!!!!!!!!!");
		Potion[] potionTypes;

		Field potionTypeArray = ReflectionHelper.findField(Potion.class, "potionTypes", "field_76425_a");
		potionTypeArray.setAccessible(true);

		try {
			Field modfield = Field.class.getDeclaredField("modifiers");
			modfield.setAccessible(true);
			modfield.setInt(potionTypeArray, potionTypeArray.getModifiers() & ~Modifier.FINAL);
			potionTypes = (Potion[]) potionTypeArray.get(null);

			final Potion[] newPotionTypes = new Potion[2048];
			System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length );
			potionTypeArray.set(null, newPotionTypes);
			com.brandon3055.brandonscore.common.utills.LogHelper.info("Potion array has been extended to " + 2048);
		}
		catch (Exception e) {
			com.brandon3055.brandonscore.common.utills.LogHelper.error("Detected error while expanding potion array! Please report this!");
			e.printStackTrace();
		}
	}

   @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
//		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
//
//		try
//		{
//			arraySize = config.get(Configuration.CATEGORY_GENERAL, "arraySize", 256, "This allows you to adjust the potion array size if necessary (Max value allows is "+Short.MAX_VALUE+")", 32, Short.MAX_VALUE).getInt(256);
//			if (config.hasChanged()) config.save();
//		}
//		catch (Exception e){
//			LogHelper.error("Config Error");
//			e.printStackTrace();
//		}
//
//		Potion[] potionTypes;
//
//		Field potionTypeArray = ReflectionHelper.findField(Potion.class, "potionTypes", "field_76425_a");
//		potionTypeArray.setAccessible(true);
//
//		try {
//			Field modfield = Field.class.getDeclaredField("modifiers");
//			modfield.setAccessible(true);
//			modfield.setInt(potionTypeArray, potionTypeArray.getModifiers() & ~Modifier.FINAL);
//			potionTypes = (Potion[]) potionTypeArray.get(null);
//
//			final Potion[] newPotionTypes = new Potion[arraySize];
//			System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length );
//			potionTypeArray.set(null, newPotionTypes);
//			LogHelper.info("Potion array has been extended to "+arraySize);
//		}
//		catch (Exception e) {
//			LogHelper.error("Detected error while expanding potion array! Please report this!");
//			e.printStackTrace();
//		}
	}

	@Mod.EventHandler
	public void postLast(FMLLoadCompleteEvent event)
	{
		SortingHandler.checkForConflicts();
	}

}
