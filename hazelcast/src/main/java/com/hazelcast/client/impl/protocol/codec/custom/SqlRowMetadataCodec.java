/*
 * Copyright (c) 2008-2020, Hazelcast, Inc. All Rights Reserved.
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

package com.hazelcast.client.impl.protocol.codec.custom;

import com.hazelcast.client.impl.protocol.ClientMessage;
import com.hazelcast.client.impl.protocol.Generated;
import com.hazelcast.client.impl.protocol.codec.builtin.*;

import static com.hazelcast.client.impl.protocol.codec.builtin.CodecUtil.fastForwardToEndFrame;
import static com.hazelcast.client.impl.protocol.ClientMessage.*;
import static com.hazelcast.client.impl.protocol.codec.builtin.FixedSizeTypesCodec.*;

@Generated("3ab721ecec146f3f57420dcf3e1ed90e")
public final class SqlRowMetadataCodec {

    private SqlRowMetadataCodec() {
    }

    public static void encode(ClientMessage clientMessage, com.hazelcast.sql.SqlRowMetadata sqlRowMetadata) {
        clientMessage.add(BEGIN_FRAME.copy());

        ListMultiFrameCodec.encode(clientMessage, sqlRowMetadata.getColumns(), SqlColumnMetadataCodec::encode);

        clientMessage.add(END_FRAME.copy());
    }

    public static com.hazelcast.sql.SqlRowMetadata decode(ClientMessage.ForwardFrameIterator iterator) {
        // begin frame
        iterator.next();

        java.util.List<com.hazelcast.sql.SqlColumnMetadata> columns = ListMultiFrameCodec.decode(iterator, SqlColumnMetadataCodec::decode);

        fastForwardToEndFrame(iterator);

        return new com.hazelcast.sql.SqlRowMetadata(columns);
    }
}
