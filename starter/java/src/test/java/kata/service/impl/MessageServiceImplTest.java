package kata.service.impl;

import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MessageServiceImplTest {

    private static Map<String, String> userMap;
    private MessageServiceImpl messageService;

    @BeforeAll
    private void setUp() {
        userMap = new HashMap<>();
        userMap.put("Alice", "1");
        userMap.put("Bob", "2");
    }

    @BeforeEach
    private void init() {
        messageService = new MessageServiceImpl();
    }

    @AfterEach
    private void destroy() {
        messageService = null;
    }

    @AfterAll
    private void cleanUp() {
        userMap.clear();
        userMap = null;
    }

    @DisplayName(" Given Alice has published \"I love the weather today.\"\n" +
            "      When Alice views her timeline\n" +
            "      Then Alice sees:\n" +
            "         \"I love the weather today.")
    @Test
    @Tag("Publishing")
    void test_messageAvailableOnTimeline_whenMessageIsAdded() {


        messageService.addMessage(userMap.get("Alice"), "I love the weather today.");

        assertAll(
                () -> assertNotNull(messageService.getMessages(userMap.get("Alice"))),
                () -> assertEquals("I love the weather today.", messageService.getMessages(userMap.get("Alice")).stream().filter(message -> message.getMessageText().equals("I love the weather today.")).map(message -> message.getMessageText()).findAny().get(), "should return the message"));
    }

    @DisplayName("Given Bob has published \"Darn! We lost!\"\n" +
            "      And Bob has published \"Good game though.\"\n" +
            "      When Alice views Bob's timeline\n" +
            "      Then Alice sees:\n" +
            "         Good game though. (1 minute ago)\n" +
            "         Darn! We lost! (2 minute ago)")
    @Test
    @Tag("Timeline")
    void test_messagesAvailableOnTimelineWithTimeHistory_whenMultipleMessagesAreAdded() {

        String message1 = "Darn! We lost!";
        messageService.addMessage(userMap.get("Bob"), message1);
        String message2 = "Good game though.";
        messageService.addMessage(userMap.get("Bob"), message2);

        assertAll(
                () -> assertNotNull(messageService.getMessages(userMap.get("Bob")).stream().filter(message -> message.getMessageTime() != null).collect(Collectors.toList()), "should return list after filtering through messagetime null check"),
                () -> assertTrue(messageService.getMessages(userMap.get("Bob")).stream().filter(message -> message.getMessageText().equals(message1)).map(message -> message.getMessageTime()).findAny().get().isBefore(messageService.getMessages(userMap.get("Bob")).stream().filter(message -> message.getMessageText().equals(message2)).map(message -> message.getMessageTime()).findAny().get()), "should return true, as message1 is posted before message2")
        );

        //added localdatetime (messagetime) property to message class, to capture timestamp, and leaving the onus of formation of message (with "1 minute ago" appended text) to the consumer of method
    }

}