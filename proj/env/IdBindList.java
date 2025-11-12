package proj.env;

import java.util.HashMap;

public class IdBindList  {

        private HashMap<String, String> lbl;

        public IdBindList(HashMap<String, String> ll) {
                lbl = ll;
        }

        public HashMap<String, String> getMap() {
                return lbl;
        }
}