/*
 * Copyright (c) 2008-2012, Hazel Bilisim Ltd. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.impl.map;

import com.hazelcast.impl.DefaultRecord;
import com.hazelcast.impl.Record;
import com.hazelcast.impl.spi.BackupOperation;
import com.hazelcast.impl.spi.OperationContext;
import com.hazelcast.nio.Data;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class PutBackupOperation extends PutOperation implements BackupOperation {

    boolean sendResponse = true;

    public PutBackupOperation(String name, Data dataKey, Data dataValue, long ttl) {
        this(name, dataKey, dataValue, ttl, true);
    }

    public PutBackupOperation(String name, Data dataKey, Data dataValue, long ttl, boolean sendResponse) {
        super(name, dataKey, dataValue, null, ttl);
        this.sendResponse = sendResponse;
    }

    public PutBackupOperation() {
    }

    public void run() {
        OperationContext context = getOperationContext();
        MapService mapService = (MapService) context.getService();
        System.out.println(context.getNodeService().getThisAddress() + " backup " + txnId + " response " + sendResponse);
        MapPartition mapPartition = mapService.getMapPartition(context.getPartitionId(), name);
        Record record = mapPartition.records.get(dataKey);
        if (record == null) {
            record = new DefaultRecord(null, mapPartition.partitionInfo.getPartitionId(), dataKey, dataValue, -1, -1, mapService.nextId());
            mapPartition.records.put(dataKey, record);
        } else {
            record.setValueData(dataValue);
        }
        record.setActive();
        record.setDirty(true);
        if (sendResponse) context.getResponseHandler().sendResponse(null);
    }

    @Override
    public void writeData(DataOutput out) throws IOException {
        super.writeData(out);
        out.writeBoolean(sendResponse);
    }

    @Override
    public void readData(DataInput in) throws IOException {
        super.readData(in);
        sendResponse = in.readBoolean();
    }

    @Override
    public String toString() {
        return "PutBackupOperation{}";
    }
}
