package com.evernym.verity.sdk.protocols.connecting.v_0_6;

import com.evernym.verity.sdk.TestHelpers;
import com.evernym.verity.sdk.exceptions.VerityException;
import com.evernym.verity.sdk.protocols.TestUtil;
import com.evernym.verity.sdk.protocols.connecting.Connecting;
import com.evernym.verity.sdk.utils.Context;
import com.evernym.verity.sdk.utils.Util;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConnectingTest {
    private String sourceId = "source_id";
    private String phoneNumber = "123-456-7891";
    private boolean includePublicDID = true;

    @Test
    public void testGetMessageType() {
        Connecting connecting = Connecting.v_06("none");
        String msgName = "msg name";
        assertEquals(
            Util.getMessageType(
                    Util.EVERNYM_MSG_QUALIFIER,
                    "connecting",
                    "0.6",
                    msgName
            ),
            Util.getMessageType(connecting, msgName)
        );
    }

    @Test
    public void testFullConstructor() throws VerityException {
        Connecting connecting = Connecting.v_06(sourceId, phoneNumber, includePublicDID);
        testConnectMsg(connecting);
    }

    @Test
    public void testConnectMsgPacked() throws Exception {
        Context context = null;
        try {
            context = TestHelpers.getContext();
            Connecting connecting = Connecting.v_06(sourceId, phoneNumber, includePublicDID);
            byte[] message = connecting.connectMsgPacked(context);
            JSONObject expectedMessage = connecting.connectMsg(context);
            JSONObject unpackedMessage = Util.unpackForwardMessage(context, message);

            TestUtil.assertJsonObjectEqual(expectedMessage, unpackedMessage);
        } catch(Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            TestHelpers.cleanup(context);
        }
    }

    @Test
    public void testStatusMsgPacked() throws Exception {
        Context context = null;
        try {
            context = TestHelpers.getContext();
            Connecting connecting = Connecting.v_06(sourceId, phoneNumber, includePublicDID);

            byte [] statusMsg = connecting.statusMsgPacked(context);
            JSONObject expectedMessage = connecting.statusMsg(context);
            JSONObject unpackedMessage = Util.unpackForwardMessage(context, statusMsg);

            TestUtil.assertJsonObjectEqual(expectedMessage, unpackedMessage);
        } catch(Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            TestHelpers.cleanup(context);
        }
    }

    private void testConnectMsg(Connecting connecting) throws VerityException {
        Context context = TestHelpers.getContext();
        JSONObject msg = connecting.connectMsg(context);
        assertEquals(Util.getMessageType(connecting, Connecting.CREATE_CONNECTION), msg.getString("@type"));
        assertNotNull(msg.getString("@id"));
        assertEquals(msg.getString("sourceId"), sourceId);
        if(phoneNumber != null)
            assertEquals(msg.getString("phoneNo"), phoneNumber);
        assertEquals(msg.getBoolean("includePublicDID"), includePublicDID);

        JSONObject statusMsg = connecting.statusMsg(context);
        assertEquals(Util.getMessageType(connecting, Connecting.GET_STATUS), statusMsg.getString("@type"));
        assertNotNull(statusMsg.getString("@id"));
        assertEquals(statusMsg.getString("sourceId"), sourceId);
    }
}