package me.ByteEdit.decompiler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import com.strobel.assembler.InputTypeLoader;
import com.strobel.assembler.metadata.Buffer;
import com.strobel.assembler.metadata.ITypeLoader;
import com.strobel.assembler.metadata.MetadataSystem;
import com.strobel.assembler.metadata.TypeDefinition;
import com.strobel.assembler.metadata.TypeReference;
import com.strobel.decompiler.DecompilationOptions;
import com.strobel.decompiler.DecompilerSettings;
import com.strobel.decompiler.PlainTextOutput;

public class ProcyonDecompiler {

	public static String decompile(ClassNode cn, byte[] b, MethodNode mn) {
		try {
			// TODO decompile method only
			DecompilerSettings settings = new DecompilerSettings();
			try {
				for (Field f : settings.getClass().getDeclaredFields()) {
					if (f.getType() == boolean.class) {
						f.setAccessible(true);
						f.setBoolean(settings, true);
					}
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
			settings.setShowSyntheticMembers(true);
			MetadataSystem metadataSystem = new MetadataSystem(new ITypeLoader() {
				private InputTypeLoader backLoader = new InputTypeLoader();

				@Override
				public boolean tryLoadType(String s, Buffer buffer) {
					if (s.equals(cn.name)) {
						buffer.putByteArray(b, 0, b.length);
						buffer.position(0);
						return true;
					} else {
						return backLoader.tryLoadType(s, buffer);
					}
				}
			});
			TypeReference type = metadataSystem.lookupType(cn.name);
			DecompilationOptions decompilationOptions = new DecompilationOptions();
			decompilationOptions.setSettings(DecompilerSettings.javaDefaults());
			decompilationOptions.setFullDecompilation(true);
			TypeDefinition resolvedType = null;
			if (type == null || ((resolvedType = type.resolve()) == null)) {
				return "error";
			}
			StringWriter stringwriter = new StringWriter();
			settings.getLanguage().decompileType(resolvedType, new PlainTextOutput(stringwriter), decompilationOptions);
			String decompiledSource = stringwriter.toString();
			return decompiledSource;
		} catch (Exception e) {
			e.printStackTrace();
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return sw.toString();
		}
	}
}
