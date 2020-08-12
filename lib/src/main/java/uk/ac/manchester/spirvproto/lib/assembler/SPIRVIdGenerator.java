package uk.ac.manchester.spirvproto.lib.assembler;

import uk.ac.manchester.spirvproto.lib.instructions.operands.SPIRVId;

import java.util.HashMap;
import java.util.Map;

public class SPIRVIdGenerator {
    private int currentId;
    private final Map<String, SPIRVId> idNameMap;

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
        if (idNameMap.containsKey(name)) {
            id = idNameMap.get(name);
        }
        else {
            id = getNextId();
            idNameMap.put(name, id);
        }
        return id;
    }
}
