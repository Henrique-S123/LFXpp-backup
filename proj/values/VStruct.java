package proj.values;

import java.util.HashMap;

public class VStruct implements IValue {

    private HashMap<String, IValue> vbl;
    boolean lin;

    public VStruct(HashMap<String, IValue> v, boolean l) {
        vbl = v;
        lin = l;
    }

    public HashMap<String, IValue> getList() {
        return vbl;
    }
    
    public String toStr() {
        String res = lin ? "linear struct {" : "struct {";

        for (String k : vbl.keySet()) {
            res += k + " = " + vbl.get(k).toStr() + "; ";
        }

        if (vbl.size() > 0) {
            res = res.substring(0, res.length()-2);
        }

        return res + "}";
    }
}