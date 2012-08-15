package net.slipcor.pvparena.commands;

import java.util.HashSet;

import net.slipcor.pvparena.arena.Arena;
import net.slipcor.pvparena.core.Language;
import net.slipcor.pvparena.core.StringParser;

import org.bukkit.command.CommandSender;

public abstract class PA__Command {
	public final String[] perms;
	
	public PA__Command(String[] s) {
		perms = s;
	}
	
	public boolean argCountValid(CommandSender sender, String[] args, HashSet<Integer> validCounts) {
		if (validCounts.contains(args.length)) {
			return true;
		}
		
		Arena.pmsg(sender, Language.parse("error.invalid_argument_count", String.valueOf(args.length), StringParser.joinSet(validCounts, "|")));
		return false;
	}
	
	public abstract void commit(CommandSender sender, String[] args);
	public abstract String getName();
	
	public boolean hasPerms(CommandSender sender) {
		if (sender.hasPermission("pvparena.admin")) {
			return true;
		}
		
		for (String perm : perms) {
			if (sender.hasPermission(perm)) {
				return true;
			}
		}
		
		if (perms.length > 0) {
			String s[] = perms[0].split(".");

			Arena.pmsg(sender, Language.parse("nopermto", Language.parse("noperm" + s[1])));
		} else {

			Arena.pmsg(sender, Language.parse("nopermto", Language.parse("admin")));
		}
		
		return false;
	}
	
	public static PA__Command getByName(String name) {
		
		name = name.toLowerCase();
		
		if (name.contains("autosetup") || name.equals("!as")) {
			return new PAA_AutoSetup();
		} else if (name.contains("create") || name.equals("!c") || name.equals("new")) {
			return new PAA_Create();
		} else if (name.contains("debug") || name.equals("!d")) {
			return new PAA_Debug();
		} else if (name.equals("install") || name.equals("!i")) {
			return new PAA_Install();
		} else if (name.contains("uninstall") || name.equals("!ui")) {
			return new PAA_Uninstall();
		} else if (name.contains("update") || name.equals("!u")) {
			return new PAA_Update();
		} else if (name.equals("list") || name.startsWith("-ls")) {
			return new PAI_ArenaList();
		} else if (name.contains("version") || name.startsWith("-v")) {
			return new PAI_Version();
		}
		
		return null;
	}
}
