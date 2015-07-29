package com.brandon3055.potionidhelper;

import com.brandon3055.brandonscore.common.handlers.FileHandler;
import com.brandon3055.potionidhelper.asm.MethodHolder;
import net.minecraft.potion.Potion;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by Brandon on 28/5/2015.
 */
public class SortingHandler {

	public static void checkForConflicts(){
		LogHelper.info("Checking for potion id conflicts");
		if (Potion.potionTypes.length < 2048) LogHelper.error("[Warning] It seems another mod has reduced the potion array size from 2048 to " + Potion.potionTypes.length);
		List<Integer> freeIds = new ArrayList<Integer>();
		for (int i = 1; i < Potion.potionTypes.length; i++) if (Potion.potionTypes[i] == null) freeIds.add(i);
		Map<Potion, Potion> conflicts = new HashMap<Potion, Potion>();
		List<String> remapOptions = new ArrayList<String>();

		//Check for conflicts
		for (Potion potion : MethodHolder.detectedPotions){
			Potion atId = Potion.potionTypes[potion.id];
			if (potion != atId){
				conflicts.put(potion, atId);
			}
		}

		//Create remap options
		for (Potion potion : conflicts.keySet()){
			String s = "Conflict: " + getUName(potion) + " --> " + getUName(conflicts.get(potion)) + " at id: " + potion.id;
			if (freeIds.size() > 0) s += " Change one of these to " + freeIds.remove(0);
			else s += " There are not enough free id's.... How that is possible i do not know.)";
			remapOptions.add(s);
		}

		Collections.sort(remapOptions);

		if (remapOptions.size() == 0) return;

		//Print remap options
		LogHelper.info("##########################################################################");
		LogHelper.info("Found the following potion id conflicts");
		System.out.println();
		for (String s : remapOptions) LogHelper.info(s);
		System.out.println();


		//Write to file
		try
		{
			File file = new File(FileHandler.configFolder, "/PotionIdConflicts.txt");
			if (!file.exists()) file.createNewFile();

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("Potion Id Sorter has found "+remapOptions.size()+" potion id conflicts.");
			bw.newLine();
			bw.write("The first potion is the one that has been overwritten. The second is the potion currently assigned to the id.");
			bw.newLine();
			bw.write("This mod has already found all of the available potion id's and assigned one to each conflict. Simply change one of the conflicting potions to the id suggested in the config for the mod that adds the potion.");
			bw.newLine();
			bw.newLine();
			bw.write("Potions are listed as:");
			bw.newLine();
			bw.write("[Potion 1 Class]:[Potion 1 Name] --> [Potion 2 Class]:[Potion 2 Name]");
			bw.newLine();
			bw.write("The mod id is not given but it should be easy to figure out the mod from the class. Just look for the mod name in the class path e.g. \"am2.buffs.ArsMagicaPotion\" = Ars Magica, \"vazkii.botania.common.brew.potion.PotionSoulCross\" = Botania, \"com.emoniph.witchery.brewing.potions.PotionChilled\" = Witchery etc.");
			bw.newLine();
			bw.newLine();
			for (String s : remapOptions) {
				bw.write(s);
				bw.newLine();
			}
			bw.newLine();
			bw.write("If for some reason you don't want to use the pre selected options these are all the available potion id's.");
			bw.newLine();
			int i2 = 0;
			for (int i = 1; i < Potion.potionTypes.length; i++) {
				if (Potion.potionTypes[i] == null){
					i2++;
					if (i2 % 500 == 0) bw.newLine();
					bw.write(i + ", ");
				}
			}
			bw.newLine();
			bw.write("Potion ID Sorter was created by brandon3055.");
			bw.newLine();
			bw.write("You can delete this file when you are finished. It will be regenerated if more conflicts are detected.");
			bw.close();

			LogHelper.info("This list has been saved to "+file.getAbsolutePath());
			LogHelper.info("##########################################################################");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}



	}

	public static String getUName(Potion potion) { return potion.getClass().getName() + ":" + potion.getName(); }
}
