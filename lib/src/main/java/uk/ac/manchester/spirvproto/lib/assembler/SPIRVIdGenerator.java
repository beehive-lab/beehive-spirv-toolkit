package uk.ac.manchester.spirvproto.lib.assembler;

import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVId;

import java.util.HashMap;
import java.util.Map;

public class SPIRVIdGenerator {
    private int currentId;
    private final Map<Integer, SPIRVId> idNameMap;

    public SPIRVIdGenerator() {
        currentId = 1;
        idNameMap = new HashMap<>();
    }

    public SPIRVId getNextId() {
        return new SPIRVId(currentId++);
    }

    public int getCurrentBound() {
        return currentId;
    }

    public SPIRVId getOrCreateId(String name) {
        SPIRVId id;
        int key = name.hashCode();
        if (idNameMap.containsKey(key)) {
            id = idNameMap.get(key);
        }
        else {
            id = getNextId();
            idNameMap.put(key, id);
        }
        return id;
    }

    public SPIRVId getOrAddId(int id) {
        SPIRVId idObj;
        if (idNameMap.containsKey(id)) {
            idObj = idNameMap.get(id);
        }
        else {
            idObj = new SPIRVId(id);
            idNameMap.put(id, idObj);
        }

        return idObj;
    }
}
