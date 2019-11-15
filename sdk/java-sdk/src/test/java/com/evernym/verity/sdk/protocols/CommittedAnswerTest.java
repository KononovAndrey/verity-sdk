package com.evernym.verity.sdk.protocols;

import com.evernym.verity.sdk.TestHelpers;
import com.evernym.verity.sdk.utils.Context;
import com.evernym.verity.sdk.utils.Util;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommittedAnswerTest {

    private String forRelationship = "abcd12345";
    private String questionText = "Question text";
    private String questionDetail = "Optional question detail";
    private String[] validResponses = {"Yes", "No"};
    private boolean requireSignature = true;

    @Test
    public void testGetMessageType() {
        CommittedAnswer questionAnswer = new CommittedAnswer(forRelationship, questionText, questionDetail, validResponses);
        String msgName = "msg name";
        assertEquals(Util.getMessageType(Util.COMMUNITY_MSG_QUALIFIER, "committedanswer", "1.0", msgName), questionAnswer.getMessageType(msgName));
    }

    @Test
    public void testConstructor() {
        CommittedAnswer questionAnswer = new CommittedAnswer(
                forRelationship,
                questionText,
                questionDetail,
                validResponses,
                requireSignature
        );
        assertEquals(forRelationship, questionAnswer.forRelationship);
        assertEquals(questionText, questionAnswer.questionText);
        assertEquals(questionDetail, questionAnswer.questionDetail);
        assertEquals(validResponses.length, questionAnswer.validResponses.length);
        testMessages(questionAnswer);
    }

    private void testMessages(CommittedAnswer questionAnswer) {
        JSONObject msg = questionAnswer.messages.getJSONObject(CommittedAnswer.ASK_QUESTION);

        msg = new JSONObject(msg.toString());

        assertEquals(questionAnswer.getMessageType(CommittedAnswer.ASK_QUESTION), msg.getString("@type"));
        assertNotNull(msg.getString("@id"));
        assertEquals(forRelationship, msg.getString("~for_relationship"));
        assertEquals(questionText, msg.getString("text"));
        assertEquals(questionDetail, msg.getString("detail"));
        assertEquals(validResponses[0], msg.getJSONArray("valid_responses").getString(0));
        assertEquals(requireSignature, msg.getBoolean("signature_required"));
        assertNotNull(msg.getJSONArray("valid_responses"));

        JSONObject statusMsg = questionAnswer.messages.getJSONObject(CommittedAnswer.GET_STATUS);
        assertEquals(questionAnswer.getMessageType(CommittedAnswer.GET_STATUS), statusMsg.getString("@type"));
        assertNotNull(statusMsg.getString("@id"));
        assertNotNull(statusMsg.getJSONObject("~thread").getString("thid"));
        assertEquals(forRelationship, statusMsg.getString("~for_relationship"));
    }

    @Test
    public void testAsk() throws Exception {
        Context context = null;
        try {
            context = TestHelpers.getContext();
            CommittedAnswer questionAnswer = new CommittedAnswer(forRelationship, questionText, questionDetail, validResponses);
            questionAnswer.disableHTTPSend();
            byte[] message = questionAnswer.ask(context);
            JSONObject unpackedMessage = Util.unpackForwardMessage(context, message);
            assertEquals(questionAnswer.getMessageType(CommittedAnswer.ASK_QUESTION), unpackedMessage.getString("@type"));
        } catch(Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            TestHelpers.cleanup(context);
        }
    }
}