import java.io.IOException;
import java.util.Stack;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultCommand;
import org.nlogo.api.DefaultReporter;
import org.nlogo.api.Dump;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.LogoList;
import org.nlogo.api.Syntax;
import org.nlogo.nvm.ExtensionContext;
import org.nlogo.nvm.Workspace.OutputDestination;


public class StructuresExtension extends org.nlogo.api.DefaultClassManager {

	public java.util.List<String> additionalJars() {
		java.util.List<String> list = new java.util.ArrayList<String>();
		return list;
	}

	private static long nextEvent = 0;
	private static boolean debug = false;

	public void load(org.nlogo.api.PrimitiveManager primManager) {
		/**********************
		/* STRUCTS PRIMITIVES
		/**********************/
		primManager.addPrimitive("new-stack", new StackNew());
		primManager.addPrimitive("stack-push", new StackPush());
		primManager.addPrimitive("stack-pop", new StackPop());
		primManager.addPrimitive("stack-peek", new StackPeek());
		primManager.addPrimitive("stack-contains", new StackContains());
		primManager.addPrimitive("stack-count", new StackCount());
	}
	public void clearAll() {
	}
	@SuppressWarnings("unchecked")
	private static class LogoStack implements org.nlogo.api.ExtensionObject {
		public Stack<Object>	stack = null;

		LogoStack() throws ExtensionException {
			stack = new Stack<Object>();
		}
		public String dump(boolean arg1, boolean arg2, boolean arg3) {
			String dumpString = "[";
			for(Object obj : stack){
				dumpString += Dump.logoObject(obj) + ",";
			}
			if(dumpString.length()>1)dumpString = dumpString.substring(0, dumpString.length()-1);
			return dumpString + "]";
		}
		public String getExtensionName() {
			return "structs";
		}
		public String getNLTypeName() {
			return "logostack";
		}
		public boolean recursivelyEqual(Object arg0) {
			return equals(arg0);
		}
	}
	/***********************
	 * Convenience Methods
	 ***********************/
	private static Long dToL(double d){
		return ((Double)d).longValue();
	}
	private static Integer roundDouble(Double d){
		return ((Long)Math.round(d)).intValue();
	}
	private static Double getDoubleFromArgument(Argument args[], Integer argIndex) throws ExtensionException, LogoException {
		Object obj = args[argIndex].get();
		if (!(obj instanceof Double)) {
			throw new ExtensionException("time: was expecting a number as argument "+(argIndex+1)+", found this instead: " + Dump.logoObject(obj));
		}
		return (Double) obj;
	}
	private static LogoList getListFromArgument(Argument args[], Integer argIndex) throws ExtensionException, LogoException {
		Object obj = args[argIndex].get();
		if (!(obj instanceof LogoList)) {
			throw new ExtensionException("time: was expecting a list as argument "+(argIndex+1)+", found this instead: " + Dump.logoObject(obj));
		}
		return (LogoList) obj;
	}
	private static Integer getIntFromArgument(Argument args[], Integer argIndex) throws ExtensionException, LogoException {
		Object obj = args[argIndex].get();
		if (obj instanceof Double) {
			// Round to nearest int
			return roundDouble((Double)obj);
		}else if (!(obj instanceof Integer)) {
			throw new ExtensionException("time: was expecting a number as argument "+(argIndex+1)+", found this instead: " + Dump.logoObject(obj));
		}
		return (Integer) obj;
	}
	private static Long getLongFromArgument(Argument args[], Integer argIndex) throws ExtensionException, LogoException {
		Object obj = args[argIndex].get();
		if (obj instanceof Double) {
			return ((Double)obj).longValue();
		}else if (!(obj instanceof Integer)) {
			throw new ExtensionException("time: was expecting a number as argument "+(argIndex+1)+", found this instead: " + Dump.logoObject(obj));
		}
		return (Long) obj;
	}
	private static String getStringFromArgument(Argument args[], Integer argIndex) throws ExtensionException, LogoException {
		Object obj = args[argIndex].get();
		if (!(obj instanceof String)) {
			throw new ExtensionException("time: was expecting a string as argument "+(argIndex+1)+", found this instead: " + Dump.logoObject(obj));
		}
		return (String) obj;
	}
	private static LogoStack getStackFromArgument(Argument args[], Integer argIndex) throws ExtensionException, LogoException {
		LogoStack stack = null;
		Object obj = args[argIndex].get();
		if (obj instanceof LogoStack) {
			stack = (LogoStack)obj;
		}else{
			throw new ExtensionException("structures: was expecting a LogoStack object as argument "+(argIndex+1)+", found this instead: " + Dump.logoObject(obj));
		}
		return stack;
	}
	private static void printToLogfile(String msg){
		Logger logger = Logger.getLogger("MyLog");  
		FileHandler fh;  

		try {  
			// This block configure the logger with handler and formatter  
			fh = new FileHandler("logfile.txt",true);
			logger.addHandler(fh);  
			//logger.setLevel(Level.ALL);  
			SimpleFormatter formatter = new SimpleFormatter();  
			fh.setFormatter(formatter);  
			// the following statement is used to log any messages  
			logger.info(msg);
			fh.close();
		} catch (SecurityException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
	}
	private static void printToConsole(Context context, String msg) throws ExtensionException{
		try {
			ExtensionContext extcontext = (ExtensionContext) context;
			extcontext.workspace().outputObject(msg,null, true, true,OutputDestination.OUTPUT_AREA);
		} catch (LogoException e) {
			throw new ExtensionException(e);
		}
	}
	/***********************
	 * Primitive Classes
	 ***********************/
	public static class StackNew extends DefaultReporter{
		public Syntax getSyntax() {
			return Syntax.reporterSyntax(new int[]{},Syntax.WildcardType());
		}
		public Object report(Argument args[], Context context) throws ExtensionException, LogoException {
			return new LogoStack();
		}
	}
	public static class StackPush extends DefaultCommand{
		public Syntax getSyntax() {
			return Syntax.commandSyntax(new int[]{Syntax.WildcardType(),Syntax.WildcardType()});
		}
		public void perform(Argument args[], Context context) throws ExtensionException, LogoException {
			getStackFromArgument(args, 0).stack.push(args[1].get());
		}
	}
	public static class StackPop extends DefaultReporter{
		public Syntax getSyntax() {
			return Syntax.reporterSyntax(new int[]{Syntax.WildcardType()},Syntax.WildcardType());
		}
		public Object report(Argument args[], Context context) throws ExtensionException, LogoException {
			LogoStack logoStack = getStackFromArgument(args, 0);
			if(logoStack.stack.empty())throw new ExtensionException("attempted to pop an empty stack");
			return logoStack.stack.pop();
		}
	}
	public static class StackPeek extends DefaultReporter{
		public Syntax getSyntax() {
			return Syntax.reporterSyntax(new int[]{Syntax.WildcardType()},Syntax.WildcardType());
		}
		public Object report(Argument args[], Context context) throws ExtensionException, LogoException {
			LogoStack logoStack = getStackFromArgument(args, 0);
			if(logoStack.stack.empty())throw new ExtensionException("attempted to peek an empty stack");
			return logoStack.stack.peek();
		}
	}
	public static class StackContains extends DefaultReporter{
		public Syntax getSyntax() {
			return Syntax.reporterSyntax(new int[]{Syntax.WildcardType(),Syntax.WildcardType()},Syntax.BooleanType());
		}
		public Object report(Argument args[], Context context) throws ExtensionException, LogoException {
			return new Boolean(getStackFromArgument(args, 0).stack.contains(args[1].get()));
		}
	}
	public static class StackCount extends DefaultReporter{
		public Syntax getSyntax() {
			return Syntax.reporterSyntax(new int[]{Syntax.WildcardType()},Syntax.NumberType());
		}
		public Object report(Argument args[], Context context) throws ExtensionException, LogoException {
			return new Double(getStackFromArgument(args, 0).stack.size());
		}
	}
}