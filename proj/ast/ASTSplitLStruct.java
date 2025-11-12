package proj.ast;

import proj.values.*;
import proj.types.*;
import proj.env.*;
import proj.errors.*;

import java.util.Map;

public class ASTSplitLStruct implements ASTNode {

    ASTNode struct, body;
	IdBindList ill;

	public ASTSplitLStruct(ASTNode s, IdBindList i, ASTNode b) {
		struct = s;
		ill = i;
		body = b;
    }

    public IValue eval(Environment<IValue> e) throws InterpreterError {
		IValue vs = struct.eval(e);
		if (vs instanceof VStruct) {
			Environment<IValue> en = e.beginScope();
			for (String label : ill.getMap().keySet()) {
				en.assoc(ill.getMap().get(label), ((VStruct) vs).getList().get(label));
			}
			return body.eval(en);
		} else {
			throw new InterpreterError("struct split: struct expected, found " + vs.toStr());
		}
    }

	public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError, InterpreterError {
		ASTType ts = struct.typecheck(e);
		ts = e.unfold(ts);
		if (ts instanceof ASTTLStruct) {
			Environment<ASTType> en = e.beginScope();
			for (Map.Entry<String, ASTType> entry : ((ASTTLStruct) ts).getList().getMap().entrySet()) {
				if (ill.getMap().keySet().contains(entry.getKey())) {
					en.assoc(ill.getMap().get(entry.getKey()), entry.getValue());
				} else {
					throw new TypeCheckError("split missing label " + entry.getKey());
				}
			}
			if (!(en.getLinears().isEmpty()))
				throw new TypeCheckError("there are unused linear values: " + en.deltaToStr());
			return body.typecheck(en);
		} else {
			throw new TypeCheckError("illegal type to struct split: " + ts.toStr());
		}
	}
}
