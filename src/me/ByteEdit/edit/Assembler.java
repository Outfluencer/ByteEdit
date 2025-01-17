package me.ByteEdit.edit;

import java.io.BufferedReader;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

import javax.swing.text.BadLocationException;

import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.IincInsnNode;
import org.objectweb.asm.tree.InnerClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.InvokeDynamicInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.MultiANewArrayInsnNode;
import org.objectweb.asm.tree.TableSwitchInsnNode;
import org.objectweb.asm.tree.TryCatchBlockNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import me.ByteEdit.main.Main;
import me.ByteEdit.utils.ClassUtil;
import me.ByteEdit.utils.CustomBufferedReader;
import me.ByteEdit.utils.OpcodesReverse;
import me.ByteEdit.utils.SwitchIntContainer;
import me.ByteEdit.utils.UnicodeUtils;

public class Assembler {

	public static final Pattern SPACE = Pattern.compile(" ");

	public static ClassNode assemble(String input) {
		Main.txtByteEditView.removeAllLineHighlights();
		CustomBufferedReader read = null;
		try {
			ClassNode clazz = new ClassNode();
			HugeStringsRev hsr = new HugeStringsRev();
			try (BufferedReader reader = new BufferedReader(new StringReader(input))) {
				String s;
				while (!(s = reader.readLine()).startsWith("// #SourceFile: ")) {
				}
				while (!(s = reader.readLine()).equals("// #Fields")) {
					if (s.startsWith("#")) {
						String[] split = SPACE.split(s, 2);
						hsr.add(Integer.parseInt(split[0].substring(1, split[0].length() - 1)),
								UnicodeUtils.unescape(null, split[1], true));
					}
				}
			}
			read = new CustomBufferedReader(new StringReader(input));
			String s;
			while (!(s = read.readLine()).startsWith("// #Class v:")) {
				if (clazz.visibleAnnotations == null) {
					clazz.visibleAnnotations = new ArrayList<>();
				}
				clazz.visibleAnnotations.add(parseAnnotation(hsr, s));
			}
			clazz.version = Integer.parseInt(s.substring(12));
			while ((s = read.readLine()).startsWith("// ")) {
				if (s.startsWith("// #Signature: ")) {
					clazz.signature = s.substring(15).equals("null") ? null
							: UnicodeUtils.unescape(hsr, s.substring(15));
				} else if (s.startsWith("// #OuterClass: ")) {
					clazz.outerClass = s.substring(16).equals("null") ? null
							: UnicodeUtils.unescape(hsr, s.substring(16));
				} else if (s.startsWith("// #OuterMethod: ")) {
					String[] split = s.split(" ");
					if (split.length > 3) {
						clazz.outerMethod = UnicodeUtils.unescape(hsr, split[2]);
						clazz.outerMethodDesc = UnicodeUtils.unescape(hsr, split[3]);
					}
				} else if (s.equals("// #InnerClasses:")) {
					clazz.innerClasses = new ArrayList<>();
				} else {
					String split[] = s.split(" ");
					int access = 0;
					String cons = s
							.substring(
									split[0].length() + split[1].length() + split[2].length() + split[3].length() + 3)
							.trim();
					if (cons.startsWith("0x")) {
						access = Integer.parseInt(cons.substring(2), 16);
					} else {
						if (cons.contains("public")) {
							access ^= ClassUtil.ACC_PUBLIC;
						}
						if (cons.contains("private")) {
							access ^= ClassUtil.ACC_PRIVATE;
						}
						if (cons.contains("protected")) {
							access ^= ClassUtil.ACC_PROTECTED;
						}
						if (cons.contains("static")) {
							access ^= ClassUtil.ACC_STATIC;
						}
						if (cons.contains("final")) {
							access ^= ClassUtil.ACC_FINAL;
						}
						if (cons.contains("synchronized")) {
							access ^= ClassUtil.ACC_SYNCHRONIZED;
						}
						if (cons.contains("bridge")) {
							access ^= ClassUtil.ACC_BRIDGE;
						}
						if (cons.contains("transient")) {
							access ^= ClassUtil.ACC_VARARGS;
						}
						if (cons.contains("native")) {
							access ^= ClassUtil.ACC_NATIVE;
						}
						if (cons.contains("interface")) {
							access ^= ClassUtil.ACC_INTERFACE;
						}
						if (cons.contains("abstract")) {
							access ^= ClassUtil.ACC_ABSTRACT;
						}
						if (cons.contains("strictfp")) {
							access ^= ClassUtil.ACC_STRICTFP;
						}
						if (cons.contains("synthetic")) {
							access ^= ClassUtil.ACC_SYNTHETIC;
						}
						if (cons.contains("annotation")) {
							access ^= ClassUtil.ACC_ANNOTATION;
						}
						if (cons.contains("enum")) {
							access ^= ClassUtil.ACC_ENUM;
						}
						if (cons.contains("mandated")) {
							access ^= ClassUtil.ACC_MANDATED;
						}
					}
					clazz.innerClasses.add(new InnerClassNode(UnicodeUtils.unescape(hsr, split[1]),
							split[2].equals("null") ? null : UnicodeUtils.unescape(hsr, split[2]),
							split[3].equals("null") ? null : UnicodeUtils.unescape(hsr, split[3]), access));
				}
			}
			{
				s = s.substring(0, s.length() - 2);
				if (s.contains(" implements ")) {
					String interfaces = s.substring(s.lastIndexOf(" implements ") + 12);
					clazz.interfaces = new ArrayList<>();
					for (String interf : interfaces.split(", ")) {
						clazz.interfaces.add(UnicodeUtils.unescape(hsr, interf));
					}
					s = s.substring(0, s.lastIndexOf(" implements "));
				}
				clazz.superName = UnicodeUtils.unescape(hsr, s.substring(s.lastIndexOf(" extends ") + 9));
				s = s.substring(0, s.lastIndexOf(" extends "));
				String[] split = s.split(" ");
				clazz.name = UnicodeUtils.unescape(hsr, split[split.length - 1]);
				clazz.access = ClassUtil.ACC_SUPER;
				switch (split[split.length - 2]) {
				case "class":
					break;
				case "enum":
					clazz.access ^= ClassUtil.ACC_ENUM;
					break;
				case "interface":
					clazz.access = 0;
					clazz.access ^= ClassUtil.ACC_ABSTRACT;
					clazz.access ^= ClassUtil.ACC_INTERFACE;
					break;
				case "@interface":
					clazz.access = 0;
					clazz.access ^= ClassUtil.ACC_ABSTRACT;
					clazz.access ^= ClassUtil.ACC_ANNOTATION;
					clazz.access ^= ClassUtil.ACC_INTERFACE;
					break;
				}
				if (split.length > 2) {
					String cons = consolidateStrings(split, 0, split.length - 2);
					if (cons.contains("public")) {
						clazz.access ^= ClassUtil.ACC_PUBLIC;
					}
					if (cons.contains("final")) {
						clazz.access ^= ClassUtil.ACC_FINAL;
					}
					if (cons.contains("synthetic")) {
						clazz.access ^= ClassUtil.ACC_SYNTHETIC;
					}
					if (cons.contains("abstract")) {
						clazz.access ^= ClassUtil.ACC_ABSTRACT;
					}
				}
			}
			s = read.readLine();
			clazz.sourceFile = s.substring(16).equals("null") ? null : UnicodeUtils.unescape(hsr, s.substring(16));
			while (!(s = read.readLine()).equals("// #Fields")) {
			}
			clazz.fields = new ArrayList<>();
			clazz.methods = new ArrayList<>();
			String signature = null;
			ArrayList<String> annotationsForNext = new ArrayList<>();
			while (!(s = read.readLine()).equals("// #Methods")) {
				s = s.trim();
				if (s.isEmpty())
					continue;
				if (s.startsWith("@")) {
					annotationsForNext.add(s);
				} else {
					if (s.startsWith("// #Signature: ")) {
						signature = UnicodeUtils.unescape(hsr, s.substring(15));
					} else {
						String[] split = s.split(" ");
						boolean hasValue = false;
						if (s.contains(" = ")) {
							hasValue = true;
						}
						Object value = hasValue ? split[split.length - 1] : null;
						if (hasValue) {
							split = s.substring(0, s.indexOf(" = ")).split(" ");
						}
						String name = UnicodeUtils.unescape(hsr, split[split.length - 1]);
						String desc = UnicodeUtils.unescape(hsr, split[split.length - 2]);
						value = getValue(hsr, (String) value, desc);
						int access = 0;
						if (split.length > 2) {
							String cons = consolidateStrings(split, 0, split.length - 2);
							if (cons.startsWith("0x")) {
								access = Integer.parseInt(cons.substring(2), 16);
							} else {
								if (cons.contains("public")) {
									access ^= ClassUtil.ACC_PUBLIC;
								}
								if (cons.contains("private")) {
									access ^= ClassUtil.ACC_PRIVATE;
								}
								if (cons.contains("protected")) {
									access ^= ClassUtil.ACC_PROTECTED;
								}
								if (cons.contains("static")) {
									access ^= ClassUtil.ACC_STATIC;
								}
								if (cons.contains("final")) {
									access ^= ClassUtil.ACC_FINAL;
								}
								if (cons.contains("synchronized")) {
									access ^= ClassUtil.ACC_SYNCHRONIZED;
								}
								if (cons.contains("bridge")) {
									access ^= ClassUtil.ACC_BRIDGE;
								}
								if (cons.contains("transient")) {
									access ^= ClassUtil.ACC_VARARGS;
								}
								if (cons.contains("native")) {
									access ^= ClassUtil.ACC_NATIVE;
								}
								if (cons.contains("interface")) {
									access ^= ClassUtil.ACC_INTERFACE;
								}
								if (cons.contains("abstract")) {
									access ^= ClassUtil.ACC_ABSTRACT;
								}
								if (cons.contains("strictfp")) {
									access ^= ClassUtil.ACC_STRICTFP;
								}
								if (cons.contains("synthetic")) {
									access ^= ClassUtil.ACC_SYNTHETIC;
								}
								if (cons.contains("annotation")) {
									access ^= ClassUtil.ACC_ANNOTATION;
								}
								if (cons.contains("enum")) {
									access ^= ClassUtil.ACC_ENUM;
								}
								if (cons.contains("mandated")) {
									access ^= ClassUtil.ACC_MANDATED;
								}
							}
						}
						FieldNode node = new FieldNode(access, name, desc, signature, value);
						if (!annotationsForNext.isEmpty()) {
							if (node.visibleAnnotations == null) {
								node.visibleAnnotations = new ArrayList<>();
							}
							for (String anno : annotationsForNext) {
								node.visibleAnnotations.add(parseAnnotation(hsr, anno));
							}
							annotationsForNext.clear();
						}
						clazz.fields.add(node);
						signature = null;
					}
				}
			}
			HashMap<LabelNode, Integer> methodLabelMap = new HashMap<LabelNode, Integer>();
			annotationsForNext.clear();
			MethodNode node = null;
			String temp = "";
			ArrayList<String> localVarsToParse = new ArrayList<>();
			ArrayList<String> tryCatchBlocksToParse = new ArrayList<>();
			int stage = 0;
			while ((s = read.readLine()) != null) {
				if (s.trim().isEmpty() || s.equals("}"))
					continue;
				s = s.substring(1);
				if (s.startsWith("@")) {
					annotationsForNext.add(s.trim());
				} else {
					if (s.startsWith("// #Max: ")) {
						node = new MethodNode();
						node.exceptions = new ArrayList<>();
						node.tryCatchBlocks = new ArrayList<>();
						node.localVariables = new ArrayList<>();
						node.instructions = new InsnList();
						s = s.substring(11);
						node.maxLocals = Integer.parseInt(s.split(" ")[0]);
						node.maxStack = Integer.parseInt(s.split(" ")[1].substring(2));
						localVarsToParse.clear();
						tryCatchBlocksToParse.clear();
						stage = 0;
					} else if (s.startsWith("// #Signature: ")) {
						node.signature = UnicodeUtils.unescape(hsr, s.substring(15));
					} else if (s.equals("// #TryCatch:")) {
						stage = 1;
					} else if (s.equals("// #LocalVars:")) {
						stage = 2;
					} else if (s.startsWith("// ")) {
						s = s.substring(3);
						if (stage == 1) {
							tryCatchBlocksToParse.add(s);
						} else if (stage == 2) {
							localVarsToParse.add(s);
						}
					} else if (s.equals("}")) {
						if (!annotationsForNext.isEmpty()) {
							if (node.visibleAnnotations == null) {
								node.visibleAnnotations = new ArrayList<>();
							}
							for (String anno : annotationsForNext) {
								node.visibleAnnotations.add(parseAnnotation(hsr, anno));
							}
							annotationsForNext.clear();
						}
						for (String st : localVarsToParse) {
							String[] sp = st.split(":");
							int start = Integer.parseInt(sp[3].substring(0, sp[3].length() - 2));
							int end = Integer.parseInt(sp[4].substring(0, sp[4].length() - 4));
							String signat = sp[5].equals("null") ? null : UnicodeUtils.unescape(hsr, sp[5]);
							LabelNode _start = null;
							LabelNode _end = null;
							for (Entry<LabelNode, Integer> entry : methodLabelMap.entrySet()) {
								if (entry.getValue().intValue() == start) {
									_start = entry.getKey();
								}
								if (entry.getValue().intValue() == end) {
									_end = entry.getKey();
								}
							}
							node.localVariables.add(new LocalVariableNode(UnicodeUtils.unescape(hsr, sp[0]),
									UnicodeUtils.unescape(hsr, sp[1].substring(1, sp[1].length() - 2)), signat, _start,
									_end, Integer.parseInt(sp[2].substring(0, sp[2].length() - 2))));
						}
						for (String st : tryCatchBlocksToParse) {
							String[] sp = st.split(" ");
							int start = Integer.parseInt(sp[1].substring(2));
							int end = Integer.parseInt(sp[2].substring(2));
							int handler = Integer.parseInt(sp[3].substring(2));
							LabelNode _start = null;
							LabelNode _end = null;
							LabelNode _handler = null;
							for (Entry<LabelNode, Integer> entry : methodLabelMap.entrySet()) {
								if (entry.getValue().intValue() == start) {
									_start = entry.getKey();
								}
								if (entry.getValue().intValue() == end) {
									_end = entry.getKey();
								}
								if (entry.getValue().intValue() == handler) {
									_handler = entry.getKey();
								}
							}
							node.tryCatchBlocks.add(new TryCatchBlockNode(_start, _end, _handler,
									sp[0].equals("null") ? null : UnicodeUtils.unescape(hsr, sp[0])));
						}
						clazz.methods.add(node);
					} else if (!s.startsWith("\t")) {
						s = s.substring(0, s.length() - 2);
						if (s.contains(" throws ")) {
							String exceptions = s.substring(s.lastIndexOf(" throws ") + 8);
							for (String excp : exceptions.split(", ")) {
								node.exceptions.add(UnicodeUtils.unescape(hsr, excp));
							}
							s = s.substring(0, s.lastIndexOf(" throws "));
						}
						String[] split = s.split(" ");
						node.desc = UnicodeUtils.unescape(hsr, split[split.length - 1]);
						node.name = UnicodeUtils.unescape(hsr, split[split.length - 2]);
						int access = 0;
						if (split.length > 2) {
							String cons = consolidateStrings(split, 0, split.length - 2);
							if (cons.startsWith("0x")) {
								access = Integer.parseInt(cons.substring(2), 16);
							} else {
								if (cons.contains("public")) {
									access ^= ClassUtil.ACC_PUBLIC;
								}
								if (cons.contains("private")) {
									access ^= ClassUtil.ACC_PRIVATE;
								}
								if (cons.contains("protected")) {
									access ^= ClassUtil.ACC_PROTECTED;
								}
								if (cons.contains("static")) {
									access ^= ClassUtil.ACC_STATIC;
								}
								if (cons.contains("final")) {
									access ^= ClassUtil.ACC_FINAL;
								}
								if (cons.contains("synchronized")) {
									access ^= ClassUtil.ACC_SYNCHRONIZED;
								}
								if (cons.contains("bridge")) {
									access ^= ClassUtil.ACC_BRIDGE;
								}
								if (cons.contains("varargs")) {
									access ^= ClassUtil.ACC_VARARGS;
								}
								if (cons.contains("native")) {
									access ^= ClassUtil.ACC_NATIVE;
								}
								if (cons.contains("interface")) {
									access ^= ClassUtil.ACC_INTERFACE;
								}
								if (cons.contains("abstract")) {
									access ^= ClassUtil.ACC_ABSTRACT;
								}
								if (cons.contains("strictfp")) {
									access ^= ClassUtil.ACC_STRICTFP;
								}
								if (cons.contains("synthetic")) {
									access ^= ClassUtil.ACC_SYNTHETIC;
								}
								if (cons.contains("annotation")) {
									access ^= ClassUtil.ACC_ANNOTATION;
								}
								if (cons.contains("enum")) {
									access ^= ClassUtil.ACC_ENUM;
								}
								if (cons.contains("mandated")) {
									access ^= ClassUtil.ACC_MANDATED;
								}
							}
						}
						node.access = access;
						methodLabelMap.clear();
					} else {
						s = s.substring(1);
						if (!temp.isEmpty()) {
							if (s.equals("]")) {
								temp += s;
								node.instructions.add(getNode(hsr, temp, methodLabelMap));
								temp = "";
							} else {
								temp += s + "\n";
							}
						} else {
							if (s.endsWith("[")) {
								temp += s + "\n";
							} else {
								node.instructions.add(getNode(hsr, s, methodLabelMap));
							}
						}
					}
				}
			}
			return clazz;
		} catch (Throwable e) {
			System.err.println("Error at line: " + read.currentLine);
			try {
				Main.txtByteEditView.setCaretPosition(Main.txtByteEditView.getLineStartOffset(read.currentLine));
			} catch (BadLocationException e1) {
				System.err.println("Can't show line!");
			}
			Main.showError(e.getMessage());
			e.printStackTrace();
			return null;
		} finally {
			try {
				read.close();
			} catch (Exception e) {
			}
		}
	}

	private static String[] splitWithStrings(String s) {
		ArrayList<String> ret = new ArrayList<>();
		String[] split = s.split(", ");
		String building = null;
		boolean inString = false;

		for (int i = 0; i < split.length; i++) {
			if (split[i].startsWith("(")) {
				ret.add(split[i]);
			} else if (split[i].startsWith("\"") && split[i].endsWith("\"")) {
				ret.add(split[i]);
			} else if (split[i].endsWith("\"") && inString) {
				if (building == null)
					ret.add(split[i]);
				else
					ret.add(building + ", " + split[i]);
				building = null;
			} else if (inString || split[i].startsWith("\"")) {
				inString = true;
				if (building == null)
					building = split[i];
				else
					building += ", " + split[i];
			} else {
				ret.add(split[i]);
			}
		}

		return ret.toArray(new String[0]);
	}

	private static AnnotationNode parseAnnotation(HugeStringsRev hsr, String s) {
		s = s.substring(1);
		String[] split = s.split(" ");
		AnnotationNode node = new AnnotationNode(UnicodeUtils.unescape(hsr, split[0]));
		if (split.length > 1) {
			if (node.values == null) {
				node.values = new ArrayList<>();
			}
			s = consolidateStrings(split, 1);
			s = s.substring(1, s.length() - 1);
			split = s.split("\\], ");
			for (int i = 0; i < split.length; i++) {
				String[] split2 = split[i].split(" = \\[");
				node.values.add(split2[0]);
				String value = split2[1];
				if (value.endsWith("]"))
					value = value.substring(0, value.length() - 1);
				if (value.startsWith("{ ")) {
					String split3[] = splitWithStrings(value.substring(2, value.length() - 2));
					List<Object> list = new ArrayList<>();
					for (String rofl : split3) {
						rofl = UnicodeUtils.unescape(hsr, rofl);
						if (rofl.startsWith("\"")) {
							int index = rofl.lastIndexOf("\"");
							list.add(rofl.substring(1, index));
						} else if (!rofl.startsWith("(")) {
							int index = rofl.indexOf(";");
							list.add(new String[] { rofl.substring(0, index) + ";",
									rofl.substring(index).substring(2) });
						} else {
							list.add(ClassUtil.getCastedValue(rofl.split(" ")[1], rofl.split("\\) ")[0].substring(1)));
						}
					}
					node.values.add(list);
				} else {
					value = UnicodeUtils.unescape(hsr, value);
					if (value.startsWith("\"")) {
						int index = value.lastIndexOf("\"");
						node.values.add(value.substring(1, index));
					} else if (!value.startsWith("(")) {
						int index = value.indexOf(";");
						node.values.add(
								new String[] { value.substring(0, index) + ";", value.substring(index).substring(2) });
					} else {
						node.values.add(
								ClassUtil.getCastedValue(value.split(" ")[1], value.split("\\) ")[0].substring(1)));
					}
				}
			}
		}
		return node;
	}

	private static String consolidateStrings(String[] args, int start) {
		if (args.length == 0) {
			return null;
		}
		String ret = args[start];
		if (args.length > start + 1) {
			for (int i = start + 1; i < args.length; i++) {
				ret = ret + " " + args[i];
			}
		}
		return ret;
	}

	private static String consolidateStrings(String[] args, int start, int end) {
		if (end == 0) {
			return null;
		}
		String ret = args[start];
		if (end > start + 1) {
			for (int i = start + 1; i < end; i++) {
				ret = ret + " " + args[i];
			}
		}
		return ret;
	}

	private static Object getValue(HugeStringsRev hsr, String s, String to) {
		if (s == null) {
			return null;
		}
		switch (to) {
		case "B":
			return Byte.parseByte(s);
		case "Z":
			return Boolean.parseBoolean(s);
		case "I":
			return Integer.parseInt(s);
		case "J":
			return Long.parseLong(s.substring(0, s.length() - 1));
		case "C":
			return (char) Integer.parseInt(s);
		case "D":
			return Double.parseDouble(s);
		case "F":
			return Float.parseFloat(s.substring(0, s.length() - 1));
		case "S":
			return Short.parseShort(s);
		case "Ljava/lang/String;":
			return UnicodeUtils.unescape(hsr, s.substring(1, s.length() - 1));
		default: {
			return null;
		}
		}
	}

	private static AbstractInsnNode getNode(HugeStringsRev hsr, String s, HashMap<LabelNode, Integer> labels)
			throws Exception {
		if (s.startsWith("// label ")) {
			int labelNr = Integer.parseInt(s.substring(9));
			for (Map.Entry<LabelNode, Integer> entry : labels.entrySet()) {
				if (entry.getValue() == labelNr) {
					return entry.getKey();
				}
			}
			LabelNode ln = new LabelNode(new Label());
			labels.put(ln, labelNr);
			return ln;
		} else if (s.startsWith("// line ")) {
			s = s.substring(8);
			int labelNr = Integer.parseInt(s.split(" ")[1]);
			for (Map.Entry<LabelNode, Integer> entry : labels.entrySet()) {
				if (entry.getValue() == labelNr) {
					return new LineNumberNode(Integer.parseInt(s.split(" ")[0]), entry.getKey());
				}
			}
			LabelNode ln = new LabelNode(new Label());
			labels.put(ln, labelNr);
			return new LineNumberNode(Integer.parseInt(s.split(" ")[0]), ln);
		} else if (s.startsWith("// frame ")) {
			String str1 = s.substring(9);
			String[] split = str1.split(" ", 2);
			int type = 0;
			Object[] local = null;
			Object[] stack = null;
			int localLen = 0;
			int stackLen = 0;
			switch (split[0].toUpperCase()) {
			case "SAME":
				type = Opcodes.F_SAME;
				break;
			case "SAME1":
				type = Opcodes.F_SAME1;
				stack = parseFrameList(hsr, split[1], labels);
				stackLen = stack.length;
				break;
			case "CHOP":
				type = Opcodes.F_CHOP;
				localLen = Integer.parseInt(split[1]);
				break;
			case "APPEND":
				type = Opcodes.F_APPEND;
				local = parseFrameList(hsr, split[1], labels);
				localLen = local.length;
				break;
			case "FULL":
				type = Opcodes.F_FULL;
				split = split[1].split("\\] \\[", 2);
				local = parseFrameList(hsr, split[0] + "]", labels);
				localLen = local.length;
				stack = parseFrameList(hsr, "[" + split[1], labels);
				stackLen = stack.length;
				break;
			}
			return new FrameNode(type, localLen, local, stackLen, stack);
		} else {
			String[] split = s.split(" ");
			String command = split[0];
			switch (command) {
			case "nop":
				return new InsnNode(0);
			case "aconst_null":
				return new InsnNode(1);
			case "iconst_m1":
				return new InsnNode(2);
			case "iconst_0":
				return new InsnNode(3);
			case "iconst_1":
				return new InsnNode(4);
			case "iconst_2":
				return new InsnNode(5);
			case "iconst_3":
				return new InsnNode(6);
			case "iconst_4":
				return new InsnNode(7);
			case "iconst_5":
				return new InsnNode(8);
			case "lconst_0":
				return new InsnNode(9);
			case "lconst_1":
				return new InsnNode(10);
			case "fconst_0":
				return new InsnNode(11);
			case "fconst_1":
				return new InsnNode(12);
			case "fconst_2":
				return new InsnNode(13);
			case "dconst_0":
				return new InsnNode(14);
			case "dconst_1":
				return new InsnNode(15);
			case "bipush": {
				return new IntInsnNode(16, (byte) Integer.parseInt(split[1]));
			}
			case "sipush": {
				return new IntInsnNode(17, (short) Integer.parseInt(split[1]));
			}
			case "ldc": {
				String str = consolidateStrings(split, 1);
				Object val;
				try {
					if (str.startsWith("Type: ")) {
						str = str.substring(8, str.length() - 2);
						String[] split2 = str.split("\n\t");
						String type = split2[0].substring(7);
						// Reflection
						val = new Type(ClassUtil.getIDFromClassNameForType(type),
								UnicodeUtils.unescape(hsr, split2[3].substring(6, split2[3].length() - 1)),
								Integer.parseInt(split2[1].substring(7)), Integer.parseInt(split2[2].substring(5)));
					} else if (str.startsWith("\"") && str.endsWith("\"")) {
						val = UnicodeUtils.unescape(hsr, str.substring(1, str.length() - 1), true);
					} else if (str.startsWith("#")) {
						val = UnicodeUtils.unescape(hsr, str);
					} else if (str.endsWith("l")) {
						val = Long.parseLong(str.substring(0, str.length() - 1));
					} else if (str.endsWith("f")) {
						val = Float.parseFloat(str.substring(0, str.length() - 1));
					} else if (str.contains(".")) {
						val = Double.parseDouble(str);
					} else {
						val = Integer.parseInt(str);
					}
				} catch (Exception e) {
					val = null;
					throw e;
				}
				return new LdcInsnNode(val);
			}
			case "iload": {
				return new VarInsnNode(21, Integer.parseInt(split[1]));
			}
			case "lload": {
				return new VarInsnNode(22, Integer.parseInt(split[1]));
			}
			case "fload": {
				return new VarInsnNode(23, Integer.parseInt(split[1]));
			}
			case "dload": {
				return new VarInsnNode(24, Integer.parseInt(split[1]));
			}
			case "aload": {
				return new VarInsnNode(25, Integer.parseInt(split[1]));
			}
			case "iaload": {
				return new InsnNode(46);
			}
			case "laload": {
				return new InsnNode(47);
			}
			case "faload": {
				return new InsnNode(48);
			}
			case "daload": {
				return new InsnNode(49);
			}
			case "aaload": {
				return new InsnNode(50);
			}
			case "baload": {
				return new InsnNode(51);
			}
			case "caload": {
				return new InsnNode(52);
			}
			case "saload": {
				return new InsnNode(53);
			}
			case "istore": {
				return new VarInsnNode(54, Integer.parseInt(split[1]));
			}
			case "lstore": {
				return new VarInsnNode(55, Integer.parseInt(split[1]));
			}
			case "fstore": {
				return new VarInsnNode(56, Integer.parseInt(split[1]));
			}
			case "dstore": {
				return new VarInsnNode(57, Integer.parseInt(split[1]));
			}
			case "astore": {
				return new VarInsnNode(58, Integer.parseInt(split[1]));
			}
			case "iastore": {
				return new InsnNode(79);
			}
			case "lastore": {
				return new InsnNode(80);
			}
			case "fastore": {
				return new InsnNode(81);
			}
			case "dastore": {
				return new InsnNode(82);
			}
			case "aastore": {
				return new InsnNode(83);
			}
			case "bastore": {
				return new InsnNode(84);
			}
			case "castore": {
				return new InsnNode(85);
			}
			case "sastore": {
				return new InsnNode(86);
			}
			case "pop": {
				return new InsnNode(87);
			}
			case "pop2": {
				return new InsnNode(88);
			}
			case "dup": {
				return new InsnNode(89);
			}
			case "dup_x1": {
				return new InsnNode(90);
			}
			case "dup_x2": {
				return new InsnNode(91);
			}
			case "dup2": {
				return new InsnNode(92);
			}
			case "dup2_x1": {
				return new InsnNode(93);
			}
			case "dup2_x2": {
				return new InsnNode(94);
			}
			case "swap": {
				return new InsnNode(95);
			}
			case "iadd": {
				return new InsnNode(96);
			}
			case "ladd": {
				return new InsnNode(97);
			}
			case "fadd": {
				return new InsnNode(98);
			}
			case "dadd": {
				return new InsnNode(99);
			}
			case "isub": {
				return new InsnNode(100);
			}
			case "lsub": {
				return new InsnNode(101);
			}
			case "fsub": {
				return new InsnNode(102);
			}
			case "dsub": {
				return new InsnNode(103);
			}
			case "imul": {
				return new InsnNode(104);
			}
			case "lmul": {
				return new InsnNode(105);
			}
			case "fmul": {
				return new InsnNode(106);
			}
			case "dmul": {
				return new InsnNode(107);
			}
			case "idiv": {
				return new InsnNode(108);
			}
			case "ldiv": {
				return new InsnNode(109);
			}
			case "fdiv": {
				return new InsnNode(110);
			}
			case "ddiv": {
				return new InsnNode(111);
			}
			case "irem": {
				return new InsnNode(112);
			}
			case "lrem": {
				return new InsnNode(113);
			}
			case "frem": {
				return new InsnNode(114);
			}
			case "drem": {
				return new InsnNode(115);
			}
			case "ineg": {
				return new InsnNode(116);
			}
			case "lneg": {
				return new InsnNode(117);
			}
			case "fneg": {
				return new InsnNode(118);
			}
			case "dneg": {
				return new InsnNode(119);
			}
			case "ishl": {
				return new InsnNode(120);
			}
			case "lshl": {
				return new InsnNode(121);
			}
			case "ishr": {
				return new InsnNode(122);
			}
			case "lshr": {
				return new InsnNode(123);
			}
			case "iushr": {
				return new InsnNode(124);
			}
			case "lushr": {
				return new InsnNode(125);
			}
			case "iand": {
				return new InsnNode(126);
			}
			case "land": {
				return new InsnNode(127);
			}
			case "ior": {
				return new InsnNode(128);
			}
			case "lor": {
				return new InsnNode(129);
			}
			case "ixor": {
				return new InsnNode(130);
			}
			case "lxor": {
				return new InsnNode(131);
			}
			case "iinc": {
				return new IincInsnNode(Integer.parseInt(split[1]), Integer.parseInt(split[2]));
			}
			case "i2l": {
				return new InsnNode(133);
			}
			case "i2f": {
				return new InsnNode(134);
			}
			case "i2d": {
				return new InsnNode(135);
			}
			case "l2i": {
				return new InsnNode(136);
			}
			case "l2f": {
				return new InsnNode(137);
			}
			case "l2d": {
				return new InsnNode(138);
			}
			case "f2i": {
				return new InsnNode(139);
			}
			case "f2l": {
				return new InsnNode(140);
			}
			case "f2d": {
				return new InsnNode(141);
			}
			case "d2i": {
				return new InsnNode(142);
			}
			case "d2l": {
				return new InsnNode(143);
			}
			case "d2f": {
				return new InsnNode(144);
			}
			case "i2b": {
				return new InsnNode(145);
			}
			case "i2c": {
				return new InsnNode(146);
			}
			case "i2s": {
				return new InsnNode(147);
			}
			case "lcmp": {
				return new InsnNode(148);
			}
			case "fcmpl": {
				return new InsnNode(149);
			}
			case "fcmpg": {
				return new InsnNode(150);
			}
			case "dcmpl": {
				return new InsnNode(151);
			}
			case "dcmpg": {
				return new InsnNode(152);
			}
			case "ifeq": {
				int labelNr = Integer.parseInt(s.split(" ")[1]);
				for (Map.Entry<LabelNode, Integer> entry : labels.entrySet()) {
					if (entry.getValue() == labelNr) {
						return new JumpInsnNode(153, entry.getKey());
					}
				}
				LabelNode ln = new LabelNode(new Label());
				labels.put(ln, labelNr);
				return new JumpInsnNode(153, ln);
			}
			case "ifne": {
				int labelNr = Integer.parseInt(s.split(" ")[1]);
				for (Map.Entry<LabelNode, Integer> entry : labels.entrySet()) {
					if (entry.getValue() == labelNr) {
						return new JumpInsnNode(154, entry.getKey());
					}
				}
				LabelNode ln = new LabelNode(new Label());
				labels.put(ln, labelNr);
				return new JumpInsnNode(154, ln);
			}
			case "iflt": {
				int labelNr = Integer.parseInt(s.split(" ")[1]);
				for (Map.Entry<LabelNode, Integer> entry : labels.entrySet()) {
					if (entry.getValue() == labelNr) {
						return new JumpInsnNode(155, entry.getKey());
					}
				}
				LabelNode ln = new LabelNode(new Label());
				labels.put(ln, labelNr);
				return new JumpInsnNode(155, ln);
			}
			case "ifge": {
				int labelNr = Integer.parseInt(s.split(" ")[1]);
				for (Map.Entry<LabelNode, Integer> entry : labels.entrySet()) {
					if (entry.getValue() == labelNr) {
						return new JumpInsnNode(156, entry.getKey());
					}
				}
				LabelNode ln = new LabelNode(new Label());
				labels.put(ln, labelNr);
				return new JumpInsnNode(156, ln);
			}
			case "ifgt": {
				int labelNr = Integer.parseInt(s.split(" ")[1]);
				for (Map.Entry<LabelNode, Integer> entry : labels.entrySet()) {
					if (entry.getValue() == labelNr) {
						return new JumpInsnNode(157, entry.getKey());
					}
				}
				LabelNode ln = new LabelNode(new Label());
				labels.put(ln, labelNr);
				return new JumpInsnNode(157, ln);
			}
			case "ifle": {
				int labelNr = Integer.parseInt(s.split(" ")[1]);
				for (Map.Entry<LabelNode, Integer> entry : labels.entrySet()) {
					if (entry.getValue() == labelNr) {
						return new JumpInsnNode(158, entry.getKey());
					}
				}
				LabelNode ln = new LabelNode(new Label());
				labels.put(ln, labelNr);
				return new JumpInsnNode(158, ln);
			}
			case "if_icmpeq": {
				int labelNr = Integer.parseInt(s.split(" ")[1]);
				for (Map.Entry<LabelNode, Integer> entry : labels.entrySet()) {
					if (entry.getValue() == labelNr) {
						return new JumpInsnNode(159, entry.getKey());
					}
				}
				LabelNode ln = new LabelNode(new Label());
				labels.put(ln, labelNr);
				return new JumpInsnNode(159, ln);
			}
			case "if_icmpne": {
				int labelNr = Integer.parseInt(s.split(" ")[1]);
				for (Map.Entry<LabelNode, Integer> entry : labels.entrySet()) {
					if (entry.getValue() == labelNr) {
						return new JumpInsnNode(160, entry.getKey());
					}
				}
				LabelNode ln = new LabelNode(new Label());
				labels.put(ln, labelNr);
				return new JumpInsnNode(160, ln);
			}
			case "if_icmplt": {
				int labelNr = Integer.parseInt(s.split(" ")[1]);
				for (Map.Entry<LabelNode, Integer> entry : labels.entrySet()) {
					if (entry.getValue() == labelNr) {
						return new JumpInsnNode(161, entry.getKey());
					}
				}
				LabelNode ln = new LabelNode(new Label());
				labels.put(ln, labelNr);
				return new JumpInsnNode(161, ln);
			}
			case "if_icmpge": {
				int labelNr = Integer.parseInt(s.split(" ")[1]);
				for (Map.Entry<LabelNode, Integer> entry : labels.entrySet()) {
					if (entry.getValue() == labelNr) {
						return new JumpInsnNode(162, entry.getKey());
					}
				}
				LabelNode ln = new LabelNode(new Label());
				labels.put(ln, labelNr);
				return new JumpInsnNode(162, ln);
			}
			case "if_icmpgt": {
				int labelNr = Integer.parseInt(s.split(" ")[1]);
				for (Map.Entry<LabelNode, Integer> entry : labels.entrySet()) {
					if (entry.getValue() == labelNr) {
						return new JumpInsnNode(163, entry.getKey());
					}
				}
				LabelNode ln = new LabelNode(new Label());
				labels.put(ln, labelNr);
				return new JumpInsnNode(163, ln);
			}
			case "if_icmple": {
				int labelNr = Integer.parseInt(s.split(" ")[1]);
				for (Map.Entry<LabelNode, Integer> entry : labels.entrySet()) {
					if (entry.getValue() == labelNr) {
						return new JumpInsnNode(164, entry.getKey());
					}
				}
				LabelNode ln = new LabelNode(new Label());
				labels.put(ln, labelNr);
				return new JumpInsnNode(164, ln);
			}
			case "if_acmpeq": {
				int labelNr = Integer.parseInt(s.split(" ")[1]);
				for (Map.Entry<LabelNode, Integer> entry : labels.entrySet()) {
					if (entry.getValue() == labelNr) {
						return new JumpInsnNode(165, entry.getKey());
					}
				}
				LabelNode ln = new LabelNode(new Label());
				labels.put(ln, labelNr);
				return new JumpInsnNode(165, ln);
			}
			case "if_acmpne": {
				int labelNr = Integer.parseInt(s.split(" ")[1]);
				for (Map.Entry<LabelNode, Integer> entry : labels.entrySet()) {
					if (entry.getValue() == labelNr) {
						return new JumpInsnNode(166, entry.getKey());
					}
				}
				LabelNode ln = new LabelNode(new Label());
				labels.put(ln, labelNr);
				return new JumpInsnNode(166, ln);
			}
			case "goto": {
				int labelNr = Integer.parseInt(s.split(" ")[1]);
				for (Map.Entry<LabelNode, Integer> entry : labels.entrySet()) {
					if (entry.getValue() == labelNr) {
						return new JumpInsnNode(167, entry.getKey());
					}
				}
				LabelNode ln = new LabelNode(new Label());
				labels.put(ln, labelNr);
				return new JumpInsnNode(167, ln);
			}
			case "jsr": {
				int labelNr = Integer.parseInt(s.split(" ")[1]);
				for (Map.Entry<LabelNode, Integer> entry : labels.entrySet()) {
					if (entry.getValue() == labelNr) {
						return new JumpInsnNode(168, entry.getKey());
					}
				}
				LabelNode ln = new LabelNode(new Label());
				labels.put(ln, labelNr);
				return new JumpInsnNode(168, ln);
			}
			case "ret": {
				return new VarInsnNode(169, Integer.parseInt(s.split(" ")[1]));
			}
			case "tableswitch": {
				String str = s.substring(14, s.length() - 2).replace("\t", "");
				String split2[] = str.split("\n");
				LinkedHashMap<SwitchIntContainer, LabelNode> labelMap = new LinkedHashMap<>();
				HashMap<BigInteger, LabelNode> labelIDs = new HashMap<>();
				for (int i = 4; i < split2.length - 1; i++) {
					if (split2[i].equals("null")) {
						labelMap.put(new SwitchIntContainer(-1 - labelMap.size()), null);
					} else {
						labelMap.put(new SwitchIntContainer(Integer.parseInt(split2[i])), null);
					}
				}
				LabelNode dflt = null;
				if (!split2[2].substring(9).equals("null")) {
					int dfltLabelNr = Integer.parseInt(split2[2].substring(9));
					for (Entry<LabelNode, Integer> entry : labels.entrySet()) {
						if (entry.getValue() == dfltLabelNr) {
							dflt = entry.getKey();
							labelIDs.put(BigInteger.valueOf(dfltLabelNr), dflt);
						}
						labelMap.replaceAll(new BiFunction<SwitchIntContainer, LabelNode, LabelNode>() {

							@Override
							public LabelNode apply(SwitchIntContainer t, LabelNode u) {
								if (entry.getValue().intValue() == t.getId()) {
									return entry.getKey();
								}
								return u;
							}

						});
					}
					if (dflt == null) {
						LabelNode ln = new LabelNode(new Label());
						labels.put(ln, dfltLabelNr);
						dflt = ln;
						labelIDs.put(BigInteger.valueOf(dfltLabelNr), dflt);
					}
				} else {
					dflt = new LabelNode(new Label());
				}
				LabelNode[] arr = new LabelNode[labelMap.size()];
				int counter = 0;
				for (Entry<SwitchIntContainer, LabelNode> entry : labelMap.entrySet()) {
					LabelNode ln;
					if (((ln = labelIDs.get(BigInteger.valueOf(entry.getKey().getId()))) == null)) {
						ln = new LabelNode(new Label());
						labels.put(ln, entry.getKey().getId());
						labelIDs.put(BigInteger.valueOf(entry.getKey().getId()), ln);
					}
					arr[counter] = ln;
					counter++;
				}
				return new TableSwitchInsnNode(Integer.parseInt(split2[0].substring(5)),
						Integer.parseInt(split2[1].substring(5)), dflt, arr);
			}
			case "lookupswitch": {
				String str = s.substring(15, s.length() - 2).replace("\t", "");
				String split2[] = str.split("\n");
				ArrayList<Integer> keys = new ArrayList<>();
				LinkedHashMap<SwitchIntContainer, LabelNode> labelMap = new LinkedHashMap<>();
				HashMap<BigInteger, LabelNode> labelIDs = new HashMap<>();
				int stage = 0;
				for (int i = 2; i < split2.length; i++) {
					String spl = split2[i];
					if (spl.equals("]")) {
						stage = 1;
					} else if (spl.equals("labels: [")) {
						stage = 2;
					} else if (stage == 0) {
						keys.add(Integer.parseInt(spl));
					} else if (stage == 2) {
						if (spl.equals("null")) {
							labelMap.put(new SwitchIntContainer(-1 - labelMap.size()), null);
						} else {
							labelMap.put(new SwitchIntContainer(Integer.parseInt(spl)), null);
						}
					}
				}
				LabelNode dflt = null;
				int labelNr = Integer.parseInt(split2[0].substring(9));
				for (Entry<LabelNode, Integer> entry : labels.entrySet()) {
					if (entry.getValue() == labelNr) {
						dflt = entry.getKey();
						labelIDs.put(BigInteger.valueOf(labelNr), dflt);
					}
					labelMap.replaceAll(new BiFunction<SwitchIntContainer, LabelNode, LabelNode>() {

						@Override
						public LabelNode apply(SwitchIntContainer t, LabelNode u) {
							if (entry.getValue().intValue() == t.getId()) {
								return entry.getKey();
							}
							return u;
						}

					});
				}
				if (dflt == null) {
					LabelNode ln = new LabelNode(new Label());
					labels.put(ln, labelNr);
					dflt = ln;
					labelIDs.put(BigInteger.valueOf(labelNr), dflt);
				}
				LabelNode[] arr = new LabelNode[labelMap.size()];
				int counter = 0;
				for (Entry<SwitchIntContainer, LabelNode> entry : labelMap.entrySet()) {
					LabelNode ln;
					if (((ln = labelIDs.get(BigInteger.valueOf(entry.getKey().getId()))) == null)) {
						ln = new LabelNode(new Label());
						labels.put(ln, entry.getKey().getId());
						labelIDs.put(BigInteger.valueOf(entry.getKey().getId()), ln);
					}
					arr[counter] = ln;
					counter++;
				}
				int[] _keys = new int[keys.size()];
				counter = 0;
				for (int i : keys) {
					_keys[counter] = i;
					counter++;
				}
				return new LookupSwitchInsnNode(dflt, _keys, arr);
			}
			case "ireturn": {
				return new InsnNode(172);
			}
			case "lreturn": {
				return new InsnNode(173);
			}
			case "freturn": {
				return new InsnNode(174);
			}
			case "dreturn": {
				return new InsnNode(175);
			}
			case "areturn": {
				return new InsnNode(176);
			}
			case "return": {
				return new InsnNode(177);
			}
			case "getstatic": {
				String target = split[2];
				int index = target.lastIndexOf("/");
				return new FieldInsnNode(178, UnicodeUtils.unescape(hsr, target.substring(0, index)),
						UnicodeUtils.unescape(hsr, target.substring(index + 1)), UnicodeUtils.unescape(hsr, split[1]));
			}
			case "putstatic": {
				String target = split[2];
				int index = target.lastIndexOf("/");
				return new FieldInsnNode(179, UnicodeUtils.unescape(hsr, target.substring(0, index)),
						UnicodeUtils.unescape(hsr, target.substring(index + 1)), UnicodeUtils.unescape(hsr, split[1]));
			}
			case "getfield": {
				String target = split[2];
				int index = target.lastIndexOf("/");
				return new FieldInsnNode(180, UnicodeUtils.unescape(hsr, target.substring(0, index)),
						UnicodeUtils.unescape(hsr, target.substring(index + 1)), UnicodeUtils.unescape(hsr, split[1]));
			}
			case "putfield": {
				String target = split[2];
				int index = target.lastIndexOf("/");
				return new FieldInsnNode(181, UnicodeUtils.unescape(hsr, target.substring(0, index)),
						UnicodeUtils.unescape(hsr, target.substring(index + 1)), UnicodeUtils.unescape(hsr, split[1]));
			}
			case "invokevirtual": {
				String target = split[2];
				int index = target.lastIndexOf("/");
				return new MethodInsnNode(182, UnicodeUtils.unescape(hsr, target.substring(0, index)),
						UnicodeUtils.unescape(hsr, target.substring(index + 1)), UnicodeUtils.unescape(hsr, split[1]),
						false);
			}
			case "invokespecial": {
				String target = split[2];
				int index = target.lastIndexOf("/");
				return new MethodInsnNode(183, UnicodeUtils.unescape(hsr, target.substring(0, index)),
						UnicodeUtils.unescape(hsr, target.substring(index + 1)), UnicodeUtils.unescape(hsr, split[1]),
						false);
			}
			case "invokestatic": {
				String target = split[2];
				int index = target.lastIndexOf("/");
				return new MethodInsnNode(184, UnicodeUtils.unescape(hsr, target.substring(0, index)),
						UnicodeUtils.unescape(hsr, target.substring(index + 1)), UnicodeUtils.unescape(hsr, split[1]),
						false);
			}
			case "invokeinterface": {
				String target = split[2];
				int index = target.lastIndexOf("/");
				return new MethodInsnNode(185, UnicodeUtils.unescape(hsr, target.substring(0, index)),
						UnicodeUtils.unescape(hsr, target.substring(index + 1)), UnicodeUtils.unescape(hsr, split[1]),
						true);
			}
			case "invokedynamic": {
				String str = s.substring(16, s.length() - 2).replace("", "");
				String sp[] = str.split("\n\t");
				ArrayList<Object> args = new ArrayList<>();
				ArrayList<String> vals = new ArrayList<>();
				int stage = 0;
				for (int i = 10; i < sp.length - 1; i++) {
					String st = sp[i].substring(1);
					if (st.equals("]")) {
						if (stage == 1) {
							try {
								// Reflection
								args.add(new Type(ClassUtil.getIDFromClassNameForType(vals.get(0).substring(6)),
										UnicodeUtils.unescape(hsr, vals.get(3).substring(6, vals.get(3).length() - 1)),
										Integer.parseInt(vals.get(1).substring(7)),
										Integer.parseInt(vals.get(2).substring(5))));
							} catch (Exception e) {
								throw e;
							}
						} else if (stage == 2) {
							args.add(new Handle(OpcodesReverse.getHandleOpcode(vals.get(4).substring(5)),
									UnicodeUtils.unescape(hsr, vals.get(1).substring(7)),
									UnicodeUtils.unescape(hsr, vals.get(0).substring(6)),
									UnicodeUtils.unescape(hsr, vals.get(2).substring(6)),
									Boolean.parseBoolean(vals.get(3).substring(13))));
							vals.clear();
						}
						stage = 0;
					} else if (st.equals("Type: [")) {
						stage = 1;
					} else if (stage == 1) {
						st = st.substring(1);
						vals.add(st);
					} else if (st.equals("Handle: [")) {
						vals.clear();
						stage = 2;
					} else if (stage == 2) {
						st = st.substring(1);
						vals.add(st);
					} else if (stage == 0) {
						if (st.startsWith("\"") && st.endsWith("\"")) {
							args.add(UnicodeUtils.unescape(hsr, st.substring(1, st.length() - 1)));
						} else if (st.endsWith("l")) {
							args.add(Long.parseLong(st.substring(0, st.length() - 1)));
						} else if (st.endsWith("f")) {
							args.add(Float.parseFloat(st.substring(0, st.length() - 1)));
						} else if (st.contains(".")) {
							args.add(Double.parseDouble(st));
						} else {
							args.add(Integer.parseInt(st));
						}
					}
				}
				Object[] _args = new Object[args.size()];
				stage = 0;
				for (Object o : args) {
					_args[stage] = o;
					stage++;
				}
				return new InvokeDynamicInsnNode(UnicodeUtils.unescape(hsr, sp[0].substring(7)),
						UnicodeUtils.unescape(hsr, sp[1].substring(6)),
						new Handle(OpcodesReverse.getHandleOpcode(sp[7].substring(6)),
								UnicodeUtils.unescape(hsr, sp[4].substring(8)),
								UnicodeUtils.unescape(hsr, sp[3].substring(7)),
								UnicodeUtils.unescape(hsr, sp[5].substring(7)),
								Boolean.parseBoolean(sp[6].substring(14))),
						_args);
			}
			case "new": {
				return new TypeInsnNode(187, UnicodeUtils.unescape(hsr, split[1]));
			}
			case "newarray": {
				return new IntInsnNode(188, ClassUtil.getArrayIDByType(split[1]));
			}
			case "anewarray": {
				return new TypeInsnNode(189, UnicodeUtils.unescape(hsr, split[1]));
			}
			case "arraylength": {
				return new InsnNode(190);
			}
			case "athrow": {
				return new InsnNode(191);
			}
			case "checkcast": {
				return new TypeInsnNode(192, UnicodeUtils.unescape(hsr, split[1]));
			}
			case "instanceof": {
				return new TypeInsnNode(193, UnicodeUtils.unescape(hsr, split[1]));
			}
			case "monitorenter": {
				return new InsnNode(194);
			}
			case "monitorexit": {
				return new InsnNode(195);
			}
			case "multianewarray": {
				return new MultiANewArrayInsnNode(UnicodeUtils.unescape(hsr, split[1]), Integer.parseInt(split[2]));
			}
			case "ifnull": {
				int labelNr = Integer.parseInt(s.split(" ")[1]);
				for (Map.Entry<LabelNode, Integer> entry : labels.entrySet()) {
					if (entry.getValue() == labelNr) {
						return new JumpInsnNode(198, entry.getKey());
					}
				}
				LabelNode ln = new LabelNode(new Label());
				labels.put(ln, labelNr);
				return new JumpInsnNode(198, ln);
			}
			case "ifnonnull": {
				int labelNr = Integer.parseInt(s.split(" ")[1]);
				for (Map.Entry<LabelNode, Integer> entry : labels.entrySet()) {
					if (entry.getValue() == labelNr) {
						return new JumpInsnNode(199, entry.getKey());
					}
				}
				LabelNode ln = new LabelNode(new Label());
				labels.put(ln, labelNr);
				return new JumpInsnNode(199, ln);
			}
			default:
				throw new IllegalArgumentException("Illegal Instruction: " + s);
			}
		}
	}

	private static Object[] parseFrameList(HugeStringsRev hsr, String str, HashMap<LabelNode, Integer> labels) {
		ArrayList<Object> list = new ArrayList<>();
		String l = str.substring(1, str.length() - 1);
		if (l.isEmpty()) {
			return new Object[0];
		} else {
			for (String asd : l.split(", ")) {
				if (!asd.startsWith("(")) {
					int frameType = ClassUtil.getFrameTypeByName(asd);
					if (frameType != -1)
						list.add(new Integer(frameType));
					else
						list.add(UnicodeUtils.unescape(hsr, asd.substring(1, asd.length() - 1)));
				} else {
					if (asd.startsWith("(label) ")) {
						int labelNr = Integer.parseInt(asd.split(" ")[1]);
						boolean found = false;
						for (Map.Entry<LabelNode, Integer> entry : labels.entrySet()) {
							if (entry.getValue() == labelNr) {
								list.add(entry.getKey());
								found = true;
							}
						}
						if (!found) {
							LabelNode ln = new LabelNode(new Label());
							labels.put(ln, labelNr);
							list.add(ln);
						}
					} else {
						list.add(ClassUtil.getCastedValue(asd.split(" ")[1], asd.split("\\) ")[0].substring(1)));
					}
				}
			}
			Object[] arr = new Object[list.size()];
			int c = 0;
			for (Object o : list) {
				arr[c] = o;
				c++;
			}
			return arr;
		}
	}

}
